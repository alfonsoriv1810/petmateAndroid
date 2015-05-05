package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.petmate.fcfm.petmate.R;

import java.util.ArrayList;

/**
 * Created by alfonso on 21/04/15.
 */

public class FCFMListadoFragment extends Fragment {

    public static ListView listviewMascotas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_listado_mascotas, container, false);

        listviewMascotas= (ListView) view.findViewById(R.id.lvMascotas);
        listviewMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }



    public static void initTablaMascotas(ArrayList<?> arregloMascotas){

        /*if (arregloMascotas != null) {
            ArrayAdapter<FCFMMascota> adapter = new ArrayAdapter<FCFMMascota>(MainActivity.getAppContext(), R.layout.fcfm_item_listado_mascotas, arregloMascotas);
            listviewMascotas.setAdapter(adapter);
        }*/
    }
}
