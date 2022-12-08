package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;
import edu.wm.cs.cs301.ScottHanna.generation.CardinalDirection;
import edu.wm.cs.cs301.ScottHanna.generation.Floorplan;
import edu.wm.cs.cs301.ScottHanna.generation.Maze;
import edu.wm.cs.cs301.ScottHanna.generation.Wall;

public class PlayAnimationActivity extends AppCompatActivity {
    private Button backbutton;
    private Button Winningbutton;
    private Button Losingbutton;
    private Button increase;
    private Button decrease;
    private Button ShowMaze;
    private Button ShowSolution;
    private Button ShowWalls;
    private Button Start;
    private Button Pause;
    SeekBar seekbar;
    TextView view;
    TextView Forward;
    TextView Left;
    TextView Right;
    TextView Back;
    public static Maze maze;
    private int px=0;
    private int py=0;
    private int pathlength=0;
    private MazePanel panel;
    CardinalDirection cd;
    private boolean showMaze=true;           // toggle switch to show overall maze on screen
    private boolean showSolution=true;       // toggle switch to show solution in overall maze on screen
    private boolean mapMode=true;
    private Floorplan seenCells;
    private Map mapView;
    private int animationspeed=50;
    private DistanceSensor f;
    private DistanceSensor r;
    private DistanceSensor l;
    private DistanceSensor b;



