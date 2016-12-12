package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout mainLay;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //mGLView = (MyGLSurfaceView) findViewById(R.id.glSurfaceView);
        mainLay = (RelativeLayout) findViewById(R.id.gameLayout);

        scoreKeeper = (TextView)findViewById(R.id.gameScore);
        scoreKeeper.setText("Score: " + score);
        button = (Button) findViewById(R.id.buttonStart);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                // inflate popup.xml and create handle
                ViewGroup container = (ViewGroup)layoutInflater.inflate(R.layout.popup, null);
                // create popup window
                popupWindow = new PopupWindow(container,600,300,true);
                popupWindow.showAtLocation(mainLay, Gravity.CENTER,0,0);
                Button sendUser = (Button) container.findViewById(R.id.send_score);
                // get handle to username EditText for inner class
                final EditText name = (EditText) container.findViewById(R.id.edit_name);

                sendUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = name.getText().toString();
                        setUser(user);
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onPause(){super.onPause(); }
    public void setUser(String user){
        saveScore(this,user);
    }

    public void onUpdate(){
        Integer intScore = Integer.parseInt(score);
        intScore++;
        score = intScore.toString();
        scoreKeeper.setText("Score: " + score);
    }
    public void saveScore(Context context, String name){
        String filename="scoreFile";
        FileOutputStream outputStream;
        File file = new File(context.getFilesDir(), filename);
        String write = name + " Score is: " + score;
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
