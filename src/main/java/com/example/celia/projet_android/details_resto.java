package com.example.celia.projet_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class details_resto extends AppCompatActivity {

    private WebView monWeb;
    EditText nom,adresse,numtel,stw,HO,tc,note,ph,lon,lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);


        //recupérer l'extras (numéro de ligne et affiché les donnée a la place)
       // teste pour la photos ac webview
        monWeb = (WebView) findViewById(R.id.webView);
        monWeb.setWebViewClient(new WebViewClient());

        monWeb.loadUrl("http://www.opera-restaurant.fr/fr/ph");
        //ajout cout

    }



    public void Modifier(View view){

        // rendre le button et le editext clicable et editabe et renvoyé un eventuel toast pour indiqué sa

        Intent ii = new Intent(this,Modifier.class);
        startActivity(ii);


    }


    public void Supprimer(View view){

        // faire un delete sur le content provider  et renvoyé un eventuel toast pour indiqué sa

    }


}


