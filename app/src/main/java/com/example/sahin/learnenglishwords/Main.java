package com.example.sahin.learnenglishwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
                Intent i = new Intent(Main.this,Pratik.class);
                startActivity(i);
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
