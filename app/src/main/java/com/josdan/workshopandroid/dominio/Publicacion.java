package com.josdan.workshopandroid.dominio;

import java.util.Date;

public class Publicacion {
    private int idPublicacion;
    private String imagen;
    private String titulo;
    private String estado;
    private double precio;
    private Date fechaDePublicacion;

    private Usuario usuario;

    public Publicacion(){

    }

    public Publicacion(int idPublicacion, String imagen, String titulo, String estado, double precio, Date fechaDePublicacion, Usuario usuario) {
        this.idPublicacion = idPublicacion;
        this.imagen = imagen;
        this.titulo = titulo;
        this.estado = estado;
        this.precio = precio;
        this.fechaDePublicacion = fechaDePublicacion;
        this.usuario = usuario;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaDePublicacion() {
        return fechaDePublicacion;
    }

    public void setFechaDePublicacion(Date fechaDePublicacion) {
        this.fechaDePublicacion = fechaDePublicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
