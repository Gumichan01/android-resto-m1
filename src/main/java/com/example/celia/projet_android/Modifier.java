package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Modifier extends Activity {
    TextView nom, adresse, numtel, stw, tc, note, ph,cout;

    private Geocoder geocoder;
    private EditText latitude;
    private EditText longitude;
    private String liste_modif[]=new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
        //recupéré les donné de la bdd


        Intent ii=getIntent();

        nom = (TextView) findViewById(R.id.nomR);
        nom.setText("("+ii.getStringExtra("nom")+")");


        adresse = (TextView) findViewById(R.id.adresseR);
        adresse.setText("("+ii.getStringExtra("adresse")+")");

        numtel = (TextView) findViewById(R.id.numtelR);
        numtel.setText("("+ii.getStringExtra("numtel")+")");

        stw = (TextView) findViewById(R.id.stR);
        stw.setText("("+ii.getStringExtra("stw")+")");

        cout = (TextView) findViewById(R.id.coutR);
        cout.setText("("+ii.getStringExtra("cout")+" Euro)");


        tc = (TextView) findViewById(R.id.tcR);
        tc.setText("("+ii.getStringExtra("type_cuis")+")");

        note = (TextView) findViewById(R.id.noteR);
        note.setText("("+ii.getStringExtra("note")+")");

        ph = (TextView) findViewById(R.id.photosR);
        ph.setText("("+ii.getStringExtra("photo")+")");


        // recupérer les donnée de la bdd et les mettre a leur place
    }


    public void modifier(View view) {

        // TODO Modifier la table selon les données saisies
        String nom_str, adresse_str, numtel_str, siteweb_str, typecuisine_str = null, note_str,cout_str, photos_str, longitude_str, latitude_str;
        EditText nom,adresse,numtel,stw,note,cout,ph;

        RadioButton [] view_cuisine = new RadioButton[4];
        view_cuisine[0] = (RadioButton) findViewById(R.id.ita);
        view_cuisine[1] = (RadioButton) findViewById(R.id.veg);
        view_cuisine[2] = (RadioButton) findViewById(R.id.jap);
        view_cuisine[3] = (RadioButton) findViewById(R.id.classi);

        nom=(EditText)findViewById(R.id.nom);
        nom_str = nom.getText().toString();

        if(!nom_str.isEmpty())
            liste_modif[0]=nom_str;
        else
        liste_modif[0]=null;

        adresse=(EditText)findViewById(R.id.adresse);
        adresse_str = adresse.getText().toString();

        if(!adresse_str.isEmpty()){

            liste_modif[1]=adresse_str;
            localiser();
            latitude = (EditText) findViewById(R.id.latitude);
            longitude = (EditText) findViewById(R.id.longitude);
            latitude_str = latitude.getText().toString();
            longitude_str = longitude.getText().toString();

            liste_modif[8]=latitude_str;
            liste_modif[9]=longitude_str;

        }else {
            liste_modif[1]=null;
            liste_modif[8]=null;
            liste_modif[9]=null;
        }


        numtel=(EditText)findViewById(R.id.numtel);
        numtel_str = numtel.getText().toString();

        if(!numtel_str.isEmpty())
            liste_modif[2]=numtel_str;
        else
        liste_modif[2]=null;

        stw=(EditText)findViewById(R.id.siteweb);
        siteweb_str = stw.getText().toString();

        if(!siteweb_str.isEmpty())
            liste_modif[3]=siteweb_str;
        else
            liste_modif[3]=null;

        for(int i = 0; i < 4; i++)
        {
            if(view_cuisine[i].isChecked()){

                typecuisine_str = view_cuisine[i].getText().toString();
                break;
            }
        }

        Log.d("CUISINE"," Type cuisine : " + typecuisine_str);

        if(typecuisine_str == null)

            liste_modif[7]=null;

        else
            liste_modif[7]=typecuisine_str;

        cout=(EditText)findViewById(R.id.cout);
        cout_str=cout.getText().toString();

        if(!cout_str.isEmpty())
            liste_modif[5]=cout_str;
        else
            liste_modif[5]=null;

        note=(EditText)findViewById(R.id.note);
        note_str = note.getText().toString();

        if(!note_str.isEmpty())
            liste_modif[4]=note_str;
        else
            liste_modif[4]=null;


        ph=(EditText)findViewById(R.id.photos);
        photos_str=ph.getText().toString();
        if(!photos_str.isEmpty())
            liste_modif[6]=photos_str;
        else
            liste_modif[6]=null;


        if(verifier()){

        AccesBase base=new AccesBase(getContentResolver());

        boolean res = base.mise_ajour_resto(liste_modif);

        if(res)
            Toast.makeText(this,"Modifications enregistrées",Toast.LENGTH_LONG).show();
        else {

            Log.e("getDB", "ECHEC Modifications");
        }
		}
        // TODO
        Intent ii=new Intent(this,MainActivity.class);
        startActivity(ii);}
        else
            Toast.makeText(this,"Aucun champs a modifier",Toast.LENGTH_LONG).show();

    }


public Boolean verifier() {
    Boolean vide = false;

    for (int i = 0; i < liste_modif.length; i++) {
        if (liste_modif[i] != null)
            vide = true;
        }
return vide;

}



    public void localiser() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        finish();

        geocoder = new Geocoder(getApplicationContext(), Locale.FRENCH);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        adresse = (EditText) findViewById(R.id.adresse);

        String adr = adresse.getText().toString();
        Log.d("getAdresse", "Adresse " + adr);
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
