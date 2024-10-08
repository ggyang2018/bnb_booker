package slkcalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateBuz
{
	SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy:MM:dd");
	String key; //in frmt1 format
	List<String> vals;
	public DateBuz() { vals = new ArrayList<String>(); }
	
	public void setKey(Date dt) { key = fmt1.format(dt); }
	public void setKey(int y, int m, int d)
	{ key = Integer.toString(y)+":"+Integer.toString(m)+":"+Integer.toString(d);}
	
	public String getKey() { return key; }
	public Date getKeyDate()throws ParseException  { return fmt1.parse(key); }
	public int[] getKeyYYMMDD()
	{
		StringTokenizer tk = new StringTokenizer(key, ":");
		int ymd[] = new int[3];
		ymd[0] = Integer.parseInt(tk.nextToken());
		ymd[1] = Integer.parseInt(tk.nextToken());
		ymd[2] = Integer.parseInt(tk.nextToken());
		
		return ymd;
	}
	
	

}
