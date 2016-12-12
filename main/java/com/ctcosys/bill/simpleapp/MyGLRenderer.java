package com.ctcosys.bill.simpleapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.*;
import static java.lang.Math.PI;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Line mLine;
    private Circle mCircle;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final  float[] mTranslationMatrix = new float[16];

    private int mWidth;
    private int mHeight;

    private float dx = 0;
    private float dy = 0;
    private float ratio = 1;
    Boolean render = false;


    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        //background color (black)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //mLine = new Line();
        mCircle = new Circle();
        Matrix.setIdentityM(mTranslationMatrix,0);
        //dx = 1.5f;
    }

    public void onDrawFrame(GL10 unused){
        float[] scratch = new float[16];
        //redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        glMatrixMode(GL_PROJECTION);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.translateM(mTranslationMatrix, 0, dx*ratio, dy, 0);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //create translational transformation for triangle
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mTranslationMatrix, 0);

        //draw triangle
        if(render)
            mCircle.draw(scratch);

        //mLine.draw(mMVPMatrix);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mWidth=width;
        mHeight=height;
        ratio = (float) width / height;
        float FOV = (float)Math.tan(110f * PI /360.0f);


        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -ratio*FOV, ratio*FOV, -1, 1, 3, 7);
        Matrix.orthoM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7);
    }

    //OpenGL Shading Language Code for environment
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public float[] computeCoords(float[] v){

        v[0] = (v[0] * 2 / mWidth) - 1;
        v[1] *= -1;
        v[1] = (v[1] * 2 / mHeight) + 1;

        dx = (v[0] - mCircle.getX())*-1;
        dy = v[1] - mCircle.getY();

        mCircle.setX(v[0]);
        mCircle.setY(v[1]);
        return v;
    }
    public void setRender(Boolean bool){
        render = bool;
    }
}
