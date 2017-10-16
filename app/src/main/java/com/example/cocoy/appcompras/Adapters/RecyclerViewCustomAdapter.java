/*
 * Copyright (C) 2017 Marcos Rivas Rojas
 *
 *
 */
package com.example.cocoy.appcompras.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerViewCustomAdapter extends
        RecyclerView.Adapter<RecyclerViewCustomAdapter.CustomViewHolder> {

    Context mContext;
    // TODO: (1) DECLARAR ESTRUCTURA DE DATOS
    private ArrayList<Product> products;

    // TODO: 11.- Creamos un miembro derivado de la interfaz creada
    private RecyclerViewClickListener listener;


    // TODO: (2) CONSTRUCTOR
    // TODO: 12.- SE AÑADE AL CONSTRUCTOR EL LISTENER COMO PARÁMETRO
    public RecyclerViewCustomAdapter(Context context, ArrayList<Product> products, RecyclerViewClickListener listener) {
        this.mContext = context;
        this.products = products;
        this.listener = listener;
    }


    // TODO: (3) DEFINIMOS EL PATRÓN VIEWHOLDER CREANDO UNA CLASE ESTÁTICA PARA DEFINIR ELEMENTOS UI
    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombre;
        private ImageView ivFoto;
        private TextView tvPrice;

        CustomViewHolder(View vista){
            super(vista);

            tvNombre = (TextView) vista.findViewById(R.id.tvNombre);
            ivFoto = (ImageView) vista.findViewById(R.id.ivFoto);
            tvPrice = (TextView) vista.findViewById(R.id.tvPrice);
        }
    }

    // TODO: (4) SE IMPLEMENTAN MÉTODOS REQUERIDOS
    @Override
    public int getItemCount() {
        return products.size();
    }

    // TODO: (5) EL MÉTODO SIRVE PARA COLOCAR VALORES
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.with(mContext).load(products.get(position).getPath()).into(holder.ivFoto);

        holder.ivFoto.setImageResource(products.get(position).getImage());
        holder.tvNombre.setText(products.get(position).getName());
        holder.tvPrice.setText("$ " + products.get(position).getPrice());
    }


    // TODO: (6) SE USA EL INFLATER PARA LINKEAR EL XML Y ASIGNARLE LOS ELEMENTOS DEL VIEWHOLDER
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fila_custom, parent, false);

        //CustomViewHolder customViewHolder = new CustomViewHolder(vista);
        //return customViewHolder;

        // TODO: 13.- Agregamos un objeto RowViewHolder y eliminamos las dos líneas anteriores
        return new RowViewHolder(vista, listener);
    }

}
