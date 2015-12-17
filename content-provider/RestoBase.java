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
    private static int  NO_VERSION = 5;

    private String suppr_req = "drop table if exists Ouvrir;\n" +
            "drop table if exists Avoir;\n" +
            "drop table if exists Restaurant;\n" +
            "drop table if exists gps;\n" +
            "drop table if exists Periode;\n" +
            "drop table if exists Note;\n";

    private String creation_req = "create table if not exists Periode (\n" +
            "\n" +
            "\tidperiode integer primary key autoincrement,\n" +
            "\tjour text not null,\n" +
            "\theure_ouverture_matinale integer not null,\n" +
            "\theure_fermeture_matinale integer not null,\n" +
            "\t\n" +
            "\theure_ouverture_aprem integer not null,\n" +
            "\theure_fermeture_aprem integer not null,\n" +
            "\n" +
            "\tcheck(jour in (\"Lundi\",\"Mardi\",\"Mercredi\",\"Jeudi\",\"Vendredi\",\"Samedi\",\n" +
            "\t\t\"Dimanche\")),\n" +
            "\n" +
            "\tcheck(heure_ouverture_matinale between 8 and 12),\n" +
            "\tcheck(heure_fermeture_matinale between 9 and 12)\n" +
            "\n" +
            "\tcheck(heure_ouverture_aprem between 12 and 18),\n" +
            "\tcheck(heure_fermeture_aprem between 13 and 23)\n" +
            ");\n" +
            "create table if not exists Restaurant (\n" +
            "\n" +
            "\tidresto integer primary key autoincrement,\n" +
            "\tnom text not null unique,\n" +
            "\tadresse text not null,\n" +
            "\ttel text not null,\n" +
            "\tweb text not null,\n" +
            "\tphoto text not null,\n" +
            "\tcout integer not null,\n" +
            "\tnote integer not null,\n" +
            "\ttype_cuisine text not null,\n" +
            "\tlatitude double not null,\n" +
            "\tlongitude double not null\n" +
            "\tcheck(type_cuisine in (\"Classique\",\"Végétarien\",\"Italien\",\"Chinois\",\"Japonais\",\"Fast food\")),\n" +
            "\tcheck(note between 0 and 5)\n" +
            ");\n" +
            "create table if not exists Ouvrir (\n" +
            "\n" +
            "\tidresto integer,\n" +
            "\tidperiode integer,\n" +
            "\tprimary key(idresto,idperiode),\n" +
            "\tforeign key(idresto) references Restaurant(idresto),\n" +
            "\tforeign key(idperiode) references Periode(idperiode));";

    public RestoBase(Context context){

        super(context,DB_NAME,null, NO_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) throws SQLException{

            db.execSQL(creation_req);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion)
        {
            db.execSQL(suppr_req);
        }
    }

}
