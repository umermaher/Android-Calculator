package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import java.util.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;
import javax.xml.xpath.XPathExpression;


public class MainActivity extends AppCompatActivity {
    private TextView expression, result;  //expression input from the user, and result textview to display result after the evaluation
    private Button equal,sinBtn,cosBtn,tanBtn,lnBtn,logBtn;
    private String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expression = findViewById(R.id.concat);
        result = findViewById(R.id.result);
        equal = findViewById(R.id.equalto);
        sinBtn=findViewById(R.id.sin);
        cosBtn=findViewById(R.id.cos);
        tanBtn=findViewById(R.id.tan);
        lnBtn=findViewById(R.id.ln);
        logBtn=findViewById(R.id.log);
//        if(concat.getText().toString().length()==12){
//            concat.setTextSize(5);
//        }
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = expression.getText().toString();
                //replacing signs and expressions
                text = text.replaceAll("÷", "/");
                text = text.replaceAll("x", "*");
                text = text.replaceAll("%", "*0.01*");
                text=text.replaceAll("e",String.valueOf(Math.E));
                text=text.replaceAll("√","sqrt");
                text=text.replaceAll("π",String.valueOf(Math.PI));
                text=text.replaceAll("log","log10");

                try {
                    Expression exp = new Expression(text);
                    //evaluating the whole expression
                    String res = String.valueOf(exp.calculate());
                    result.setText(res);   //set evaluated expression in result text view
                    double d = Double.parseDouble(res);
                    //for showing the result as integer if and only if +, /, * integers.
                    long i1 = (long) d;
                    boolean test = true;
                    for (int i = 0; i < text.length(); i++) {
                        if (text.charAt(i) == '/' || text.charAt(i) == '.' || text.contains("^-") || text.contains("0.01") ||
                                text.contains(String.valueOf(Math.E)) || text.contains("ln") || text.contains("log") || text.contains("sqrt") || text.contains("pi")){
                            result.setText(String.valueOf(d));
                            test = false;
                            break;
                        }
                    }
                    if (test)
                        result.setText(String.valueOf(i1));

                    //Expression class does not convert degrees in radian, therefore converting angles in degrees into radians is necessary
                    if(text.contains("sin(") || text.contains("sin^-1(") || text.contains("cos(") || text.contains("cos^-1(") || text.contains("tan(") || text.contains("tan^-1(")){
                            d=specifiedFunc(text);
                            result.setText(String.valueOf(d));
                        if(String.valueOf(d).contains("E")){
                            if(text.contains("tan(90)") || text.contains("tan(270)") || text.contains("tan(450)") || text.contains("tan(630)"))
                                result.setText("Error");
                            else
                                result.setText("0");
                        }
                    }

                    //Expression class handle Arithmetic exception by showing NaN
                    if(result.getText().toString()=="NaN"){
                        int a=0,b;
                         b=1/a;
                    }
                } catch (Exception e) {
                    result.setText("Error");
                }
            }
        });
    }

    public void zero(View v) {
        expression.append("0");
    }

    public void one(View v) {
        expression.append("1");
    }

    public void two(View v) {
        expression.append("2");
    }

    public void three(View v) {
        expression.append("3");
    }

    public void four(View v) {
        expression.append("4");
    }

    public void five(View v) {
        expression.append("5");
    }

    public void six(View v) {
        expression.append("6");
    }

    public void seven(View v) {
        expression.append("7");
    }

    public void eight(View v) {
        expression.append("8");
    }

    public void nine(View v) {
        expression.append("9");
    }

    public void dot(View v) {
        addOperator(".");
    }

    public void plus(View v) {
        addOperator("+");
    }

    public void minus(View v) {
        addOperator("-");
    }

    public void multiply(View v) {
        addOperator("x");
    }

    public void divide(View v) {
        addOperator("÷");
    }

    public void power(View v) {
        addOperator("^");
    }

    public void percentage(View v){
        addOperator("%");
    }

    public void clearEntry(View v) {
        String s = expression.getText().toString();
        try {
            StringBuilder sb = new StringBuilder(s);
            sb.deleteCharAt(s.length() - 1);  //Delete last character of the expression
            expression.setText(sb);
        } catch (Exception e) {
            expression.setText("");
        }
    }

    //clearing the whole expression
    public void clear(View v) {
        expression.setText("");
        result.setText("");
    }

    public void addOperator(String op) {
        try {
            String s = expression.getText().toString();
            int i = s.length();
            //checking the last character of expression, if last one is operator, then no operator will be added more , immediately after sign
            if (s.charAt(i-1)=='%' || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-' || s.charAt(i - 1) == '÷' || s.charAt(i - 1) == 'x' || s.charAt(i - 1) == '.' || s.charAt(i - 1) == '^') {
                expression.append("");
            } else
                expression.append(op);
        } catch (Exception e) {
            expression.setText("");
        }
    }
    public void multiplicativeInverse(View v){
        addOperator("^-1");
    }
    public void bracket1(View v){
        expression.append("(");
    }
    public void bracket2(View v){
        expression.append(")");
    }
    public void squareRoot(View v){
        expression.append("√(");
    }
    public void squareRootOf(View v){
        expression.append("^(1÷");
    }
    public void fact(View v){
        try{
            String s=expression.getText().toString();
            int i=s.length();
            if (s.charAt(i-1)=='!' || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-' || s.charAt(i - 1) == '÷' || s.charAt(i - 1) == 'x' || s.charAt(i - 1) == '.' || s.charAt(i - 1) == '^') {
                expression.append("");
            }
            else
                expression.append("!");
        }catch(Exception e){
            expression.setText("");
        }
    }

    public void euler(View v){
        expression.append("e");
    }

    public void ln(View v){
        if(lnBtn.getText().equals("e^x"))
            expression.append("e^");
        else
            expression.append("ln(");
    }

    public void log(View v){
        if(logBtn.getText().equals("10^x"))
            expression.append("10^");
        else
            expression.append("log(");
    }

    public void sine(View v){
        if(sinBtn.getText().equals("sin^-1"))
            expression.append("sin^-1(");
        else
            expression.append("sin(");
    }
    public void cosine(View v){
        if(cosBtn.getText().equals("cos^-1"))
            expression.append("cos^-1(");
        else
            expression.append("cos(");
    }
    public void tangent(View v){
        if(tanBtn.getText().equals("tan^-1"))
            expression.append("tan^-1(");
        else
            expression.append("tan(");
    }
    int count=1;
    //converting functions into inverse function and inverse function into function
    public void inverse(View v){
        count++;
        if(count%2==0){
            sinBtn.setText("sin^-1");
            cosBtn.setText("cos^-1");
            tanBtn.setText("tan^-1");
            lnBtn.setText("e^x");
            logBtn.setText("10^x");
        }
        else {
            sinBtn.setText("sin");
            cosBtn.setText("cos");
            tanBtn.setText("tan");
            lnBtn.setText("ln");
            logBtn.setText("log");
        }
    }
    public void pi(View v){
        //expression.append("π");
        try {
            String s = expression.getText().toString();
            int i = s.length();
            //checking the last character of expression, if last one is sign, then no sign will be added more , immediately after sign
            if (s.charAt(i-1)=='%' || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-' || s.charAt(i - 1) == '÷' || s.charAt(i - 1) == 'x' || s.charAt(i - 1) == '(' || s.charAt(i - 1) == ')'  ) {
                expression.append("π");
            } else
                expression.append("xπ");
        } catch (Exception e) {
            expression.setText("π");
        }
    }

    //for evaluating trigonmetric functions as Expression class does not convert degrees into radians
    public double specifiedFunc(String s){
        double d=0;
        try{
            int[] brackets=scope(s);
            double ang=Math.toRadians(Double.parseDouble(s.substring(brackets[0]+1,brackets[1])));
            double val=Double.parseDouble(s.substring(brackets[0]+1,brackets[1]));
            if(s.contains("sin(") ){
                d = Math.sin(ang);
            }
            if(s.contains("cos(") ){
                d = Math.cos(ang);
            }
            if(s.contains("tan(") ){
                d = Math.tan(ang);
            }
            if(s.contains("sin^-1(") ){
                d = Math.asin(val);
                d=(d*180)/Math.PI;
            }
            if(s.contains("cos^-1(") ){
                d = Math.acos(val);
                d=(d*180)/Math.PI;
            }
            if(s.contains("tan^-1(")){
                d = Math.atan(val);
                d=(d*180)/Math.PI;
            }
        }catch (Exception e){
            result.setText("Error");
        }
        if(s.contains("E"))
            return 0;
        else
            return d;
    }
    //checking angle
    public int[] scope(String s){
        int b[]=new int[2];
        for(int i=0;i<s.length();i++) {
            if(s.charAt(i)=='('){
                b[0]=i;
            }
            if(s.charAt(i)==')'){
                b[1]=i;
                break;
            }
        }
        return b;
    }
}
