package com.petmate.fcfm.petmate.constantes;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.petmate.fcfm.petmate.R;
import com.petmate.fcfm.petmate.adaptadores.AdaptadorMascotasHome;
import com.petmate.fcfm.petmate.adaptadores.FCFMAdaptadorMascotasDetalleUsuario;
import com.petmate.fcfm.petmate.modelos.FCFMUsuario;

/**
 * Created by alfonso on 04/05/15.
 */
public abstract class FCFMSingleton {
    public static final String baseURL = "http://192.168.43.250/petmateServicios/";
    private static ProgressDialog pDialog;

    public static FCFMUsuario usuario = null;

    public static AdaptadorMascotasHome adapterHome = null;
    public static FCFMAdaptadorMascotasDetalleUsuario adaptadorMascotasUsuario = null;

    public static void muestraPrograsDialogConTexto (Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.tituloCargando));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void escondeProgressDialog () {
        if (pDialog.isShowing())
        pDialog.dismiss();
    }

    public static void showMessage(String msg, Context contexto){
        Toast.makeText(contexto, msg, Toast.LENGTH_SHORT).show();
    }
}
