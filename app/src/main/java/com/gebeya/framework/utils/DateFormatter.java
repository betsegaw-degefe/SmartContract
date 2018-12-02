package com.gebeya.framework.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String DateFormatter(String date) {
        Date mDate = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.ENGLISH);
        try {
            mDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(mDate);

        return formattedDate;

    }
}
