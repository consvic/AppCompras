package com.example.cocoy.appcompras.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by cocoy on 11/10/2017.
 */
// TODO 3: Creamos clase que extiende a SQLiteOpenHelper

public class DataBaseHelper extends SQLiteOpenHelper{

    //TODO 4: declaramos variables para nombre y versión de DB
    private static final String DB_NOMBRE = "compras.db";
    private static final int DB_VERSION = 1;
    private final Context context;

    //TODO 5: Creación de sentencia SQL para crear tabla
    public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE "
                            + ProductContract.Entry.NOMBRE_TABLA + "("
                            + ProductContract.Entry.COLUMNA_ID + " TEXT PRIMARY KEY, "
                            + ProductContract.Entry.COLUMNA_NOMBRE + " TEXT NOT NULL, "
                            + ProductContract.Entry.COLUMNA_PRECIO + " REAL NOT NULL, "
                            + ProductContract.Entry.COLUMNA_FOTO + " TEXT NOT NULL" + ")";

    public static final String CREATE_DETAIL_TABLE = "CREATE TABLE "
                            + DetailPurchaseContract.Entry.NOMBRE_TABLA + "("
                            + DetailPurchaseContract.Entry.COLUMNA_ID + " TEXT NOT NULL, "
                            + DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO + " TEXT NOT NULL, "
                            + DetailPurchaseContract.Entry.COLUMNA_NOMBRE + " TEXT NOT NULL, "
                            + DetailPurchaseContract.Entry.COLUMNA_CANTIDAD + " INTEGER NOT NULL, "
                            + DetailPurchaseContract.Entry.COLUMNA_SUBTOTAL + " REAL NOT NULL, "
                            + "FOREIGN KEY ("+ DetailPurchaseContract.Entry.COLUMNA_ID_PRODUCTO + ") "
                            + "REFERENCES "+ ProductContract.Entry.NOMBRE_TABLA
                            + " (" + ProductContract.Entry.COLUMNA_ID + "),"
                            + "FOREIGN KEY ("+ DetailPurchaseContract.Entry.COLUMNA_ID + ") "
                            + "REFERENCES "+ HistoryPurchaseContract.Entry.NOMBRE_TABLA
                            + " (" + HistoryPurchaseContract.Entry.COLUMNA_ID+"))";

    public static final String CREATE_HISTORY_TABLE = "CREATE TABLE "
                            + HistoryPurchaseContract.Entry.NOMBRE_TABLA + "("
                            + HistoryPurchaseContract.Entry.COLUMNA_ID + " TEXT PRIMARY KEY, "
                            + HistoryPurchaseContract.Entry.COLUMNA_FECHA + " DATE NOT NULL, "
                            + HistoryPurchaseContract.Entry.COLUMNA_ELEMENTOS + " INTEGER NOT NULL, "
                            + HistoryPurchaseContract.Entry.COLUMNA_TOTAL + " REAL NOT NULL"+ ")";


    //TODO 6: Creación de sentencia SQL para eliminar tabla
    private static final String SQL_DELETE_ENTRIES_PRODUCT = "DROP TABLE IF EXISTS"
                                        + ProductContract.Entry.NOMBRE_TABLA;
    private static final String SQL_DELETE_ENTRIES_DETAIL= "DROP TABLE IF EXISTS"
            + DetailPurchaseContract.Entry.NOMBRE_TABLA;
    private static final String SQL_DELETE_ENTRIES_HISTORY = "DROP TABLE IF EXISTS"
            + HistoryPurchaseContract.Entry.NOMBRE_TABLA;

    //TODO 7: Constructor
    public DataBaseHelper(Context context) {

        super(context,DB_NOMBRE,null,DB_VERSION);
        this.context = context;
    }

    //TODO 8: Para mandar a crear las tablas
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_DETAIL_TABLE);
    }

    //TODO 9: Para actualizar las tablas cuando cambie de versión la DB
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_PRODUCT);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_DETAIL);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES_HISTORY);
        onCreate(sqLiteDatabase);
    }
}
