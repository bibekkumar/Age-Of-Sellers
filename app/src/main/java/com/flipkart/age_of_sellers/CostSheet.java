package com.flipkart.age_of_sellers;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bibek.kumar on 06/06/15.
 */
public class CostSheet {

    public HashMap<String, String> marketing_cost;
    public HashMap<String, String> inventory_cost;
    public HashMap<String, String> operating_cost;
    public HashMap<String, HashMap<String, HashMap<String, String>>> bound;
    HashMap<String, HashMap<String, String>> segment;
    HashMap<String, String> type;
    private static CostSheet ourInstance = new CostSheet();

    public static CostSheet getInstance() {
        return ourInstance;
    }

    private CostSheet() {
        marketing_cost = new HashMap<String, String>();
        inventory_cost = new HashMap<String, String>();
        operating_cost = new HashMap<String,String>();
        bound = new HashMap<String, HashMap<String, HashMap<String, String>>>();
        segment = new HashMap<String, HashMap<String, String>>();
        type = new HashMap<String, String>();
    }

    public void init(JSONObject result) {
        try {
            marketing_cost.put(result.getJSONArray("investement_packages").getJSONObject(0).getString("package"), result.getJSONArray("investement_packages").getJSONObject(0).getString("cost_per_week"));
            marketing_cost.put(result.getJSONArray("investement_packages").getJSONObject(1).getString("package"), result.getJSONArray("investement_packages").getJSONObject(1).getString("cost_per_week"));
            inventory_cost.put(result.getJSONArray("investement_packages").getJSONObject(7).getString("package"), result.getJSONArray("investement_packages").getJSONObject(7).getString("cost_per_week"));
            inventory_cost.put(result.getJSONArray("investement_packages").getJSONObject(8).getString("package"), result.getJSONArray("investement_packages").getJSONObject(8).getString("cost_per_week"));
            inventory_cost.put(result.getJSONArray("investement_packages").getJSONObject(9).getString("package"), result.getJSONArray("investement_packages").getJSONObject(9).getString("cost_per_week"));
            operating_cost.put(result.getJSONArray("investement_packages").getJSONObject(2).getString("package"), result.getJSONArray("investement_packages").getJSONObject(2).getString("cost_per_week"));
            operating_cost.put(result.getJSONArray("investement_packages").getJSONObject(3).getString("package"), result.getJSONArray("investement_packages").getJSONObject(3).getString("cost_per_week"));
            operating_cost.put(result.getJSONArray("investement_packages").getJSONObject(4).getString("package"), result.getJSONArray("investement_packages").getJSONObject(4).getString("cost_per_week"));
            operating_cost.put(result.getJSONArray("investement_packages").getJSONObject(5).getString("package"), result.getJSONArray("investement_packages").getJSONObject(5).getString("cost_per_week"));
            operating_cost.put(result.getJSONArray("investement_packages").getJSONObject(6).getString("package"), result.getJSONArray("investement_packages").getJSONObject(6).getString("cost_per_week"));

            for (int k = 0; k < 3; k++) {


                for (int j = 0; j < 3; j++) {

                    for (int i = 0; i < 3; i++) {
                        type.put(result.getJSONArray("purchase_cost").getJSONObject(j).getJSONArray("items").getJSONObject(i).getString("category"), result.getJSONArray("purchase_cost").getJSONObject(0).getJSONArray("items").getJSONObject(i).getString("category"));
                        segment.put(result.getJSONArray("purchase_cost").getJSONObject(j).getString("segment"), type);
                    }
                    bound.put(result.getJSONArray("purchase_cost").getJSONObject(k).getString("stock_lower_bound") + "-" + result.getJSONArray("purchase_cost").getJSONObject(k).getString("stock_lower_bound"), segment);
                }
            }
        } catch (Exception e) {

        }
    }
}
