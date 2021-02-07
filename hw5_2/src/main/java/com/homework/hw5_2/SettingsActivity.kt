package com.homework.hw5_2

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    private lateinit var sPref: SharedPreferences
    private var task = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<View>(R.id.radioButton1).setOnClickListener {
            task = 1
            saveTask(task)
            Toast.makeText(this@SettingsActivity,
                    "Mode \"ThreadPoolExecutor and Handler\"", Toast.LENGTH_SHORT).show()
            finish()
        }
        findViewById<View>(R.id.radioButton2).setOnClickListener {
            task = 2
            saveTask(task)
            Toast.makeText(this@SettingsActivity,
                    "Mode \"CompletableFuture and ThreadPoolExecutor\"", Toast.LENGTH_SHORT).show()
            finish()
        }
        findViewById<View>(R.id.radioButton3).setOnClickListener {
            task = 3
            saveTask(task)
            Toast.makeText(this@SettingsActivity,
                    "Mode \"RxJava\"", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveTask(task: Int) {
        sPref = getSharedPreferences("SettingsActivity", MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putInt("TASK", task)
        ed.apply()
    }
}