package com.josdan.workshopandroid.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.josdan.workshopandroid.data.Api;
import com.josdan.workshopandroid.data.IListenerApi;
import com.josdan.workshopandroid.R;
import com.josdan.workshopandroid.dominio.Publicacion;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IListenerApi {

    public static final String ID_PUBLICACION = "com.josdan.workshowpandroid.ID_PUBLICACION";

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private ProgressBar progressBar;
    private IApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.publicaciones);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);

        api = Api.getInstancia();
        api.setListener(this);

        if(!comprobarConexion())
        {
            crearAlertDialog(getString(R.string.network_titulo), getString(R.string.network_mensaje));
        }
        else
        {
            api.loadData(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.updateMenu){
            api.updateData(this);
        }
        return true;
    }

    private void itemClick(int idPublicacion) {
        Intent intent  = new Intent(this, DetalleActivity.class);
        intent.putExtra(ID_PUBLICACION, idPublicacion);
        startActivity(intent);
    }

    @Override
    public void dataLoaded(List<Publicacion> publicaciones) {

        progressBar.setVisibility(View.INVISIBLE);

        recyclerView  = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ItemAdapter(this, publicaciones);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id= (int) adapter.getItemId(recyclerView.getLayoutManager().getPosition(v));
                itemClick(id);
            }
        });
    }

    @Override
    public void showError(Throwable throwable) {

        crearAlertDialog(getString(R.string.error_titulo), getString(R.string.error_mensaje));
    }


    private boolean comprobarConexion(){

        boolean conectado = false;

        ConnectivityManager connec =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++){
            if (redes[i].getState() == NetworkInfo.State.CONNECTED){
                conectado = true;
            }
        }

        return conectado;
    }


    private void crearAlertDialog(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        api.loadData(getApplicationContext());
                    }
                });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
