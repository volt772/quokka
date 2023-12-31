package com.kakao.quokka.ext

import android.net.Uri

/**
 * String External Functions
 */

/**
 * Retrieve file key from url string
 * @desc get last url param
 */
fun String?.retrieveFileKey(): String {
	return this?.let { _url ->
		val uri = Uri.parse(_url)
		val path = uri.path
		path?.substringAfterLast("/")
	} ?: ""
}

/**
 * Split file key
 */
fun String.splitKey(): Pair<String, Long> {
	return try {
		val keyArr = this.split("||")
		keyArr[0] to keyArr[1].toLong()
	} catch (e: IndexOutOfBoundsException) {
		"" to 0
	}
}