package com.josdan.workshopandroid.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.josdan.workshopandroid.dominio.Publicacion;
import com.josdan.workshopandroid.dominio.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Api implements com.josdan.workshopandroid.presentation.IApi {

    private static final String URL_API_SERVER = "https://workshop-go-utnfrt.herokuapp.com/purchases";

    private IListenerApi listener;
    private List<Publicacion> publicacionesCached;
    private Boolean dataCached = false;

    private static Api instancia;

    private Api(){
    }

    public static Api getInstancia(){
        if(instancia == null)
            instancia = new Api();
        return instancia;
    }

    @Override
    public void loadData(Context context) {

        if(publicacionesCached != null)
            listener.dataLoaded(publicacionesCached);
        else{
            requestData(context);
        }

    }

    private void requestData(Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_API_SERVER,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("OK", "onResponse: ");
                        publicacionesCached = parseJson(response);
                        listener.dataLoaded(publicacionesCached);
                        dataCached = true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "Error de respuesta");
                        listener.showError(error.getCause());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void setListener(IListenerApi listener) {
        this.listener = listener;
    }

    private List<Publicacion> parseJson(JSONArray response) {

        List<Publicacion> publi = new ArrayList<>();

        try{

            for (int i = 0; i < response.length(); i++){
                JSONObject object = response.getJSONObject(i);

                Usuario user = parseJsonUsuario(object.getJSONObject("user"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                Date fechaPublicacion = format.parse(object.getString("creation_date"));

                Publicacion nuevaPublicacion = new Publicacion(
                        object.getInt("id"),
                        object.getString("image"),
                        object.getString("title"),
                        object.getString("status"),
                        object.getDouble("amount"),
                        fechaPublicacion,
                        user
                );

                publi.add(nuevaPublicacion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return publi;
    }

    private Usuario parseJsonUsuario(JSONObject object){

        Usuario usuario;

        try {
            usuario = new Usuario(
                    object.getInt("user_id"),
                    object.getString("name"),
                    object.getString("last_name"),
                    object.getString("dni")
            );

            return usuario;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Publicacion getPublicacion(int idPublicacion) {

        for (Publicacion publicacion: publicacionesCached) {
            if(publicacion.getIdPublicacion() == idPublicacion)
                return publicacion;
        }
        return null;
    }

    @Override
    public void updateData(Context context){
        publicacionesCached = null;
        requestData(context);
    }
}
