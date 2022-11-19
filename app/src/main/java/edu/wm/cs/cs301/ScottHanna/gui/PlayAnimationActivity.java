package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class PlayAnimationActivity extends AppCompatActivity {
    private Button backbutton;
    private Button Winningbutton;
    private Button Losingbutton;
    SeekBar seekbar;
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playanimationscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();

            }
        });

        Losingbutton=findViewById(R.id.Losing);
        Losingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivitytolosing();

            }
        });

        Winningbutton=findViewById(R.id.Winning);
        Winningbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivitytowinning();

            }
        });

        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        view=(TextView)findViewById(R.id.Speed);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                view.setText("Speed"+String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
    private void changeActivitytolosing(){
        Intent intent=new Intent(this, LosingActivity.class);
        startActivity(intent);
    }
}