    private ProgressBar pbar;
    private TextView tv;
    private int progressstatus;
    private Handler handler=new Handler();
    private Robot robot;
    private RobotDriver driver;
    @Override
    /**
     * Responsible for button clicks, progress bar, and seekbar, sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playanimationscreen);
        // adjust internal state of maze model
        // init data structure for visible walls
        panel=findViewById(R.id.mazePanel3);
        seenCells = new Floorplan(maze.getWidth()+1,maze.getHeight()+1) ;
        // set the current position and direction consistently with the viewing direction
        setPositionDirectionViewingDirection();

        if (panel != null) {
            startDrawer();

        }
        Bundle extras=getIntent().getExtras();
        Forward=findViewById(R.id.Forward);
        Left=findViewById(R.id.Left);
        Right=findViewById(R.id.Right);
        Back=findViewById(R.id.Backward);




        String driverstr=extras.getString("Driver type");
        if(driverstr.equals("Wall Follower")){
            driver=new WallFollower();
            String robotstr= extras.getString("Robot type");
            if(robotstr.equals("Premium")){
                robot=new ReliableRobot();
                Forward.setTextColor(Color.GREEN);
                Right.setTextColor(Color.GREEN);
                Left.setTextColor(Color.GREEN);
                Back.setTextColor(Color.GREEN);
                ReliableSensor f=new ReliableSensor();
                ReliableSensor b=new ReliableSensor();
                ReliableSensor l=new ReliableSensor();
                ReliableSensor r=new ReliableSensor();
                robot.addDistanceSensor(f, Robot.Direction.FORWARD);
                robot.addDistanceSensor(l, Robot.Direction.LEFT);
                robot.addDistanceSensor(r, Robot.Direction.RIGHT);
                robot.addDistanceSensor(b, Robot.Direction.BACKWARD);
                robot.setController(this);
                driver.setRobot(robot);
                driver.setMaze(maze);
            }
            else if (robotstr.equals("Mediocore")){
                robot=new UnreliableRobot();
                f=new ReliableSensor();
                b=new ReliableSensor();
                l=new UnreliableSensor();
                r=new UnreliableSensor();
                robot.addDistanceSensor(f, Robot.Direction.FORWARD);
                robot.addDistanceSensor(l, Robot.Direction.LEFT);
                robot.addDistanceSensor(r, Robot.Direction.RIGHT);
                robot.addDistanceSensor(b, Robot.Direction.BACKWARD);
                robot.setController(this);
                driver.setRobot(robot);
                driver.setMaze(maze);
                try {
                    robot.startFailureAndRepairProcess(Robot.Direction.LEFT, 4, 2);

                }
                catch(Exception e) {

                }
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    //start left sensor, if the left sensor is reliable, catch the exception
                    robot.startFailureAndRepairProcess(Robot.Direction.RIGHT, 4, 2);

                }
                catch(Exception e) {

                }
            }
            else if (robotstr.equals("Soso")){
                robot=new UnreliableRobot();
                l=new ReliableSensor();
                r=new ReliableSensor();
                f=new UnreliableSensor();
                b=new UnreliableSensor();
                robot.addDistanceSensor(f, Robot.Direction.FORWARD);
                robot.addDistanceSensor(l, Robot.Direction.LEFT);
                robot.addDistanceSensor(r, Robot.Direction.RIGHT);
                robot.addDistanceSensor(b, Robot.Direction.BACKWARD);
                robot.setController(this);
                driver.setRobot(robot);
                driver.setMaze(maze);
                try {
                    robot.startFailureAndRepairProcess(Robot.Direction.FORWARD, 4, 2);

                }
                catch(Exception e) {

                }
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    //start left sensor, if the left sensor is reliable, catch the exception
                    robot.startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4, 2);

                }
                catch(Exception e) {

                }}
            else{
                robot=new UnreliableRobot();
                f=new UnreliableSensor();
                b=new UnreliableSensor();
                l=new UnreliableSensor();
                r=new UnreliableSensor();
                    robot.addDistanceSensor(f, Robot.Direction.FORWARD);
                    robot.addDistanceSensor(l, Robot.Direction.LEFT);
                    robot.addDistanceSensor(r, Robot.Direction.RIGHT);
                    robot.addDistanceSensor(b, Robot.Direction.BACKWARD);
                    robot.setController(this);
                    driver.setRobot(robot);
                    driver.setMaze(maze);
                try {
                    robot.startFailureAndRepairProcess(Robot.Direction.FORWARD, 4, 2);

                }
                catch(Exception e) {

                }

                try {
                    //start left sensor, if the left sensor is reliable, catch the exception
                    robot.startFailureAndRepairProcess(Robot.Direction.LEFT, 4, 2);

                }
                catch(Exception e) {

                }

                try {
                    //start backward sensor, if the backward sensor is reliable, catch the exception
                    robot.startFailureAndRepairProcess(Robot.Direction.BACKWARD, 4, 2);

                }
                catch(Exception e) {

                }

                try {
                    //start right sensor, if the right sensor is reliable, catch the exception
                    robot.startFailureAndRepairProcess(Robot.Direction.RIGHT, 4, 2);

                }
                catch(Exception e) {

                }
            }
        }
        else{
            driver=new Wizard();
            robot=new ReliableRobot();
            robot.setController(this);
            driver.setRobot(robot);
            driver.setMaze(maze);
            Forward.setTextColor(Color.GREEN);
            Right.setTextColor(Color.GREEN);
            Left.setTextColor(Color.GREEN);
            Back.setTextColor(Color.GREEN);
        }




        //Initializes Back Button
        backbutton=findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on back button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });
        //Initializes Zoom in Button
        increase=findViewById(R.id.Increase);

        increase.setOnClickListener(new View.OnClickListener() {

            /**
             * Listens for click on zoom in button
             */
            @Override

            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");

            }
        });
        //Initializes Zoom out Button
        decrease=findViewById(R.id.Decrease);

        decrease.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for click on zoom out button
             */
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");

            }
        });
        //Initializes Start Button
        Start=findViewById(R.id.Start);

        Start.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for click on Start button
             */
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Robot Started",Toast.LENGTH_LONG).show();
                Log.v("Robot_Start","Robot resumes playing");

            }
        });
        //Initializes Pause Button
        Pause=findViewById(R.id.Pause);

        Pause.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for click on Pause button
             */
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Robot Paused",Toast.LENGTH_LONG).show();
                Log.v("Robot_Pause","Robot stops playing");
            }
        });

        //Initializes Show Maze Button
        ShowMaze=findViewById(R.id.ShowMap);

        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on show maze button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");

            }
        });
        //Initializes Show Solution Button
        ShowSolution=findViewById(R.id.Solution);

        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on show solution button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
            }
        });
        //Initializes Show Walls Button
        ShowWalls=findViewById(R.id.ShowWall);

        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on show walls button
             */
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
            }
        });
        //Initializes Go2Losing Button
        Losingbutton=findViewById(R.id.Losing);

        Losingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on go2losing button
             */
            public void onClick(View view) {


            }
        });

        //Initializes Go2Winning Button
        Winningbutton=findViewById(R.id.Winning);

        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on go2winning button
             */
            public void onClick(View view) {


            }
        });
        //Initializes Animation Speed Seekbar
        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        //Initializes Speed textview
        view=(TextView)findViewById(R.id.Speed);

        //listens for change in animation speed seek bar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * listens for ange in animation speed seek bar
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //LogV message and Toast when seekbar value changed
                Toast.makeText(PlayAnimationActivity.this,"Speed Change",Toast.LENGTH_LONG).show();
                Log.v("Animation_Speed_Changed","Changed to"+String.valueOf(i));
                //changes text about seekbar
                view.setText("Speed"+String.valueOf(i));
                animationspeed=i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Initializes Progress bar used for energy
        pbar=(ProgressBar) findViewById(R.id.EnergyBar);
        //Initializes text view for out of energy
        tv=(TextView) findViewById(R.id.Outofenergy);
        //runs progress bar
        new Thread(new Runnable() {
            @Override
            /**
             * Increments energy consumption progress bar
             */
            public void run() {
                //max energy consumption 3500
                try {
                    if(driver.drive2Exit()==true){
                        changeActivitytowinning();

                    }
                    else{
                        changeActivitytolosing();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            /**
             * Increments energy consumption progress bar
             */
            public void run() {
                while(driver.getEnergyConsumption()<3500){
                    pbar.setProgress((int) driver.getEnergyConsumption());
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            /**
             * Increments energy consumption progress bar
             */
            public void run() {
                while(robot.isAtExit()==false){
                    if(f.isworking()){
                        if(Forward.getCurrentTextColor()==Color.GREEN){}
                        else{
                            Forward.setTextColor(Color.GREEN);
                        }
                    }
                    else{
                        if(Forward.getCurrentTextColor()==Color.RED){}
                        else{
                            Forward.setTextColor(Color.RED);
                        }
                    }

                    if(b.isworking()){
                        if(Back.getCurrentTextColor()==Color.GREEN){}
                        else{
                            Back.setTextColor(Color.GREEN);
                        }
                    }
                    else{
                        if(Back.getCurrentTextColor()==Color.RED){}
                        else{
                            Back.setTextColor(Color.RED);
                        }
                    }

                    if(l.isworking()){
                        if(Left.getCurrentTextColor()==Color.GREEN){}
                        else{
                            Left.setTextColor(Color.GREEN);
                        }
                    }
                    else{
                        if(Left.getCurrentTextColor()==Color.RED){}
                        else{
                            Left.setTextColor(Color.RED);
                        }
                    }

                    if(r.isworking()){
                        if(Right.getCurrentTextColor()==Color.GREEN){}
                        else{
                            Right.setTextColor(Color.GREEN);
                        }
                    }
                    else{
                        if(Right.getCurrentTextColor()==Color.RED){}
                        else{
                            Right.setTextColor(Color.RED);
                        }
                    }
                }

            }
        }).start();
        /*
        while(progressstatus>3500){
                    //increments progress bar
                    progressstatus+=1;
                    android.os.SystemClock.sleep(5);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbar.setProgress(progressstatus);

                        }
                    });

                }

               */


    }
    /**
     * changes activity from play animation to title
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
    /**
     * changes activity from play animation to winning
     */
    private void changeActivitytowinning(){
        //Hard code values for path length and energy consumption


        Intent intent=new Intent(this, WinningActivity.class);
        intent.putExtra("Pathlength",String.valueOf(robot.getOdometerReading()));
        intent.putExtra("EnergyConsumption",String.valueOf(robot.getBatteryLevel()));
        startActivity(intent);
    }
    /**
     * changes activity from play animation to losing
     */
    public void changeActivitytolosing(){
        Intent intent=new Intent(this, LosingActivity.class);
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

            Thread.sleep(50*(50/animationspeed));
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

    private void setForwardGreen(){
        Forward.setTextColor(Color.GREEN);

    }

    private void setBackwardGreen(){
        Back.setTextColor(Color.GREEN);


    }

    private void setLeftGreen(){
        Left.setTextColor(Color.GREEN);

    }

    private void setRightGreen(){
        Right.setTextColor(Color.GREEN);

    }

    private void setForwardRed(){
        Forward.setTextColor(Color.RED);

    }

    private void setBackwardRed(){
        Back.setTextColor(Color.RED);


    }

    private void setLeftRed(){
        Left.setTextColor(Color.RED);

    }

    private void setRightRed(){
        Right.setTextColor(Color.RED);

    }



}
