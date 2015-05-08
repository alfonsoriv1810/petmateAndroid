package com.petmate.fcfm.petmate.modelos;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by alfonso on 03/05/15.
 */
public class FCFMUsuario {
    private int idUsuario;
    private String username;
    private String password;
    private String nombre;
    private String telefono;
    private String estado;
    private String urlFoto;

    public FCFMUsuario() {

    }

    public FCFMUsuario(String username, String password, String nombre, String telefono, String estado, String pathFoto) {
        this.username = username;
        this.password = password;
    }

    public FCFMUsuario(JSONObject jsonUsuario) {
        setIdUsuario(Integer.parseInt(jsonUsuario.optString("usuario_id", "0")));
        setUsername(jsonUsuario.optString("usuario_correo", ""));
        setPassword(jsonUsuario.optString("usuario_contrasena", ""));
        setNombre(jsonUsuario.optString("usuario_nombre", ""));
        setTelefono(jsonUsuario.optString("usuario_telefono", ""));
        setEstado(jsonUsuario.optString("usuario_estado", ""));
        setUrlFoto(jsonUsuario.optString("usuario_foto",""));
    }

    public int getIdUsuario() {return idUsuario;}

    public void setIdUsuario(int idUsuario) {this.idUsuario = idUsuario;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {this.estado = estado;}

    public String getUrlFoto() {return urlFoto;}

    public void setUrlFoto(String urlFoto) {this.urlFoto = urlFoto;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}
}
