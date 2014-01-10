package com.zhangyue.zeus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @date 2013-9-6
 * @author rongneng
 */
public class DateUtils {

    /**
     * get current time
     * 
     * @return
     */
    public static String getDate() {
        String s = null;
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        s = sdf.format(d);
        return s;
    }

    /**
     * 获取年月
     * 
     * @param date
     * @param dateformat
     * @return
     */
    public static String getYearMonth(Date date, String dateformat) {
        SimpleDateFormat dd = new SimpleDateFormat(dateformat);
        return dd.format(date);
    }

    /**
     * 字符串转日期
     * 
     * @param date
     * @return
     */
    public static Date StringToDate(String dateStr, String dateformat) {
        SimpleDateFormat dd = new SimpleDateFormat(dateformat);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * get time(one hour,one day,one week)
     * 
     * @param n
     * @return
     */
    public static String getPastTime(int n) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String dt = df.format(date);
        date = new Date(System.currentTimeMillis() - n * 60 * 60 * 1000);
        dt = df.format(date);
        return dt;
    }

    /**
     * function get the days of one year
     * 
     * @return days
     */
    public static boolean getLeapYear() {
        boolean tag = false;
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String dt = df.format(date);
        int pastYear = Integer.parseInt(dt) - 1;
        if (pastYear % 4 == 0 && (pastYear % 100 != 0 || pastYear % 400 == 0)) tag = true;
        return tag;
    }

    /**
     * function get days's date
     * 
     * @param date
     * @param days
     * @return
     */
    public static String decDays(int days) {
        String dateStr = null;
        Date date = new Date();
        Date dt = add(date, days, Calendar.DATE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = df.format(dt);
        return dateStr;
    }

    /**
     * function get pastyear date
     * 
     * @return date
     */
    public static String getPastYearDate() {
        String dateStr = null;
        if (getLeapYear()) {
            dateStr = decDays(-366);
        } else {
            dateStr = decDays(-365);
        }
        return dateStr;
    }

    /**
     * function get assign time
     * 
     * @param date
     * @param amount
     * @param field
     * @return
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * function format time
     * 
     * @param str
     * @return
     */
    public static String getFormatDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String dateStr = null;
        try {
            date = sdf.parse(str);
            dateStr = sdf.format(date).replaceAll("-", "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * 得到某年某月的第一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 得到某年某月的最后一天
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    }

    /**
     * 获取年月
     * 
     * @return
     */
    public static String getYearMonth(String datestr) {
        Date date = StringToDate(datestr, "yyy-MM-dd");
        return getYearMonth(date, "yyyy-MM");
    }

    public static void main(String[] args) {
        String str = "2013-11-25";
        System.out.println(DateUtils.getYearMonth(str));

    }
}
