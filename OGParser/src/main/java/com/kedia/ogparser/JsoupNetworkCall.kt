package com.kedia.ogparser

import org.jsoup.Jsoup

class JsoupNetworkCall {

    private val REFERRER = "http://www.google.com"
    private val TIMEOUT = 100000
    private val DOC_SELECT_QUERY = "meta[property^=og:]"
    private val OPEN_GRAPH_KEY = "content"
    private val PROPERTY = "property"
    private val OG_IMAGE = "og:image"
    private val OG_DESCRIPTION = "og:description"
    private val OG_URL = "og:url"
    private val OG_TITLE = "og:title"
    private val OG_SITE_NAME = "og:site_name"
    private val OG_TYPE = "og:type"

    private var openGraphResult: OpenGraphResult? = null

    fun callUrl(url: String, agent: String): OpenGraphResult? {
        openGraphResult = OpenGraphResult()
        try {
            val response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent(agent)
                .referrer(REFERRER)
                .timeout(TIMEOUT)
                .followRedirects(true)
                .execute()

            val doc = response.parse()
            val ogTags = doc.select(DOC_SELECT_QUERY)
            when {
                ogTags.size > 0 ->
                    ogTags.forEachIndexed { index, _ ->
                        val tag = ogTags[index]
                        val text = tag.attr(PROPERTY)

                        when (text) {
                            OG_IMAGE -> {
                                openGraphResult!!.image = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_DESCRIPTION -> {
                                openGraphResult!!.description = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_URL -> {
                                openGraphResult!!.url = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_TITLE -> {
                                openGraphResult!!.title = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_SITE_NAME -> {
                                openGraphResult!!.siteName = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_TYPE -> {
                                openGraphResult!!.type = (tag.attr(OPEN_GRAPH_KEY))
                            }
                        }
                    }
            }

            if (openGraphResult!!.title.isNullOrEmpty())
                openGraphResult!!.title = doc.title()
            if (openGraphResult!!.description.isNullOrEmpty())
                openGraphResult!!.description = if (doc.select("meta[name=description]").size != 0) doc.select("meta[name=description]")
                    .first().attr("content") else ""
            if (openGraphResult!!.url.isNullOrEmpty())
                openGraphResult!!.url = getBaseUrl(url)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return openGraphResult
    }
}