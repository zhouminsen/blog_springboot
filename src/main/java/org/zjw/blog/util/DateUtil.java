package org.zjw.blog.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * @author 周家伟
 *
 */
public class DateUtil {
	/**
	 * 默认时间显示格式
	 */
	public static final String DEFAULTPATTERN = "yyyy-MM-dd";

	/**
	 * 普通格式"yyyy-MM"
	 */
	public static final String  YYYYMM= "yyyy-MM";
	/**
	 * 普通格式,显示为:年-月-日 时:分:秒
	 */
	public static final String NORMALPATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String NORMALPATTERNHMS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获得当前Date类型时间
	 * @return 当前时间
	 */
	public static Date getCurDate(){
		return strToDate(dateToStr(new Date(), null), null);
	}
	/**
	 * 获取当前时间
	 * @param parttern 时间格式
	 * @return
	 */
	public static Date getCurDate(String parttern){
		return strToDate(dateToStr(new Date(), parttern), parttern);
	}
	
	/**
	 * 获得当前String类型时间
	 * @return
	 */
	public static String getCurDateStr(){
		return dateToStr(new Date(), null);
	}
	/**
	 * 获取当时时间
	 * @param parttern 时间格式
	 * @return
	 */
	public static String getCurDateStr(String parttern){
		return dateToStr(new Date(), parttern);
	}
	
	/**
	 * 把日期字符串转为java.utils.Date类型
	 */
	public static Date strToDate(String dateStr){
		return strToDate(dateStr,null);
	}
	public static Date strToDate(String dateStr,String parttern){
		if (parttern == null || parttern.equals("")){
			parttern = DEFAULTPATTERN;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(parttern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 把java.utils.Date类型转为日期字符串
	 */
	public static String dateToStr(Date date){
		return dateToStr(date,null);
	}
	public static String dateToStr(Date date,String parttern){
		if (parttern == null || parttern.equals("")){
			parttern = DEFAULTPATTERN;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(parttern);
		return sdf.format(date);
	}
	
	/**
     * 获取上个月第一天
     * @return
     */
    public static Date getFirstDayOfLastMonth(){
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH, -1);
    	c.set(Calendar.DAY_OF_MONTH,1);
    	c.set(Calendar.HOUR_OF_DAY,	0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	return c.getTime();
    }
    
    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getLastDayOfLastMonth(){
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MONTH, -1);
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	
    	String dateStr = format.format(c.getTime());
    	try{
    		return strToDate(dateStr+" 23:59:59","yyyy-MM-dd hh:mm:ss");
    	}catch(Exception e){
    		return null;
    	}
    	
    	/*c.set(Calendar.HOUR_OF_DAY, 23);
    	c.set(Calendar.MINUTE, 59);
    	c.set(Calendar.SECOND, 59);
    	return c.getTime();*/
    }

	/**
	 * 获取当月第一天
	 * @return
	 */
	public static Date getFirstDayOfCurrentMonth(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY,	0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取当月最后一天
	 * @return
	 */
	public static Date getLastDayOfCuurentMonth(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		String dateStr = format.format(c.getTime());
		try{
			return strToDate(dateStr+" 23:59:59","yyyy-MM-dd hh:mm:ss");
		}catch(Exception e){
			return null;
		}

    	/*c.set(Calendar.HOUR_OF_DAY, 23);
    	c.set(Calendar.MINUTE, 59);
    	c.set(Calendar.SECOND, 59);
    	return c.getTime();*/
	}

	/**
	 * 获取指定日期当月第一天
	 * @return
	 */
	public static Date getFirstDayOfMonthByDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY,	0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定日期当月最后一天
	 * @return
	 */
	public static Date getLastDayOfMonthByDate(Date date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		String dateStr = format.format(c.getTime());
		try{
			return strToDate(dateStr+" 23:59:59","yyyy-MM-dd hh:mm:ss");
		}catch(Exception e){
			return null;
		}

    	/*c.set(Calendar.HOUR_OF_DAY, 23);
    	c.set(Calendar.MINUTE, 59);
    	c.set(Calendar.SECOND, 59);
    	return c.getTime();*/
	}

	/**
	 * 获取向指定日期添加月份的第一天
	 * @return
	 */
	public static Date getFirstDayOfAddMonthByDate(Date date,int month){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY,	0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}


	/**
	 * 向指定日期添加分钟
	 * @return
	 */
	public static Date addMinuteByDate(Date date,int minute){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}
	/**
	 * 向当前日期添加分钟
	 * @return
	 */
	public static Date addMinute(int minute){
		return addMinuteByDate(new Date(),minute);
	}

    
    /**
     * 获取日期的年份
     * @param date
     * @return
     */
    public static int getYear(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.YEAR);
    }
    
    /**
     * 获取日期的月份
     * @param date
     * @return
     */
    public static int getMonth(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.MONTH)+1;	
    }
    
    /**
     *获得日期的天
     * @param date
     * @return
     */
    public static int getDay(Date date) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	//return c.get(Calendar.DATE);
    	return c.get(Calendar.DAY_OF_MONTH);
	}
    
    /**
     * 获取上一年的年份
     * @return
     */
    public static int getLastYear(){
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.YEAR, -1);
    	return c.get(Calendar.YEAR);
    }
    
