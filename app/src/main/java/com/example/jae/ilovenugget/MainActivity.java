package com.example.jae.ilovenugget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private TextView counter_view;
    private ImageButton nugget;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign a view to a variable by view id
        submit = findViewById(R.id.submit_button);
        counter_view = findViewById(R.id.nugget_counter);
        nugget = findViewById(R.id.nugget_click);

        //Set click listener to the nugget button
        nugget.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
             counter += 1;
             counter_view.setText(""+ counter);
            }
        });

        //Set click listener to the submit button
        //It changes layout and passes the number of nugget clicked
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(counter <= 0){
                    Toast.makeText(getApplicationContext(), "Come on, there is no nuggets", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(getApplicationContext(), detail_info.class);
                    intent.putExtra("num_nugget", counter);
                    startActivity(intent);
                }
            }
        });
    }
}
