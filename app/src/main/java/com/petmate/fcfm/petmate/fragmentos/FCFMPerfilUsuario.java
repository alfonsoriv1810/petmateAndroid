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
import com.petmate.fcfm.petmate.adaptadores.AdaptadorMascotasHome;
import com.petmate.fcfm.petmate.adaptadores.FCFMAdaptadorMascotasDetalleUsuario;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by alfonso on 27/04/15.
 */
public class FCFMPerfilUsuario extends Fragment implements DescargaServicio.onDowloadList {
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
    private interfaceTocoMascotaDetalleUsuario interfaceDetalleUsuario;

    private LinkedList<FCFMMascota> _listaMascotas = new LinkedList<>();
    private FCFMAdaptadorMascotasDetalleUsuario _listadoAd;

    @Override
    public void onResume() {
        super.onResume();
        DescargaServicio descargaServicio = new DescargaServicio(this);
        descargaServicio.execute(FCFMSingleton.baseURL + "get_mascotas.php?caso=1&id=" + FCFMSingleton.usuario.getIdUsuario());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_usuario_perfil, container, false);

        interfaceDetalleUsuario = (interfaceTocoMascotaDetalleUsuario) inflater.getContext();

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

        _listadoAd= new FCFMAdaptadorMascotasDetalleUsuario();
        listviewMascotas.setAdapter(_listadoAd);

        listviewMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                interfaceDetalleUsuario.cargaMascotaConMoeloDetalleUsuario((FCFMMascota) view.getTag());
            }
        });

        return view;
    }

    @Override
    public void estaDescarga(String string) {
        try {
            JSONArray array =new JSONArray(string);
            _listaMascotas.clear();
            for (int i = 0; i < array.length(); i++) {
                _listaMascotas.add(new FCFMMascota(array.optJSONObject(i)));
            }
            _listadoAd.set_lista(_listaMascotas);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface interfaceTocoMascotaDetalleUsuario {
        public void cargaMascotaConMoeloDetalleUsuario(FCFMMascota mascotaModelo);
    };
}
