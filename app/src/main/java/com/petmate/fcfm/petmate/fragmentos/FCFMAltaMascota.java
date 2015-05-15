package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.modelos.FCFMRaza;
import com.petmate.fcfm.petmate.sqllite.AdminSQLiteOpenHelper;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alfonso on 06/05/15.
 */
public class FCFMAltaMascota extends Fragment implements DescargaServicio.onDowloadList {

    private ArrayList<FCFMRaza> _listaRazas = new ArrayList<>();
    private ArrayList<String> _listaRazasString = new ArrayList<>();
    private int idRaza;
    private int sexoMascotaNueva;
    private boolean tocoBotonAgregar;
    private FCFMRaza razaSeleccionada;
    Button botonAgregarMascota;

    EditText editTextNombreMascotaAgrega;
    EditText editTextColorMascotaAgrega;

    public DescargaServicio descargaServicio;

    private LinkedList<FCFMMascota> _listaMascotas = new LinkedList<>();

    AdminSQLiteOpenHelper admin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fcfm_alta_mascota, container, false);
        sexoMascotaNueva = 0;
        tocoBotonAgregar = false;
        descargaServicio = new DescargaServicio(this);
        hayRazasGuardadas();
        if (_listaRazas == null) {
            descargaServicio.execute(FCFMSingleton.baseURL + "get_razas.php");
        }

        botonAgregarMascota = (Button) view.findViewById(R.id.botonAgregaMascota);

        editTextNombreMascotaAgrega = (EditText) view.findViewById(R.id.editTextNombreMascotaNueva);
        editTextColorMascotaAgrega  = (EditText) view.findViewById(R.id.editTextColorMascotaNueva);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerRaza);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, _listaRazasString); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        Spinner spinerSexo = (Spinner) view.findViewById(R.id.spinnerSexoMascotaNueva);
        spinerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexoMascotaNueva = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                razaSeleccionada = _listaRazas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNombreMascotaAgrega.getText().toString().equals("") && !editTextColorMascotaAgrega.getText().toString().equals("")) {
                    tocoBotonAgregar = true;
                    FCFMSingleton.muestraPrograsDialogConTexto(getActivity());
                    Log.e("URL AGREGA MASCOTA--------->",""+ FCFMSingleton.baseURL + "insertar_mascota.php?nombre=" + editTextNombreMascotaAgrega.getText().toString()+"&color="+editTextColorMascotaAgrega.getText().toString()+"&foto_path=path&sexo="+sexoMascotaNueva+"&raza="+razaSeleccionada.getRazaId()+"&usuario="+FCFMSingleton.usuario.getIdUsuario());
                    descargaServicio.execute(FCFMSingleton.baseURL + "insertar_mascota.php?nombre=" + editTextNombreMascotaAgrega.getText().toString() + "&color=" + editTextColorMascotaAgrega.getText().toString() + "&foto_path=path&sexo=" + sexoMascotaNueva + "&raza=" + razaSeleccionada.getRazaId() + "&usuario=" + FCFMSingleton.usuario.getIdUsuario());
                    editTextColorMascotaAgrega.setText("");
                    editTextNombreMascotaAgrega.setText("");
                }
            }
        });
        return view;
    }

    void hayRazasGuardadas() {
        admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from Razas ", null);
        if (fila.moveToFirst()) {
            while(!fila.isAfterLast()) {
                String idRaza = fila.getString(fila.getColumnIndex("idRaza"));
                String nombreRaza = fila.getString(fila.getColumnIndex("nombreRaza"));
                String fotoRaza = fila.getString(fila.getColumnIndex("fotoRaza"));

                FCFMRaza raza = new FCFMRaza(idRaza, nombreRaza, fotoRaza);
                _listaRazas.add(raza);
                _listaRazasString.add(raza.getRazaNombre());
                fila.moveToNext();
                razaSeleccionada = _listaRazas.get(0);
            }
        } else {
            DescargaServicio descargaServicio = new DescargaServicio(this);
            descargaServicio.execute(FCFMSingleton.baseURL + "get_razas.php");
        }
    }

    @Override
    public void estaDescarga(String string) {

        if (tocoBotonAgregar){
            tocoBotonAgregar = false;
            try {
                JSONArray array = new JSONArray(string);
                _listaMascotas.clear();
                for (int i = 0; i < array.length(); i++) {
                    _listaMascotas.add(new FCFMMascota(array.optJSONObject(i)));
                }
                FCFMSingleton.adapterHome.set_lista(_listaMascotas);
                FCFMSingleton.escondeProgressDialog();
                FCFMSingleton.showMessage(getString(R.string.mascotaAgregada),getActivity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }

        admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null,4);
        try {
            JSONArray array = new JSONArray(string);
            if (array.length() > 0 && _listaRazas != null) {
                _listaRazas.clear();
                SQLiteDatabase bd = admin.getWritableDatabase();
                for (int i = 0; i < array.length(); i++) {
                    FCFMRaza raza = new FCFMRaza(array.optJSONObject(i));
                    ContentValues registro = new ContentValues();
                    registro.put("idRaza", raza.getRazaId());
                    registro.put("nombreRaza", raza.getRazaNombre());
                    registro.put("fotoRaza", raza.getUrlFoto());
                    bd.insert("Razas", null, registro);
                    _listaRazas.add(raza);
                    _listaRazasString.add(raza.getRazaNombre());
                }
                bd.close();
            }
            razaSeleccionada = _listaRazas.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}