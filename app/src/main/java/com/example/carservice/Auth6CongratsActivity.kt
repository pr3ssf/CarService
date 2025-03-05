package com.example.carservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class Auth6CongratsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth6_congrats)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            startActivity(Intent(this, Auth2LoginActivity::class.java))
        }
    }
}
