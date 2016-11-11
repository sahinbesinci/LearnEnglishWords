package com.example.sahin.learnenglishwords;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sahin on 21.10.2016.
 */
public class Main extends Activity{
    Button btnPratikYap, btnKelimeler;

    private ObjectPreference objectPreference;
    ComplexPreferences complexPrefenreces;
    ArrayList<Kelime> listComplexPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.main_layout);
        objectPreference = (ObjectPreference) this.getApplication();
        bilesenleri_yukle();
    }
    private void bilesenleri_yukle()
    {
        btnPratikYap = (Button) findViewById(R.id.btnPratikYap);
        btnKelimeler = (Button) findViewById(R.id.btnKelimeler);
        btnPratikYap.setOnClickListener(btnPratikYapListener);
        btnKelimeler.setOnClickListener(btnKelimelerListener);
        complexPrefenreces = objectPreference.getComplexPreference();
        listComplexPreferences = new ArrayList<>();
    }
    private View.OnClickListener btnPratikYapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listComplexPreferences.clear();
            listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
            if (listComplexPreferences.size()==0)
                Toast.makeText(getApplicationContext(),"Lütfen kelime kaydet.",Toast.LENGTH_LONG).show();
            else {
                new AlertDialog.Builder(Main.this)
                        .setTitle("Alıştırma Türü")
                        .setMessage("Türkçe mi yoksa ingilizce anlamı mı tahmin etmek istersin?")
                        .setPositiveButton("İngilizce", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Main.this,Pratik.class);
                                i.putExtra("Tur","Eng");
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Türkçe", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Main.this,Pratik.class);
                                i.putExtra("Tur","Turkce");
                                startActivity(i);
                            }
                        })
                        .show();
            }

        }
    };
    private View.OnClickListener btnKelimelerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(Main.this,Kelimeler.class);
            startActivity(i);
        }
    };

}
