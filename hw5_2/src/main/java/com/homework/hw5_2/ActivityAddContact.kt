package com.homework.hw5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView

class ActivityAddContact : MainActivity() {
    private lateinit var phoneButton: RadioButton
    private lateinit var emailButton: RadioButton
    private lateinit var nameView: TextView
    private lateinit var infoView: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        phoneButton = findViewById(R.id.PhoneButton)
        phoneButton.setOnClickListener(radioButtonClickListener)
        emailButton = findViewById(R.id.EmailButton)
        emailButton.setOnClickListener(radioButtonClickListener)
        nameView = findViewById(R.id.nameEditTextView)
        infoView = findViewById(R.id.editTextViewInfo)
        findViewById<View>(R.id.toolbarBackButton).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        findViewById<View>(R.id.toolbarSaveButton).setOnClickListener {
            val resultIntent = Intent()
            if (phoneButton.isChecked) {
                ContactList.addContact(
                        Contact(nameView.text.toString(),
                                infoView.text.toString(),
                                Contact.InfoType.PHONE_NUMBER))
                resultIntent.putExtra(NEW_CONTACT, "Contact added")
            } else if (emailButton.isChecked) {
                ContactList.addContact(
                        Contact(nameView.text.toString(),
                                infoView.text.toString(),
                                Contact.InfoType.EMAIL))
                resultIntent.putExtra(NEW_CONTACT, "Contact added")
            }
            setResult(RESULT_OK, resultIntent)
            finish()
            nameView.text = ""
            infoView.text = ""
        }
    }

    private var radioButtonClickListener: View.OnClickListener = View.OnClickListener { v ->
        val rb = v as RadioButton
        when (rb.id) {
            R.id.PhoneButton -> infoView.setHint(R.string.Phone_number)
            R.id.EmailButton -> infoView.setHint(R.string.Email)
            else -> {
            }
        }
    }
}
