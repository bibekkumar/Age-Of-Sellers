package com.flipkart.age_of_sellers;

/**
 * Created by bibek.kumar on 06/06/15.
 */
public class CostSheet {


    private static CostSheet ourInstance = new CostSheet();

    public static CostSheet getInstance() {
        return ourInstance;
    }

    private CostSheet() {
    }
}
