package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class Ajouter_Resto extends AppCompatActivity {
    public static final int MYREQUESTCODE = 50;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveau_restaurant);
        note=(EditText)findViewById(R.id.note);


    }

    public void calculez(View view) {

        Intent ii = new Intent(this, criteres_notation.class);
        startActivityForResult(ii, MYREQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){

    if(requestCode==MYREQUESTCODE&&resultCode==RESULT_OK){

        String not= intent.getStringExtra("resultat");
        note.setText(not);





    }

}






    public void Ajouter(){
       // recuperation de tous les elements les rajouté a la bdd
        //afficher un toast bien ajouter

        //revenir a la fenetre precedente et on mettera a jour la listeview

        Intent ii=new Intent(this, MainActivity.class);
        startActivity(ii);



    }

}
