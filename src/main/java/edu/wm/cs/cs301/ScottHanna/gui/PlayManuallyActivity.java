package edu.wm.cs.cs301.ScottHanna.gui;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;
import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;
import edu.wm.cs.cs301.ScottHanna.generation.Floorplan;
import edu.wm.cs.cs301.ScottHanna.generation.Maze;

public class PlayManuallyActivity extends AppCompatActivity {
    private Button backbutton;
    //private Button Winningbutton;
    private Button increase;
    private Button decrease;
    private Button ShowMaze;
    private Button ShowSolution;
    private Button ShowWalls;
    private Button Forward;
    private Button Left;
    private Button Jump;
    private Button Right;
    private int px=0;
    private int py=0;
    private int pathlength=0;
    public static Maze maze;

    private MazePanel panel;
    CardinalDirection cd;
    private boolean showMaze=false;           // toggle switch to show overall maze on screen
    private boolean showSolution=false;       // toggle switch to show solution in overall maze on screen
    private boolean mapMode=false;
    private Floorplan seenCells;
    private Map mapView;





    /**
     * Responsible for button clicks, and sets screen views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.playmanualscreen);

        panel=findViewById(R.id.mazePanel3);


        // adjust internal state of maze model
        // init data structure for visible walls
        seenCells = new Floorplan(maze.getWidth()+1,maze.getHeight()+1) ;
        // set the current position and direction consistently with the viewing direction
        setPositionDirectionViewingDirection();

        if (panel != null) {
            startDrawer();

        }



        //Initializes Back Button
        backbutton=findViewById(R.id.backbutton);
        //Listens for click on Back Button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on back Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });

        /*
        //Initializes Shortcut Button
        Winningbutton=findViewById(R.id.Winning);
        //Listens for click on Shortcut Button
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Listens for click on Go2Winning Button

                Toast.makeText(PlayManuallyActivity.this,"Winning",Toast.LENGTH_LONG).show();
                Log.v("Winning","Changed to Winning");
                changeActivitytowinning();

            }
        });
        */
        //Initializes Zoom in button
        increase=findViewById(R.id.Increase);
        //Listens for click on Zoom inButton
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Listens for click on zoom in Button
                 */
                Toast.makeText(PlayManuallyActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");
                handleUserInput(Constants.UserInput.ZOOMIN,0);

            }
        });
        //Initializes Zoom out button
        decrease=findViewById(R.id.Decrease);
        //Listens for click on Zoom out Button
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on zoom out Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");
                handleUserInput(Constants.UserInput.ZOOMOUT,0);

            }
        });

        //Initializes Show Maze button
        ShowMaze=findViewById(R.id.ShowMap);

        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on show maze Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");
                handleUserInput(Constants.UserInput.TOGGLEFULLMAP,0);

            }
        });
        //Initializes Show solution button
        ShowSolution=findViewById(R.id.Solution);

        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             * Listens for click on show solutions Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
                handleUserInput(Constants.UserInput.TOGGLESOLUTION,0);
            }
        });
        //Initializes Show walls button
        ShowWalls=findViewById(R.id.ShowWall);

        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on show walls Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
                handleUserInput(Constants.UserInput.TOGGLELOCALMAP,0);
            }
        });
        //Initializes Forward button
        Forward=findViewById(R.id.Forward);

        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on forward Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Forward",Toast.LENGTH_LONG).show();
                Log.v("Forward","Robot moved forward");
                pathlength++;
                handleUserInput(Constants.UserInput.UP,0);
            }
        });
        //Initializes Right button
        Right=findViewById(R.id.Right);
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on Right Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Right",Toast.LENGTH_LONG).show();
                Log.v("Right","Robot moved Right");
                handleUserInput(Constants.UserInput.RIGHT,0);
            }
        });

        //Initializes Left button
        Left=findViewById(R.id.Left);

        Left.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             * Listens for click on left Button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Left",Toast.LENGTH_LONG).show();
                Log.v("Left","Robot moved Left");
                handleUserInput(Constants.UserInput.LEFT,0);
            }
        });

        //Initializes Jump button
        Jump=findViewById(R.id.Jump);
        //Listens for click on Jump Button
        Jump.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * listens for click on Jump button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Jump",Toast.LENGTH_LONG).show();
                Log.v("Jump","Robot Jumped");
                handleUserInput(Constants.UserInput.JUMP,0);
                pathlength++;
            }
        });
    }

    /**
     * changes activity from play manually to title
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        Log.v("Change activity","Player has gone back to title");
        startActivity(intent);
    }

    /**
     * changes activity from play manually to winning
     */
    private void changeActivitytowinning(){

        Intent intent=new Intent(this, WinningActivity.class);
        Log.v("Winning","Player has exited the maze");
        intent.putExtra("Pathlength",String.valueOf(pathlength));
        intent.putExtra("EnergyConsumption",String.valueOf(0));
        startActivity(intent);

    }


    /**
     * The method provides an appropriate response to user keyboard input.
     * The control calls this method to communicate input and delegate its handling.
     * Method requires {@link //start(Control, MazePanel) start} to be
     * called before.
     * @param userInput provides the feature the user selected
     * @param value is not used in this state, exists only for consistency across State classes
     * @return false if not started yet otherwise true
     */
    public boolean handleUserInput(Constants.UserInput userInput, int value) {
        // user input too early, not sure how this could happen
        /*
        if (!started) {
            LOGGER.info("Premature keyboard input:" + userInput + "with value " + value + ", ignored for mitigation");
            return false;
        }
        */


        // react to input for directions and interrupt signal (ESCAPE key)
        // react to input for displaying a map of the current path or of the overall maze (on/off toggle switch)
        // react to input to display solution (on/off toggle switch)
        // react to input to increase/reduce map scale
        switch (userInput) {
            case START: // misplaced, do nothing
                break;
            case UP: // move forward
                //Log.v("Move Forward","Move 1 step forward");
                walk(1);
                // check termination, did we leave the maze?
                if (isOutside(px,py)) {

                    changeActivitytowinning();
                }
                break;
            case LEFT: // turn left
                //Log.v("Left","Turn left");
                rotate(1);
                break;
            case RIGHT: // turn right
                //Log.v("Right","Turn right");
                rotate(-1);
                break;
            case JUMP: // make a step forward even through a wall
                //Log.v("Jump","Jump 1 step forward");
                // go to position if within maze
                int[] tmpDxDy = cd.getDxDyDirection();
                if (maze.isValidPosition(px + tmpDxDy[0], py + tmpDxDy[1])) {
                    setCurrentPosition(px + tmpDxDy[0], py + tmpDxDy[1]) ;
                    draw(cd.angle(), 0) ;
                }
                break;
            case TOGGLELOCALMAP: // show local information: current position and visible walls
                // precondition for showMaze and showSolution to be effective
                // acts as a toggle switch
                mapMode = !mapMode;
                draw(cd.angle(), 0) ;
                break;
            case TOGGLEFULLMAP: // show the whole maze
                // acts as a toggle switch
                showMaze = !showMaze;
                draw(cd.angle(), 0) ;
                break;
            case TOGGLESOLUTION: // show the solution as a yellow line towards the exit
                // acts as a toggle switch
                showSolution = !showSolution;
                draw(cd.angle(), 0) ;
                break;
            case ZOOMIN: // zoom into map
                mapView.incrementMapScale();
                draw(cd.angle(), 0) ;
                break ;
            case ZOOMOUT: // zoom out of map
                mapView.decrementMapScale();
                draw(cd.angle(), 0) ;
                break ;
        } // end of internal switch statement for playing state
        return true;
    }

    /**
     * Draws the current content on panel to show it on screen.
     * @param angle the current viewing angle, east == 0 degrees, south == 90, west == 180, north == 270
     * @param walkStep a counter for intermediate steps within a single step forward or backward
     */
    protected void draw(int angle, int walkStep) {

        if (panel == null) {
            printWarning();
            return;
        }
        // draw the first person view and the map view if wanted
        firstPersonView.draw(panel, px, py, walkStep, angle,
                maze.getPercentageForDistanceToExit(px, py)) ;
        if (isInMapMode()) {
            mapView.draw(panel, px, py, angle, walkStep,
                    isInShowMazeMode(),isInShowSolutionMode()) ;
        }
        // update the screen with the buffer graphics
        panel.commit() ;
    }

    /**
     * Initializes the drawer for the first person view
     * and the map view and then draws the initial screen
     * for this state.
     */
    private FirstPersonView firstPersonView;
    private CompassRose cr;
    protected void startDrawer() {
        Log.v("Drawing started","Drawing of the screen has started");
        cr = new CompassRose();
        cr.setPositionAndSize(Constants.VIEW_WIDTH/2,
                (int)(0.1*Constants.VIEW_HEIGHT),35);

        firstPersonView = new FirstPersonView(Constants.VIEW_WIDTH,
                Constants.VIEW_HEIGHT, Constants.MAP_UNIT,
                Constants.STEP_SIZE, seenCells, maze.getRootnode()) ;

        mapView = new Map(seenCells, 30, maze) ;
        // draw the initial screen for this state
        draw(cd.angle(), 0);

    }


    /**
     * Prints the warning about a missing panel only once
     */
    boolean printedWarning = false;
    protected void printWarning() {
        if (printedWarning)
            return;
        Log.e("Warning","No panel for drawing during executing, dry-run game without graphics!");
        printedWarning = true;
    }
    ////////////////////////////// set methods ///////////////////////////////////////////////////////////////
    ////////////////////////////// Actions that can be performed on the maze model ///////////////////////////
    protected void setCurrentPosition(int x, int y) {
        px = x;
        py = y;
    }
    protected int[] getCurrentPosition() {
        int[] result = new int[2];
        result[0] = px;
        result[1] = py;
        return result;
    }
    protected CardinalDirection getCurrentDirection() {
        return cd;
    }
    boolean isInMapMode() {
        return mapMode ;
    }
    boolean isInShowMazeMode() {
        return showMaze ;
    }
    boolean isInShowSolutionMode() {
        return showSolution ;
    }
    public Maze getMaze() {
        return maze ;
    }
    //////////////////////// Methods for move and rotate operations ///////////////

    /**
     * Determines if one can walk in the given direction
     * @param dir is the direction of interest, either 1 or -1
     * @return true if there is no wall in this direction, false otherwise
     */
    protected boolean wayIsClear(int dir) {
        switch (dir) {
            case 1: // forward
                return !maze.hasWall(px, py, cd);
            case -1: // backward
                return !maze.hasWall(px, py, cd.oppositeDirection());
            default:
                throw new RuntimeException("Unexpected direction value: " + dir);
        }
    }
    /**
     * Draws and waits. Used to obtain a smooth appearance for rotate and move operations
     */
    private void slowedDownRedraw(int angle, int walkStep) {
        Log.v("used to slow draw","Drawing intermediate figures: angle " + angle + ", walkStep " + walkStep);
        draw(angle, walkStep) ;
        try {
            Thread.sleep(25);
        } catch (Exception e) {
            // may happen if thread is interrupted
            // no reason to do anything about it, ignore exception
        }
    }

    /**
     * Performs a rotation with 4 intermediate views,
     * updates the screen and the internal direction
     * @param dir for current direction, values are either 1 or -1
     */
    private synchronized void rotate(int dir) {
        Log.v("Rotate","Player is rotating");
        final int originalAngle = cd.angle();//angle;
        final int steps = 4;
        int angle = originalAngle; // just in case for loop is skipped
        for (int i = 0; i != steps; i++) {
            // add 1/4 of 90 degrees per step
            // if dir is -1 then subtract instead of addition
            angle = originalAngle + dir*(90*(i+1))/steps;
            angle = (angle+1800) % 360;
            // draw method is called and uses angle field for direction
            // information.
            slowedDownRedraw(angle, 0);
        }
        // update maze direction only after intermediate steps are done
        // because choice of direction values are more limited.
        cd = CardinalDirection.getDirection(angle);
        //logPosition(); // debugging
        drawHintIfNecessary();
    }

    /**
     * Moves in the given direction with 4 intermediate steps,
     * updates the screen and the internal position
     * @param dir, only possible values are 1 (forward) and -1 (backward)
     */
    private synchronized void walk(int dir) {
        Log.v("Rotate","Player is walking");
        // check if there is a wall in the way
        if (!wayIsClear(dir))
            return;
        int walkStep = 0;
        // walkStep is a parameter of FirstPersonView.draw()
        // it is used there for scaling steps
        // so walkStep is implicitly used in slowedDownRedraw
        // which triggers the draw operation in
        // FirstPersonView and Map
        for (int step = 0; step != 4; step++) {
            walkStep += dir;
            slowedDownRedraw(cd.angle(), walkStep);
        }
        // update position to neighbor
        int[] tmpDxDy = cd.getDxDyDirection();
        setCurrentPosition(px + dir*tmpDxDy[0], py + dir*tmpDxDy[1]) ;
       // logPosition(); // debugging
        drawHintIfNecessary();

    }

    /**
     * Checks if the given position is outside the maze
     * @param x coordinate of position
     * @param y coordinate of position
     * @return true if position is outside, false otherwise
     */
    private boolean isOutside(int x, int y) {
        return !maze.isValidPosition(x, y) ;
    }
    /**
     * Draw a visual cue to help the user unless the
     * map is on display anyway.
     * This is the map if current position faces a dead end
     * otherwise it is a compass rose.
     */
    private void drawHintIfNecessary() {
        Log.v("Drawing hint","Drawing hint");
        if (isInMapMode())
            return; // no need for help
        // in testing environments, there is sometimes no panel to draw on
        // or the panel is unable to deliver a graphics object
        // check this and quietly move on if drawing is impossible
        //|| panel.getBufferGraphics() == null
        if ((panel == null )) {
            printWarning();
            return;
        }
        // if current position faces a dead end, show map with solution
        // for guidance
        if (maze.isFacingDeadEnd(px, py, cd)) {
            //System.out.println("Facing deadend, help by showing solution");
            mapView.draw(panel, px, py, cd.angle(), 0, true, true) ;
        }
        else {
            // draw compass rose
            cr.setCurrentDirection(cd);
            cr.paintComponent(panel);
        }
        panel.commit();
    }
    /**
     * Internal method to set the current position, the direction
     * and the viewing direction to values consistent with the
     * given maze.
     */
    private void setPositionDirectionViewingDirection() {
        int[] start = maze.getStartingPosition() ;
        setCurrentPosition(start[0],start[1]) ;
        cd = CardinalDirection.East;
    }


}

