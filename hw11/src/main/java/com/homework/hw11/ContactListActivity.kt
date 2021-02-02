package com.homework.hw11

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.InvalidParameterException

open class ContactListActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    private val contactList: ArrayList<Contact> = ArrayList()
    private lateinit var adapter: ContactListAdapter
    private lateinit var recyclerView: RecyclerView
    private val TAG = "Contacts"

    interface ListContactActionListener {
        fun onContactClicked(number: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
//        getAll()
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ContactListAdapter(
                object : ListContactActionListener {
                    override fun onContactClicked(number: Int) {
                    }
                })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
                this, RecyclerView.VERTICAL, false)
        adapter.addItems(contactList)

    }

    private fun getAll() {
        val projection = arrayOf(
                ContactsContract.Columns._ID,
                ContactsContract.Columns.NAME,
                ContactsContract.Columns.INFO
        )
        val contentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(ContactsContract.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Columns.NAME)
        if (cursor != null) {
            Log.d(TAG, "count: " + cursor.count)
            // перебор элементов
            while (cursor.moveToNext()) {
                for (i in 0 until cursor.columnCount) {
                    Log.d(TAG, cursor.getColumnName(i).toString() + " : " + cursor.getString(i))
                    contactList.add(Contact(cursor.getColumnName(i).toString(),
                            cursor.getString(i),
                            Contact.InfoType.PHONE_NUMBER))
                }
                Log.d(TAG, "=========================")
            }
            cursor.close()
        } else {
            Log.d(TAG, "Cursor is null")
        }
    }

    // Добавление
    fun add() {
        val contentResolver = contentResolver
        val values = ContentValues()
        values.put(ContactsContract.Columns.NAME, "Denis")
        values.put(ContactsContract.Columns.INFO, "denis.it.cources@gmail.com")
        val uri: Uri? = contentResolver.insert(ContactsContract.CONTENT_URI, values)
        Log.d(TAG, "Contact added")
    }

    // Обновление
    fun update() {
        val contentResolver = contentResolver
        val values = ContentValues()
        values.put(ContactsContract.Columns.NAME, "Denis Latushko")
        values.put(ContactsContract.Columns.INFO, "denis.it.cources@gmail.com")
        val selection: String = ContactsContract.Columns.NAME + " = 'Denis'"
        val count = contentResolver.update(ContactsContract.CONTENT_URI, values, selection, null)
        Log.d(TAG, "Contact updated")
    }

    // Удаление
    fun delete() {
        val contentResolver = contentResolver
        val selection: String = ContactsContract.Columns.NAME + " = ?"
        val args = arrayOf("Denis Latushko")
        val count = contentResolver.delete(ContactsContract.CONTENT_URI, selection, args)
        Log.d(TAG, "Contact deleted")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        adapter.addItems(contactList)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(
                ContactsContract.Columns._ID,
                ContactsContract.Columns.NAME,
                ContactsContract.Columns.INFO
        )
        return if (id == LOADER_ID) CursorLoader(this, ContactsContract.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Columns.NAME) else throw InvalidParameterException("Invalid loader id")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        if (cursor != null) {
            Log.d(TAG, "count: " + cursor.count)
            // перебор элементов
            while (cursor.moveToNext()) {
                for (i in 0 until cursor.columnCount) {
                    Log.d(TAG, cursor.getColumnName(i).toString() + " : " + cursor.getString(i))
                    contactList.add(Contact(cursor.getColumnName(i).toString(),
                            cursor.getString(i),
                            Contact.InfoType.PHONE_NUMBER))
                }
                Log.d(TAG, "=========================")
            }
            cursor.close()
        } else {
            Log.d(TAG, "Cursor is null")
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Log.d(TAG, "onLoaderReset...");
    }

    companion object {
        private const val LOADER_ID = 666
    }

    object Columns {
        const val _ID = "_id"
        const val NAME = "Name"
        const val INFO = "Info"
    }
}
