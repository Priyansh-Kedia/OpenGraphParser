package com.kedia.ogparser

interface OpenGraphCallback {
    fun onPostResponse(openGraphResult: OpenGraphResult)
    fun onError(error: String)
}