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
    TextView textViewPerfil;
    TextView textViewCorreoUsuario;
    EditText editTextCorreoUsuario;
    TextView textViewTelefonoUsuario;
    EditText editTextTelefonoUsuario;
    TextView textViewEstadoUsuario;
    Boolean estaEditando = false;
    private interfaceTocoMascotaDetalleUsuario interfaceDetalleUsuario;

    private LinkedList<FCFMMascota> _listaMascotas = new LinkedList<>();
    private FCFMAdaptadorMascotasDetalleUsuario _listadoAd;

    @Override
    public void onResume() {
        super.onResume();
        if (FCFMSingleton.usuario != null) {
            DescargaServicio descargaServicio = new DescargaServicio(this);
            descargaServicio.execute(FCFMSingleton.baseURL + "get_mascotas.php?caso=1&id=" + FCFMSingleton.usuario.getIdUsuario());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fcfm_usuario_perfil, container, false);

        interfaceDetalleUsuario = (interfaceTocoMascotaDetalleUsuario) inflater.getContext();

        textViewPerfil = (TextView) view.findViewById(R.id.textViewPerfil);
        imagenUsuario = (ImageView) view.findViewById(R.id.imagenUsuario);
        textViewCorreoUsuario = (TextView) view.findViewById(R.id.textViewCorreoUsuario);
        textViewTelefonoUsuario = (TextView) view.findViewById(R.id.textViewTelefonoUsuario);
        textViewEstadoUsuario = (TextView) view.findViewById(R.id.textViewEstadoUsuario);

        if (FCFMSingleton.usuario != null) {
            textViewPerfil.setText(FCFMSingleton.usuario.getNombre());
            textViewCorreoUsuario.setText(FCFMSingleton.usuario.getUsername());
            textViewTelefonoUsuario.setText(FCFMSingleton.usuario.getTelefono());
            textViewEstadoUsuario.setText(FCFMSingleton.usuario.getEstado());
        }

        editTextCorreoUsuario  = (EditText) view.findViewById(R.id.editTextCorreoUsuario);
        editTextTelefonoUsuario = (EditText) view.findViewById(R.id.editTextTelefonoUsuario);

        botonEditar = (Button) view.findViewById(R.id.botonEditar);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estaEditando == true){
                    estaEditando = false;
                    textViewCorreoUsuario.setVisibility(View.VISIBLE);
                    textViewTelefonoUsuario.setVisibility(View.VISIBLE);
                    textViewEstadoUsuario.setVisibility(View.VISIBLE);

                    if (FCFMSingleton.usuario != null) {
                        textViewCorreoUsuario.setText(FCFMSingleton.usuario.getUsername());
                        textViewTelefonoUsuario.setText(FCFMSingleton.usuario.getTelefono());
                        textViewEstadoUsuario.setText(FCFMSingleton.usuario.getEstado());
                    }

                    editTextCorreoUsuario.setVisibility(View.GONE);
                    editTextTelefonoUsuario.setVisibility(View.GONE);
                } else  {
                    estaEditando = true;
                    textViewCorreoUsuario.setVisibility(View.GONE);
                    textViewTelefonoUsuario.setVisibility(View.GONE);
                    textViewEstadoUsuario.setVisibility(View.GONE);

                    editTextCorreoUsuario.setVisibility(View.VISIBLE);
                    editTextTelefonoUsuario.setVisibility(View.VISIBLE);

                    if (FCFMSingleton.usuario != null) {
                        editTextCorreoUsuario.setText(FCFMSingleton.usuario.getUsername());
                        editTextTelefonoUsuario.setText(FCFMSingleton.usuario.getTelefono());
                    }
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
