package com.pi12a082.hf21

// 必要なモジュールをインポート
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// マップを表示するためのフラグメント
class MapFragment : Fragment(), OnMapReadyCallback {

    // Google Maps インスタンス
    private lateinit var googleMap: GoogleMap

    // フラグメントのビューを生成する
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_map.xml をインフレートしてビューを作成
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // SupportMapFragment を取得
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this) // マップが準備できたときにコールバックを受け取る

        return view
    }

    // Google Maps の準備が完了したときに呼ばれるコールバック
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 東京の座標
        val tokyo = LatLng(35.681236, 139.767125)

        // 東京にマーカーを追加
        googleMap.addMarker(
            MarkerOptions()
                .position(tokyo) // マーカーの位置を指定
                .title("東京駅") // マーカーのタイトル
        )

        // カメラを東京に移動
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 10f)) // ズームレベルは10
    }
}
