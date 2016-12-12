package com.ctcosys.bill.simpleapp;

/**
 * Created by Admin on 12/10/2016.
 */

public class Circle {

    Triangle[] circle;
    int iters = 0;

    public Circle(){

        float init = 10;
        float angle = init;
        float angleRads = (float)(Math.toRadians(angle));
        float length = 0.1f;
        iters = (360/(int)angle);
        circle = new Triangle[iters];
        circle[0] = new Triangle(length,angleRads,length,0f);
        //mTriangle = new Triangle(length,angleRads,length,0f);


        for(int i =1;i<iters; i++){
            angle = angle + init;
            angleRads = (float)(Math.toRadians(angle));
            circle[i] = new Triangle(length,angleRads,circle[i-1].getNewX(),circle[i-1].getNewY());

        }
    }
    public void draw(float[] mvpMatrix){
        for(int i=0;i<iters;i++){
            circle[i].draw(mvpMatrix);
        }
    }
    public float getX(){return circle[0].getX();}
    public float getY(){return circle[0].getY();}
    public float getNewX(){return circle[0].getNewX();}
    public float getNewY(){return circle[0].getNewY();}
    public void setX(float x){ circle[0].setX(x);}
    public void setY(float y){ circle[0].setY(y);}
}
