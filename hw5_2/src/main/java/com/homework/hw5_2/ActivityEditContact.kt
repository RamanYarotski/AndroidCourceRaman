package com.homework.hw5_2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.ExecutionException


class ActivityEditContact : MainActivity() {
    private lateinit var nameView: TextView
    private lateinit var infoView: TextView
    private var contactNumber = 0
    private lateinit var sPref: SharedPreferences
    private var task = 0
    private  lateinit var name: String
    private  lateinit var info: String
    private val position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        nameView = findViewById(R.id.nameEditTextView)
        infoView = findViewById(R.id.editTextViewInfo)
        val intent = intent
        if (intent != null) {
            contactNumber = intent.getIntExtra(CONTACT_NUMBER, 0)
            dBManager.openDB()
            val contact = dBManager.readDBContact(contactNumber)
            name = contact.name
            info = contact.info
            nameView.text = name
            infoView.text = info

        }
        findViewById<View>(R.id.toolbarEditSaveButton).setOnClickListener {
            val resultIntent = Intent()

            if (loadTask() == 1 || !sPref.contains("TASK")) {
                val task1 = Task1(name, applicationContext, position, info)
                task1.handler.sendEmptyMessage(2)
                task1.startThreadEdit()
                Toast.makeText(this@ActivityEditContact, "Contact change with 1 approach", Toast.LENGTH_SHORT).show()
            } else if (loadTask() == 2) {
                val task2 = Task2(name, applicationContext, position, info)
                try {
                    task2.startThreadEdit()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Toast.makeText(this@ActivityEditContact, "Contact change with 2 approach", Toast.LENGTH_SHORT).show()
            } else if (loadTask() == 3) {
                val task3 = Task3(name, applicationContext, position, info)
                task3.startThreadEdit()
                Toast.makeText(this@ActivityEditContact, "Contact change using the RX Java", Toast.LENGTH_SHORT).show()
            }

            resultIntent.putExtra(MODIFIED_CONTACT, "Contact was changed")
            setResult(RESULT_OK, resultIntent)
            finish()
            nameView.text = ""
            infoView.text = ""
        }

        findViewById<View>(R.id.toolbarEditBackButton).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<View>(R.id.removeButton).setOnClickListener {
            nameView.text = ""
            infoView.text = ""
        }
    }

    private fun loadTask(): Int {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE)
        this.task = sPref.getInt("TASK", 0)
        return task
    }
}

