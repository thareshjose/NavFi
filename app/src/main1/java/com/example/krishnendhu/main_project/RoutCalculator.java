package com.example.krishnendhu.main_project;

/**
 * Created by tharesh on 09-Apr-17.
 */

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

    //for initial position
    public String rout_position_move_to(String point) {

        String rout_move_to = "";
        int poker2 = resources.getIdentifier("p_" + point.trim(), "string", PACKAGE_NAME);
        rout_move_to = "ctx.moveTo(" + resources.getString(poker2) + ");";

        return rout_move_to;
    }

    //for final position
    public String rout_position_line_to(String point) {
        String rout_line_to = "";
        int poker2 = resources.getIdentifier("p_" + point.trim(), "string", PACKAGE_NAME);
        rout_line_to = "ctx.lineTo(" + resources.getString(poker2) + ");";
        return rout_line_to;
    }

    //for path generation
    public String rout_path(String start_from, String end_to, String path_map_result) {
        //calling the dijkstra's algorithm
        String return_path = "";  // to be return
        int path_length = path_map_result.length();// length pf data from algorithm
        String temp_string = "";
        String temp_string2 = "";

        for (int i = 1; i < path_length - 1; i++) {

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

