package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class AMazeActivity extends AppCompatActivity{

    private Button explore;
    private Button revisit;
    private String[] Algorithms={"DFS","Prim","Boruvka"};
    private String[] rooms={"Rooms","No Rooms"};
    SeekBar seekbar;
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mazetitle);
        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        view=(TextView)findViewById(R.id.rating);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                view.setText("Rating"+String.valueOf(i));
                Toast.makeText(AMazeActivity.this,String.valueOf(i),Toast.LENGTH_LONG).show();
                Log.v("Seekbar_Changed","Seekbar value changed to"+String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        explore=findViewById(R.id.Explore);
        revisit=findViewById(R.id.revisit);
        Spinner alg=(Spinner) findViewById(R.id.Algorithm);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Algorithms);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alg.setAdapter(aa);
        alg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AMazeActivity.this,Algorithms[i],Toast.LENGTH_LONG).show();
                Log.v("Builder_selection","Builder changed to"+Algorithms[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AMazeActivity.this,Algorithms[0],Toast.LENGTH_LONG).show();
                Log.v("Builder_selection","Builder is"+Algorithms[0]);

            }
        });

        Spinner roomyn=(Spinner) findViewById(R.id.Rooms);

        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, rooms);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomyn.setAdapter(a2);
        roomyn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AMazeActivity.this,rooms[i],Toast.LENGTH_LONG).show();
                Log.v("Rooms_selection","Rooms changed to"+rooms[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AMazeActivity.this,rooms[0],Toast.LENGTH_LONG).show();
                Log.v("Rooms_selection","Rooms set to"+rooms[0]);

            }
        });


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AMazeActivity.this,"Explore",Toast.LENGTH_LONG).show();
                Log.v("Explore","Explore clicked");
                changeActivity();


            }
        });
        revisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AMazeActivity.this,"Revisit",Toast.LENGTH_LONG).show();
                Log.v("Revisit","Revisit clicked");
                changeActivity();

            }
        });


    }


    /**
     * Changes the activity from losing screen to title screen
     */
    private void changeActivity(){
        Intent intent=new Intent(this, GeneratingActivity.class);
        startActivity(intent);
    }



}
