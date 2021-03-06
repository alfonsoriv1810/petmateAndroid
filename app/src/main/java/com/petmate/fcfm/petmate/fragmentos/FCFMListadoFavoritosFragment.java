package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.adaptadores.FCFMAdaptadorMascotasDetalleUsuario;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

/**
 * Created by alfonso on 06/05/15.
 */
public class FCFMListadoFavoritosFragment extends Fragment implements DescargaServicio.onDowloadList {

    ListView listViewFavoritos;
    TextView textViewSinMascotas;
    private LinkedList<FCFMMascota> _listaMascotas = new LinkedList<>();
    private FCFMAdaptadorMascotasDetalleUsuario _listadoAd;
    private interfaceTocoMascotaFavorito interfaceTocoMascota;

    @Override
    public void onResume() {
        super.onResume();

        if (FCFMSingleton.usuario != null){
            DescargaServicio descargaServicio = new DescargaServicio(this);
            descargaServicio.execute(FCFMSingleton.baseURL + "get_favoritos.php?usuario_id=" + FCFMSingleton.usuario.getIdUsuario());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_listado_favoritos, container, false);

        textViewSinMascotas = (TextView) view.findViewById(R.id.textViewSinMascotas);

        listViewFavoritos = (ListView) view.findViewById(R.id.listViewFavoritosMascotas);
        _listadoAd= new FCFMAdaptadorMascotasDetalleUsuario();
        listViewFavoritos.setAdapter(_listadoAd);

        interfaceTocoMascota = (interfaceTocoMascotaFavorito) inflater.getContext();

        listViewFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                interfaceTocoMascota.cargaMascotaConMoeloFavoritos((FCFMMascota) view.getTag());
            }
        });
        return view;
    }

    @Override
    public void estaDescarga(String string) {
        try {
            JSONArray array = new JSONArray(string);
            if (array.length() > 0) {
                listViewFavoritos.setVisibility(View.VISIBLE);
                textViewSinMascotas.setVisibility(View.GONE);
                _listaMascotas.clear();
                for (int i = 0; i < array.length(); i++) {
                    Log.e("OBTIENE DE FAVORITOS ------------->", ""+array.optJSONObject(i));
                    FCFMMascota mascotaAgregar = new FCFMMascota(array.optJSONObject(i));
                    _listaMascotas.add(new FCFMMascota(array.optJSONObject(i)));
                }
                _listadoAd.set_lista(_listaMascotas);
            } else {
                listViewFavoritos.setVisibility(View.GONE);
                textViewSinMascotas.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            listViewFavoritos.setVisibility(View.GONE);
            textViewSinMascotas.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    public interface interfaceTocoMascotaFavorito {
        public void cargaMascotaConMoeloFavoritos(FCFMMascota mascotaModelo);
    };

}
