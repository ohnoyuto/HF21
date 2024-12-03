package com.pi12a082.hf21

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // UI要素取得
        val passwordInput = findViewById<EditText>(R.id.register_password)
        val confirmPasswordInput = findViewById<EditText>(R.id.register_confirm_password)
        val passwordToggle = findViewById<Button>(R.id.password_toggle)
        val confirmPasswordToggle = findViewById<Button>(R.id.confirm_password_toggle)
        val birthdateInput = findViewById<EditText>(R.id.register_birthdate)

        // パスワード表示/非表示トグル
        passwordToggle.setOnClickListener {
            val isPasswordVisible = passwordInput.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            if (isPasswordVisible) {
                passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.text = "表示"
            } else {
                passwordInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.text = "非表示"
            }
            passwordInput.setSelection(passwordInput.text.length) // カーソル位置を維持
        }

        confirmPasswordToggle.setOnClickListener {
            val isPasswordVisible = confirmPasswordInput.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            if (isPasswordVisible) {
                confirmPasswordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPasswordToggle.text = "表示"
            } else {
                confirmPasswordInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirmPasswordToggle.text = "非表示"
            }
            confirmPasswordInput.setSelection(confirmPasswordInput.text.length)
        }

        // 生年月日自動フォーマット
        birthdateInput.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                val input = s.toString().replace("-", "")
                val formatted = when (input.length) {
                    in 1..4 -> input
                    in 5..6 -> "${input.substring(0, 4)}-${input.substring(4)}"
                    in 7..8 -> "${input.substring(0, 4)}-${input.substring(4, 6)}-${input.substring(6)}"
                    else -> input
                }
                birthdateInput.setText(formatted)
                birthdateInput.setSelection(formatted.length)
                isEditing = false
            }
        })
    }
}
