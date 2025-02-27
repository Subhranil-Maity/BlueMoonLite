package com.subhranil.bluemoon.lite.utils

object Validator {
    fun isUrlValid(url: String): Boolean {
        val regex = Regex("^(http|https)://[^\\s/$.?#].\\S*$")
        return regex.matches(url)
    }
}