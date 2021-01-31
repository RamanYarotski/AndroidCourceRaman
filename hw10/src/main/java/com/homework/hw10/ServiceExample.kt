package com.homework.hw10

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class ServiceExample : Service(), ServiceActions {

    private val resultFile: String = "Result_HW10.txt"

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG, "Work_onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG, "Work_onStartCommand")
        val bl: Bundle? = intent?.extras
        val event: String? = bl?.getString("EVENT")
        someJob(event)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return ServiceBinder()
    }

    inner class ServiceBinder() : Binder() {
        fun getServiceActions(): ServiceActions =
                this@ServiceExample
    }

    private fun someJob(event: String?) {
        val runnable = Runnable {
            if (event != null) {
                if (loadBooleanStatement()) {
                    Log.d(LOG, "Write to external Storage")
                    writeToExternal(event)
                } else if (!loadBooleanStatement()) {
                    Log.d(LOG, "Write to internal Storage")
                    writeToInternal(event)
                }
            }
        }
        Thread(runnable).start()
    }

    private fun writeToInternal(event: String?) {
        val date = Date(System.currentTimeMillis())
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDate: String = formatter.format(date)
        val bw = BufferedWriter(OutputStreamWriter(
                openFileOutput(resultFile, Context.MODE_APPEND)))
        bw.write("$currentDate - $event\n")
        bw.close()
    }

    private fun writeToExternal(event: String?) {
        val date = Date(System.currentTimeMillis())
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDate: String = formatter.format(date)
        val externalPath = File(Environment.getExternalStorageDirectory()
                .absolutePath, "Actions")
        externalPath.mkdir()
        val externalFile = File(externalPath, resultFile)
        val bw = BufferedWriter(FileWriter(externalFile, true))
        bw.write("$currentDate - $event\n")
        bw.close()
    }

    override fun getData(): Int {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG, "Work method onDestroy")
    }

    private fun loadBooleanStatement(): Boolean {
        val sharedPreferences = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("STATEMENT", false)
    }

    companion object {
        private val LOG = "ServiceExample"
    }
}
