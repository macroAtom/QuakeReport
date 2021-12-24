package com.example.android.quakereport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
//import android.preference.PreferenceFragment;

public class SettingsActivity extends AppCompatActivity {

    public static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }


    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);


            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            Log.i(LOG_TAG, "onCreate: " + minMagnitude);
            bindPreferenceSummaryToValue(minMagnitude);

        }
        // 这个函数就是用来 将震级显示在标题下面，如果弹出的magitude 设为空，这里value 传入一个值，这个值会显示在界面上，但不会作用与url
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            Log.i(LOG_TAG, "onPreferenceChange: preference " + preference);

            Log.i(LOG_TAG, "onPreferenceChange: " + stringValue);
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            Log.i(LOG_TAG, "bindPreferenceSummaryToValue: preference " + preference);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

            Log.i(LOG_TAG, "bindPreferenceSummaryToValue: preferences " + preferences);

            String preferenceString = preferences.getString(preference.getKey(), "");

            Log.i(LOG_TAG, "bindPreferenceSummaryToValue: preferenceString " + preferenceString);

            onPreferenceChange(preference, preferenceString);
        }
    }
}