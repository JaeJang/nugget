package com.example.jae.ilovenugget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class weekly_page extends AppCompatActivity {

    private GraphView graph;
    private LinkedList list = new LinkedList<Last7days>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_page);

        graph = findViewById(R.id.graph);

        getData();

    }

    protected void onStart(){
        super.onStart();

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,1),
                new DataPoint(1,5),
                new DataPoint(2,3)
        });

        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
    }

    private void getData(){
        SharedPreferences saved = getSharedPreferences("date", Activity.MODE_PRIVATE);
        Map<String, ?> all = saved.getAll();
        int seven_days = all.size() - 7;
        int i = 0;
        if(all.size() <= 7){
            for(Map.Entry<String, ?> entry : all.entrySet()){
                String temp = entry.getKey().toString();
                String day = temp.substring(0,2);
                String month = temp.substring(2,4);
                temp = day + "/" + month;
                list.add(new Last7days(temp, Integer.parseInt(entry.getValue().toString())));
            }
        } else {
            for(Map.Entry<String, ?> entry : all.entrySet()){

                if(i >= seven_days){
                    String temp = entry.getKey().toString();
                    String day = temp.substring(0,2);
                    String month = temp.substring(2,4);
                    temp = day + "/"+ month;
                    list.add(new Last7days(temp, Integer.parseInt(entry.getValue().toString())));
                }
                ++i;
            }
        }
        Log.d("nuggetTracker", "size : " + list.size());
        for(int j = 0; j < list.size(); ++j){
            Last7days t = (Last7days) list.get(j);
            Log.d("nuggetTracker", "date: "+t.getDate() + ", nugeet: " + t.getNuggets());
        }

    }

    public class Last7days{
        private final String date;
        private final int nuggets;

        public Last7days(String date, Integer nuggets){
            this.date = date;
            this.nuggets = nuggets;
        }

        public String getDate(){
            return date;
        }

        public int getNuggets(){
            return nuggets;
        }
    }


}
