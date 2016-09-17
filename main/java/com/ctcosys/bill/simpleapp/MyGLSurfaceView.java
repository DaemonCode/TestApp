package com.ctcosys.bill.simpleapp;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by Admin on 8/28/2016.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    //private final float TOUCH_SCALE_FACTOR = 0.0001f;
    private float mPreviousX;
    private float mPreviousY;

    //this pointer points to render object
    private int mActivePointerId = -1;

    private onScoreUpdateListener updater;

    //Constructor (Context context)
    public MyGLSurfaceView(Context activity) {
        super(activity);

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
        //might need this: only draws when there is a change in drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public interface onScoreUpdateListener {
        public void onUpdate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        final int action = MotionEventCompat.getActionMasked(e);

        switch(action){
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(e);

                final float x = e.getX(pointerIndex);
                final float y = e.getY(pointerIndex);

                //where did we start the motion?
                mPreviousX = x;
                mPreviousY = y;

                //anyways save this pointerId for later
                mActivePointerId = e.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //find index of active pointer and fetch position
                final int pointerIndex = e.findPointerIndex(mActivePointerId);

                final float x = e.getX(pointerIndex);
                final float y = e.getY(pointerIndex);

                //calculate distance moved
                final float dx = x - mPreviousX;
                final float dy = y - mPreviousY;


                //tell the renderer how to translate
                mRenderer.setTranslation(dx,dy);
                //updater.onUpdate();
                requestRender();

                mPreviousX=x;
                mPreviousY=y;
                if(dx > 0 || dy > 0){


                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = -1;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = -1;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = e.getActionIndex();
                final int pointerId = e.getPointerId(pointerIndex);

                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mPreviousX = e.getX(newPointerIndex);
                    mPreviousY = e.getY(newPointerIndex);
                    mActivePointerId = e.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }
}
