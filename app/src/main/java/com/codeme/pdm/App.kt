package com.codeme.pdm

import android.database.sqlite.SQLiteDatabase
import android.net.Uri

internal object App {
//    var DATABASE_PATH = System.getenv("EXTERNAL_STORAGE") + "/pdm/"
    lateinit var DATABASE_PATH: String
    lateinit var databaseFilename: String
    var Table_supp = "supp"
    var Table_product = "product"
    var Table_product_all = "product_all"
    private const val AUTHORITY = "com.codeme.pdm.Provider"
    val CONTENT_URI = Uri.parse("content://$AUTHORITY/pdm")!!
    var db: SQLiteDatabase? = null
    lateinit var spa: SectionsPagerAdapter
}
