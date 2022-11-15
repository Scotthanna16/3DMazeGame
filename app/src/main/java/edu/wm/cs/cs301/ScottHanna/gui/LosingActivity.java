package edu.wm.cs.cs301.ScottHanna.gui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.wm.cs.cs301.ScottHanna.R;

import android.view.View;
import android.widget.Button;

public class LosingActivity extends AppCompatActivity {

    private Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.losingscreen);
        backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
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
        Intent intent=new Intent(this, AMazeActivity.class);
        startActivity(intent);
    }
}
