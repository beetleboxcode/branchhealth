package com.app.branchhealth.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(String date){
        SimpleDateFormat formatInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 2011-03-18
        // 04:44:43.926
        SimpleDateFormat formatOutput = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try{
            Date d = formatInput.parse(date);
            return formatOutput.format(d);
        }catch(ParseException e){
            Log.e(DateUtils.class.getName(), "Cannot format date input : " + date);
        }
        return null;
    }

    public static boolean isEqualOrLaterCurrentDate(String date, String dateFormat){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date selectedDate = sdf.parse(date);
            String currDate = sdf.format(new Date());
            if(!date.equals(currDate)){
                if(selectedDate.after(new Date()))
                    return true;
            }else
                return true;
        }catch(ParseException e){
        }

        return false;
    }

    public static boolean isBeforeCurrentDate(String date, String dateFormat){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date selectedDate = sdf.parse(date);
            String currDate = sdf.format(new Date());
            if(!date.equals(currDate)){
                if(selectedDate.before(new Date()))
                    return true;
            }
        }catch(ParseException e){
        }

        return false;
    }

    public static String formatDate(String sDate, String fmtSrcDate, String fmtDstDate){
        SimpleDateFormat formatInput = new SimpleDateFormat(fmtSrcDate);
        SimpleDateFormat formatOutput = new SimpleDateFormat(fmtDstDate);
        try{
            Date d = formatInput.parse(sDate);
            return formatOutput.format(d);
        }catch(ParseException e){
            Log.e(DateUtils.class.getName(), "Cannot format date input : " + sDate);
        }
        return null;
    }

    public static String displayCurrentTimestamp(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public static String displayCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getCurrentDate(String dateFormat){
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

}
