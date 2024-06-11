package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Notification extends AppCompatActivity implements JsonResponse {

    ListView lv_notifictaions;
    SharedPreferences sh;

    String[] notification, date,type,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv_notifictaions = (ListView) findViewById(R.id.lv_notification);

        getDetails();
    }

    void getDetails() {
        try {
            JsonReq JR=new JsonReq();
            JR.json_response=(JsonResponse) Notification.this;
            String q = "/notifications/?cons_id=" + sh.getString("uid", "0");
            JR.execute(q);
//            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
            Log.d("notification_log", q);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("result");
                if (ja.length() > 0) {
                    type = new String[ja.length()];
                    notification = new String[ja.length()];
                    date = new String[ja.length()];
                    details = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        type[i] = ja.getJSONObject(i).getString("notificationtype");
                        notification[i] = ja.getJSONObject(i).getString("description");
                        date[i] = ja.getJSONObject(i).getString("date");
                        details[i] ="Type : "+type[i]+"\nNotification : "+notification[i] + "\nDate : " + date[i] + "\n";
                    }
                    lv_notifictaions.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}
