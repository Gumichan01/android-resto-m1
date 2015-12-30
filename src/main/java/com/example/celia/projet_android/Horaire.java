package com.example.celia.projet_android;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Luxon.
 */
public class Horaire implements Serializable {

    // Ouverture
    private static final int MIN_OUV_MATIN = 8;
    private static final int MAX_OUV_MATIN = 11;
    private static final int MIN_OUV_APREM = 12;
    private static final int MAX_OUV_APREM = 18;

    // Fermeture
    private static final int MAX_FERME_MATIN = 12;
    private static final int MAX_FERME_APREM = 23;

    private String ouvert_matin;
    private String ferme_matin;
    private String ouvert_aprem;
    private String ferme_aprem;

    public Horaire(){
        this(null,null,null,null);
    }

    public Horaire(String ouvre, String ferme){
        this(ouvre, null, null, ferme);
    }

    public Horaire(String o_matin, String f_matin,String o_aprem, String f_aprem){

        ouvert_matin = o_matin;
        ferme_matin = f_matin;
        ouvert_aprem = o_aprem;
        ferme_aprem = f_aprem;
    }

    public String getOuvertureMatin() {

        return ouvert_matin;
    }

    public String getFermetureMatin() {

        return ferme_matin;
    }

    public String getOuvertureAprem() {

        return ouvert_aprem;
    }

    public String getFermetureAprem() {

        return ferme_aprem;
    }

    public void setOuvrertureMatin(String s){

        try{
            int n  = Integer.parseInt(s);
            Log.d("HORAIRE", "Matin : " + n + " heures");

            if(n >= MIN_OUV_MATIN && n <= MAX_OUV_MATIN)
                ouvert_matin = new String(s);
            else
                Log.e("HORAIRE","Heure ouverture matin invalide : " + n+" heures");

        }catch (NumberFormatException ne){
            ne.printStackTrace();
        }
    }


    public void setFermetureMatin(String s){

        try{
            int n  = Integer.parseInt(s);
            Log.d("HORAIRE","Matin : " + n +" heures");

            if(n <= MAX_FERME_MATIN)
                ferme_matin = new String(s);
            else
                Log.e("HORAIRE","Heure fermeture matin invalide : " + n+" heures");

        }catch (NumberFormatException ne){
            ne.printStackTrace();
        }
    }


    public void setOuvrertureAprem(String s){

        try{
            int n  = Integer.parseInt(s);
            Log.d("HORAIRE","Après-midi : " + n +" heures");

            if(n >= MIN_OUV_APREM && n <= MAX_OUV_APREM)
                ouvert_aprem = new String(s);
            else
                Log.e("HORAIRE","Heure ouverture après-midi invalide : " + n+" heures");

        }catch (NumberFormatException ne){
            ne.printStackTrace();
        }
    }


    public void setFermetureAprem(String s){

        try{
            int n  = Integer.parseInt(s);
            Log.d("HORAIRE", "Après-midi : " + n + " heures");

            if(n <= MAX_FERME_APREM)
                ferme_aprem = new String(s);
            else
                Log.e("HORAIRE","Heure fermeture après-midi invalide : " + n+" heures");

        }catch (NumberFormatException ne){
            Log.e("HORAIRE","Erreur au niveau de parseInt() de " + s + "\n" + ne.getMessage() );
            ne.printStackTrace();
        }
    }

    @Override
    public String toString(){

        String s = " Horaire >> ";

        if(ouvert_matin != null)
            s += ouvert_matin + " heures -> ";

        if(ferme_matin != null)
            s += ferme_matin + " heures | ";

        if(ouvert_aprem != null)
            s += ouvert_aprem + " heures -> ";

        if(ferme_aprem != null)
            s += ferme_aprem + " heures | ";

        return s;
    }
}
