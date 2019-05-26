package com.example.krishnendhu.main_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.res.Resources;

public class RoutCalculator {
    Resources resources;
    public static String PACKAGE_NAME;
    private Context c;

    public RoutCalculator(Context c) { //for resources
        this.c = c;
        resources = this.c.getResources();
        PACKAGE_NAME = this.c.getPackageName();
    }

    //for circle @ position
    public String rout_position(String point) {

        String point_return = "";
        int poker2 = resources.getIdentifier("p_" + point.trim(), "string", PACKAGE_NAME);
        point_return = "ctx.arc(" + resources.getString(poker2) + ",8,0,2*Math.PI);";
        return point_return;
    }

    //for path generation
    public String rout_path(String start_from, String end_to, String path_map_result) {
        //calling the algorithm
        String return_path = "";  // to be return
        int path_length = path_map_result.length();// length pf data from algorithm
        String temp_string = "";
        String temp_string2 = "";

        for (int i = 1; i < path_length ; i++) {

            if ((path_map_result.charAt(i) == ',') || (path_map_result.charAt(i) == (']'))) {

                String bask3 = temp_string.trim().toString();
                temp_string = "";
                int poker3 = resources.getIdentifier("p_" + bask3.trim(), "string", PACKAGE_NAME);
                return_path += "ctx.lineTo(" + resources.getString(poker3) + ");";


            } else {
                temp_string += path_map_result.charAt(i);
            }
        }
        return return_path;
    }
}

