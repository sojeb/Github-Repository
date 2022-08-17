package com.sohid.brain23.database

import android.util.Log
import androidx.room.TypeConverter
import com.sohid.brain23.model.DateFormatters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

  @TypeConverter
  fun fromTimeToDate(localDateTime: String): LocalDateTime {

    return LocalDateTime.from(DateFormatters.ofApiResult().parse(localDateTime))
  }

  @TypeConverter
  fun fromDateToTime(localDateTime: LocalDateTime): String {
    if(localDateTime==null || localDateTime.equals("")) return "";
    Log.i("i see time converted value: ","demo: "+localDateTime.year +" "+localDateTime.month +" "+localDateTime.dayOfMonth +" "+localDateTime.hour +" "+ localDateTime.minute + " "+localDateTime.second)
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      return localDateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:ss"))
    }else{
      return "Date shown from android 8(Api26)"
    }

  }
}
