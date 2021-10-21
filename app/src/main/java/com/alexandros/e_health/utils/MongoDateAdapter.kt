package com.alexandros.e_health.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class MongoDateAdapter (mongoDate: String){

    private val mongoDate = mongoDate

    private fun getDay(): Int {
        return mongoDate.substring(8,10).toInt()
    }

    private fun getMonth(): Int {
        return mongoDate.substring(5,7).toInt()
    }

    private fun getYear(): Int {
        return mongoDate.substring(0,4).toInt()
    }

    private fun getHours(): Int {
        return mongoDate.substring(11,13).toInt()
    }

    private fun getMinutes(): Int {
        return mongoDate.substring(14,16).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateToLocalZone(): ZonedDateTime? {
        var date = LocalDateTime.of(
            this.getYear(),
            this.getMonth(),
            this.getDay(),
            this.getHours(),
            this.getMinutes()
        ).atZone(ZoneId.of("UTC"))
        date = date.withZoneSameInstant(ZoneId.of("Europe/Athens"))

        return date
    }
}