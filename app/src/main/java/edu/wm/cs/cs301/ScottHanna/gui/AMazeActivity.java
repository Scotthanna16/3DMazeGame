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

    private String Chosenalg;
    private String chosenrooms;
    private int chosendiff=0;
    private boolean revisitclick;

    /**
     * Responsible for button clicks, seekbar adjustments, and spinner choices, sets screen views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        revisitclick=false;
        setContentView(R.layout.mazetitle);
        //initializing seekbar and textview
        seekbar= (SeekBar)findViewById(R.id.Seekbarid);
        view=(TextView)findViewById(R.id.rating);
        //Listens for seekbar change
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /**
             * Listens for change in seek bar
             * @param seekBar
             * @param i
             * @param b
             */
            @Override

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //changes text to represent seekbar
                view.setText("Rating"+String.valueOf(i));
                //Toast and LogV message
                Toast.makeText(AMazeActivity.this,String.valueOf(i),Toast.LENGTH_LONG).show();
                Log.v("Seekbar_Changed","Seekbar value changed to"+String.valueOf(i));
                chosendiff=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Initializes explore and revisit buttons
        explore=findViewById(R.id.Explore);
        revisit=findViewById(R.id.revisit);
        //initalizes spinner for builder algorithm
        Spinner alg=(Spinner) findViewById(R.id.Algorithm);
        //initializes array adapter to use algorithms string array
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Algorithms);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sets aa as adapter for alg
        alg.setAdapter(aa);
        //listens for change in alg spinner
        alg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Listens for change in builder algorithms selection spinner
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast and LogV message when spinner changes
                Toast.makeText(AMazeActivity.this,Algorithms[i],Toast.LENGTH_LONG).show();
                Log.v("Builder_selection","Builder changed to"+Algorithms[i]);
                Chosenalg=Algorithms[i];
            }


            /**
             * When nothing selected, choose DFS
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast and LogV message when spinner not changed
                Toast.makeText(AMazeActivity.this,Algorithms[0],Toast.LENGTH_LONG).show();
                Log.v("Builder_selection","Builder is"+Algorithms[0]);
                Chosenalg=Algorithms[0];

            }
        });
        //initializes spinner for rooms
        Spinner roomyn=(Spinner) findViewById(R.id.Rooms);
        //initializes array adapter to use rooms array
        ArrayAdapter<String> a2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, rooms);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sets a2 as adapter for alg
        roomyn.setAdapter(a2);
        //listens for change in alg spinner
        roomyn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Listens for change in rooms selection spinner
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast and LogV message when spinner changes
                Toast.makeText(AMazeActivity.this,rooms[i],Toast.LENGTH_LONG).show();
                Log.v("Rooms_selection","Rooms changed to"+rooms[i]);
                chosenrooms=rooms[i];
            }

            /**
             * When nothing selected make rooms auto
             */
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast and LogV message when spinner not changed
                Toast.makeText(AMazeActivity.this,rooms[0],Toast.LENGTH_LONG).show();
                Log.v("Rooms_selection","Rooms set to"+rooms[0]);
                chosenrooms=rooms[0];

            }
        });


        explore.setOnClickListener(new View.OnClickListener() {
            /**
             * Listens for click on explore button
             */

            @Override
            public void onClick(View view) {
                //Toast and LogV message when button clicked
                Toast.makeText(AMazeActivity.this,"Explore",Toast.LENGTH_LONG).show();
                Log.v("Explore","Explore clicked");
                changeActivity();


            }
        });

        revisit.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Listens for click on revisit button
             */
            public void onClick(View view) {
                //Toast and LogV message when button clicked
                Toast.makeText(AMazeActivity.this,"Revisit",Toast.LENGTH_LONG).show();
                Log.v("Revisit","Revisit clicked");
                revisitclick=true;
                changeActivity();

            }
        });


    }


    /**
     * Changes the activity from Title screen to Generating Screen
     */
    private void changeActivity(){
        Intent intent=new Intent(this, GeneratingActivity.class);
        intent.putExtra("Algorithm",Chosenalg);
        intent.putExtra("Rooms",chosenrooms);
        intent.putExtra("Difficulty",chosendiff);
        intent.putExtra("revisitclicked",revisitclick);
        Log.v("Change Activity","Changed from Title to generating");
        startActivity(intent);
    }



}
