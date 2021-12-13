package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
    public EarthQuakeAdapter(@NonNull Context context, int resource, @NonNull List<EarthQuake> objects) {
        super(context, resource, objects);
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
        magTextView.setText(earthQuake.getMagnitude());

        TextView cityTextView = listItemView.findViewById(R.id.city_text_view);
        cityTextView.setText(earthQuake.getCity());

        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(earthQuake.getDate());

        return listItemView;
    }
}
