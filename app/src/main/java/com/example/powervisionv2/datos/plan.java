package com.example.powervisionv2.datos;

public class plan {
    private String watts;
    private String nombre;
    private String valor;

    public plan() {
    }

    public plan(String watts, String nombre, String valor) {
        this.watts = watts;
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getWatts() {
        return watts;
    }

    public void setWatts(String watts) {
        this.watts = watts;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
