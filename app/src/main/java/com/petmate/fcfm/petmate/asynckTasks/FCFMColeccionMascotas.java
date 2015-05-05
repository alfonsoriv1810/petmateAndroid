package com.petmate.fcfm.petmate.asynckTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.loopj.android.http.JsonHttpResponseHandler;

import com.loopj.android.http.SyncHttpClient;
import com.petmate.fcfm.petmate.MainActivity;
import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.constantes.FCFMSingleton;
import com.petmate.fcfm.petmate.fragmentos.FCFMListadoFragment;
import com.petmate.fcfm.petmate.modelos.FCFMMascota;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alfonso on 04/05/15.
 */
public class FCFMColeccionMascotas extends AsyncTask<Void, Void, Void> {

    private static SyncHttpClient clientGetListadoMascotas = new SyncHttpClient();
    public static ArrayList<FCFMMascota> arregloMascotas;
    public static Context context;

    public FCFMColeccionMascotas(Context context) {
        this.context = context;
    }

    public static void descargaMascotas(){
        clientGetListadoMascotas.get(FCFMSingleton.baseURL + "/petmateServicios/get_mascotas.php", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.e("--------> Descarga"," Esto es lo que descargo " + response);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject c = response.getJSONObject(i);
                        String idMascota = c.getString("mascota_id");
                        String nombreMascota = c.getString("mascota_nombre");
                        String colorMascota = c.getString("mascota_color");
                        String razaMascota = c.getString("raza_nombre");

                        FCFMMascota mascota = new FCFMMascota(idMascota,nombreMascota,razaMascota,colorMascota);
                        arregloMascotas.add(mascota);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (arregloMascotas != null) {
                    ((MainActivity) context).finishedLoadingListView(arregloMascotas);
                }
            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        FCFMSingleton.muestraPrograsDialogConTexto(context.getString(R.string.tituloCargando),context);
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        FCFMSingleton.escondeProgressDialog();
    }

    @Override
    protected Void doInBackground(Void... params) {
        descargaMascotas();
        return null;
    }
}
