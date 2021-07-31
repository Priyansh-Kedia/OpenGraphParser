package com.kedia.opengraphpreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult

class MainActivity : AppCompatActivity(), OpenGraphCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openGraphParser = OpenGraphParser(this)
        openGraphParser.parse("https://www.youtube.com/watch?v=RRlgbXUMKt8")
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        Log.d("TAG!!!!", "onPostResponse: called $openGraphResult")
    }

    override fun onError(error: String) {
        Log.e("TAG!!!!", "$error")
    }
}