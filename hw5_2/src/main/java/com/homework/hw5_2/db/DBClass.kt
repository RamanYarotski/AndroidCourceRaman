package com.homework.hw5_2.db

import android.provider.BaseColumns

object DBClass {
    const val TABLE_NAME = "Contacts"
    const val COLUMN_NAME_NAME = "Name"
    const val COLUMN_NAME_INFO = "info"
    const val COLUMN_NAME_INFO_TYPE = "infoType"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "HW7_Contacts.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
    "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_NAME TEXT," +
            "$COLUMN_NAME_INFO TEXT, $COLUMN_NAME_INFO_TYPE TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}