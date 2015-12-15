package com.example.celia.projet_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Recherche extends AppCompatActivity {
    TextView nomr,note,nom_et_ad,inf,sup,egale;
    LinearLayout l2,l3,l4;
    private Button b2,b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        nomr=(TextView)findViewById(R.id.Rnom);

        l2=(LinearLayout)findViewById(R.id.layout2);
        l3=(LinearLayout)findViewById(R.id.layout3);
        l4=(LinearLayout)findViewById(R.id.layout4);

        note=(TextView)findViewById(R.id.note);
        nom_et_ad=(TextView)findViewById(R.id.neta);

        l3.setVisibility(View.INVISIBLE);
        l4.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.INVISIBLE);

    }


    public void chercher(View view){

        if(view==nomr){
            l3.setVisibility(View.INVISIBLE);
            l4.setVisibility(View.INVISIBLE);
            l2.setVisibility(View.VISIBLE);




        }
        else
            if (view==note)
            { l2.setVisibility(View.INVISIBLE);
                l4.setVisibility(View.INVISIBLE);
                l3.setVisibility(View.VISIBLE);
            }

            else
            {   l2.setVisibility(View.INVISIBLE);

                l3.setVisibility(View.INVISIBLE);
                 l4.setVisibility(View.VISIBLE);}








    }

    public void intervallenote(View view){

        //selon l'intevalle recherche on appel le content provider et le resulat sera afficher ds une fenetre resultat recherche puis dans detail resto

        inf=(TextView)findViewById(R.id.INF);
        sup=(TextView)findViewById(R.id.SUP);

        egale=(TextView)findViewById(R.id.EG);


        if(view==inf){
            //recherche de tous les resto ayant une note inf a 10
        }
        else
            if(view==sup){

                //recherche de tous les resto ayant une note sup a 10
            }
        else{
                //redcherche de ts les resto ayant une note egale a 10
            }





    }



    public void ok(View view){

       // selon le view chercher le/ les resto qui conviennent et les afficher ds une fenetre resultat
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);

   if(view==b1){
       //recherche par nom
   }
        else
   {//recherche par nom et adresse//
    }





    }



}
