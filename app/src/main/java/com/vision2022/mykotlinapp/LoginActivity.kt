package com.vision2022.mykotlinapp

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vision2022.mykotlinapp.baseui.BaseActivity
import com.vision2022.mykotlinapp.model.LoginResponse
import com.vision2022.mykotlinapp.model.User
import com.vision2022.mykotlinapp.network.ApiClient
import com.vision2022.mykotlinapp.util.JWTUtils
import com.vision2022.mykotlinapp.util.LocaleHelper
import com.vision2022.mykotlinapp.util.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity()  {
    lateinit var username: EditText
    lateinit var password: EditText
    val MIN_PASSWORD_LENGTH = 4
    lateinit var progerssProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        viewInitializations()

        // get reference to ImageView
        val image_view = findViewById(R.id.iv_langualge) as ImageView
        // set on-click listener for ImageView
        image_view.setOnClickListener {
            //Change Application level locale
            var mLanguageCode = "";
            val sharedPreference: SharedPreference = SharedPreference(this@LoginActivity)
            if (sharedPreference.getValueString("lang") == "ar")
            {
                mLanguageCode = "en";
            }
            else if (sharedPreference.getValueString("lang") == "en")
            {
                mLanguageCode = "ar";
            }

            //default langulage is english
            if (mLanguageCode == "")
            {
                mLanguageCode = "en"
            }

            LocaleHelper.setLocale(this@LoginActivity, mLanguageCode);
            //It is required to recreate the activity to reflect the change in UI.
            sharedPreference.save("lang",mLanguageCode)

            recreate();
        }
    }

    fun viewInitializations() {
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
    }
    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (username.text.toString() == "") {
            username.error = "Please enter user name"
            return false
        }
        if (password.text.toString() == "") {
            password.error = "Please enter password"
            return false
        }
        // checking the proper email format
        if (username.text.length < MIN_PASSWORD_LENGTH) {
            username.error = "Enter 4 digit valid user name"
            return false
        }
        // checking minimum password Length
        if (password.text.length < MIN_PASSWORD_LENGTH) {
            password.error = "Password length must be more than " + MIN_PASSWORD_LENGTH + "characters"
            return false
        }
        return true
    }

    fun isEmailValid(username: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    fun signUp(view: View?) {
        startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
    }
    // Hook Click Event
    fun performSignUp(v: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server
            val valusername = username!!.text.toString()
            val valpassword = password!!.text.toString()

            //show progress dialog
            progerssProgressDialog=ProgressDialog(this)
            progerssProgressDialog.setTitle("Processing")
            progerssProgressDialog.setCancelable(false)
            progerssProgressDialog.show()

            sendLoginRequest(valusername, valpassword)
            //Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            // Here you can call you API
        }
    }

    fun sendLoginRequest(username: String, password: String)
    {
        var user: User = User(username, password)
        val intent = Intent(this, HomeActivity::class.java)

        ApiClient.getClient.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    //progerssProgressDialog.dismiss()
                    var result: LoginResponse? = response.body()

                    var dcode: JWTUtils = JWTUtils()
                    dcode.decoded(result!!.token)

                    //store token in shared preference
                    val sharedPreference: SharedPreference = SharedPreference(this@LoginActivity)
                    sharedPreference.save("token",result.token)

                    //This line will throw an exception if it is not a signed JWS (as expected)
                    Log.i("", "post registration to API" + response.body().toString())
                    //call home screen
                    startActivity(intent)
                }
                else
                {
                    progerssProgressDialog.dismiss()
                    MaterialAlertDialogBuilder(this@LoginActivity)
                            .setTitle(resources.getString(R.string.title))
                            .setMessage(resources.getString(R.string.incorrectlogin))
                            .setNeutralButton(resources.getString(R.string.okbutton)) { dialog, which ->
                                // Respond to neutral button press
                            }
                            .show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progerssProgressDialog.dismiss()
                //Log.i("", "post registration to API" + t.toString())
                MaterialAlertDialogBuilder(this@LoginActivity)
                        .setTitle(resources.getString(R.string.title))
                        .setMessage(resources.getString(R.string.incorrectlogin))
                        .setNeutralButton(resources.getString(R.string.okbutton)) { dialog, which ->
                            // Respond to neutral button press
                        }
                        .show()
            }
        })
    }

}