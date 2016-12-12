package com.ctcosys.bill.simpleapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

/**
 * Created by Admin on 12/11/2016.
 */

public class DevSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private TimeKeeper timeKeeper;
    MotionGroup head = null;
    MotionGroup currMG = head;

    public DevSurfaceView(Context activity, AttributeSet attrs) {
        super(activity,attrs);

        setEGLContextClientVersion(2);
        //set version
        mRenderer = new MyGLRenderer();
        //set renderer for drawing on surfaceview
        setRenderer(mRenderer);
        // only draws when there is a change in drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.DevLayout);
        Button save = (Button) layout.findViewById(R.id.save_level);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timeKeeper.saveLevel(head);
            }
        });

        try {
            //make sure container activity implements callback interface
            timeKeeper =(TimeKeeper) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement TimeKeeper");
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        float rx;
        float ry;
        float coords[] = new float[3];

        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                coords[2] = (float)timeKeeper.getTime();
                mRenderer.computeCoords(coords);
                // Create new MotionGRoup
                MotionGroup mg = new MotionGroup();
                // make new MotionGroup the current work group
                currMG = mg;
                // add coordinates and time to MotionGroup
                mg.add(coords);
                // Insert MotionGroup into linked list
                if(head == null)
                    head = mg;
                else {
                    MotionGroup curr = head;
                    while(curr.next != null){
                        curr = curr.next;
                    }
                    curr.next = mg;
                }
                mRenderer.setRender(true);
                requestRender();
                break;
            case MotionEvent.ACTION_MOVE:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                coords[2] = (float)timeKeeper.getTime();
                mRenderer.computeCoords(coords);
                currMG.add(coords);
                mRenderer.setRender(true);
                requestRender();
                break;
            case MotionEvent.ACTION_UP:
                rx = e.getRawX();
                ry = e.getRawY();
                coords[0] = rx;
                coords[1] = ry;
                coords[2] = (float)timeKeeper.getTime();
                mRenderer.computeCoords(coords);
                currMG.add(coords);
                currMG = null;
                mRenderer.setRender(false);
                requestRender();
                break;
        }
        return true;
    }
    public interface TimeKeeper{
        long getTime();
        void saveLevel(MotionGroup mg);
    }
}
