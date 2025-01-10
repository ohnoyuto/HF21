package com.pi12a082.hf21

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        // お問い合わせボタンのクリック処理
        findViewById<Button>(R.id.contact_button).setOnClickListener {
            showContactOptions()
        }

        // 戻るボタンのクリック処理
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish() // 現在の画面を閉じる
        }
    }

    private fun showContactOptions() {
        // 選択肢
        val options = arrayOf(
            "ボディ登録について",
            "所有しているボディについて",
            "登録情報の変更について",
            "利用履歴について"
        )

        // ダイアログの表示
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogTheme)
        builder.setTitle("お問い合わせ内容を選択してください")
            .setItems(options) { _, which ->
                Toast.makeText(this, "選択: ${options[which]}", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

}
