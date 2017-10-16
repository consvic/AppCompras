package com.example.cocoy.appcompras.Adapters;

import android.view.View;

/**
 * Created by cocoy on 13/10/2017.
 */

public class RowViewHolder3
        extends RecyclerViewCustomAdapter3.CustomViewHolder
        implements View.OnClickListener {

    private RecyclerViewClickListener listener;

    public RowViewHolder3(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.listener = listener;
        // TODO: 9.- Asignamos a nuestro itemView el evento listener de la clase que está implementando
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO: 10.- Cuando se de click el evento onClick de nuestra interfaz va a tomar la acción
        listener.onClick(view, getAdapterPosition());
    }
}