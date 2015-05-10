package com.petmate.fcfm.petmate.modelos;

import org.json.JSONObject;

/**
 * Created by alfonso on 03/05/15.
 */
public class FCFMRaza {

    private String razaId;
    private String razaNombre;
    private String urlFoto;

    public FCFMRaza() {}

    public FCFMRaza(String razaIdJson, String razaNombreJson, String urlFoto) {
        this.razaId = razaIdJson;
        this.razaNombre = razaNombreJson;
        this.urlFoto = urlFoto;
    }

    public FCFMRaza(JSONObject jsonRaza){
        this.razaId = jsonRaza.optString("raza_id", "");
        this.razaNombre = jsonRaza.optString("raza_nombre","");
        this.urlFoto = jsonRaza.optString("raza_foto","");
    }

    public String getUrlFoto() {return urlFoto;}

    public void setUrlFoto(String urlFoto) {this.urlFoto = urlFoto;}

    public String getRazaId() {return razaId;}

    public void setRazaId(String razaId) {
        this.razaId = razaId;
    }

    public String getRazaNombre() {
        return razaNombre;
    }

    public void setRazaNombre(String razaNombre) {
        this.razaNombre = razaNombre;
    }
}