    /**
     * 获取指定年月份最后一天
     * @param year 指定的年
     * @param month 指定的月
     * @return
     */
    public static String getLastDayOfMonth(int year, int month){
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month-1);
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));
    	String dateStr = format.format(c.getTime());
    	return dateStr;
    }
    
    /**
     * 获取指定年月份共有多少天
     * @param year 指定的年
     * @param month 指定的月
     * @return
     */
    public static int getCurMonthTotalDays(int year, int month){
    	String dateStr=DateUtil.getLastDayOfMonth(year,month);
		return Integer.parseInt(dateStr.substring(dateStr.lastIndexOf("-")+1));
    }
    
    /**
     * 获取指定年月份共有多少天
     * @param year 指定的年
     * @param month 指定的月
     * @return
     */
 	public static int getDaysByYearMonth(int year, int month) {
 		Calendar a = Calendar.getInstance();
 		a.set(Calendar.YEAR, year);
 		a.set(Calendar.MONTH, month - 1);
 		a.set(Calendar.DATE, 1);
 		a.roll(Calendar.DATE, -1);
 		int maxDate = a.get(Calendar.DATE);
 		return maxDate;
 	}
    
    /**
     * 指定时间 添加或减少多少天
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date,int day){
    	 date =strToDate(dateToStr(date, null), null);
         Calendar rightNow = Calendar.getInstance();
         rightNow.setTime(date);
         //rightNow.add(Calendar.YEAR,-1);//日期减1年
         //rightNow.add(Calendar.MONTH,3);//日期加3个月
         rightNow.add(Calendar.DAY_OF_YEAR,day);//日期加XX天
         return rightNow.getTime();
    	
    }
    /**
     * 根据季度解析月份
     * @param quarter 季度
     * @return
     */
   public static int[] getMonthByQuarter(int quarter){
	    int begin=1;
		int end=3;
		if (quarter==2) {
			begin=4;
			end=6;
		}else if (quarter==3) {
			begin=7;
			end=9;
		}else if (quarter==4) {
			begin=10;
			end=12;
		}
		return new int[]{begin,end};
   }
   
   /**
    * 指定时间 添加或减少多少月
    * @param date
    * @param month
    * @return
    */
   public static Date addMonth(Date date,int month){
   	 date =strToDate(dateToStr(date, null), null);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        //rightNow.add(Calendar.YEAR,-1);//日期减1年
        //rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.MONTH,month);//日期加XX天
        return rightNow.getTime();
   	
   }
   
	/**
	 * 获得指定日期第一天
	 * 
	 * @param date yyyy-MM
	 * @return {@link String} 'yyyy-MM-dd'
	 */
	public static String getMonthFirstDay(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date theDate;
		try {
			theDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			theDate = new Date();
		}
		return getMonthFirstDay(theDate);

	}

	/**
	 * 获得指定日期的第一天
	 * @param date
	 * @return {@link String}"yyyy-MM-dd"
	 */
	public static String getMonthFirstDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(getFirstDayDate(date));
	}

	/**
	 * 
	 * 获得指定日期第一天
	 * @param date
	 * @return {@link Date}
	 */
	public static Date getFirstDayDate(Date date) {
		GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();

	}
	
	/**
	 * 获得指定日期的最后一天
	 * 
	 * @param date
	 * @return {@link String} 'yyyy-MM-dd'
	 */
	public static String getMonthLastDay(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date theDate;
		try {
			theDate = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			theDate = new Date();
		}
		return getMonthLastDay(theDate);
	}

	/**
	 * 
	 *  获得指定日期的最后天
	 * @param date
	 * @return {@link String} 'yyyy-MM-dd'
	 */
	public static String getMonthLastDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(getLastDayOfMonth(date));
	}

	/**
	 * 获得指定日期的最后天
	 * 
	 * @param date
	 * @return {@link Date}
	 */
	public static Date getLastDayOfMonth(Date date) {
		GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.roll(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
    
	 /**
     * 获得指定日期指定星期几的日期
     * @param date
     * @param day
     * @return
     */
    public static Date getDayOfWeek(Date date, int day)
    {
        GregorianCalendar c = (GregorianCalendar)Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, day);
        return c.getTime();
    }

	/**
	 * 获得指定年份的第一天
	 * @param date
	 * @return
	 */
	public static Date getYearFirstDay(Date date) {
		return getYearFirstDay(getYear(date));
	}

	/**
	 * 获得指定年份的第一天
	 * @param year
	 * @return
	 */
	public static Date getYearFirstDay(int year) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		try {
			Date date = sdf.parse(year+"");
			return  DateUtil.getFirstDayOfAddMonthByDate(date, 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		/*Date firstDayOfcurrentMonth = getFirstDayOfCurrentMonth();
		String str = DateUtil.dateToStr(firstDayOfcurrentMonth);
		System.out.println(str);*/
		/*Date date = addMonth(new Date(), -1);

		Date firstDayByDate = getFirstDayOfMonthByDate(date);
		String str = dateToStr(firstDayByDate);
		System.out.println(str);*/
/*
		String str = dateToStr(getLastDayOfCuurentMonth());
		System.out.println(str);*/
		/*String str = dateToStr(getLastDayOfMonthByDate(new Date()));
		System.out.println(str);*/
		String str = dateToStr(getFirstDayOfAddMonthByDate(new Date(), 1));
		System.out.println(str);

	}
}
