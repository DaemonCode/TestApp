package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Admin on 8/28/2016.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    //Dev mode or Game mode

    private onScoreUpdateListener updater;

    public MyGLSurfaceView(Context activity, AttributeSet attrs) {
        super(activity, attrs);

        try {
            //make sure container activity implements callback interface so
            // we can update the score textview
            updater =(onScoreUpdateListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement onScoreUpdaterListener");
        }

        setEGLContextClientVersion(2);
        //set version
        mRenderer = new MyGLRenderer();
        //set renderer for drawing on surfaceview
        setRenderer(mRenderer);
        // only draws when there is a change in drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public interface onScoreUpdateListener {
        void onUpdate();
        void saveScore(Context context, String user);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        float rx;
        float ry;
        float coords[] = new float[2];

        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                updater.onUpdate();
                mRenderer.computeCoords(coords);
                mRenderer.setRender(true);
                requestRender();
                break;
            case MotionEvent.ACTION_MOVE:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                updater.onUpdate();
                mRenderer.computeCoords(coords);
                mRenderer.setRender(true);
                requestRender();
                break;
            case MotionEvent.ACTION_UP:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                updater.onUpdate();
                mRenderer.computeCoords(coords);
                mRenderer.setRender(false);
                requestRender();
                break;
        }
        return true;
    }
}
