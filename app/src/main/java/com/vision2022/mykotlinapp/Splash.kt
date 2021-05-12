package com.vision2022.mykotlinapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.os.Handler
import android.view.WindowManager
import com.vision2022.mykotlinapp.util.MyApplication
import com.vision2022.mykotlinapp.util.SharedPreference

class Splash : AppCompatActivity() {
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({

            val context: Context = MyApplication.instance
            val sharedPreference: SharedPreference = SharedPreference(context)

            var jwttoken = sharedPreference.getValueString("token");

            if (jwttoken == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if (jwttoken != null)
            {
                //val intent = Intent(this, HomeActivity::class.java)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}