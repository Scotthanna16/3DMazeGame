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

public class MazePanel extends View {

    Paint paint;
    Bitmap bmap;
    Canvas canvas;


    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel(Context context) {
        super(context);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);
        paint.setColor(Color.RED);
        canvas.drawOval(225,225,675,675,paint);




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
    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);
        paint.setColor(Color.RED);
        canvas.drawOval(225,225,675,675,paint);


    }
    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);
        paint.setColor(Color.RED);
        canvas.drawOval(225,225,675,675,paint);

    }
    /**
     * Constructor for maze panel, draws shapes
     * @param context
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        canvas.drawRect(0,450,900,900,paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,900,450,paint);
        paint.setColor(Color.RED);
        canvas.drawOval(225,225,675,675,paint);


    }

}
