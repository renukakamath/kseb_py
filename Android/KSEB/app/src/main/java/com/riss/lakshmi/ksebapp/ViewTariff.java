package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewTariff extends AppCompatActivity implements JsonResponse {

    ListView l1;
    String[] ctype,minusage,maxusage,amount,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tariff);

        l1=(ListView) findViewById(R.id.listView1);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ViewTariff.this;
        String q = "/viewtariff/";
        JR.execute(q);
//            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);

    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");
                if (ja.length() > 0) {
                    ctype = new String[ja.length()];
                    minusage = new String[ja.length()];
                    maxusage = new String[ja.length()];
                    amount = new String[ja.length()];
                    details = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        ctype[i] = ja.getJSONObject(i).getString("connection_type");
                        minusage[i] = ja.getJSONObject(i).getString("minimum_usage");
                        maxusage[i] = ja.getJSONObject(i).getString("maximum_usage");
                        amount[i] = ja.getJSONObject(i).getString("amount_per_unit");
                        details[i] = "Connection Type : " + ctype[i] + "\nMinimum Usage : " + minusage[i] + "\nMaximum Usage : " + maxusage[i] + "\nAmount : " + amount[i];
                    }
                    l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
                }
            } else {
                Toast.makeText(getApplicationContext(), "No bills available..!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
