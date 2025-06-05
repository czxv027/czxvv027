package com.example.ma;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //这里只能加声明，并不能使用函数
    EditText output;
    Button id0;
    Button id1;
    Button id2;
    Button id3;
    Button id4;
    Button id5;
    Button id6;
    Button id7;
    Button id8;
    Button id9;
    Button jia;
    Button jian;
    Button chen;
    //Button chu;
    //Button del;
    Button clear;
    Button cal;
    iStack s1=new iStack(); //s1是值栈，s2是符号栈
    cStack s2=new cStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output=(EditText)findViewById(R.id.output);
        id0=(Button)findViewById(R.id.id0);
        id1=(Button)findViewById(R.id.id1);
        id2=(Button)findViewById(R.id.id2);
        id3=(Button)findViewById(R.id.id3);
        id4=(Button)findViewById(R.id.id4);
        id5=(Button)findViewById(R.id.id5);
        id6=(Button)findViewById(R.id.id6);
        id7=(Button)findViewById(R.id.id7);
        id8=(Button)findViewById(R.id.id8);
        id9=(Button)findViewById(R.id.id9);
        jia=(Button)findViewById(R.id.jia);
        jian=(Button)findViewById(R.id.jian);
        chen=(Button)findViewById(R.id.chen);
        //chu=(Button)findViewById(R.id.chu);
        //del=(Button)findViewById(R.id.del);
        clear=(Button)findViewById(R.id.clear);
        cal=(Button)findViewById(R.id.cal);

        id0.setOnClickListener(this);   //为此，我的类加了个接口
        id1.setOnClickListener(this);
        id2.setOnClickListener(this);
        id3.setOnClickListener(this);
        id4.setOnClickListener(this);
        id5.setOnClickListener(this);
        id6.setOnClickListener(this);
        id7.setOnClickListener(this);
        id8.setOnClickListener(this);
        id9.setOnClickListener(this);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);
        chen.setOnClickListener(this);
        //chu.setOnClickListener(this);
        clear.setOnClickListener(this);
        //del.setOnClickListener(this);
        cal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str=output.getText().toString();
        switch(v.getId()) {
            case R.id.jia:
            case R.id.jian:
            case R.id.chen:
                //case R.id.chu:
                int len=str.length();
                if(len!=0)
                {
                    char lastcode=str.charAt(len-1);
                    if(lastcode<48||lastcode>57)
                        str=str.substring(0,len-1);
                }
            case R.id.id0:
            case R.id.id1:
            case R.id.id2:
            case R.id.id3:
            case R.id.id4:
            case R.id.id5:
            case R.id.id6:
            case R.id.id7:
            case R.id.id8:
            case R.id.id9:
                str=str+((Button)v).getText();
                output.setText(str);
                break;
            case R.id.clear:
                str="";
                output.setText(str);
                break;
            //case R.id.del:
            case R.id.cal:
                getAns();
                break;
        }
    }
    public void getAns()
    {
        String str=output.getText().toString()+'#';
        int len=str.length();
        if(len==1)return;
        if(str.charAt(0) < 48 || str.charAt(0) > 57)
        {
            str=str.substring(1,len-1);
            output.setText(str);
            return;
        }
        if(str.charAt(len-2) < 48 || str.charAt(len-2) > 57)
        {
            str=str.substring(0,len-2);
            output.setText(str);
            return;
        }
        s2.push('#');
        int n=0;    //n是表示扫描到的数字值为多少,初始化为0
        boolean book=false; //没办法，用来当做n取值的有效值,true有效
        for(int i=0;i<len;i++) {
            char c = str.charAt(i);
            if (c >= 48 && c <= 57) {
                n = n * 10 + c - 48;
                book = true;
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '#' ) {
                if (book == true) {
                    s1.push(n); //遇到符号就入栈，并清零
                    n = 0;
                    book = false;
                }
                if (compare(c)) {    //如果当前优先级要低就开始计算
                    int n2 = s1.top();    //3+5，则n1为3，n2为5
                    s1.pop();
                    ;
                    int n1 = s1.top();
                    s1.pop();
                    ;

                    char op = s2.top();
                    s2.pop();
                    int ans = 0;
                    switch (op) {
                        case '+':
                            ans = n1 + n2;
