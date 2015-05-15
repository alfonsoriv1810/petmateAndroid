package com.petmate.fcfm.petmate.fragmentos;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.adaptadores.AdaptadorMascotasHome;
import com.petmate.fcfm.petmate.adaptadores.FCFMAdaptadorMascotasDetalleUsuario;
import com.petmate.fcfm.petmate.asynckTasks.ADLoadImageAsyncTask;
import com.petmate.fcfm.petmate.asynckTasks.ADSaveImageAsyncTask;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;
import com.petmate.fcfm.petmate.utilidades.DescargaServicio;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.Calendar;
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

    private static final String ARCHIVO_IMAGEN_EXPEDIENTE = "FotoExpediente";
    private static final String KEY_IMAGEN_EXPEDIENTE="IMGEXPEDIENTE";

    private File _directoriorRecursos;

    private SharedPreferences _preference;

    private Uri imgSeleccionada;

    private Bitmap _bitmapGlobal;

    private String _nombreimagen;
    private String _pathFinal;
    private static int RESULT_LOAD_IMAGE = 1,CAMERA_PIC_REQUEST = 2;

    private ADSaveImageAsyncTask.ImageSavedListener _listener;
    private ADSaveImageAsyncTask _saveImage;

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
        imagenUsuario = (ImageButton) view.findViewById(R.id.imagenUsuario);
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

        imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam, CAMERA_PIC_REQUEST);
            }
        });

        _directoriorRecursos = new File(getActivity().getFilesDir().getAbsolutePath(), "/resources");
        if (!_directoriorRecursos.exists()) {
            _directoriorRecursos.mkdir();
        }

        _preference = getActivity().getSharedPreferences("fotoUsuario",getActivity().MODE_PRIVATE);

        if (_preference != null) {
            if (_preference.contains(KEY_IMAGEN_EXPEDIENTE)) {
                Log.e("entro a la condicion ", "Lo que tiene---->" + ARCHIVO_IMAGEN_EXPEDIENTE);
                imagenUsuario.setImageDrawable(null);
                ADLoadImageAsyncTask hilo = new ADLoadImageAsyncTask(_directoriorRecursos.getAbsolutePath() + "/" + ARCHIVO_IMAGEN_EXPEDIENTE, imagenUsuario, new ADLoadImageAsyncTask.OnLoadedImage() {
                    @Override
                    public void loadedImage() {
                        Log.e("Cargo la imagen ", "Se cargo---->");
                        SharedPreferences.Editor editor = _preference.edit();
                        editor.putString(KEY_IMAGEN_EXPEDIENTE, ARCHIVO_IMAGEN_EXPEDIENTE);
                        editor.commit();
                    }

                    @Override
                    public void loadedError() {
                        Log.e("Error", "Error al cargar la imagen");
                    }
                });
                hilo.execute();
            }

            cargarImagen();
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        _directoriorRecursos = new File(getActivity().getFilesDir().getAbsolutePath(), "/resources");
        if (!_directoriorRecursos.exists()) {
            _directoriorRecursos.mkdir();
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == getActivity().RESULT_OK && null != data) {
                imgSeleccionada = data.getData();
                _bitmapGlobal = (Bitmap) data.getExtras().get("data");
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                int _nombre = (int) cal.getTimeInMillis();
                _nombreimagen = String.valueOf(_nombre);
                Log.e("_nombreimagen" + _nombreimagen, "_directoriorRecursos" + _directoriorRecursos);
                _saveImage = new ADSaveImageAsyncTask(ARCHIVO_IMAGEN_EXPEDIENTE, _directoriorRecursos.getAbsolutePath(), new ADSaveImageAsyncTask.ImageSavedListener() {
                    @Override
                    public void imageSaved() {
                        Log.e("Cargo la imagen ", "Se cargo---->");
                        SharedPreferences.Editor editor = _preference.edit();
                        editor.putString(KEY_IMAGEN_EXPEDIENTE, ARCHIVO_IMAGEN_EXPEDIENTE);
                        editor.commit();

                        imagenUsuario.setImageBitmap(_bitmapGlobal);
                    }
                });
                _pathFinal = _directoriorRecursos.getAbsolutePath() + "/" + _nombreimagen;
                _saveImage.execute(_bitmapGlobal);
            } else {
                //Toast.makeText(getActivity(), "No se cargo imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cargarImagen (){
        if (_bitmapGlobal != null ){
            ADLoadImageAsyncTask hilo = new ADLoadImageAsyncTask(_directoriorRecursos.getAbsolutePath() + "/" + ARCHIVO_IMAGEN_EXPEDIENTE, imagenUsuario, new ADLoadImageAsyncTask.OnLoadedImage() {
                @Override
                public void loadedImage() {
                    SharedPreferences.Editor editor = _preference.edit();
                    editor.putString(KEY_IMAGEN_EXPEDIENTE, ARCHIVO_IMAGEN_EXPEDIENTE);
                    editor.commit();
                    imagenUsuario.setImageBitmap(_bitmapGlobal);
                }

                @Override
                public void loadedError() {

                }
            });
            hilo.execute();
        }
    }
}
