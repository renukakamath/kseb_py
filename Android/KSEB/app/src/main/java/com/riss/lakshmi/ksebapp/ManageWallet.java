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

public class ManageWallet extends AppCompatActivity implements JsonResponse {
    EditText e1,e3;
    Button b1;
    String s;
    String[] amount,type,date;
    ListView l1;
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_wallet);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        e1=(EditText)findViewById(R.id.editText);
        e3=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button);
        l1=(ListView)findViewById(R.id.listview);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) ManageWallet.this;
        String q = "/viewwallet/?cons_id=" + sh.getString("uid", "0");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s=e1.getText().toString();

                JsonReq JR=new JsonReq();
                JR.json_response=(JsonResponse) ManageWallet.this;
                String q = "/addwallet/?cons_id=" + sh.getString("uid", "0")+"&amount="+s+"&type=credit";
                JR.execute(q);
            }
        });




    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("result", method);

            if (method.equalsIgnoreCase("addwallet")) {


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
            if (method.equalsIgnoreCase("viewwallet")) {

                try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        e3.setText("Your Current Balance is :"+jo.getString("val"));
                        JSONArray ja = (JSONArray) jo.getJSONArray("data");

                        if (ja.length() > 0) {
                            amount = new String[ja.length()];
                            type = new String[ja.length()];
                            date = new String[ja.length()];
                            String[] details = new String[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                amount[i] = ja.getJSONObject(i).getString("transaction_amount");
                                type[i] = ja.getJSONObject(i).getString("transaction_type");
                                date[i] = ja.getJSONObject(i).getString("date");
                                details[i] = "Amount : " + amount[i] + "\nType : " + type[i] + "\nDate : " + date[i] + "\n";
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
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
