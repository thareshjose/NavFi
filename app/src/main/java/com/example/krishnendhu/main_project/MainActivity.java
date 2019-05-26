package com.example.krishnendhu.main_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity{

    private Button scan_btn,loc_btn,floor_btn,done;
    private IntentIntegrator qrScan;
    private int flag=0;
    public SharedPreferences sharedPref;
    private int internetConnectedFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn=(Button) findViewById(R.id.scan_btn);
        //loc_btn=(Button) findViewById(R.id.loc_btn);
        floor_btn=(Button) findViewById(R.id.scan_btn_floor);
        //done=(Button) findViewById(R.id.button4);
        qrScan = new IntentIntegrator(this);
    }
    //Outdoor services
    public void outdoorServices(View v)
    {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //mobile
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        //wifi
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            internetConnectedFlag=1;
        } else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            internetConnectedFlag=1;
        } else {
            //Toast.makeText(getApplicationContext(),"NO",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // set title
            alertDialogBuilder.setTitle("No Internet Connection");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Enable Internet/Wifi and try again")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //enable wifi
                            dialog.dismiss();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        if(internetConnectedFlag==1)
        {
            internetConnectedFlag=0;
            Intent intent=new Intent(MainActivity.this,Outdoor.class);
            startActivity(intent);
        }
    }

    public void deleteSavedLoc(View v)
    {
        SharedPreferences sharedPref= getSharedPreferences("pref",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        sharedPref= MainActivity.this.getSharedPreferences("LatLong",0);
        editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        Outdoor.locationSetFlag=0;
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "All saved locations deleted", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
        //Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
    }

    public void onScanParkClick(View v){
        flag=1;
        qrScan.setPrompt("Scan QR code");
        qrScan.setBeepEnabled(false);
        qrScan.setBarcodeImageEnabled(true);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }

    public void onScanClick(View v){
        flag=2;
        qrScan.setPrompt("Scan QR code");
        qrScan.setBeepEnabled(false);
        qrScan.setBarcodeImageEnabled(true);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                String qr_content=result.getContents();
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                if(flag==2) {
                    int numb=Integer.parseInt(qr_content);
                    if(numb>=1 && numb<=48)
                    { floorscan(qr_content); }
                    else {
                        Toast.makeText(this, "Invalid QR code", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                    int numb=Integer.parseInt(qr_content);
                        if(numb>=101 && numb<=155)
                            saveParkingLocation(qr_content);
                        else {
                            Toast.makeText(this, "Invalid QR code", Toast.LENGTH_LONG).show();
                        }

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void saveParkingLocation(String qr_content)
    {
        flag=0;
        // Create object of SharedPreferences.
        SharedPreferences sharedPref= MainActivity.this.getSharedPreferences("pref", 0);
        //now get Editor
        SharedPreferences.Editor editor= sharedPref.edit();
        //put your value
        editor.putString("location", qr_content);
        //commits your edits
        editor.apply();
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "Parking spot location has been saved", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
        //Toast.makeText(this, "Parking Location Saved", Toast.LENGTH_LONG).show();
    }
    public void floorscan(String qr_content){
        flag=0;
        RoutCalculator routCalculator=new RoutCalculator(MainActivity.this);
        String temp_o=""+qr_content;
        String temp_o2="10";
        String start_position = routCalculator.rout_position(temp_o.trim());//start circle
        String end_position=routCalculator.rout_position(temp_o2);//end circle

        String path_map_result = BellmanFord.mainMethod(MainActivity.this,temp_o.trim(),temp_o2);

        String mid_position=routCalculator.rout_path(temp_o.trim(),temp_o2,path_map_result);

        Intent intent=new Intent(MainActivity.this, FloorMap.class);
        intent.putExtra("start_location",start_position);
        intent.putExtra("end_location",end_position);
        intent.putExtra("mid_location",mid_position);
        startActivity(intent);
    }
}
