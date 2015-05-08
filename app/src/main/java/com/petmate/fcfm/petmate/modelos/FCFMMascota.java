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
    private int razaID;
    private String razaURL;
    private String colorMascota;
    private String urlImagenMascota;
    private Boolean sexoMascota;
    private int usuarioID;
    private String usuarioNombre;
    private String usuarioCorreo;

    public FCFMMascota() {

    }


    public FCFMMascota(JSONObject object) {
        setIdMascota(Integer.parseInt(object.optString("mascota_id", "")));
        setNombreMascota(object.optString("mascota_nombre", ""));
        setRazaMascota(object.optString("raza_nombre", ""));
        setColorMascota(object.optString("mascota_color", ""));
        setUrlImagenMascota(object.optString("raza_foto", ""));

        if (object.optString("mascota_sexo").equals("0")){
            setSexoMascota(false);
        } else  {
            setSexoMascota(true);
        }
        setRazaID(Integer.parseInt(object.optString("raza_id", "")));
        setRazaMascota(object.optString("raza_nombre", ""));
        setUsuarioID(Integer.parseInt(object.optString("usuario_id", "")));
        setUsuarioNombre(object.optString("usuario_nombre", ""));
        setUsuarioCorreo(object.optString("usuario_correo",""));
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getRazaURL() {
        return razaURL;
    }

    public void setRazaURL(String razaURL) {
        this.razaURL = razaURL;
    }

    public int getRazaID() {
        return razaID;
    }

    public void setRazaID(int razaID) {
        this.razaID = razaID;
    }

    public String getUrlImagenMascota() {
        return urlImagenMascota;
    }

    public void setUrlImagenMascota(String urlImagenMascota) {
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

    public String getColorMascota() {
        return colorMascota;
    }

    public void setColorMascota(String colorMascota) {
        this.colorMascota = colorMascota;
    }

    public Boolean isSexoMascota() {
        return sexoMascota;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

}
