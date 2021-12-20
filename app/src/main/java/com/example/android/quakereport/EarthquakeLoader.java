package com.example.android.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {


    /**
     * query url
     */

    private String mUrl;

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    public EarthquakeLoader(@NonNull Context context,String url) {
        super(context);
        this.mUrl = url;
    }

    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {

        List<EarthQuake> result = QueryUtils.fetchEarthquakeData(mUrl);

        return result;
    }

}
