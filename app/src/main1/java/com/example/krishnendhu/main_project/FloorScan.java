package com.example.krishnendhu.main_project;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class FloorScan extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_scan);

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 700)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {

            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(FloorScan.this,
                                new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch(IOException e) {
                    e.printStackTrace();
                }

            }
            public void surfaceChanged(SurfaceHolder surfaceHolder,int i,int i1,int i2){

            }
            public void surfaceDestroyed(SurfaceHolder surfaceHolder){

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            public void release(){

            }
            public  void receiveDetections(Detector.Detections<Barcode> detections){
                final SparseArray<Barcode> qrcodes= detections.getDetectedItems();
                if (qrcodes.size()!=0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //vibrator.vibrate(300);
                            //txtResult.setText(qrcodes.valueAt(0).displayValue);
                            RoutCalculator routCalculator=new RoutCalculator(FloorScan.this);
                            String temp_o="1";
                            String temp_o2="3";
                            String start_position = routCalculator.rout_position(temp_o.trim());//start circle
                            String end_position=routCalculator.rout_position(temp_o2);//end circle

                            Dijkstra path_map = new Dijkstra(FloorScan.this);
                            String path_map_result = path_map.newMain(temp_o.trim(),temp_o2);

                            String mid_position=routCalculator.rout_position_move_to(temp_o.trim())+//start position
                                    routCalculator.rout_path(temp_o.trim(),temp_o2,path_map_result)//path generation
                                    +routCalculator.rout_position_line_to(temp_o2);//end position

                            Intent intent=new Intent(FloorScan.this, FloorMap.class);
                            intent.putExtra("start_location",start_position);
                            intent.putExtra("end_location",end_position);
                            intent.putExtra("mid_location",mid_position);
                            startActivity(intent);

                            //Intent intent=new Intent(FloorScan.this,FloorMap.class);
                           // startActivity(intent);
                        }
                    });
                }
            }

        });
    }
}