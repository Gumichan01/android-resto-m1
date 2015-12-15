package com.example.celia.projet_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class criteres_notation extends AppCompatActivity {

    private EditText acc,sr,pra,cuis,rapport,note;
    private LinearLayout L1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criteres_notation);


        acc=(EditText)findViewById(R.id.acc);
        sr=(EditText)findViewById(R.id.SR);
        pra=(EditText)findViewById(R.id.PA);
        cuis=(EditText)findViewById(R.id.cuis);
        rapport=(EditText)findViewById(R.id.rapport);
        note= (EditText)findViewById(R.id.NF);
        L1=(LinearLayout)findViewById(R.id.layout1);

    }

    public void calcule(View view){

        int a = (Integer.parseInt(acc.getText().toString())+ Integer.parseInt(sr.getText().toString())+ Integer.parseInt(pra.getText().toString())+
                Integer.parseInt(cuis.getText().toString()) +Integer.parseInt(rapport.getText().toString())) / 5;

        String b= String.valueOf(a);
        L1.setVisibility(View.VISIBLE);
        note.setText(b+" /20");
    }

    public void ok (View view){

        Intent ii= new Intent();
        ii.putExtra("resultat",note.getText().toString());
        setResult(RESULT_OK,ii);
        finish();


    }




}
