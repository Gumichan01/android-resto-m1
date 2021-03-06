package com.example.celia.projet_android;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Luxon on 17/12/2015.
 */
public class AccesBase {

    private ContentResolver resolver;

    // Periode
    private String jour_key = "jour";
    private String ouvma_key = "heure_ouverture_matinale";
    private String ferma_key = "heure_fermeture_matinale";
    private String ouvap_key = "heure_ouverture_aprem";
    private String ferap_key = "heure_fermeture_aprem";
    private String [] jours = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};

    // Restaurant
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
    private String s;
    private ArrayList <String> id_periode=new ArrayList();


    public AccesBase(ContentResolver r){

        resolver = r;
    }


    public boolean ajoutResto(HashMap<String,Horaire> map_h,String nom,String adresse,String tel,
                              String web,String note,String cout,String photo,String cuis,
                              String latitude,String longitude){

        // On ne fait rien si le restaurant n'a pas d'horaire
        if(map_h == null || map_h.isEmpty()){

            Log.e("HORAIRE","Pas d'horaire, echec de la récupération");
            return false;
        }

        ContentValues values_periode = new ContentValues();
        ContentValues values_resto = new ContentValues();

        // Mettre les periodes
        for (String j : jours)
        {
            if(map_h.containsKey(j)){

                Horaire h = map_h.get(j);
                Log.d("HORAIRE", " horaire récupéré pour le " + j + " : " + h.toString());

                // Mettre les valeurs dans la periode
                values_periode.put(jour_key,j);
                values_periode.put(ouvma_key,h.getOuvertureMatin());
                values_periode.put(ferma_key,h.getFermetureMatin());
                values_periode.put(ouvap_key,h.getOuvertureAprem());
                values_periode.put(ferap_key,h.getFermetureAprem());

                Uri uri_periode = resolver.insert(Uri.parse("content://com.example.celia.projet_provider/Periode"),
                        values_periode);

                if(uri_periode != null)
                    Log.d("getDB", "INSERT Periode");
                else{
                    Log.d("getDB", "FAILED INSERT Periode");
                    return false;
                }
            }
        }

        // Mettre les restaurants
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

        Uri uri_resto = resolver.insert(Uri.parse("content://com.example.celia.projet_provider/Restaurant"),
                values_resto);

        if(uri_resto != null)
            Log.d("getDB", "INSERT Resto");
        else{
            Log.d("getDB", "FAILED INSERT Resto");
            return false;
        }
        return true;
    }

    public boolean mise_ajour_resto(String [] liste_modif ){

        ContentValues values_resto = new ContentValues();

        if(liste_modif[0]!=null){
            values_resto.put(nom_key,liste_modif[0]);
        }

        if(liste_modif[1]!=null){
            values_resto.put(adresse_key,liste_modif[1]);
        }

        if(liste_modif[2]!=null){
            values_resto.put(tel_key,liste_modif[2]);
        }

        if(liste_modif[3]!=null){
            values_resto.put(web_key,liste_modif[3]);
        }

        if(liste_modif[4]!=null){
            values_resto.put(note_key,liste_modif[4]);
        }

        if(liste_modif[5]!=null){
            values_resto.put(cout_key,liste_modif[5]);
        }

        if(liste_modif[6]!=null){
            values_resto.put(photo_key,liste_modif[6]);
        }

        if(liste_modif[7]!=null){
            values_resto.put(type_cuisine_key,liste_modif[7]);
        }

        if(liste_modif[8]!=null){
            values_resto.put(latitude_key,liste_modif[8]);
        }

        if(liste_modif[9]!=null){
            values_resto.put(longitude_key,liste_modif[9]);
        }

        int i= resolver.update(Uri.parse("content://com.example.celia.projet_provider/Restaurant"),values_resto,null,null);


        if(i>=1)
            return true;
        else
            return false;





    }

    public Cursor selectTousResto(){

        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null,
                null, null, null);

        return cursor;
    }



    public ArrayList<String >selectDetailResto(String nom){
//int x= Integer.parseInt(id);
        ArrayList <String> s = new ArrayList<>();
        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, "nom = ?", new String[]{nom}, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToNext()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    s.add(cursor.getString(i));
                }
            }
        }
        else return null;
        return s;
    }


    public boolean suppression_resto(String id) {
        String str_id_resto = null;
        int a = 0, j = 0, k = 0;
        String idperiode;

        Cursor c = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), new String[]{"ROWID"}, "nom = ?", new String[]{id}, null);


        if (c.moveToNext())
            str_id_resto = c.getString(0);

        Cursor c2 = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Ouvrir"), new String[]{"idperiode"}, "idresto = ?", new String[]{str_id_resto}, null);


        while (c2.moveToNext()) {
            idperiode = c2.getString(0);
            a = resolver.delete(Uri.parse("content://com.example.celia.projet_provider/Periode"), "ROWID = ?", new String[]{idperiode});


        }

        j = resolver.delete(Uri.parse("content://com.example.celia.projet_provider/Ouvrir"), "idresto = ?", new String[]{str_id_resto});
        k = resolver.delete(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), " nom = ?", new String[]{id});


        if ((a >= 1) && (j >= 1) && (k >= 1))
        {
            return true;}
        else
            return false;
    }



    public String idresto(String nom){

        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), new String[]{"ROWID"}, "nom = ?", new String[]{nom}, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToNext()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    s=cursor.getString(i);
                }
            }
        }
        return s;
    }



    public Cursor idperiode_resto(String idresto){

        Cursor cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Ouvrir"), null, "idresto = ?", new String[]{idresto}, null);
        return cursor;
    }



    public ArrayList<Cursor> Horaire_ouv_ferm(String[]list){

        Cursor cursor=null;
        int i=0;
        ArrayList<Cursor>resultat = new ArrayList();


        while(i<list.length){
            cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Periode"), null, " ROWID = ?",new String[]{list[i]}, null);
            resultat.add(cursor);
            i++;
        }

        return resultat;
    }


    public Cursor noteegale(String note){

        Cursor cursor = null;
        if(note.equals("egale"))
            cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, " note = ?",new String[]{"5"}, null);
        else
        if(note.equals("sup"))
            cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, " note >= ?",new String[]{"3"},null);
        else if(note.equals("inf"))
            cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, " note <= ?",new String[]{"3"},null);


        return cursor;

    }

    public Cursor recherche_par_adr(String adr){

        Cursor cursor=null;

        cursor = resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, " adresse = ?",new String[]{adr}, null);
        return cursor;
    }

    public Cursor recherche_par_Tc(String tc){

        Cursor c= resolver.query(Uri.parse("content://com.example.celia.projet_provider/Restaurant"), null, " type_cuisine = ?",new String[]{tc}, null);

        return c ;


    }
}
