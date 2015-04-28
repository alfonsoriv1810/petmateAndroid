package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.petmate.fcfm.petmate.R;

/**
 * Created by alfonso on 27/04/15.
 */
public class FCFMPerfilUsuario extends Fragment {
    ListView listviewMascotas;
    Button botonEditar;
    ImageView imagenUsuario;
    TextView textViewNombreUsuario;
    TextView textViewTelefonoUsuario;
    TextView textViewEstadoUsuario;
    Boolean estaEditando = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_usuario_perfil, container, false);

        imagenUsuario = (ImageView) view.findViewById(R.id.imagenUsuario);
        textViewNombreUsuario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        textViewTelefonoUsuario = (TextView) view.findViewById(R.id.textViewTelefonoUsuario);
        textViewEstadoUsuario = (TextView) view.findViewById(R.id.textViewEstadoUsuario);

        botonEditar = (Button) view.findViewById(R.id.botonEditar);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estaEditando == true){
                    estaEditando = false;
                    textViewNombreUsuario.setVisibility(View.VISIBLE);
                    textViewTelefonoUsuario.setVisibility(View.VISIBLE);
                    textViewEstadoUsuario.setVisibility(View.VISIBLE);
                } else  {
                    estaEditando = true;
                    textViewNombreUsuario.setVisibility(View.GONE);
                    textViewTelefonoUsuario.setVisibility(View.GONE);
                    textViewEstadoUsuario.setVisibility(View.GONE);
                }
            }
        });

        listviewMascotas= (ListView) view.findViewById(R.id.listadoMascotasUsuario);
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

    void initTablaMascotasUsuario(){

    }
}
