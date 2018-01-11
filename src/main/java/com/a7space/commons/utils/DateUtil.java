package com.a7space.commons.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;



/**
 * 日期工具类
 */
public class DateUtil {

    private static String defaultPattern = "yyyy-MM-dd";
    
    private static int FIRST_DATE_OF_WEEK = Calendar.SUNDAY;

    private static String LOCAL_TIMEZONE="GMT+8";
    /**
     *  Created on 2016年2月19日 
     * <p>Discription:[判断字符串是否是日期类型]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param dateStr
     * @param pattern yyyy-MM-dd
     * @return
     */
    public static boolean isValidDate(String dateStr, String pattern) {
        boolean isValid = false;

        if (pattern == null || pattern.length() < 1) {
            pattern = "yyyy-MM-dd";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            // sdf.setLenient(false);
            String date = sdf.format(sdf.parse(dateStr));
            if (date.equalsIgnoreCase(dateStr)) {
                isValid = true;
            }
        } catch (Exception e) {
            isValid = false;
        }
        
        return isValid;
    }
    
    

    /**
     * Formats a Date into a date/time string.
     * 
     * @param date
     * @param pattern
     *            格式 yyyyMMddHHmmss / yyMMdd /...
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return date == null ? null : sdf.format(date);
    }
    
    /**
     *  Created on 2016年6月16日 
     * <p>Discription:[将ISO日期格式化成mongo的iso date str]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param date
     * @return yyyy-MM-ddTHH:mm:ss.000Z
     */
    public static String formatISODateStr(Date date) {
        String result="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date!= null){
            result=sdf.format(date);
            result=result.replace(" ", "T");
            result=result+".000Z";
            //result+="T00:00:00.000Z";
        }
        return result;
    }

    public static String formatISODateStr(String date,String format) {
        String result="";
        try{
            if(StringUtils.isBlank(format)){
                format="yyyy-MM-dd";
            }
            Date temp=parse(date, format);
            result=formatISODateStr(temp);
        }catch(Exception e){
            result=date;
        }
        return result;
    }

    public static Date getDayFromDate(Date date){
        if(date!=null){
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
        }
        return date;
    }
    /**
     * Parses text from the beginning of the given string to produce a date.
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            格式 yyyyMMddHHmmss / yyMMdd /...
     * @return
     * @throws ParseException
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (date == null || "".equals(date)) {
            return null;
        }
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException e) {
            throw new RuntimeException("日期转换错误", e);
        }

    }


    /**
     * add(Calendar.DAY_OF_MONTH, -5)
     * 
     * @param date
     * @param calendorField
     * @param amount
     * @return
     */
    public static Date add(Date date, int calendorField, int amount) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(calendorField, amount);

        return cal.getTime();
    }

    public static Date addSecond(Date date,int amount){
        return add(date,Calendar.SECOND,amount);
    }
    public static Date addMinute(Date date,int amount){
        return add(date,Calendar.MINUTE,amount);
    }
    public static Date addHour(Date date,int amount){
        return add(date,Calendar.HOUR_OF_DAY,amount);
    }
    
    public static Date addDay(Date date,int amount){
        return add(date,Calendar.DAY_OF_MONTH,amount);
    }

     public static Date addMonth(Date date,int amount){
        return add(date,Calendar.MONTH,amount);
    }

    public static String getCurrentDateStr(String pattern){
    	return format(getCurrentDate(), pattern);
    }
    
    public static Date GetYestorDay(){
        Date today=getCurrentDate();
        return addDay(today, -1);
    }
    
    public static Date getCurrentDate(){
        return new Date();
    }
    public static Date getCurrentDate(String pattern){
        String dateStr=format(new Date(), pattern);
        return parse(dateStr, pattern);
    }

    /**
     * @return Calendar.SUNDAY <br/>
     *         Calendar.MONDAY <br/>
     *         Calendar.TUESDAY <br/>
     *         Calendar.WEDNESDAY <br/>
     *         Calendar.THURSDAY <br/>
     *         Calendar.FRIDAY <br/>
     *         Calendar.SATURDAY <br/>
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
        case 1:
            return 7;
        case 2:
            return 1;
        case 3:
            return 2;
        case 4:
            return 3;
        case 5:
            return 4;
        case 6:
            return 5;
        default:
            return 6;
        }
    }


    /**
     * @return Calendar.SUNDAY <br/>
     *         Calendar.MONDAY <br/>
     *         Calendar.TUESDAY <br/>
     *         Calendar.WEDNESDAY <br/>
     *         Calendar.THURSDAY <br/>
     *         Calendar.FRIDAY <br/>
     *         Calendar.SATURDAY <br/>
     */
    public static int getDayOfMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 计算两个日期间相差的天数
     * 
     * @param date
     * @param compareDate
     * @return
     * @throws ParseException
     */
    public static long compareTo(Date date, Date compareDate) {
        // 去掉时分秒
        date = parse(format(date, "yyMMdd"), "yyMMdd");
        compareDate = parse(format(compareDate, "yyMMdd"), "yyMMdd");
        if ((null != date) && (null != compareDate)) {
            long a = (date.getTime() - compareDate.getTime()) / (1000 * 60 * 60 * 24);
            return a;
        }
        return -1;
    }


    /**
     * 判断是否为一周的最后一天(目前配置的是周日为一周的第一天)
     * 
     * @param date
     * @return
     */
    public static boolean isEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        if (weekDay == FIRST_DATE_OF_WEEK) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为月末
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是月末 false 表示不为月末
     * */
    public static boolean isEndOfMonth(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为季末
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是季末 false 表示不是季末
     */
    public static boolean isEndOfQuarter(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1
                && (month == Calendar.MARCH || month == Calendar.JUNE || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER)) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为季出
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是季初 false 表示不是季初
     */
    public static boolean isBeginOfQuarter(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1
                && (month == Calendar.JANUARY || month == Calendar.APRIL || month == Calendar.JULY || month == Calendar.OCTOBER)) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为半年末
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是半年末 false 表示不是半年末
     */
    public static boolean isEndOfHalfYear(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1 && (month == Calendar.JUNE || month == Calendar.DECEMBER)) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为半年出
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是半年初 false 表示不是半年初
     */
    public static boolean isBeginOfHalfYear(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1 && (month == Calendar.JANUARY || month == Calendar.JULY)) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为年末
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是年末 false 表示不为年末
     */
    public static boolean isEndOfYear(Date nowDate) {
        if ("1231".equals(format(nowDate, "MMdd"))) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否为年初
     * 
     * @param nowDate
     *            日期（需要验证的日期）
     * @return boolean true 表示是年初 false 表示不为年初
     */
    public static boolean isBeginOfYear(Date nowDate) {
        if ("0101".equals(format(nowDate, "MMdd"))) {
            return true;
        }
        return false;
    }

    /**
     * 判断某个日期是否在某个日期范围 
     *
     * @param beginDate
     *            日期范围开始 
     * @param endDate
     *            日期范围结束 
     * @param src
     *            需要判断的日期 
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**    *判断原日期是否在目标日期之后
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**    *判断原日期是否在目标日期之前
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }
    
    /**
     *  Created on 2016年2月25日 
     * <p>Discription:[返回两个日期中最早的日期]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param src
     * @param dst
     * @return
     */
    public static Date getMin(Date src, Date dst){
        if(src!=null&&dst!=null){
            if(isBefore(src, dst)){
                return src;
            }else{
                return dst;
            }
        }else if(src!=null&&dst==null){
            return src;
        }else if(src==null&&dst!=null){
            return dst;
        }else{
            return null;
        }
    }

    /**
     *  Created on 2016年2月25日 
     * <p>Discription:[返回两个日期中最晚的日期]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param src
     * @param dst
     * @return
     */
    public static Date getMax(Date src, Date dst){
        if(src!=null&&dst!=null){
            if(isBefore(src, dst)){
                return dst;
            }else{
                return src;
            }
        }else if(src!=null&&dst==null){
            return src;
        }else if(src==null&&dst!=null){
            return dst;
        }else{
            return null;
        }
    }
    
    public static Long getTimeDifference(Date endDate, Date startDate) {
        return Math.abs(endDate.getTime() - startDate.getTime());
    }
    
    public static Long getTimeDifference(String endDateStr, String startDateStr) {
        return getTimeDifference(parse(endDateStr,"yyyy-MM-dd hh:mm:ss"), parse(startDateStr,"yyyy-MM-dd hh:mm:ss"));
    }

    public static int getDayDifference(Date endDate, Date startDate) {
        Long day=getTimeDifference(endDate, startDate)/(24*3600*1000);
        return day.intValue();
    }
    public static int getDayDifference(String endDateStr, String startDateStr) {
        Long day=getTimeDifference(endDateStr, startDateStr)/(24*3600*1000);
        return day.intValue();
    }
    
    public static int getSecondDifference(Date endDate, Date startDate){
        Long second=getTimeDifference(endDate, startDate)/1000;
        return second.intValue();
    }
    

    public static List<Date> GetDatesBetweenAB(String A, String B) {
        List<Date> result = new ArrayList<Date>();
        Date start = parse(A, defaultPattern);
        Date end = parse(B, defaultPattern);

        while (start.getTime() <= end.getTime()) {
            result.add(start);
            start = new Date(start.getTime() + 86400000);
        }
        return result;
    }
    public static List<Date> GetDatesBetweenAB(Date A, Date B) {
        List<Date> result = new ArrayList<Date>();
        Date start = A;
        Date end = B;
        while (start.getTime() <= end.getTime()) {
            result.add(start);
            start = new Date(start.getTime() + 86400000);
        }
        return result;
    }

    public static List<String> GetDatesStringBetweenAB(String A, String B) {
        return GetTimesStringBetweenAB(A,B,"yyyy-MM-dd",86400000);
    }
    
    public static List<String> GetDatesStringBetweenAB(long A, long B) {
        return GetTimesStringBetweenAB(A,B,"yyyy-MM-dd",86400000);
    }
    public static List<String> GetDatesStringBetweenAB(Date A, Date B) {
        return GetTimesStringBetweenAB(A,B,"yyyy-MM-dd",86400000);
    }
    
    public static List<String> GetHoursStringBetweenAB(long A, long B,String pattern) {
        return GetTimesStringBetweenAB(A,B,pattern,3600000);
    }
    public static List<String> GetHoursStringBetweenAB(String A, String B,String pattern) {
        return GetTimesStringBetweenAB(A,B,pattern,3600000);
    }
    public static List<String> GetHoursStringBetweenAB(Date A, Date B,String pattern) {
        return GetTimesStringBetweenAB(A,B,pattern,3600000);
    }
    
    public static List<String> GetTimesStringBetweenAB(long A, long B,String pattern,long millisOffset) {
        if(StringUtils.isBlank(pattern)){
            pattern="yyyy-MM-dd";
        }
        List<String> result = new ArrayList<String>();
        Date start = timeMillisToDate(A);
        Date end = timeMillisToDate(B);
        while (start.getTime() <= end.getTime()) {
            String item = format(start, pattern);
            result.add(item);
            start = new Date(start.getTime() + millisOffset);
        }
        return result;
    }
    
    public static List<String> GetTimesStringBetweenAB(String A, String B,String pattern,long millisOffset) {
        if(StringUtils.isBlank(pattern)){
            pattern="yyyy-MM-dd";
        }
        Date start = parse(A, pattern);
        Date end = parse(B, pattern);
        return GetTimesStringBetweenAB(start,end,pattern,millisOffset);
    }
    public static List<String> GetTimesStringBetweenAB(Date A, Date B,String pattern,long millisOffset) {
        if(StringUtils.isBlank(pattern)){
            pattern="yyyy-MM-dd";
        }
        List<String> result = new ArrayList<String>();
        Date start = A;
        Date end = B;
        while (start.getTime() <= end.getTime()) {
            String item = format(start, pattern);
            result.add(item);
            start = new Date(start.getTime() + millisOffset);
        }
        return result;
    }

    public static Date timeMillisToDate(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date result=calendar.getTime();
        
        return result;
    }
    public static String timeMillisToDate(long millis,String pattern){
        if(StringUtils.isBlank(pattern)){
            pattern="yyyy-MM-dd hh:mm:ss";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Date result=calendar.getTime();
        return format(result, pattern);
    }
    public static Date longSecondToDate(long second) {  
        // 将微信传入的CreateTime转换成long类型，再乘以1000
        long microsecond = second * 1000L;
        return new Date(microsecond);
    }
    public static String longSecondToDate(long second,String pattern){
        Date time=longSecondToDate(second);
        if(StringUtils.isBlank(pattern)){
            pattern="yyyy-MM-dd hh:mm:ss";
        }
        return format(time, pattern);
    }
    public static int getAge(Date birthDay) {
        int result=0;
        try {
            Calendar cal = Calendar.getInstance();  
            if (cal.before(birthDay)) {  
                return 0;
            }  
            int yearNow = cal.get(Calendar.YEAR);  
            int monthNow = cal.get(Calendar.MONTH);  
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
            cal.setTime(birthDay);  
      
            int yearBirth = cal.get(Calendar.YEAR);  
            int monthBirth = cal.get(Calendar.MONTH);  
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
      
            int age = yearNow - yearBirth;  
      
            if (monthNow <= monthBirth) {  
                if (monthNow == monthBirth) {  
                    if (dayOfMonthNow < dayOfMonthBirth) age--;  
                }else{  
                    age--;  
                }  
            }
            result=age;
        } catch (Exception e) {
            // TODO: handle exception
            result=0;
        }
        return result;  
    }
    
    public static Date clearTime(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
    
    public static boolean isToday(Date date){
    	if (date == null) {
			return false;
		}
    	
    	Date today=DateUtil.getCurrentDate();
    	String todayStr=DateUtil.format(today, "yyyy-MM-dd");
    	String dateStr=DateUtil.format(date, "yyyy-MM-dd");
    	return StringUtils.equals(todayStr, dateStr);
    }
}