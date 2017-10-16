package com.example.cocoy.appcompras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cocoy.appcompras.Adapters.RecyclerViewClickListener;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter2;
import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.BaseDatos.DetailPurchase;
import com.example.cocoy.appcompras.BaseDatos.DetailPurchaseContract;
import com.example.cocoy.appcompras.BaseDatos.HistoryPurchase;
import com.example.cocoy.appcompras.BaseDatos.HistoryPurchaseContract;
import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Spinner spinner;
    private EditText etCantidad;
    private FloatingActionButton fabSaveCar, fabAddProduct;
    private RecyclerViewClickListener listener;
    private RecyclerViewCustomAdapter2 adapter;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Integer> integers = new ArrayList<>();
    ArrayList<DetailPurchase> detailPurchases = new ArrayList<>();
    private HistoryPurchase historyPurchase;
    private String id;
    private Date history_date;
    private int elements;
    private float total;
    int posSelection = 0;
    String quantity;
    int quantityInt;
    float subtotal;
    ComprasCRUD comprasCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layoutCarrito);

        comprasCRUD = new ComprasCRUD(this);
        products = comprasCRUD.getProducts();
        collapsingToolbar.setTitle("$0 \n 0 elementos");


        addItemsSpinner();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCarrito);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //EJEMPLO
        id = "fake";

        adapter = new RecyclerViewCustomAdapter2(detailPurchases, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        etCantidad = (EditText) findViewById(R.id.etCantidad);

        fabAddProduct = (FloatingActionButton) findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListenerOnSpinnerItemSelection();
                saveData();
                //integers.add(quantityInt);
                if(quantityInt != 0) {
                    detailPurchases.add(new DetailPurchase(id,
                            products.get(posSelection).getId(),
                            products.get(posSelection).getName(),
                            quantityInt,
                            subtotal));
                    Log.d("quantity", quantity);
                    Log.d("subtotal", subtotal + "");
                    Log.d("size array", detailPurchases.size() + "");
                }
                adapter.notifyDataSetChanged();
            }
        });

        fabSaveCar = (FloatingActionButton) findViewById(R.id.fabSaveCar);
        fabSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailPurchases != null) {
                    int totalPurchase = 0;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date now;
                    String other = dateFormat.format(new Date());
                    String id_history = HistoryPurchaseContract.Entry.generarIdHistoryOrder();
                    try {
                        now = dateFormat.parse(dateFormat.format(new Date()));
                        for (int i = 0; i<detailPurchases.size();i++){
                            totalPurchase += detailPurchases.get(i).getSubtotal();
                        }
                        HistoryPurchase historyPurchase = new HistoryPurchase(
                                id_history,
                                other,
                                detailPurchases.size(),
                                totalPurchase
                        );
                        Log.d("date: ", other);
                            comprasCRUD.newHistoryPurchase(historyPurchase);
                        for (int i = 0; i<detailPurchases.size();i++){
                            comprasCRUD.newDetailPurchase(detailPurchases.get(i),id_history);
                        }
                        //Log.d("history: ", comprasCRUD.getHistories().size()+"");
                        //Log.d(("details: "),comprasCRUD.getDetailPurchases().size()+"");
                        startActivity(new Intent(CarritoActivity.this, MainActivity.class));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public void saveData() {
        quantity = etCantidad.getText().toString();
        quantityInt = Integer.parseInt(quantity);
        subtotal = quantityInt * products.get(posSelection).getPrice();
    }

    public void addItemsSpinner() {
        spinner = (Spinner) findViewById(R.id.list_spinner);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i<products.size();i++){
            list.add(products.get(i).getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.list_spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
            posSelection = pos;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}
