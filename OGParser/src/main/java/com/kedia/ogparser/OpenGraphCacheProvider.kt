package com.kedia.ogparser

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager

class OpenGraphCacheProvider(context: Context) : CacheProvider {

    private val pm: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val OG_PARSER = "Og_Parser"
    private val TITLE = OG_PARSER + "_title"
    private val DESCRIPTION = OG_PARSER + "_description"
    private val URL = OG_PARSER + "_url"
    private val IMAGE = OG_PARSER + "_image"
    private val SITE_NAME = OG_PARSER + "_site_name"
    private val TYPE = OG_PARSER + "_type"

    private fun setTitle(link: String, title: String) {
        pm.edit().putString(TITLE + "_$link", title).apply()
    }

    private fun getTitle(link: String): String {
        return pm.getString(TITLE + "_$link", "") ?: ""
    }

    private fun setDescription(link: String, description: String) {
        pm.edit().putString(DESCRIPTION + "_$link", description).apply()
    }

    private fun getDescription(link: String): String {
        return pm.getString(DESCRIPTION + "_$link", "") ?: ""
    }

    private fun setUrl(link: String, url: String) {
        pm.edit().putString(URL + "_$link", url).apply()
    }

    private fun getUrl(link: String): String {
        return pm.getString(URL + "_$link", "") ?: ""
    }

    private fun setImage(link: String, image: String) {
        pm.edit().putString(IMAGE + "_$link", image).apply()
    }

    private fun getImage(link: String): String {
        return pm.getString(IMAGE + "_$link", "") ?: ""
    }

    private fun setSiteName(link: String, siteName: String) {
        pm.edit().putString(SITE_NAME + "_$link", siteName).apply()
    }

    private fun getSiteName(link: String): String {
        return pm.getString(SITE_NAME + "_$link", "") ?: ""
    }

    private fun setType(link: String, type: String) {
        pm.edit().putString(TYPE + "_$link", type).apply()
    }

    private fun getType(link: String): String {
        return pm.getString(TYPE + "_$link", "") ?: ""
    }

    private fun urlExists(title: String, description: String, image: String): Boolean {
        return title.isNotEmpty() &&
                title.equals("null").not() &&
                description.isNotEmpty() &&
                description.equals("null").not() &&
                image.isNotEmpty() &&
                image.equals("null").not()
    }

    override suspend fun setOpenGraphResult(openGraphResult: OpenGraphResult, url: String) {
        setTitle(url, openGraphResult.title.toString())
        setDescription(url, openGraphResult.description.toString())
        setImage(url, openGraphResult.image.toString())
        setSiteName(url, openGraphResult.siteName.toString())
        setType(url, openGraphResult.type.toString())
        setUrl(url, openGraphResult.url.toString())
    }

    override suspend fun getOpenGraphResult(url: String): OpenGraphResult? {
        val title = getTitle(url)
        val description = getDescription(url)
        val image = getImage(url)

        if (!urlExists(title, description, image)) {
            return null
        }

        val siteName = getSiteName(url)
        val type = getType(url)
        val url = getUrl(url)
        return OpenGraphResult(title, description, url, image, siteName, type)
    }
}
