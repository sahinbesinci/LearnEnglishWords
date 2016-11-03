package com.example.sahin.learnenglishwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Kelimeler extends AppCompatActivity {
    ListView lvKelimeler;
    public KelimelerBaseAdapter kelimelerBaseAdapter;
    SearchView searchView;
    private ObjectPreference objectPreference;
    ComplexPreferences complexPrefenreces;
    String sArama="";

    ArrayList<Kelime> listComplexPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objectPreference = (ObjectPreference) this.getApplication();
        bilesenleri_yukle();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Kelimeler.this,KelimeKaydet.class);
                i.putExtra("searchString",searchView.getQuery().toString());
                startActivity(i);
            }
        });
    }
    private void bilesenleri_yukle()
    {
        lvKelimeler = (ListView) findViewById(R.id.lvKelimeler);
        complexPrefenreces = objectPreference.getComplexPreference();
        listComplexPreferences = new ArrayList<>();
        listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
        kelimelerBaseAdapter = new KelimelerBaseAdapter(getApplicationContext(), listComplexPreferences);
        lvKelimeler.setAdapter(kelimelerBaseAdapter);

        lvKelimeler.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(Kelimeler.this)
                        .setTitle("Kelimeyi Sil")
                        .setMessage("Bu kelimeyi silmek istediğinizden emin misiniz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Kelime kelime = kelimelerBaseAdapter.getItem(position);
                                complexPrefenreces.removeObject(kelime.getIngilizce());
                                complexPrefenreces.commit();
                                listComplexPreferences.remove(kelime);
                                kelimelerBaseAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"Silindi",Toast.LENGTH_SHORT).show();
                                Kelimeler.this.kelimelerBaseAdapter.getFilter().filter(sArama);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return false;
            }
        });
        searchView = (SearchView) findViewById(R.id.sArama);
        searchView.setOnQueryTextListener(sAramaListener);

    }


    private SearchView.OnQueryTextListener sAramaListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Kelimeler.this.kelimelerBaseAdapter.getFilter().filter(newText);
            sArama=newText;
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        kelimelerBaseAdapter.notifyAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        kelimelerBaseAdapter.notifyDataSetChanged();
    }

}
