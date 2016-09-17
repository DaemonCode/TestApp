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
    private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //creates GLSurfaceview instance, sets as content for this activity
        mGLView = new MyGLSurfaceView(this);
        //scoreKeeper = (TextView)findViewById(R.id.gameScore);
        //scoreKeeper.setText(score);
        setContentView(mGLView);

        //addContentView(scoreKeeper, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void onUpdate(){

        score++;
        scoreKeeper.setText(score);
    }
    public void saveScore(){
        //save it?
    }

}
