package com.homework.hw6

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.settings.backAndApplyButton

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val sharedStorageButton = findViewById<RadioButton>(R.id.sharedStorageButton)
        val appSpecificButton = findViewById<RadioButton>(R.id.appSpecificButton)
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),
        Context.MODE_PRIVATE)

        setStorage(sharedPref, sharedStorageButton, appSpecificButton)
        backAndApplyButton.setOnClickListener {
            val result = Intent()
            val editor = sharedPref.edit()

            if (sharedStorageButton.isChecked) {
                editor.putBoolean(getString(R.string.preference_file_key), true)
                result.putExtra(getString(R.string.preference_file_key), true)
            } else {
                editor.putBoolean(getString(R.string.preference_file_key), false)
                result.putExtra(getString(R.string.preference_file_key), false)
            }
            editor.apply()
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    private fun setStorage(
            sharedPref: SharedPreferences,
            sharedStorageButton: RadioButton,
            appSpecificButton: RadioButton
    ) {
        val isSharedPrefChecked = sharedPref.getBoolean(
                getString(R.string.preference_file_key), false)
        if (isSharedPrefChecked) {
            sharedStorageButton.isChecked = true
        } else {
            appSpecificButton.isChecked = true
        }
    }
}