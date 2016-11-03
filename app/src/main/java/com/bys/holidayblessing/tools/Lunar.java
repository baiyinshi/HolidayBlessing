package com.bys.holidayblessing.tools;

import java.util.Calendar;
import java.util.Date;

public class Lunar {
	private static String[] Animals;
	private static String[] Gan;
	private static String[] Zhi;
	private static int day;
	private static int dayCyl;
	private static boolean isLeap;
	private static int[] lunarInfo = { 19416, 19168, 42352, 21717, 53856,
			55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840,
			119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261,
			27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432,
			59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432,
			120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423,
			27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728,
			27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632,
			23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616,
			46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216,
			27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984,
			27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888,
			30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780,
			44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296,
			44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200,
			30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645,
			46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936,
			44448 };
	private static int monCyl;
	private static int month;
	private static String[] monthNong;
	private static String[] nStr1;
	private static String[] nStr2;
	private static int year;
	private static int yearCyl;
	private static String[] yearName;

	static {
		Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
		Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉",
				"戌", "亥" };
		Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴",
				"鸡", "狗", "猪" };
		int[] arrayOfInt = new int[24];
		arrayOfInt[1] = 21208;
		arrayOfInt[2] = 42467;
		arrayOfInt[3] = 63836;
		arrayOfInt[4] = 85337;
		arrayOfInt[5] = 107014;
		arrayOfInt[6] = 128867;
		arrayOfInt[7] = 150921;
		arrayOfInt[8] = 173149;
		arrayOfInt[9] = 195551;
		arrayOfInt[10] = 218072;
		arrayOfInt[11] = 240693;
		arrayOfInt[12] = 263343;
		arrayOfInt[13] = 285989;
		arrayOfInt[14] = 308563;
		arrayOfInt[15] = 331033;
		arrayOfInt[16] = 353350;
		arrayOfInt[17] = 375494;
		arrayOfInt[18] = 397447;
		arrayOfInt[19] = 419210;
		arrayOfInt[20] = 440795;
		arrayOfInt[21] = 462224;
		arrayOfInt[22] = 483532;
		arrayOfInt[23] = 504758;
		nStr1 = new String[] { "日", "一", "二", "三", "四", "五", "六", "七", "八",
				"九", "十" };
		nStr2 = new String[] { "初", "十", "廿", "卅", "　" };
		monthNong = new String[] { "正", "正", "二", "三", "四", "五", "六", "七", "八",
				"九", "十", "十一", "十二" };
		yearName = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌",
				"玖" };
	}

	private static void Lunar1(Date objDate) {
		int i, leap = 0, temp = 0;

		// int monCyl,dayCyl,yearCyl;

		// int year,month,day;

		// boolean isLeap;

		Calendar cl = Calendar.getInstance();

		cl.set(1900, 0, 31); // 1900-01-31是农历1900年正月初一

		Date baseDate = cl.getTime();

		// 1900-01-31是农历1900年正月初一

		int offset = (int) ((objDate.getTime() - baseDate.getTime()) / 86400000); // 天数(86400000=24*60*60*1000)

		// System.out.println(offset);

		dayCyl = offset + 40; // 1899-12-21是农历1899年腊月甲子日

		monCyl = 14; // 1898-10-01是农历甲子月

		// 得到年数

		for (i = 1900; i < 2050 && offset > 0; i++) {

			temp = lYearDays(i); // 农历每年天数

			offset -= temp;

			monCyl += 12;

		}

		if (offset < 0) {

			offset += temp;

			i--;

			monCyl -= 12;

		}

		year = i; // 农历年份

		yearCyl = i - 1864; // 1864年是甲子年

		leap = leapMonth(i); // 闰哪个月

		isLeap = false;

		for (i = 1; i < 13 && offset > 0; i++) {

			// 闰月

			if (leap > 0 && i == (leap + 1) && isLeap == false) {

				--i;

				isLeap = true;

				temp = leapDays(year);

			} else {

				temp = monthDays(year, i);

			}

			// 解除闰月

			if (isLeap == true && i == (leap + 1))

				isLeap = false;

			offset -= temp;

			if (isLeap == false)

				monCyl++;

		}

		if (offset == 0 && leap > 0 && i == leap + 1)

			if (isLeap) {

				isLeap = false;

			} else {

				isLeap = true;

				--i;

				--monCyl;

			}

		if (offset < 0) {

			offset += temp;

			--i;

			--monCyl;

		}

		month = i; // 农历月份

		day = offset + 1; // 农历天份

		// System.out.println(day);}
	}

	private static String cDay(int paramInt) {
		switch (paramInt) {
		default:
			return nStr2[(paramInt / 10)] + nStr1[(paramInt % 10)];
		case 10:
			return "初十";
		case 20:
			return "二十";
		case 30:
		}
		return "三十";
	}

	private static String cYear(int paramInt) {
		int i;
		for (String str = " ";; str = yearName[i] + str) {
			if (paramInt <= 0)
				return str;
			i = paramInt % 10;
			paramInt = (paramInt - i) / 10;
		}
	}

	private static String cyclical(int paramInt) {
		return Gan[(paramInt % 10)] + Zhi[(paramInt % 12)];
	}

	private static int getDay() {
		return day;
	}

	private static int getDayCyl() {
		return dayCyl;
	}

	private static boolean getIsLeap() {
		return isLeap;
	}

	public static String getLunar(String paramString) {
		return getLunar(paramString.split("-")[0], paramString.split("-")[1],
				paramString.split("-")[2]);
	}

	public static String getLunar(String year, String month, String day) {
		Date sDObj;
		String s;
		int SY, SM, SD;
		int sy;
		SY = Integer.parseInt(year);

		SM = Integer.parseInt(month);

		SD = Integer.parseInt(day);

		sy = (SY - 4) % 12;

		Calendar cl = Calendar.getInstance();

		cl.set(SY, SM - 1, SD);

		sDObj = cl.getTime();

		// com.veriweb.util.OurLog.debug("sDObj="+sDObj);

		// 日期

		Lunar1(sDObj); // 农历

		s = "农历 " + "【" + Animals[sy] + "】" + cYear(getYear()) + "年" + " ";

		s += (getIsLeap() ? "闰" : "") + monthNong[getMonth()] + "月"

		+ (monthDays(getYear(), getMonth()) == 29 ? "小" : "大");

		s += cDay(getDay()) + " ";

		s += cyclical(getYearCyl()) + "年" + cyclical(getMonCyl()) + "月"

		+ cyclical(getDayCyl()) + "日";

		// System.out.println(s);

		return s;

	}

	private static int getMonCyl() {
		return monCyl;
	}

	private static int getMonth() {
		return month;
	}

	private static int getYear() {
		return year;
	}

	private static int getYearCyl() {
		return yearCyl;
	}

	private static int lYearDays(int y) {
		int i;
		int sum = 348; // 29*12
		for (i = 0x8000; i > 0x8; i >>= 1) {
			// OurLog.debug("i="+i);
			sum += (lunarInfo[y - 1900] & i) == 0 ? 0 : 1; // 大月+1天
		}
		return (sum + leapDays(y)); // +闰月的天数
	}

	private static int leapDays(int paramInt) {
		if (leapMonth(paramInt) != 0) {
			if ((0x10000 & lunarInfo[(paramInt - 1900)]) == 0)
				return 29;
			return 30;
		}
		return 0;
	}

	private static int leapMonth(int paramInt) {
		return 0xF & lunarInfo[(paramInt - 1900)];
	}

	public static void main(String[] paramArrayOfString) {
		System.out.println(getLunar("1990", "8", "26"));
		System.out.println(getLunar("2008", "6", "8"));
		System.out.println(getLunar("2009", "4", "3"));
		System.out.println(getLunar("2011", "11", "22"));
	}

	private static int monthDays(int paramInt1, int paramInt2) {
		if ((lunarInfo[(paramInt1 - 1900)] & 65536 >> paramInt2) == 0)
			return 29;
		return 30;
	}
}