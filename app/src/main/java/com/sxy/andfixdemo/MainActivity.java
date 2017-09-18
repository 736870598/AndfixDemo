package com.sxy.andfixdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView showResult;
    private Calculate calculate = new Calculate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FixManager.getManager().setContext(getApplicationContext());

        showResult = (TextView) findViewById(R.id.showResult);

    }

    public void calculate(View view){
        int reslut = calculate.calculateResult();
        showResult.setText("结果：" + reslut);
        Log.v("showResult", "计算结果：" + reslut);
    }

    public void fix(View view){
        FixManager.getManager().loadFile(new File(Environment.getExternalStorageDirectory(),"out.apatch"));
    }


}
