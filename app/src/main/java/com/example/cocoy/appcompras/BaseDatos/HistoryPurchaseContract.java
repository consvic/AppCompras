package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 11/10/2017.
 */

import android.provider.BaseColumns;

import java.util.UUID;


public final class HistoryPurchaseContract {
    private HistoryPurchaseContract(){}

    public static class Entry implements BaseColumns {
        public static final String NOMBRE_TABLA = "historial";
        public static final String COLUMNA_ID = "id_purchase";
        public static final String COLUMNA_FECHA = "history_date";
        public static final String COLUMNA_ELEMENTOS = "elements_purchase";
        public static final String COLUMNA_TOTAL = "total_purchase";

        public static String generarIdHistoryOrder() {
            return "HO-" + UUID.randomUUID().toString();
        }
    }
}
