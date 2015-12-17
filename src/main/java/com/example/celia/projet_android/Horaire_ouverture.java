package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Horaire_ouverture extends Activity implements AdapterView.OnItemSelectedListener {
LinearLayout lh;
    CheckBox lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche;
    Spinner ouvma,ferma,ouvap,ferap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire_ouverture);
        lh= (LinearLayout)findViewById(R.id.layouthoraire);
        lh.setVisibility(View.INVISIBLE);
       lundi=(CheckBox)findViewById(R.id.lundi);
        mardi=(CheckBox)findViewById(R.id.mardi);
        mercredi=(CheckBox)findViewById(R.id.mercredi);
        jeudi=(CheckBox)findViewById(R.id.jeudi);
        vendredi=(CheckBox)findViewById(R.id.vendredi);
        samedi=(CheckBox)findViewById(R.id.dimanche);
        dimanche=(CheckBox)findViewById(R.id.dimanche);




        ouvma=(Spinner)findViewById(R.id.spmo);
        ferma=(Spinner)findViewById(R.id.spmf);

        ouvap=(Spinner)findViewById(R.id.spamo);
        ferap=(Spinner)findViewById(R.id.spamf);



    }




    public void jours(View view){
String jour=null;
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
            //ouverture et fermeuture matinal:


            ouvma.setOnItemSelectedListener(this);
            ferma.setOnItemSelectedListener(this);


            //ouverture et fermeuture aprem :

            ouvap.setOnItemSelectedListener(this);
            ferap.setOnItemSelectedListener(this);



    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //si on clique sur le spiner :

        //sauvgarde dans la bdd
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
    public void validez(View view) {


        lh.setVisibility(view.INVISIBLE);

    }


    public void retour(View view){

        // renvois de la ligne inserer a l'activité ajouter resto

      /*  Intent ii= new Intent();
        ii.putExtra("resultat",note.getText().toString());
        setResult(RESULT_OK,ii);
        finish();*/







    }
}