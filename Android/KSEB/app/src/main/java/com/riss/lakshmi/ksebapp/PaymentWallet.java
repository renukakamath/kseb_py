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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class PaymentWallet extends AppCompatActivity implements  JsonResponse {
    EditText e1,e2;
    Button b1;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_wallet);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.editText4);
        e2=(EditText)findViewById(R.id.editText5);
        b1=(Button)findViewById(R.id.button3);

        e2.setText(History.amount);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) PaymentWallet.this;
        String q = "/viewbalance/?cons_id=" + sh.getString("uid", "0");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double camount=Double.parseDouble(e1.getText().toString());
                Double damount=Double.parseDouble(e2.getText().toString());
                if(camount>damount) {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) PaymentWallet.this;
                    String q = "/walletpayment/?cons_id=" + sh.getString("uid", "0") + "&amount=" + History.amount+"&bill_id="+History.bill_id;
                    JR.execute(q);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Apt Amount To Dedect",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),History.class));
                }

            }
        });







    }

    @Override
    public void response(JSONObject jo) {


        try {
            String method = jo.getString("method");
            Log.d("result", method);

            if (method.equalsIgnoreCase("walletpayment")) {


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
            if (method.equalsIgnoreCase("viewbalance")) {

                try {
                    String status = jo.getString("status");
                    Log.d("result", status);

                    if (status.equalsIgnoreCase("success")) {
                        e1.setText(jo.getString("val"));
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
