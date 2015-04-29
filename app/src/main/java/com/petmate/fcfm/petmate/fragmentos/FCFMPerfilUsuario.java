package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editTextNombreUsuario;
    TextView textViewTelefonoUsuario;
    EditText editTextTelefonoUsuario;
    TextView textViewEstadoUsuario;
    EditText editTextEstadoUsuario;
    Boolean estaEditando = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_usuario_perfil, container, false);

        imagenUsuario = (ImageView) view.findViewById(R.id.imagenUsuario);
        textViewNombreUsuario = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        textViewTelefonoUsuario = (TextView) view.findViewById(R.id.textViewTelefonoUsuario);
        textViewEstadoUsuario = (TextView) view.findViewById(R.id.textViewEstadoUsuario);

        editTextNombreUsuario  = (EditText) view.findViewById(R.id.editTextNombreUsuario);
        editTextTelefonoUsuario = (EditText) view.findViewById(R.id.editTextTelefonoUsuario);
        editTextEstadoUsuario = (EditText) view.findViewById(R.id.editTextEstadoUsuario);

        botonEditar = (Button) view.findViewById(R.id.botonEditar);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estaEditando == true){
                    estaEditando = false;
                    textViewNombreUsuario.setVisibility(View.VISIBLE);
                    textViewTelefonoUsuario.setVisibility(View.VISIBLE);
                    textViewEstadoUsuario.setVisibility(View.VISIBLE);

                    textViewNombreUsuario.setText(editTextNombreUsuario.getText().toString());
                    textViewTelefonoUsuario.setText(editTextTelefonoUsuario.getText().toString());
                    textViewEstadoUsuario.setText(editTextEstadoUsuario.getText().toString());

                    editTextNombreUsuario.setVisibility(View.GONE);
                    editTextTelefonoUsuario.setVisibility(View.GONE);
                    editTextEstadoUsuario.setVisibility(View.GONE);
                } else  {
                    estaEditando = true;
                    textViewNombreUsuario.setVisibility(View.GONE);
                    textViewTelefonoUsuario.setVisibility(View.GONE);
                    textViewEstadoUsuario.setVisibility(View.GONE);

                    editTextNombreUsuario.setVisibility(View.VISIBLE);
                    editTextTelefonoUsuario.setVisibility(View.VISIBLE);
                    editTextEstadoUsuario.setVisibility(View.VISIBLE);

                    editTextNombreUsuario.setText(textViewNombreUsuario.getText().toString());
                    editTextTelefonoUsuario.setText(textViewTelefonoUsuario.getText().toString());
                    editTextEstadoUsuario.setText(textViewEstadoUsuario.getText().toString());
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
