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
                // 通过数组列表获取地震对象
                //EarthQuake earthQuake = earthquakes.get(position);
                //或者按照answer中的示例获取对象
                EarthQuake earthQuake = mAdapter.getItem(position);
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

        /**
         * 内部类用于执行background thread.
         */
        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAsyncTask earthQuakeAsyn = new EarthquakeAsyncTask();
        earthQuakeAsyn.execute(USGS_REQUEST_URL);
    }


    /**
     * 创建一个内部类，用于执行网络请求
     */
//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, String> {
//
////        ArrayList<EarthQuake> earthquake;
//
//        String jsonEarthquke;
////        /**
////         * 解决方式1
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
//                 * 构建URL 对象
//                 */
////                URL url = new URL(urls[0]);
//
//                URL url = new URL(urls[0]);
//
//                /**
//                 * 创建HttpURLConnect 对象用于http连接
//                 */
//                urlConnection = (HttpURLConnection) url.openConnection();
//
//                /**
//                 * 设置获取远程主机的方式为GET
//                 */
//                urlConnection.setRequestMethod("GET");
//
//                /**
//                 * 设置读取时长为10m，连接超时为15s
//                 */
//                urlConnection.setReadTimeout(10000);
//                urlConnection.setConnectTimeout(15000);
//
//                /**
//                 * 连接远程主机
//                 */
//                urlConnection.connect();
//
//                /**
//                 * 判断连接状态
//                 */
//                if (urlConnection.getResponseCode() == 200) {
//                    /**
//                     * 读取输入流，以字节的形式
//                     */
//                    inputStream = urlConnection.getInputStream();
//
//                    /**
//                     * 解析字节流
//                     */
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//
//                    /**
//                     * 读取字节流
//                     */
//                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                    /**
//                     * 按行读取并存储到jsonEarthquake 中
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
//                 * 最终断开连接，并关闭inputStream
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
//             * 生成earthquake 对象
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
         * 用于请求网络的后台方法
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
         * 网络请求完成并返回结果后的执行方法
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