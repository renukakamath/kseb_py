package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewpridictions extends AppCompatActivity implements JsonResponse {
    SharedPreferences sh;
    TextView tv;
    EditText e1, e2, e3, e4, e5;
    String humidity, holidays, mintemp, maxtemp, rainfall, results;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpridictions);

        e1 = (EditText) findViewById(R.id.ethumidity);
        e2 = (EditText) findViewById(R.id.etholidays);
        e3 = (EditText) findViewById(R.id.etmintemp);
        e4 = (EditText) findViewById(R.id.etmaxtemp);
        e5 = (EditText) findViewById(R.id.etrainfall);
        b1 = (Button) findViewById(R.id.btpredict);
        tv = (TextView) findViewById(R.id.tvpreicted);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humidity = e1.getText().toString();
                holidays = e2.getText().toString();
                mintemp = e3.getText().toString();
                maxtemp = e4.getText().toString();
                rainfall = e5.getText().toString();

                int flg = 0;
                if (humidity.equalsIgnoreCase("")) {
                    e1.setError("Humidity please");
                    e1.setFocusable(true);
                    flg++;
                } else if (Integer.parseInt(humidity) < 0 && Integer.parseInt(humidity) > 100) {
                    e1.setError("Enter humidity greater than 0 and less than 100");
                    e1.setFocusable(true);
                    flg++;
                }

                if (holidays.equalsIgnoreCase("")) {
                    e2.setError("Holidays please");
                    e2.setFocusable(true);
                    flg++;
                } else if (Integer.parseInt(holidays) < 0 && Integer.parseInt(holidays) > 15) {
                    e2.setError("Enter holidays greater than 0 and less than 15");
                    e2.setFocusable(true);
                    flg++;
                }

                if (mintemp.equalsIgnoreCase("")) {
                    e3.setError("MinTemp please");
                    e3.setFocusable(true);
                    flg++;
                } else if (Integer.parseInt(mintemp) < 0 && Integer.parseInt(mintemp) > 40) {
                    e3.setError("Enter Minimum Temp greater than 0 and less than 40");
                    e3.setFocusable(true);
                    flg++;
                }

                if (maxtemp.equalsIgnoreCase("")) {
                    e4.setError("MAX please");
                    e4.setFocusable(true);
                    flg++;
                } else if (Integer.parseInt(maxtemp) < 0 && Integer.parseInt(maxtemp) > 40) {
                    e4.setError("Enter Max Temp greater than 0 and less than 40");
                    e4.setFocusable(true);
                    flg++;
                }

                if (rainfall.equalsIgnoreCase("")) {
                    e5.setError("RainFall please");
                    e5.setFocusable(true);
                    flg++;
                } else if (Integer.parseInt(rainfall) < 0 && Integer.parseInt(rainfall) > 800) {
                    e5.setError("Enter holidays greater than 0 and less than 800");
                    e5.setFocusable(true);
                    flg++;
                }

                if (flg == 0) {
                    try {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) viewpridictions.this;
                        String q = "/checkprediction/?humidity=" + humidity + "&holidays=" + holidays + "&mintemp=" + mintemp + "&maxtemp=" + maxtemp + "&rainfall=" + rainfall;
                        JR.execute(q);
//			            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
                        Log.d("login_log", q);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("result", status);

            if (status.equalsIgnoreCase("success")) {
                results = jo.getString("result");
                ;
                tv.setText("Predicted Result is:" + results);
            } else {
                Toast.makeText(getApplicationContext(), "Not Valid", Toast.LENGTH_LONG).show();
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