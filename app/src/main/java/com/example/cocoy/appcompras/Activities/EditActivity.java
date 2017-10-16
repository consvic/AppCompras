package com.example.cocoy.appcompras.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.R;

public class EditActivity extends AppCompatActivity {

    FloatingActionButton fabEdit;
    Button bDelete;

    String id_selected;

    ComprasCRUD comprasCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edits);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbarE);

        collapsingToolbar.setTitle("App Compras");

        id_selected = getIntent().getStringExtra("ID_item");

        comprasCRUD = new ComprasCRUD(this);
        final boolean isEdit = true;
        bDelete = (Button) findViewById(R.id.bDelete);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabSaveEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this,AddProductActivity.class);
                intent.putExtra("EDIT",isEdit);
                intent.putExtra("ID_item",id_selected);
                startActivity(intent);
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprasCRUD.deleteProduct(id_selected);
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });
    }

}
