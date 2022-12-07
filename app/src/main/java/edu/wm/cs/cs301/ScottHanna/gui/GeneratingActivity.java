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


import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.wm.cs.cs301.ScottHanna.generation.Maze;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import edu.wm.cs.cs301.ScottHanna.R;
import edu.wm.cs.cs301.ScottHanna.generation.DefaultOrder;
import edu.wm.cs.cs301.ScottHanna.generation.MazeFactory;
import edu.wm.cs.cs301.ScottHanna.generation.Order;
import edu.wm.cs.cs301.ScottHanna.gui.Robot;
import edu.wm.cs.cs301.ScottHanna.gui.RobotDriver;

public class GeneratingActivity extends AppCompatActivity {
    private Button backbutton;
    String[]Driver={"None","Manual","Wall Follower", "Wizard"};
    String[]Reliability={"None","Premium","Mediocore","Soso","Shaky"};

    private ProgressBar pbar;
    private TextView tv;
    private TextView text;
    private int progressstatus=0;
    private Handler handler=new Handler();
    private boolean drivermanual=false;
    private static Maze maze;
    private SharedPreferences seed;
    private String Robotstr="None";
    private String Driverstr="None";
    private boolean mazeacquired=false;

    private boolean driverselected=false;
    private boolean robotselected=false;

    private int count=0;




    @Override
    /**
     * Responsible for button clicks, progress bar, and spinner choices, sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generatingscreen);

        Bundle extras=getIntent().getExtras();
        final String[] rooms = {extras.getString("Rooms")};
        final String[] Alg = {extras.getString("Algorithm")};
        int[] diff = {extras.getInt("Difficulty")};
        boolean revisited=extras.getBoolean("revisitclicked");




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
                    driverselected=true;
                    drivermanual=true;
                    Driverstr="Manual";
                }
                else if(Driver[i]=="Wizard"||Driver[i]=="Wall Follower"){
                    driverselected=true;
                    drivermanual=false;
                    if(Driver[i]=="Wizard"){
                        Driverstr="Wizard";
                    }
                   else {
                        Driverstr = "Wall Follower";
                    }

                }
                checkifready();
            }


            /**
             * When nothing is selected, auto choose manual
             * @param adapterView
             */

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Toast and LogV message when spinner doesn't change
                Toast.makeText(GeneratingActivity.this,Driver[0],Toast.LENGTH_LONG).show();
                Log.v("Driver_Selected","No Driver Selected: "+Driver[0]);
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
                //"Premium","Mediocore","Soso","Shaky"};
                if(Reliability[i]=="Premium"){
                    Robotstr="Premium";
                }
                else if(Reliability[i]=="Mediocore"){
                    Robotstr="Mediocore";

                }
                else if(Reliability[i]=="Soso"){
                    Robotstr="Soso";

                }
                else if(Reliability[i]=="Shaky"){
                    Robotstr="Shaky";
                }
                checkifready();



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
        text=findViewById(R.id.noselection);

