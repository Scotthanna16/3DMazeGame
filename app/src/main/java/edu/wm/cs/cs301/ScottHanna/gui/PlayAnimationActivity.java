package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
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

    private ProgressBar pbar;
    private TextView tv;
    private int progressstatus;
    private Handler handler=new Handler();
    @Override
    /**
     * Responsible for button clicks, progress bar, and seekbar, sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playanimationscreen);

        //Initializes Back Button
        backbutton=findViewById(R.id.backbutton);
        //Listens for click on button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });
        //Initializes Zoom in Button
        increase=findViewById(R.id.Increase);
        //Listens for click on button
        increase.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");

            }
        });
        //Initializes Zoom out Button
        decrease=findViewById(R.id.Decrease);
        //Listens for click on button
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");

            }
        });
        //Initializes Start Button
        Start=findViewById(R.id.Start);
        //Listens for click on button
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Robot Started",Toast.LENGTH_LONG).show();
                Log.v("Robot_Start","Robot resumes playing");

            }
        });
        //Initializes Pause Button
        Pause=findViewById(R.id.Pause);
        //Listens for click on button
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Robot Paused",Toast.LENGTH_LONG).show();
                Log.v("Robot_Pause","Robot stops playing");
            }
        });

        //Initializes Show Maze Button
        ShowMaze=findViewById(R.id.ShowMap);
        //Listens for click on button
        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");

            }
        });
        //Initializes Show Solution Button
        ShowSolution=findViewById(R.id.Solution);
        //Listens for click on button
        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
            }
        });
        //Initializes Show Walls Button
        ShowWalls=findViewById(R.id.ShowWall);
        //Listens for click on button
        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
            }
        });
        //Initializes Go2Losing Button
        Losingbutton=findViewById(R.id.Losing);
        //Listens for click on button
        Losingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Losing",Toast.LENGTH_LONG).show();
                Log.v("Losing","Changed to losing");
                changeActivitytolosing();

            }
        });

        //Initializes Go2Winning Button
        Winningbutton=findViewById(R.id.Winning);
        //Listens for click on button
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayAnimationActivity.this,"Winning",Toast.LENGTH_LONG).show();
                Log.v("Winning","Changed to Winning");
                changeActivitytowinning();

            }
        });
        //Initializes Animation Speed Seekbar
        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        //Initializes Speed textview
        view=(TextView)findViewById(R.id.Speed);

        //listens for change in animation speed seek bar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //LogV message and Toast when seekbar value changed
                Toast.makeText(PlayAnimationActivity.this,"Speed Change",Toast.LENGTH_LONG).show();
                Log.v("Animation_Speed_Changed","Changed to"+String.valueOf(i));
                //changes text about seekbar
                view.setText("Speed"+String.valueOf(i));
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

            public void run() {
                //max energy consumption 3500
                while(progressstatus<3500){
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //When out of energy, show out of energy message
                        tv.setVisibility(View.VISIBLE);
                        android.os.SystemClock.sleep(2000);
                        changeActivitytolosing();

                    }
                });
            }
        }).start();

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
        intent.putExtra("Pathlength",String.valueOf(50));
        intent.putExtra("EnergyConsumption",String.valueOf(50));
        startActivity(intent);
    }
    /**
     * changes activity from play animation to losing
     */
    private void changeActivitytolosing(){
        Intent intent=new Intent(this, LosingActivity.class);
        startActivity(intent);
    }
}
