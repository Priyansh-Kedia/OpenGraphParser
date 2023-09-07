package com.kedia.ogparser

import android.util.Log
import org.jsoup.Jsoup

/**
 * @param timeout - Timeout for requests, specified in milliseconds (default - 60000)
 * @param jsoupProxy - Specify proxy for requests
 * @param maxBodySize - The maximum size to fetch for body
 */

class JsoupNetworkCall(
    private val timeout: Int? = DEFAULT_TIMEOUT,
    private val jsoupProxy: JsoupProxy? = null,
    private val maxBodySize: Int? = null
) {

    fun callUrl(url: String, agent: String): OpenGraphResult? {
        val openGraphResult = OpenGraphResult()
        try {
            val connection = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent(agent)
                .referrer(REFERRER)
                .timeout(timeout ?: DEFAULT_TIMEOUT)
                .followRedirects(true)

            jsoupProxy?.let { connection.proxy(it.host, it.port) }
            maxBodySize?.let { connection.maxBodySize(it) }

            val response = connection.execute()

            val doc = response.parse()
            val ogTags = doc.select(DOC_SELECT_OGTAGS)

            ogTags.forEach { tag ->
                val text = tag.attr(PROPERTY)

                when (text) {
                    OG_IMAGE -> {
                        openGraphResult.image = tag.attr(OPEN_GRAPH_KEY)
                    }
                    OG_DESCRIPTION -> {
                        openGraphResult.description = tag.attr(OPEN_GRAPH_KEY)
                    }
                    OG_URL -> {
                        openGraphResult.url = tag.attr(OPEN_GRAPH_KEY)
                    }
                    OG_TITLE -> {
                        openGraphResult.title = tag.attr(OPEN_GRAPH_KEY)
                    }
                    OG_SITE_NAME -> {
                        openGraphResult.siteName = tag.attr(OPEN_GRAPH_KEY)
                    }
                    OG_TYPE -> {
                        openGraphResult.type = tag.attr(OPEN_GRAPH_KEY)
                    }
                }
            }

            if (openGraphResult.title.isNullOrEmpty()) {
                openGraphResult.title = doc.title()
            }
            if (openGraphResult.description.isNullOrEmpty()) {
                val docSelection = doc.select(DOC_SELECT_DESCRIPTION)
                openGraphResult.description = docSelection.firstOrNull()?.attr("content") ?: ""
            }
            if (openGraphResult.url.isNullOrEmpty()) {
                openGraphResult.url = getBaseUrl(url)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return openGraphResult
    }

    companion object {
        private const val REFERRER = "http://www.google.com"
        private const val DEFAULT_TIMEOUT = 60000

        private const val DOC_SELECT_OGTAGS = "meta[property^=og:]"
        private const val DOC_SELECT_DESCRIPTION = "meta[name=description]"

        private const val OPEN_GRAPH_KEY = "content"
        private const val PROPERTY = "property"

        private const val OG_IMAGE = "og:image"
        private const val OG_DESCRIPTION = "og:description"
        private const val OG_URL = "og:url"
        private const val OG_TITLE = "og:title"
        private const val OG_SITE_NAME = "og:site_name"
        private const val OG_TYPE = "og:type"
    }
}
