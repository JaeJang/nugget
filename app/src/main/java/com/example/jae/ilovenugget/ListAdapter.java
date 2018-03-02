package com.example.jae.ilovenugget;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jaeja on 2018-03-01.
 */

public class ListAdapter extends BaseAdapter {

    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList = new ArrayList<>();;
    private Activity mActivity;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
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
    };

    public ListAdapter(DatabaseReference ref, Activity activity){
        mDatabaseReference = ref;
        mDatabaseReference.addChildEventListener(mListener);
        mActivity = activity;
        Log.d("nuggetTracker","" + mSnapshotList.size());
    }


    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public NuggetData getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(NuggetData.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nugget_list, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.date_meal_view = convertView.findViewById(R.id.date_meal_view);
            holder.nugget_sauce = convertView.findViewById(R.id.nugget_sauce);
            holder.you_regret_or_not = convertView.findViewById(R.id.you_regret_or_not);
            convertView.setTag(holder);
        }

        final NuggetData data = getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();

        String date = data.getDate();
        String convertedDate = date.substring(0,2) + " / " + date.substring(2, 4)
                + " / " + date.substring(4);
        holder.date_meal_view.setText(convertedDate + " - " + data.getNumOfNugget() + " NUGGETS");

        holder.nugget_sauce.setText("You had " + data.getNumOfNugget() + " nuggets with "
                + data.getSauce() + " for your " + data.getMeal());

        String YorN = data.getRegret();
        if(YorN.equalsIgnoreCase("yes")){
            holder.you_regret_or_not.setText("YOU REGRETTED (enjoyment: " + data.getEnjoyment() + ")");
        } else {
            holder.you_regret_or_not.setText("You did not regret (enjoyment: " + data.getEnjoyment() + ")");

        }
        return convertView;
    }

    static class ViewHolder{
        TextView date_meal_view;
        TextView nugget_sauce;
        TextView you_regret_or_not;
    }
}
