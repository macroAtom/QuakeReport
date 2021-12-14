/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        ArrayList<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
//
//        earthquakes.add(new EarthQuake("7.2","San Francisco","Feb 2, 2016"));
//        earthquakes.add(new EarthQuake("6.1","London","July 20,2015"));
//        earthquakes.add(new EarthQuake("3.9","Tokyo","Nov 10,2014"));
//        earthquakes.add(new EarthQuake("5.4","Mexico City","May 3,2014"));
//        earthquakes.add(new EarthQuake("2.8","Moscow","Jan 31,2013"));
//        earthquakes.add(new EarthQuake("4.9","Rio de Janeiro","Aug 19,2012"));
//        earthquakes.add(new EarthQuake("1.6","Paris","Oct 30,2011"));

        ArrayList<EarthQuake> earthquakes = QueryUtils.extractEarthquakes();


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuakeAdapter adapter = new EarthQuakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 通过数组列表获取地震对象
//                EarthQuake earthQuake = earthquakes.get(position);

//                或者按照answer中的示例获取对象
                EarthQuake earthQuake = adapter.getItem(position);
                // 通过地震对象数据获取发生地震的详情页面的url
                String url = earthQuake.getEventUrl();

                /**
                 * 通过intent 向web 浏览器传递信息 并打开事件发生的详情页面
                 */
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });
    }
}