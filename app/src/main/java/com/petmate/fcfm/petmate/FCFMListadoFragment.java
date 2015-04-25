package com.petmate.fcfm.petmate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by alfonso on 21/04/15.
 */

public class FCFMListadoFragment extends Fragment {

    ListView listviewMascotas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_listado_mascotas, container, false);

        listviewMascotas= (ListView) view.findViewById(R.id.lvMascotas);
        listviewMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    void initTablaMascotas(){

    }
}
