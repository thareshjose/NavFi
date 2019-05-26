package com.example.krishnendhu.main_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity{

    private Button scan_btn,loc_btn,floor_btn,done;
    private IntentIntegrator qrScan;
    private int flag=0;
    public SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn=(Button) findViewById(R.id.scan_btn);
        loc_btn=(Button) findViewById(R.id.loc_btn);
        floor_btn=(Button) findViewById(R.id.scan_btn_floor);
        done=(Button) findViewById(R.id.button4);
        qrScan = new IntentIntegrator(this);

/*         sharedPref= MainActivity.this.getSharedPreferences("pref", 0);
        String qr = sharedPref.getString("location", "");
        Toast.makeText(this, qr, Toast.LENGTH_LONG).show();*/

        /*scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,scanner.class);
                startActivity(i);
            }
        });
        loc_btn.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){
                Intent intent=new Intent(MainActivity.this,Location.class);
                startActivity(intent);
            }
        });*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this,New.class);
                startActivity(intent2);
            }
        });
    }
    public void deleteSavedLoc(View v)
    {
        SharedPreferences sharedPref= getSharedPreferences("pref",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();      //its clear all data.
        editor.apply();  //Don't forgot to commit  SharedPreferences.
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
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                String qr_content=result.getContents();
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                if(flag==2)
                    floorscan(qr_content);
                else
                    saveParkingLocation(qr_content);

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
        Toast.makeText(this, qr_content, Toast.LENGTH_LONG).show();
    }
    public void floorscan(String qr_content){
        flag=0;
        RoutCalculator routCalculator=new RoutCalculator(MainActivity.this);
        String temp_o=""+qr_content;
        //txtResult.setText(temp_o);
        String temp_o2="10";
        //temp_o="1";
        //System.out.println(temp_o);
        //Toast.makeText(Floor_scan.this, (String)temp_o,Toast.LENGTH_LONG).show();
        String start_position = routCalculator.rout_position(temp_o.trim());//start circle
        String end_position=routCalculator.rout_position(temp_o2);//end circle

        //BellmanFord path_map = new BellmanFord(Floor_scan.this);
        //BellmanFord path_map = new BellmanFord(Floor_scan.this,temp_o.trim(),temp_o2);
        String path_map_result = BellmanFord.mainMethod(MainActivity.this,temp_o.trim(),temp_o2);

        /*String mid_position=routCalculator.rout_position_move_to(temp_o.trim())+//start position
                routCalculator.rout_path(temp_o.trim(),temp_o2,path_map_result)//path generation
                +routCalculator.rout_position_line_to(temp_o2);//end position*/

        String mid_position=routCalculator.rout_path(temp_o.trim(),temp_o2,path_map_result);

        Intent intent=new Intent(MainActivity.this, FloorMap.class);
        intent.putExtra("start_location",start_position);
        intent.putExtra("end_location",end_position);
        intent.putExtra("mid_location",mid_position);
        startActivity(intent);
    }

}
