package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

public class AMazeActivity extends AppCompatActivity {

    private Button explore;
    private Button revisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mazetitle);
        explore=findViewById(R.id.Explore);
        revisit=findViewById(R.id.revisit);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();

            }
        });
        revisit.setOnClickListener(new View.OnClickListener() {
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
        Intent intent=new Intent(this, GeneratingActivity.class);
        startActivity(intent);
    }
}
