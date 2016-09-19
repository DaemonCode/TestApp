package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Game extends Activity implements MyGLSurfaceView.onScoreUpdateListener{

    private GLSurfaceView mGLView;
    private TextView scoreKeeper;
    private String score="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGLView = (MyGLSurfaceView) findViewById(R.id.glSurfaceView);

        //creates GLSurfaceview instance, sets as content for this activity
        //replace by findViewById for XML
        //mGLView = new MyGLSurfaceView(this);

        scoreKeeper = (TextView)findViewById(R.id.gameScore);
        scoreKeeper.setText("Score: " + score);
        //setContentView(mGLView);
    }
    @Override
    protected void onStop(){
        super.onStop();
        Context context = this;
        saveScore(context);
    }

    public void onUpdate(){
        Integer intScore = Integer.parseInt(score);
        intScore++;
        score=intScore.toString();
        scoreKeeper.setText("Score: " + score);
    }
    public void saveScore(Context context){
        String filename="scoreFile";
        FileOutputStream outputStream;
        File file = new File(context.getFilesDir(), filename);
        String write = "High Score is: " + score;

        try {
            File check = file;
            boolean exists = check.exists();
            if(exists){
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                PrintWriter pw = new PrintWriter(outputStream);
                pw.print(write);
                Boolean error = pw.checkError();
                if(error)
                    System.out.println("ERROR FOUND");
                //outputStream.write(write);
                outputStream.close();
                pw.close();
                System.out.println("SAVED SCORE");
                /*
                FileInputStream fis = context.openFileInput(filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String testScore=br.readLine();
                System.out.println(testScore);*/
            }
            else {
                throw new FileNotFoundException("File Not Found");
            }
        } catch(IOException e) {
            System.out.println("FUCKING HELL");

        }

    }

}
