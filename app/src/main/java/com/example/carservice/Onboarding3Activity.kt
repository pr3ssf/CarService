package com.example.carservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Onboarding3Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_3)

        val tvSkip = findViewById<TextView>(R.id.tvSkip)
        val btnNext = findViewById<Button>(R.id.btnNext)

        tvSkip.setOnClickListener {
            startActivity(Intent(this, Auth1StartActivity::class.java))
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, Auth1StartActivity::class.java))
        }
    }
}
