package com.jcca.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateTimeUtil
 * @Description: 日期格式话辅助类
 * @author: TanLiang
 */
public final class DateTimeUtil {
    /**
     * 构造函数
     */
    private DateTimeUtil() {
    }

    /**
     * 日期时间格式化类
     */
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式化类
     */
    private static SimpleDateFormat dateTime = new SimpleDateFormat("HH:mm:ss");

    /**
     * 日期类型格式化类
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 不带-的日期
     */
    private static SimpleDateFormat dateFormatyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    
    /**
     * 不带-的时间
     */
    private static SimpleDateFormat dateTimeFormatHHmmss = new SimpleDateFormat("HHmmss");

    /**
     * 时间(HH:mm)
     */
    private static SimpleDateFormat dateTimeFormatHHmm = new SimpleDateFormat("HH:mm");
    
    /**
     * 日期类型格式化类
     */
    private static SimpleDateFormat dateTimeyyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 日期类型格式化类
     */
    private static SimpleDateFormat dateTimeyyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 获取当前系统时间并格式化成日期时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentDateyyyyMMdd() {
        return dateFormatyyyyMMdd.format(new Date());
    }

    /**
     * 获取当前系统时间并格式化成时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentTimeHHmmss() {
        return dateTimeFormatHHmmss.format(new Date());
    }

    /**
     * 获取当前系统时间并格式化成日期时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentDateTime() {
        return dateTimeFormat.format(new Date());
    }

    /**
     * 获取当前系统时间并格式化成时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentTime() {
        return dateTime.format(new Date());
    }
    
    /**
     * 获取当前系统时间并格式化成日期类型.
     * 
     * @return 字符串
     */
    public static String getCurrentDate() {
        return dateFormat.format(new Date());
    }

    /**
     * 格式化指定日期成日期字符串.
     * 
     * @param date
     *            日期
     * @return 字符串
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 格式化指定日期成日期时间字符串.
     * 
     * @param date
     *            日期
     * 
     * @return 字符串
     */
    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 相隔天数
     * @throws ParseException
     *             ParseException
     */
    public static int daysBetween(String beginDate, String endDate) throws ParseException {
        long betweenDays = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        try {
            date1 = sdf.parse(beginDate);
            date2 = sdf.parse(endDate);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();

            betweenDays = (time2 - time1) / (1000 * 3600 * 24) + 1;

        } catch (ParseException e) {
            throw e;
        }

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 计算两个日期之间的周天(星期的日子)
     * 
     * @param beginDate
     *            开始日期
     * @param endDate
     *            结束日起
     * @return 相隔周天
     * @throws ParseException
     *             ParseException
     */
    public static int weekDaysBetween(String beginDate, String endDate) throws ParseException {
        int holidays = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        int weekDay = -1;

        try {
            date1 = sdf.parse(beginDate);
            date2 = sdf.parse(endDate);

            Calendar calFrom = Calendar.getInstance();
            calFrom.setTime(date1);
            Calendar calTo = Calendar.getInstance();
            calTo.setTime(date2);

            while (true) {
                weekDay = calFrom.get(Calendar.DAY_OF_WEEK);
                if (weekDay == 1){
                    holidays++;
                }
                if (calFrom.compareTo(calTo) == 0) {
                    break;
                }
                calFrom.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            throw e;
        }

        return holidays;
    }
    
    /**
     * 获取参数日期的时间为最大时间
     * @param source yyyy-MM-dd
     * @return yyyy-MM-dd 23:59:59
     * @throws ParseException
     */
    public static Date maxDateTime(String source) throws ParseException{
		Date date = dateFormat.parse(source);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//加1天
		calendar.add(Calendar.DATE, 1);
		//减1秒
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
    
    /**
     * java获取当前时间加4小时之后的时间
     * @return
     */
    public static String getPlusAmountDateTime(){
    	String excuDateTime = "";
    	long curren = System.currentTimeMillis();		
    	curren += 4*60 * 60 * 1000;		
    	Date da = new Date(curren);		
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");		
    	excuDateTime = dateFormat.format(da);
    	return excuDateTime;
    }
    
    public static String getCurrentMonthFirst() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		return first;
	}
    
    /**
	 * 获取当月最后一天
	 * @author shj
	 * @return
	 */
	public static String getCurrentMonthLast() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}
	

	/**
	 * 获取指定日期的前一天
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}
	
	
	 /**
     * 获取上一个月
     * 
     * @return
     */
    public static String getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String lastMonth = dft.format(cal.getTime());
        return lastMonth;
    }

    /**
     * 获取当前月
     * 
     * @return
     */
    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
        String currentMonth = dft.format(cal.getTime());
        return currentMonth;
    }
    
    
    public static void main(String[] args) {
    	String aa = DateTimeUtil.getPlusAmountDateTime();
    	System.out.println(aa);
    }
    
    /**
     * 获取当前系统时间并格式化成日期时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentTimeyyyyMMddHHmmss() {
    	return dateTimeyyyyMMddHHmmss.format(new Date());
    }
    
    /**
     * 获取当前系统时间并格式化成日期时间类型.
     * 
     * @return 字符串
     */
    public static String getCurrentTimeyyyyMMddHHmmssSSS() {
    	return dateTimeyyyyMMddHHmmssSSS.format(new Date());
    }
    

	/**
	 * 格式化指定日期成日期时间字符串. private static SimpleDateFormat dateTimeFormat = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); static SimpleDateFormat
	 * 存在多线程安全问题 为什么阿里巴巴禁止把SimpleDateFormat定义为static类型的？
	 * https://blog.csdn.net/l18848956739/article/details/84887643
	 * 主要的几个手段有改为局部变量、使用synchronized加锁、使用Threadlocal为每一个线程单独创建一个和使用Java8中的DateTimeFormatter类代替等。
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return 字符串
	 */
	public static String formatDateTimeSecurity(Date date) {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateTimeFormat.format(date);
	}

    /**
     * 格式转化（HH:mm）
     * @param date
     * @return
     */
	public static  String  formatDateTimeFormatHHmm(Date date){
        return dateTimeFormatHHmm.format(date);
    }

}
