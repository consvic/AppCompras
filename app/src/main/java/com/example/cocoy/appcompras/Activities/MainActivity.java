package com.example.cocoy.appcompras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cocoy.appcompras.Adapters.RecyclerViewClickListener;
import com.example.cocoy.appcompras.Adapters.RecyclerViewCustomAdapter;
import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerViewClickListener listener;
    private RecyclerViewCustomAdapter adapter;
    ArrayList<Product> products = new ArrayList<>();

    //private Button seeHistory;
    private FloatingActionButton addCarrito;
    //private Button addNewItem;

    ComprasCRUD comprasCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        comprasCRUD = new ComprasCRUD(this);
        products = comprasCRUD.getProducts();
        //addNewItem = (Button) findViewById(R.id.bAddNewItem);

        //seeHistory = (Button) findViewById(R.id.bHistory);
        addCarrito = (FloatingActionButton) findViewById(R.id.fabToCarrito);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //EJEMPLO
        //products.add(new Product("Robot", (float) 1000.0,R.mipmap.diamond));

        adapter = new RecyclerViewCustomAdapter(getApplicationContext(), products, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Elemento " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //intent.putExtra("com.example.cocoy.appcompras.mainactivity.ID", products.get(position).getId());
                intent.putExtra("ID_item",products.get(position).getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        addCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,CarritoActivity.class);

                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mNew_Item) {
            Intent intent = new Intent(MainActivity.this,AddProductActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.mHistory_Purchase) {
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
