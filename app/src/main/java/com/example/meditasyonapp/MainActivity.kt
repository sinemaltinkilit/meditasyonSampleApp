package com.example.meditasyonapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionbar()
        checkConnection()
    }

    private fun checkConnection(): Boolean {
        val cm: ConnectivityManager = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (cm.isDefaultNetworkActive) {
            val networkInfo = cm.isDefaultNetworkActive
            networkInfo
        } else {
            false
        }
    }

    private fun setActionbar() {
        supportActionBar?.hide()
    }
}