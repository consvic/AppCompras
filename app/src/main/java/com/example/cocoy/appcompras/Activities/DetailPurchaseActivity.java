package com.example.cocoy.appcompras.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cocoy.appcompras.Adapters.RecyclerViewClickListener;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter2;
import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.BaseDatos.DetailPurchase;
import com.example.cocoy.appcompras.BaseDatos.DetailPurchaseContract;
import com.example.cocoy.appcompras.R;

import java.util.ArrayList;

public class DetailPurchaseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewClickListener listener;
    private RecyclerViewCustomAdapter2 adapter;
    private ArrayList<DetailPurchase> detailPurchases = new ArrayList<>();
    ComprasCRUD comprasCRUD;

    TextView tvTotal;

    String id_detail;
    float totalf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_purchase);

        Bundle bundle = getIntent().getExtras();
        totalf = bundle.getFloat("total_history");
        id_detail = bundle.getString("id_history");

        comprasCRUD = new ComprasCRUD(this);
        //detailPurchases = comprasCRUD.selectDetails(DetailPurchaseContract.Entry.COLUMNA_ID + "=" + id_detail);
        detailPurchases = comprasCRUD.getTheDetailsH(id_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDetail);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //EJEMPLO

        adapter = new RecyclerViewCustomAdapter2(detailPurchases, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        tvTotal = (TextView) findViewById(R.id.tvTotalDetail);
        tvTotal.setText("Total: $"+totalf);
    }
}
