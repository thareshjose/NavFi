package com.example.krishnendhu.main_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Outdoor extends AppCompatActivity implements LocationListener {


    Intent intent;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TrackGPS gps;
    double longitude;
    double latitude;
    static int locationSetFlag=0;
    static int gpsFlag=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor);
        intent = new Intent(this, MainActivity.class);
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//mobile
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

//wifi
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            // Toast.makeText(getApplicationContext(),"Mobile",Toast.LENGTH_SHORT).show();
        } else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            //Toast.makeText(getApplicationContext(),"Wifi",Toast.LENGTH_SHORT).show();
        } else {
            startActivity(intent);
        }
    }


    public void saveCurrentLocation(View view) {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        else
            gpsFlag=1;

        if(gpsFlag==1) {
            gps = new TrackGPS(Outdoor.this);
            if (gps.canGetLocation()) {

                longitude = gps.getLongitude();
                latitude = gps.getLatitude();

                //Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = Outdoor.this.getSharedPreferences("LatLong", 0);
                SharedPreferences.Editor editor = sharedPref.edit();
                String latLng = "" + Double.toString(latitude) + ", " + Double.toString(longitude);
                editor.putString("coordinate", latLng);
                editor.apply();
                locationSetFlag = 1;
                Snackbar snackbar = Snackbar.make(view, "Parking location has been saved", Snackbar.LENGTH_LONG);

                snackbar.show();
                //Toast.makeText(this, latLng, Toast.LENGTH_LONG).show();
            } else {

                gps.showSettingsAlert();
            }
        }
    }

    public void setManualLocation(View view)
    {
        //9.557828, 76.792183
        String latLng="9.557828, 76.792183";
        SharedPreferences sharedPref= Outdoor.this.getSharedPreferences("LatLong", 0);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString("coordinate", latLng);
        editor.apply();
        locationSetFlag=1;
        Toast.makeText(this, "Location Set"+latLng, Toast.LENGTH_LONG).show();
    }

    public void onLocateCar(View view)
    {
        if(locationSetFlag==1)
        {
            Intent intent = new Intent(Outdoor.this, MapsActivity.class);
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Parking location wasn't saved!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        gpsFlag=1;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        gpsFlag=0;
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + location.getLatitude() + "\nLong: " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
