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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName="scoreFile";
        String testScore;
        Context context = getApplicationContext();
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;

        TextView textView;

        setContentView(R.layout.activity_scores);

        try {
            // open and read file
            fis = context.openFileInput(fileName);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            testScore=br.readLine();

            // set up score TextView
            textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText(testScore);
        } catch(IOException e){
            // File doesn't exist. No scores available
            textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText("No scores available");
        }

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_score_screen);
        layout.addView(textView);

    }
}
