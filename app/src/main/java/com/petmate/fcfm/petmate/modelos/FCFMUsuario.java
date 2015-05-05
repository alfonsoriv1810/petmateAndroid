package com.petmate.fcfm.petmate.modelos;

import com.google.gson.Gson;

import java.net.URI;

/**
 * Created by alfonso on 03/05/15.
 */
public class FCFMUsuario {
    private String username;
    private String password;
    private String nombre;
    private String telefono;
    private String estado;
    private URI urlFoto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public URI getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(URI urlFoto) {
        this.urlFoto = urlFoto;
    }

    public FCFMUsuario() {

    }

    public FCFMUsuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
        Gracias a la libreria GSON que estamos usando, podemos convertir todoo un objeto de JAVA a formato JSON.

        Ejemplo:
            Si esta clase User tiene 2 propiedades, una es username y otra es password, al momento de llamara a este metodo
            que estamos creando "toJSON" nos va a convertir la clase a formato JSON:

            Resultado en JSON = {"password":"pablo01","username":"pablo"}
     */
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
