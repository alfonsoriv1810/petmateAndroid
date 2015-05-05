package com.petmate.fcfm.petmate;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.petmate.fcfm.petmate.asynckTasks.FCFMColeccionMascotas;
import com.petmate.fcfm.petmate.fragmentos.FCFMListadoFragment;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    TabHost menuTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuTab = (TabHost) findViewById(android.R.id.tabhost);
        initTabs();
        menuTab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabColor(menuTab);

            }
        });

        new FCFMColeccionMascotas(this).execute();
    }

    public void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.item_unselected_menu); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundResource(R.color.item_selected_menu); // selected
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    void  initTabs(){
        menuTab.setup();

        addNewTab(R.id.tab1, getResources().getDrawable(R.drawable.btn_home));
        addNewTab(R.id.tab2, getResources().getDrawable(R.drawable.btn_fav));
        addNewTab(R.id.tab3, getResources().getDrawable(R.drawable.btn_mascota));
        addNewTab(R.id.tab4, getResources().getDrawable(R.drawable.btn_user));
        addNewTab(R.id.tab5, getResources().getDrawable(R.drawable.btn_settings));

        setTabColor(menuTab);
    }


    void addNewTab(int content, Drawable icon){
        //Esto sirve para cambiar el idioma dependiendo del texto

        TabHost.TabSpec spec = menuTab.newTabSpec("");
        spec.setIndicator("", icon);//Titulo de la tab
        spec.setContent(content);//Contenido a mostrar (Buscar una manera de hacerlo separado y no en un solo xml)-
        menuTab.addTab(spec);
    }

    public void finishedLoadingListView(final ArrayList<FCFMMascota> arregloMascotas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FCFMListadoFragment.initTablaMascotas(arregloMascotas);
            }
        });
    }
}
