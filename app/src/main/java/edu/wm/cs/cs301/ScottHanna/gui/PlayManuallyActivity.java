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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmanualscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });



        Winningbutton=findViewById(R.id.Winning);
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Winning",Toast.LENGTH_LONG).show();
                Log.v("Winning","Changed to Winning");
                changeActivitytowinning();

            }
        });

        increase=findViewById(R.id.Increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Zoomed in",Toast.LENGTH_LONG).show();
                Log.v("Increase","View Zoomed in");

            }
        });

        decrease=findViewById(R.id.Decrease);
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Zoomed out",Toast.LENGTH_LONG).show();
                Log.v("Decrease","View Zoomed out");

            }
        });


        ShowMaze=findViewById(R.id.ShowMap);
        ShowMaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Maze Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Maze","Maze is being shown to user");

            }
        });

        ShowSolution=findViewById(R.id.Solution);
        ShowSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Solution Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Solution","Solution is being shown to user");
            }
        });

        ShowWalls=findViewById(R.id.ShowWall);
        ShowWalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Walls Shown",Toast.LENGTH_LONG).show();
                Log.v("Show_Walls","Walls are being shown to user");
            }
        });

        Forward=findViewById(R.id.Forward);
        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Forward",Toast.LENGTH_LONG).show();
                Log.v("Forward","Robot moved forward");
            }
        });
        Right=findViewById(R.id.Right);
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Right",Toast.LENGTH_LONG).show();
                Log.v("Right","Robot moved Right");
            }
        });
        Left=findViewById(R.id.Left);
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Left",Toast.LENGTH_LONG).show();
                Log.v("Left","Robot moved Left");
            }
        });
        Jump=findViewById(R.id.Jump);
        Jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayManuallyActivity.this,"Jump",Toast.LENGTH_LONG).show();
                Log.v("Jump","Robot Jumped");
            }
        });
    }

    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
    private void changeActivitytowinning(){
        Intent intent=new Intent(this, WinningActivity.class);
        startActivity(intent);
    }

}
