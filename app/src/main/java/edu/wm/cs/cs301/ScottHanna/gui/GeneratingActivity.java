package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class GeneratingActivity extends AppCompatActivity {
    private Button backbutton;
    String[]Driver={"Manual","Wall Follower", "Wizard"};
    String[]Reliability={"Premium","Mediocore","Soso","Shaky"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();

            }
        });

        Spinner drvr=(Spinner) findViewById(R.id.Driver);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Driver);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drvr.setAdapter(aa);
        drvr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GeneratingActivity.this,Driver[i],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(GeneratingActivity.this,Reliability[0],Toast.LENGTH_LONG).show();

            }
        });


    }
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}
