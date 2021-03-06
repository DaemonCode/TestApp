package com.ctcosys.bill.simpleapp;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Admin on 8/28/2016.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;

    //number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX=3;
    static float tCoords[]={ //counterclockwise
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };


    //a whole bunch of shader code bullshit for OpenGL
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private final int mProgram;
    //use to access and set the view transformation
    private int mMVPMatrixHandle;

   /*
    Variables and constants for draw() method
    */
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = tCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private float xCoord;
    private float yCoord;
    private float newX;
    private float newY;

    /*
        CONSTRUCTOR!!!
     */
    public Triangle(){

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        //create empty OpenGL ES Program
        mProgram=GLES20.glCreateProgram();
        //add vertexShader to program
        GLES20.glAttachShader(mProgram,vertexShader);
        //add fragment shader to program
        GLES20.glAttachShader(mProgram,fragmentShader);
        //creates OpenGL ES program executable (SHIT WILL ALL WORK TOGETHER NOW)
        GLES20.glLinkProgram(mProgram);

        //initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(tCoords.length*4);
        //^^ number of coords * 4 bytes per float
        //use device hardware native byte order (big/little endian)
        bb.order(ByteOrder.nativeOrder());

        //create floating point buffer from the Bytebuffer
        vertexBuffer = bb.asFloatBuffer();
        //add coordinates to float buffer
        vertexBuffer.put(tCoords);
        //set buffer to read first coordinate
        vertexBuffer.position(0);

        xCoord = 0;
        yCoord = 0;
    }
    public Triangle(float length, float angRads, float prevX, float prevY){


        newX = (float)(Math.cos(angRads)*length);
        newY = (float)(Math.sin(angRads)*length);


        tCoords[0] = prevX;     // top left x
        tCoords[1] = prevY;     // top left y
        tCoords[2] = 0;         // top left z coord
        tCoords[3] = 0;         // origin x
        tCoords[4] = 0;         // origin y
        tCoords[5] = 0;         // origin z
        tCoords[6] = newX;    // top right x
        tCoords[7] = newY;    // top right Y
        tCoords[8] = 0;


        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        //create empty OpenGL ES Program
        mProgram=GLES20.glCreateProgram();
        //add vertexShader to program
        GLES20.glAttachShader(mProgram,vertexShader);
        //add fragment shader to program
        GLES20.glAttachShader(mProgram,fragmentShader);
        //creates OpenGL ES program executable (SHIT WILL ALL WORK TOGETHER NOW)
        GLES20.glLinkProgram(mProgram);

        //initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(tCoords.length*4);
        //^^ number of coords * 4 bytes per float
        //use device hardware native byte order (big/little endian)
        bb.order(ByteOrder.nativeOrder());

        //create floating point buffer from the Bytebuffer
        vertexBuffer = bb.asFloatBuffer();
        //add coordinates to float buffer
        vertexBuffer.put(tCoords);
        //set buffer to read first coordinate
        vertexBuffer.position(0);

        xCoord = 0;
        yCoord = 0;


    }
    public void draw(float[] mvpMatrix) { //pass in calculated transformation matrix


        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        //get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");

        //pass the projection and view transformation to shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mvpMatrix,0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void setX(float x){ xCoord = x;};
    public void setY(float y){ yCoord = y;};
    public float getX(){return xCoord;}
    public float getY(){return yCoord;}
    public float getNewX(){return newX;}
    public float getNewY(){return newY;}
}
