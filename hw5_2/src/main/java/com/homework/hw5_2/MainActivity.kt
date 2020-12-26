package com.homework.hw5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class MainActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var adapter: ContactListAdapter
    private lateinit var recyclerView: RecyclerView

    interface ListContactActionListener {
        fun onContactClicked(number: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ContactListAdapter(
                object : ListContactActionListener {
                    override fun onContactClicked(number: Int) {
                        val intent = Intent(
                                this@MainActivity, ActivityEditContact::class.java)
                        intent.putExtra(CONTACT_NUMBER, number)
                        startActivityForResult(intent, 456)
                    }
                })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        findViewById<View>(R.id.buttonAddContact).setOnClickListener {
            startActivityForResult(
                    Intent(this@MainActivity, ActivityAddContact::class.java),
                    123)
        }
        searchView = findViewById(R.id.searchContact)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            adapter.addItems(ContactList.getContactList())
        } else if (requestCode == 456 && resultCode == RESULT_OK && data != null) {
            adapter.addItems(ContactList.getContactList())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        adapter.addItems(ContactList.getContactList())
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        const val NEW_CONTACT = "NewContact"
        const val CONTACT_NUMBER = "ContactNumber"
        const val MODIFIED_CONTACT = "ModifiedContact"
    }
}
