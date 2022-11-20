package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class WinningActivity extends AppCompatActivity {

    private Button backbutton;
    public static int pathlength=0;
    public static int energyconsumption=0;
    TextView view;
    TextView view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.winningscreen);
        if(pathlength!=0){
            view=(TextView)findViewById(R.id.Pathlength);
            view2=(TextView)findViewById(R.id.EnergyConsumption);
            view.setText("Path Length:"+String.valueOf(pathlength));
            view2.setText("Energy Consumption:"+String.valueOf(energyconsumption));
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }


        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();

            }
        });
    }

    /**
     * Changes the activity from losing screen to title screen
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}
