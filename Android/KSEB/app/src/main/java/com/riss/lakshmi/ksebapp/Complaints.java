package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Complaints extends AppCompatActivity implements JsonResponse {

    EditText e1;
    Button b1;
    ListView l1;
    SharedPreferences sh;

    String[] complaintid,complaint,date,statuss,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        e1=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button2);
        l1=(ListView) findViewById(R.id.listView1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Complaints.this;
        String q = "/viewcomplaints/?cons_id=" + sh.getString("uid", "0");
            JR.execute(q);
//            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
        Log.d("notification_log", q);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val=e1.getText().toString();

                if (val.equalsIgnoreCase("")) {
                    e1.setError("enter complaints");
                    e1.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Complaints.this;
                    String q = "/complaints/?cons_id=" + sh.getString("uid", "0") + "&complaint=" + val;
                    JR.execute(q);
//            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
                    Log.d("notification_log", q);
                }
            }
        });
    }

    @Override
    public void response(JSONObject jo) {

        try {
            String method = jo.getString("method");
            Log.d("result", method);

            if (method.equalsIgnoreCase("complaints")) {


                try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "Success.!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed.TRY AGAIN..!!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
            if (method.equalsIgnoreCase("viewcomplaints")) {
                try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        JSONArray ja = (JSONArray) jo.getJSONArray("data");
                        if (ja.length() > 0) {
                            complaintid = new String[ja.length()];
                            complaint = new String[ja.length()];
                            date = new String[ja.length()];
                            statuss = new String[ja.length()];
                            details = new String[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                complaintid[i] = ja.getJSONObject(i).getString("comp_id");
                                complaint[i] = ja.getJSONObject(i).getString("complaints");
                                date[i] = ja.getJSONObject(i).getString("date");
                                statuss[i] = ja.getJSONObject(i).getString("status");
                                details[i] = "Complaint : " + complaint[i] + "\nDate : " + date[i] + "\nstatus : " + statuss[i] + "\n";
                            }
                            l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.cust_list, details));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No bills available..!!", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
