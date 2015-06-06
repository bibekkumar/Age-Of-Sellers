package com.flipkart.age_of_sellers;

import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by bibek.kumar on 06/06/15.
 */
public class Seller {

    private int budget;

    private int workingCapital;

    private String name;

    private String eMail;

    private String geoLocation;

    private String phone;

    private SharedPreferences sPref;

    Context context;

    private static Seller ourInstance = new Seller();

    public static Seller getInstance() {
        return ourInstance;
    }

    void init(String name, String email, String geo, String phn, Context cxt)
    {
        this.name = name;
        this.eMail = email;
        this.geoLocation = geo;
        this.phone = phn;
        this.context = cxt;

        sPref = context.getSharedPreferences(eMail, 0);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("name", this.name);
        editor.putString("eMail", this.eMail);
        editor.putString("geoLocation", this.geoLocation);
        editor.putString("phone", this.phone);

        editor.commit();
    }

    String getName()
    {
        return name;
    }

    String geteMail()
    {
        return eMail;
    }

    String getGeoLocation()
    {
        return geoLocation;
    }

    String getPhone()
    {
        return phone;
    }

    int getBudget()
    {
        return budget;
    }

    void setBudget(int bud)
    {
        budget = bud;

        sPref = context.getSharedPreferences(eMail, 0);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("budget", this.budget);

        editor.commit();
    }

    int getWorkingCapital()
    {
        return workingCapital;
    }

    void setWorkingCapital(int wc)
    {
        workingCapital = wc;

        sPref = context.getSharedPreferences(eMail, 0);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt("workingCapital", this.workingCapital);

        editor.commit();
    }

    private Seller() {

    }
}
