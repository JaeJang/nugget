package com.example.jae.ilovenugget;

/**
 * Created by jaeja on 2018-02-27.
 */

public class NuggetData {

    private int numOfNugget;
    private String date;
    private String meal;
    private String sauce;
    private int enjoyment;
    private String regret;

    public NuggetData(int numOfNugget, String date, String meal, String sauce, int enjoyment, String regret){
        this.numOfNugget = numOfNugget;
        this.date = date;
        this.meal = meal;
        this.sauce = sauce;
        this.enjoyment = enjoyment;
        this.regret = regret;
    }

    public NuggetData(){

    }

    public int getNumOfNugget() {
        return numOfNugget;
    }

    public String getDate() {
        return date;
    }

    public String getMeal() {
        return meal;
    }

    public String getSauce() {
        return sauce;
    }

    public int getEnjoyment() {
        return enjoyment;
    }

    public String getRegret() {
        return regret;
    }
    public String toString(){
        return numOfNugget + " " + date + " " + meal + " "  + sauce + " " + enjoyment + " " + regret;
    }
}
