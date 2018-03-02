package com.example.jae.ilovenugget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by jaeja on 2018-02-28.
 */

public class MenuListener implements View.OnClickListener {

    private final Context context;
    private final ImageButton popUpBtn;
    private  PopupMenu mainPopUp;

    public MenuListener(Context context, View popUpBtn, PopupMenu mainPopUp){
        this.context = context;
        this.popUpBtn = (ImageButton)popUpBtn;
        this.mainPopUp = mainPopUp;
    }
    @Override
    public void onClick(View v) {

        mainPopUp.getMenuInflater().inflate(R.menu.popup_menu, mainPopUp.getMenu());

        mainPopUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Nuggets")) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    return true;
                } else if (menuItem.getTitle().equals("Graphs")) {
                    Intent intent = new Intent(context, monthly_page.class);
                    context.startActivity(intent);
                    return true;
                } /*else if (menuItem.getTitle().equals("Achievements")) {
                            Intent intent = new Intent(getApplicationContext(), AchievementActivity.class);
                            startActivity(intent);
                            return true;
                        }*/ else
                    return false;
            }
        });
        mainPopUp.show();
    }
}

