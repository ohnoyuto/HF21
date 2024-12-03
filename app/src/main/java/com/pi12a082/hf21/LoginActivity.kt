package com.pi12a082.hf21

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // UI要素を取得
        val loginId = findViewById<EditText>(R.id.login_id)
        val loginPassword = findViewById<EditText>(R.id.login_password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerLink = findViewById<TextView>(R.id.register_link)
        val forgotPasswordLink = findViewById<TextView>(R.id.forgot_password_link)

        // ログインボタンのクリック処理
        loginButton.setOnClickListener {
            val id = loginId.text.toString()
            val password = loginPassword.text.toString()

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "IDまたはパスワードを入力してください", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ログイン成功！", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuActivity::class.java)) // メニュー画面に遷移
                finish() // 現在のアクティビティを終了
            }
        }

        // 新規登録リンクのクリック処理
        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
