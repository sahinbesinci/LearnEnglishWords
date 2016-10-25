package com.example.sahin.learnenglishwords;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sahin on 25.10.2016.
 */

public class TebrikFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bu fragment'in layout'unu hazır hale getirelim
        return inflater.inflate(R.layout.tebrik_layout, container, false);
    }
}
