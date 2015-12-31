package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class criteres_notation extends Activity {

    private static final int nb_notes = 5;
    private static final int note_min = 0;
    private static final int note_max = 5;

    private EditText acc, sr, pra, cuis, rapport, note;
    private LinearLayout L1;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteres_notation);

        acc = (EditText) findViewById(R.id.acc);
        sr = (EditText) findViewById(R.id.SR);
        pra = (EditText) findViewById(R.id.PA);
        cuis = (EditText) findViewById(R.id.cuis);
        rapport = (EditText) findViewById(R.id.rapport);
        note = (EditText) findViewById(R.id.NF);
        L1 = (LinearLayout) findViewById(R.id.layout1);
        L1.setVisibility(View.INVISIBLE);

    }

    public void calcule(View view) {

        try{

            /*
                On met les valeurs récupérés dans un tableau à donner en paramèttre
                à la fonction inRange()
            */
            int [] tab = new int[]{Integer.parseInt(acc.getText().toString()),
                    Integer.parseInt(sr.getText().toString()),
                    Integer.parseInt(pra.getText().toString()),
                    Integer.parseInt(cuis.getText().toString()),
                    Integer.parseInt(rapport.getText().toString())};

            // On initialise la somme à 0, sinon cela ne fonctionnera pas
            sum = 0;

            // Calcul de la somme
            for(int v : tab){

                sum += v;
            }

            // On peut avoir (sum/5) <= 5 mais des valeurs individuelle > 5
            // D'où l'interêt d'appeler la fonction inRange()
            if (!inRange(tab)) {

                Toast.makeText(this, "Veuiller des valeurs comprises entre " + note_min + " et " + note_max,
                        Toast.LENGTH_SHORT).show();

            } else {

                L1.setVisibility(View.VISIBLE);
                note.setText((String.valueOf(sum/nb_notes)) + " /5");
            }

        }catch(NumberFormatException ne){

            Toast.makeText(this, "Veuiller remplir toutes les cases",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *   Verifie si chacune des nombre saisies pour calculer la moyenne est bien
    *   entre la note minimale et la note maximale
    *
    *   Pour note_in = 0 et note_max = 5
    *
    *   inRange() repond TRUE si on a, par exemple : {1,5,2,3,4}
    *   Mais il repond FALSE si on a {1,6,2,3,4} car 6 > 5
    *
    * */
    private boolean inRange(int [] tab){

        for(int v : tab) {

            if (v < note_min || v >note_max)
                return false;
        }
        return true;
    }

    public void ok(View view) {

        String txt = note.getText().toString();
        Intent ii = new Intent();

        ii.putExtra("resultat", txt);
        setResult(RESULT_OK, ii);
        finish();
    }
}
