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
    private static int  NO_VERSION = 39;

    private String suppr_ouvrir = "drop table if exists Ouvrir;";
    private String suppr_resto = "drop table if exists Restaurant;";
    private String suppr_periode = "drop table if exists Periode;";

    private String creation_resto =
            "create table Restaurant (" +
                    "nom text unique," +
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
            "heure_ouverture_matinale integer," +
            "heure_fermeture_matinale integer," +
            "heure_ouverture_aprem integer," +
            "heure_fermeture_aprem integer," +
            "check(jour in (\"Lundi\",\"Mardi\",\"Mercredi\",\"Jeudi\",\"Vendredi\",\"Samedi\"," +
            "\"Dimanche\")));";

    private String creation_ouvrir =
            "create table if not exists Ouvrir (" +
                    "idresto integer," +
                    "idperiode integer," +
                    "foreign key(idresto) references Restaurant(ROW_ID)," +
                    "foreign key(idperiode) references Periode(ROW_ID)" +
                    ");";


    public RestoBase(Context context){

        super(context, DB_NAME, null, NO_VERSION);
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
            db.execSQL(suppr_ouvrir);
            Log.d("DATABASE_LOG", "SUPPR ouvrir fait");
            db.execSQL(suppr_resto);
            Log.d("DATABASE_LOG", "SUPPR resto fait");
            db.execSQL(suppr_periode);
            Log.d("DATABASE_LOG", "SUPPR periode fait");
            db.execSQL(creation_periode);
            Log.d("DATABASE_LOG", "Create PERIODE fait");
            db.execSQL(creation_resto);
            Log.d("DATABASE_LOG", "Create RESTO fait");
            db.execSQL(creation_ouvrir);
            Log.d("DATABASE_LOG", "Create OUVRIR fait");
            Log.d("DATABASE_LOG", "SUCCESS Mise à jour de la base de données");
        }
    }

}