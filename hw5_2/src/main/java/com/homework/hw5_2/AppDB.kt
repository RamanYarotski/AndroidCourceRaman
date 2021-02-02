package com.homework.hw5_2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppDB private constructor(context: Context?, private val list: ArrayList<Contact>?)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE " + ContactsContract.TABLE_NAME + "(" +
                ContactsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ContactsContract.Columns.NAME + " TEXT NOT NULL, " +
                ContactsContract.Columns.INFO + " INFO NOT NULL)"
        db.execSQL(sql)

        if (list != null) {
            for (i in 0 until list.size) {
                // добавление начальных данных
                db.execSQL("INSERT INTO " + ContactsContract.TABLE_NAME
                        + " (" + ContactsContract.Columns.NAME + ", " + ContactsContract.Columns.INFO
                        + ") VALUES ('${list[i].name}', '${list[i].info}');")
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val DATABASE_NAME = "contacts.db"
        const val DATABASE_VERSION = 1
        private var instance: AppDB? = null
        fun getInstance(context: Context?, list: ArrayList<Contact>?): AppDB? {
            if (instance == null) {
                instance = AppDB(context, list)
            }
            return instance
        }
    }
}