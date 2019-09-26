package com.example.sosmap;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    Map<Location,String> locations = new HashMap<>();
    static private DataBase inst;
    private DataBase(){}
    public static DataBase getInstance() {
        if(inst==null){
            inst=new DataBase();
        }
        return inst;
    }
    public void  addLocation(Location loc, String s){
        locations.put(loc,s);
    }
    public  Map<Location,String> getLocations(){
        return locations;
    }

}
