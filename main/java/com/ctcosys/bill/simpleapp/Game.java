package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onUpdate(){
        Integer intScore = Integer.parseInt(score);
        intScore++;
        score=intScore.toString();
        scoreKeeper.setText("Score: " + score);
    }
    public void saveScore(){
        //save it?
    }

}
