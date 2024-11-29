package com.pi12a082.hf21

// 必要なモジュールをインポート
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// メニュー画面を表示するアクティビティ
class MenuActivity : AppCompatActivity() {

    // アクティビティ作成時に呼び出される
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu) // レイアウトファイルを設定

        // スタンド検索ボタンの取得
        val standSearchButton = findViewById<Button>(R.id.stand_search_button)

        // スタンド検索ボタンを押したときの処理
        standSearchButton.setOnClickListener {
            // マップ画面 (MainActivity) へ遷移
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // QR読み取りボタンの処理
        findViewById<Button>(R.id.qr_button).setOnClickListener {
            // 今後追加予定のQR読み取り画面の処理を記述
            // val intent = Intent(this, QRActivity::class.java)
            // startActivity(intent)
        }

        // 他のボタンの処理を追加可能
        // 例: 折り方検索、購入履歴など
        findViewById<Button>(R.id.how_to_fold_button).setOnClickListener {
            // 折り方検索Activityを追加する場合、ここに処理を記述
        }
        findViewById<Button>(R.id.history_button).setOnClickListener {
            // 購入履歴Activityを追加する場合、ここに処理を記述
        }
    }
}
