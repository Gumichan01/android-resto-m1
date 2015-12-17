package com.example.celia.projet_provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

//import java.lang.UnsupportedOperationException;

/**
 * Created by celia on 15/12/15.
 * Update by Luxon on 17/12/2015
 */
public class RestoContentProvider extends ContentProvider {

    //appelé la bdd
    RestoBase bd;


    private static final String authority = "com.example.celia";

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(authority, "Note", 0);
        matcher.addURI(authority, "Periode/*", 1);
        matcher.addURI(authority, "gps/*", 2);
        matcher.addURI(authority, "Restaurant/*", 3);
        matcher.addURI(authority, "Avoir/*", 4);
        matcher.addURI(authority, "Ouvrir/*", 5);
    }

    @Override
    public boolean onCreate() {

        try{
            bd = new RestoBase(getContext());
        }catch (SQLException sqle){

            Log.e("RESTO_DB_CREATION", "Echec de la création de la base "+
                    " de données des restaurants - " + sqle.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        throw new UnsupportedOperationException("TODO: recherche");
        //return null;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {




            throw new UnsupportedOperationException("TODO: insertion");
            //return null;


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
