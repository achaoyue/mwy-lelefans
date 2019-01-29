package com.lelefans.mwy.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 得到几天前的时间
     *
     * @param d   时间
     * @param day 几天
     * @return 结果
    */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d   时间
     * @param day 几天
     * @return 结果
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }
}
