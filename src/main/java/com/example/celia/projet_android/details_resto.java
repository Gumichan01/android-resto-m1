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
import android.provider.ContactsContract;
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
    ArrayList <String> ligne_resto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);
        Intent ii = getIntent();
        String aa = ii.getStringExtra("la phrase");
        AccesBase base = new AccesBase(getContentResolver());

        ligne_resto=base.selectDetailResto(aa);

        if(ligne_resto==null){
            Toast.makeText(this,"erreur",Toast.LENGTH_SHORT).show();

        } else {

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
        cout.setText(ligne_resto.get(5)+" €");

        tc=(TextView)findViewById(R.id.TC);
        tc.setText(ligne_resto.get(7));

		//recupérer l'extras (numéro de ligne et affiché les donnée a la place)
		// teste pour la photos ac webview
		monWeb = (WebView) findViewById(R.id.webView);
		monWeb.setWebViewClient(new WebViewClient());

        monWeb.loadUrl(ligne_resto.get(6));
        }
    }


    public void Modifier(View view) {
        Intent ii = new Intent(this, Modifier.class);

        ii.putExtra("nom", ligne_resto.get(0));
        ii.putExtra("adresse", ligne_resto.get(1));
        ii.putExtra("numtel", ligne_resto.get(2));
        ii.putExtra("stw", ligne_resto.get(3));
        ii.putExtra("note", ligne_resto.get(4));
        ii.putExtra("cout", ligne_resto.get(5));
        ii.putExtra("photo", ligne_resto.get(6));
        ii.putExtra("type_cuis", ligne_resto.get(7));

        startActivity(ii);
    }


    public void Supprimer(View view) {

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
                boolean bool = base.suppression_resto(ligne_resto.get(0));
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
        Uri uri = Uri.parse("geo:" + ligne_resto.get(8) + " ," + ligne_resto.get(9) + "?q=" + Uri.encode(ligne_resto.get(1)));
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
        ii.putExtra("nom", ligne_resto.get(0));
        startActivity(ii);
    }


    public void appeler(View view){

        TextView v = (TextView) findViewById(R.id.numtel);
        CharSequence cs = v.getText();

        if(cs == null){
            Toast.makeText(getApplicationContext(),"Téléphone non répertoriée", Toast.LENGTH_SHORT);
            return;
        }

        String call ="tel:" + cs.toString().trim();
        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(call));
        startActivity(intent);
    }

    public void contact(View view){

        TextView view_nom = (TextView) findViewById(R.id.nom);
        TextView view_tel = (TextView) findViewById(R.id.numtel);
        CharSequence cs_nom = view_nom.getText();
        CharSequence cs_tel = view_tel.getText();

        if(cs_nom == null){
            Toast.makeText(getApplicationContext(),"Non du contact inconnu", Toast.LENGTH_SHORT);
            return;
        }

        if(cs_tel == null){
            Toast.makeText(getApplicationContext(),"Numéro de téléphone inconnu", Toast.LENGTH_SHORT);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, cs_nom.toString());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, cs_tel.toString());
        startActivity(intent);
    }

}


