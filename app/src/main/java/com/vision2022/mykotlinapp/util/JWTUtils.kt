package com.vision2022.mykotlinapp.util

import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException

class JWTUtils {

    private lateinit var split: Array<String>

    @Throws(java.lang.Exception::class)
    fun decoded(JWTEncoded: String): String? {
        try {
            split = JWTEncoded.split("\\.").toTypedArray()
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
            //Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
            //Log.d("JWT_DECODED", "Signiture: " + getJson(split[2]))
        } catch (e: UnsupportedEncodingException) {
            //Error
        }
        return getJson(split[0])
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        //return String(decodedBytes, "UTF-8")
        return String(decodedBytes)
    }
}