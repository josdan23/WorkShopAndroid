package com.josdan.workshopandroid.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josdan.workshopandroid.R;
import com.josdan.workshopandroid.dominio.Publicacion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ViewHolder>
implements View.OnClickListener{

    private View.OnClickListener listener;
    private List<Publicacion> publicaciones;
    private Context context;

    public ItemAdapter( Context context, List<Publicacion> publicaciones) {
        this.context = context;
        this.publicaciones = publicaciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publicacion_item, viewGroup, false);

        v.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tituloTv.setText(publicaciones.get(i).getTitulo());
        viewHolder.estadoTv.setText(publicaciones.get(i).getEstado());

        String precio = "$ " + publicaciones.get(i).getPrecio();

        viewHolder.precioTv.setText(precio);
        viewHolder.idPosition = publicaciones.get(i).getIdPublicacion();
        Picasso.with(context).load(publicaciones.get(i).getImagen()).placeholder(R.drawable.img_placeholder).into(viewHolder.imageViewTV);
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    @Override
    public long getItemId(int position) {
        return publicaciones.get(position).getIdPublicacion();
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}

class ViewHolder extends RecyclerView.ViewHolder{

    public TextView tituloTv;
    public TextView estadoTv;
    public TextView precioTv;
    public ImageView imageViewTV;
    public int idPosition;

    public ViewHolder(View v){
        super(v);
        tituloTv =  v.findViewById(R.id.tituloTv);
        estadoTv =  v.findViewById(R.id.estadoTv);
        precioTv =  v.findViewById(R.id.precioTv);
        imageViewTV = v.findViewById(R.id.imagenIv);
    }

}
