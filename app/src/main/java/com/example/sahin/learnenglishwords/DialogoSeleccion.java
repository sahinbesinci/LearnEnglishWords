package com.example.sahin.learnenglishwords;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

/**
 * Created by Sahin on 10.11.2016.
 */

public class DialogoSeleccion extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = {"Ezberledim","Sil","Vazgeç"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("İşlem Seç").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0: Toast.makeText(getActivity(),"0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: Toast.makeText(getActivity(),"1", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                };
            }
        });
        return builder.create();
    }


}
