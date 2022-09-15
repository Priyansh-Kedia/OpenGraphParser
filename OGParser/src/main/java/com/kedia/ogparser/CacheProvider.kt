package com.kedia.ogparser

interface CacheProvider {
    fun getOpenGraphResult(url: String): OpenGraphResult?
    fun setOpenGraphResult(openGraphResult: OpenGraphResult, url: String)
}
