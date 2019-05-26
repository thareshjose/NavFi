package com.example.krishnendhu.main_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import java.util.Scanner;

//package com.example.bill.inmap;

public class Location extends AppCompatActivity {
    WebView web_vw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        web_vw=(WebView)findViewById(R.id.web_vw);
        String start_loc=getIntent().getStringExtra("start_location").trim();
        String end_loc=getIntent().getStringExtra("end_location").trim();
        String mid_loc=getIntent().getStringExtra("mid_location").trim();

        String html_s="<html><body>"
                + "<canvas id="+'"'+"myCanvas"+'"'+" width="+'"'+"3008"+'"'+" height="+'"'+"3516"+'"'+" ></canvas>"
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
                + "ctx.beginPath();"// info red
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
                + "ctx.fill();"
                + "};"
                + "imageObj.src = 'floor1.jpg';"
                + "</script>"
              /*  +"<p style="
                +'"'+"margin-left:1060px;margin-top:-1095px;"+'"'+">aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</p><p style="
                +'"'+"margin-left:1060px;margin-top:-1130px;"+'"'+">bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb</p><p style="
                +'"'+"margin-left:1060px;margin-top:-1168px;"+'"'+">cccccccccccccccccccccccccccccccccccc</p>"*/
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
    public void showFloor(View v)
    {
        SharedPreferences sharedPrefe= Location.this.getSharedPreferences("floor", 0);
        SharedPreferences.Editor editor= sharedPrefe.edit();
        String start_location = sharedPrefe.getString("start_location", "");
        String end_loc = sharedPrefe.getString("end_loc", "");
        String mid_loc = sharedPrefe.getString("mid_loc", "");
        Intent setIntent = new Intent(this,FloorMap.class);
        //setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        setIntent.putExtra("start_location",start_location);
        setIntent.putExtra("end_location",end_loc);
        setIntent.putExtra("mid_location",mid_loc);
        editor.clear();
        editor.apply();
        startActivity(setIntent);
    }
    /*@Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this,FloorMap.class);
        //setIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        //finish();
    }*/
}