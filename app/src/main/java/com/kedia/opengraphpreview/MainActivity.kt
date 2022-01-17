package com.kedia.opengraphpreview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kedia.ogparser.OpenGraphCallback
import com.kedia.ogparser.OpenGraphParser
import com.kedia.ogparser.OpenGraphResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OpenGraphCallback {

    private val openGraphParser by lazy { OpenGraphParser(this, showNullOnEmpty = true) }

    private val LINKS_TO_TEST = mutableListOf(
        "https://www.linkedin.com/posts/madhusmita-padhy_machinelearning-datascience-activity-6886390508722163712-yhQ0",
        "https://www.youtube.com/watch?v=n3zsoX7bRlc",
        "https://twitter.com/levelsio/status/1481942293108359168",
        "https://stackoverflow.com/questions/44515769/conda-is-not-recognized-as-internal-or-external-command",
        "https://github.com/Priyansh-Kedia/OpenGraphParser",
        "https://chat.whatsapp.com/DdWAKRkt2VfAmd4OS47y7P",
        "https://www.reddit.com/r/MachineLearning/comments/s3mjqf/deep_learning_interviews_hundreds_of_fully_solved/?utm_medium=android_app&utm_source=share",
        "https://instagram.com/fcbarcelona?utm_medium=copy_link",
        "https://www.facebook.com/groups/777946865955982/permalink/1385110621906267/",
        "https://mobile.twitter.com/Twitter?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openGraphParser.parse(LINKS_TO_TEST.first())
        LINKS_TO_TEST.removeFirstOrNull()

//        for (link in LINKS_TO_TEST) {
//            Handler().postDelayed({
//                openGraphParser.parse(link)
//            }, 2000)
//            Log.e("TAG!!!!", "called parse")
//        }

        button.setOnClickListener {
            openGraphParser.parse(tview.text.toString())
        }
    }

    override fun onPostResponse(openGraphResult: OpenGraphResult) {
        Log.e("TAG!!!!", "response $openGraphResult")
        tview.setText(openGraphResult.toString())
        if (LINKS_TO_TEST.isNotEmpty()) {
            openGraphParser.parse(LINKS_TO_TEST.first())
            LINKS_TO_TEST.removeFirstOrNull()
        }
    }

    override fun onError(error: String) {
        Log.e("TAG!!!!", "$error")
//        tview.text = error
        if (LINKS_TO_TEST.isNotEmpty()) {
            openGraphParser.parse(LINKS_TO_TEST.first())
            LINKS_TO_TEST.removeFirstOrNull()
        }
    }
}