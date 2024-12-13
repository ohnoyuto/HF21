package com.pi12a082.hf21

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyListView = findViewById<ListView>(R.id.history_list_view)
        val clearHistoryButton = findViewById<Button>(R.id.clear_history_button)
        val backButton = findViewById<Button>(R.id.back_button)

        val sharedPreferences = getSharedPreferences("HistoryInfo", MODE_PRIVATE)
        val historySet = sharedPreferences.getStringSet("history_list", mutableSetOf()) ?: mutableSetOf()

        // ListView に履歴を表示
        val historyList = historySet.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historyList)
        historyListView.adapter = adapter

        // 履歴を削除
        clearHistoryButton.setOnClickListener {
            with(sharedPreferences.edit()) {
                remove("history_list")
                apply()
            }
            adapter.clear()
            adapter.notifyDataSetChanged()
        }

        // 戻るボタン
        backButton.setOnClickListener {
            finish()
        }
    }
}
