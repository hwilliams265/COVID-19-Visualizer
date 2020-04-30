package com.example.covid_19visualizer.ui.main;

//creating a class to store the details of every information tab from the CDC's RSS feed
//created by Federico Coppo

public class xmlinfo {

    //Store the headlines
    public String myheadlines;

    //Store the descriptions
    private String mydescriptions;

    //Constructor used to create an instance of the xmlinfo object
    public xmlinfo(String myheadlines, String mydescriptions)
    {
        this.myheadlines = myheadlines;
        this.mydescriptions = mydescriptions;
    }

    public String getheadlines()
    {
        return myheadlines;
    }

    public void setmyheadlines(String myheadlines)
    {
        this.myheadlines = myheadlines;
    }

    public String getdescriptions()
    {
        return mydescriptions;
    }

    public void setmydescriptions(String mydescriptions)
    {
        this.mydescriptions = mydescriptions;
    }
}
