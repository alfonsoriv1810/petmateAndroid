package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.adaptadores.AdaptadorMascotasHome;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alfonso on 21/04/15.
 */

public class FCFMListadoFragment extends Fragment implements DescargaServicio.onDowloadList {
    public ListView listviewMascotas;
    private LinkedList<FCFMMascota> _listaMascotas = new LinkedList<>();
    private AdaptadorMascotasHome _listadoAd;
    private tocoMascota interfaceTocoMascota;

    public FCFMListadoFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        DescargaServicio descargaServicio = new DescargaServicio(this);
        descargaServicio.execute(FCFMSingleton.baseURL + "get_home.php");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_listado_mascotas, container, false);
        listviewMascotas = (ListView) view.findViewById(R.id.lvMascotas);
        _listadoAd = new AdaptadorMascotasHome();
        listviewMascotas.setAdapter(_listadoAd);

        interfaceTocoMascota = (tocoMascota) inflater.getContext();

        listviewMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("", "TAG ---->" + view.getTag());
                interfaceTocoMascota.cargaMascotaConMoelo((FCFMMascota) view.getTag());
            }
        });
        return view;
    }

    @Override
    public void estaDescarga(String string) {
        try {
            JSONArray array = new JSONArray(string);
            _listaMascotas.clear();
            for (int i = 0; i < array.length(); i++) {
                _listaMascotas.add(new FCFMMascota(array.optJSONObject(i)));
            }
            _listadoAd.set_lista(_listaMascotas);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface tocoMascota {
        public void cargaMascotaConMoelo(FCFMMascota mascotaModelo);
    }

    ;
}
