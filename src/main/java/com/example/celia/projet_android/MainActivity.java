package com.example.celia.projet_android;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.Array;

public class MainActivity extends Activity {
    private ListView lv;
    private ArrayAdapter lesresto;
    private int code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO : faire en sorte que recup soit appellé par l'utilisateur, pas automatiquement
        //recup();
    }


    public void recup(){


        lv = (ListView) findViewById(R.id.Resto);
        lesresto=new ArrayAdapter<String>(this,R.layout.liste_resto);

        AccesBase base = new AccesBase(getContentResolver());
        Cursor s = base.selectTousResto();

        if(s != null){
            int j = 0;
            Toast.makeText(this, "" + s.getCount(), Toast.LENGTH_SHORT).show();

            while (j <= s.getCount()){
                if(s.moveToNext()){

                    lesresto.add(s.getString(0));
                    lv.setAdapter(lesresto);
                }else{
                    Toast.makeText(this," erreur Adapter",Toast.LENGTH_SHORT);
                }

                j++;
            }

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    show(lesresto.getItem(position).toString());
                }
            });
        }
    }


    public void show(String a) {
        //recpéré le numéro de ligne souhaité et le passé ds extras
        Intent ii = new Intent(this, details_resto.class);
        ii.putExtra("la phrase", a);

        startActivity(ii);
    }



    public void Ajouter(View view) {

        Intent ii = new Intent(this, Ajouter_Resto.class);
        startActivityForResult(ii, code);
    }


    public void Rechercher(View view) {
        // renvoyé vers une activité qui contient les critères de recherches

        Intent ii = new Intent(this, Recherche.class);
        startActivity(ii);
    }
}