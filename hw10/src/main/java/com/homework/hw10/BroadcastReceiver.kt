package com.homework.hw10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastReceiver : BroadcastReceiver() {
    private val eventPower: String = "ACTION_POWER_CONNECTED"
    private val eventTick: String = "ACTION_TIME_TICK"
    private val eventTimeZone: String = "ACTION_TIMEZONE_CHANGED"

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action ?: "") {
            Intent.ACTION_POWER_CONNECTED -> sendTaskToService(eventPower, context)
            Intent.ACTION_TIME_TICK -> sendTaskToService(eventTick, context)
            Intent.ACTION_TIMEZONE_CHANGED -> sendTaskToService(eventTimeZone, context)
        }
    }

    private fun sendTaskToService(event: String, context: Context?) {
        val intent = Intent(context, ServiceExample::class.java)
        intent.putExtra("EVENT", event)
        context?.startService(intent)
    }
}