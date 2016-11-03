package com.bys.holidayblessing.tools;

public class NongLiHelper {
	public static String getYLrq(String paramString) {
		return Common.getDateadd(ChineseCalendar.sCalendarLundarToSolar(Integer
				.valueOf(paramString.split("-")[0]).intValue(), Integer
				.valueOf(paramString.split("-")[1]).intValue(), Integer
				.valueOf(paramString.split("-")[2]).intValue()), -1,
				"yyyy-MM-dd");
	}
}
