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
    public static int pathlength=0;
    public static int energyconsumption=0;
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
        //If the path length is 0, then don't show the path length text because manual
        //must have been played
        if(pathlength!=0){
            view=(TextView)findViewById(R.id.Pathlength);
            view2=(TextView)findViewById(R.id.EnergyConsumption);
            view.setText("Path Length:"+String.valueOf(pathlength));
            view2.setText("Energy Consumption:"+String.valueOf(energyconsumption));
            view.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
        }

        //Initializes back button
        backbutton=findViewById(R.id.backbutton);
        //listens for click on back button
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
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
        startActivity(intent);
    }
}
