package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    Button btnSub;
    Button btnMul;
    Button btnDiv;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btnRes;
    Button btnC;
    Button btnD;
    Button btnStop;
    TextView textView;

    private static String expression = "";
    private static char operation = ' ';
    private static final String KEY_EXPRESSION = "EXPRESSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnC = findViewById(R.id.btnC);
        btnRes = findViewById(R.id.btnRes);
        btnD = findViewById(R.id.btnD);
        btnStop = findViewById(R.id.btnStop);
        textView = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            expression = savedInstanceState.getString(KEY_EXPRESSION);
        }
        textView.setText(expression);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('1', textView);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('2', textView);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('3', textView);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('4', textView);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('5', textView);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('6', textView);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('7', textView);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('8', textView);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('9', textView);
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('0', textView);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_op('+', textView);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_op('-', textView);
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_op('*', textView);
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_op('/', textView);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                print_expr('.', textView);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                expression = "";
                textView.setText(expression);
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!expression.equals("")) {
                    expression = expression.substring(0, expression.length() - 1);
                }
                textView.setText(expression);
            }
        });
        btnRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (operation != ' ') {
                    try {
                        expression = calc();
                    } catch (Exception e) {
                        expression = "";
                    }
                }
                textView.setText(expression);
            }
        });
    }

    private static void print_expr(char str, TextView textView) {
        expression += str;
        textView.setText(expression);
    }

    private static void print_op(char str, TextView textView) {
        operation = str;
        print_expr(str, textView);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_EXPRESSION, expression);
    }

    private static String calc() {
        int i = 0;
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();
        boolean flag = false;
        while (i < expression.length()) {
            if (operation == expression.charAt(i)) {
                flag = true;
            } else {
                if (!flag) {
                    first.append(expression.charAt(i));
                } else {
                    second.append(expression.charAt(i));
                }
            }
            i++;
        }
        double result = 0;
        double left_operand = Double.parseDouble(first.toString());
        double right_operand = Double.parseDouble(second.toString());
        switch (operation) {
            case ('-'): {
                result = left_operand - right_operand;
                break;
            }
            case ('+'): {
                result = left_operand + right_operand;
                break;
            }
            case ('*'): {
                result = left_operand * right_operand;
                break;
            }
            case ('/'): {
                result = left_operand / right_operand;
                break;
            }
        }
        operation = ' ';
        return Double.toString(result);
    }
}
