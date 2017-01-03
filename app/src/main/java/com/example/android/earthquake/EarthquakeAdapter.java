package com.example.android.earthquake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.resource;

/**
 * Created by arpan on 12/23/2016.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNews(getItem(position).getNewsUrl());
            }
        });

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitudeView.setText(getItem(position).getMagnitude());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(getItem(position).getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView placeView = (TextView) listItemView.findViewById(R.id.primary_location);
        placeView.setText(getItem(position).getLocation());
        TextView timeView = (TextView) listItemView.findViewById(R.id.date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM-dd-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(getItem(position).getTime()));
        timeView.setText(dateFormatter.format(calendar.getTime()));

        TextView nearBy = (TextView) listItemView.findViewById(R.id.location_offset);
        nearBy.setText(getItem(position).getDistanceFromLocation());
        // Find the TextView with view ID time
        TextView datetimeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        Date date = new Date(Long.parseLong(getItem(position).getTime()));
        String formattedTime = formatTime(date);
        // Display the time of the current earthquake in that TextView
        datetimeView.setText(formattedTime);
        return listItemView;
    }

    private void openNews(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        getContext().startActivity(i);
    }

    private int getMagnitudeColor(String magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(Double.parseDouble(magnitude));
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
