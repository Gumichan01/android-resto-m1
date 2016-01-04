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

    /*
        Je le mets à static pour qu'il existe même quand la classe n'est pas instanciée,
        sinon il y aura un problème dans ajoutResto.
        Cela est sans doute dû à un effet de bord indésirable
    */
    static private HashMap<String,Horaire> map_horaires;

    private TextView HO;
    private EditText note;
    private EditText latitude;
    private EditText longitude;
    private EditText adresse;
    private Geocoder geocoder;
    private AccesBase base;
    private HashMap<String,Horaire> tmp_map;


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

        if (requestCode == MYREQUESTCODE && resultCode == RESULT_OK) {

            String note_final = intent.getStringExtra("resultat");


            note.setText(note_final.substring(0,1));

        }
        else if(requestCode == reqst && resultCode == RESULT_OK){

            tmp_map = (HashMap<String,Horaire>) intent.getSerializableExtra("result-map");

            if(tmp_map == null){

                Log.d("HORAIRE","MAP null");
                return;

            }else{
                Log.d("HORAIRE","MAP OK - " + tmp_map.size() + "\n -> " + tmp_map.toString());
                map_horaires = tmp_map;
            }
        }
    }

    public void ouverture(View view) {

        Intent II = new Intent(this, Horaire_ouverture.class);
        startActivityForResult(II, reqst);
    }


    public void Ajouter(View view) {
        // revenir a la fenetre precedente et on mettera a jour la listeview

        // On récupère tout
        EditText view_nom = (EditText) findViewById(R.id.nom);
        EditText view_adr = (EditText) findViewById(R.id.adresse);
        EditText view_tel = (EditText) findViewById(R.id.numtel);
        EditText view_web = (EditText) findViewById(R.id.siteweb);
        EditText view_cout = (EditText) findViewById(R.id.cout);
        EditText view_photo = (EditText) findViewById(R.id.photos);

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

        if (str_nom.isEmpty()) {

            Log.e("Ajouter", "Nom du restaurant non renseigné");
            Toast.makeText(this,"Veuillez renseignez le nom du restaurant",Toast.LENGTH_LONG).show();

        } else if (str_cuis == null) {

            Log.e("Ajouter", "Pas de type de cuisine");
            Toast.makeText(this,"Selectionnez le type de cuisine",Toast.LENGTH_LONG).show();

        } else {

            Log.d("Ajouter", "Données valides, Insertion...");
            localiser();                                                // Récupérer les coordonées
            String str_lat = latitude.getText().toString();
            String str_long = longitude.getText().toString();

            Toast.makeText(this,"Insertion en cours ...",Toast.LENGTH_LONG).show();

            boolean res = base.ajoutResto(map_horaires, str_nom, str_adr, str_tel, str_web, str_note,
                    str_cout, str_photo, str_cuis, str_lat, str_long);
            if(res)
                Log.d("getDB", "Insertion réussie ");
            else {

                Log.e("getDB", "ECHEC insertion");
                Toast.makeText(this,"Echec de l'insertion; Verifier vos données et vos horaires",Toast.LENGTH_LONG).show();
            }
        }
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
        Log.d("getAdresse", "OK pour " + Double.toString(a.getLatitude()) + " " +
                Double.toString(a.getLongitude()));
        longitude.setText(Double.toString(a.getLongitude()));
        latitude.setText(Double.toString(a.getLatitude()));
    }
}