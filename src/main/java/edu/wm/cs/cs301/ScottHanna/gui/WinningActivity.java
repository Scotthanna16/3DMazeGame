package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class WinningActivity extends AppCompatActivity {

    private Button backbutton;

    TextView view;
    TextView view2;
    @Override
    /**
     * Responsible for button clicks, and sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.winningscreen);

        //Gets path length and energy consumption for play classes
        Bundle extras=getIntent().getExtras();
        String pathlength=extras.getString("Pathlength");
        int pl=Integer.valueOf(pathlength);
        String energyconsumption= extras.getString("EnergyConsumption");
        float ec=3500-Float.valueOf(energyconsumption);
        //If the energy consumption is 0, then don't show the path length text because manual
        //must have been played
        if(pl!=0){
            view=(TextView)findViewById(R.id.Pathlength);
            view.setText("Path Length:"+pathlength);
            view.setVisibility(View.VISIBLE);

        }
        if (ec!=3500){
            view2=(TextView)findViewById(R.id.EnergyConsumption);

            view2.setText("Energy Consumption:"+String.valueOf(ec));

            view2.setVisibility(View.VISIBLE);
        }

        //Initializes back button
        backbutton=findViewById(R.id.backbutton);
        //listens for click on back button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * listens for click on backbutton
             */
            public void onClick(View view) {
                //LogV and Toast for when back button is clicked
                Toast.makeText(WinningActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });
    }

    /**
     * Changes the activity from winning screen to title screen
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        Log.v("Change Activity","Player has exited the maze");
        startActivity(intent);
    }
}
