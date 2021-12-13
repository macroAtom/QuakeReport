package com.example.android.quakereport;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {


    private String locationOffset;
    private String primaryLocation;
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthQuakeAdapter(@NonNull Context context, int resource, @NonNull List<EarthQuake> objects) {
        super(context, resource, objects);
    }


    public EarthQuakeAdapter(Context context, List<EarthQuake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;


        /**
         * 这里检查是否可以使用回收视图，如果不能，我们使用earthquake_list xml 中定义的新列表项布局
         * Android basic Network Lesson 1:JSON Parsing, 11 show more info on each earthquake,answer 3:03
         */

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        EarthQuake earthQuake = getItem(position);

        /**
         * Magnitude View
         */
        TextView magTextView = listItemView.findViewById(R.id.magnitude);

        String formattedMagnitude = formatMagnitude(earthQuake.getMagnitude());

        magTextView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthQuake.getMagnitude());

        // Set the color on the magnitude circle

//        ContextCompat.getColor(getContext(),R.color.magnitude7);

        magnitudeCircle.setColor(magnitudeColor);



        /**
         * 位置view
         */

        String originalLocation = earthQuake.getLocation();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        // 设置地震位置文本

        TextView addressTextView = listItemView.findViewById(R.id.location_offset);
        addressTextView.setText(locationOffset);


        TextView cityTextView = listItemView.findViewById(R.id.primary_location);
        cityTextView.setText(primaryLocation);

//        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
//        dateTextView.setText(earthQuake.getDate());

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(earthQuake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        // 设置日期格式，并填充到layout布局中
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);


        // 设置时间格式，并填充到layout布局中
        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedTime = formatTime(dateObject);
        // Display the date of the current earthquake in that TextView
        timeView.setText(formattedTime);


        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(Double magnitude) {
        /**
         * 创建格式化对象
         */
        DecimalFormat formatter = new DecimalFormat("0.0");

        /**
         * 通过格式化对象，将数据进行格式化，格式化震级
         */

        return formatter.format(magnitude);
    }

//    getMagnitudeColor(double magnitude)
//  设置震级颜色
    private int getMagnitudeColor(Double magnitude){
        int magnitudeColorResourceId;

//        int maga = magnitude.intValue();


        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
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



}
