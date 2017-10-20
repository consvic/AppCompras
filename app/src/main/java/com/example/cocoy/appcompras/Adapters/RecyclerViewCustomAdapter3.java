package com.example.cocoy.appcompras.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocoy.appcompras.BaseDatos.HistoryPurchase;
import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.R;

import java.util.ArrayList;

/**
 * Created by cocoy on 12/10/2017.
 */

public class RecyclerViewCustomAdapter3 extends
        RecyclerView.Adapter<RecyclerViewCustomAdapter3.CustomViewHolder> {

    // TODO: (1) DECLARAR ESTRUCTURA DE DATOS
    private ArrayList<HistoryPurchase> historyPurchases;

    // TODO: 11.- Creamos un miembro derivado de la interfaz creada
    private RecyclerViewClickListener listener;


    // TODO: (2) CONSTRUCTOR
    // TODO: 12.- SE AÑADE AL CONSTRUCTOR EL LISTENER COMO PARÁMETRO
    public RecyclerViewCustomAdapter3(ArrayList<HistoryPurchase> historyPurchases, RecyclerViewClickListener listener) {
        this.historyPurchases = historyPurchases;
        this.listener = listener;
    }


    // TODO: (3) DEFINIMOS EL PATRÓN VIEWHOLDER CREANDO UNA CLASE ESTÁTICA PARA DEFINIR ELEMENTOS UI
    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombre_item;
        private TextView tvPrecioDe,tvCantidad;

        CustomViewHolder(View vista){
            super(vista);

            tvNombre_item = (TextView) vista.findViewById(R.id.tvNombre_item);
            tvPrecioDe = (TextView) vista.findViewById(R.id.tvPrecioDe);
            tvCantidad = (TextView) vista.findViewById(R.id.tvCantidad);

        }
    }

    // TODO: (4) SE IMPLEMENTAN MÉTODOS REQUERIDOS
    @Override
    public int getItemCount() {
        return historyPurchases.size();
    }

    // TODO: (5) EL MÉTODO SIRVE PARA COLOCAR VALORES
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvNombre_item.setText("Compra " + historyPurchases.get(position).getReal_date());
        holder.tvPrecioDe.setText(historyPurchases.get(position).getElements() + " elementos");
        holder.tvCantidad.setText("$"+historyPurchases.get(position).getTotal());
    }


    // TODO: (6) SE USA EL INFLATER PARA LINKEAR EL XML Y ASIGNARLE LOS ELEMENTOS DEL VIEWHOLDER
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_custom2, parent, false);

        //CustomViewHolder customViewHolder = new CustomViewHolder(vista);
        //return customViewHolder;

        // TODO: 13.- Agregamos un objeto RowViewHolder y eliminamos las dos líneas anteriores
        return new RowViewHolder3(vista, listener);
    }

}
