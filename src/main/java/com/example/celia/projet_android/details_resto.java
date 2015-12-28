package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
    ArrayList <String> tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);
        Intent ii=getIntent();
       String aa= ii.getStringExtra("la phrase");
        AccesBase base = new AccesBase(getContentResolver());
         tab=base.selectDetailResto(aa);

       // int i=0;
        //while (i<tab.size()){

       // Toast.makeText(this,tab.get(i),Toast.LENGTH_SHORT).show();
       /// i++;}

        nom=(TextView)findViewById(R.id.nom);
        nom.setText(tab.get(0));

        adresse=(TextView)findViewById(R.id.adresse);
        adresse.setText(tab.get(1));

        numtel=(TextView)findViewById(R.id.numtel);
        numtel.setText(tab.get(2));

        stw=(TextView)findViewById(R.id.Sitew);
        stw.setText(tab.get(3));

        note=(TextView)findViewById(R.id.note);
        note.setText(tab.get(4)+ " /5");


        cout=(TextView)findViewById(R.id.cout);
        cout.setText(tab.get(5)+" Euro");


        tc=(TextView)findViewById(R.id.TC);
        tc.setText(tab.get(7));


        //recupérer l'extras (numéro de ligne et affiché les donnée a la place)
        // teste pour la photos ac webview
        monWeb = (WebView) findViewById(R.id.webView);
        monWeb.setWebViewClient(new WebViewClient());

        monWeb.loadUrl(tab.get(6));
        Toast.makeText(this,tab.get(6),Toast.LENGTH_SHORT).show();
        //ajout cout

    }


    public void Modifier(View view) {

        // rendre le button et le editext clicable et editabe et renvoyé un eventuel toast pour indiqué sa

        Intent ii = new Intent(this, Modifier.class);
        startActivity(ii);


    }


    public void Supprimer(View view) {

        // faire un delete sur le content provider  et renvoyé un eventuel toast pour indiqué sa

        AccesBase base = new AccesBase(getContentResolver());
        boolean bool= base.suppression_resto(tab.get(0));

        if(bool==true){
            Toast.makeText(this,"bien supp",Toast.LENGTH_SHORT).show();

            Intent ii= new Intent(this,MainActivity.class);
            startActivity(ii);
        }
        else
            Toast.makeText(this,"erreur",Toast.LENGTH_SHORT).show();


    }



    public void Localisation(View view){

        //recuperation des coordonnée spherique et de l'adresse de la bdd

        Uri uri=  Uri.parse("geo:"+tab.get(9)+" ,"+tab.get(10)+"?q=" + Uri.encode(tab.get(2)));
        Intent ii= new Intent(Intent.ACTION_VIEW,uri);
        ii.setPackage("com.google.android.apps.maps");

        if(ii.resolveActivity(getPackageManager())!=null){

            startActivity(ii);
        }
        else {
            Toast.makeText(this,"erreur",Toast.LENGTH_SHORT).show();
        }
    }


    public void acceder(View view){

        Intent iii = new Intent(Intent.ACTION_VIEW);

        iii.setData(Uri.parse("http://"+ stw.getText().toString()));
        startActivity(iii);
    }

}


