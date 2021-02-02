package com.homework.hw5_2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.ExecutionException

class ActivityAddContact : MainActivity() {
    private lateinit var phoneButton: RadioButton
    private lateinit var emailButton: RadioButton
    private lateinit var nameView: TextView
    private lateinit var infoView: TextView
    private lateinit var sPref: SharedPreferences

    private var task = 0
    var settingsActivity: SettingsActivity? = null

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

//////////////////////////////////////////////////////////
            settingsActivity = SettingsActivity()

            val resultIntent = Intent()
            var iT: Contact.InfoType = Contact.InfoType.PHONE_NUMBER
            if (emailButton.isChecked) {
                iT = Contact.InfoType.EMAIL
            }
            val dBSize = dBManager.countContacts() + 1
            val name = nameView.text.toString()
            val info = infoView.text.toString()
            val infoType = iT.toString()

            if (loadTask() == 1 || !sPref.contains("TASK")) {
                val task1 = Task1(dBSize, name, info, infoType, applicationContext)
                task1.handler.sendEmptyMessage(1)
                task1.startThreadAdd()
                Toast.makeText(this@ActivityAddContact, "The object has been added using 1 approach", Toast.LENGTH_SHORT).show()
            } else if (loadTask() == 2) {
                val task2 = Task2(dBSize, name, info, infoType, applicationContext)
                try {
                    task2.startThreadAdd()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Toast.makeText(this@ActivityAddContact, "The object has been added using 2 approach", Toast.LENGTH_SHORT).show()
            } else if (loadTask() == 3) {
                val task3 = Task3(dBSize, name, info, infoType, applicationContext)
                task3.startThreadAdd()
                Toast.makeText(this@ActivityAddContact, "The object is added using the RX Java", Toast.LENGTH_SHORT).show()
            }

            resultIntent.putExtra(NEW_CONTACT, "Contact added")
            setResult(RESULT_OK, resultIntent)
            nameView.text = ""
            infoView.text = ""
            finish()
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


    private fun loadTask(): Int {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE)
        this.task = sPref.getInt("TASK", 0)
        return task
    }

}
