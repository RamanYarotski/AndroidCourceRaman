package com.homework.hw5_2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


class Task1 : Runnable {
    private var counter = 0
    private var name: String
    private var info: String
    private var context: Context
    private lateinit var infoType:String
    private var position = 0
    private var threadPoolExecutor: ThreadPoolExecutor? = null


    constructor(name: String, context: Context, position: Int, info: String) {
        this.name = name
        this.context = context
        this.position = position
        this.info = info
    }

    constructor(counter: Int, name: String, info: String, infoType: String , context: Context) {
        this.counter = counter
        this.name = name
        this.info = info
        this.context = context
        this.infoType = infoType
    }

    fun startThreadAdd() {
        threadPoolExecutor = Executors.newFixedThreadPool(1) as ThreadPoolExecutor
        threadPoolExecutor!!.submit(Task1(counter, name, info, infoType, context))
    }

    fun startThreadEdit() {
        threadPoolExecutor = Executors.newFixedThreadPool(1) as ThreadPoolExecutor
        threadPoolExecutor!!.submit(Task1(name, context, position, info))
    }

    @SuppressLint("HandlerLeak")
    val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                insertDB()
            } else if (msg.what == 2) {
                updateDB()
            }
        }
    }

    override fun run() {}
    private fun insertDB() {
        val dBManager = com.homework.hw5_2.db.DBManager(context)
        dBManager.openDB()
        dBManager.addToDB(name, info, infoType)
    }

    private fun updateDB() {
        val dBManager = com.homework.hw5_2.db.DBManager(context)
        dBManager.openDB()
        dBManager.updateInDB(position, name, info)
    }
}
