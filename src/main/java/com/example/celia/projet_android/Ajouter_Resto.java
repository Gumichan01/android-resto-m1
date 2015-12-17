package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Ajouter_Resto extends Activity {
    public static final int MYREQUESTCODE = 50;
    private static final int reqst = 1;
    private TextView HO;
    private EditText note;
    private EditText latitude;
    private EditText longitude;
    private EditText adresse;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveau_restaurant);
        note = (EditText) findViewById(R.id.note);
        //horaire ouverture dans une fenrete tt seul


    }

    public void calculez(View view) {

        Intent ii = new Intent(this, criteres_notation.class);
        startActivityForResult(ii, MYREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == MYREQUESTCODE && resultCode == RESULT_OK) {

            String not = intent.getStringExtra("resultat");
            note.setText(not);

        }
        if (requestCode == reqst && resultCode == RESULT_OK) {

            //recupéré la ligneinséré


        }
    }

    public void ouverture(View view) {

        Intent II = new Intent(this, Horaire_ouverture.class);
        startActivityForResult(II, reqst);
    }


    public void Ajouter(View view) {
        // recuperation de tous les elements les rajouté a la bdd
        //afficher un toast bien ajouter

        //revenir a la fenetre precedente et on mettera a jour la listeview

        EditText view_nom = (EditText) findViewById(R.id.nom);
        EditText view_adr = (EditText) findViewById(R.id.adresse);
        EditText view_tel = (EditText) findViewById(R.id.numtel);
        EditText view_web = (EditText) findViewById(R.id.siteweb);
        EditText view_note = (EditText) findViewById(R.id.note);
        EditText view_cout = (EditText) findViewById(R.id.cout);
        EditText view_photo = (EditText) findViewById(R.id.photos);
        //// TODO Recevoir les horaires
        CheckBox [] view_cuisine = new CheckBox[4];
        view_cuisine[1] = (CheckBox) findViewById(R.id.ita);
        view_cuisine[2] = (CheckBox) findViewById(R.id.veg);
        view_cuisine[3] = (CheckBox) findViewById(R.id.chin);
        view_cuisine[4] = (CheckBox) findViewById(R.id.classi);

        String str_cuis;
        String str_nom = view_nom.getText().toString();
        String str_adr = view_adr.getText().toString();
        String str_tel = view_tel.getText().toString();
        String str_web = view_web.getText().toString();
        String str_note = view_note.getText().toString();
        String str_cout = view_cout.getText().toString();
        String str_photo = view_photo.getText().toString();

        for(int i = 0; i < 4; i++)
        {
            if(view_cuisine[i].isChecked()){

                str_cuis = view_cuisine[i].getText().toString();
                break;
            }
        }

        localiser();                                        // Récupérer la latitude et la longitude
        String str_lat = latitude.getText().toString();
        String str_long = longitude.getText().toString();



    }


    public void localiser() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(this, "geocoder absent", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            finish();
        } else
            Toast.makeText(this, "geocoder here", Toast.LENGTH_LONG).show();


        geocoder = new Geocoder(getApplicationContext(),Locale.FRENCH);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        adresse = (EditText) findViewById(R.id.adresse);

        String adr = adresse.getText().toString();
        Log.d("getAdresse", "Adresse "+adr);
        List<Address> ad;

        try {
            ad = geocoder.getFromLocationName(adr, 1);
        } catch (IOException e) {
            Log.d("getAdresse", "IOException");
            return;
        }
        if (ad == null || ad.size() <= 0) {
            Log.d("getAdresse", "pas d\'adresses");
            return;
        }
        Address a = ad.get(0);
        Log.d("getAdresse", "OK pour "+Double.toString(a.getLatitude())+Double.toString(a.getLongitude()));
        longitude.setText(Double.toString(a.getLongitude()));
        latitude.setText(Double.toString(a.getLatitude()));
    }
}
