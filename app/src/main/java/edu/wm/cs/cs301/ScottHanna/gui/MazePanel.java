package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Path;
import android.graphics.Color;

import androidx.annotation.Nullable;

public class MazePanel extends View implements P7PanelF22{

    Paint paint;
    Bitmap bmap;
    Canvas canvas;


    /**
     * Constructor for maze panel
     * @param context
     */
    public MazePanel(Context context) {
        super(context);
        init();






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
     * Constructor for maze panel
     * @param context
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();


    }
    /**
     * Constructor for maze panel
     * @param context
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }
    /**
     * Constructor for maze panel
     * @param context
     */
    public MazePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();




    }
    /**
     * Commits all accumulated drawings to the UI.
     * Substitute for MazePanel.update method.
     */
    @Override
    public void commit() {
        invalidate();

    }

    /**
     * tells if grpahics are needed by checking if the canvas object is null
     * @return true if canvas not null, false otherwise
     */
    @Override
    public boolean isOperational() {
        if(canvas!=null){
            return true;
        }
        return false;
    }
    /**
     * Sets color of paint object
     */
    @Override
    public void setColor(int argb) {

        paint.setColor(argb);

    }
    /**
     * gets color of paint object
     */
    @Override
    public int getColor() {

        return paint.getColor();
    }

    /**
     * Draws two solid rectangles to provide a background.
     * Note that this also erases any previous drawings.
     * The color setting adjusts to the distance to the exit to
     * provide an additional clue for the user.
     * Colors transition from black to gold and from grey to green.
     * Substitute for FirstPersonView.drawBackground method.
     * @param percentToExit gives the distance to exit
     */

    @Override
    public void addBackground(float percentToExit) {

        paint.setColor(blend(Color.valueOf(Color.BLACK),ColorTheme.getColor(ColorTheme.MazeColors.TITLE_LARGE),percentToExit));
        canvas.drawRect(0,0,900,450,paint);
        paint.setColor(blend(Color.valueOf(Color.GRAY),ColorTheme.getColor(ColorTheme.MazeColors.TITLE_SMALL),percentToExit));
        canvas.drawRect(0,450,900,900,paint);

    }

    private int blend(Color fstColor, Color sndColor, double weightFstColor) {
        if (weightFstColor < 0.1)
            return sndColor.toArgb();
        if (weightFstColor > 0.95)
            return fstColor.toArgb();
        double r = weightFstColor * fstColor.red() + (1-weightFstColor) * sndColor.red();
        double g = weightFstColor * fstColor.green()+ (1-weightFstColor) * sndColor.green();
        double b = weightFstColor * fstColor.blue() + (1-weightFstColor) * sndColor.blue();


        int selectedcolor=Color.rgb((float)r,(float)g,(float)b);
        return selectedcolor;




    }
    /**
     * Adds a filled rectangle.
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis.
     * Substitute for Graphics.fillRect() method
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the rectangle
     * @param height is the height of the rectangle
     */
    @Override
    public void addFilledRectangle(int x, int y, int width, int height) {
        //right corner is left corner + width, same idea for bottom
        if(isOperational()==true) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(((float) x), ((float) y), ((float) x + (float) width), ((float) y + (float) height), paint);
        }
    }

    /**
     * Adds a filled polygon.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.fillPolygon() method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        paint.setStyle(Paint.Style.FILL);
        Path wallpath = new Path();
        wallpath.reset();
        wallpath.moveTo((float)xPoints[0],(float)yPoints[0]);
        for(int i=0;i<=nPoints-1;i++){
            wallpath.lineTo(xPoints[i],yPoints[i]);
        }
        wallpath.lineTo((float)xPoints[0],(float)yPoints[0]);
        canvas.drawPath(wallpath, paint);


    }
    /**
     * Adds a polygon.
     * The polygon is not filled.
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * Substitute for Graphics.drawPolygon method
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
    @Override
    public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        if(isOperational()==true) {
            if (xPoints.length == yPoints.length) {
                paint.setStyle(Paint.Style.STROKE);
                for (int i = 0; i < nPoints-1; i++) {
                    canvas.drawLine(xPoints[i],yPoints[i],xPoints[i+1],yPoints[i+1],paint);

                }
                canvas.drawLine(xPoints[nPoints-1],yPoints[nPoints-1],xPoints[0],yPoints[0],paint);
            }
        }
    }
    /**
     * Adds a line.
     * A line is described by {@code (x,y)} coordinates for its
     * starting point and its end point.
     * Substitute for Graphics.drawLine method
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX is the x-coordinate of the end point
     * @param endY is the y-coordinate of the end point
     */
    @Override
    public void addLine(int startX, int startY, int endX, int endY) {
        if(isOperational()==true){
            canvas.drawLine((float) startX,(float) startY,(float) endX,(float) endY,paint );
        }

    }

    /**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle.
     * Substitute for Graphics.fillOval method
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the oval
     * @param height is the height of the oval
     */

    @Override
    public void addFilledOval(int x, int y, int width, int height) {
        if(isOperational()==true){
            paint.setStyle(Paint.Style.FILL);
            canvas.drawOval((float)x, (float)y,(float)x+(float)width, (float)y+(float)height,paint );
        }

    }
    /**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees,
     * using the current color. Angles are interpreted such that 0 degrees
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of
     * the bounding rectangle such that 45 degrees always falls on the
     * line from the center of the ellipse to the upper right corner of
     * the bounding rectangle. As a result, if the bounding rectangle is
     * noticeably longer in one axis than the other, the angles to the start
     * and end of the arc segment will be skewed farther along the longer
     * axis of the bounds.
     * Substitute for Graphics.drawArc method
     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn.
     * @param height the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
    @Override
    public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        if(isOperational()==true){
            paint.setStyle(Paint.Style.FILL);
            canvas.drawArc((float)x, (float)y,(float)x+(float)width,(float)y+(float)height,(float)startAngle,(float)arcAngle,true,paint);
        }

    }
    /**
     * Adds a string at the given position.
     * Substitute for CompassRose.drawMarker method
     * @param x the x coordinate
     * @param y the y coordinate
     * @param str the string
     */
    @Override
    public void addMarker(float x, float y, String str) {
        if(isOperational()==true) {
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(Typeface.SERIF);

            canvas.drawText(str, (float) x, (float) y, paint);
        }
    }

    /**
     * Sets the value of a single preference for the rendering algorithms.
     * It internally maps given parameter values into corresponding java.awt.RenderingHints
     * and assigns that to the internal graphics object.
     * Hint categories include controls for rendering quality
     * and overall time/quality trade-off in the rendering process.
     *
     * Refer to the awt RenderingHints class for definitions of some common keys and values.
     *
     * Note for Android: start with an empty default implementation.
     * Postpone any implementation efforts till the Android default rendering
     * results in unsatisfactory image quality.
     *
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified hint category.
     */
    @Override
    public void setRenderingHint(P7RenderingHints hintKey, P7RenderingHints hintValue) {

    }


}
