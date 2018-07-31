/**
 *
 */
package icignal.batch.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
  * @fileName : ICNDateUtility.java
  * @project : iCignal-Common
  * @date : 2017. 1. 18.
  * @author : knlee
  * @변경이력 :
  * @descripton :
  */
public class ICNDateUtility {


	/**	기본 날짜 포맷 */
	public final static  String yyyy ="yyyy";
	public final static  String MM ="MM";
	public final static  String dd ="dd";
	public final static  String HH ="HH";
	public final static  String mm ="mm";
	public final static  String ss ="ss";

	public final static  String yyyyMMdd_DASH = "yyyy-MM-dd";
	public final static  String yyyyMMdd_SLASH = "yyyy/MM/dd";
	public final static  String yyyyMMdd =  "yyyyMMdd";
	public final static String yyyyMMddHHmmssSSS ="yyyyMMddHHmmssSSS";
	public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public final static String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

	public final static String SIMPLE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	public final static String HANGUL_DATE_FORMAT = "yyyy년 MM월 dd일 HH시 mm분 ss초";
	public final static String HANGUL_SHORT_DATE_FORMAT = "yyyy년 MM월 dd일";

	public final static String SHORT_DATE_FORMAT = "yyyy/MM/dd";


	/** 기본 TimeZone */
	public final static String DEFAULT_TIMEZONE = "JST";

    /**
     * Date를 timezone과 format에 따른 날짜 String으로 변환해서 리턴한다.
     * @param 	date 날짜
     * @param 	format 날짜 포맷 (예) yyyyMMddHHmmss
     * @param 	timezone Timezone (예) JST
     * @return 	날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public static String getFormattedDate(Date date, String format, String timezone) {
			java.util.TimeZone homeTz = java.util.TimeZone.getTimeZone(timezone);
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
			formatter.setTimeZone(homeTz);
			return formatter.format(date);
	}

        public static String getFormattedDate(long date) {
                return getFormattedDate(new Date(date), DEFAULT_DATE_FORMAT);
        }

        public static String getFormattedShortDate(long c_date) {
                return getFormattedDate(new Date(c_date), yyyyMMdd);
        }


        public static String getFormattedDate(long date, String format) {
            return getFormattedDate(new Date(date), format);
        }


     /**
      * 프로그램 실행시간 구하기(초)
      * @param start  시작시간(System.currentTimeMillis())
      * @param end   종료시간(System.currentTimeMillis())
      * @return
      */
     public static String getPerformTime_ss(long start , long end ){
         return String.valueOf((start - end) / 1000);
     }

    /**
     * Date의 format을 변경해서 리턴한다.
     * @param 	date 날짜
     * @param 	format1 날짜 포맷 (예) yyyyMMddHHmmss
     * @param 	format2 날짜 포맷 (예) yyyyMMddHHmmss
     * @return 	날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public static String changeForm(String date, String format1, String format2) {
		return getFormattedDate(getDate(date, format1), format2);
	}

	/**
	 * Date를 format에 따른 날짜 String으로 변환해서 리턴한다. (DEFAULT_TIMEZONE을 사용)
     * @param 	date 날짜
     * @param 	format 날짜 포맷 (예) yyyyMMddHHmmss
     * @return 	날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public static String getFormattedDate(Date date, String format) {
		return getFormattedDate(date, format, DEFAULT_TIMEZONE);
	}

	/**
	 * Date를 날짜 String으로 변환해서 리턴한다. (DEFAULT_TIMEZONE과 DEFAULT_DATE_FORMAT을 사용)
     * @param 	date 날짜
     * @return 	날짜 String을 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public static String getFormattedDate(Date date) {
		return getFormattedDate(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * Date를 yyyyMMdd 에 맞춰 리턴한다.
	 */
	public static String getFormattedShortDate(Date date) {
		return getFormattedDate(date, yyyyMMdd);
	}

