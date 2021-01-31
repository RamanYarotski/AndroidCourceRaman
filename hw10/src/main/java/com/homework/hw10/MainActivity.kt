package com.homework.hw10

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private var switchStorage: Boolean = false
    private lateinit var switch: SwitchCompat
    private val broadcastReceiver by lazy { BroadcastReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.stopService).setOnClickListener {
            stopService(Intent(this@MainActivity, ServiceExample::class.java))
        }

        switch = findViewById(R.id.switcher)
        switch.isChecked = loadBooleanStatement()

        switch.setOnCheckedChangeListener { _, isChecked ->
            switchStorage = if (isChecked) {
                Toast.makeText(this, "External Storage", Toast.LENGTH_SHORT).show()
                true
            } else {
                Toast.makeText(this, "Internal Storage", Toast.LENGTH_SHORT).show()
                false
            }
            saveBooleanStatement(switchStorage)
        }
    }

    override fun onStart() {
        super.onStart()
        findViewById<MaterialButton>(R.id.startService).setOnClickListener {
            val intentFilter = IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_TIME_TICK)
                addAction(Intent.ACTION_TIMEZONE_CHANGED)
            }
            registerReceiver(broadcastReceiver, intentFilter)
            startService(Intent(this@MainActivity, ServiceExample::class.java))
        }
    }

    private fun loadBooleanStatement(): Boolean {
        val sharedPreferences = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("STATEMENT", false)
    }

    private fun saveBooleanStatement(switchStorage: Boolean) {
        val sharedPreferences = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sharedPreferences.edit()
        ed.putBoolean("STATEMENT", switchStorage)
        ed.apply()
    }
}
