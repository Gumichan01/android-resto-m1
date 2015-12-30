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

//import java.lang.UnsupportedOperationException;

/**
 * Created by celia on 15/12/15.
 * Update by Luxon on 17/12/2015
 */
public class RestoContentProvider extends ContentProvider {
    private static final String authority = "com.example.celia.projet_provider";

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

        return db.query(table_resto, projection, selection, selectionArgs,null,null, sortOrder);
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
                    uriR = ContentUris.withAppendedId(CONTENT_URI0, _ID0);
                    //getContext().getContentResolver().notifyChange(_uri, null);
                }}
            break;
            case 1:
                long _ID1 = db.insert(table_periode, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    uriR = ContentUris.withAppendedId(CONTENT_URI1, _ID1);
                    //  getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case 3:
                long _ID2 = db.insert(table_ouvrir, "", values);
                //---if added successfully---
                if (_ID2 > 0) {
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

        return  db.delete(table_resto,selection,selectionArgs);

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }}