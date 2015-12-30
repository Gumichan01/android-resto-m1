package com.example.celia.projet_android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.Array;

public class MainActivity extends Activity {
    private ListView lv;
    private ArrayAdapter lesresto;
    private int code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Afficher les restaurants s'ils existent
        recup();
    }


    public void recup() {

        lv = (ListView) findViewById(R.id.Resto);
        lesresto = new ArrayAdapter<String>(this, R.layout.liste_resto);

        try {

            AccesBase base = new AccesBase(getContentResolver());
            Cursor s = base.selectTousResto();

            if (s != null) {

                Log.d("getDB client", "nb restos : " + s.getCount());
                //Toast.makeText(this, "" + s.getCount(), Toast.LENGTH_SHORT).show();

                while (s.moveToNext()) {

                    lesresto.add(s.getString(0));
                    lv.setAdapter(lesresto);
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        show(lesresto.getItem(position).toString());
                    }
                });
            }

        } catch (NullPointerException e) {

            Log.e("getDB", "recup a échoué -> " + e.toString());
            //throw e;
        }
    }


    public void show(String a) {
        //recpéré le numéro de ligne souhaité et le passé ds extras
        Intent ii = new Intent(this, details_resto.class);
        ii.putExtra("la phrase", a);
        startActivity(ii);
    }


    public void Ajouter(View view) {

        Intent ii = new Intent(this, Ajouter_Resto.class);
        startActivityForResult(ii, code);
        recup();
    }


    public void Rechercher(View view) {
        // renvoyé vers une activité qui contient les critères de recherches

        Intent ii = new Intent(this, Recherche.class);
        startActivity(ii);
    }


    public void prendrePhoto(View view) {

        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(photoIntent, code);
        else
            Toast.makeText(this, "erreur", Toast.LENGTH_SHORT);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == code && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras(); //data est l’intent reçu en argument
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView image = (ImageView) findViewById(R.id.vuePhoto);

            image.setImageBitmap(imageBitmap);


        }

    }
}