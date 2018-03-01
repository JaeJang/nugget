package com.example.jae.ilovenugget;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

public class monthly_page extends AppCompatActivity {


    private TextView totalNuggets;
    private int m1, m2, m3, m4, m5, m6, m7, m8, m9 ,m10, m11, m12, total;
    private BarChart barChart;
    private DatabaseReference database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_page);
        //graph = findViewById(R.id.graph);
        database = FirebaseDatabase.getInstance().getReference();
        barChart = findViewById(R.id.graph);
        totalNuggets = findViewById(R.id.totalNuggets);
        /*ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NuggetData data = dataSnapshot.getValue(NuggetData.class);
                Log.d("nuggetTracker", "" + data);
                mSnapshotList.add(dataSnapshot.getValue(NuggetData.class));
                String month = data.getDate().substring(2,4);
                int nugget = data.getNumOfNugget();
                countNuggetByMonth(Integer.parseInt(month), nugget);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //Log.d("nuggetTracker", ""+ mSnapshotList.size());
    }

    protected void onStart(){
        super.onStart();
        //DataPoint dataPoint[] = null;
        ref = database.child("record");
        m1 = 0; m2 = 0; m3 = 0; m4 = 0; m5 = 0; m6 = 0; m7 = 0; m8 = 0; m9 = 0; m10 = 0; m11 = 0;m12 = 0;
        total = 0;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    NuggetData data = snap.getValue(NuggetData.class);
                    Log.d("nuggetTracker", "" + data);
                    String month = data.getDate().substring(2,4);
                    int nugget = data.getNumOfNugget();
                    countNuggetByMonth(Integer.parseInt(month), nugget);
                }

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(1, m1));
                barEntries.add(new BarEntry(2, m2));
                barEntries.add(new BarEntry(3, m3));
                barEntries.add(new BarEntry(4, m4));
                barEntries.add(new BarEntry(5, m5));
                barEntries.add(new BarEntry(6, m6));
                barEntries.add(new BarEntry(7, m7));
                barEntries.add(new BarEntry(8, m8));
                barEntries.add(new BarEntry(9, m9));
                barEntries.add(new BarEntry(10, m10));
                barEntries.add(new BarEntry(11, m11));
                barEntries.add(new BarEntry(12, m12));

                BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");


                BarData theData = new BarData(barDataSet);

                final String[] dates = new String[] {" ","JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return dates[(int)value];
                    }

                };
                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(formatter);
                barChart.setData(theData);

                barChart.setTouchEnabled(true);
                barChart.setAutoScaleMinMaxEnabled(true);

                barChart.invalidate();
                total = m1+m2+m3+m4+m5+m6+m7+m8+m9+m10+m11+m12;
                totalNuggets.setText("" + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void countNuggetByMonth(int month, int nugget) {
        if(month == 1)
            m1+= nugget;
        if(month == 2)
            m2+= nugget;
        if(month == 3)
            m3+= nugget;
        if(month ==4)
            m4+= nugget;
        if(month == 5)
            m5+= nugget;
        if(month == 6)
            m6+= nugget;
        if(month == 7)
            m7+= nugget;
        if(month == 8)
            m8+= nugget;
        if(month == 9)
            m9+= nugget;
        if(month == 10)
            m10+= nugget;
        if(month == 11)
            m11+= nugget;
        if(month == 12)
            m12+= nugget;



    }


}
