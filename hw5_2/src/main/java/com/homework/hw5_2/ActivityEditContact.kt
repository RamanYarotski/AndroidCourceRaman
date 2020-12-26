package com.homework.hw5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ActivityEditContact : MainActivity() {
    private lateinit var nameView: TextView
    private lateinit var infoView: TextView
    private lateinit var contact: Contact
    private var contactNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        nameView = findViewById(R.id.nameEditTextView)
        infoView = findViewById(R.id.editTextViewInfo)
        val intent = intent
        if (intent != null) {
            contactNumber = intent.getIntExtra(CONTACT_NUMBER, 0)
            contact = ContactList.getContact(contactNumber)
            nameView.text = contact.name
            infoView.text = contact.info
        }
        findViewById<View>(R.id.toolbarEditSaveButton).setOnClickListener {
            val resultIntent = Intent()
            ContactList.setContact(contactNumber,
                    Contact(nameView.text.toString(),
                            infoView.text.toString(),
                            contact.infoType))
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
}

