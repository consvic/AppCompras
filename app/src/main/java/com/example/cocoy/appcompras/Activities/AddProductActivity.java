package com.example.cocoy.appcompras.Activities;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cocoy.appcompras.*;
import com.example.cocoy.appcompras.BaseDatos.ComprasCRUD;
import com.example.cocoy.appcompras.BaseDatos.Product;
import com.example.cocoy.appcompras.BaseDatos.ProductContract;
import com.example.cocoy.appcompras.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProducto, etPrecio;
    private Button bTakeP, bGallery;
    private FloatingActionButton fabSave;
    private ImageView ivPhoto;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private final int RESULT_LOAD_IMAGE = 100;

    String mCurrentPhotoPath;
    String product,price;
    float pricef;
    private ComprasCRUD comprasCRUD;
    boolean isEdit;
    String id_toEdit;
    Product productO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.cocoy.appcompras.R.layout.activity_add_product);

        comprasCRUD = new ComprasCRUD(this);
        etProducto = (EditText) findViewById(R.id.etProducto);
        etPrecio = (EditText) findViewById(R.id.etPrecio);
        bTakeP = (Button) findViewById(R.id.bTomarFoto);
        bGallery = (Button) findViewById(R.id.bSelGaleria);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSaveItem);
        ivPhoto = (ImageView) findViewById(R.id.ivMosFoto);

        Intent intent = getIntent();

        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("EDIT")) {
                isEdit = extras.getBoolean("EDIT", false);

                // TODO: Do something with the value of isNew.
            }
        }
        Log.d("Entro",isEdit+"");
        if(isEdit) {

            id_toEdit = getIntent().getStringExtra("ID_item");
            productO = comprasCRUD.selectProduct(id_toEdit);
            etProducto.setText(productO.getName());
            etPrecio.setText(productO.getPrice()+"");
            mCurrentPhotoPath = productO.getPath();
            Picasso.with(this).load(mCurrentPhotoPath).into(ivPhoto);
        }

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                if(!isEdit) {
                    comprasCRUD.newProduct(new Product(ProductContract.Entry.generarIdProducto(), product, pricef, mCurrentPhotoPath));
                } else {
                    productO = new Product(id_toEdit,product,pricef,mCurrentPhotoPath);
                    comprasCRUD.updateProduct(productO);
                }
                startActivity(new Intent(AddProductActivity.this, MainActivity.class));
            }
        });

        bTakeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosCamara();
            }
        });

        bGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarPermisosStorage();
            }
        });

    }

    public void saveData() {
        product = etProducto.getText().toString();
        price = etPrecio.getText().toString();
        pricef = Float.parseFloat(price);
    }

    public void validarPermisosStorage() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //debemos mostrar un mensaje
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //mostramos una explicacind eque no acepto dar permiso para acceder a la libreria

            } else  {
                //pedimos permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_EXTERNAL_STORAGE);

            }
        } else {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    public void validarPermisosCamara() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //debemos mostrar un mensaje
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                //mostramos una explicacind eque no acepto dar permiso para acceder a la libreria

            } else  {
                //pedimos permiso
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);


            }
        } else {
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case REQUEST_IMAGE_CAPTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Prueba","entro");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    dispatchTakePictureIntent();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cocoy.appcompras",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri pat;
        if(requestCode == RESULT_LOAD_IMAGE) {
            pat = data.getData();
            //String path;
            ivPhoto.setImageURI(pat);
            //tvUrl.setText(pat.toString());
            //path=pat.toString();
            mCurrentPhotoPath = pat.toString();
            Log.d("load: ", pat.toString());
            //getExifDate(pat.toString());
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Picasso.with(this).load(mCurrentPhotoPath).into(ivPhoto);

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file://"+image.getAbsolutePath();
        //tvUrl.setText(mCurrentPhotoPath);
        return image;
    }
}
