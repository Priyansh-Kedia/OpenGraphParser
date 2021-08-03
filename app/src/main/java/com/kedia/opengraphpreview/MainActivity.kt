package com.kedia.opengraphpreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OpenGraphCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openGraphParser = OpenGraphParser(this)
        openGraphParser.parse("https://www.youtube.com")
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        tview.text = openGraphResult.toString()
    }

    override fun onError(error: String) {
        Log.e("TAG!!!!", "$error")
        tview.text = error
    }
}