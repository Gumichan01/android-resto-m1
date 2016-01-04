package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

public class Horaire_ouverture extends Activity implements AdapterView.OnItemSelectedListener {
    LinearLayout lh;
    RadioButton lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
    Spinner ouvma, ferma, ouvap, ferap;

    String jour;
    Horaire horaire;
    HashMap<String,Horaire> map_horaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire_ouverture);

        // Informations sur le jour et l'horaire selectionnés
        jour = null;
        horaire = new Horaire();
        map_horaire = new HashMap<>();

        lh = (LinearLayout) findViewById(R.id.layouthoraire);
        lh.setVisibility(View.INVISIBLE);

        lundi = (RadioButton) findViewById(R.id.lundi);
        mardi = (RadioButton) findViewById(R.id.mardi);
        mercredi = (RadioButton) findViewById(R.id.mercredi);
        jeudi = (RadioButton) findViewById(R.id.jeudi);
        vendredi = (RadioButton) findViewById(R.id.vendredi);
        samedi = (RadioButton) findViewById(R.id.samedi);
        dimanche = (RadioButton) findViewById(R.id.dimanche);

        ouvma = (Spinner) findViewById(R.id.spmo);
        ferma = (Spinner) findViewById(R.id.spmf);
        ouvap = (Spinner) findViewById(R.id.spamo);
        ferap = (Spinner) findViewById(R.id.spamf);
    }


    public void jours(View view) {

        lh.setVisibility(View.VISIBLE);

        //sauvgarde dans la bdd  du jour
        if (view.equals(lundi)) {
            jour = lundi.getText().toString();
        } else if (view.equals(mardi)) {
            jour = mardi.getText().toString();
        } else if (view.equals(mercredi)) {
            jour = mercredi.getText().toString();
        } else if (view.equals(jeudi)) {
            jour = jeudi.getText().toString();
        } else if (view.equals(vendredi)) {
            jour = vendredi.getText().toString();
        } else if (view.equals(samedi)) {
            jour = samedi.getText().toString();
        } else if (view.equals(dimanche)) {
            jour = dimanche.getText().toString();
        }

        //sauvgarde dans la table pour le  lundi
        //ouverture et fermeuture matinal
        ouvma.setOnItemSelectedListener(this);
        ferma.setOnItemSelectedListener(this);

        //ouverture et fermeuture aprem
        ouvap.setOnItemSelectedListener(this);
        ferap.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position) != null)
            Log.d("HORAIRE", "Item not NULL");
        else{
            Log.e("HORAIRE", "Item NULL");
            return;
        }

        String tmp = parent.getItemAtPosition(position).toString();
        String heure = tmp.substring(0,tmp.length()-1);

        // TODO: 29/12/2015 Ajout des horraires dans la map
        if(position > 0) {   // Correspond à "-" dans la selection

            Log.d("HORAIRE", "At position " + position + " : " + parent.getItemAtPosition(position).toString());
            Log.d("HORAIRE", "VIEW " + parent.getId());
            Log.d("HORAIRE", "OUVMA " + ouvma.getId());
            Log.d("HORAIRE", "FERMA " + ferma.getId());
            Log.d("HORAIRE", "OUVAP " + ouvap.getId());
            Log.d("HORAIRE", "FERAP " + ferap.getId());

            // Ajout de l'heure en fonction de la zone saisie
            if (parent.getId() == ouvma.getId()) {
                horaire.setOuvrertureMatin(heure);

            } else if (parent.getId() == ferma.getId()) {
                horaire.setFermetureMatin(heure);

            } else if (parent.getId() == ouvap.getId()) {
                horaire.setOuvrertureAprem(heure);

            } else if (parent.getId() == ferap.getId())
                horaire.setFermetureAprem(heure);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void validerHoraires(View view) {

        lh.setVisibility(view.INVISIBLE);
        ouvma.setSelection(0);
        ferma.setSelection(0);
        ouvap.setSelection(0);
        ferap.setSelection(0);

        if(jour == null)
            Log.e("HORAIRE","Jour null. Impossible de sauvegarder les données");
        else{

            map_horaire.put(jour,horaire);
            Log.d("HORAIRE", "jour OK.");
            jour = null;
            horaire = new Horaire();
        }
    }


    public void retour(View view){

        // renvois de la ligne inserer a l'activité ajouter resto
        Intent ii= new Intent();
        ii.putExtra("result-map", (HashMap<String, Horaire>) map_horaire.clone());

        setResult(RESULT_OK,ii);
        finish();
    }
}