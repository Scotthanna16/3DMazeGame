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




    public MazePanel(Context context) {
        super(context);
        init();
        canvas.drawRect(0,0,500,500,paint);




    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Log.v("Null testing", String.valueOf(bmap == null));
        canvas.drawBitmap(bmap,0,0,paint);
    }



    public void init(){
        bmap= Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565);
        canvas=new Canvas(bmap);
        paint=new Paint();
        paint.setColor(Color.BLACK);
    }

    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        canvas.drawRect(0,0,500,500,paint);

    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        canvas.drawRect(0,0,500,500,paint);

    }

    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        canvas.drawRect(0,0,500,500,paint);

    }

}
