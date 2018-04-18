package com.example.roony.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;

import bsh.EvalError;
import bsh.Interpreter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView rsText = null;  //显示器
    boolean isClear = false; //用于是否显示器需要被清理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_0 ;
        Button btn_1;
        Button btn_2;
        Button btn_3 ;
        Button btn_4 ;
        Button btn_5 ;
        Button btn_6 ;  //数字按钮
        Button btn_7 ;
        Button btn_8 ;
        Button btn_9 ;
        Button btn_point ;  //小数点按钮
        Button btn_clear ;
        Button btn_del ;
        Button btn_jia ;
        Button btn_jian ;
        Button btn_cheng ;
        Button btn_chu ;
        Button btn_de ;
        Button btn_more ;
        Button btn_zuo ;
        Button btn_you ;


        //listener
        rsText = (TextView) findViewById(R.id.rsText);//实化后的显示屏
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
        btn_point = (Button) findViewById(R.id.Buttondian);
        btn_clear = (Button) findViewById(R.id.ButtonC);
        btn_del = (Button) findViewById(R.id.Buttonback);
        btn_jia = (Button) findViewById(R.id.Buttonjia);
        btn_jian = (Button) findViewById(R.id.Buttonjian);
        btn_cheng = (Button) findViewById(R.id.Buttoncheng);
        btn_chu = (Button) findViewById(R.id.Buttonchu);
        btn_de = (Button) findViewById(R.id.Buttonde);
        btn_more = (Button) findViewById(R.id.Buttonmore);
        btn_zuo = (Button) findViewById(R.id.Buttonzuo);
        btn_you = (Button) findViewById(R.id.Buttonyou);


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
        btn_point.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_jia.setOnClickListener(this);
        btn_jian.setOnClickListener(this);
        btn_cheng.setOnClickListener(this);
        btn_chu.setOnClickListener(this);
        btn_de.setOnClickListener(this);
        btn_zuo.setOnClickListener(this);
        btn_you.setOnClickListener(this);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
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
                ){
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

        else if(btn.getText().equals("=")){
            if(isEmpty(exp)) return;
            exp = exp.replaceAll("×", "*");
            exp = exp.replaceAll("÷", "/");
            rsText.setText(getRs(exp));
            isClear = false;
        }

        else{
            rsText.setText(rsText.getText()+""+btn.getText());
            isClear = false;
        }
    }

    //  @param  exp 算数表达式
    //  @return 根据表达式返回结果

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
        return Arrays.toString(num).replaceAll("[\\[\\], ]", "");//处理空括号
    }
    /***
     * @param str
     * @return 字符串非空验证
     */
    private boolean isEmpty(String str){
        return (str==null || str.trim().length()==0);
    }

}