	/**
	 * 날짜 String을 Date로 변환해서 리턴한다.
     * @param 	date 날짜 String
     * @param 	format 날짜 포맷
     * @return 	날짜 String을 Date로 변환해서 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public static Date getDate(String date, String format) {

		if ((date == null) || (format == null)) return null;
	
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		return formatter.parse(date, new java.text.ParsePosition(0));
	
	}

	/**
	 * 날짜 String을 Date로 변환해서 리턴한다. (DEFAULT_DATE_FORMAT을 사용)
     * @return 날짜 String을 Date로 변환해서 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
/*
	public static Date getDate(String date) {
		return getDate(date, DEFAULT_DATE_FORMAT);
	}
*/
	/**
	 * src_date가 시간상 target_date 이전인지를 check한다.
     * @param 	format 날짜 포맷
     * @return 	src_date가 target_date이전이면 true를 그렇지 않으면 false를 리턴한다.
     * @exception 	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean before(String src_date, String target_date, String format) {
		Date src = getDate(src_date, format);
		Date target = getDate(target_date, format);

		if ((src == null) || (target == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		return src.before(target);
	}

	/**
	 * src_date가 시간상 target_date 이전인지를 check한다. (DEFAULT_DATE_FORMAT을 사용)
     * @return	src_date가 target_date이전이면 true를 그렇지 않으면 false를 리턴한다.
     * @exception 	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean before(String src_date, String target_date) {
		return before(src_date, target_date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * src_date가 시간상 target_date 이후인지를 check한다.
     * @param	format 날짜 포맷
     * @return	src_date가 target_date이후이면 true를 그렇지 않으면 false를 리턴한다.
     * @exception	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean after(String src_date, String target_date, String format) {
		Date src = getDate(src_date, format);
		Date target = getDate(target_date, format);

		if ((src == null) || (target == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		return src.after(target);
	}

	/**
	 * src_date가 시간상 target_date 이후인지를 check한다. (DEFAULT_DATE_FORMAT을 사용)
     * @return	src_date가 target_date이후이면 true를 그렇지 않으면 false를 리턴한다.
     * @exception 	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean after(String src_date, String target_date) {
		return after(src_date, target_date, DEFAULT_DATE_FORMAT);

	}

    /**
     * src_date가 시간상 target_date와 같은지 check한다.
     * @param 	format 날짜 포맷
     * @return 	src_date가 target_date와 같으면 true를 그렇지 않으면 false를 리턴한다.
     * @exception	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean equals(String src_date, String target_date, String format) {
		
		if(src_date == null || target_date == null ) return false;
		
		Date src = getDate(src_date, format);
		Date target = getDate(target_date, format);

		if ((src == null) || (target == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		return (src.compareTo(target) == 0) ? true:false;
	}

	/**
	 * src_date가 시간상 target_date와 같은지 check한다.
     * @return 	src_date가 target_date와 같으면 true를 그렇지 않으면 false를 리턴한다.
     * @exception	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean equals(String src_date, String target_date) {
		return equals(src_date, target_date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * start_date와 end_date 사이의 날자 간격을 리턴한다.
	 * @param 	format 날짜 포맷
	 * @exception 	IllegalArgumentException Parameter가 null이거나 날짜 포맷과 맞지 않는 경우 발생
	 */
	public static int difference(String start_date, String end_date, String format) {
		Date start = getDate(start_date, format);
		Date end = getDate(end_date, format);

		if ((start == null) || (end == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		long lStart = start.getTime()/(1000*60*60*24);
		long lEnd = end.getTime()/(1000*60*60*24);

		return (int) Math.abs(lStart - lEnd);
	}

	/**
	 * start_date와 end_date 사이의 날자 간격을 리턴한다. (DEFAULT_DATE_FORMAT 사용)
	 * @exception 	IllegalArgumentException Parameter가 null이거나 날짜 포맷과 맞지 않는 경우 발생
	 */
	public static int difference(String start_date, String end_date) {
		return difference(start_date, end_date, DEFAULT_DATE_FORMAT);
	}


	/**
	 * src_date가 시간상 start_date와 end_date 사이에 있는지 check한다.
     * @param 	format 날짜 포맷
     * @return	true if start_data <= src_date <= end_date, otherwise false
     * @exception 	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생
     */
	public static boolean between(String src_date, String start_date, String end_date, String format) {
		Date src = getDate(src_date, format);
		Date start = getDate(start_date, format);
		Date end = getDate(end_date, format);

		if ((src == null) || (start == null) || (end == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		return ((src.compareTo(start) < 0) || (src.compareTo(end) > 0)) ? false:true;
	}

	/**
	 * src_date가 시간상 start_date와 end_date 사이에 있는지 check한다. (DEFAULT_DATE_FORMAT을 사용)
     * @return	true if start_data <= src_date <= end_date, otherwise false
     * @exception 	IllegalArgumentException 날짜 String이 포맷과 맞지 않을 경우 발생한다.
     */
	public static boolean between(String src_date, String start_date, String end_date) {
		return between(src_date, start_date, end_date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * src_date가 시간상 start_date와 end_date 사이에 있는지 check한다.
     * @return	true if start_data <= src_date <= end_date, otherwise false
     * @exception 	IllegalArgumentException Parameter가 null이거나 날짜 포맷과 맞지 않는 경우 발생
     */
	public static boolean between(Date src_date, Date start_date, Date end_date) {
		if ((src_date == null) || (start_date == null) || (end_date == null)) {
			throw new java.lang.IllegalArgumentException();
		}

		return ((src_date.compareTo(start_date) < 0) || (src_date.compareTo(end_date) > 0)) ? false:true;
	}


	/**
	 * date를 기준으로 n 분을 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null이면 발생
     */
	public static Date addMinute(Date date, int minute) {
		if (date == null) throw new java.lang.IllegalArgumentException();

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.MINUTE , minute);
		return cal.getTime();
	}

	/**
	 * date를 기준으로 n 시간을 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null이면 발생
     */
	public static Date addHour(Date date, int hour) {
		if (date == null) throw new java.lang.IllegalArgumentException();

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.HOUR , hour);
		return cal.getTime();
	}


	 /**
      *두날짜를 비교하여 그 차이를 반환한다.
      *@param  long befor  비교할 date
      *@param  long after   비교할 date
      *@return  String 결과치
      */
    public static String dateDiff(long befor, long after) {
             long diff = after - befor;
		long result = diff / (1000*60*60*24);
	     return String.valueOf(result);
	}



     /**
      *오늘부터 시점일까지의 날짜를 반환한다.
      *ex: 2002/11/18 의 30일전 2002/10/19
      *@param   int term   특정 가간일. ex: 30일-> 30
      *@return   String  특정일의 long값
      */
     public static String dateAdd(int term) {
         Calendar now = Calendar.getInstance();
         return dateAdd(now, term);

     }

    /**
      *특정일부터 시점일까지의 날짜를 반환한다.
      *ex: 2002/11/18 의 30일전 2002/10/19
      *@param   Calendar  특정일
      *@param   int term   특정 가간일. ex: 30일-> 30
      *@return   String      특정일의 long값
      */
    public static String dateAdd(Calendar now, int term) {
            now.add(Calendar.DAY_OF_YEAR, - (term));
            long time = now.getTime().getTime();
            return String.valueOf(time);
	}


	/**
	 * date를 기준으로 n날을 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null이면 발생
        */
	public static Date addDay(Date date, int day) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH , day);
		return cal.getTime();
	}

	/**
	 * date를 기준으로 n주를 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null일 경우 발생
     */
	public static Date addWeek(Date date, int week) {
		if (date == null) throw new java.lang.IllegalArgumentException();

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, week);
		return cal.getTime();
	}

	/**
	 * date를 기준으로 n달을 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null일 경우 발생
     */
	public static Date addMonth(Date date, int month) {
		if (date == null) throw new java.lang.IllegalArgumentException();

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * 특정월(수)를 더한 달의 마지막 날짜를 구한다. 예: 2016-12-07이후 10개월이 지난 달의 마지막 날짜. 결과일자: 2017-10-31
	 * @param date
	 * @param month
	 * @exception 	IllegalArgumentException date가 null일 경우 발생
	 * @return
	 */
	public static Date getMonthLastDate(Date date, int addMonth){
		return getMonthLastDate(date, addMonth, DEFAULT_TIMEZONE);
	}

	/**
	 * 특정월(수)를 더한 달의 마지막 날짜를 구한다. 예: 2016-12-07이후 10개월이 지난 달의 마지막 날짜. 결과일자: 2017-10-31
	 * @param date
	 * @param addMonth
	 * @param timezone "JST"
	 * @return
	 */
	public static Date getMonthLastDate(Date date, int addMonth, String timezone){
		if (date == null) return null;
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);

	  return getLastDayCalendar(cal).getTime();

	}


	/**
	 * date를 기준으로 n년을 더한 날짜를 리턴한다.
	 * @exception 	IllegalArgumentException date가 null일 경우 발생
     */
	public static Date addYear(Date date, int year) {
		if (date == null) throw new java.lang.IllegalArgumentException();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	  * DEFAULT_DATE_FORMAT형태의 String Data를 SIMPLE_DATE_FORMAT형태의
	  * String Format 으로 리턴한다
	  */
	public static String toPrintForm(String date) {
	        Date dt = getDate(date);
	        return getFormattedDate(dt, SIMPLE_DATE_FORMAT);
	}

	/**
	  * DEFAULT_DATE_FORMAT형태의 String Data를 SHORT_DATE_FORMAT형태의
	  * String Format 으로 리턴한다
	  */
	public static String toShortForm(String date) {
	    try {
	        Date dt = getDate(date);
	        return getFormattedDate(dt, SHORT_DATE_FORMAT);
	    } catch (Exception e) {
	        return date;
	    }
	}

	/**
	  * SIMPLE_DATE_FORMAT형태의 String Data를 DEFAULT_DATE_FORMAT형태의
	  * String Format 으로 리턴한다
	  */
	public static String toDateForm(String date) {
	        Date dt = getDate(date, SIMPLE_DATE_FORMAT);
	        return getFormattedDate(dt);
	}

	/**
	  * 현재시간을 long값으로 으로 리턴한다
	  */
	public static long getTime() {
    		Date date = new Date();
		return date.getTime();
	}



	/**
	 * 지정된 일자의 마지막날짜를 가져온다
	 * @param s date
	 */
	public static String getLastDayOfMonth(String day , String pattern ){
		String p = getPattern(pattern);
		DateFormat dtFormat = 	new SimpleDateFormat(p);
		String d = null;
		d = removeChar(day);
		Calendar cal = new GregorianCalendar(	Integer.parseInt(d.substring(0,4)),
												Integer.parseInt(d.substring(4,6))-1,
												Integer.parseInt(d.substring(6))
											 );
		return dtFormat.format(getLastDayCalendar(cal).getTime());
	}

	private static String removeChar(String str){
		if(str == null || str.trim().equals("")) return "";
		if(str.length() <= 8 ) return str;
		else return str.substring(0,4)+str.substring(5,7)+str.substring(8);

	}




	/**
	 * 지정된 일자의 첫날짜를 가져온다
	 * @param s date
	 * @return String 예: 2009-02-01
	 */
	public static String getFirstDayOfMonth(String day , String pattern ){
		String p = getPattern(pattern);
		DateFormat dtFormat = 	new SimpleDateFormat(p);
		String d = null;
		d = removeChar(day);
		Calendar cal = new GregorianCalendar(	Integer.parseInt(d.substring(0,4)),
												Integer.parseInt(d.substring(4,6))-1,
												Integer.parseInt(d.substring(6))
											);

		return dtFormat.format(getFirstDayCalendar(cal).getTime());
	}


	public static String getPattern(String pattern){
		String p = null;
		p = pattern;
		if(p == null || p.trim().equals("")) p = "yyyy-MM-dd";
		return p;
	}

	/**
	 * 현재달의 첫날짜를 가져온다 예: 2009-02-01
	 * @param pattern String  yyyy-MM-dd
	 * @return String 예: 2009-02-01
	 */
	public static String getFirstDayOfCurrentMonth(String pattern) {
		String p = getPattern(pattern);
		DateFormat dtFormat = 	new SimpleDateFormat(p);
    	return dtFormat.format(getFirstDayCalendar(Calendar.getInstance()).getTime());
	}


	private static Calendar getFirstDayCalendar(Calendar curCal){

		return new GregorianCalendar(	curCal.get(Calendar.YEAR),
			   							curCal.get(Calendar.MONTH),
			   							curCal.getActualMinimum ( Calendar.DATE )
			  						);

	}


	private static Calendar getLastDayCalendar(Calendar curCal){
		return new GregorianCalendar(	curCal.get(Calendar.YEAR),
			   							curCal.get(Calendar.MONTH),
			   							curCal.getActualMaximum ( Calendar.DATE )
			  						);

	}



	/**
	 * 현재달의 마지막날짜를 가져온다 예: 2009-02-28
	 * @param pattern String  yyyy-MM-dd
	 * @return String 예: 2009-02-28
	 */
	public static String getLastDayOfCurrentMonth(String pattern) {
		String p = getPattern(pattern);
		DateFormat dtFormat = new SimpleDateFormat(p);
		return dtFormat.format(getLastDayCalendar(Calendar.getInstance()).getTime());

	}

////////////////////////////////////////////////////////////////////////////
	  // 일년 간 달력의 월별 날짜수 배열을 구한다.
	  //
	  public static int[] getMonthDaysArray(int yr) {
	    int[] a1 = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	    int[] a2 = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	    if (yr <= 1582) {
	      if ((yr % 4) == 0) {
	        if (yr == 4) {
	          return a1;
	        }
	        return a2;
	      }
	    }
	    else {
	      if (((yr % 4) == 0) && ((yr % 100) != 0)) {
	        return a2;
	      }
	      else if ((yr % 400) == 0) {
	        return a2;
	      }
	    }

	    return a1;
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정된 년도와 달에 따른 요일 편차를 구한다.
	  //
	  public static int addWeekDays(int y, int m) {
	    int[] b1 = { 3, 0, 3, 2, 3, 2, 3, 3, 2, 3, 2, 3 };
	    int[] b2 = { 3, 1, 3, 2, 3, 2, 3, 3, 2, 3, 2, 3 };
	    int i = 0;
	    int rval = 0;

	    if (y <= 1582) {
	      if ((y % 4) == 0) {
	        if (y == 4) {
	          for (i = 0; i < m - 1; i++)
	            rval += b1[i];
	        }
	        else {
	          for (i = 0; i < m - 1; i++)
	            rval += b2[i];
	        }
	      }
	      else {
	        for (i = 0; i < m - 1; i++)
	          rval += b1[i];
	      }
	    }
	    else {
	      if (((y % 4) == 0) && ((y % 100) != 0)) {
	        for (i = 0; i < m - 1; i++)
	          rval += b2[i];
	      }
	      else if ((y % 400) == 0) {
	        for (i = 0; i < m - 1; i++)
	          rval += b2[i];
	      }
	      else {
	        for (i = 0; i < m - 1; i++)
	          rval += b1[i];
	      }
	    }

	    return rval;
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 총 날짜 수를 구한다.
	  //
	  public static int getDaysInYear(int y) {
	    if (y > 1582) {
	        if (y % 400 == 0)
	            return 366;
	        else if (y % 100 == 0)
	            return 365;
	        else if (y % 4 == 0)
	            return 366;
	        else
	            return 365;
	    }
	    else if (y == 1582)
	        return 355;
	    else if (y > 4) {
	        if (y % 4 == 0)
	            return 366;
	        else
	            return 365;
	    }
	    else if (y > 0)
	        return 365;
	    else
	        return 0;
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도, 지정한 월의 총 날짜 수를 구한다.
	  //
	  public static int getDaysInMonth(int m, int y) {
	    if (m < 1 || m > 12)
	        throw new RuntimeException("Invalid month: " + m);

	    int[] b = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	    if (m != 2 && m >= 1 && m <= 12 && y != 1582)
	        return b[m - 1];
	    if (m != 2 && m >= 1 && m <= 12 && y == 1582)
	        if (m != 10)
	            return b[m - 1];
	        else
	            return b[m - 1] - 10;

	    if (m != 2)
	        return 0;

	    // m == 2 (즉 2월)
	    if (y > 1582) {
	        if (y % 400 == 0)
	            return 29;
	        else if (y % 100 == 0)
	            return 28;
	        else if (y % 4 == 0)
	            return 29;
	        else
	            return 28;
	    }
	    else if (y == 1582)
	        return 28;
	    else if (y > 4) {
	        if (y % 4 == 0)
	            return 29;
	        else
	            return 28;
	    }
	    else if (y > 0)
	        return 28;
	    else
	        throw new RuntimeException("Invalid year: " + y);
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 지정한 월의 첫날 부터 지정한 날 까지의 날짜 수를 구한다.
	  //
	  public static int getDaysFromMonthFirst(int d, int m, int y) {
	    if (m < 1 || m > 12)
	        throw new RuntimeException("Invalid month " + m + " in " + d + "/" + m + "/" + y);

	    int max = getDaysInMonth(m, y);
	    if (d >= 1 && d <= max)
	        return d;
	    else
	        throw new RuntimeException("Invalid date " + d + " in " + d + "/" + m + "/" + y);
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
	  //
	  public static int getDaysFromYearFirst(int d, int m, int y) {
	    if (m < 1 || m > 12)
	        throw new RuntimeException("Invalid month " + m + " in " + d + "/" + m + "/" + y);

	    int max = getDaysInMonth(m, y);
	    if (d >= 1 && d <= max) {
	        int sum = d;
	        for (int j = 1; j < m; j++)
	            sum += getDaysInMonth(j, y);
	        return sum;
	    }
	    else
	        throw new RuntimeException("Invalid date " + d + " in " + d + "/" + m + "/" + y);
	  }

	  ////////////////////////////////////////////////////////////////////////////
	  // 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다.
	  // 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
	  //
	  public static int getDaysFrom21Century(int d, int m, int y) {
	    if (y >= 2000) {
	        int sum = getDaysFromYearFirst(d, m, y);
	        for (int j = y - 1; j >= 2000; j--)
	            sum += getDaysInYear(j);
	        return sum - 1;
	    }
	    else if (y > 0 && y < 2000) {
	        int sum = getDaysFromYearFirst(d, m, y);
	        for (int j = 1999; j >= y; j--)
	             sum -= getDaysInYear(y);
	        return sum - 1;
	    }
	    else
	        throw new RuntimeException("Invalid year " + y + " in " + d + "/" + m + "/" + y);
	  }



	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 지정한 월의 첫날 부터 지정한 날 까지의 날짜 수를 구한다.
	  //
	  public static int getDaysFromMonthFirst(String s) {
	    int d, m, y;
	    if (s.length() == 8) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(4, 6));
	        d = Integer.parseInt(s.substring(6));
	        return getDaysFromMonthFirst(d, m, y);
	    }
	    else if (s.length() == 10) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(5, 7));
	        d = Integer.parseInt(s.substring(8));
	        return getDaysFromMonthFirst(d, m, y);
	    }
	    else if (s.length() == 11) {
	        d = Integer.parseInt(s.substring(0, 2));
	        String strM = s.substring(3, 6).toUpperCase();
	        String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
	                                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	        m = 0;
	        for (int j = 1; j <= 12; j++) {
	            if (strM.equals(monthNames[j-1])) {
	                m = j;
	                break;
	            }
	        }
	        if (m < 1 || m > 12)
	            throw new RuntimeException("Invalid month name: " + strM + " in " + s);
	        y = Integer.parseInt(s.substring(7));
	        return getDaysFromMonthFirst(d, m, y);
	    }
	    else
	        throw new RuntimeException("Invalid date format: " + s);
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 첫날 부터 지정한 월의 지정한 날 까지의 날짜 수를 구한다.
	  //
	  public static int getDaysFromYearFirst(String s) {
	    int d, m, y;
	    if (s.length() == 8) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(4, 6));
	        d = Integer.parseInt(s.substring(6));
	        return getDaysFromYearFirst(d, m, y);
	    }
	    else if (s.length() == 10) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(5, 7));
	        d = Integer.parseInt(s.substring(8));
	        return getDaysFromYearFirst(d, m, y);
	    }
	    else if (s.length() == 11) {
	        d = Integer.parseInt(s.substring(0, 2));
	        String strM = s.substring(3, 6).toUpperCase();
	        String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
	                                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	        m = 0;
	        for (int j = 1; j <= 12; j++) {
	            if (strM.equals(monthNames[j-1])) {
	                m = j;
	                break;
	            }
	        }
	        if (m < 1 || m > 12)
	            throw new RuntimeException("Invalid month name: " + strM + " in " + s);
	        y = Integer.parseInt(s.substring(7));
	        return getDaysFromYearFirst(d, m, y);
	    }
	    else
	        throw new RuntimeException("Invalid date format: " + s);
	  }


