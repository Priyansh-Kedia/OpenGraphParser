package com.kedia.ogparser

data class OpenGraphResult(
    var title: String = "",
    var description: String = "",
    var url: String = "",
    var image: String = "",
    var siteName: String = "",
    var type: String = ""
)