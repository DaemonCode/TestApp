package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

//extends AppCompatActivity?
public class Scores extends Activity {

    private TextView scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName="scoreFile";
        String testScore="";
        Context context = getApplicationContext();
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;

        setContentView(R.layout.activity_scores);

        try {
            fis = context.openFileInput(fileName);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            testScore=br.readLine();
            System.out.println(testScore);
        } catch(IOException e){
            System.out.println("Well Shit");
        }

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(testScore);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_score_screen);
        layout.addView(textView);

        //scores = (TextView)findViewById(R.id.gameScore);
        //scores.setText(testScore);
    }
}
