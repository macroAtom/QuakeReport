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
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
//    ArrayList<EarthQuake> earthquakes;
    EarthQuakeAdapter mAdapter;

    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // earthquakes = QueryUtils.extractEarthquakes();
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        //mAdapter = new EarthQuakeAdapter(this, earthquakes);

        List<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
        mAdapter = new EarthQuakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ????????????????????????????????????
                //EarthQuake earthQuake = earthquakes.get(position);
                //????????????answer????????????????????????
                EarthQuake earthQuake = mAdapter.getItem(position);
                // ????????????????????????????????????????????????????????????url
                String url = earthQuake.getEventUrl();

                /**
                 * ??????intent ???web ????????????????????? ????????????????????????????????????
                 */
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            }
        });

        /**
         * ?????????????????????background thread.
         */
        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAsyncTask earthQuakeAsyn = new EarthquakeAsyncTask();
        earthQuakeAsyn.execute(USGS_REQUEST_URL);
    }


    /**
     * ????????????????????????????????????????????????
     */
//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, String> {
//
////        ArrayList<EarthQuake> earthquake;
//
//        String jsonEarthquke;
////        /**
////         * ????????????1
////         */
////        @Override
////        protected EarthQuake doInBackground(String... urls) {
////            // Perform the HTTP request for earthquake data and process the response.
////
////            if (urls.length < 1 || urls[0] == null) {
////                return null;
////            }
////
////            earthquake = Utils.fetchEarthquakeData(urls[0]);
////            // Update the information displayed to the user.
////
////            return earthquake;
////        }
////        @Override
////        protected void onPostExecute(EarthQuake event) {
//////            super.onPostExecute(event);
////            if(event == null){
////                return;
////            }
////            updateUi(event);
////        }
//        @Override
//        protected String doInBackground(String... urls) {
//            // Perform the HTTP request for earthquake data and process the response.
////            earthquake = Utils.fetchEarthquakeData(USGS_REQUEST_URL);
//            // Update the information displayed to the user.
//
//            if(urls.length<1 || urls[0] == null){
//                return null;
//            }
//
//            HttpURLConnection urlConnection = null;
//            InputStream inputStream = null;
//
//            try {
//                /**
//                 * ??????URL ??????
//                 */
////                URL url = new URL(urls[0]);
//
//                URL url = new URL(urls[0]);
//
//                /**
//                 * ??????HttpURLConnect ????????????http??????
//                 */
//                urlConnection = (HttpURLConnection) url.openConnection();
//
//                /**
//                 * ????????????????????????????????????GET
//                 */
//                urlConnection.setRequestMethod("GET");
//
//                /**
//                 * ?????????????????????10m??????????????????15s
//                 */
//                urlConnection.setReadTimeout(10000);
//                urlConnection.setConnectTimeout(15000);
//
//                /**
//                 * ??????????????????
//                 */
//                urlConnection.connect();
//
//                /**
//                 * ??????????????????
//                 */
//                if (urlConnection.getResponseCode() == 200) {
//                    /**
//                     * ????????????????????????????????????
//                     */
//                    inputStream = urlConnection.getInputStream();
//
//                    /**
//                     * ???????????????
//                     */
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//
//                    /**
//                     * ???????????????
//                     */
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                    /**
//                     * ????????????????????????jsonEarthquake ???
//                     */
//                    String line = bufferedReader.readLine();
//
//                    StringBuilder output = new StringBuilder();
//                    while (line != null) {
//                        output = output.append(line);
//                        line = bufferedReader.readLine();
//                    }
//
////                    earthquake = extractFeatureFromJson(output.toString());
//                    jsonEarthquke = output.toString();
//                    Log.i(LOG_TAG, "doInBackground: "+jsonEarthquke);
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                /**
//                 * ??????????????????????????????inputStream
//                 */
//
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//
//                if(inputStream != null){
//                    try {
//                        inputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return jsonEarthquke;
//        }
//
//
//        @Override
//        protected void onPostExecute(String jsonEarthquke) {
////            super.onPostExecute(event);
//            Log.i(LOG_TAG, "onPostExecute: "+jsonEarthquke);
//            if (jsonEarthquke == null){
//                return;
//            }
//
////            updateUi(event);
//
//
//            /**
//             * ??????earthquake ??????
//             */
//
//            earthquakes = QueryUtils.extractEarthquakesParameter(jsonEarthquke);
//
//
//            // Find a reference to the {@link ListView} in the layout
//            ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//            // Create a new {@link ArrayAdapter} of earthquakes
//            EarthQuakeAdapter adapter = new EarthQuakeAdapter(
//                    EarthquakeActivity.this, earthquakes);
//
//            // Set the adapter on the {@link ListView}
//            // so the list can be populated in the user interface
//            earthquakeListView.setAdapter(adapter);
//
//        }
//    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<EarthQuake>> {

        /**
         * ?????????????????????????????????
         *
         * @param urls
         * @return
         */
        @Override
        protected List<EarthQuake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<EarthQuake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            Log.i(LOG_TAG, "log doInBackground: "+result);
            return result;

        }

        /**
         * ???????????????????????????????????????????????????
         *
         * @param data
         */
        @Override
        protected void onPostExecute(List<EarthQuake> data) {

            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}