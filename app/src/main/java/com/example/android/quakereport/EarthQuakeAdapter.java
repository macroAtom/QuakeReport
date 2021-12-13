package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list,parent,false);
        }

        EarthQuake earthQuake = getItem(position);

        TextView magTextView = listItemView.findViewById(R.id.mag_text_view);


        String formattedMagnitude = formatMagnitude(earthQuake.getMagnitude());

        magTextView.setText(formattedMagnitude);


        String originalLocation = earthQuake.getLocation();


//        (detailLocation.indexOf(" of ") == -1)

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
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
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_text_view);
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

    private String formatMagnitude(Double magnitude){
        /**
         * 创建格式化对象
         */
        DecimalFormat formatter = new DecimalFormat("0.0");

        /**
         * 通过格式化对象，将数据进行格式化，格式化震级
         */

        return formatter.format(magnitude);
    }
}
