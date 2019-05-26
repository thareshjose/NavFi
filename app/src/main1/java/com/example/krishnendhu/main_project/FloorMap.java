package com.example.krishnendhu.main_project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

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
        //<p style="margin-left:500px;margin-top:200px;">Akhil Ashok</p>
        String html_s="<html><body>"
                + "<canvas id="+'"'+"myCanvas"+'"'+" width="+'"'+"2508"+'"'+" height="+'"'+"2816"+'"'+" ></canvas>"
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
                + "imageObj.src = 'floor.jpg';"
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
}
