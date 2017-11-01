package com.letv.handleassistant.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
    public static SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat HHmmssNoColon = new SimpleDateFormat("HHmmss");
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    public static SimpleDateFormat MMddYYYYHHmmss = new SimpleDateFormat("MMddyyyyHHmmss");
    public static SimpleDateFormat MMddHHmmss = new SimpleDateFormat("MMddHHmmss");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat shortyyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat yyyy_MM_dde = new SimpleDateFormat("yyyy-MM-dd E");
    public static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static SimpleDateFormat yyyy_MM_dd_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static String getCurrentDateTimeyyyyMMddHHmmss() {
        return yyyyMMddHHmmss.format(new Date());
    }

    /**
     * 获取当前毫秒数 -- 秒数
     */
    public static String getCurrentTime() {

        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /*
     * 两个毫秒值之间的时间差
     */
    public static String getBetweenTime(Long ms) {


        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
//		if(second > 0) {
//			sb.append(second+"秒");
//		}
//		if(milliSecond > 0) {
//			sb.append(milliSecond+"毫秒");
//		}
        return sb.toString();
    }
    /*
     * 分钟转换为 天 小时 分
     */
    public static String getBetweenTime(int min) {



        Integer hh =  60;
        Integer dd = hh * 24;

        int day = min / dd;
        int hour = (min - day * dd) / hh;
        int minute = (min - day * dd - hour * hh) ;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute >= 0) {
            sb.append(minute + "分");
        }

        return sb.toString();
    }

    /**
     * 将这种类型yyyy-MM-dd HH:mm:ss的时间转化为long类型的
     *
     * @param serverTimeString
     * @return
     */
    public static long getMillisecondsFromString(String serverTimeString) {
        if ("".equals(serverTimeString)) {
            return 0;
        }

        long millisecond;
        try {
            millisecond = yyyy_MM_dd_HHmmss.parse(serverTimeString).getTime();
            return millisecond;
        } catch (ParseException e) {
            return 0;
        }
    }


    /**
     * 将毫秒数转化成yyyy-MM-dd HH:mm:ss类型的日期
     *
     * @param milliseconds
     * @return
     */
    public static String getStringDateFromMilliseconds(long milliseconds) {
        if (milliseconds == 0) {
            return "";
        }

        String string;
        Date date = new Date(milliseconds);
        string = yyyy_MM_dd_HHmmss.format(date);
        return string;
    }

    public static String getTime(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return getCurrentDateTimeyyyyMMddHHmmss();
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return yyyyMMddHHmmss.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return getCurrentDateTimeyyyyMMddHHmmss();
        }
    }

    public static String getyyyy_MM_ddTime(Date date) {
        return yyyyMMdd.format(date);
    }


    public static String getTimeyyyy_MM_dd_HH_mm_ss(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return yyyy_MM_dd_HHmmss.format(new Date());
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return yyyy_MM_dd_HHmmss.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return yyyy_MM_dd_HHmmss.format(new Date());
        }
    }


    /**
     * 根据毫秒数 获取天数
     *
     * @param millisSeconds
     * @return
     */
    public static String getStringFromMillisSeconds(long millisSeconds) {
        String string = "";
        long days = millisSeconds / 1000 / 60 / 60 / 24;
        long hours = (millisSeconds - days * 24 * 60 * 60 * 1000) / 1000 / 60 / 60;
        long mins = (millisSeconds - days * 24 * 60 * 60 * 1000 - hours * 60 * 60 * 1000) / 1000 / 60;
        long seconds = (millisSeconds - days * 24 * 60 * 60 * 1000 - hours * 60 * 60 * 1000 - mins * 60 * 1000) / 1000;
        string = days + "天" + hours + "小时" + mins + "分钟" + seconds + "秒";
        return string;
    }

    public static String getTimeMMddYYYYHHmmss(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return MMddYYYYHHmmss.format(new Date());
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return MMddYYYYHHmmss.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return MMddYYYYHHmmss.format(new Date());
        }
    }

    public static String getTimeMMddHHmmss(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return MMddHHmmss.format(new Date());
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return MMddHHmmss.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return MMddHHmmss.format(new Date());
        }
    }

    public static long getTimeLong(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return new Date().getTime();
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                return timeSum;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date().getTime();
        }
    }


    public static String getDate(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                Date date = new Date();
                return shortyyyyMMdd.format(date);
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return shortyyyyMMdd.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return getDate(new Date());
        }
    }


    public static String getDateyyyy_MM_dd(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                Date date = new Date();
                return yyyyMMdd.format(date);
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return yyyyMMdd.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return getDate(new Date());
        }
    }


    public static String getOnlytime(String timeStr, long time) {
        try {
            if (TextUtils.isEmpty(timeStr)) {
                return HHmmssNoColon.format(new Date());
            } else {
                long timeSum = yyyyMMddHHmmss.parse(timeStr).getTime() + time;
                Date date = new Date(timeSum);
                return HHmmssNoColon.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return HHmmssNoColon.format(new Date());
        }
    }

    public static String getOnlytime(Date date) {
        return HHmmssNoColon.format(date);
    }

    public static String getDate(Date date) {
        return yyyyMMdd.format(date);
    }

    public static String getTime() {
        return HHmmss.format(new Date());
    }

    public static Long getCurrentMilliseconds() {
        return (new Date().getTime());
    }

    public static String formatDate(String date) {
        try {
            Date d = yyyyMMdd.parse(date);
            return yyyyMMdd.format(d);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(SimpleDateFormat sdf, String date) {
        try {
            Date d = sdf.parse(date);
            String result = sdf.format(d);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDate(Date dt, int num) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DATE, num);// 你要加减的日
        Date result = rightNow.getTime();
        return result;
    }

    /**
     * 获取当前时间的字符串用来给图片命名
     *
     * @return
     */
    public static String getCurrentTimeForPicName() {
        Date date = new Date();
        String format = yyyyMMdd_HHmmss.format(date);
        return format;
    }

    /**
     * 根据出生日期计算出年龄
     */
    public static String getAgeByBirthday(String birthday) {
        Date now = new Date();
        Date born;
        try {
            born = new SimpleDateFormat("yyyy-MM-dd").parse(StringUtil
                    .removeMHS(birthday));
            long days = (now.getTime() - born.getTime())
                    / (1000 * 60 * 60 * 24);
            String years = days / 365 + "";
            return years;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * yyyyMMdd转换成yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String conv2yy_MM_ddStr(String time) {
        String convStr = "";
        if (StringUtil.isNullOrEmpty(time)) {
            return convStr;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            convStr = formatter.format(shortyyyyMMdd.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convStr;
    }

    /**
     * yyyyMMdd转换成MM年dd日
     *
     * @param time
     * @return
     */
    public static String conv2MMddStr(String time) {
        String convStr = "";
        if (StringUtil.isNullOrEmpty(time)) {
            return convStr;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
            convStr = formatter.format(shortyyyyMMdd.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convStr;
    }


    /**
     * yyyyMMddHHmmss转换成yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static String conv2yyyy_MM_ddStr2(String time) {
        String convStr = "";
        if (StringUtil.isNullOrEmpty(time)) {
            return convStr;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            convStr = formatter.format(yyyyMMddHHmmss.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convStr;
    }

    /**
     * 获取当前年月
     *
     * @return
     */
    public static String getCurrYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date());
    }

    /**
     * 根据传入的时间获取当前的年月
     *
     * @param time 时间格式为：yyyyMMdd
     * @return
     */
    public static String getYearMonthByTime(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        try {
            return formatter.format(shortyyyyMMdd.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
