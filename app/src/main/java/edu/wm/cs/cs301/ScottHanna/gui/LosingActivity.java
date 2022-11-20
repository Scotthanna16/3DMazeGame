package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LosingActivity extends AppCompatActivity {

    private Button backbutton;
    @Override
    /**
     * Responsible for button clicks, and sets screen views
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.losingscreen);
        //initializes backbutton
        backbutton=findViewById(R.id.backbutton);
        //listens for click on backbutton
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LogV and Toast for when back button is clicked
                Toast.makeText(LosingActivity.this,"Back",Toast.LENGTH_LONG).show();
                Log.v("Back","Back clicked");
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
