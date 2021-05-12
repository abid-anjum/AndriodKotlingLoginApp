package com.vision2022.mykotlinapp.baseui

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vision2022.mykotlinapp.util.LogoutListener
import com.vision2022.mykotlinapp.util.MyApplication
import com.vision2022.mykotlinapp.util.SharedPreference
import java.util.*

open class BaseActivity: AppCompatActivity(), LogoutListener {
    private var isUserTimedOut = false
    private val mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).registerSessionListener(this)
        (application as MyApplication).startUserSession()

        //set locale first time and get from shared preference
        val sharedPreference: SharedPreference = SharedPreference(this@BaseActivity)
        var lang = sharedPreference.getValueString("lang")
        if (lang != null)
        {
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,
                    baseContext.resources.displayMetrics)
        }

    }
    override fun onRestart() {
        super.onRestart()
    }
    override fun onUserInteraction() {
        super.onUserInteraction()
    }
    override fun onResume() {
        super.onResume()
        if (isUserTimedOut) {
            //show TimerOut dialog
        } else {
            (application as MyApplication).onUserInteracted()
        }
    }
    override fun onSessionLogout() {
        isUserTimedOut = true
    }
}
