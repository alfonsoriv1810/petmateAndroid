package com.petmate.fcfm.petmate.modelos;

/**
 * Created by alfonso on 03/05/15.
 */
public class FCFMRaza {

    private String razaId;
    private String razaNombre;

    public FCFMRaza() {}

    public FCFMRaza(String razaIdJson, String razaNombreJson) {
        this.razaId = razaIdJson;
        this.razaNombre = razaNombreJson;
    }

    public String getRazaId() {
        return razaId;
    }

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
