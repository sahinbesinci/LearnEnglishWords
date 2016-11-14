package com.example.sahin.learnenglishwords;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

public class Kelimeler extends ActionBarActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    Pager adapter;

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
        setContentView(R.layout.kelimeler_tab_layout);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("TEKRAR ETTİKLERİM"));
        tabLayout.addTab(tabLayout.newTab().setText("EZBERDEKİLER"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new RotateUpTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1)
                {
                    if (viewPager.getCurrentItem() == 0)
                        adapter.tab2.updateFragmentListView();
                    else if (viewPager.getCurrentItem() == 1)
                        adapter.tab1.updateFragmentListView();
                }
            }
        });
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (viewPager.getCurrentItem() == 0)
            adapter.tab2.updateFragmentListView();
        else if (viewPager.getCurrentItem() == 1)
            adapter.tab1.updateFragmentListView();
    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
















    /*
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
                                dialog.cancel();
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
*/
