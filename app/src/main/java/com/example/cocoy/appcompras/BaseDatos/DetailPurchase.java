package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 11/10/2017.
 */

public class DetailPurchase {
    private String id;
    private String product_name;
    private String id_product;
    private int quantity;
    private float subtotal;

    public DetailPurchase(String id, String id_product, String product_name, int quantity, float subtotal) {
        this.id = id;
        this.product_name = product_name;
        this.id_product = id_product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getId_product() {
        return id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }
}
