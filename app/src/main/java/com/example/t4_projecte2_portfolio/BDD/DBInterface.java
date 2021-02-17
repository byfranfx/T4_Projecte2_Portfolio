package com.example.t4_projecte2_portfolio.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBInterface {

    // Versio
    public static final int VERSIO = 1;

    // Base de dades.
    public static final String BD_PORTFOLIO = "BD_PORTFOLIO";

    // Taules.USER
    public static final String TAULA_USER = "TAULA_USER";

    // Columnes.USER
    public static final String USER_id = "id_user";
    public static final String USER_nickname = "nickname";
    public static final String USER_password = "password";

    // CreateTable.USER
    public static final String CT_USER = "CREATE TABLE \"" + TAULA_USER + "\" (\n" +
            "\t\"" + USER_id + "\"\tINTEGER NOT NULL UNIQUE,\n" +
            "\t\"" + USER_nickname + "\"\tTEXT NOT NULL UNIQUE,\n" +
            "\t\"" + USER_password +"\"\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(\"" + USER_id + "\" AUTOINCREMENT)\n" +
            ");";

    //.
}
