package com.wgu.rusd.c196.database;

import android.util.Log;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.wgu.rusd.c196.util.MyDateUtil.dtf;

public class DateConverter {
    public static final String TAG = DateConverter.class.getName();

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        if(value == null) {
            return null;
        }
        Log.d(TAG + " fromTimestamp", value + "");

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = Instant.ofEpochMilli(value).atZone(zoneId).toLocalDate();
        Log.d(TAG, localDate.format(dtf));
        return localDate;
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate localDate) {
        if(localDate == null) {
            return null;
        }
        Log.d(TAG + " dateToTimestamp",  localDate.format(dtf));
        ZoneId zoneId = ZoneId.systemDefault();
        long epoch = localDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
        Log.d(TAG,epoch + "");
        return epoch;
    }

}
