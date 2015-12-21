package com.example.celia.projet_provider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Luxon on 15/12/2015.
 */
public class RestoBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "projet-base.db";
    private static int  NO_VERSION = 13;

    private String suppr_req = "drop table if exists Restaurant;";

    private String creation_req =
            "create table Restaurant(" +
            "idresto integer," +
            "nom text," +
            "adresse text," +
            "tel text," +
            "web text," +
            "photo text," +
            "cout integer," +
            "note integer," +
            "type_cuisine text," +
            "latitude double," +
            "longitude double," +
            "check(type_cuisine in (\"Classique\",\"Végétarien\",\"Italien\",\"Chinois\",\"Japonais\",\"Fast food\"))," +
            "check(note between 0 and 5)," +
            "primary key(idresto));";

    public RestoBase(Context context){

        super(context,DB_NAME,null, NO_VERSION);
        Log.d("DATABASE_LOG", "...");
    }


    @Override
    public void onCreate(SQLiteDatabase db) throws SQLException{

        Log.d("DATABASE_LOG","Creation base");
        db.execSQL(creation_req);
        Log.d("DATABASE_LOG","Creation fait");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion)
        {
            Log.d("DATABASE_LOG","Update");
            db.execSQL(suppr_req);
            Log.d("DATABASE_LOG", "SUPPR fait");
            db.execSQL(creation_req);
            Log.d("DATABASE_LOG", "Nouvell création fait");
        }
    }

}
