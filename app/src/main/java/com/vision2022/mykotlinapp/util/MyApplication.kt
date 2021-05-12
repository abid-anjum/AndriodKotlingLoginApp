package com.vision2022.mykotlinapp.util
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import java.util.*

class MyApplication : Application() {
    private var listener: LogoutListener? = null
    private var timer: Timer? = null
    private val INACTIVE_TIMEOUT: Long = 10000 // 3 min

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        lateinit var instance: MyApplication
            private set
    }
    fun startUserSession() {
        cancelTimer()
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                listener!!.onSessionLogout()
            }
        }, INACTIVE_TIMEOUT)
    }
    private fun cancelTimer() {
        if (timer != null) timer!!.cancel()
    }
    fun registerSessionListener(listener: LogoutListener?) {
        this.listener = listener
    }
    fun onUserInteracted() {
        startUserSession()
    }
}

