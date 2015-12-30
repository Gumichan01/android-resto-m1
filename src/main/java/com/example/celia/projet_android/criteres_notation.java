package com.example.celia.projet_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class criteres_notation extends Activity {

    private EditText acc, sr, pra, cuis, rapport, note;
    private LinearLayout L1;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteres_notation);


        acc = (EditText) findViewById(R.id.acc);
        sr = (EditText) findViewById(R.id.SR);
        pra = (EditText) findViewById(R.id.PA);
        cuis = (EditText) findViewById(R.id.cuis);
        rapport = (EditText) findViewById(R.id.rapport);
        note = (EditText) findViewById(R.id.NF);
        L1 = (LinearLayout) findViewById(R.id.layout1);
        L1.setVisibility(View.INVISIBLE);

    }

    public void calcule(View view) {

        try{

            sum = Integer.parseInt(acc.getText().toString()) +
                    Integer.parseInt(sr.getText().toString()) +
                    Integer.parseInt(pra.getText().toString()) +
                    Integer.parseInt(cuis.getText().toString()) +
                    Integer.parseInt(rapport.getText().toString());

            L1.setVisibility(View.VISIBLE);
            note.setText((String.valueOf(sum/5)) + " /5");

        }catch(NumberFormatException ne){

            Toast.makeText(this, "Veuiller remplir toutes les cases",Toast.LENGTH_SHORT).show();
        }
    }

    public void ok(View view) {

        String txt = note.getText().toString();

        if (txt != null) {

            try {

                // Lance une exception si cela echoue
                if ((sum/5)> 5) {
                    Toast.makeText(this, "Noter sur 5", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent ii = new Intent();
                    ii.putExtra("resultat", txt);
                    setResult(RESULT_OK, ii);
                    finish();}

                }catch(NumberFormatException ne){

                    Toast.makeText(this, "Faites d'abord le calcul", Toast.LENGTH_SHORT).show();
                }
            }
            else
            Toast.makeText(this, "Faites d'abord le calcul", Toast.LENGTH_SHORT).show();

    }
}
