package com.homework.hw5_2

import android.content.ContentUris
import android.net.Uri

object ContactsContract {
    const val TABLE_NAME = "contacts"
    const val CONTENT_AUTHORITY = "com.homework.hw11"
    val CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY)
    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME
    val CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    // создает uri с помощью id
    fun buildContactUri(taskId: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, taskId)
    }

    // получает id из uri
    fun getContactId(uri: Uri?): Long {
        return ContentUris.parseId(uri!!)
    }

    object Columns {
        const val _ID = "_id"
        const val NAME = "Name"
        const val INFO = "Info"
    }
}