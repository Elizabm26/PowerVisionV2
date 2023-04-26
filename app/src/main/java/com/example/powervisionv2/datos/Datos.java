package com.example.powervisionv2.datos;

public class Datos {
    private String nombre;
    private String pais;
    private String correo;
    private String contraseña;
    private String rol;

    public Datos() {
    }

    public Datos(String nombre, String pais, String correo, String contraseña, String rol) {
        this.nombre = nombre;
        this.pais = pais;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
