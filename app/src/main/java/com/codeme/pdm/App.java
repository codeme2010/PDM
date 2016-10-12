package com.codeme.pdm;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

class App {
    static String DATABASE_PATH = System.getenv("EXTERNAL_STORAGE") + "/pdm/";
    static String Table_supp = "supp";
    static String Table_product = "product";
    static String databaseFilename = DATABASE_PATH + "pdm.db";
    private static final String AUTHORITY = "com.codeme.pdm.Provider";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pdm");
    static SQLiteDatabase db;

}
