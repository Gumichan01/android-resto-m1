package com.example.celia.projet_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Recherche extends Activity implements AdapterView.OnItemSelectedListener {
    private TextView nomr, note, nom_et_ad, inf, sup, egale,cuisine;
    private LinearLayout l2, l3, l4,l5;
    private Button b2, b1;
    private Spinner sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        nomr = (TextView) findViewById(R.id.Rnom);

        l2 = (LinearLayout) findViewById(R.id.layout2);
        l3 = (LinearLayout) findViewById(R.id.layout3);
        l4 = (LinearLayout) findViewById(R.id.layout4);
        l5 = (LinearLayout) findViewById(R.id.layout5);
        sp=(Spinner)findViewById(R.id.sp);
        sp.setOnItemSelectedListener(this);

        note = (TextView) findViewById(R.id.note);
        nom_et_ad = (TextView) findViewById(R.id.neta);
        cuisine=(TextView)findViewById(R.id.TC);

        l3.setVisibility(View.INVISIBLE);
        l4.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.INVISIBLE);
        l5.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String tmp = parent.getItemAtPosition(position).toString();
        Cursor c=null;
        try {
            AccesBase base= new AccesBase(getContentResolver());
            c=base.recherche_par_Tc(tmp);
          if(  c==null){

              Dialog box= new Dialog(this);
              box.setTitle("Aucun restaurant trouvé");
              box.show();
          }
            else{

              Intent ii = new Intent(this, Resultats.class);
            ArrayList<String> result = new ArrayList<>();

            while (c.moveToNext()) {
                result.add(c.getString(0));
            }

            ii.putExtra("cursor", result);
            startActivity(ii);}
        }
        catch (NullPointerException e) {

            Toast.makeText(this, "Echec", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void chercher(View view) {

        if (view == nomr) {
            l3.setVisibility(View.INVISIBLE);
            l4.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.VISIBLE);


        } else if (view == note) {
            l2.setVisibility(View.INVISIBLE);
            l4.setVisibility(View.INVISIBLE);
            l3.setVisibility(View.VISIBLE);
        } else {
            if(view==nom_et_ad){
            l2.setVisibility(View.INVISIBLE);

            l3.setVisibility(View.INVISIBLE);
            l4.setVisibility(View.VISIBLE);}
            else
            {
                l2.setVisibility(View.INVISIBLE);

                l3.setVisibility(View.INVISIBLE);
                l4.setVisibility(View.INVISIBLE);
                l5.setVisibility(View.VISIBLE);
            }

        }


    }

    public void intervallenote(View view) {
        String note_egale="egale";
        String note_sup="sup";
        String note_inf="inf";


        //selon l'intevalle recherche on appel le content provider et le resulat sera afficher ds une fenetre resultat recherche puis dans detail resto

        inf = (TextView) findViewById(R.id.INF);
        sup = (TextView) findViewById(R.id.SUP);

        egale = (TextView) findViewById(R.id.EG);


        Cursor c = null;
        if (view == inf) {
            //recherche de tous les resto ayant une note inf a 3

            try {
                AccesBase base= new AccesBase(getContentResolver());

                c= base.noteegale(note_inf);

            } catch (NullPointerException e) {

                Toast.makeText(this, "Echec reup note <3", Toast.LENGTH_SHORT).show();

            }

        } else if (view == sup) {

            //recherche de tous les resto ayant une note sup a 3
            try {
                AccesBase base= new AccesBase(getContentResolver());

                c= base.noteegale(note_sup);

            } catch (NullPointerException e) {

                Toast.makeText(this, "Echec reup note = 5", Toast.LENGTH_SHORT).show();

            }

        } else {
            //redcherche de ts les resto ayant une note egale a 5

            try {
                AccesBase base= new AccesBase(getContentResolver());

                 c= base.noteegale(note_egale);

            } catch (NullPointerException e) {

                Toast.makeText(this, "Echec reup note = 5", Toast.LENGTH_SHORT).show();

            }

        }
        if( c.getCount()==0){
            Dialog box= new Dialog(this);
            box.setTitle("Aucun restaurant trouvé");
            box.show();
        }
        else{

            Intent ii = new Intent(this, Resultats.class);
            ArrayList<String>result=new ArrayList<>();

            while (c.moveToNext()){
                result.add(c.getString(0));
            }

            ii.putExtra("cursor",result);


            startActivity(ii);
        }

    }


    public void ok(View view) {
        EditText nom;
        Cursor c=null;

        // selon le view chercher le/ les resto qui conviennent et les afficher ds une fenetre resultat
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        nom=(EditText)findViewById(R.id.nomresto);

        if (view == b1) {
            //recherche par nom
            try {


                AccesBase base = new AccesBase(getContentResolver());
                if( base.selectDetailResto( nom.getText().toString())==null){
                    Dialog box= new Dialog(this);
                    box.setTitle("Restaurant non trouvé");
                    box.show();
                   }
                else{

                Intent ii = new Intent(this, details_resto.class);
                ii.putExtra("la phrase", nom.getText().toString());
                startActivity(ii);}
            }
            catch (NullPointerException e) {

                Toast.makeText(this, "Echec", Toast.LENGTH_SHORT).show();

            }


        } else {// nom et adresse//

            if (view == b2) {

                EditText nomR = (EditText) findViewById(R.id.adresse);

                try {
                    AccesBase base = new AccesBase(getContentResolver());

                    c = base.recherche_par_adr(nomR.getText().toString());

                } catch (NullPointerException e) {

                    Toast.makeText(this, "Echec reup restaurant", Toast.LENGTH_SHORT).show();

                }

            }
            if (c.getCount() == 0) {
                Dialog box = new Dialog(this);
                box.setTitle("Aucun restaurant trouvé");
                box.show();
            } else {

                Intent ii = new Intent(this, Resultats.class);
                ArrayList<String> result = new ArrayList<>();

                while (c.moveToNext()) {
                    result.add(c.getString(0));
                }

                ii.putExtra("cursor", result);


                startActivity(ii);
            }

        }



    }


    }


