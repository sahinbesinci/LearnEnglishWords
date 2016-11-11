package com.example.sahin.learnenglishwords;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Sahin on 9.11.2016.
 */

public class TekrarEttigimKelimelerFragment extends Fragment{

    ListView lvKelimeler;
    public KelimelerBaseAdapter kelimelerBaseAdapter;
    SearchView searchView;
    private ObjectPreference objectPreference;
    ComplexPreferences complexPrefenreces;
    CompPrefEzber compPrefEzber;
    String sArama="";

    ArrayList<Kelime> listComplexPreferences;
    View rootView;
    Spinner spinner;
    int spinnerSecim = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        objectPreference = (ObjectPreference) getActivity().getApplication();
        bilesenleri_yukle();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),KelimeKaydet.class);
                i.putExtra("searchString",searchView.getQuery().toString());
                startActivity(i);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.order,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return rootView;
    }

    private void bilesenleri_yukle()
    {
        lvKelimeler = (ListView) rootView.findViewById(R.id.lvKelimeler);
        complexPrefenreces = objectPreference.getComplexPreference();
        compPrefEzber = objectPreference.getEzberComplexPreferences();
        listComplexPreferences = new ArrayList<>();
        listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
        kelimelerBaseAdapter = new KelimelerBaseAdapter(getActivity(), listComplexPreferences);
        lvKelimeler.setAdapter(kelimelerBaseAdapter);

        lvKelimeler.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("İşlem Seç")
                        .setMessage("Kelimeyi silmek için 'SİL' bir daha tekrar yapmamak için 'EZBERLENDİ' seçeneğini seçin!")
                        .setPositiveButton("Ezberlendi", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String ingilizce = kelimelerBaseAdapter.getItem(position).getIngilizce();
                                Kelime kelime = kelimelerBaseAdapter.getItem(position);
                                compPrefEzber.putObject(ingilizce, kelime);
                                compPrefEzber.commit();
                                complexPrefenreces.removeObject(kelime.getIngilizce());
                                complexPrefenreces.commit();
                                listComplexPreferences.remove(kelime);
                                kelimelerBaseAdapter.notifyDataSetChanged();
                                TekrarEttigimKelimelerFragment.this.kelimelerBaseAdapter.getFilter().filter(sArama);
                            }
                        })
                        .setNegativeButton("Sil", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Kelime kelime = kelimelerBaseAdapter.getItem(position);
                                complexPrefenreces.removeObject(kelime.getIngilizce());
                                complexPrefenreces.commit();
                                listComplexPreferences.remove(kelime);
                                kelimelerBaseAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(),"Silindi",Toast.LENGTH_SHORT).show();
                                TekrarEttigimKelimelerFragment.this.kelimelerBaseAdapter.getFilter().filter(sArama);
                            }
                        })
                        .setNeutralButton("Vazgeç", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
                return false;
            }
        });
        searchView = (SearchView) rootView.findViewById(R.id.sArama);
        searchView.setOnQueryTextListener(sAramaListener);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(onItemSelectedListenerSpinner);
    }

    private SearchView.OnQueryTextListener sAramaListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            TekrarEttigimKelimelerFragment.this.kelimelerBaseAdapter.getFilter().filter(newText);
            sArama=newText;
            return false;
        }
    };
    private Spinner.OnItemSelectedListener onItemSelectedListenerSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position)
            {
                //Ingilizce sıralama
                case 0: spinnerSecim = 0;
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new Sort());
                    break;
                //Türkçe sıralama
                case 1: spinnerSecim = 1;
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortTurkce());
                    break;
                //Doğru sıralama
                case 2: spinnerSecim = 2;
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortDogru());
                    break;
                //Yanlış sıralama
                case 3: spinnerSecim = 3;
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortYanlis());
                    break;
                default:break;

            }
            kelimelerBaseAdapter.notifyDataSetChanged();
            TekrarEttigimKelimelerFragment.this.kelimelerBaseAdapter.getFilter().filter(sArama);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    public void updateFragmentListView(){
        if(kelimelerBaseAdapter != null){
            listComplexPreferences = new ArrayList<>();
            listComplexPreferences = complexPrefenreces.getAllObject(Kelime.class);
            kelimelerBaseAdapter = new KelimelerBaseAdapter(getActivity(), listComplexPreferences);
            lvKelimeler.setAdapter(kelimelerBaseAdapter);
            kelimelerBaseAdapter.notifyDataSetChanged();

            switch (spinnerSecim)
            {
                //Ingilizce sıralama
                case 0:
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new Sort());
                    break;
                //Türkçe sıralama
                case 1:
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortTurkce());
                    break;
                //Doğru sıralama
                case 2:
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortDogru());
                    break;
                //Yanlış sıralama
                case 3:
                    Collections.sort(listComplexPreferences, (Comparator<? super Kelime>) new SortYanlis());
                    break;
                default:break;

            }
            kelimelerBaseAdapter.notifyDataSetChanged();



            TekrarEttigimKelimelerFragment.this.kelimelerBaseAdapter.getFilter().filter(sArama);

        }
    }
}
