package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataCalenerMain {

	public static void main(String[] args) throws Exception {
		/*
		 * java.util.Date
		 * The class Date represents a specific instant in time, with millisecond precision
		 */
		Date now = new Date();
		String nowStr = now.toString();
		System.out.println(nowStr);
		String nowLocaleStr = now.toLocaleString();
		System.out.println(nowLocaleStr);
		System.out.println("---시간저장형식---");
		long currentTimeMillis1 = now.getTime();
		/*
		 Returns the number of milliseconds since January 1, 1970, 00:00:00 GMTrepresented by this Date object.
		 */
		System.out.println(currentTimeMillis1);
		long currentTimeMillis2 = System.currentTimeMillis();
		/*
		the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC.
		 */
		System.out.println(currentTimeMillis2);
		System.out.println("--------SimpleDateFormat[Date --> String]----------");
		SimpleDateFormat sdf1= new SimpleDateFormat();
		sdf1.applyPattern("yyyy년 MM월 dd일");
		String formatStr1 = sdf1.format(now);
		System.out.println(formatStr1);
		sdf1.applyPattern("yyyy/MM/dd HH:mm:ss.SSSS");
		String formatStr2=sdf1.format(now);
		System.out.println(formatStr2);
		sdf1.applyPattern("yyyy");
		String yearStr = sdf1.format(now);
		System.out.println(yearStr);
		System.out.println(Integer.parseInt(yearStr));
		
		System.out.println("--------SimpleDateFormat[String --> Date]----------");
		SimpleDateFormat sdf2 = new SimpleDateFormat();
		sdf2.applyPattern("yyyy-MM-dd");
		Date myBirthDay = sdf2.parse("2000-06-03");
		sdf2.applyPattern("yyyy/MM/dd HH:mm:ss");
		Date yourBirthDay = sdf2.parse("1998/05/04 04:12:30");
		/*
		 * java.util.calander
		 */
		System.out.println("-----------------java.util.calander----------------------");
		Calendar cal1 = Calendar.getInstance();
		System.out.println(cal1.getCalendarType());
		long currentTimeMillis3 = cal1.getTimeInMillis();
		System.out.println(currentTimeMillis3);
		int year = cal1.get(Calendar.YEAR);
		int month = cal1.get(Calendar.MONTH)+1;
		//month는 0부터 나온다 그래서 +1을 해준다
		int day = cal1.get(Calendar.DAY_OF_MONTH);
		int hour = cal1.get(Calendar.HOUR);
		int minute = cal1.get(Calendar.MINUTE);
		int second = cal1.get(Calendar.SECOND);
		int msec = cal1.get(Calendar.MILLISECOND);
		
		System.out.println(year+"/"+month+"/"+day+"/"+hour+"/"+minute+"/"+second+"/"+msec);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.clear();
		cal2.set(2018, Calendar.APRIL, 3, 11, 50, 30);
		Calendar cal3 = Calendar.getInstance();
		cal3.clear();
		cal3.set(Calendar.YEAR, 2021);
		cal3.set(Calendar.MONTH, Calendar.NOVEMBER);
		System.out.println(cal3);
		Calendar cal4 = Calendar.getInstance();
		cal4.setTimeInMillis(System.currentTimeMillis());
		System.out.println(cal4);
		System.out.println("----------Date <---> Calendar----------");
		Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2021/05/01 10:39");
		Calendar calendar1= Calendar.getInstance();
		calendar1.setTimeInMillis(date1.getTime());
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.clear();
		calendar2.set(2019, Calendar.JANUARY, 3);
		Date date2 = new Date(calendar2.getTimeInMillis());
		
		System.out.println("/**************Calendar객체메소드*******************/");
		Calendar calendar3 = Calendar.getInstance();
		calendar3.clear();
		calendar3.set(2019, Calendar.MAY, 20);
		
		Calendar calendar4 = Calendar.getInstance();
		calendar4.clear();
		calendar4.set(2019, Calendar.MAY, 20);

		Calendar calendar5 = Calendar.getInstance();
		calendar5.clear();
		calendar5.set(2020, Calendar.JANUARY, 5);
		
		System.out.println(calendar3.compareTo(calendar4));
		System.out.println(calendar3.equals(calendar4));
		System.out.println(calendar3.compareTo(calendar5));
		System.out.println(calendar5.compareTo(calendar3)); //calendar5가 3보다 이후(더 나중)여서 양수 1이 나옴
		
		System.out.println("원날짜:"+calendar3.get(Calendar.YEAR)+"년 "+calendar3.get(Calendar.MONTH)+"월 "
		+calendar3.get(Calendar.DAY_OF_MONTH)+"일");
		
		calendar3.add(Calendar.DATE, 5);
		System.out.println("5일후:"+calendar3.get(Calendar.YEAR)+"년 "+calendar3.get(Calendar.MONTH)+"월 "
				+calendar3.get(Calendar.DAY_OF_MONTH)+"일");
		calendar3.add(Calendar.DATE, -10);
		System.out.println("10일전:"+calendar3.get(Calendar.YEAR)+"년 "+calendar3.get(Calendar.MONTH)+"월 "
				+calendar3.get(Calendar.DAY_OF_MONTH)+"일");
		calendar3.add(Calendar.MONTH, 6);
		System.out.println("6개월후:"+calendar3.get(Calendar.YEAR)+"년 "+calendar3.get(Calendar.MONTH)+"월 "
				+calendar3.get(Calendar.DAY_OF_MONTH)+"일");
		calendar3.add(Calendar.YEAR, 1);
		System.out.println("1년후:"+calendar3.get(Calendar.YEAR)+"년 "+calendar3.get(Calendar.MONTH)+"월 "
				+calendar3.get(Calendar.DAY_OF_MONTH)+"일");
		
		
		System.out.println("/***************Date객체메소드**********************/");
		Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-07");
		Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-07");
		Date date5 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-08");
		System.out.println("equals:"+date3.equals(date4));
		System.out.println("compareTo:"+date3.compareTo(date4));
		System.out.println("compareTo:"+date3.compareTo(date5));
		System.out.println("compareTo:"+date5.compareTo(date3));
		
	}

}
