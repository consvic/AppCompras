package com.example.cocoy.appcompras.BaseDatos;

import java.util.Date;

/**
 * Created by cocoy on 11/10/2017.
 */

public class HistoryPurchase {
    private String id;
    private Date history_date;
    private String real_date;
    private int elements;
    private float total;

    public HistoryPurchase(String id, Date history_date, int elements, float total) {
        this.id = id;
        this.history_date = history_date;
        this.elements = elements;
        this.total = total;
    }

    public HistoryPurchase(String id, String real_date, int elements, float total) {
        this.id = id;
        this.real_date = real_date;
        this.elements = elements;
        this.total = total;
    }
    public String getId() {
        return id;
    }

    public String getHistory_dateString() {
        history_date.toString();
        return history_date.toString();
    }

    public int getElements() {
        return elements;
    }

    public float getTotal() {
        return total;
    }

    public String getReal_date() {
        return real_date;
    }
}
