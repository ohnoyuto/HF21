package com.pi12a082.hf21

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // ボタン取得
        val registerBodyButton = findViewById<Button>(R.id.register_body_button)
        val ownedBodyButton = findViewById<Button>(R.id.owned_body_button)
        val changeRegistrationButton = findViewById<Button>(R.id.change_registration_button)
        val usageHistoryButton = findViewById<Button>(R.id.usage_history_button)
        val contactUsButton = findViewById<Button>(R.id.contact_us_button)
        val backButton = findViewById<Button>(R.id.back_button)

        // 戻るボタンの設定
        backButton.setOnClickListener {
            finish() // 現在の画面を閉じて前の画面に戻る
        }

        // ボタンのクリックイベント設定
        registerBodyButton.setOnClickListener {
            Toast.makeText(this, "ボディの登録が選択されました", Toast.LENGTH_SHORT).show()
        }

        ownedBodyButton.setOnClickListener {
            Toast.makeText(this, "所有しているボディが選択されました", Toast.LENGTH_SHORT).show()
        }

        changeRegistrationButton.setOnClickListener {
            Toast.makeText(this, "登録情報の変更が選択されました", Toast.LENGTH_SHORT).show()
        }

        usageHistoryButton.setOnClickListener {
            // 利用履歴画面を開く
            val intent = Intent(this, UsageHistoryActivity::class.java)
            startActivity(intent)
        }

        contactUsButton.setOnClickListener {
            // お問い合わせ画面を開く
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }
}
