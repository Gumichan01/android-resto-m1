package com.example.celia.projet_android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView lv;
    private ArrayAdapter<String> lesresto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lesresto = new ArrayAdapter<>(this, R.layout.liste_resto);
        lv = (ListView) findViewById(R.id.Resto);

        //recupérer les donné via le content provider
        AccesBase base = new AccesBase(getContentResolver());

        String s = base.selectTousResto();

        lesresto.add(s);
        lv.setAdapter(lesresto);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show(lesresto.getItem(position));
            }
        });
    }


    public void show(String a) {
        //recpéré le numéro de ligne souhaité et le passé ds extras
        Intent ii = new Intent(this, details_resto.class);
        ii.putExtra("la phrase", a);

        startActivity(ii);


    }


    public void Ajouter(View view) {


        Intent ii = new Intent(this, Ajouter_Resto.class);
        startActivity(ii);


    }


    public void Rechercher(View view) {
        // renvoyé vers une activité qui contient les critères de recherches

        Intent ii = new Intent(this, Recherche.class);


        startActivity(ii);
    }


}
