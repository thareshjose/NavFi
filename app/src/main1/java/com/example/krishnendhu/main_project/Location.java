package com.example.krishnendhu.main_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

//package com.example.bill.inmap;

public class Location extends AppCompatActivity {
    WebView web_vw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        String start_loc="ctx.arc(470,380,8,0,2*Math.PI);";
        web_vw=(WebView)findViewById(R.id.web_vw);
        String html_s="<html><body>"
                + "<canvas id="+'"'+"myCanvas"+'"'+" width="+'"'+"1508"+'"'+" height="+'"'+"1816"+'"'+" ></canvas>"
                + "<script>"
                + "var canvas = document.getElementById('myCanvas');"
                + "var ctx = canvas.getContext('2d');"
                + "var imageObj = new Image();"
                + "imageObj.onload = function() {"
                + "ctx.drawImage(imageObj, 0, 0);"
                + "ctx.beginPath();"
                ;            //middle point circle

        String html_e="ctx.lineWidth = 7;"
                + "ctx.strokeStyle = 'blue';"
                + "ctx.stroke();"
                + "ctx.beginPath();"
                + start_loc            //start point circle
                + "ctx.fillStyle = 'green';"
                + "ctx.fill();"
                + "ctx.beginPath();"
                + "};"
                + "imageObj.src = 'p1.jpg';"
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
