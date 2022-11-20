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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playanimationscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });

        increase=findViewById(R.id.Increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");

            }
        });

        decrease=findViewById(R.id.Decrease);
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");

            }
        });

        Start=findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Robot Started",Toast.LENGTH_LONG).show();
                Log.v("Robot_Start","Robot resumes playing");

            }
        });

        Pause=findViewById(R.id.Pause);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Robot Paused",Toast.LENGTH_LONG).show();
                Log.v("Robot_Pause","Robot stops playing");
            }
        });


        ShowMaze=findViewById(R.id.ShowMap);
        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");

            }
        });

        ShowSolution=findViewById(R.id.Solution);
        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
            }
        });

        ShowWalls=findViewById(R.id.ShowWall);
        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
            }
        });

        Losingbutton=findViewById(R.id.Losing);
        Losingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Losing",Toast.LENGTH_LONG).show();
                Log.v("Losing","Changed to losing");
                changeActivitytolosing();

            }
        });


        Winningbutton=findViewById(R.id.Winning);
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayAnimationActivity.this,"Winning",Toast.LENGTH_LONG).show();
                Log.v("Winning","Changed to Winning");
                changeActivitytowinning();

            }
        });

        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        view=(TextView)findViewById(R.id.Speed);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(PlayAnimationActivity.this,"Speed Change",Toast.LENGTH_LONG).show();
                Log.v("Animation_Speed_Changed","Changed to"+String.valueOf(i));
                view.setText("Speed"+String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pbar=(ProgressBar) findViewById(R.id.EnergyBar);
        tv=(TextView) findViewById(R.id.Outofenergy);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressstatus<3500){
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
                        tv.setVisibility(View.VISIBLE);
                        android.os.SystemClock.sleep(2000);
                        changeActivitytolosing();

                    }
                });
            }
        }).start();

    }
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
    private void changeActivitytowinning(){
        WinningActivity.pathlength=50;
        WinningActivity.energyconsumption=50;

        Intent intent=new Intent(this, WinningActivity.class);
        startActivity(intent);
    }
    private void changeActivitytolosing(){
        Intent intent=new Intent(this, LosingActivity.class);
        startActivity(intent);
    }
}
