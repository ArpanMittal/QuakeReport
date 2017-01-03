package com.example.android.earthquake;

import java.io.Serializable;
import java.net.URL;
import java.text.DecimalFormat;

import static com.example.android.earthquake.R.id.magnitude;

/**
 * Created by arpan on 12/21/2016.
 */
public class Earthquake implements Serializable {

    private String magnitude;
    private String location;
    private String time;
    private String distanceFrom;

    public String getNewsUrl() {
        return newsUrl;
    }

    private String newsUrl;


    public Earthquake(float magnitude, String location, String nearBy, String time, String url){
        this.magnitude = String.valueOf(magnitude);
        //String [] parts = location.split("of");
        this.newsUrl = url;
        this.distanceFrom = nearBy;
        this.location = location;
        this.time =  time;
        this.newsUrl = url;

    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "magnitude=" + magnitude +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getMagnitude() {

        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDistanceFromLocation(){
        return distanceFrom;
    }

    public String getTime() {
        return time;
    }
}
