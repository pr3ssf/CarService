package com.example.carservice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Auth2LoginActivity : BaseActivity() {
    private var isPasswordVisible = false

    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth2_login)

        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.isEnabled = false
        btnLogin.setBackgroundResource(R.drawable.button_gray_rounded)

        tvRegister.setOnClickListener {
            startActivity(Intent(this, Auth3Signup1Activity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, _ForgotPasswordActivity::class.java))
        }

        etEmail.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString().trim()
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setBackgroundResource(R.drawable.edittext_rounded_lightgray_border)
                } else {
                    etEmail.setBackgroundResource(R.drawable.edittext_rounded_red_border)
                }
                updateLoginButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateLoginButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (etPassword.right - etPassword.compoundDrawables[drawableEnd].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        etPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        etPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                    }
                    etPassword.setSelection(etPassword.text.length)
                    true
                } else false
            } else false
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("open_settings", true)
            startActivity(intent)
            finish()
        }

    }

    private fun updateLoginButton() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        val validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validPassword = password.isNotEmpty()

        if (validEmail && validPassword) {
            btnLogin.isEnabled = true
            btnLogin.setBackgroundResource(R.drawable.button_purple_rounded)
        } else {
            btnLogin.isEnabled = false
            btnLogin.setBackgroundResource(R.drawable.button_gray_rounded)
        }
    }
}
