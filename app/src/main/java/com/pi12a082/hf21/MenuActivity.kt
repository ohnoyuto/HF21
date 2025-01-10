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

        // マイページボタンの処理
        findViewById<Button>(R.id.my_page_button).setOnClickListener {
            // マイページActivityへ遷移
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        // 折り方ボタンの処理
        findViewById<Button>(R.id.how_to_fold_button).setOnClickListener {
            // 折り方検索Activityを追加する場合、ここに処理を記述
            // 例: val intent = Intent(this, HowToFoldActivity::class.java)
            // startActivity(intent)
        }

        // 履歴ボタンの処理
        findViewById<Button>(R.id.history_button).setOnClickListener {
            // UsageHistoryActivity へ遷移
            val intent = Intent(this, UsageHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}
