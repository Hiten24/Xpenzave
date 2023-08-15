package com.hcapps.xpenzave.data.local_source.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun toDate(dateInString: String): LocalDateTime = LocalDateTime.parse(dateInString, formatter)

    @TypeConverter
    fun fromDate(date: LocalDateTime): String = date.format(formatter)

}