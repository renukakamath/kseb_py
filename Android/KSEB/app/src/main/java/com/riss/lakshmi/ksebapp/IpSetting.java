package com.riss.lakshmi.ksebapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IpSetting extends AppCompatActivity {

    EditText ed_ip;
    Button bt_ip;

    public static String ipVal = "";

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_setting);

        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {}

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ed_ip = (EditText) findViewById(R.id.ed_ip);
        bt_ip = (Button) findViewById(R.id.bt_ip);

        ed_ip.setText(sh.getString("ip", "192.168.1.1"));

        bt_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ipVal = ed_ip.getText().toString();
                if (ipVal.equalsIgnoreCase("")) {
                    ed_ip.setError("IP address please");
                    ed_ip.setFocusable(true);
                } else {
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ipVal);
                    ed.commit();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });
    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Are you sure you want to exit..?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }
}