package com.homework.hw5_2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri


class ContactsContentProvider : ContentProvider() {
    private var mOpenHelper: AppDB? = null
    private var list: ArrayList<Contact>? = null
    override fun onCreate(): Boolean {
        list = Main.lis
        mOpenHelper = AppDB.getInstance(context, list)
        return true
    }

    object Main : MainActivity() {
        val lis = list
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val match = sUriMatcher.match(uri)
        val queryBuilder = SQLiteQueryBuilder()
        when (match) {
            CONTACTS -> queryBuilder.tables = ContactsContract.TABLE_NAME
            CONTACTS_ID -> {
                queryBuilder.tables = ContactsContract.TABLE_NAME
                val taskId: Long = ContactsContract.getContactId(uri)
                queryBuilder.appendWhere(ContactsContract.Columns._ID + " = " + taskId)
            }
            else -> return null
        }

        val db: SQLiteDatabase? = mOpenHelper?.readableDatabase

        return queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder)
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            CONTACTS -> ContactsContract.CONTENT_TYPE
            CONTACTS_ID -> ContactsContract.CONTENT_ITEM_TYPE
            else -> return null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val match = sUriMatcher.match(uri)
        val db: SQLiteDatabase?
        val returnUri: Uri
        val recordId: Long?
        if (match == CONTACTS) {
            db = mOpenHelper?.writableDatabase
            recordId = db?.insert(ContactsContract.TABLE_NAME, null, values)
            returnUri = if (recordId != null && recordId > 0) {
                ContactsContract.buildContactUri(recordId)
            } else {
                throw SQLException("Failed to insert: $uri")
            }
        } else {
            return null
        }
        return returnUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val match = sUriMatcher.match(uri)
        val db: SQLiteDatabase? = mOpenHelper?.writableDatabase
        var selectionCriteria = selection
        require(!(match != CONTACTS && match != CONTACTS_ID)) { "Unknown URI: $uri" }
        if (match == CONTACTS_ID) {
            val taskId: Long = ContactsContract.getContactId(uri)
            selectionCriteria = ContactsContract.Columns._ID + " = " + taskId
            if (selection != null && selection.isNotEmpty()) {
                selectionCriteria += " AND ($selection)"
            }
        }
        return db!!.delete(ContactsContract.TABLE_NAME, selectionCriteria, selectionArgs)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val match = sUriMatcher.match(uri)
        val db: SQLiteDatabase? = mOpenHelper?.writableDatabase
        var selectionCriteria = selection
        require(!(match != CONTACTS && match != CONTACTS_ID)) { "Unknown URI: $uri" }
        if (match == CONTACTS_ID) {
            val taskId: Long = ContactsContract.getContactId(uri)
            selectionCriteria = ContactsContract.Columns._ID + " = " + taskId
            if (selection != null && selection.isNotEmpty()) {
                selectionCriteria += " AND ($selection)"
            }
        }
        return db!!.update(ContactsContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
    }

    companion object {
        private val sUriMatcher = buildUriMatcher()
        const val CONTACTS = 100
        const val CONTACTS_ID = 101
        private fun buildUriMatcher(): UriMatcher {
            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            // content://com.homework.hw11/CONTACTS
            matcher.addURI(ContactsContract.CONTENT_AUTHORITY, ContactsContract.TABLE_NAME, CONTACTS)
            // content://com.homework.hw11/CONTACTS_ID/6
            matcher.addURI(ContactsContract.CONTENT_AUTHORITY, ContactsContract.TABLE_NAME + "/#", CONTACTS_ID)
            return matcher
        }
    }
}