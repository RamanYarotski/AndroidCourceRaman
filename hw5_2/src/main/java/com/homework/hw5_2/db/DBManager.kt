package com.homework.hw5_2.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.homework.hw5_2.Contact

class DBManager(val context: Context) {
    private lateinit var contact: Contact
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = dbHelper.writableDatabase
    }

    fun addToDB(name: String, info: String, infoType: String) {
        val values = ContentValues().apply {
            put(DBClass.COLUMN_NAME_NAME, name)
            put(DBClass.COLUMN_NAME_INFO, info)
            put(DBClass.COLUMN_NAME_INFO_TYPE, infoType)
        }
        db?.insert(DBClass.TABLE_NAME, null, values)
    }


    fun updateInDB(index: Int, name: String, info: String) {
        val values = ContentValues().apply {
            put(DBClass.COLUMN_NAME_NAME, name)
            put(DBClass.COLUMN_NAME_INFO, info)
        }
        db?.update(DBClass.TABLE_NAME, values, "_id = ?", arrayOf(index.toString()))
    }

    fun readDBData(): ArrayList<Contact> {
        val dataList = ArrayList<Contact>()
        val cursor = db?.query(DBClass.TABLE_NAME, null, null,
                null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val name = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_NAME))
            val info = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_INFO))
            val infoTypeString = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_INFO_TYPE))

            var infoType: Contact.InfoType = Contact.InfoType.PHONE_NUMBER
            if (infoTypeString == "EMAIL") infoType = Contact.InfoType.EMAIL

            dataList.add(Contact(name, info, infoType))
        }
        cursor.close()
        return dataList
    }

    fun readDBContact(index: Int): Contact {
        val cursor = db?.query(DBClass.TABLE_NAME, null, "_id = ?",
                arrayOf(index.toString()), null, null, null)
        while (cursor?.moveToNext()!!) {
            val name = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_NAME))
            val info = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_INFO))
            val infoTypeString = cursor.getString(cursor.getColumnIndex(DBClass.COLUMN_NAME_INFO_TYPE))

            var infoType: Contact.InfoType = Contact.InfoType.PHONE_NUMBER
            if (infoTypeString == "EMAIL") infoType = Contact.InfoType.EMAIL
            contact = (Contact(name, info, infoType))
        }
        cursor.close()
        return contact
    }

    fun closeDB() {
        dbHelper.close()
    }
}