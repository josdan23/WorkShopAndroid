package com.josdan.workshopandroid.data;

import com.josdan.workshopandroid.dominio.Publicacion;

import java.util.List;

public interface IListenerApi {

    void dataLoaded(List<Publicacion> publicaciones);

    void showError(Throwable throwable);
}
