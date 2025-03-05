package com.example.carservice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Auth3Signup1Activity : BaseActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var checkBoxAgreement: CheckBox
    private lateinit var btnNext: Button

    private var isPassword1Visible = false
    private var isPassword2Visible = false

    private val passwordRegex = Regex("^[A-Za-z0-9!@_-]*$")

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth3_signup1)

        val ivBack = findViewById<ImageView>(R.id.iv_welcome_image)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etRepeatPassword = findViewById(R.id.etRepeatPassword)
        checkBoxAgreement = findViewById(R.id.tvAgreement)
        btnNext = findViewById(R.id.btnNext)

        btnNext.isEnabled = false
        btnNext.setBackgroundResource(R.drawable.button_gray_rounded)

        ivBack.setOnClickListener {
            startActivity(Intent(this, Auth1StartActivity::class.java))
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
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pass1 = s.toString()
                if (passwordRegex.matches(pass1) && pass1.length >= 8) {
                    etPassword.setBackgroundResource(R.drawable.edittext_rounded_lightgray_border)
                } else {
                    etPassword.setBackgroundResource(R.drawable.edittext_rounded_red_border)
                }
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (etPassword.right - etPassword.compoundDrawables[drawableEnd].bounds.width())) {
                    isPassword1Visible = !isPassword1Visible
                    if (isPassword1Visible) {
                        etPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        etPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                    }
                    etPassword.setSelection(etPassword.text.length)
                    true
                } else false
            } else false
        }

        etRepeatPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val pass1 = etPassword.text.toString()
                val pass2 = s.toString()
                if (pass2 == pass1) {
                    etRepeatPassword.setBackgroundResource(R.drawable.edittext_rounded_lightgray_border)
                } else {
                    etRepeatPassword.setBackgroundResource(R.drawable.edittext_rounded_red_border)
                }
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etRepeatPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (etRepeatPassword.right - etRepeatPassword.compoundDrawables[drawableEnd].bounds.width())) {
                    isPassword2Visible = !isPassword2Visible
                    if (isPassword2Visible) {
                        etRepeatPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        etRepeatPassword.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                    }
                    etRepeatPassword.setSelection(etRepeatPassword.text.length)
                    true
                } else false
            } else false
        }

        checkBoxAgreement.setOnCheckedChangeListener { _, _ ->
            updateNextButton()
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, Auth4Signup2Activity::class.java))
        }
    }

    private fun updateNextButton() {
        val email = etEmail.text.toString().trim()
        val pass1 = etPassword.text.toString()
        val pass2 = etRepeatPassword.text.toString()
        val agreed = checkBoxAgreement.isChecked
        val validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validPass1 = passwordRegex.matches(pass1) && pass1.length >= 8
        val validPass2 = pass1 == pass2 && pass2.length >= 8 && pass1.isNotEmpty()
        if (validEmail && validPass1 && validPass2 && agreed) {
            btnNext.isEnabled = true
            btnNext.setBackgroundResource(R.drawable.button_purple_rounded)
        } else {
            btnNext.isEnabled = false
            btnNext.setBackgroundResource(R.drawable.button_gray_rounded)
        }
    }
}
