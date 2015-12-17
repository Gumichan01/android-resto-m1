package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


    public void Ajouter() {
        // recuperation de tous les elements les rajouté a la bdd
        //afficher un toast bien ajouter

        //revenir a la fenetre precedente et on mettera a jour la listeview

        Intent ii = new Intent(this, MainActivity.class);
        startActivity(ii);
    }


    public void Localiser(View view) {

        if (!Geocoder.isPresent()) {
            Toast.makeText(this, "geocoder absent", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            finish();
        } else
            Toast.makeText(this, "geocoder here", Toast.LENGTH_LONG).show();
        geocoder = new Geocoder(getApplicationContext(),
                Locale.FRENCH);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        adresse = (EditText) findViewById(R.id.adresse);

        String adr = adresse.getText().toString();
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
        longitude.setText(Double.toString(a.getLongitude()));
        latitude.setText(Double.toString(a.getLatitude()));
    }
}
