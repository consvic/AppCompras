package com.example.cocoy.appcompras.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cocoy.appcompras.Adapters.RecyclerViewClickListener;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter3;
import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.BaseDatos.DetailPurchase;
import com.example.cocoy.appcompras.BaseDatos.HistoryPurchase;
import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.R;

import java.text.ParseException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerViewClickListener listener;
    private RecyclerViewCustomAdapter3 adapter;
    ArrayList<HistoryPurchase> historyPurchases = new ArrayList<>();
    ComprasCRUD comprasCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        comprasCRUD = new ComprasCRUD(this);

        try {
            historyPurchases = comprasCRUD.getHistories();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("size histories: ",historyPurchases.size()+"");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistorial);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        adapter = new RecyclerViewCustomAdapter3(historyPurchases, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HistoryActivity.this, "Elemento " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HistoryActivity.this, DetailPurchaseActivity.class);
                intent.putExtra("id_history",historyPurchases.get(position).getId());
                intent.putExtra("total_history",historyPurchases.get(position).getTotal());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
