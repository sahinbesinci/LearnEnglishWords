package com.example.sahin.learnenglishwords;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sahin on 21.10.2016.
 */

public class KelimeKaydet extends AppCompatActivity {
    final static String NAME = "KELIMELER";
    final static String INGILIZCE = "INGILIZCE";
    final static String TURKCE = "TURKCE";
    private ObjectPreference objectPreference;
    ComplexPreferences complexPrefenreces;
    Button btnKaydet;
    EditText etTurkce,etIngilizce;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.kelime_kaydet_layout);
        objectPreference = (ObjectPreference) this.getApplication();
        bilesenleri_yukle();
        complexPrefenreces = objectPreference.getComplexPreference();
    }
    private void bilesenleri_yukle()
    {
        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnKaydet.setOnClickListener(btnKaydetListener);
        etIngilizce = (EditText) findViewById(R.id.etIngilizce);
        etTurkce = (EditText) findViewById(R.id.etTurkce);
        etIngilizce.setText(getIntent().getExtras().getString("searchString").toString());
    }
    private View.OnClickListener btnKaydetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!etIngilizce.getText().toString().equals("") && !etTurkce.getText().toString().equals(""))
            {
                String ing = etIngilizce.getText().toString().toLowerCase();
                String tur = etTurkce.getText().toString().toLowerCase();

                if (kelimeVarmi(ing))
                {
                    Kelime kelime = new Kelime(ing,tur);

                    if(complexPrefenreces != null) {
                        String ingilizce = kelime.getIngilizce().toString();
                        complexPrefenreces.putObject(ingilizce, kelime);
                        Toast.makeText(getApplicationContext(),"Yeni Kelimeniz Eklendi\n- " + ingilizce,Toast.LENGTH_SHORT).show();
                        complexPrefenreces.commit();

                        Intent intent = new Intent(KelimeKaydet.this,Kelimeler.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else
                        Toast.makeText(getApplicationContext(),"Hata Meydana Geldi",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Kelime Mevcut!",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(),"Kutucukları Doldurun!",Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean kelimeVarmi(String ing)
    {
        ArrayList<Kelime> listComplexPreferences = new ArrayList<>();
        listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
        for (Kelime kelime : listComplexPreferences) {
            if (kelime.getIngilizce().equals(ing))
                return false;
        }
        return true;
    }
}
