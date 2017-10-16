package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 11/10/2017.
 */
import android.provider.BaseColumns;

import java.util.UUID;

// TODO 2: Creación de clase ProductContract

public final class ProductContract {
    private ProductContract() {}

    public static class Entry implements BaseColumns {
        public static final String NOMBRE_TABLA = "productos";
        public static final String COLUMNA_ID = "id_producto";
        public static final String COLUMNA_NOMBRE = "nombre_producto";
        public static final String COLUMNA_PRECIO = "precio_producto";
        public static final String COLUMNA_FOTO = "foto_producto";

        public static String generarIdProducto() {
            return "PRO-" + UUID.randomUUID().toString();
        }

    }
}
