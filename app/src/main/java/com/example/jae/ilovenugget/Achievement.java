package com.example.jae.ilovenugget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Achievement extends AppCompatActivity {

    private TextView achieve1, achieve2, achieve3, achieve4;
    private ImageButton menu;
    private DatabaseReference ref;
    private ArrayList<NuggetData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        list = new ArrayList<>();

        achieve1 = findViewById(R.id.achieve1);
        achieve2 = findViewById(R.id.achieve2);
        achieve3 = findViewById(R.id.achieve3);
        achieve4 = findViewById(R.id.achieve4);
        menu = findViewById(R.id.menu4);


        ref = FirebaseDatabase.getInstance().getReference().child("record");

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final PopupMenu mainPopUp = new PopupMenu(Achievement.this, menu);
                mainPopUp.getMenuInflater().inflate(R.menu.popup_menu, mainPopUp.getMenu());

                mainPopUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Nugget")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            return true;
                        } else if (menuItem.getTitle().equals("Graphs")) {
                            Intent intent = new Intent(getApplicationContext(), monthly_page.class);
                            startActivity(intent);
                            return true;
                        } else if (menuItem.getTitle().equals("List")) {
                            Intent intent = new Intent(getApplicationContext(), List_page.class);
                            startActivity(intent);
                            return true;
                        } else
                            return false;
                    }
                });
                mainPopUp.show();
            }
        });
    }

    protected void onStart(){
        super.onStart();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    list.add(snap.getValue(NuggetData.class));
                }

                int total = getTotalNuggets(list);
                int calories = total * 48;
                int days = getDays(list);
                double money = total * 0.65;

                DecimalFormat fmt = new DecimalFormat(".##");

                achieve1.setText(total + " Lifetime Nuggets");
                achieve2.setText(calories + " calories earned");
                achieve3.setText(days + "/365 days this year");
                achieve4.setText("$" + fmt.format(money) + " spent");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    /**
     * Return total number of nuggets in Database.
     *
     * @param list an ArrayList of NuggetData class
     * @return total  (int)number of nuggets
     */
    private int getTotalNuggets(ArrayList<NuggetData> list){
        int total = 0;
        for(NuggetData data : list){
            total += data.getNumOfNugget();
        }
        return total;
    }

    /**
     * Get the days they had nuggets for current year
     *
     * @param list an ArrayList of NuggetData
     * @return (int) the number of days
     */
    private int getDays(ArrayList<NuggetData> list){

        String currentYear = "" + Calendar.getInstance().get(Calendar.YEAR);
        HashMap<String,Integer> differentDays = new HashMap<>();
        for(NuggetData data : list){
            String year = data.getDate().substring(4);
            if(year.equalsIgnoreCase(currentYear)){
                differentDays.put(data.getDate(), 1);
            }
        }
        return differentDays.size();
    }
}
