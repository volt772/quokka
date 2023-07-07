package com.kakao.quokka.ext

import java.text.SimpleDateFormat
import java.util.Date

const val DOC_DATE_FORMAT = "yy.MM.dd"
const val DOC_TIME_FORMAT = "HH:mm"
const val DOC_FULL_FORMAT = "yy.MM.dd HH:mm"
const val DOC_RESP_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

val currMillis: Long
    get() {
        return System.currentTimeMillis()
    }

fun String?.convertFormat(): Pair<String, String> {
    return this?.let { formed ->
        val sdf = SimpleDateFormat(DOC_RESP_DATE_FORMAT)
        val date: Date = sdf.parse(formed) as Date
        val millis = date.time

        /* Date*/
        val dateFormatter = SimpleDateFormat(DOC_DATE_FORMAT)
        dateFormatter.format(Date(millis))

        /* Time*/
        val timeFormatter = SimpleDateFormat(DOC_TIME_FORMAT)
        timeFormatter.format(Date(millis))

        dateFormatter.format(Date(millis)) to timeFormatter.format(Date(millis))
    } ?: ("" to "")
}

fun Long?.millisToFormedDate(format : String = DOC_DATE_FORMAT): String {
    val formed = this?.let { millis ->
        val formatter = SimpleDateFormat(format)
        formatter.format(Date(millis))
    } ?: ""

    return formed
}