package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 11/10/2017.
 */

import android.provider.BaseColumns;

public final class DetailPurchaseContract {
    private DetailPurchaseContract(){}

    public static class Entry implements BaseColumns {
        public static final String NOMBRE_TABLA = "detalle_compra";
        public static final String COLUMNA_ID = "id_purchase";
        public static final String COLUMNA_NOMBRE = "name_purchase";
        public static final String COLUMNA_ID_PRODUCTO = "id_name_purchase_fk";
        public static final String COLUMNA_CANTIDAD = "quantity_purchase";
        public static final String COLUMNA_SUBTOTAL = "subtotal_purchase";
    }
}
