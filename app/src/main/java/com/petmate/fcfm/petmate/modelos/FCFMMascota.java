package com.petmate.fcfm.petmate.modelos;

import java.net.URL;

/**
 * Created by alfonso on 24/04/15.
 */
public class FCFMMascota {

    String nombreMascota;
    String razaMascota;
    URL urlImagenMascota;
    Boolean sexoMascota;

    public URL getUrlImagenMascota() {
        return urlImagenMascota;
    }

    public void setUrlImagenMascota(URL urlImagenMascota) {
        this.urlImagenMascota = urlImagenMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getRazaMascota() {
        return razaMascota;
    }

    public void setRazaMascota(String razaMascota) {
        this.razaMascota = razaMascota;
    }

    public Boolean getSexoMascota() {
        return sexoMascota;
    }

    public void setSexoMascota(Boolean sexoMascota) {
        this.sexoMascota = sexoMascota;
    }

    public FCFMMascota() {

    }
}
