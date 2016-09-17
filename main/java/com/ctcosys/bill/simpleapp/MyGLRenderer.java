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

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private volatile float dx=0;
    private volatile float dy=0;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private int mWidth;
    private int mHeight;
    private final float scaleFactor = .001f;


    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        //background color (black)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        mTriangle = new Triangle();
    }

    public void onDrawFrame(GL10 unused){
        float[] scratch = new float[16];
        //redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        glMatrixMode(GL_PROJECTION);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //create translational transformation for triangle
        Matrix.translateM(mTriangle.mModelMatrix,0,dx,dy,0);

        //combines projection/view matrix with translation, mMVP first for some reason
        Matrix.multiplyMM(scratch,0,mMVPMatrix,0,mTriangle.mModelMatrix,0);

        //draw triangle
        mTriangle.draw(scratch);

    }/*
    public float[] convertScreenCoords(float x, float y) {
        int[] viewPortMatrix = new int[]{0, 0, (int)mWidth, (int)mHeight};
        float[] outputNear = new float[4];
        float[] outputFar = new float[4];

        y = mHeight - y;
        int successNear = GLU.gluUnProject(x, y, 0, mMVPMatrix, 0, mProjectionMatrix, 0, viewPortMatrix, 0, outputNear, 0);
        int successFar = GLU.gluUnProject(x, y, 1,mMVPMatrix, 0, mProjectionMatrix, 0, viewPortMatrix, 0, outputFar, 0);

        if (successNear == GL_FALSE || successFar == GL_FALSE) {
            throw new RuntimeException("Cannot invert matrices!");
        }

        //convert4DCoords(outputNear);
        //convert4DCoords(outputFar);

        float distance = outputNear[2] / (outputFar[2] - outputNear[2]);
        float normalizedX = (outputNear[0] + (outputFar[0] - outputNear[0]) * distance);
        float normalizedY = (outputNear[1] + (outputFar[1] - outputNear[1]) * distance);

        float[] normalCoords = {normalizedX,normalizedY};
        return normalCoords;
    }*/
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mWidth=width;
        mHeight=height;
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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

    public void setTranslation(float x, float y) {
        dx = x * scaleFactor * -1;
        dy = y * scaleFactor * -1;
    }
}
