package com.example.cocoy.appcompras.BaseDatos;

/**
 * Created by cocoy on 08/10/2017.
 */

public class Product {
    private String id;
    private String name;
    private float price;
    private String path;
    private int image;

    public Product(String id, String name, float price, String path) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.path = path;
    }

    public Product(String name, float price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public float getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}
