package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petmate.fcfm.petmate.R;

/**
 * Created by alfonso on 28/04/15.
 */
public class FCFMConfiguracion extends Fragment {

    Button botonConfiguracion;
    Button botonAcercaDe;
    LinearLayout linearAcercade;

    TextView fcfmTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_configuracion, container, false);

        botonConfiguracion = (Button) view.findViewById(R.id.botonConfiguracion);
        botonAcercaDe= (Button) view.findViewById(R.id.botonAcercaDe);
        linearAcercade = (LinearLayout) view.findViewById(R.id.linearLayoutAcercaDe);
        fcfmTV = (TextView) view.findViewById(R.id.textViewFCFM);

        botonConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        botonAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAcercade.setVisibility(View.VISIBLE);
                fcfmTV.setVisibility(View.GONE);
                botonAcercaDe.setVisibility(View.GONE);
            }
        });

        linearAcercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAcercade.setVisibility(View.GONE);
                fcfmTV.setVisibility(View.VISIBLE);
                botonAcercaDe.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
