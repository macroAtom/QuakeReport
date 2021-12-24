package com.example.android.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {


    /**
     * URL for earthquake data from the USGS dataset
     */
//    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    //    ArrayList<EarthQuake> earthquakes;
    EarthQuakeAdapter mAdapter;

    ListView earthquakeListView;

    TextView mEmptyStateTextView;

    ProgressBar mProgressBar;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, "log onCreate: start ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        earthquakeListView = (ListView) findViewById(R.id.list);

        List<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
        mAdapter = new EarthQuakeAdapter(this, earthquakes);

        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        mEmptyStateTextView = findViewById(R.id.empty_view);

        mProgressBar = findViewById(R.id.loading_spinner);

        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        /**
         * 设置监听器，监听list item的点击，并产生新的行为
         */
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

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


        /**
         * 18:intro to loaders,part2 Lesson3-Threads & Parallelism
         * in first place to kick off a loader,并调用initLoader
         *
         * forceLoad 的目的是触发后台作业
         * onStartLoading 方法将在initLoader中自动触发
         * 当调用forceLoad 时，Loader将被启动
         */

        //老版本初始化loader 的方式
        // getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        Log.i(LOG_TAG, "log initLoader ");
        if (isConnected) {
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

    /**
     * inflate a mean
     * 会在页面上显示菜单标题，如果有icon，会显示Icon
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    /**
     * 点击菜单项后的行为
     * @param
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Log.i(LOG_TAG, "onOptionsItemSelected: id:"+id);
        MenuItem c = item;

        Log.i(LOG_TAG, "onOptionsItemSelected: "+c);

        if(id == R.id.action_settings){
            Intent settingsIntent  = new Intent(this,SettingsActivity.class);

            Log.i(LOG_TAG,"onOptionsItemSelected: settingsIntent "+settingsIntent);

            startActivity(settingsIntent );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle args) {

        Log.i(LOG_TAG, "Log onCreateLoader: " + new EarthquakeLoader(MainActivity.this, USGS_REQUEST_URL));


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        String new_nrl = uriBuilder.toString();
        Log.i(LOG_TAG, "onCreateLoader: "+new_nrl);
        return new EarthquakeLoader(MainActivity.this, uriBuilder.toString());
    }


    /**
     * 后台线程冲网络加载完数据后，执行的方法
     *
     * @param loader
     * @param data   从网络端请求的数据对象
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        Log.i(LOG_TAG, "log onLoadFinished: " + data);


        /**
         * 隐藏indicator,因为数据已经加载
         */
        mProgressBar.setVisibility(View.GONE);

        /**
         * 设置地震为空时显示的文本
         */
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthQuake>> loader) {
// Loader reset, so we can clear out our existing data.
        Log.i(LOG_TAG, "log onLoaderReset: " + mAdapter);
        mAdapter.clear();

    }
}