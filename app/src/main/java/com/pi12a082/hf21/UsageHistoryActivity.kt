package com.pi12a082.hf21

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class UsageHistoryActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_history)

        // UI要素を取得
        val historyListView = findViewById<ListView>(R.id.history_list_view)
        val backButton = findViewById<Button>(R.id.back_button)

        // SharedPreferencesを初期化
        sharedPreferences = getSharedPreferences("HistoryInfo", MODE_PRIVATE)

        // 履歴リストを取得してアダプターを設定
        val historyList = getHistoryList()
        historyAdapter = HistoryAdapter(this, historyList)
        historyListView.adapter = historyAdapter

        // 戻るボタンの設定
        backButton.setOnClickListener {
            finish() // 現在のアクティビティを終了
        }
    }

    /**
     * 履歴データを取得
     */
    private fun getHistoryList(): MutableList<HistoryItem> {
        val historyList = mutableListOf<HistoryItem>()
        val historySet = sharedPreferences.getStringSet("history_list", mutableSetOf()) ?: mutableSetOf()

        for (entry in historySet) {
            val parts = entry.split(" - ")
            if (parts.size == 2) {
                val actionAndLocation = parts[0].split(": ")
                if (actionAndLocation.size == 2) {
                    val action = actionAndLocation[0]
                    val location = actionAndLocation[1]
                    val timestamp = parts[1].toLongOrNull() ?: continue
                    historyList.add(HistoryItem(action, location, timestamp))
                }
            }
        }
        return historyList
    }

    /**
     * 履歴アイテムを削除
     */
    fun deleteHistoryItem(historyItem: HistoryItem) {
        // SharedPreferencesから履歴データを取得
        val historySet = sharedPreferences.getStringSet("history_list", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // 削除対象のアイテムを特定
        val targetString = "${historyItem.action}: ${historyItem.location} - ${historyItem.timestamp}"
        if (historySet.contains(targetString)) {
            historySet.remove(targetString) // 履歴を削除
        }

        // 更新後のデータを保存
        sharedPreferences.edit().putStringSet("history_list", historySet).apply()

        // アダプターから削除してUIを更新
        historyAdapter.remove(historyItem)
    }
}
