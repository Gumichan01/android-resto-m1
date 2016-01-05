package com.example.celia.projet_android;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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

    TextView nom, adresse, numtel, stw, HO, tc, note, ph, lon, lat, cout;
    private WebView monWeb;
    ArrayList<String> tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);
        Intent ii = getIntent();
        String aa = ii.getStringExtra("la phrase");
        AccesBase base = new AccesBase(getContentResolver());
        tab = base.selectDetailResto(aa);

        if (tab == null) {
            Toast.makeText(this, "erreur", Toast.LENGTH_SHORT).show();

        } else {

            // int i=0;
            //while (i<tab.size()){

            // Toast.makeText(this,tab.get(i),Toast.LENGTH_SHORT).show();
            /// i++;}

            nom = (TextView) findViewById(R.id.nom);
            nom.setText(tab.get(0));

            adresse = (TextView) findViewById(R.id.adresse);
            adresse.setText(tab.get(1));

            numtel = (TextView) findViewById(R.id.numtel);
            numtel.setText(tab.get(2));

            stw = (TextView) findViewById(R.id.Sitew);
            stw.setText(tab.get(3));

            note = (TextView) findViewById(R.id.note);
            note.setText(tab.get(4) + " /5");


            cout = (TextView) findViewById(R.id.cout);
            cout.setText(tab.get(5) + " Euro");


            tc = (TextView) findViewById(R.id.TC);
            tc.setText(tab.get(7));


            //recupérer l'extras (numéro de ligne et affiché les donnée a la place)
            // teste pour la photos ac webview
            monWeb = (WebView) findViewById(R.id.webView);
            monWeb.setWebViewClient(new WebViewClient());

            monWeb.loadUrl(tab.get(6));
            Toast.makeText(this, tab.get(6), Toast.LENGTH_SHORT).show();
            //ajout cout

        }
    }


    public void Modifier(View view) {
        Intent ii = new Intent(this, Modifier.class);

        ii.putExtra("nom", tab.get(0));
        ii.putExtra("adresse", tab.get(1));
        ii.putExtra("numtel", tab.get(2));
        ii.putExtra("stw", tab.get(3));
        ii.putExtra("note", tab.get(4));
        ii.putExtra("cout", tab.get(5));
        ii.putExtra("photo", tab.get(6));
        ii.putExtra("type_cuis", tab.get(7));

        //les horaires manque

        startActivity(ii);
    }


    public void Supprimer(View view) {

        // faire un delete sur le content provider  et renvoyé un eventuel toast pour indiqué sa

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Confirmer la suppression");

        // set dialog message
        AlertDialog.Builder builder = alertDialogBuilder;
        builder.setMessage("Cliquer oui pour confirmer");
        builder.setCancelable(false);
        AlertDialog.Builder oui = builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AccesBase base = new AccesBase(getContentResolver());
                boolean bool = base.suppression_resto(tab.get(0));
                if (bool == true) {
                    Dialog box = new Dialog(alertDialogBuilder.getContext());
                    box.setTitle("Suppression réussie");
                    box.show();
                    Intent ii= new Intent(alertDialogBuilder.getContext(),MainActivity.class);
                    startActivity(ii);
                }
                else {
                    Dialog box = new Dialog(alertDialogBuilder.getContext());
                    box.setTitle("Echec de la suppression");
                    box.show();
                }

            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void appel(View view) {

        String tel = numtel.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tel));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        }
        else
            Toast.makeText(this,"votre appareil ne peut pas passer d'appel",Toast.LENGTH_SHORT).show();

    }


    public void Localisation(View view){

        //recuperation des coordonnée spherique et de l'adresse de la bdd
        Uri uri = Uri.parse("geo:"+tab.get(8)+" ,"+tab.get(9)+"?q=" + Uri.encode(tab.get(1)));
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

    public void horaire(View view){
        Intent ii= new Intent(this,Horaire_Resto.class);
        ii.putExtra("nom",tab.get(0));
        startActivity(ii);


    }

}


