package com.gumichan.luxon.projet_base;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Luxon on 15/12/2015.
 */
public class BaseTest extends SQLiteOpenHelper {

    private static final String DB_NAME = "projet-base.db";
    private static int  NO_VERSION = 3;

    private String suppr_req = "drop table if exists Ouvrir;\n" +
            "drop table if exists Avoir;\n" +
            "drop table if exists Restaurant;\n" +
            "drop table if exists gps;\n" +
            "drop table if exists Periode;\n" +
            "drop table if exists Note;\n";

    private String creation_req = "create table if not exists Note (\n" +
            "\n" +
            "\tidnote integer primary key autoincrement,\n" +
            "\tvaleur integer not null, check(valeur between 0 and 5)\n" +
            ");\n" +
            "\n" +
            "create table if not exists Periode (\n" +
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
            "\tcheck(heure_ouverture_matinale between 0 and 23),\n" +
            "\tcheck(heure_fermeture between 0 and 23)\n" +
            "\n" +
            "\tcheck(heure_ouverture_aprem between 0 and 23),\n" +
            "\tcheck(heure_fermeture_aprem between 0 and 23)\n" +
            ");\n" +
            "create table if not exists gps (\n" +
            "\n" +
            "\tidgps integer primary key autoincrement,\n" +
            "\tlatitude double not null,\n" +
            "\tlongitude double not null\n" +
            ");\n" +
            "create table if not exists Restaurant (\n" +
            "\n" +
            "\tidresto integer primary key autoincrement,\n" +
            "\tnom text not null unique,\n" +
            "\tadresse text not null,\n" +
            "\tville text not null,\n" +
            "\ttel text not null,\n" +
            "\tweb text not null,\n" +
            "\tphoto text not null,\n" +
            "\tcout integer not null,\n" +
            "\ttype_cuisine text not null,\n" +
            "\tidgps not null unique,\n" +
            "\tcheck(type_cuisine in (\"Classique\",\"Végétarien\",\"Italien\",\"Chinois\",\"Japonais\",\"Fast food\"))\n" +
            "\tforeign key(idgps) references gps(idgps)\n" +
            ");\n" +
            "\n" +
            "create table if not exists Avoir (\n" +
            "\n" +
            "\tidresto integer,\n" +
            "\tidnote integer,\n" +
            "\tprimary key(idresto,idnote),\n" +
            "\tforeign key(idresto) references Restaurant(idresto),\n" +
            "\tforeign key(idnote) references Note(idnote)\n" +
            ");\n" +
            "create table if not exists Ouvrir (\n" +
            "\n" +
            "\tidresto integer,\n" +
            "\tidperiode integer,\n" +
            "\tprimary key(idresto,idperiode),\n" +
            "\tforeign key(idresto) references Restaurant(idresto),\n" +
            "\tforeign key(idperiode) references Periode(idperiode)\n" +
            ");";

    public BaseTest(Context context){

        super(context,DB_NAME,null, NO_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            db.execSQL(creation_req);

        }catch(SQLException e)
        {
            Log.e("ERROR_DB_CREATION", "Echec de la création de la base " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion)
        {
            db.execSQL(suppr_req);
        }
    }

}
