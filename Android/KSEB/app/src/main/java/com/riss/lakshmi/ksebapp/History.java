package com.riss.lakshmi.ksebapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class History extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView lv;
    SharedPreferences sh;
    String[] bill_ids, amounts, bill_dates, pay_statuss,used;
    public static String bill_id,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv = (ListView) findViewById(R.id.lv_history);
        lv.setOnItemClickListener(this);

        getDetails();
    }

    void getDetails() {
        try {
            JsonReq JR=new JsonReq();
            JR.json_response=(JsonResponse) History.this;
            String q = "/get_my_bills/?cons_id=" + sh.getString("uid", "0");
            JR.execute(q);
//            Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
            Log.d("login_log", q);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        bill_id=bill_ids[position];
        amount=amounts[position];


        if (!pay_statuss[position].equals("pending")) {
            Toast.makeText(getApplicationContext(), "You have paid this bill.!", Toast.LENGTH_LONG).show();
        } else {
            final CharSequence[] items = {"Card Payment", "Wallet Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
            builder.setTitle("Select Any!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Card Payment"))
                    {
                        startActivity(new Intent(getApplicationContext(),Payment.class));

                    } else if (items[item].equals("Wallet Payment")) {

                        startActivity(new Intent(getApplicationContext(),PaymentWallet.class));

                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        }
    }

    void inItArray(int len) {
        bill_ids = new String[len];
        amounts = new String[len];
        bill_dates = new String[len];
        pay_statuss = new String[len];
        used = new String[len];
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
                    inItArray(ja.length());
                    String[] details = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        bill_ids[i] = ja.getJSONObject(i).getString("bill_id");
                        amounts[i] = ja.getJSONObject(i).getString("amount");
                        bill_dates[i] = ja.getJSONObject(i).getString("bill_date");
                        used[i] = ja.getJSONObject(i).getString("usage");
                        pay_statuss[i] = ja.getJSONObject(i).getString("pay_status");
                        details[i] ="Date :"+ bill_dates[i] +"\nUsed :"+ used[i] + "\nAmount : " + amounts[i] + "\n";
                    }
                    lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list, details));
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