package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    public static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    /**
     * query url
     */

    private String mUrl;


    public EarthquakeLoader(@NonNull Context context,String url) {
        super(context);
        this.mUrl = url;
    }

    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        Log.i(LOG_TAG, "log onStartLoading: ");

        forceLoad();



    }


    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {
        Log.i(LOG_TAG, "log loadInBackground: ");
        List<EarthQuake> result = QueryUtils.fetchEarthquakeData(mUrl);

        return result;
    }

}
