package com.kakao.quokka.ext

import android.net.Uri


fun String?.retrieveFileKey(): String {
	return this?.let { _url ->
		val uri = Uri.parse(_url)
		val path = uri.path
		path?.substringAfterLast("/")
	} ?: ""
}

fun String.splitUrlKey(): Pair<String, Long> {
	return try {
		val keyArr = this.split("||")
		keyArr[0] to keyArr[1].toLong()
	} catch (e: IndexOutOfBoundsException) {
		"" to 0
	}
}