        //new thread to increment loading
        new Thread(new Runnable() {
            /**
             * Runs while loop that increments progress bar
             */
            @Override
            public void run() {
                MazeFactory factory=new MazeFactory();
                DefaultOrder order;
                int seed;
                //Revisit wasn't clicked, must store new maze and settings
                if(revisited==false){
                    Random rand=new Random();
                    seed= rand.nextInt(10000);
                    saveDataInSharedPreferences(seed,Integer.valueOf(diff[0]), rooms[0], Alg[0]);

                }
                else{
                    //revisit was clicked, must get maze settings from shared preferences
                    seed=readDataFromSharedPreferencesseed();
                    diff[0] =readDataFromSharedPreferencessize();
                    rooms[0] =readDataFromSharedPreferencesRooms();
                    Alg[0] =readDataFromSharedPreferencesALG();
                    Log.v("Maze setting recovered","Maze settings recovered from last play using shared preferences");

                }
                if(Alg[0].equals("DFS")){
                    if(rooms[0].equals("Rooms")){
                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.DFS,false,seed);
                    }
                    else{
                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.DFS,true,seed);
                    }
                }
                else if(Alg[0].equals("Prim")){
                    if(rooms[0].equals("Rooms")){

                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.Prim,false,seed);
                    }
                    else{
                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.Prim,true,seed);
                    }
                }
                else{
                    if(rooms[0].equals("Rooms")){
                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.Boruvka,false,seed);
                    }
                    else{
                        order=new DefaultOrder(Integer.valueOf(diff[0]), Order.Builder.Boruvka,true,seed);
                    }
                }
                //generate the maze
                factory.order(order);
                //Update progress bar
                while(progressstatus<100){
                    progressstatus=order.getProgress();
                    android.os.SystemClock.sleep(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbar.setProgress(progressstatus);

                        }
                    });

                }
                //get the maze
                maze=order.getMaze();

                if(maze!=null){
                    Log.v("Maze acquired","Maze acruied from factory");
                    mazeacquired=true;




                }
                else{
                    Log.e("Maze null","maze is null");
                }




                /**
                 * Once progress bar at max, show text and switch screen
                 */






                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        //sets text to visible once loading is complete
                        tv.setVisibility(View.VISIBLE);
                        checkifready();

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
     * Saves the various parameters using sharedpreferences so that they can be accessed if revisit
     * button is clicked
     * @param mazeseed seed to be saved
     * @param size size to be saved
     * @param perfect whether the saved maze is perfect
     * @param algor Algorithm used to build maze
     */
    private void saveDataInSharedPreferences(int mazeseed, int size, String perfect,String algor) {
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myedit= sharedPreferences.edit();
        myedit.putInt("Seed",mazeseed);
        myedit.putInt("Size",size);
        myedit.putString("Perfect",perfect);

        myedit.putString("Algorithm",algor);
        myedit.commit();
    }

    /**
     * Gets seed of previous maze from shared preferences
     * @return seed saved in shared preferences
     */
    private int readDataFromSharedPreferencesseed(){
        Random rand=new Random();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int mazeseed=sharedPreferences.getInt("Seed",rand.nextInt(10000));
        return mazeseed;
    }
    /**
     * Gets size of previous maze from shared preferences
     * @return size saved in shared preferences
     */
    private int readDataFromSharedPreferencessize(){
        Random rand=new Random();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int mazesize=sharedPreferences.getInt("Size",rand.nextInt(10));
        return mazesize;
    }

    /**
     * Gets algorithm used to build previous maze from shared preferences
     * @return algorithm saved in shared preferences
     */
    private String readDataFromSharedPreferencesALG(){
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String alg=sharedPreferences.getString("Algorithm","Prim");
        return alg;

    }
    /**
     * Gets whether the previous maze was perfect
     * @return rooms saved in shared preferences
     */
    private String readDataFromSharedPreferencesRooms(){
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String rooms=sharedPreferences.getString("Perfect","rooms");
        return rooms;
    }

    /**
     * Changes Activity to title, used for backbutton
     */
    private void changeActivity(){
        Intent intent=new Intent(this, AMazeActivity.class);
        Log.v("Change activity","Activity changed to title");
        startActivity(intent);
    }

    /**
     * Changes Activity to playmanually
     * also passes maze to play manual
     */

    private void changeActivitytomanual(){


            Intent intent=new Intent(this, PlayManuallyActivity.class);
            PlayManuallyActivity.maze=maze;
            Log.v("Change activity","Activity changed to playmanually");
            startActivity(intent);

    }

    /**
     * Changes Activity to playanimation
     * also passes maze to play animation
     */
    private void changeActivitytoanimation(){

            Intent intent=new Intent(this, PlayAnimationActivity.class);
            PlayAnimationActivity.maze=maze;
            intent.putExtra("Robot type",Robotstr);
            intent.putExtra("Driver type",Driverstr);

            Log.v("Change activity","Activity changed to playanimation");
            startActivity(intent);



    }

    private void checkifready(){
        if(Driverstr!="None"&&Robotstr!="None"&&mazeacquired==true){
            if(Driverstr=="Manual"){
                changeActivitytomanual();
            }
            else{
                changeActivitytoanimation();
            }
        }
        if (mazeacquired){
            text.setVisibility(View.VISIBLE);
        }
    }


}
