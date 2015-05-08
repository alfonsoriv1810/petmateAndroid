package com.petmate.fcfm.petmate.constantes;

import android.app.ProgressDialog;
import android.content.Context;

import com.petmate.fcfm.petmate.modelos.FCFMUsuario;

/**
 * Created by alfonso on 04/05/15.
 */
public abstract class FCFMSingleton {
    public static final String baseURL = "http://192.168.1.98/petmateServicios/";
    private static ProgressDialog pDialog;

    public static FCFMUsuario usuario = null;

    public static void muestraPrograsDialogConTexto (String textoParaDialog, Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(textoParaDialog);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void escondeProgressDialog () {
        if (pDialog.isShowing())
        pDialog.dismiss();
    }
}
