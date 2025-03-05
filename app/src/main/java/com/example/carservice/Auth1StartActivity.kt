package com.example.carservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Auth1StartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth1_start)

        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)

        btnRetry.setOnClickListener {
            startActivity(Intent(this, Auth2LoginActivity::class.java))
        }

        btnSignUp.setOnClickListener {
            startActivity(Intent(this, Auth3Signup1Activity::class.java))
        }
    }
}
