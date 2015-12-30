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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private AccesBase base;
    private HashMap<String,Horaire> map_horaires;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveau_restaurant);
        note = (EditText) findViewById(R.id.note);
        base = new AccesBase(getContentResolver());
        Log.d("DATABASE_LOG", "Base created ");
    }

    public void calculez(View view) {

        Intent ii = new Intent(this, criteres_notation.class);
        startActivityForResult(ii, MYREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if(requestCode == reqst && resultCode == RESULT_OK){

            map_horaires = (HashMap<String,Horaire>) intent.getSerializableExtra("result-map");

            if(map_horaires == null){

                Log.d("HORAIRE","MAP null");
                return;

            }else{
                Log.d("HORAIRE","MAP OK - "+map_horaires.size()+"\n -> "+map_horaires.toString());
            }
        }
    }

    public void ouverture(View view) {

        Intent II = new Intent(this, Horaire_ouverture.class);
        startActivityForResult(II, reqst);
    }


    public void Ajouter(View view) {
        // recuperation de tous les elements les rajouté a la bdd
        // afficher un toast bien ajouter

        // revenir a la fenetre precedente et on mettera a jour la listeview

        EditText view_nom = (EditText) findViewById(R.id.nom);
        EditText view_adr = (EditText) findViewById(R.id.adresse);
        EditText view_tel = (EditText) findViewById(R.id.numtel);
        EditText view_web = (EditText) findViewById(R.id.siteweb);
        EditText view_cout = (EditText) findViewById(R.id.cout);
        EditText view_photo = (EditText) findViewById(R.id.photos);
        //// TODO Recevoir les horaires



        RadioButton [] view_cuisine = new RadioButton[4];
        view_cuisine[0] = (RadioButton) findViewById(R.id.ita);
        view_cuisine[1] = (RadioButton) findViewById(R.id.veg);
        view_cuisine[2] = (RadioButton) findViewById(R.id.jap);
        view_cuisine[3] = (RadioButton) findViewById(R.id.classi);

        String str_cuis = null;
        String str_nom = view_nom.getText().toString();
        String str_adr = view_adr.getText().toString();
        String str_tel = view_tel.getText().toString();
        String str_web = view_web.getText().toString();
        String str_note = note.getText().toString();
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

        String res = base.ajoutResto(map_horaires,str_nom,str_adr,str_tel,str_web,str_note,
                                        str_cout,str_photo,str_cuis,str_lat,str_long);

        if(res != null)
            Log.d("getDB", "Resultat : " + res);
        else
            Log.e("getDB", "ECHEC de l'ajout ");

    }


    public void localiser() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        finish();

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
        Log.d("getAdresse", "OK pour " + Double.toString(a.getLatitude()) + Double.toString(a.getLongitude()));
        longitude.setText(Double.toString(a.getLongitude()));
        latitude.setText(Double.toString(a.getLatitude()));
    }
}