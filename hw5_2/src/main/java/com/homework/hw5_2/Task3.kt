package com.homework.hw5_2

import android.content.Context
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ThreadPoolExecutor
import java.util.function.Supplier


class Task3 {
    private var counter = 0
    private var name: String
    private var info: String
    private lateinit var infoType:String
    private var context: Context
    private var position = 0
    var supplier: Supplier<*>? = null
    var future: CompletableFuture<Void>? = null
    private val threadPoolExecutor: ThreadPoolExecutor? = null

    constructor(counter: Int, name: String, info: String, infoType: String, context: Context) {
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

    fun startThreadAdd() {
        Observable.just("one")
                .subscribe(observer)
    }

    fun startThreadEdit() {
        Observable.just("two")
                .subscribe(observer)
    }

    private var observer: Observer<Any?> = object : Observer<Any?> {
        override fun onSubscribe(d: @NonNull Disposable?) {}
        override fun onNext(o: Any?) {
            if (o === "one") {
                insertDB()
            } else if (o === "two") {
                updateDB()
            }
        }

        override fun onError(e: @NonNull Throwable?) {}
        override fun onComplete() {}
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
