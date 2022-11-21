package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MazePanel2 extends View{

    Paint paint;
    Bitmap bmap;
    Canvas canvas;


    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel2(Context context) {
        super(context);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);

        //Drawing Green polygon
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(225,207,225,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(-100,200,225,300,paint);

        canvas.drawLine(225,600,-100,700,paint);
        canvas.drawLine(100,300,100,600,paint);

        //Drawing Yellow Polygon
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(675,207,675,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(1000,200,675,300,paint);

        canvas.drawLine(675,600,1000,700,paint);
        canvas.drawLine(800,300,800,600,paint);






    }

    /**
     * onDraw used to actually draw thing
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(bmap,0,0,paint);
    }


    /**
     * Initializes objects
     */
    public void init(){
        bmap= Bitmap.createBitmap(900, 900, Bitmap.Config.RGB_565);
        canvas=new Canvas(bmap);
        paint=new Paint();
        paint.setColor(Color.BLACK);
    }
    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);

        //Drawing Green Polygon
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(225,207,225,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(-100,200,225,300,paint);

        canvas.drawLine(225,600,-100,700,paint);
        canvas.drawLine(100,300,100,600,paint);

        //Drawing yellow Polygon
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(675,207,675,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(1000,200,675,300,paint);

        canvas.drawLine(675,600,1000,700,paint);
        canvas.drawLine(800,300,800,600,paint);



    }
    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);

        //Drawing Green Polygon
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(225,207,225,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(-100,200,225,300,paint);

        canvas.drawLine(225,600,-100,700,paint);
        canvas.drawLine(100,300,100,600,paint);

        //Drawing yellow Polygon
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(675,207,675,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(1000,200,675,300,paint);

        canvas.drawLine(675,600,1000,700,paint);
        canvas.drawLine(800,300,800,600,paint);


    }
    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);

        //Drawing Green Polygon
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(225,207,225,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(-100,200,225,300,paint);

        canvas.drawLine(225,600,-100,700,paint);
        canvas.drawLine(100,300,100,600,paint);

        //Drawing Yellow Polygon
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(75);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(675,207,675,693,paint);
        paint.setStrokeWidth(200);
        canvas.drawLine(1000,200,675,300,paint);

        canvas.drawLine(675,600,1000,700,paint);
        canvas.drawLine(800,300,800,600,paint);



    }
}
