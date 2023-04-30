package com.example.powervisionv2.datos;

public class Datos {
    private static String nombre;
    private static String pais;
    private static String correo;
    private static String contraseña;
    private static String rol;
    private static String plan;

    public Datos() {
    }

    public Datos(String nombre, String pais, String correo, String contraseña, String rol, String plan) {
        this.nombre = nombre;
        this.pais = pais;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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
