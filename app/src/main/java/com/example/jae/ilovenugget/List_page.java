package com.example.jae.ilovenugget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class List_page extends AppCompatActivity {

    private ImageButton menu;
    private ListView list;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        list = findViewById(R.id.nuget_list);
        menu = findViewById(R.id.menu2);

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final PopupMenu mainPopUp = new PopupMenu(List_page.this, menu);
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
                        } else if (menuItem.getTitle().equals("Achievements")) {
                            Intent intent = new Intent(getApplicationContext(), Achievement.class);
                            startActivity(intent);
                            return true;
                        } else
                            return false;
                    }
                });
                mainPopUp.show();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("record");

    }

    protected void onStart(){
        super.onStart();
        list.setAdapter(new ListAdapter(ref, this));
    }
}
