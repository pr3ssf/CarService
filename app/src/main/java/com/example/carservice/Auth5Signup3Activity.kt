package com.example.carservice

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Auth5Signup3Activity : BaseActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var ivUserPhoto: ImageView
    private lateinit var tvPhotoDescription: TextView
    private lateinit var etDriverLicenseNumber: EditText
    private lateinit var etIssueDate: EditText
    private lateinit var btnUploadDriverLicense: TextView
    private lateinit var btnUploadPassport: TextView
    private lateinit var btnNext: Button

    private var isProfileLoaded = false
    private var isDriverLicenseLoaded = false
    private var isPassportLoaded = false
    private var isDriverNumberValid = false
    private var isIssueDateValid = false

    private val pickProfileImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            ivUserPhoto.setImageURI(it)
            isProfileLoaded = true
            tvPhotoDescription.text = "Фото профиля загружено"
            updateNextButton()
        }
    }

    private val pickDriverLicenseImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            btnUploadDriverLicense.text = "Загружено"
            isDriverLicenseLoaded = true
            updateNextButton()
        }
    }

    private val pickPassportImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            btnUploadPassport.text = "Загружено"
            isPassportLoaded = true
            updateNextButton()
        }
    }

    private lateinit var issueDateWatcher: TextWatcher
    private var isChangingIssueDate = false
    private var oldIssueDateText = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth5_signup3)

        ivBack = findViewById(R.id.ivBack)
        ivUserPhoto = findViewById(R.id.ivUserPhoto)
        tvPhotoDescription = findViewById(R.id.tvPhotoDescription)
        etDriverLicenseNumber = findViewById(R.id.etDriverLicenseNumber)
        etIssueDate = findViewById(R.id.etIssueDate)
        btnUploadDriverLicense = findViewById(R.id.btnUploadDriverLicense)
        btnUploadPassport = findViewById(R.id.btnUploadPassport)
        btnNext = findViewById(R.id.btnNext)

        btnNext.isEnabled = false

        ivBack.setOnClickListener {
            startActivity(Intent(this, Auth4Signup2Activity::class.java))
        }

        ivUserPhoto.setOnClickListener {
            pickProfileImage.launch("image/*")
        }

        btnUploadDriverLicense.setOnClickListener {
            pickDriverLicenseImage.launch("image/*")
        }

        btnUploadPassport.setOnClickListener {
            pickPassportImage.launch("image/*")
        }

        etDriverLicenseNumber.inputType = EditorInfo.TYPE_CLASS_NUMBER
        etDriverLicenseNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val digits = s.toString().trim()
                isDriverNumberValid = digits.matches(Regex("^\\d{10}\$"))
                etDriverLicenseNumber.setBackgroundResource(
                    if (isDriverNumberValid) R.drawable.edittext_rounded_lightgray_border
                    else R.drawable.edittext_rounded_red_border
                )
                updateNextButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etIssueDate.inputType = EditorInfo.TYPE_CLASS_PHONE
        issueDateWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                oldIssueDateText = s.toString()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isChangingIssueDate) {
                    isChangingIssueDate = true
                    formatIssueDate(s)
                    isChangingIssueDate = false
                    validateIssueDate()
                    updateNextButton()
                }
            }
        }
        etIssueDate.addTextChangedListener(issueDateWatcher)

        etIssueDate.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableStart = 0
                val bounds = etIssueDate.compoundDrawables[drawableStart]
                if (bounds != null && event.rawX <= bounds.bounds.width()) {
                    showDatePicker()
                    return@setOnTouchListener true
                }
            }
            false
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, Auth6CongratsActivity::class.java))
        }
    }

    @SuppressLint("DefaultLocale")
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, { _, year, month, day ->
            val chosen = String.format("%02d/%02d/%04d", day, month + 1, year)
            etIssueDate.removeTextChangedListener(issueDateWatcher)
            etIssueDate.setText(chosen)
            etIssueDate.addTextChangedListener(issueDateWatcher)
            validateIssueDate()
            updateNextButton()
        }, y, m, d).show()
    }

    private fun formatIssueDate(s: Editable?) {
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
        etIssueDate.removeTextChangedListener(issueDateWatcher)
        etIssueDate.setText(builder.toString())
        etIssueDate.setSelection(etIssueDate.text.length)
        etIssueDate.addTextChangedListener(issueDateWatcher)
    }

    private fun validateIssueDate() {
        val text = etIssueDate.text.toString()
        val regexDate = Regex("^\\d{2}/\\d{2}/\\d{4}\$")
        if (regexDate.matches(text)) {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.isLenient = false
            try {
                format.parse(text)
                etIssueDate.setBackgroundResource(R.drawable.edittext_rounded_lightgray_border)
                isIssueDateValid = true
            } catch (e: Exception) {
                etIssueDate.setBackgroundResource(R.drawable.edittext_rounded_red_border)
                isIssueDateValid = false
            }
        } else {
            etIssueDate.setBackgroundResource(R.drawable.edittext_rounded_red_border)
            isIssueDateValid = false
        }
    }

    private fun updateNextButton() {
        val allValid = isProfileLoaded && isDriverLicenseLoaded && isPassportLoaded && isDriverNumberValid && isIssueDateValid
        btnNext.isEnabled = allValid
        btnNext.setBackgroundResource(if (allValid) R.drawable.button_purple_rounded else R.drawable.button_gray_rounded)
    }
}
