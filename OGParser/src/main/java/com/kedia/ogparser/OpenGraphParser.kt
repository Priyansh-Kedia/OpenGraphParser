package com.kedia.ogparser

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OpenGraphParser(
    private val listener: OpenGraphCallback,
    private var showNullOnEmpty: Boolean = false,
    private val cacheProvider: CacheProvider? = null
) {

    private val jsoupNetworkCall = JsoupNetworkCall()


    fun parse(url: String) {
        ParseLink(url).parse()
    }

    inner class ParseLink(private val url: String) : CoroutineScope {
        private val job: Job = Job()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

        fun parse() = launch {
            val result = fetchContent(url)
            result?.let {
                listener.onPostResponse(it)
            }
        }
    }

    private suspend fun fetchContent(url: String) = withContext(Dispatchers.IO) {
        var validatedUrl = url
        if (!validatedUrl.contains("http")) {
            validatedUrl = "http://$validatedUrl"
        }

        cacheProvider?.getOpenGraphResult(url)?.let {
            return@withContext it
        }

        var openGraphResult: OpenGraphResult? = null
        AGENTS.forEach {
            openGraphResult = jsoupNetworkCall.callUrl(validatedUrl, it)
            val isResultNull = checkNullParserResult(openGraphResult)
            if (!isResultNull) {
                openGraphResult?.let { cacheProvider?.setOpenGraphResult(it, url) }
                return@withContext openGraphResult
            }
        }

        if (checkNullParserResult(openGraphResult) && showNullOnEmpty) {
            launch(Dispatchers.Main) {
                listener.onError("Null or empty response from the server")
            }
            return@withContext null
        }

        openGraphResult?.let { cacheProvider?.setOpenGraphResult(it, url) }

        return@withContext openGraphResult
    }

    companion object {
        private val AGENTS = arrayOf(
            "facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)",
            "Mozilla",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36",
            "WhatsApp/2.19.81 A",
            "facebookexternalhit/1.1",
            "facebookcatalog/1.0"
        )
    }
}
