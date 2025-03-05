package com.example.carservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Context

class NoConnectionActivity : AppCompatActivity() {

    private var lastActivityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        lastActivityName = intent.getStringExtra("LAST_ACTIVITY")

        val btnRetry = findViewById<Button>(R.id.btnRetry)
        btnRetry.setOnClickListener {
            if (isOnline()) {
                if (!lastActivityName.isNullOrEmpty()) {
                    try {
                        val clazz = Class.forName(lastActivityName!!)
                        startActivity(Intent(this, clazz))
                        finish()
                    } catch (e: Exception) {
                        startActivity(Intent(this, SplashActivity::class.java))
                        finish()
                    }
                } else {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
