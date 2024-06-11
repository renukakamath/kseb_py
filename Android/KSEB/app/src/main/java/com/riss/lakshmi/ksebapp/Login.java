package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Login extends AppCompatActivity implements JsonResponse {

    EditText ed_uname, ed_pass;
    Button bt_login;

    public static String logid = "0";

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!sh.getString("uid", "0").equals("0")) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        ed_uname = (EditText) findViewById(R.id.ed_uname);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_uname.getText().toString();
                String password = ed_pass.getText().toString();

                int flg = 0;
                if (username.equalsIgnoreCase("")) {
                    ed_uname.setError("Username please");
                    ed_uname.setFocusable(true);
                    flg++;
                }
                if (password.equalsIgnoreCase("")) {
                    ed_pass.setError("Password please");
                    ed_pass.setFocusable(true);
                    flg++;
                }

                if (flg == 0) {
                    try {
                        JsonReq JR=new JsonReq();
                        JR.json_response=(JsonResponse) Login.this;
                        String q = "/login/?username=" + username + "&password=" + password;
                        JR.execute(q);
//			        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
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
        // TODO Auto-generated method stub
        try {
            String status=jo.getString("status");
            Log.d("result", status);

            if(status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("result");
                logid = ja.getJSONObject(0).getString("cons_id");
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("uid", logid);
                ed.commit();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), IpSetting.class));
    }
}
