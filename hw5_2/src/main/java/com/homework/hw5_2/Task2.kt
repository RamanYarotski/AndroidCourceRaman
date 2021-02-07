package com.homework.hw5_2

import android.content.Context
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.function.Supplier


class Task2 {
    private var counter = 0
    private var name: String
    private var info: String
    private lateinit var infoType:String
    private var context: Context
    private var position = 0
    var supplier: Supplier<*>? = null
    private lateinit var future: CompletableFuture<Void>
    private var threadPoolExecutor: ThreadPoolExecutor? = null


    constructor(counter: Int, name: String, info: String, infoType: String , context: Context) {
        this.counter = counter
        this.name = name
        this.info = info
        this.infoType = infoType
        this.context = context
    }

    constructor(name: String, context: Context, position: Int, info: String) {
        this.name = name
        this.context = context
        this.position = position
        this.info = info
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun startThreadAdd(): CompletableFuture<Void> {
        threadPoolExecutor = Executors.newFixedThreadPool(1) as ThreadPoolExecutor
        future = CompletableFuture.runAsync({ insertDB() }, threadPoolExecutor)
        return future
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun startThreadEdit(): CompletableFuture<Void> {
        threadPoolExecutor = Executors.newFixedThreadPool(1) as ThreadPoolExecutor
        future = CompletableFuture.runAsync({ updateDB() }, threadPoolExecutor)
        return future
    }

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
