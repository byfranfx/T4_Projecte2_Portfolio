package com.example.t4_projecte2_portfolio.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBInterface {

    // Versio
    public static final int VERSIO = 1;

    // Base de dades.
    public static final String BD_PORTFOLIO = "BD_PORTFOLIO";

    // Taules
    public static final String TAULA_USER = "TAULA_USER";
    public static final String TAULA_CRYPTO = "TAULA_CRYPTO";
    public static final String TAULA_PORTFOLIO = "TAULA_PORTFOLIO";
    public static final String TAULA_TRANSACTION = "TAULA_TRANSACTION";

    // Columnes.USER
    public static final String USER_id = "id_user";
    public static final String USER_nickname = "nickname";
    public static final String USER_password = "password";

    // Columnes.CRYPTO
    public static final String CRYPTO_ABR = "ABR";
    public static final String CRYPTO_NAME = "name";
    public static final String CRYPTO_IMG = "img";

    // Columnes.PORTFOLIO
    public static final String PORTFOLIO_id = "ID_portfolio";
    //public static final String USER_id = "id_user";

    // Columnes.TRANSACTION
    //public static final String PORTFOLIO_id = "ID_portfolio";
    //public static final String CRYPTO_ABR = "ABR";
    public static final String TRANSACTION_priceBuy = "price_buy";
    public static final String TRANSACTION_quantity = "quantity";

    // CreateTable.USER
    public static final String CT_USER = "CREATE TABLE \"" + TAULA_USER + "\" (\n" +
            "\t\"" + USER_id + "\"\tINTEGER NOT NULL,\n" +
            "\t\"" + USER_nickname + "\"\tTEXT NOT NULL,\n" +
            "\t\"" + USER_password + "\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"" + USER_id + "\" AUTOINCREMENT)\n" +
            ");";

    // CreateTable.CRYPTO
    public static final String CT_CRYPTO = "CREATE TABLE \"" + TAULA_CRYPTO + "\" (\n" +
            "\t\"" + CRYPTO_ABR + "\"\tTEXT NOT NULL,\n" +
            "\t\"" + CRYPTO_NAME + "\"\tTEXT NOT NULL,\n" +
            "\t\"" + CRYPTO_IMG + "\"\tBLOB,\n" +
            "\tPRIMARY KEY(\"" + CRYPTO_ABR + "\")\n" +
            ");";

    // CreateTable.PORTFOLIO
    public static final String CT_PORTFOLIO = "CREATE TABLE \"" + TAULA_PORTFOLIO + "\" (\n" +
            "\t\"" + PORTFOLIO_id + "\"\tINTEGER NOT NULL,\n" +
            "\t\"" + USER_id + "\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\tFOREIGN KEY(\"" + USER_id + "\") REFERENCES \"" + TAULA_USER + "\"(\"" + USER_id + "\"),\n" +
            "\tPRIMARY KEY(\"" + PORTFOLIO_id + "\" AUTOINCREMENT)\n" +
            ");";

    // CreateTable.TRANSACTION
    public static final String CT_TRANSACTION = "CREATE TABLE \"" + TAULA_TRANSACTION+ "\" (\n" +
            "\t\"" + PORTFOLIO_id + "\"\tINTEGER NOT NULL,\n" +
            "\t\"" + CRYPTO_ABR + "\"\tTEXT NOT NULL,\n" +
            "\t\"" + TRANSACTION_priceBuy + "\"\tINTEGER NOT NULL,\n" +
            "\t\"" + TRANSACTION_quantity + "\"\tINTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(\"" + CRYPTO_ABR + "\") REFERENCES \"" + TAULA_CRYPTO + "\"(\"" + CRYPTO_ABR + "\"),\n" +
            "\tFOREIGN KEY(\"" + PORTFOLIO_id + "\") REFERENCES \"" + TAULA_PORTFOLIO + "\"(\"" + PORTFOLIO_id + "\"),\n" +
            "\tPRIMARY KEY(\"" + TRANSACTION_priceBuy + "\")\n" +
            ")";

    // Constructors parametres
    private final Context context;
    private AjudaDB ajuda;
    private SQLiteDatabase bd;

    public DBInterface(Context con) {
        this.context = con;
        ajuda = new AjudaDB(context);
    }

    public DBInterface obre() {
        bd = ajuda.getWritableDatabase();
        return this;
    }

    public void tanca() {
        ajuda.close();
    }

    // Metode.USER Registrar usuari
    public long registerUser(String nickname, String password) {
        ContentValues Values = new ContentValues();
        Values.put(USER_nickname, nickname);
        Values.put(USER_password, password);
        return bd.insert(TAULA_USER, null, Values);
    }

    // Metode.USER Loggin usuari
    public Cursor logginUsuari(String nicknameRecevied, String passwordRecevied) throws SQLException {
        Cursor mCursor = bd.query(true, TAULA_USER, new String[]{USER_id,
                USER_nickname, USER_password}, USER_nickname + " = '" + nicknameRecevied + "'" + " and " + USER_password + " = '" + passwordRecevied + "'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Metode.CRYPTO addNewCoin
    public long addNewCoin(String abr, String nom, byte[] img) {
        ContentValues Values = new ContentValues();
        Values.put(CRYPTO_ABR, abr);
        Values.put(CRYPTO_NAME, nom);
        Values.put(CRYPTO_IMG, img);
        return bd.insert(TAULA_CRYPTO, null, Values);
    }

    // Metode.CRYPTO obtenirAllCrypto
    public Cursor obtenirAllCrypto() {
        return bd.rawQuery("Select " +
                "tc." + CRYPTO_ABR +
                ", tc." + CRYPTO_NAME +
                ", tc." + CRYPTO_IMG +
                " from "
                + TAULA_CRYPTO + " tc ", null);
    }

    // Metode.USER obtenirUserID
    public int obtenirUserID(String nicknameRecevied){
        //fetch string array
        int r = 0;
        Cursor c = bd.rawQuery("Select " + "tu." + USER_id +
                " from " + TAULA_USER + " tu" +
                " where " + "tu." + USER_nickname + " = '" + nicknameRecevied + "'", null);
        // Loop through the Cursor
        while(c.moveToNext()) {
            // r.add(c.getInt(0)); //<<<< see note
            r = c.getInt(0);
        }
        c.close(); //<<<< Should always close a Cursor when done with it.
        return r;
    }

    // Metode.PORTFOLIO crearPortfolio
    public long crearPortfolio(int user) {
        ContentValues Values = new ContentValues();
        Values.put(USER_id, user);
        return bd.insert(TAULA_PORTFOLIO, null, Values);
    }

    // Metode.PORTFOLIO obtenirPortfolioID
    public int obtenirPortfolioID(int user){
        //fetch string array
        int r = 0;
        Cursor c = bd.rawQuery("Select " + "tu." + PORTFOLIO_id +
                " from " + TAULA_PORTFOLIO + " tu" +
                " where " + "tu." + USER_id + " = '" + user + "'", null);
        // Loop through the Cursor
        while(c.moveToNext()) {
            // r.add(c.getInt(0)); //<<<< see note
            r = c.getInt(0);
        }
        c.close(); //<<<< Should always close a Cursor when done with it.
        return r;
    }

    // Metode.PORTFOLIO obtenirPortfolioID
    public int obtenirPortfolioID2(int user){
        //fetch string array
        int r = 0;
        Cursor c = bd.rawQuery("SELECT " + TAULA_PORTFOLIO + "." + PORTFOLIO_id + "\n" +
                "FROM " + TAULA_USER + "\n" +
                "INNER JOIN " + TAULA_PORTFOLIO + "\n" +
                "ON " + TAULA_USER + "." + USER_id + " = " + TAULA_PORTFOLIO + "." + USER_id + "\n" +
                "WHERE " + TAULA_USER + "." + USER_id + " = '" + user + "'", null);
        // Loop through the Cursor
        while(c.moveToNext()) {
            // r.add(c.getInt(0)); //<<<< see note
            r = c.getInt(0);
        }
        c.close(); //<<<< Should always close a Cursor when done with it.
        return r;
    }

    // Metode.TRANSACTION crearPortfolio
    public long crearTransaction(int portfolio, String ABR, int price, int quantity) {
        ContentValues Values = new ContentValues();
        Values.put(PORTFOLIO_id, portfolio);
        Values.put(CRYPTO_ABR, ABR);
        Values.put(TRANSACTION_priceBuy, price);
        Values.put(TRANSACTION_quantity, quantity);
        return bd.insert(TAULA_TRANSACTION, null, Values);
    }

    // Metode.TRANSACTION obtenirAllTransaction
    public Cursor obtenirAllTransaction(int id) {
        return bd.rawQuery("SELECT " +
                "tt." + PORTFOLIO_id + ", \n" +
                "tt." + CRYPTO_ABR + ", \n" +
                "tt." + TRANSACTION_priceBuy + ", \n" +
                "tt." + TRANSACTION_quantity + " \n" +
                "FROM '" + TAULA_TRANSACTION + "' tt \n" +
                "WHERE tt." + PORTFOLIO_id + " = '" + id +"'", null);
    }

    // Metode.DASHBOARD getInvestment
    public int getInvestment(int id){
        //fetch string array
        int r = 0;
        Cursor c = bd.rawQuery("SELECT \n" +
                "SUM(tt." +TRANSACTION_priceBuy + " * tt." + TRANSACTION_quantity + ")\n" +
                "FROM '" + TAULA_TRANSACTION + "' tt \n" +
                "WHERE " + PORTFOLIO_id + " = '" + id + "'", null);
        // Loop through the Cursor
        while(c.moveToNext()) {
            // r.add(c.getInt(0)); //<<<< see note
            r = c.getInt(0);
        }
        c.close(); //<<<< Should always close a Cursor when done with it.
        return r;
    }
}


