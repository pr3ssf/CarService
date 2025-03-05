package com.example.carservice

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Auth4Signup2Activity : BaseActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var etSurname: EditText
    private lateinit var etName: EditText
    private lateinit var etPatronymic: EditText
    private lateinit var etBirthday: EditText
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var btnNext: Button

    private val cyrillicRegex = Regex("^[А-ЯЁа-яё]+$")
    private var isChangingBirthday = false
    private var oldBirthdayText = ""
    private var isSurnameValid = false
    private var isNameValid = false
    private var isPatronymicValid = false
    private var isBirthdayValid = false

    private lateinit var birthdayWatcher: TextWatcher

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth4_signup2)

        ivBack = findViewById(R.id.ivBack)
        etSurname = findViewById(R.id.etSurname)
        etName = findViewById(R.id.etName)
        etPatronymic = findViewById(R.id.etPatronymic)
        etBirthday = findViewById(R.id.etBirthday)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        btnNext = findViewById(R.id.btnNext)

        btnNext.isEnabled = false
        btnNext.setBackgroundResource(R.drawable.button_gray_rounded)

        ivBack.setOnClickListener {
            startActivity(Intent(this, Auth3Signup1Activity::class.java))
        }

        etSurname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                isSurnameValid = text.isNotEmpty() && cyrillicRegex.matches(text)
                etSurname.setBackgroundResource(
                    if (isSurnameValid) R.drawable.edittext_rounded_lightgray_border
                    else R.drawable.edittext_rounded_red_border
                )
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                isNameValid = text.isNotEmpty() && cyrillicRegex.matches(text)
                etName.setBackgroundResource(
                    if (isNameValid) R.drawable.edittext_rounded_lightgray_border
                    else R.drawable.edittext_rounded_red_border
                )
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etPatronymic.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString().trim()
                isPatronymicValid = text.isNotEmpty() && cyrillicRegex.matches(text)
                etPatronymic.setBackgroundResource(
                    if (isPatronymicValid) R.drawable.edittext_rounded_lightgray_border
                    else R.drawable.edittext_rounded_red_border
                )
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etBirthday.inputType = EditorInfo.TYPE_CLASS_PHONE
        birthdayWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                oldBirthdayText = s.toString()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isChangingBirthday) {
                    isChangingBirthday = true
                    formatBirthday(s)
                    isChangingBirthday = false
                    validateBirthday()
                    updateNextButton()
                }
            }
        }
        etBirthday.addTextChangedListener(birthdayWatcher)

        etBirthday.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableStart = 0
                val bounds = etBirthday.compoundDrawables[drawableStart]
                if (bounds != null && event.rawX <= bounds.bounds.width()) {
                    showDatePicker()
                    return@setOnTouchListener true
                }
            }
            false
        }

        rbMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) rbFemale.isChecked = false
            updateNextButton()
        }
        rbFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) rbMale.isChecked = false
            updateNextButton()
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, Auth5Signup3Activity::class.java))
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, { _, y, m, d ->
            val chosen = String.format("%02d/%02d/%04d", d, m + 1, y)
            etBirthday.removeTextChangedListener(birthdayWatcher)
            etBirthday.setText(chosen)
            etBirthday.addTextChangedListener(birthdayWatcher)
            validateBirthday()
            updateNextButton()
        }, year, month, day).show()
    }

    private fun formatBirthday(s: Editable?) {
        if (s == null) return
        var text = s.toString().filter { it.isDigit() || it == '/' }
        if (text.length > 10) text = text.substring(0, 10)
        val builder = StringBuilder()
        for (i in text.indices) {
            builder.append(text[i])
            if ((i == 1 || i == 3) && i == text.lastIndex && text[i] != '/') {
                builder.append('/')
            }
        }
        etBirthday.removeTextChangedListener(birthdayWatcher)
        etBirthday.setText(builder.toString())
        etBirthday.setSelection(etBirthday.text.length)
        etBirthday.addTextChangedListener(birthdayWatcher)
    }

    private fun validateBirthday() {
        val text = etBirthday.text.toString()
        val regexDate = Regex("^\\d{2}/\\d{2}/\\d{4}\$")
        if (regexDate.matches(text)) {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.isLenient = false
            try {
                format.parse(text)
                etBirthday.setBackgroundResource(R.drawable.edittext_rounded_lightgray_border)
                isBirthdayValid = true
            } catch (e: Exception) {
                etBirthday.setBackgroundResource(R.drawable.edittext_rounded_red_border)
                isBirthdayValid = false
            }
        } else {
            etBirthday.setBackgroundResource(R.drawable.edittext_rounded_red_border)
            isBirthdayValid = false
        }
    }

    private fun updateNextButton() {
        val maleChecked = rbMale.isChecked
        val femaleChecked = rbFemale.isChecked
        val allValid = isSurnameValid && isNameValid && isPatronymicValid && isBirthdayValid && (maleChecked || femaleChecked)
        btnNext.isEnabled = allValid
        btnNext.setBackgroundResource(if (allValid) R.drawable.button_purple_rounded else R.drawable.button_gray_rounded)
    }
}
