package com.riss.lakshmi.ksebapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    SharedPreferences sh;
    Button bt_scan, bt_history, bt_notification,bt_wallet, bt_logout,bttariff,btcomplaint,btwallet;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bt_scan = (Button) findViewById(R.id.bt_scan);
        bt_history = (Button) findViewById(R.id.bt_history);
        bt_notification = (Button) findViewById(R.id.bt_notification);
        bt_logout = (Button) findViewById(R.id.bt_logout);
        bt_wallet = (Button) findViewById(R.id.bt_wallet);
        bttariff= (Button) findViewById(R.id.bttariff);
        btcomplaint = (Button) findViewById(R.id.btcompalint);

        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Connections.class));
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(Home.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }

            }
        });

        bt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
            }
        });

        bt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ManageWallet.class));
            }
        });

        bttariff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewTariff.class));
            }
        });
        bt_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Notification.class));
            }
        });
        btcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Complaints.class));
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("uid", "0");
                ed.commit();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }


    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);

        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                try {

//                    JsonReq jr= new JsonReq();
//                    jr.json_response=(JsonResponse) Home.this;
//                    String q="/checkuser/?id="+contents;
//
//                    q.replace("", "%20");pvy186
//                    jr.execute(q);
                    if(contents.equals(sh.getString("uid","")))
                    {
                        Toast.makeText(getApplicationContext(),"Consumer is Verified",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Not Apt consumer",Toast.LENGTH_LONG).show();
                    }

//			}
                } catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Exception : "+e, Toast.LENGTH_SHORT).show();
                }



//				Intent i=new Intent(getApplicationContext(), Virtual.class);
//				i.putExtra("imageID",contents);
//				startActivity(i);



            }
        }
    }
    @Override
    public void onBackPressed() {
		/*Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);*/
        Intent I= new Intent(getApplicationContext(),Home.class);
        startActivity(I);
    }




}
