package com.example.roony.mycalculator;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.String;
import java.lang.Integer;
import java.util.Arrays;
import bsh.EvalError;
import bsh.Interpreter;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    TextView rsText = null;  //显示器
    boolean isClear = false; //用于是否显示器需要被清理
    Button btn_0;
    Button btn_1;
    Button btn_2;
    Button btn_3;
    Button btn_4;
    Button btn_5;
    Button btn_6;  //数字按钮
    Button btn_7;
    Button btn_8;
    Button btn_9;
    Button btn_clear;
    Button btn_del;
    Button btn_zhuan2;
    Button btn_zhuan8;
    Button btn_sin;
    Button btn_cos;
    Button btn_sqrt;
    Button btn_goback;
    Button btn_CF;
    Button btn_help;
    Button btn_esc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn_0 = (Button) findViewById(R.id.Button0);
        btn_1 = (Button) findViewById(R.id.Button1);
        btn_2 = (Button) findViewById(R.id.Button2);
        btn_3 = (Button) findViewById(R.id.Button3);
        btn_4 = (Button) findViewById(R.id.Button4);
        btn_5 = (Button) findViewById(R.id.Button5);
        btn_6 = (Button) findViewById(R.id.Button6);
        btn_7 = (Button) findViewById(R.id.Button7);
        btn_8 = (Button) findViewById(R.id.Button8);
        btn_9 = (Button) findViewById(R.id.Button9);
        btn_clear = (Button) findViewById(R.id.ButtonC);
        btn_del = (Button) findViewById(R.id.Buttonback);
        btn_zhuan2 = (Button) findViewById(R.id.Buttonzhuan2);
        btn_zhuan8 = (Button) findViewById(R.id.Buttonzhuan8);
        btn_sin = (Button) findViewById(R.id.Buttonsin);
        btn_cos = (Button) findViewById(R.id.Buttoncos);
        btn_sqrt = (Button) findViewById(R.id.Buttonsqrt);
        btn_goback = (Button) findViewById(R.id.Buttongoback);
        btn_CF = (Button) findViewById(R.id.ButtonCF);
        btn_help = (Button) findViewById(R.id.Buttonhelp);
        btn_esc = (Button) findViewById(R.id.Buttonesc);
        rsText = (TextView) findViewById(R.id.rsText);


        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_zhuan2.setOnClickListener(this);
        btn_zhuan8.setOnClickListener(this);
        btn_sin.setOnClickListener(this);
        btn_cos.setOnClickListener(this);
        btn_sqrt.setOnClickListener(this);
        btn_CF.setOnClickListener(this);
        btn_esc.setOnClickListener(this);
        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);

                builder.setMessage("这是一个帮助！");
                builder.show();
            }
        });
    }


    @Override
    public void onClick(View e) {
        Button btn = (Button)e;
        String exp = rsText.getText().toString();
        if(isClear &&(
                btn.getText().equals("0")
                        ||btn.getText().equals("1")
                        ||btn.getText().equals("2")
                        ||btn.getText().equals("3")
                        ||btn.getText().equals("4")
                        ||btn.getText().equals("5")
                        ||btn.getText().equals("6")
                        ||btn.getText().equals("7")
                        ||btn.getText().equals("8")
                        ||btn.getText().equals("9")
                        ||btn.getText().equals("."))
                ||btn.getText().equals("算数公式错误")){
            rsText.setText("");
            isClear = false;
        }
        if(btn.getText().equals("C")){
            rsText.setText("");
        }else if(btn.getText().equals("←")){
            if(isEmpty(exp)) return;
            else
                rsText.setText(exp.substring(0, exp.length()-1));
        }
        //实现sin，cos，开根
        else if(btn.getText().equals("sqrt")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            double a=Double.parseDouble(sart.getText().toString());//数据类型转换
            double num1=Math.sqrt(a);
            rsText.setText(Double.toString(num1));
        }
        else if(btn.getText().equals("sin")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            double a=Double.parseDouble(sart.getText().toString());
            double num2=Math.sin(a*Math.PI/180);
            rsText.setText(Double.toString(num2));
        }
        else if(btn.getText().equals("cos")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            double a=Double.parseDouble(sart.getText().toString());
            double num3=Math.cos(a*Math.PI/180);
            rsText.setText(Double.toString(num3));
        }


        //实现单位换算
        else if(btn.getText().equals("CF")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            double a=Double.parseDouble(sart.getText().toString());
            double f=32+a*1.8;
            rsText.setText(Double.toString(f));
        }
        else if(btn.getText().equals("二")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            int b = Integer.valueOf(sart.getText().toString());
            rsText.setText(String.valueOf(Integer.toBinaryString((int) b)));
        }
        else if(btn.getText().equals("八")){
            TextView sart=(TextView) findViewById(R.id.rsText);
            int b = Integer.valueOf(sart.getText().toString());
            rsText.setText(String.valueOf(Integer.toOctalString((int) b)));
        }


        else{
            rsText.setText(rsText.getText()+""+btn.getText());
            isClear = false;
        }
    }

    /***
     * @param  exp 算数表达式
     * @return 根据表达式返回结果
     */
    //计算，传入需要计算的字符串，返回结果字符串
    private String getRs(String exp){
        Interpreter bsh = new Interpreter();
        Number result = null;
        try {
            exp = filterExp(exp);
            result = (Number)bsh.eval(exp);
        } catch (EvalError e) {
            e.printStackTrace();
            isClear = true;
            return "算数公式错误";
        }
        exp = result.doubleValue()+"";
        if(exp.endsWith(".0"))
            exp = exp.substring(0, exp.indexOf(".0"));
        return exp;
    }


    /**
     * 因为计算过程中,全程需要有小数参与,所以需要过滤一下
     * @param exp 算数表达式
     * @return
     */
    private String filterExp(String exp) {
        String num[] = exp.split("");
        String temp = null;
        int begin=0,end=0;
        for (int i = 1; i < num.length; i++) {
            temp = num[i];
            if(temp.matches("[+-/()*]")){
                if(temp.equals(".")) continue;
                end = i - 1;
                temp = exp.substring(begin, end);
                if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                    num[i-1] = num[i-1]+".0";
                begin = end + 1;
            }
        }
        return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
    }

    /***
     * @param str
     * @return 字符串非空验证
     */
    private boolean isEmpty(String str){
        return (str==null || str.trim().length()==0);
    }

}

