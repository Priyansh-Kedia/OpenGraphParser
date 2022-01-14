package com.kedia.ogparser

fun checkNullParserResult(openGraphResult: OpenGraphResult?): Boolean {
    return (openGraphResult!!.title.isNullOrEmpty() ||
            openGraphResult!!.title.equals("null")) &&
            (openGraphResult!!.description.isNullOrEmpty() ||
                    openGraphResult!!.description.equals(
                        "null"
                    ))
}