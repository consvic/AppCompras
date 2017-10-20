package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 11/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ComprasCRUD {
    private DataBaseHelper helper;

    //TODO 10: Creamos el constructor pidiendo de parámetro el contexto
    public ComprasCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void newProduct (Product product) {
        //TODO 11: Solicitamos la base de datos en modo escritura
        //Obtiene la BD en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        //TODO 12: Mapeamos columnas con valores
        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre  de columna
        ContentValues values = new ContentValues();
        values.put(ProductContract.Entry.COLUMNA_ID,product.getId());
        values.put(ProductContract.Entry.COLUMNA_NOMBRE,product.getName());
        values.put(ProductContract.Entry.COLUMNA_PRECIO,product.getPrice());
        values.put(ProductContract.Entry.COLUMNA_FOTO,product.getPath());

        //TODO 13: Insertamos fila
        //Inserta la nueva fila, regresando el valor de la primary key
        long newRowId = db.insert(ProductContract.Entry.NOMBRE_TABLA,null,values);

        //cerrar conexion
        db.close();
    }

    public void newHistoryPurchase(HistoryPurchase historyPurchase) {
        //TODO 11: Solicitamos la base de datos en modo escritura
        //Obtiene la BD en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        //TODO 12: Mapeamos columnas con valores
        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre  de columna
        ContentValues values = new ContentValues();
        values.put(HistoryPurchaseContract.Entry.COLUMNA_ID,historyPurchase.getId());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_FECHA,historyPurchase.getReal_date());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_ELEMENTOS,historyPurchase.getElements());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_TOTAL,historyPurchase.getTotal());
        //TODO 13: Insertamos fila
        //Inserta la nueva fila, regresando el valor de la primary key
        long newRowId = db.insert(HistoryPurchaseContract.Entry.NOMBRE_TABLA,null,values);

        //cerrar conexion
        db.close();
    }

    public void newDetailPurchase(DetailPurchase detailPurchase, String id) {
        //TODO 11: Solicitamos la base de datos en modo escritura
        //Obtiene la BD en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        //TODO 12: Mapeamos columnas con valores
        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombre  de columna
        /*ContentValues values = new ContentValues();
        values.put(DetailPurchaseContract.Entry.COLUMNA_ID, detailPurchase.getId());
        values.put(DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO, detailPurchase.getId_product());
        values.put(DetailPurchaseContract.Entry.COLUMNA_NOMBRE,detailPurchase.getProduct_name());
        values.put(DetailPurchaseContract.Entry.COLUMNA_CANTIDAD,detailPurchase.getQuantity());
        values.put(DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL,detailPurchase.getSubtotal());
        */

        String query = "INSERT INTO " + DetailPurchaseContract.Entry.NOMBRE_TABLA
                + " VALUES ("
                + "'" + id + "',"
                + "'" + detailPurchase.getId_product() + "',"
                + "'" + detailPurchase.getProduct_name() + "',"
                + detailPurchase.getQuantity() + ","
                + detailPurchase.getSubtotal()
                + ")";
        //TODO 13: Insertamos fila
        //Inserta la nueva fila, regresando el valor de la primary key
        db.execSQL(query);
        //long newRowId = db.insert(DetailPurchaseContract.Entry.NOMBRE_TABLA,null,values);

        //cerrar conexion
        db.close();
    }

    public ArrayList<Product> getProducts() {
        //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<Product> products = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        //Especificamos las columnas a usar
        String []columnas = {
                ProductContract.Entry.COLUMNA_ID,
                ProductContract.Entry.COLUMNA_NOMBRE,
                ProductContract.Entry.COLUMNA_PRECIO,
                ProductContract.Entry.COLUMNA_FOTO
        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = sqLiteDatabase.query(
                ProductContract.Entry.NOMBRE_TABLA,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            products.add(new Product(
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_NOMBRE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_PRECIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_FOTO))
            ));
        }

        //TODO 17: Cerramos conexión y regresamos elementos
        cursor.close();
        return products;
    }

    public ArrayList<HistoryPurchase> getHistories() throws ParseException {
        //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<HistoryPurchase> historyPurchases = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        //Especificamos las columnas a usar
        String []columnas = {
                HistoryPurchaseContract.Entry.COLUMNA_ID,
                HistoryPurchaseContract.Entry.COLUMNA_FECHA,
                HistoryPurchaseContract.Entry.COLUMNA_ELEMENTOS,
                HistoryPurchaseContract.Entry.COLUMNA_TOTAL
        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = sqLiteDatabase.query(
                HistoryPurchaseContract.Entry.NOMBRE_TABLA,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            //Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(HistoryPurchaseContract.Entry.COLUMNA_FECHA)));
            historyPurchases.add(new HistoryPurchase(
                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryPurchaseContract.Entry.COLUMNA_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(HistoryPurchaseContract.Entry.COLUMNA_FECHA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(HistoryPurchaseContract.Entry.COLUMNA_ELEMENTOS)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(HistoryPurchaseContract.Entry.COLUMNA_TOTAL))
            ));
        }

        //TODO 17: Cerramos conexión y regresamos elementos
        cursor.close();
        return historyPurchases;
    }

    public ArrayList<DetailPurchase> getDetailPurchases() {
        //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
        ArrayList<DetailPurchase> detailPurchases = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        //Especificamos las columnas a usar
        String []columnas = {
                DetailPurchaseContract.Entry.COLUMNA_ID,
                DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO,
                DetailPurchaseContract.Entry.COLUMNA_NOMBRE,
                DetailPurchaseContract.Entry.COLUMNA_CANTIDAD,
                DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL
        };

        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = sqLiteDatabase.query(
                DetailPurchaseContract.Entry.NOMBRE_TABLA,
                columnas,
                null, //texto para filtrar
                null, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            detailPurchases.add(new DetailPurchase(
                    cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_NOMBRE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_CANTIDAD)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL))
            ));
        }

        //TODO 17: Cerramos conexión y regresamos elementos
        cursor.close();
        return detailPurchases;
    }


    public void updateProduct (Product product) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(ProductContract.Entry.COLUMNA_ID,product.getId());
        values.put(ProductContract.Entry.COLUMNA_NOMBRE,product.getName());
        values.put(ProductContract.Entry.COLUMNA_PRECIO,product.getPrice());
        values.put(ProductContract.Entry.COLUMNA_FOTO,product.getPath());

        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                ProductContract.Entry.NOMBRE_TABLA,
                values,
                ProductContract.Entry.COLUMNA_ID+" = ?",
                new String[]{String.valueOf(product.getId())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void updateHistoryPurchase (HistoryPurchase historyPurchase) {
        //Obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //Crea un nuevo mapa de valores, de tipo clave-valor, donde clave es nombrre de columna
        ContentValues values = new ContentValues();
        values.put(HistoryPurchaseContract.Entry.COLUMNA_ID,historyPurchase.getId());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_FECHA,historyPurchase.getHistory_dateString());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_ELEMENTOS,historyPurchase.getElements());
        values.put(HistoryPurchaseContract.Entry.COLUMNA_TOTAL,historyPurchase.getTotal());

        //TODO 19: Actualizamos fila
        //Insterta la nueva fila, regresando el valor de la primary key
        sqLiteDatabase.update(
                HistoryPurchaseContract.Entry.NOMBRE_TABLA,
                values,
                HistoryPurchaseContract.Entry.COLUMNA_ID+" = ?",
                new String[]{String.valueOf(historyPurchase.getId())}
        );

        //cierra conexion
        sqLiteDatabase.close();
    }

    public void deleteProduct(Product product) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                ProductContract.Entry.NOMBRE_TABLA,
                ProductContract.Entry.COLUMNA_ID+" = ?",
                new String[]{String.valueOf(product.getId())}
        );

        sqLiteDatabase.close();
    }

    public void deleteProduct(String id) {
        //obtiene la BD en modo de escritura
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        //TODO 20: Eliminamos fila
        //inserta la nueva, regresando el valor de la primary key
        sqLiteDatabase.delete(
                ProductContract.Entry.NOMBRE_TABLA,
                ProductContract.Entry.COLUMNA_ID+" = ?",
                new String[]{String.valueOf(id)}
        );

        sqLiteDatabase.close();
    }

    public Product selectProduct(String id) {

        String name = "";
        float price = 0.0f;
        String photo = "";
        Product product = new Product(id,name,price,photo);;
        SQLiteDatabase db = helper.getReadableDatabase();
        String []columnas = {
                ProductContract.Entry.COLUMNA_ID,
                ProductContract.Entry.COLUMNA_NOMBRE,
                ProductContract.Entry.COLUMNA_PRECIO,
                ProductContract.Entry.COLUMNA_FOTO
        };
        //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
        Cursor cursor = db.query(
                ProductContract.Entry.NOMBRE_TABLA,
                columnas,
                ProductContract.Entry.COLUMNA_ID+" = ?", //texto para filtrar
                new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                null, // agrupar
                null, // contiene
                null); //limite

        //TODO 16: Se recorren los resultados y se añaden a la lista
        while (cursor.moveToNext()) {
            product = new Product(
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_NOMBRE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_PRECIO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.Entry.COLUMNA_FOTO))
            );
        }

        cursor.close();
        db.close();
        return product;
    }

    public ArrayList<DetailPurchase> selectDetails(String comparison) {
        String id = "";
        String id_product;
        String product_name = "";
        int quantity;
        float subtotal;
        ArrayList<DetailPurchase> detailPurchases = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DetailPurchaseContract.Entry.NOMBRE_TABLA
                + " WHERE " + comparison,null);

        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                id_product = c.getString(1);
                product_name = c.getString(2);
                quantity = c.getInt(3);
                subtotal = c.getFloat(4);
                detailPurchases.add(new DetailPurchase(
                        id,
                        id_product,
                        product_name,
                        quantity,
                        subtotal
                ));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return detailPurchases;
    }

    public ArrayList<DetailPurchase> getTheDetailsH(String id) {
            //TODO 14: Crear una lista para almacenar elementos, llamamos Db y definimos columnas
            ArrayList<DetailPurchase> detailPurchases = new ArrayList<>();

            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
            //Especificamos las columnas a usar
            String []columnas = {
                    DetailPurchaseContract.Entry.COLUMNA_ID,
                    DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO,
                    DetailPurchaseContract.Entry.COLUMNA_NOMBRE,
                    DetailPurchaseContract.Entry.COLUMNA_CANTIDAD,
                    DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL
            };

            //TODO 15: Se crea un cursor para hacer recorrido de resultados y se crea una estructura de query
            Cursor cursor = sqLiteDatabase.query(
                    DetailPurchaseContract.Entry.NOMBRE_TABLA,
                    columnas,
                    DetailPurchaseContract.Entry.COLUMNA_ID+" = ?", //texto para filtrar
                    new String[]{String.valueOf(id)}, // arreglo de parametros a filtrar
                    null, // agrupar
                    null, // contiene
                    null); //limite

            //TODO 16: Se recorren los resultados y se añaden a la lista
            while (cursor.moveToNext()) {
                detailPurchases.add(new DetailPurchase(
                        cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_NOMBRE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_CANTIDAD)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL))
                ));
            }

            //TODO 17: Cerramos conexión y regresamos elementos
            cursor.close();
            return detailPurchases;

    }


    public HistoryPurchase selectHistoryPurchase(String comparison) throws ParseException {
        String id = "";
        Date date = new Date(0);
        int elements = 0;
        float total = 0.0f;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        HistoryPurchase historyPurchase = new HistoryPurchase(id,date,elements,total);;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + HistoryPurchaseContract.Entry.NOMBRE_TABLA
                + " WHERE " + comparison,null);

        if (c.moveToFirst()) {
            do {
                id = c.getString(0);
                date = dateFormat.parse(c.getString(1));
                elements = c.getInt(2);
                total = c.getFloat(3);
                historyPurchase = new HistoryPurchase(id,date,elements,total);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return historyPurchase;
    }
}
