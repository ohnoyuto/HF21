package com.pi12a082.hf21

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(
    private val context: Context,
    private val historyList: MutableList<HistoryItem>
) : ArrayAdapter<HistoryItem>(context, 0, historyList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_history, parent, false)

        val historyItem = getItem(position)
        val actionTextView = view.findViewById<TextView>(R.id.action_text_view)
        val locationTextView = view.findViewById<TextView>(R.id.location_text_view)
        val timestampTextView = view.findViewById<TextView>(R.id.timestamp_text_view)
        val deleteButton = view.findViewById<Button>(R.id.delete_button)

        // データを表示
        actionTextView.text = historyItem?.action
        locationTextView.text = historyItem?.location
        timestampTextView.text = formatTimestamp(historyItem?.timestamp ?: 0)

        // 削除ボタン
        deleteButton.setOnClickListener {
            (context as UsageHistoryActivity).deleteHistoryItem(historyItem!!)
            Toast.makeText(context, "履歴を削除しました", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return format.format(date)
    }
}

