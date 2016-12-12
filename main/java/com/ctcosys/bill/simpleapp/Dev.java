package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static java.security.AccessController.getContext;

/**
 * Created by Admin on 12/11/2016.
 */

public class Dev extends Activity implements DevSurfaceView.TimeKeeper {

    //DevSurfaceView devView;
    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        timer = (Chronometer) findViewById(R.id.chronometer);
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

    }
    public long getTime(){
        long time = SystemClock.elapsedRealtime() - timer.getBase();
        return time;
    }

    public void saveLevel(MotionGroup mg){
        String filename="LevelOne";
        PrintWriter pw = null;
        FileOutputStream os = null;
        File file = new File(this.getFilesDir(), filename);
        String write = null;
        int i = 1;

        // an ugly system of checking and opening file streams
        try {
            boolean exists = file.exists();
            if(exists){
                os = openFileOutput(filename, Context.MODE_PRIVATE);
                pw = new PrintWriter(os);
                Boolean error = pw.checkError();
                if(error)
                    System.err.println("ERROR FOUND");
                System.out.println("SAVED SCORE");
            }
            else {
                throw new FileNotFoundException("File Not Found");
            }
        } catch(IOException e) {
            return;
        }

        // Print MotionGroup contents
        MotionGroup curr = mg;
        while(curr != null){
            write = "Motion " + i + "\r\n";
            pw.print(write);
            write = mg.getLine() + "\r\n";
            // check
            while(write != null){
                pw.print(write);
                write = mg.getLine() + "\r\n";
            }
            pw.print("\r\n");
            i++;
        }

        // close files and such
        try {
            os.close();
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
