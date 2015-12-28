package com.example.celia.projet_android;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Luxon on 17/12/2015.
 */
public class AccesBase {

    private ContentResolver resolver;
    private String nom_key = "nom";
    private String adresse_key = "adresse";
    private String tel_key = "tel";
    private String note_key = "note";

    private String web_key = "web";
    private String photo_key = "photo";
    private String cout_key = "cout";
    private String type_cuisine_key = "type_cuisine";
    private String latitude_key = "latitude";
    private String longitude_key = "longitude";

    public AccesBase(ContentResolver r){

        resolver = r;
    }


    public String ajoutResto(String nom,String adresse,String tel,String web,String note,
                             String cout,String photo,String cuis,String latitude,String longitude){






        ContentValues values_resto = new ContentValues();

        values_resto.put(nom_key,nom);
        values_resto.put(adresse_key,adresse);
        values_resto.put(tel_key,tel);
        values_resto.put(web_key,web);
         values_resto.put(note_key,note);
        values_resto.put(cout_key,cout);
        values_resto.put(photo_key,photo);
        values_resto.put(type_cuisine_key,cuis);
        values_resto.put(latitude_key,latitude);
        values_resto.put(longitude_key,longitude);

        Uri uri = resolver.insert(Uri.parse("content://com.example.celia.projet_provider/Restaurant"),values_resto);

        if(uri != null){

            Log.d("getDB", "OK Uri insert ");
            return uri.toString();
        }

        return null;
    }

    public Cursor selectTousResto(){

        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider"), null,
                null, null, null);


        return cursor;
    }





    public ArrayList<String >selectDetailResto( String nom){
//int x= Integer.parseInt(id);
       ArrayList <String> s = new ArrayList<>();
        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"),null,"nom = ?",new String[]{nom},null);


        if(cursor.getCount() > 0){
            if(cursor.moveToNext()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    s.add(cursor.getString(i));
                }
            }
        }
        return s;
    }


    public boolean suppression_resto(String id){


        int i = resolver.delete(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), "nom = ?", new String[]{id});

if(i>=1)
        return true;
        else
    return false;
    }

}
