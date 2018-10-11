package com.corantinhoulbert.cheesechaser;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;


public class Game extends View implements View.OnTouchListener{



    private boolean mBooleanIsPressed = false;

    int dx = 0;
    int dy = 0;
    int predx = 0;
    int predy = 0;

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }



    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            Log.d(" HANDLER" , "log LONG TOUCH");
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.chat);


        canvas.drawBitmap(bitmap, dx, dy, paint);



    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(" TOUCH" , "DOWN");
            // Execute your Runnable after 1000 milliseconds = 1 second.
            handler.postDelayed(runnable, 1000);
            mBooleanIsPressed = true;

            predx = x;
            predy = y;

        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(mBooleanIsPressed) {
                mBooleanIsPressed = false;
                handler.removeCallbacks(runnable);

            }
            Log.d(" TOUCH" , "UP");


        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {

            //anything happening with event here is the X Y of the raw screen event, relative to the view.
            Log.d(" TOUCH" , "MOVE");
            this.dx =  predx + (x - predx);
            this.dy =   predy + (y - predy) ;

        }
        this.invalidate();
        return true;
    }
}
