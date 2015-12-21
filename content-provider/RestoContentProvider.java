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

    //appelé la bdd
    RestoBase base;
    private String table_resto = "Restaurant";
    private String table_periode = "Periode";
    private String table_ouvrir = "Ouvrir";
    private static int id = 1;


    private static final String authority = "com.example.celia.projet_provider";
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //matcher.addURI(authority, "Periode/*", 0);
        matcher.addURI(authority, "Restaurant", 1);
        //matcher.addURI(authority, "Ouvrir/*", 2);
    }

    @Override
    public boolean onCreate() {

        try{
            base = new RestoBase(getContext());
        }catch (SQLException sqle){

            Log.e("DATABASE_LOG", "Echec de la création de la base "+
                    " de données des restaurants - " + sqle.getMessage());
            return false;
        }

        Log.e("DATABASE_LOG", "SUCCESS de la création de la base "+
                " de données des restaurants - ");

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = null;

        try{
            db = base.getReadableDatabase();
        }catch(SQLException sqle){
            Log.e("DATABASE_LOG", "Echec de la création de la base "+
                    " de données des restaurants - " + sqle.getMessage());
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

        values.put("idresto",id);
        id++;

        try{

            db = base.getWritableDatabase();

        }catch(SQLiteException e){

            Log.e("W_DATABASE_GET", "Cannot get the writable database");
        }

        long result = db.insert(table_resto,null,values);

        return ContentUris.withAppendedId(Uri.parse("content://com.example.celia.projet_provider"),
                                            result);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("TODO: suppression");
        //return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
