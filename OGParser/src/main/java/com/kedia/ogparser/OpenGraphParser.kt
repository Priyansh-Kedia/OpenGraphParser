package com.kedia.ogparser

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class OpenGraphParser(
    private val listener: OpenGraphCallback,
    private var showNullOnEmpty: Boolean = false,
    context: Context? = null
) {

    private val sharedPrefs: SharedPrefs? = context?.let { SharedPrefs(it) }

    private var url: String = ""

    private val AGENTS = mutableListOf(
        "facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)",
        "Mozilla",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36",
        "WhatsApp/2.19.81 A",
        "facebookexternalhit/1.1",
        "facebookcatalog/1.0"
    )
    private val jsoupNetworkCall = JsoupNetworkCall()

    private var openGraphResult: OpenGraphResult? = null

    fun parse(url: String) {
        this.url = url
        parseLink().parse()
    }

    inner class parseLink : CoroutineScope {

        private val job: Job = Job()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job


        fun parse() = launch {
            val result = fetchContent()
            result?.let {
                listener.onPostResponse(it)
            }
        }
    }

    private suspend fun fetchContent() = withContext(Dispatchers.IO) {
        if (!url.contains("http")) {
            url = "http://$url"
        }
        if (sharedPrefs?.urlExists(url) == true) {
            return@withContext sharedPrefs?.getOpenGraphResult(url)
        }

        AGENTS.forEach {
            openGraphResult = jsoupNetworkCall.callUrl(url, it)
            val isResultNull = checkNullParserResult(openGraphResult)
            if (!isResultNull) {
                openGraphResult?.let { sharedPrefs?.setOpenGraphResult(it, url) }
                return@withContext openGraphResult
            }
        }

        if (checkNullParserResult(openGraphResult) && showNullOnEmpty) {
            launch(Dispatchers.Main) {
                listener.onError("Null or empty response from the server")
            }
            return@withContext null
        }
        openGraphResult?.let { sharedPrefs?.setOpenGraphResult(it, url) }
        return@withContext openGraphResult
    }
}