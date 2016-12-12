package com.ctcosys.bill.simpleapp;

import java.util.ArrayList;

/**
 * Created by Admin on 12/11/2016.
 */

public class MotionGroup {
    static int motions = 0;


    int motionNum;
    MotionGroup next;
    ArrayList<float[]> list = new ArrayList<>();
    int i = 0;

    public MotionGroup(){
        motionNum = ++motions;
        next = null;

    }
    public void add(float[] bundle){
        list.add(bundle);
    }
    public String getLine(){
        String line = null;
        float[] arr;
        if(list.get(i) != null){
            arr=list.get(i);
            line = arr[2] + " " + arr[0] + " " + arr[1];
        }
        else
            i = 0;
        return line;
    }
}
