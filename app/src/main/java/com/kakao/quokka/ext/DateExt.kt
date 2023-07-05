package com.kakao.quokka.ext

import java.text.SimpleDateFormat
import java.util.Date

//
//
val currMillis: Long
    get() {
        return System.currentTimeMillis()
    }
//
//
val DOC_FORMAT = "yy.MM.dd HH:mm"
val DOC_DATE_FORMAT = "yy.MM.dd"
val DOC_TIME_FORMAT = "HH:mm"
//
///**
// * Joda DateTime 이 올바른 값인지 검사.
// */
//fun DateTime?.isValid() = this?.let { it.millis != Long.MIN_VALUE } ?: false
//
///**
// * 시간 Formatted
// * @param type
// */
//fun DateTime?.convertDateByType(type: Int): String {
//    this ?: return ""
//
//    return when (type) {
//        1 -> this.toString("YY.MM.dd HH:mm")
//        2 -> this.toString("HH:mm")
//        3 -> this.toString("YY-MM-dd HH:mm")
//        4 -> this.toString("YYYY-MM-dd")
//        5 -> this.toString("YY-MM-dd")
//        6 -> this.toString("MM-dd (EEE)")
//        7 -> this.toString("YYYY-MM")
//        8 -> this.toString("YYYY-MM-dd (EEE)")
//        9 -> this.toString("YYYY-MM-dd HH:mm")
//        10 -> this.toString("YYYY-MM-dd")
//        11 -> this.toString("MM-dd")
//        else -> this.toString("YY.MM.dd HH:mm")
//    }
//}
//
//fun String.getUTCDateTime() {
//    val stringDate = "2023-06-28T21:00:48.000+09:00"
////    2022-12-15T09:00:00.624+09:00 =>  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
//    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//    var consultationDate = sdf.parse(this)
//    println("probe :: date : $consultationDate")
//}
//
///**
// * Date Convert
// * @desc String to Millis
// */
//fun String?.formedDateToMillis(format : String = BASE_DATE_FORMATE): Long {
//    val millis = this?.let { formed ->
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//        val date: Date = sdf.parse(formed) as Date
//        date.time
//    } ?: 0L
//
//    return millis
//}
//
///**
// * Date Convert
// * @desc Millis to String
// */
//fun Long?.millisToFormedDate(format : String = BASE_DATE_FORMATE): String {
//    val formed = this?.let { millis ->
//        val formatter = SimpleDateFormat(format)
//        formatter.format(Date(millis))
//    } ?: ""
//
//    return formed
//}

fun String?.convertFormat(): Pair<String, String> {
    return this?.let { formed ->
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val date: Date = sdf.parse(formed) as Date
        val millis = date.time

        val dateFormatter = SimpleDateFormat(DOC_DATE_FORMAT)
        dateFormatter.format(Date(millis))

        val timeFormatter = SimpleDateFormat(DOC_TIME_FORMAT)
        timeFormatter.format(Date(millis))

        dateFormatter.format(Date(millis)) to timeFormatter.format(Date(millis))
    } ?: ("" to "")
}
//
//fun getMillis(d: Long): Long {
//    val offset: Int = TimeZone.getDefault().getOffset(d)
//    return (d + offset) / 86400000L * 86400000L - offset
//}
//
///**
// * Today Millis
// */
//fun getTodayMillis(): Long {
//    val d = Date().time
//    return getMillis(d)
//}
//
//fun getWeekMillis(day: Int): Long {
//    val d = Date()
//    val cal = Calendar.getInstance()
//    cal.setTime(d)
//    cal.add(Calendar.DAY_OF_YEAR, day)
//    return getMillis(cal.time.time)
//}
//
///**
// * D-Day
// * @desc "yy.MM.dd".getDfFromToday()
// * @desc "millis".getDfFromToday()
// */
//fun Any.getDfFromToday(): Int {
//    val today = getTodayMillis()
//
//    val period = when (this) {
//        is String -> Period(today, this.formedDateToMillis())
//        is Long -> Period(today, this)
//        else -> Period()
//    }
//
//    return period.weeks * 7 + period.days
//}
//
///**
// * D-Day String Maker
// * @desc "yy.MM.dd (D-NN)"
// */
//fun convertDateLabel(_date: Long, onlyDay: Boolean = false): String {
//    val dateLabel = _date.millisToFormedDate()
//    val dfDays = _date.getDfFromToday()
//    val dDayLabel = if (dfDays != 0) {
//        if (dfDays < 0) {
//            "D+%d".format(dfDays * -1)
//        } else {
//            "D-%d".format(dfDays)
//        }
//    } else {
//        "D Day"
//    }
//
//    val label = if (onlyDay) {
//        "(%s)".format(dDayLabel)
//    } else {
//        "%s (%s)".format(dateLabel, dDayLabel)
//    }
//
//    return label
//}
//
//fun getTimeUsingInWorkRequest() : Long {
//    val currentDate = Calendar.getInstance()
//    val dueDate = Calendar.getInstance()
//
//    dueDate.set(Calendar.HOUR_OF_DAY, 17)
//    dueDate.set(Calendar.MINUTE, 0)
//    dueDate.set(Calendar.SECOND, 0)
//
//    if(dueDate.before(currentDate)) {
//        dueDate.add(Calendar.HOUR_OF_DAY, 24)
//    }
//
//    return dueDate.timeInMillis - currentDate.timeInMillis
//}
//
//fun getTodaySeparate(type: String): Int {
//    val localDate = LocalDate()
//    val today = localDate.toString()
//    val todayArr = today.splitExt("-")
//
//    when (type) {
//        CmdConstants.Date.YEAR -> return Integer.parseInt(todayArr[0])
//        CmdConstants.Date.MONTH -> return Integer.parseInt(todayArr[1])
//        CmdConstants.Date.DAY -> return Integer.parseInt(todayArr[2])
//    }
//
//    return 0
//}
//
//fun String.getDateToAbbr(divider: String): String {
//    if (this.isBlank()) return ""
//
//    val year = this.substring(2, 4)
//    val month = this.substring(4, 6)
//    val day = this.substring(6, 8)
//
//    return String.format(Locale.getDefault(), "%s%s%s%s%s", year, divider, month, divider, day)
//}