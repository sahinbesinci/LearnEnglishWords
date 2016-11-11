package com.example.sahin.learnenglishwords;

/**
 * Created by sahin on 21.10.2016.
 */
import android.app.Application;

public class ObjectPreference extends Application {
    private static final String TAG = "ObjectPreference";
    private ComplexPreferences complexPrefenreces = null;
    private CompPrefEzber EzberComplexPreferences = null;

    @Override
    public void onCreate() {
        super.onCreate();
        complexPrefenreces = ComplexPreferences.getComplexPreferences(getBaseContext(), "abhan", MODE_PRIVATE);
        EzberComplexPreferences = CompPrefEzber.getComplexPreferences(getBaseContext(), "sahin", MODE_PRIVATE);
        android.util.Log.i(TAG, "Preference Created.");
    }

    public ComplexPreferences getComplexPreference() {
        if(complexPrefenreces != null) {
            return complexPrefenreces;
        }
        return null;
    }
    public CompPrefEzber getEzberComplexPreferences() {
        if(EzberComplexPreferences != null) {
            return EzberComplexPreferences;
        }
        return null;
    }
}