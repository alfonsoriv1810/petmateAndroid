package com.petmate.fcfm.petmate.modelos;

import org.json.JSONObject;

import java.net.URL;

/**
 * Created by alfonso on 24/04/15.
 */
public class FCFMMascota {

   private int idMascota;
   private String nombreMascota;
   private String razaMascota;
   private String colorMascota;
   private String urlImagenMascota;
   private Boolean sexoMascota;

    public FCFMMascota() {

    }


    public FCFMMascota(JSONObject object){
        setIdMascota(Integer.parseInt(object.optString("mascota_id","")));
        setNombreMascota(object.optString("mascota_nombre",""));
        setRazaMascota(object.optString("raza_nombre",""));
        setColorMascota(object.optString("mascota_color",""));
        setUrlImagenMascota(object.optString("mascota_foto",""));
    }

    public String getUrlImagenMascota() {return urlImagenMascota;}

    public void setUrlImagenMascota(String urlImagenMascota) {this.urlImagenMascota = urlImagenMascota;}

    public String getNombreMascota() {return nombreMascota;}

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getRazaMascota() {return razaMascota;}

    public void setRazaMascota(String razaMascota) {
        this.razaMascota = razaMascota;
    }

    public Boolean getSexoMascota() {
        return sexoMascota;
    }

    public void setSexoMascota(Boolean sexoMascota) {
        this.sexoMascota = sexoMascota;
    }

    public String getColorMascota() {return colorMascota;}

    public void setColorMascota(String colorMascota) {this.colorMascota = colorMascota;}

    public Boolean isSexoMascota() {return sexoMascota;}

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

}
