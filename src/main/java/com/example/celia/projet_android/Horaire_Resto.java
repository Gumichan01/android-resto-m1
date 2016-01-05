package com.example.celia.projet_android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.celia.projet_android.R;

import java.util.ArrayList;

public class Horaire_Resto extends Activity {

    private int i=0;

   private String s;
    private ArrayList<String> liste_id_periode=new ArrayList();
private ListView lv;
    private ArrayAdapter<String>lesresto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaire__resto);

        recuphoraire();
    }

    public void recuphoraire() {

        lv = (ListView) findViewById(R.id.Resto);
        lesresto = new ArrayAdapter<String>(this, R.layout.liste_resto);
        try {
            Intent ii = getIntent();
            AccesBase base = new AccesBase(getContentResolver());
            s = base.idresto(ii.getStringExtra("nom"));

           //recupération des idperiode
            Cursor id_periode = base.idperiode_resto(s);
            String[] tab_periode = new String[id_periode.getCount()];

            i = 0;
            while (id_periode.moveToNext()) {
                tab_periode[i] = (id_periode.getString(1));
                i++;
            }

           //recupération des horaires et affichage dans une listeview
            ArrayList<Cursor> horaire_jour = base.Horaire_ouv_ferm(tab_periode);
            if (horaire_jour.isEmpty())
                Toast.makeText(this, " vide", Toast.LENGTH_SHORT).show();

            i = 0;
            while (i < horaire_jour.size()) {

                while (horaire_jour.get(i).moveToNext()){

                    //lesresto.add(horaire_jour.get(i).getString(0) + ":   de " + horaire_jour.get(i).getString(1) + " H à " + horaire_jour.get(i).getString(2) + " H     &     de  " + horaire_jour.get(i).getString(3) + "H  à  " + horaire_jour.get(i).getString(4)+" H");
                    Horaire h = new Horaire(horaire_jour.get(i).getString(1),horaire_jour.get(i).getString(2),
                                            horaire_jour.get(i).getString(3),horaire_jour.get(i).getString(4));
                    lesresto.add(horaire_jour.get(i).getString(0) + " : " + h.toString());
                    lv.setAdapter(lesresto);
                }
                i++;
            }

        } catch (NullPointerException e) {

            Log.e("getDB", "echec -> " + e.toString());
        }

    }
}
