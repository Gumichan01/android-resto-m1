package com.example.celia.projet_provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

//import java.lang.UnsupportedOperationException;

/**
 * Created by celia on 15/12/15.
 * Update by Luxon on 17/12/2015
 */
public class RestoContentProvider extends ContentProvider {
    private static final String authority = "com.example.celia.projet_provider";
    private static final String ouvrir_id_resto_key = "idresto";
    private static final String ouvrir_id_periode_key = "idperiode";

    public static final Uri CONTENT_URI0 =
            Uri.parse("content://"+ authority + "/Restaurant");
    public static final Uri CONTENT_URI1 =
            Uri.parse("content://"+ authority + "/Periode");

    public static final Uri CONTENT_URI2 =
            Uri.parse("content://"+ authority + "/Ouvrir");
    private static final UriMatcher matcher ;
    private static int id = 1;

    static {
        matcher= new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(authority, "Restaurant", 0);
        matcher.addURI(authority, "Periode", 1);
        matcher.addURI(authority, "Ouvrir", 2);
    }

    // Base de données
    RestoBase base;
    private String table_resto = "Restaurant";
    private String table_periode = "Periode";
    private String table_ouvrir = "Ouvrir";

    // Struture nécessaire pour les ROW_ID de Periode
    private ArrayList<Long> row_ids =  new ArrayList<>();

    @Override
    public boolean onCreate() {

        try{
            base = new RestoBase(getContext());
        }catch (SQLException sqle){

            Log.e("DATABASE_LOG", "Echec de la création de la base de données - " +
                    sqle.getMessage());
            return false;
        }

        Log.e("DATABASE_LOG", "SUCCESS de la création de la base de données - ");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = null;

        try{
            db = base.getReadableDatabase();
        }catch(SQLException sqle){
            Log.e("DATABASE_LOG", "Provider - Echec de la création de la base de données - " +
                    sqle.getMessage());
        }

        Cursor resultat = null;
        switch (matcher.match(uri)) {
            case 0:

                return db.query(table_resto, projection, selection, selectionArgs, null, null, sortOrder);
            case 1:

                return db.query(table_periode, projection, selection, selectionArgs, null, null, sortOrder);
            case 2:
                return db.query(table_ouvrir, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                return null;
        }



    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = null;

        try{

            db = base.getWritableDatabase();

        }catch(SQLiteException e){

            Log.e("W_DATABASE_GET", "Cannot get the writable database");
        }

        Uri uriR = null;
        switch (matcher.match(uri)){
            case 0:
            {
                long _ID0 = db.insert(table_resto,"",values);
                //---if added successfully---
                if (_ID0 > 0) {
                    Log.d("DATABASE_LOG","Inserted in Resto, ROW_ID : " + _ID0);
                    uriR = ContentUris.withAppendedId(CONTENT_URI0, _ID0);
                    //getContext().getContentResolver().notifyChange(_uri, null);
                }

                if(row_ids.size() > 0){

                    Log.d("DATABASE_LOG", "Trying multiple insertion in Ouvrir");
                    if(insertIntoOuvrirValues(_ID0))
                        Log.d("DATABASE_LOG", "SUCCESS of multiple insertion in Ouvrir");
                    else{
                        Log.e("DATABASE_LOG", "FAILURE of multiple insertion in Ouvrir");
                        // Echec, donc on met uriR à null pour prévenir la fonction appelante
                        uriR = null;
                    }
                }
                else{
                    Log.e("DATABASE_LOG", "Restaurant sans horaire, ne DOIT JAMAIS être atteint");
                }

            }
            break;
            case 1:
                long _ID1 = db.insert(table_periode, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    Log.d("DATABASE_LOG","Inserted in Periode, ROW_ID : " + _ID1);

                    // Ajout à la liste des ROW_ID
                    /*
                        A chaque ajout, insert(), en cas de success, renvoie l'ID de la ligne
                        insérée dans la table en cas de succes
                    */
                    row_ids.add(_ID1);
                    uriR = ContentUris.withAppendedId(CONTENT_URI1, _ID1);
                    //  getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case 3:
                long _ID2 = db.insert(table_ouvrir, "", values);
                //---if added successfully---
                if (_ID2 > 0) {
                    Log.d("DATABASE_LOG","Inserted in Ouvrir, ROW_ID : " + _ID2);
                    uriR = ContentUris.withAppendedId(CONTENT_URI2, _ID2);
                    //  getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }

        return uriR;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = null;

        db = base.getWritableDatabase();

        //suppression du resto sans prendre en compte les horraire
        switch (matcher.match(uri)) {

            case 0:
                return db.delete(table_resto, selection, selectionArgs);

            case 1:
                return db.delete(table_periode, selection, selectionArgs);

            case 2:
                return db.delete(table_ouvrir, selection, selectionArgs);


            default:
                return -1;

        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }

    // Fonction auxiliaire qui insère tous les couple (idresto,row_ids[i]) pour i indice de la list
    private boolean insertIntoOuvrirValues(long idresto){

        ContentValues values = new ContentValues();
        SQLiteDatabase db = null;

        try{
            db = base.getWritableDatabase();
        } catch (SQLiteException e) {

            Log.e("DATABASE_LOG","Cannot get the database : " + e.toString());
            return false;
        }

        for (Long id : row_ids) {

            values.put(ouvrir_id_resto_key, idresto);
            values.put(ouvrir_id_periode_key, id);

            if(db.insert(table_ouvrir, "", values) == -1){

                Log.e("DATABASE_LOG","Could not INSERT " + values.toString() + " INTO " + CONTENT_URI2.toString());
                // Nettoyage de l'arraylist en cas d'échec
                row_ids.clear();
                return false;
            }
            else{
                Log.d("DATABASE_LOG","INSERT OUVRIR");
            }

            values.clear();
        }
        row_ids.clear();
        return true;
    }


}