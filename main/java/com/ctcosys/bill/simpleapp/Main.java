package com.ctcosys.bill.simpleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view){
        Intent intent = new Intent(this,Game.class);
        startActivity(intent);
    }
    public void openScores(View view){
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }
}
