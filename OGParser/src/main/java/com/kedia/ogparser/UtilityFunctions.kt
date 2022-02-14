package com.kedia.ogparser

import java.net.URI
import java.net.URL

fun checkNullParserResult(openGraphResult: OpenGraphResult?): Boolean {
    return (openGraphResult?.title.isNullOrEmpty() ||
            openGraphResult?.title.equals("null")) &&
            (openGraphResult?.description.isNullOrEmpty() ||
                    openGraphResult?.description.equals(
                        "null"
                    ))
}

fun getBaseUrl(urlString: String): String {
    val url: URL = URI.create(urlString).toURL()
    return url.protocol.toString() + "://" + url.authority + "/"
}