package com.kedia.ogparser

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefs(context: Context) {

    private val pm: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val OG_PARSER = "Og_Parser"
    private val TITLE = OG_PARSER + "_title"
    private val DESCRIPTION = OG_PARSER + "_description"
    private val URL = OG_PARSER + "_url"
    private val IMAGE = OG_PARSER + "_image"
    private val SITE_NAME = OG_PARSER + "_site_name"
    private val TYPE = OG_PARSER + "_type"

    fun setTitle(link: String, title: String) {
        pm.edit().putString(TITLE + "_$link", title).apply()
    }

    fun getTitle(link: String): String {
        return pm.getString(TITLE + "_$link", "") ?: ""
    }

    fun setDescription(link: String, description: String) {
        pm.edit().putString(DESCRIPTION + "_$link", description).apply()
    }

    fun getDescription(link: String): String {
        return pm.getString(DESCRIPTION + "_$link", "") ?: ""
    }

    fun setUrl(link: String, url: String) {
        pm.edit().putString(URL + "_$link", url).apply()
    }

    fun getUrl(link: String): String {
        return pm.getString(URL + "_$link", "") ?: ""
    }

    fun setImage(link: String, image: String) {
        pm.edit().putString(IMAGE + "_$link", image).apply()
    }

    fun getImage(link: String): String {
        return pm.getString(IMAGE + "_$link", "") ?: ""
    }

    fun setSiteName(link: String, siteName: String) {
        pm.edit().putString(SITE_NAME + "_$link", siteName).apply()
    }

    fun getSiteName(link: String): String {
        return pm.getString(SITE_NAME + "_$link", "") ?: ""
    }

    fun setType(link: String, type: String) {
        pm.edit().putString(TYPE + "_$link", type).apply()
    }

    fun getType(link: String): String {
        return pm.getString(TYPE + "_$link", "") ?: ""
    }

    fun setOpenGraphResult(openGraphResult: OpenGraphResult, url: String) {
        setTitle(url, openGraphResult.title.toString())
        setDescription(url, openGraphResult.description.toString())
        setImage(url, openGraphResult.image.toString())
        setSiteName(url, openGraphResult.siteName.toString())
        setType(url, openGraphResult.type.toString())
        setUrl(url, openGraphResult.url.toString())
    }

    fun getOpenGraphResult(url: String): OpenGraphResult {
        val title = getTitle(url)
        val description = getDescription(url)
        val image = getImage(url)
        val siteName = getSiteName(url)
        val type = getType(url)
        val url = getUrl(url)
        return OpenGraphResult(title, description, url, image, siteName, type)
    }

    fun urlExists(url: String): Boolean {
        val title = getTitle(url)
        val description = getDescription(url)
        val image = getImage(url)

        return title.isNotEmpty() && description.isNotEmpty() && image.isNotEmpty()
    }

}