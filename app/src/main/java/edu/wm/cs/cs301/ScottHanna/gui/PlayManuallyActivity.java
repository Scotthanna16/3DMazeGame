package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class PlayManuallyActivity extends AppCompatActivity {
    private Button backbutton;
    private Button Winningbutton;
    private Button increase;
    private Button decrease;
    private Button ShowMaze;
    private Button ShowSolution;
    private Button ShowWalls;
    private Button Forward;
    private Button Left;
    private Button Jump;
    private Button Right;




    /**
     * Responsible for button clicks, and sets screen views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmanualscreen);

        //Initializes Back Button
        backbutton=findViewById(R.id.backbutton);
        //Listens for click on Back Button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });


        //Initializes Shortcut Button
        Winningbutton=findViewById(R.id.Winning);
        //Listens for click on Shortcut Button
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Winning",Toast.LENGTH_LONG).show();
                Log.v("Winning","Changed to Winning");
                changeActivitytowinning();

            }
        });
        //Initializes Zoom in button
        increase=findViewById(R.id.Increase);
        //Listens for click on Zoom inButton
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");

            }
        });
        //Initializes Zoom out button
        decrease=findViewById(R.id.Decrease);
        //Listens for click on Zoom out Button
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");

            }
        });

        //Initializes Show Maze button
        ShowMaze=findViewById(R.id.ShowMap);
        //Listens for click on Show Maze Button
        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");

            }
        });
        //Initializes Show solution button
        ShowSolution=findViewById(R.id.Solution);
        //Listens for click on Solution Button
        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
            }
        });
        //Initializes Show walls button
        ShowWalls=findViewById(R.id.ShowWall);
        //Listens for click on Show Wall Button
        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
            }
        });
        //Initializes Forward button
        Forward=findViewById(R.id.Forward);
        //Listens for click on Forward Button
        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Forward",Toast.LENGTH_LONG).show();
                Log.v("Forward","Robot moved forward");
            }
        });
        //Initializes Right button
        Right=findViewById(R.id.Right);
        //Listens for click on Right Button
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Right",Toast.LENGTH_LONG).show();
                Log.v("Right","Robot moved Right");
            }
        });

        //Initializes Left button
        Left=findViewById(R.id.Left);
        //Listens for click on left Button
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Left",Toast.LENGTH_LONG).show();
                Log.v("Left","Robot moved Left");
            }
        });

        //Initializes Jump button
        Jump=findViewById(R.id.Jump);
        //Listens for click on Jump Button
        Jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast and LogV message when button is clicked
                Toast.makeText(PlayManuallyActivity.this,"Jump",Toast.LENGTH_LONG).show();
                Log.v("Jump","Robot Jumped");
            }
        });
    }

    /**
     * changes activity from play manually to title
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
    /**
     * changes activity from play manually to winning
     */
    private void changeActivitytowinning(){
        Intent intent=new Intent(this, WinningActivity.class);
        startActivity(intent);
    }

}
