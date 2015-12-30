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
    private static int  NO_VERSION = 32;

    private String suppr_resto = "drop table if exists Restaurant;";
    private String suppr_periode = "drop table if exists Periode;";

    private String creation_resto =
            "create table Restaurant (" +
                    "nom text," +
                    "adresse text," +
                    "tel text," +
                    "web text," +
                    "note integer," +
                    "cout integer," +
                    "photo text," +
                    "type_cuisine text," +
                    "latitude double," +
                    "longitude double," +
                    "check(type_cuisine in (\"Classique\",\"Végétarien\",\"Italien\",\"Chinois\",\"Japonais\",\"Fast food\")),\n" +
                    "check(note between 0 and 5)" +
                    ");";

    private String creation_periode =
            "create table Periode (" +
            "jour text," +
            "heure_ouverture_matinale," +
            "heure_fermeture_matinale integerl," +
            "heure_ouverture_aprem integer," +
            "heure_fermeture_aprem integer," +
            "check(jour in (\"Lundi\",\"Mardi\",\"Mercredi\",\"Jeudi\",\"Vendredi\",\"Samedi\"," +
            "\"Dimanche\"))," +
            "check(heure_ouverture_matinale between 8 and 11)," +
            "check(heure_fermeture_matinale between 9 and 12)," +
            "check(heure_ouverture_aprem between 13 and 18)," +
            "check(heure_fermeture_aprem between 14 and 23));";

    public RestoBase(Context context){

        super(context,DB_NAME,null, NO_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) throws SQLException{

        Log.d("DATABASE_LOG","Creation base");
        db.execSQL(creation_periode);
        Log.d("DATABASE_LOG", "Create PERIODE fait");
        db.execSQL(creation_resto);
        Log.d("DATABASE_LOG", "Create RESTO fait");
        Log.d("DATABASE_LOG", "SUCCESS creation de la base de données");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion)
        {
            Log.d("DATABASE_LOG","Update");
            db.execSQL(suppr_resto);
            Log.d("DATABASE_LOG", "SUPPR resto fait");
            db.execSQL(suppr_periode);
            Log.d("DATABASE_LOG", "SUPPR periode fait");
            db.execSQL(creation_periode);
            Log.d("DATABASE_LOG", "Create PERIODE fait");
            db.execSQL(creation_resto);
            Log.d("DATABASE_LOG", "Create RESTO fait");
            Log.d("DATABASE_LOG", "SUCCESS Mise à jour de la base de données");
        }
    }

}