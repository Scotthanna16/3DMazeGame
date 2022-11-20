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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingscreen);



        Spinner drvr=(Spinner) findViewById(R.id.Driver);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Driver);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drvr.setAdapter(aa);
        drvr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GeneratingActivity.this,Driver[i],Toast.LENGTH_LONG).show();
                Log.v("Driver_Selected","Driver Selected: "+Driver[i]);
                if(Driver[i]=="Manual"){
                    drivermanual=true;
                }
                else{
                    drivermanual=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(GeneratingActivity.this,Driver[0],Toast.LENGTH_LONG).show();
                Log.v("Driver_Selected","Driver Selected: "+Driver[0]);
                drivermanual=true;


            }
        });

        Spinner robot=(Spinner) findViewById(R.id.Robot);

        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Reliability);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        robot.setAdapter(a2);
        robot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GeneratingActivity.this,Reliability[i],Toast.LENGTH_LONG).show();

                Log.v("Robot_Selected","Robot Selected: "+Reliability[i]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(GeneratingActivity.this,Reliability[0],Toast.LENGTH_LONG).show();
                Log.v("Robot_Selected","Robot Selected: "+Reliability[0]);

            }
        });

        pbar=(ProgressBar)findViewById(R.id.ProgressBar) ;
        tv=(TextView)findViewById(R.id.loading);
        new Thread(new Runnable() {
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
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
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GeneratingActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
                changeActivity();

            }
        });


    }
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }

    private void changeActivitytomanual(){
        Intent intent=new Intent(this, PlayManuallyActivity.class);
        startActivity(intent);
    }
    private void changeActivitytoanimation(){
        Intent intent=new Intent(this, PlayAnimationActivity.class);
        startActivity(intent);
    }
}
