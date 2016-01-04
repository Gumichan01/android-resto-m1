package com.example.celia.projet_android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Resultats extends Activity {
    private ListView lv;
    private ArrayAdapter lesresto;
    private int code = 1;
    private ArrayList<String> r=new ArrayList<>();
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        // Afficher les restaurants s'ils existent
        recup();
    }


    public void recup() {

        lv = (ListView) findViewById(R.id.Resto);
        lesresto = new ArrayAdapter<String>(this, R.layout.liste_resto);

        try {
             Intent ii= getIntent();
         r=ii.getStringArrayListExtra("cursor");

                while (i<r.size()) {

                    lesresto.add(r.get(i));
                    lv.setAdapter(lesresto);
                    i++;
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        show(lesresto.getItem(position).toString());
                    }
                });


        } catch (NullPointerException e) {

            Log.e("getDB", "recup a échoué -> " + e.toString());
        }
    }


    public void show(String a) {
        //recupérer le numéro de ligne souhaité et le passé ds extras
        Intent ii = new Intent(this, details_resto.class);
        ii.putExtra("la phrase", a);
        startActivity(ii);
    }

}
