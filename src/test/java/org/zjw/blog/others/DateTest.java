package org.zjw.blog.others;

import org.junit.Test;
import org.zjw.blog.util.DateUtil;

import java.util.Date;

public class DateTest {
	/**
	 * 获得天
	 */
	@Test
	public void getDay() {
		int days = DateUtil.getDay(new Date());
		System.out.println(days);
	}

	/**
	 * 指定年月计算当月共有多少天
	 */
	@Test
	public void getDaysByYearMonth() {
		System.out.println("当前月份共有" + DateUtil.getLastDayOfMonth(2016, 7));
		System.out.println(DateUtil.getCurMonthTotalDays(2016, 7));
	}
	
	/**
	 * 指定年月计算当月共有多少天
	 */
	@Test
	public void getFirstDayDate() {
		System.out.println(DateUtil.getMonthFirstDay(new Date()));
		System.out.println(DateUtil.getFirstDayDate(new Date()));
		System.out.println(DateUtil.getMonthFirstDay("2016-01-07"));
		System.out.println(DateUtil.dateToStr(DateUtil.getDayOfWeek(DateUtil.strToDate("2016-8-24"), 2)));
		System.out.println(DateUtil.getLastDayOfMonth(new Date()));
		System.out.println(DateUtil.getMonthLastDay(new Date()));
		System.out.println(DateUtil.getMonthLastDay("2014-7-7"));
	}

}
