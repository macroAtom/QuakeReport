package com.example.android.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>>{


    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    //    ArrayList<EarthQuake> earthquakes;
    EarthQuakeAdapter mAdapter;


    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        List<EarthQuake> earthquakes = new ArrayList<EarthQuake>();
        mAdapter = new EarthQuakeAdapter(this, earthquakes);

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
         * 18:intro to loaders,part2 Lesson3-Threads & Parallelism
         * in first place to kick off a loader,并调用initLoader
         *
         * forceLoad 的目的是触发后台作业
         * onStartLoading 方法将在initLoader中自动触发
         * 当调用forceLoad 时，Loader将被启动
         */


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();

//        getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this).forceLoad();

    }

    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(MainActivity.this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> data) {

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
        mAdapter.clear();
    }
}