	  //////////////////////////////////////////////////////////////////////
	  // 2000년 1월 1일 부터 지정한 년, 월, 일 까지의 날짜 수를 구한다.
	  // 2000년 1월 1일 이전의 경우에는 음수를 리턴한다.
	  //
	  public static int getDaysFrom21Century(String s) {
	    int d, m, y;
	    if (s.length() == 8) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(4, 6));
	        d = Integer.parseInt(s.substring(6));
	        return getDaysFrom21Century(d, m, y);
	    }
	    else if (s.length() == 10) {
	        y = Integer.parseInt(s.substring(0, 4));
	        m = Integer.parseInt(s.substring(5, 7));
	        d = Integer.parseInt(s.substring(8));
	        return getDaysFrom21Century(d, m, y);
	    }
	    else if (s.length() == 11) {
	        d = Integer.parseInt(s.substring(0, 2));
	        String strM = s.substring(3, 6).toUpperCase();
	        String[] monthNames = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
	                                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	        m = 0;
	        for (int j = 1; j <= 12; j++) {
	            if (strM.equals(monthNames[j-1])) {
	                m = j;
	                break;
	            }
	        }
	        if (m < 1 || m > 12)
	            throw new RuntimeException("Invalid month name: " + strM + " in " + s);
	        y = Integer.parseInt(s.substring(7));
	        return getDaysFrom21Century(d, m, y);
	    }
	    else
	        throw new RuntimeException("Invalid date format: " + s);
	  }


	  /////////////////////////////////////////////
	  // (양 끝 제외) 날짜 차이 구하기
	  //
	  public static int getDaysBetween(String s1, String s2) {
	    int y1 = getDaysFrom21Century(s1);
	    int y2 = getDaysFrom21Century(s2);
	    return y1 - y2 - 1;
	  }


	  /////////////////////////////////////////////
	  // 날짜 차이 구하기
	  //
	  public static int getDaysDiff(String s1, String s2) {
	    int y1 = getDaysFrom21Century(s1);
	    int y2 = getDaysFrom21Century(s2);
	    return y1 - y2;
	  }


	  /////////////////////////////////////////////
	  // (양 끝 포함) 날짜 차이 구하기
	  //
	  public static int getDaysFromTo(String s1, String s2) {
	    int y1 = getDaysFrom21Century(s1);
	    int y2 = getDaysFrom21Century(s2);
	    return y1 - y2 + 1;
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도, 지정한 월에 지정한 요일이 들어 있는 회수를 구한다.
	  //
	  public static int getWeekdaysInMonth(int weekDay, int m, int y) {
	    int week = ((weekDay - 1) % 7);
	    int days = getDaysInMonth(m, y);
	    int sum = 6;   // 2000년 1월 1일은 토요일
	    if (y >= 2000) {
	      for (int i = 2000; i < y; i++)
	        sum += getDaysInYear(i);
	    }
	    else {
	      for (int i = y; i < 2000; i++)
	        sum -= getDaysInYear(i);
	    }
	    for (int i = 1; i < m; i++)
	      sum += getDaysInMonth(i, y);

	    // if (sum < 0)
	    //  sum += 350*3000;

	    int firstWeekDay = sum % 7;
	    if (firstWeekDay < 0) {
	        firstWeekDay += 7;
	    }

	    // firstWeekDay += 1;
	    int n = firstWeekDay + days;
	    int counter = (n / 7) + (((n % 7) > week) ? 1 : 0);
	    if (firstWeekDay > week)
	      counter--;

	    return counter;
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 지정한 년도의 지정한 달에 지정한 요일이 들어 있는 회수를 구한다.
	  //
	  public static int getWeekdaysInMonth(String weekName, int m, int y) {
	    StringBuffer weekNames1 = new StringBuffer("일월화수목금토");
	    StringBuffer weekNames2 = new StringBuffer("日月火水木金土");
	    String[] weekNames3 = { "sun", "mon", "tue", "wed", "thu", "fri", "sat" };

	    int n = weekNames1.indexOf(weekName);
	    if (n < 0)
	      n = weekNames2.indexOf(weekName);
	    if (n < 0) {
	      String str = weekName.toLowerCase();
	      for (int i = 0; i < weekNames3.length; i++) {
	        if (str.equals(weekNames3[i])) {
	          n = i;
	          break;
	        }
	      }
	    }
	    // System.out.println("n = " + n);

	    if (n < 0)
	      throw new RuntimeException("Invalid week name: " + weekName);

	    return getWeekdaysInMonth(n + 1, m, y);
	  }


	  ////////////////////////////////////////////////////////////////////////////
	  // 2000년 1월 1일 기준을 n일 경과한 날짜 구하기
	  //
	  // @return  yyyy-mm-dd 양식의 String 타입
	  public static String getDateStringFrom21Century(int elapsed) {
	    int y = 2000;
	    int m = 1;
	    int d = 1;

	    int n = elapsed + 1;
	    int j = 2000;
	    if (n > 0) {
	      while (n > getDaysInYear(j)) {
	        n -= getDaysInYear(j);
	        j++;
	      }
	      y = j;

	      int i = 1;
	      while (n > getDaysInMonth(i, y)) {
	        n -= getDaysInMonth(i, y);
	        i++;
	      }
	      m = i;
	      d = n;
	    }
	    else if (n < 0) {
	      while (n < 0) {
	        n += getDaysInYear(j - 1);
	        j--;
	      }
	      y = j;

	      int i = 1;
	      while (n > getDaysInMonth(i, y)) {
	        n -= getDaysInMonth(i, y);
	        i++;
	      }
	      m = i;
	      d = n;
	    }

	    String strY = "" + y;
	    String strM = "";
	    String strD = "";

	    if (m < 10)
	      strM = "0" + m;
	    else
	      strM = "" + m;

	    if (d < 10)
	      strD = "0" + d;
	    else
	      strD = "" + d;

	    return strY + "-" + strM + "-" + strD;
	  }

      /**
	   * 지정한 날짜를 offset일 경과한 날짜 구하기
	   * @param offset
	   * @param d
	   * @param m
	   * @param y
	   * @return yyyy-mm-dd 양식의 String 타입
	   */
	  public static String addDate(int offset, int d, int m, int y) {
	    int z = getDaysFrom21Century(d, m, y);
	    int n = z + offset;
	    return getDateStringFrom21Century(n);
	  }


	  ///////////////////
	  // 지정한 날짜를 offset일 경과한 날짜 구하기
	  //
	  // @return  yyyy-mm-dd 양식의 String 타입
	  public static String addDate(int offset, String date) {
	    int z = getDaysFrom21Century(date);
	    int n = z + offset;
	    return getDateStringFrom21Century(n);
	  }


	  /**
	   *  달 차이 구하기
	   * @param s1
	   * @param s2
	   * @return int
	   */
	   public static int getMonthBetween(String s1, String s2) {
		  int day = getDaysBetween(s1, s2);
	      int month = day/30;
	    return month;
	  }

	  /**
	   * 지정된 몇달 후의 일자를 가져온다.  format : yyyy-mm-dd 예: 2월13일 기준 1달후는 3월13일
	   * @param date 시작일
	   * @param term 몇달 후
	   * @return String  yyyy-mm-dd
	   */
	  public static String getAfterMonth(Date date, int term) {
		  //return getFormattedDate(addMonth(date, term), yyyyMMdd_DASH);
		  return getAfterMonth(date, term, yyyyMMdd_DASH);
	  }


	  /**
	   * 지정된 날부터 몇달 후의 일자를 가져온다.  format : yyyy-mm-dd  예: 2월13일 기준 1달후는 3월13일
	   * @param date 시작일
	   * @param term 몇달 후
	   * @return String  yyyy-mm-dd
	   */
	  public static String getAfterMonth(Date date, int term, String pattern) {

		  return getFormattedDate( addMonth(date, term) , pattern);
	  }

	  /**
	   * 지정된 날부터 몇달 후의 일자를 가져온다.  format : yyyy-mm-dd  예: 2월13일 기준 1달후는 3월12일
	   * @param date 시작일
	   * @param term 몇달 후
	   * @return String  yyyy-mm-dd
	   */
	  public static String getAfterMonthBus(Date date, int term, String pattern) {
		  Date ad = addMonth(date, term);
		  return getFormattedDate( addDay(ad, -1) , pattern);
	  }


	  /**
	   * 지정된 몇달 후의 일자를 가져온다.  format : yyyy-mm-dd 	예: 2월13일 기준 1달후는 3월12일
	   * @param date 시작일
	   * @param term 몇달 후
	   * @return String  yyyy-mm-dd
	   */
	  public static String getAfterMonthBus(Date date, int term) {
		  //return getFormattedDate(addMonth(date, term), yyyyMMdd_DASH);
		  return getAfterMonthBus(date, term, yyyyMMdd_DASH);
	  }


	  /**
	   * 해당일의 요일을 가져온다. 일요일-> 1.
	   *
	   * @param date  yyyyMMdd형식의 문자열
	   * @return int
	   */
	/* public static int getDayOfWeek(String date ){
		 Date d = DateUtil.getDate(date, yyyyMMdd);
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	 }
*/

	 /**
	   * 해당월이 몇주까지 있는지를 가져온다.  예: 20090301 -> 5
	   *
	   * @param date  yyyyMMdd형식의 문자열
	   * @return int
	   */
//	 public static int getCountWeekOfMonth(String date ){
//		 String lastDate = DateUtil.getLastDayOfMonth(date, yyyyMMdd);
//		 Date d = DateUtil.getDate(lastDate, yyyyMMdd);
//		 Calendar cal = Calendar.getInstance();
//		 cal.setTime(d);
//		return cal.get(Calendar.WEEK_OF_MONTH);
//	 }
//
//


	 /**
	  * 달 및 일짜의  문자열 2자리형식으로 리턴한다. 1월 -> 01, 2월 -> 02
	  * @param month
	  * @return
	  */
	public static String getMD2CipherFormat(int month){
		String result = null;
		result = month < 10 ? "0"+String.valueOf(month):String.valueOf(month);
		return result;

	}

     /**
      * 시분초 제외된 Date 가져오기
      * @param localDate
      * @return
      */
	 public static Date asDate(LocalDate localDate) {
		    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }
	 
	 
	 public static Date asDate(LocalDateTime localDateTime) {
		    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	 }

	 public static LocalDate asLocalDate(Date date) {
		    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	 }

	 public static LocalDateTime asLocalDateTime(Date date) {
		    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	 }


	
	public static void main(String args[]){
		
		Date date = asDate(LocalDate.now());
		System.out.println(date);
		
		
		LocalDate today = LocalDate.now();
		
		System.out.println(today);
		
		System.out.println(addDay(new Date(), -1));
		
		/*System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.yyyy));
		System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.MM));
		System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.dd));
		System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.HH));
		System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.mm));
		System.out.println(DateUtil.getFormattedDate(new Date(), DateUtil.ss));
		*/
		//System.out.println(addDate(1,DateUtil.getLastDayOfMonth("2009-04-01", DateUtil.yyyyMMdd_DASH)));
	//	System.out.println(getCountWeekOfMonth("20090501"));
	//System.out.println(	getDaysDiff("2009-04-03", "2009-03-30"));
		/*
		//String startDate = null;
		String endDate = null;
		//String terms = "3";
		//DateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date dt = new Date();
	    DateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
	  //  Calendar cal = Calendar.getInstance();
	    //System.out.println("curCal.DAY_OF_WEEK: " + cal.DAY_OF_WEEK);
	   //System.out.println("cal.getFirstDayOfWeek(): "  + cal.getFirstDayOfWeek());
	   //System.out.println(cal.add(field, amount)

	   	String startDate = dtFormat.format(Calendar.getInstance().getTime());
	   //	 System.out.println("cal.getTime(): "+ cal.getTime());
	 //  	System.out.println("startDate: "+startDate);
//	   	cal.add(cal.MONTH, -(Integer.parseInt(terms)));
//	   	cal.add(cal.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_MONTH)-1));
//	   	startDate = dtFormat.format(cal.getTime());
//	   	System.out.println("startDate: "+startDate);
	   	System.out.println(dtFormat.format(dt));



	   	Calendar curCal = Calendar.getInstance();
	  //	curCal.add(curCal.DAY_OF_WEEK,-(curCal.get(Calendar.DAY_OF_MONTH)));
	   	curCal.add(curCal.DAY_OF_WEEK, -(curCal.get(Calendar.DAY_OF_MONTH)) +1);

	   	// nextMonth = curCal.add(curCal.MONTH, 1);
	    String firstDate = dtFormat.format(curCal.getTime());
	    	System.out.println("firstDate: "+ firstDate);
	//    curCal.add(curCal.getActualMaximum(field), 1);
	   //  endDate = dtFormat.format(curCal.getTime());
    	System.out.println("endDate: "+ curCal.getActualMaximum ( Calendar.DATE ));
    	System.out.println("endDate: "+ curCal.getActualMinimum ( Calendar.DATE ));
    	int year = curCal.get(curCal.YEAR);
    	System.out.println("year: " + year);

    	int month = curCal.get(curCal.MONTH);
    	System.out.println("month: " + month);

    	int last_date = curCal.getActualMaximum ( Calendar.DATE );
    	int first_date = curCal.getActualMinimum ( Calendar.DATE );
    //	Date date = new Date(year, month, day)
    	Calendar cal1 = new GregorianCalendar(year, month, first_date);
    	String fDate = dtFormat.format(cal1.getTime());

	    	System.out.println("firstDate: "+ fDate);
	    	Calendar cal2 = new GregorianCalendar(year, month, last_date);
	    	String lDate = dtFormat.format(cal2.getTime());
	    	System.out.println("lastDate: " + lDate);
    	*/
	//	System.out.println("getFirstDayOfMonth: " + getFirstDayOfMonth("20091201", "yyyy-MM-dd"));
	//	System.out.println("getLastDayOfMonth: " + getLastDayOfMonth("2009-12-03", "yyyy-MM-dd"));



	//	System.out.println("getFirstDayOfCurrentMonth: " + getFirstDayOfCurrentMonth( "yyyy-MM-dd"));
	//	System.out.println("getLastDayOfCurrentMonth: " + getLastDayOfCurrentMonth( "yyyy-MM-dd"));
	//	Date date = new Date();
	//	System.out.println("getAfterMonth: " + getAfterMonth(new Date(), 1));
		//String str = "adf\"'";



		//System.out.println(str.replaceAll("\"", "'"));


		//유효시작일 구하기
//		Date vaildStartDate = addDay(getDate("2016-11-30", DateUtil.yyyyMMdd_DASH), 5);
//		System.out.println("유효시작일: " + vaildStartDate);
//
//
//		//특정월수를 더한 달의 마지막 날짜 구하기.(소멸예정일 구하기)
//		Date lastDate = getMonthLastDate( vaildStartDate, 12);
//		System.out.println("소명예정일: " + lastDate);


	//	String test_date = "20170120";
	//	System.out.println(getDate(test_date, DateUtil.yyyyMMdd));

	}




	/**
	 * 날짜 String을 Date로 변환해서 리턴한다. (DEFAULT_DATE_FORMAT을 사용)
	 * @author knlee
	 * @param date
     * @return 날짜 String을 Date로 변환해서 리턴한다. 변환도중 error발생시 null을 리턴한다.
     */
	public synchronized final static Date getDate(String date) {
		return getDate(ICNStringUtility.extNumber(date), "yyyyMMdd");
	}


}
