package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class GeneratingActivity extends AppCompatActivity {
    private Button backbutton;
    String[]Driver={"Manual","Wall Follower", "Wizard"};
    String[]Reliability={"Premium","Mediocore","Soso","Shaky"};

    private ProgressBar pbar;
    private TextView tv;
    private int progressstatus;
    private Handler handler=new Handler();
    private boolean drivermanual=false;


    @Override
    /**
     * Responsible for button clicks, progress bar, and spinner choices, sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingscreen);

        Bundle extras=getIntent().getExtras();
        String rooms=extras.getString("Rooms");
        String Alg=extras.getString("Algorithm");
        String diff=extras.getString("Difficulty");


        //Initializes spinner for driver choice
        Spinner drvr=(Spinner) findViewById(R.id.Driver);
        //Initializes array adapter to use driver string array
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Driver);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sets adapter for drvr to aa
        drvr.setAdapter(aa);
        //listens for change on driver spinner
        drvr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Listen for change on driver spinner
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast and LogV message when spinner changes
                Toast.makeText(GeneratingActivity.this,Driver[i],Toast.LENGTH_LONG).show();
                Log.v("Driver_Selected","Driver Selected: "+Driver[i]);
                //Controls boolean so that we can switch to correct screen
                if(Driver[i]=="Manual"){
                    drivermanual=true;
                }
                else{
                    drivermanual=false;
                }
            }


            /**
             * When nothing is selected, auto choose manual
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast and LogV message when spinner doesn't change
                Toast.makeText(GeneratingActivity.this,Driver[0],Toast.LENGTH_LONG).show();
                Log.v("Driver_Selected","Driver Selected: "+Driver[0]);
                drivermanual=true;


            }
        });
        //Initializes robot spinner
        Spinner robot=(Spinner) findViewById(R.id.Robot);
        //initializes array adapter to use reliability string array
        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Reliability);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sets adapter for drvr to a2
        robot.setAdapter(a2);
        //listens for change on robot spinner
        robot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Listen for change on robot spinner
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast and LogV message when spinner changes
                Toast.makeText(GeneratingActivity.this,Reliability[i],Toast.LENGTH_LONG).show();

                Log.v("Robot_Selected","Robot Selected: "+Reliability[i]);

            }

            /**
             * When nothing is selected auto choose premium
             * @param adapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast and LogV message when spinner doesn't change
                Toast.makeText(GeneratingActivity.this,Reliability[0],Toast.LENGTH_LONG).show();
                Log.v("Robot_Selected","Robot Selected: "+Reliability[0]);

            }
        });
        //Initializes Progress Bar
        pbar=(ProgressBar)findViewById(R.id.ProgressBar) ;
        //Initializes loading text view
        tv=(TextView)findViewById(R.id.loading);

        //new thread to increment loading
        new Thread(new Runnable() {
            /**
             * Runs while loop that increments progress bar
             */
            @Override
            public void run() {
                while(progressstatus<100){
                    progressstatus+=1;
                    android.os.SystemClock.sleep(50);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbar.setProgress(progressstatus);

                        }
                    });

                }
                /**
                 * Once progress bar at max, show text and switch screen
                 */
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //sets text to visible once loading is complete
                        tv.setVisibility(View.VISIBLE);
                        if(drivermanual==true){
                            changeActivitytomanual();
                        }
                        else{
                            changeActivitytoanimation();
                        }

                    }
                });
            }
        }).start();
        //initializes back button
        backbutton=findViewById(R.id.backbutton);
        //listens for click on back button
        backbutton.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for click on back button
             * @param view
             */
            @Override
            public void onClick(View view) {
                //Toast and LogV message when back button clicked
                Toast.makeText(GeneratingActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });


    }

    /**
     * Changes Activity to title, used for backbutton
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }

    /**
     * Changes Activity to playmanually
     */

    private void changeActivitytomanual(){
        Intent intent=new Intent(this, PlayManuallyActivity.class);
        startActivity(intent);
    }

    /**
     * Changes Activity to playanimation
     */
    private void changeActivitytoanimation(){
        Intent intent=new Intent(this, PlayAnimationActivity.class);
        startActivity(intent);
    }
}
