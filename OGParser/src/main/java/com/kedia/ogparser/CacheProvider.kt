package com.kedia.ogparser

interface CacheProvider {
    suspend fun getOpenGraphResult(url: String): OpenGraphResult?
    suspend fun setOpenGraphResult(openGraphResult: OpenGraphResult, url: String)
}
