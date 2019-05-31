package com.josdan.workshopandroid.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.josdan.workshopandroid.R;
import com.josdan.workshopandroid.data.Api;
import com.josdan.workshopandroid.dominio.Publicacion;
import com.squareup.picasso.Picasso;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        getSupportActionBar().setTitle(R.string.detalle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int idPublicacion = getIntent().getIntExtra(MainActivity.ID_PUBLICACION, 0);

        Publicacion publicacion = Api.getInstancia().getPublicacion(idPublicacion);
        if(publicacion != null)
            mostrarInfo(publicacion);

    }

    public void mostrarInfo(Publicacion publicacion){
        TextView tituloDetalleTv = findViewById(R.id.titulo_detalle_tv);
        tituloDetalleTv.setText(publicacion.getTitulo());

        TextView estadoDetalleTv = findViewById(R.id.estado_detalle_tv);

        switch (publicacion.getEstado()){
            case "new": estadoDetalleTv.setTextColor(getResources().getColor(R.color.colorDisponible));
                        break;
            case "cancelled": estadoDetalleTv.setTextColor(getResources().getColor(R.color.colorNoDisponible));
                        break;
            case "finished":estadoDetalleTv.setTextColor(getResources().getColor(R.color.colorFinalizado));
        }
        estadoDetalleTv.setText((publicacion.getEstado()));

        TextView precioDetalleTv = findViewById(R.id.precio_detalle_view);
        String precio = "$ " + publicacion.getPrecio();
        precioDetalleTv.setText(precio);

        TextView fechaDetalleTv = findViewById(R.id.fecha_publicacion_tv);
        fechaDetalleTv.setText(publicacion.getFechaDePublicacion().toString());

        TextView usuarioDetalleTv = findViewById(R.id.nombre_usuario_tv);
        usuarioDetalleTv.setText(publicacion.getUsuario().getApellido() + ", " + publicacion.getUsuario().getNombre());

        TextView dniDetalleTv = findViewById(R.id.dni_usuario_tv);
        dniDetalleTv.setText(publicacion.getUsuario().getDni());

        ImageView imageViewDetalle = findViewById(R.id.imagenDetalleIv);
        Picasso.with(this).load(publicacion.getImagen()).into(imageViewDetalle);
    }
}
