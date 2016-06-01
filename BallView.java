package com.example.andre.gyrogolf;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.*;
import android.content.Context;
import java.io.*;
import android.content.res.Resources;
/**
 * Created by andre on 5/25/16.
 */
public class BallView extends View{

    private int xMin = 0;          // This view's bounds
    private int xMax;
    private int yMin = 0;
    private int yMax;
    //boolean canDraw = false;
   // private Canvas canvas;

    //Bitmap mBitmap = BitmapFactory.decodeFile("/Users/andre/AndroidStudioProjects/Gyrogolf/app/src/main/res/drawable/green.jpg");
    //File f = new File("/data/data/com.example.andre.gyrogolf/res/drawable/green.jpg");
  //  Bitmap mBitmap;// = BitmapFactory.decodeResource(this.getResources(),R.drawable.green);
    private float ballRadius = 80; // Ball's radius
    private float ballX = ballRadius + 20;  // Ball's center (x,y)
    private float ballY = ballRadius + 40;
    private float ballSpeedX = 5;  // Ball's speed (x,y)
    private float ballSpeedY = 3;
    public float previousX;
    public float previousY;
    private RectF ballBounds;      // Needed for Canvas.drawOval
    private Paint paint;
     private Paint paintb;
    // The paint used for drawing
   // Resources res = getResources();
  //  SurfaceHolder s;


    // Constructor
    public BallView(Context context) {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();
      //  s = getHolder();
        // mBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.greenb);
        this.setFocusableInTouchMode(true);

    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //File f = new File(this.getContext().getFilesDir(),"green.jpg");
        //System.out.println(f.getAbsolutePath());
        // draw background
       // System.out.println(mBitmap);
        //paintb.setColor(Color.GREEN);
        canvas.drawColor(Color.GREEN);

        // Draw the ball

        ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius, ballY+ballRadius);
        paint.setColor(Color.WHITE);

        canvas.drawOval(ballBounds, paint);



        // Update the position of the ball, including collision detection and reaction.
        update();

        // Delay
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) { }

        invalidate();  // Force a re-draw
    }

    // Detect collision and update the position of the ball.
    private void update() {
        // Get new (x,y) position
        ballX += ballSpeedX;
        ballY += ballSpeedY;
        // Detect collision and react
        if (ballX + ballRadius > xMax) {
            ballSpeedX = -ballSpeedX;
            ballX = xMax-ballRadius;
        } else if (ballX - ballRadius < xMin) {
            ballSpeedX = -ballSpeedX;
            ballX = xMin+ballRadius;
        }
        if (ballY + ballRadius > yMax) {
            ballSpeedY = -ballSpeedY;
            ballY = yMax - ballRadius;
        } else if (ballY - ballRadius < yMin) {
            ballSpeedY = -ballSpeedY;
            ballY = yMin + ballRadius;
        }
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        xMax = w-1;
        yMax = h-1;
    }


    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float scalingFactor = 10.0f / ((xMax > yMax) ? yMax : xMax);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;
                ballSpeedX += deltaX * scalingFactor;
                ballSpeedY += deltaY * scalingFactor;
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;
        return true;  // Event handled
    }

  /*  @Override
    public void run() {
        while(canDraw){
            if(!s.getSurface().isValid()){
                continue;
            }
            canvas = s.lockCanvas();
            canvas.drawBitmap(mBitmap,0,0,null);
            s.unlockCanvasAndPost(canvas);
        }
    }*/
}








