package com.example.krishnendhu.main_project;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button scan_btn,loc_btn,floor_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn=(Button) findViewById(R.id.scan_btn);
        loc_btn=(Button) findViewById(R.id.loc_btn);
        floor_btn=(Button) findViewById(R.id.scan_btn_floor);
        scan_btn.setOnClickListener(new View.OnClickListener() {
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
        });
        floor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this,FloorScan.class);
                startActivity(intent2);
            }
        });
    }


}
