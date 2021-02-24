package com.example.t4_projecte2_portfolio.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.BD_PORTFOLIO;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.CT_CRYPTO;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.CT_PORTFOLIO;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.CT_TRANSACTION;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.CT_USER;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.TAULA_CRYPTO;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.TAULA_PORTFOLIO;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.TAULA_TRANSACTION;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.TAULA_USER;
import static com.example.t4_projecte2_portfolio.BDD.DBInterface.VERSIO;

public class AjudaDB extends SQLiteOpenHelper {

    AjudaDB(Context contexte) {
        super(contexte, BD_PORTFOLIO, null, VERSIO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CT_USER);
            db.execSQL(CT_CRYPTO);
            db.execSQL(CT_PORTFOLIO);
            db.execSQL(CT_TRANSACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versioAntiga, int versioNova) {
        Log.v(TAG, "Actualitzant Base de dades de la versio" + versioAntiga + " a " +
                versioNova + ". Destruira totes les dades");
        db.execSQL("DROP TABLE IF EXISTS " + TAULA_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TAULA_CRYPTO);
        db.execSQL("DROP TABLE IF EXISTS " + TAULA_PORTFOLIO);
        db.execSQL("DROP TABLE IF EXISTS " + TAULA_TRANSACTION);
        onCreate(db);
    }
}
