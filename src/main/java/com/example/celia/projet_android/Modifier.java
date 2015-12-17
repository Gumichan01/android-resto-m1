package com.example.celia.projet_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Modifier extends Activity {
    EditText nom,adresse,numtel,stw,HO,tc,note,ph,lon,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
//recupéré les donné de la bdd




        nom= (EditText)findViewById(R.id.nom);
        adresse=(EditText)findViewById(R.id.adresse);
        numtel=(EditText)findViewById(R.id.numtel);
        stw=(EditText)findViewById(R.id.siteweb);
        HO=(EditText)findViewById(R.id.Horaire);
        tc=(EditText)findViewById(R.id.cuis);
        note=(EditText)findViewById(R.id.note);
        ph=(EditText)findViewById(R.id.photos);
        lon=(EditText)findViewById(R.id.lon);
        lat=(EditText)findViewById(R.id.lat);
        // recupérer les donnée de la bdd et les mettre a leur place



    }


    public void modifier(View view){

        String n,a,nu,s,h,t,no,p,l,la;

        n=nom.getText().toString();
        a=adresse.getText().toString();
        nu=numtel.getText().toString();
        s=stw.getText().toString();
        h=HO.getText().toString();
        t=tc.getText().toString();
        no=note.getText().toString();
        p=ph.getText().toString();
        l=lon.getText().toString();
        la=lat.getText().toString();

        //mettre a jour les donnée a partir du content provider






















    }

}
