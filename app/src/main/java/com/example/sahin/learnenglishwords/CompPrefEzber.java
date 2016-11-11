package com.example.sahin.learnenglishwords;

/**
 * Created by Sahin on 9.11.2016.
 */
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CompPrefEzber {
    private static CompPrefEzber       ezcomplexPreferences;
    private final Context                   context;
    private final SharedPreferences         preferences;
    private final SharedPreferences.Editor  editor;
    private static Gson                     GSON            = new Gson();
    Type                                    typeOfObject    = new TypeToken<Object>(){}
            .getType();

    private CompPrefEzber(Context context, String namePreferences, int mode) {
        this.context = context;
        if (namePreferences == null || namePreferences.equals("")) {
            namePreferences = "sahin";
        }
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static CompPrefEzber getComplexPreferences(Context context,
                                                           String namePreferences, int mode) {
        if (ezcomplexPreferences == null) {
            ezcomplexPreferences = new CompPrefEzber(context,
                    namePreferences, mode);
        }
        return ezcomplexPreferences;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putString(key, GSON.toJson(object));
    }

    public void removeObject(String key)
    {
        if (key.equals("") || key == null) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.remove(key);
    }

    public void commit() {
        editor.commit();
    }

    public <T> ArrayList<T> getAllObject(Class<T> a) {
        List<String> gson = new ArrayList<>();

        Map<String,?> keys = preferences.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            gson.add(entry.getKey());
        }

        if (gson == null) {
            return null;
        }
        else {
            try {

                ArrayList<T> kelimes = new ArrayList<>();
                for (String str : gson) {
                    String prefStr = preferences.getString(str, null);
                    kelimes.add(GSON.fromJson(prefStr,a));
                }
                Collections.sort(kelimes, (Comparator<? super T>) new Sort());
                return kelimes;
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key "
                        + " is instance of other class");
            }
        }
    }

}