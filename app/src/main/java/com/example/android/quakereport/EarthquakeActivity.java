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

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
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


//        String strJson="{\"Employee\" :[{\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"},{\"id\":\"02\",\"name\":\"Sairamkrishna\",\"salary\":\"500000\"},{\"id\":\"03\",\"name\":\"Sathish kallakuri\",\"salary\":\"600000\"}]}";
//
//        String data = "";
//        try {
//            JSONObject jsonRootObject = new JSONObject(strJson);
//
//            //Get the instance of JSONArray that contains JSONObjects
//            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");
//
//            //Iterate the jsonArray and print the info of JSONObjects
//            for(int i=0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                int id = Integer.parseInt(jsonObject.optString("id").toString());
//
//                int id_simple = jsonObject.optInt("id");
//
//                String name = jsonObject.optString("name").toString();
//
//                String name_simple = jsonObject.optString("name");
//
//                float salary = Float.parseFloat(jsonObject.optString("salary").toString());
//
//                int salary_simple = jsonObject.getInt("salary");
//
//                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n";
//            }
//        } catch (JSONException e ) {
//            e.printStackTrace();
//        }


        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuakeAdapter adapter = new EarthQuakeAdapter(
                this, R.layout.earthquake_list, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}