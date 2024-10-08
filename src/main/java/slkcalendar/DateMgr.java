/*
 * DateMgr manager all component and prividate a functional interface to external
 * caller. 
 * Author GY. 
 */
package slkcalendar;

import java.util.*;
import java.text.SimpleDateFormat;


public class DateMgr
{
	SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy/MM/dd");
	int month, year, selectedDay;
	//only take reference not create
	ActAdapt dayChooser;
	MonthChooser monthChooser;
	YearChooser  yearChooser;
	int index = 0;
	
	public DateMgr()
	{
		month =1; year=2006; selectedDay=1;
	}
	public void setIndex(int idx) { index = idx; }
	public void setDayAction(ActAdapt ch){ dayChooser = ch; }
	public void setMonthChooser(MonthChooser ch){  monthChooser = ch; }
	public void setYearChooser(YearChooser ch){   yearChooser = ch; }
	
	public void actYear(int y)
	{
		year = y;
		dayChooser.setYear(y);
	}
	
	public void actMonth(int m)
	{
		month = m;
		dayChooser.setMonth(m);
	}
	
	public void actDay(int d, int m, int y)
	{
		//System.out.println("DateMgr: "+d+m+y);
		doDayAction(d, m, y, index);
	}
	
//	------------- Date calculator -----------------------------------------
 	public Calendar getDateCalendar(Date dat)
 	{
		Calendar c1 = new GregorianCalendar();
		c1.setTime(dat);
		return c1;
	}

	public Date getCalendarDate(Calendar ca) { return ca.getTime(); }

    public Date calEndDate(Date start, long days)
    {
		long start1 = start.getTime();
		long day_time = days * (1000*60*60*24);
		long end1 = start1 + day_time;
		Date  dat1 = new Date(end1);
		return dat1;
	}

    public long calDayInterval(Calendar start, Calendar end)
    {
		return calDayInterval(start.getTime(), end.getTime());
	}

    public long calDayInterval(Date start, Date end)
    {

		long dif = end.getTime()-start.getTime();
	    long days = Math.round((dif/(1000*60*60*24)));
		return days;
	}


    //calculate day not excludes start day but includes end day.
	public long calWeekDay(Calendar c1, Calendar c2)
	{
		//Sunday = 1; start as 1
		long totalDif = calDayInterval(c1, c2);
		int weekDay1 = c1.get(Calendar.DAY_OF_WEEK);
		//System.out.println("DAY_OF_WEEK: " + weekDay1);
		int weekDay2 = c2.get(Calendar.DAY_OF_WEEK);
		//System.out.println("DAY_OF_WEEK: " + weekDay2);

		long rst = (totalDif-weekDay2+weekDay1-8)*5/7
		   - Math.max(-2, -weekDay1)-Math.min(1, weekDay2)+5-weekDay1+weekDay2;
		return rst;
	}

    public void calDayInterval(int y0, int m0, int d0, int y1, int m1, int d1 )
    {
		long from = new GregorianCalendar(y0, m0, d0).getTime().getTime();
		long to = new  GregorianCalendar(y1, m1, d1).getTime().getTime();
		double difference = to - from;
		long days = Math.round((difference/(1000*60*60*24)));
        System.out.println(days);
	}
    
    public void testDateCalculator( )
    {
		Calendar c1 = new GregorianCalendar();;
		c1.set(2006, 1, 20); //month form 0 -11 not 1-12
		System.out.println("start long="+c1.getTime().getTime());
		System.out.println("start="+c1.getTime().toString());
		Calendar c2 = new GregorianCalendar();
		c2.set(2006, 2, 5);
		System.out.println("end long="+c2.getTime().getTime());
		System.out.println("end="+c2.getTime().toString());

		long days = calDayInterval(c1, c2);
		System.out.println("days = "+Long.toString(days));

		Date end1 = calEndDate(getCalendarDate(c1), days);
		System.out.println("cal end long="+end1.getTime());
		System.out.println("End date="+fmt1.format(end1));

		long weekDays = calWeekDay(c1, c2);
	    System.out.println("WeekDays="+weekDays);
	}
    
    // application specified action
    public void doDayAction(int d, int m, int y, int mod)
    {
    	
    }
}
