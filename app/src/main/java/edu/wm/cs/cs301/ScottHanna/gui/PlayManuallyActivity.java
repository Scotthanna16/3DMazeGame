package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class PlayManuallyActivity extends AppCompatActivity {
    private Button backbutton;
    private Button Winningbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmanualscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();

            }
        });



        Winningbutton=findViewById(R.id.Winning);
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivitytowinning();

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
