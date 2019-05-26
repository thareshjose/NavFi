package com.example.krishnendhu.main_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class FloorMap extends AppCompatActivity {
    WebView web_vw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map);

        web_vw=(WebView)findViewById(R.id.web_vw);
        String start_loc=getIntent().getStringExtra("start_location").trim();
        String end_loc=getIntent().getStringExtra("end_location").trim();
        String mid_loc=getIntent().getStringExtra("mid_location").trim();

        SharedPreferences sharedPrefe= FloorMap.this.getSharedPreferences("floor", 0);
        SharedPreferences.Editor editor= sharedPrefe.edit();
        editor.putString("start_location", start_loc);
        editor.putString("end_loc", end_loc);
        editor.putString("mid_loc", mid_loc);
        editor.apply();

        String html_s="<html><body>"
                + "<canvas id="+'"'+"myCanvas"+'"'+" width="+'"'+"2300"+'"'+" height="+'"'+"3300"+'"'+" ></canvas>"
                + "<script>"
                + "var canvas = document.getElementById('myCanvas');"
                + "var ctx = canvas.getContext('2d');"
                + "var imageObj = new Image();"
                + "imageObj.onload = function() {"
                + "ctx.drawImage(imageObj, 0, 0);"
                + "ctx.beginPath();"
                + mid_loc;            //middle point circle

        String html_e="ctx.lineWidth = 7;"
                + "ctx.strokeStyle = 'blue';"
                + "ctx.stroke();"
                + "ctx.beginPath();"
                + start_loc            //start point circle
                + "ctx.fillStyle = 'green';"
                + "ctx.fill();"
                + "ctx.beginPath();"
                + end_loc              //end point circle
                + "ctx.fillStyle = 'red';"
                + "ctx.fill();"
               /* + "ctx.beginPath();"// info red
                + "ctx.arc(1057,1095,8,0,2*Math.PI);"
                + "ctx.fillStyle = 'green';"
                + "ctx.fill();"
                + "ctx.beginPath();"// info green
                + "ctx.arc(1057,1130,8,0,2*Math.PI);"
                + "ctx.fillStyle = 'red';"
                + "ctx.fill();"
                + "ctx.beginPath();"// info blue
                + "ctx.arc(1057,1168,8,0,2*Math.PI);"
                + "ctx.fillStyle = 'blue';"
                + "ctx.fill();"*/
                + "};"
                + "imageObj.src = 'floor.jpg';"
                + "</script>"
                + "</body>"
                + "</html> ";

        String html_f= html_s+html_e;

        web_vw.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        web_vw.getSettings().setBuiltInZoomControls(true);
        web_vw.getSettings().setDisplayZoomControls(false);
        web_vw.getSettings().setSupportZoom(true);
        web_vw.getSettings().setJavaScriptEnabled(true);
        web_vw.getSettings().setLoadWithOverviewMode(true);
        web_vw.getSettings().setUseWideViewPort(true);
        web_vw.getSettings().setAppCacheEnabled(true);
        web_vw.getSettings().setAllowFileAccess(true);
        web_vw.loadDataWithBaseURL("file:///android_res/drawable/", html_f.trim(), "text/html", "UTF-8", html_f);
    }

    public void showParkingLot(View v)
    {
        SharedPreferences sharedPref= FloorMap.this.getSharedPreferences("pref", 0);
        String qr = sharedPref.getString("location", "");
        if(qr=="")
        {
             Toast.makeText(this,"Parking location not saved",Toast.LENGTH_LONG).show();
        }
        else
        {
            //Toast.makeText(this, qr, Toast.LENGTH_LONG).show();

            RoutCalculator routCalculator=new RoutCalculator(FloorMap.this);
            String temp_o="108";
            String temp_o2=""+qr;
            String start_position = routCalculator.rout_position(temp_o.trim());//start circle
            String end_position=routCalculator.rout_position(temp_o2);//end circle
            String path_map_result = BellmanFord.mainPark(FloorMap.this,temp_o.trim(),temp_o2);
            String mid_position=routCalculator.rout_path(temp_o.trim(),temp_o2,path_map_result);
            Intent intent=new Intent(FloorMap.this,Location.class);
            intent.putExtra("start_location",start_position);
            intent.putExtra("end_location",end_position);
            intent.putExtra("mid_location",mid_position);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed() {
        SharedPreferences sharedPrefe= FloorMap.this.getSharedPreferences("floor", 0);
        SharedPreferences.Editor editor= sharedPrefe.edit();
        editor.clear();
        editor.apply();
        Intent setIntent = new Intent(this,MainActivity.class);
        setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        finish();
    }
}
