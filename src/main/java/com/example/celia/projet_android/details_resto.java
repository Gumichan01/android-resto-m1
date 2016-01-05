package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class details_resto extends Activity {

    TextView nom, adresse, numtel, stw, HO, tc, note, ph, lon, lat,cout;
    private WebView monWeb;
    ArrayList <String> ligne_resto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);
        Intent ii=getIntent();
       String aa= ii.getStringExtra("la phrase");
        AccesBase base = new AccesBase(getContentResolver());
         ligne_resto=base.selectDetailResto(aa);

        if(ligne_resto==null){
            Toast.makeText(this,"erreur",Toast.LENGTH_SHORT).show();

        } else {

       // int i=0;
        //while (i<ligne_resto.size()){

       // Toast.makeText(this,ligne_resto.get(i),Toast.LENGTH_SHORT).show();
       /// i++;}

        nom=(TextView)findViewById(R.id.nom);
        nom.setText(ligne_resto.get(0));

        adresse=(TextView)findViewById(R.id.adresse);
        adresse.setText(ligne_resto.get(1));

        numtel=(TextView)findViewById(R.id.numtel);
        numtel.setText(ligne_resto.get(2));

        stw=(TextView)findViewById(R.id.Sitew);
        stw.setText(ligne_resto.get(3));

        note=(TextView)findViewById(R.id.note);
        note.setText(ligne_resto.get(4)+ " /5");


        cout=(TextView)findViewById(R.id.cout);
        cout.setText(ligne_resto.get(5)+" Euro");


        tc=(TextView)findViewById(R.id.TC);
        tc.setText(ligne_resto.get(7));


        //recupérer l'extras (numéro de ligne et affiché les donnée a la place)
        // teste pour la photos ac webview
        monWeb = (WebView) findViewById(R.id.webView);
        monWeb.setWebViewClient(new WebViewClient());

        monWeb.loadUrl(ligne_resto.get(6));
        Toast.makeText(this,ligne_resto.get(6),Toast.LENGTH_SHORT).show();
        //ajout cout

    }}


    public void Modifier(View view) {

        // rendre le button et le editext clicable et editabe et renvoyé un eventuel toast pour indiqué sa

        Intent ii = new Intent(this, Modifier.class);
        startActivity(ii);
    }


    public void Supprimer(View view) {

        // faire un delete sur le content provider  et renvoyé un eventuel toast pour indiqué sa
        AccesBase base = new AccesBase(getContentResolver());
        boolean bool= base.suppression_resto(ligne_resto.get(0));

        if(bool==true){
            Toast.makeText(this,"Suppression effectuée",Toast.LENGTH_SHORT).show();

            Intent ii= new Intent(this,MainActivity.class);
            startActivity(ii);
        }
        else
            Toast.makeText(this,"Erreur Suppression",Toast.LENGTH_SHORT).show();
    }



    public void Localisation(View view){

        //recuperation des coordonnée spherique et de l'adresse de la bdd
        Uri uri = Uri.parse("geo:"+ligne_resto.get(8)+" ,"+ligne_resto.get(9)+"?q=" + Uri.encode(ligne_resto.get(1)));
        Intent ii = new Intent(Intent.ACTION_VIEW,uri);
        ii.setPackage("com.google.android.apps.maps");

        //if(ii.resolveActivity(getPackageManager())!=null){
        if(ii.getPackage() != null){

            Log.d("getDB", "PACK : " + ii.getPackage());
            startActivity(ii);
        }
        else {
            Toast.makeText(this,"Erreur Package",Toast.LENGTH_SHORT).show();
        }
    }


    public void acceder(View view){

        Intent iii = new Intent(Intent.ACTION_VIEW);

        iii.setData(Uri.parse("http://" + stw.getText().toString()));
        startActivity(iii);
    }

    public void horaireAffichage(View view){
        Intent ii= new Intent(this,Horaire_Resto.class);

        Log.d("getDB", "horaireAffichege ");

        if(ligne_resto != null){

            Log.d("getDB", "Ligne restaurant 1 OK ");

            if(ligne_resto.get(0) == null){

                Log.d("getDB", "Ligne restaurant 2 OK ");
                ii.putExtra("nom",ligne_resto.get(0));
                startActivity(ii);
            }else
                Log.e("getDB", "ligne_resto(0) est NULL ");
        }else
            Log.e("getDB", "ligne_resto(0) est NULL ");
    }

}


