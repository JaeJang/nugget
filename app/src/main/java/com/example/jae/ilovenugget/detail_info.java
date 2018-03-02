package com.example.jae.ilovenugget;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Map;

public class detail_info extends AppCompatActivity implements View.OnClickListener {

    private static TextView date;
    private static int _day, _month, _year;
    private TextView numOfNugget;
    private ImageButton dateSelect;
    private Spinner meal_spinner, sauce_spinner;
    private Button submit, button;
    private DatabaseReference ref;
    private SeekBar seekbar;
    private CheckBox yes, no;

    private String meal_selected, sauce_selected;
    private static String date_selected;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        ref = FirebaseDatabase.getInstance().getReference();

        numOfNugget = findViewById(R.id.num_nugget);
        dateSelect = findViewById(R.id.date_select);
        date = findViewById(R.id.date);
        meal_spinner = findViewById(R.id.meal_spinner);
        sauce_spinner = findViewById(R.id.sauce_spinner);
        submit = findViewById(R.id.goToWeekly);
        seekbar = findViewById(R.id.seekbar_enjoyment);
        yes = findViewById(R.id.checkbox_yes);
        no = findViewById(R.id.checkbox_no);


        counter = getIntent().getIntExtra("num_nugget", 0);

        numOfNugget.setText("" + counter);

        dateSelect.setOnClickListener(this);
        submit.setOnClickListener(this);

        setSpinner(R.array.meal_list, meal_spinner);
        setSpinner(R.array.sauce_list, sauce_spinner);
    }

    public void onClick(View v){
        if(v.getId() == R.id.date_select){
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        } else if(v.getId() == R.id.goToWeekly){
            if(date.equals("")){
                Toast.makeText(getApplicationContext(), "DATE DATE DATE DATE DATE", Toast.LENGTH_SHORT).show();
            } else if(yes.isChecked() && no.isChecked()) {
                Toast.makeText(getApplicationContext(), "REGRET OR WHAT?", Toast.LENGTH_SHORT).show();
            } else if(!yes.isChecked() && !no.isChecked()){
                Toast.makeText(getApplicationContext(), "REGRET OR WHAT?", Toast.LENGTH_SHORT).show();
            } else if(meal_selected.equals("") || sauce_selected.equals("")){
                Toast.makeText(getApplicationContext(), "DO COMPLETE!", Toast.LENGTH_SHORT).show();
            } else{
                NuggetData data = null;
                data = yes.isChecked() ? new NuggetData(counter, date_selected,meal_selected, sauce_selected,seekbar.getProgress(), "yes")
                        : new NuggetData(counter, date_selected,meal_selected, sauce_selected,seekbar.getProgress(), "no");
                /*if(yes.isChecked()){
                        data = new NuggetData(counter, date_selected,meal_selected, sauce_selected,seekbar.getProgress(), "yes");

                } else if(no.isChecked()){
                        data = new NuggetData(counter, date_selected,meal_selected, sauce_selected,seekbar.getProgress(), "no");
                }*/
                Log.d("nuggetTracker","" + data);
                ref.child("record").push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Log.d("nuggetTracker","failed / " + task.getException());
                        } else{
                            Intent intent = new Intent(getApplicationContext(), monthly_page.class);
                            startActivity(intent);
                        }
                    }
                });
            }

        }

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        public Dialog onCreateDialog(Bundle savedInstanceState){
            int day, month, year;
            Calendar cal = Calendar.getInstance();
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            _day = dayOfMonth; _month = month + 1; _year = year;
            //Log.d("nuggetTracker", "day: " + _day + " month: " + _month + " year: " + _year );
            date.setText(dayOfMonth + " / " + (month + 1)  + " / " + year);

            if(_day < 10 && _month < 10){

                date_selected = "0" + String.valueOf(_day) + "0" + String.valueOf(_month) + String.valueOf(_year);

            } else if( _day < 10 && _month >=10) {
                date_selected ="0" + String.valueOf(_day) + String.valueOf(_month) + String.valueOf(_year);

            } else if(_day >= 10 && _month <10){
                date_selected =String.valueOf(_day) + "0" + String.valueOf(_month) + String.valueOf(_year);

            } else if(_day >=10 && _month >= 10){

                date_selected =String.valueOf(_day) +String.valueOf(_month) + String.valueOf(_year);
            }
        }
    }

    private void setSpinner(int array, final Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getId() == R.id.meal_spinner){
                    meal_selected = parent.getItemAtPosition(position).toString();
                }
                else if(spinner.getId() == R.id.sauce_spinner){
                    sauce_selected = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




}
