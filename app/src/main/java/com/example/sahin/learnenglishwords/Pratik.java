package com.example.sahin.learnenglishwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sahin on 21.10.2016.
 */

public class Pratik extends FragmentActivity {
    TextView tvKelime,tvHata;
    EditText etCevap;
    Button btnSorgula,btnGec;

    private Random randomGenerator;
    ArrayList<Integer> listRandomInteger;
    int Sayac = 0;
    Kelime kelime;

    private ObjectPreference objectPreference;
    ComplexPreferences complexPrefenreces;
    ArrayList<Kelime> listComplexPreferences;
    ArrayList<Kelime> gecilenKelimeler;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pratik_layout);
        objectPreference = (ObjectPreference) this.getApplication();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();

        bilesenleri_yukle();
    }
    private void bilesenleri_yukle()
    {
        tvKelime = (TextView) findViewById(R.id.tvKelime);
        tvHata = (TextView) findViewById(R.id.tvHata);

        etCevap = (EditText) findViewById(R.id.etCevap);
        btnSorgula = (Button) findViewById(R.id.btnSorgula);
        btnSorgula.setOnClickListener(btnSorgulaListener);
        btnGec = (Button) findViewById(R.id.btnGec);
        btnGec.setOnClickListener(btnGecListener);

        complexPrefenreces = objectPreference.getComplexPreference();
        listComplexPreferences = new ArrayList<>();
        listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
        gecilenKelimeler = new ArrayList<>();
        listRandomInteger = new ArrayList<>();
        randomGenerator = new Random();
        sonrakiSoru();

        tvHata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHata.setText(kelime.getTurkce());
            }
        });
    }

    private View.OnClickListener btnGecListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gecilenKelimeler.add(kelime);

            sonrakiSoru();
            Sayac = 0;
        }
    };

    private View.OnClickListener btnSorgulaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (etCevap.getText().toString().equals(kelime.getTurkce()))
            {
                kelime.setDogru();

                ComplexPreferences complexPrefenreces = objectPreference.getComplexPreference();
                if(complexPrefenreces != null) {
                    String ingilizce = kelime.getIngilizce().toString();
                    complexPrefenreces.putObject(ingilizce, kelime);
                    complexPrefenreces.commit();
                }else
                    Toast.makeText(getApplicationContext(),"Hata Meydana Geldi",Toast.LENGTH_SHORT).show();

                sonrakiSoru();
                Sayac = 0;
            }
            else
            {
                Sayac++;
                Toast.makeText(getApplicationContext(),"Yanlış düşünüyorsun :D",Toast.LENGTH_SHORT).show();
                if (Sayac == 3)
                {
                    kelime.setYanlis();
                    Sayac = 0;
                }
            }
        }
    };

    private void sonrakiSoru()
    {
        if (listComplexPreferences.size() == 0)
        {
            if (gecilenKelimeler.size() == 0)
            {
                LinearLayout linearLayout= (LinearLayout) findViewById(R.id.fragment);
                linearLayout.setVisibility(View.VISIBLE);
                LinearLayout layoutInflater2 = (LinearLayout) findViewById(R.id.content);
                layoutInflater2.setVisibility(View.GONE);
                return;
            }

            listComplexPreferences.addAll(gecilenKelimeler);
            gecilenKelimeler.clear();
        }
        int listSize = listComplexPreferences.size();
        int position = randomGenerator.nextInt(listSize);

        kelime = listComplexPreferences.get(position);
        tvKelime.setText(kelime.getIngilizce());
        tvHata.setText("? ? ?");
        etCevap.setText("");
        listComplexPreferences.remove(position);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragmentManager.popBackStack();
    }
}
