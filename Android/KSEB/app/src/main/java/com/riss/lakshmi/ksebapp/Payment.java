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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Payment extends AppCompatActivity implements JsonResponse {

    String bill_id = "0", amount = "0";
    SharedPreferences sh;

    TextView tv_amount, tv_details;
    EditText ed_number, ed_cvv;
    Button bt_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bill_id = History.bill_id;
        amount = History.amount;

        ed_number= (EditText) findViewById(R.id.ed_card_number);
        ed_cvv = (EditText) findViewById(R.id.ed_card_cvv);
        tv_amount = (TextView) findViewById(R.id.tv_pay_amount);
        tv_details = (TextView) findViewById(R.id.tv_pay_details);
        bt_pay = (Button) findViewById(R.id.bt_pay_pay);

        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = ed_number.getText().toString();
                String cvv = ed_cvv.getText().toString();
                int pflg = 0;
                if (number.equals("") || number.length() != 16) {
                    pflg++;
                    ed_number.setError("Enter vaid number");
                }
                if (cvv.equals("") || cvv.length() != 3) {
                    pflg++;
                    ed_cvv.setError("valid CVV please");
                }
                if (pflg == 0) {
                    try {
                        JsonReq JR=new JsonReq();
                        JR.json_response=(JsonResponse) Payment.this;
                        String q = "/payment/?cons_id=" + sh.getString("uid", "0") + "&bill_id=" + bill_id + "&amount=" + amount;
                        JR.execute(q);
//                        Toast.makeText(getApplicationContext(), q, Toast.LENGTH_SHORT).show();
                        Log.d("login_log", q);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        tv_amount.setText(amount);
        tv_details.setText("Bill no : " + bill_id);
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try {
            String status=jo.getString("status");
            Log.d("result", status);

            if(status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Success.!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed.TRY AGAIN..!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), History.class));
    }
}