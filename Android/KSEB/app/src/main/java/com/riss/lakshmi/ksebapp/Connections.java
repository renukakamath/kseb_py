package com.riss.lakshmi.ksebapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Connections extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_my_connections;
    SharedPreferences sh;
    String[] con_req_ids, addresses;
    public static String con_req_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv_my_connections = (ListView) findViewById(R.id.lv_my_connections);
        lv_my_connections.setOnItemClickListener(this);

        try {
            String namespace = "http://dbcon/";
            String method = "get_my_connections";
            SoapObject soap=new SoapObject(namespace, method);
            soap.addProperty("cons_id", sh.getString("uid", "0"));

            SoapSerializationEnvelope senv=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            senv.setOutputSoapObject(soap);

            HttpTransportSE htp=new HttpTransportSE(sh.getString("url", ""));
            htp.call(namespace + method, senv);

            String result=senv.getResponse().toString();
            if (!result.equals("na")) {
                String[] temp = result.split("\\^");
                if (temp.length > 0) {
                    con_req_ids = new String[temp.length];
                    addresses = new String[temp.length];
                    for (int i = 0; i < temp.length; i++) {
                        String[] tempee = temp[i].split("\\#");
                        con_req_ids[i] = tempee[0];
                        addresses[i] = tempee[1];
                    }

                    lv_my_connections.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, addresses));
                }
            } else {
                Toast.makeText(getApplicationContext(), "Nothing to show you.!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        con_req_id = con_req_ids[position];
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Home.class));
    }
}
