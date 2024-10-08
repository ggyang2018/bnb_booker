package bbooker;

/* RoomBook is a year month specified Room book object
 * composite key yyyy:mm and roomId, store in CSV file 
 * @Author Sunny
 */
import java.util.StringTokenizer;
import java.util.Arrays;

public class RoomBook 
{
	String roomId;
	String customerId; //custome Id
	String yymmId; //format yyyy:mm
	int year, month;
	String expectDays; //1-2-3-4
	String takeDays; //1-2-3-4
	int expDays[]; int expSize =0;
	int tkDays[]; int tkSize = 0;
	
	
	
	 
	   
	   String  bookDate;
	   String expectArriveTime;
	   String visitReason;
	   boolean eveningMeal;
	   String dietRequirement;
	   String groupName;
	   int    nbrGroup;
	   
	   String bookRoomIds[]; //fixed length dependes how many rooms
	   
	
	
	public RoomBook(String rmid) 
	{ 
		roomId = rmid;
		expDays = new int[31];
		tkDays = new int[31];
		Arrays.fill(expDays, 0);
		Arrays.fill(tkDays, 0);
	}
	
	public String getRoomId() { return roomId; }
	public String getYYMMId() { return yymmId; }
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int[] getExpectDays()
	{
		int exd[] = new int[expSize];
		int j=0;
		for(int i=0; i<31; i++)
		{
			if(expDays[i] !=0)
			{
				exd[j] = expDays[i];
				j++;
			}
		}
		return exd;
	}
	
	public int[] getTakeDays()
	{
		int tkd[] = new int[tkSize];
		int j=0;
		for(int i=0; i<31; i++)
		{
			if(tkDays[i] !=0)
			{
				tkd[j] = tkDays[i];
				j++;
			}
		}
		return tkd;
	}
	
	public void setCustomerId(String cid){ customerId = cid; }
	
	public String getCustomerId() { return customerId; }
   //---------- set methods --------------------------------	
	public void setYYMMId(String yymm) 
	{
		StringTokenizer tk = new StringTokenizer(yymm, ":");
		try
		{
			year = Integer.parseInt(tk.nextToken());
			month = Integer.parseInt(tk.nextToken());
			yymmId = yymm;
		}catch(Exception ex) { ex.printStackTrace(); }		
	}
	
	public void setYYMMId(int yy, int mm)
	{
		year = yy; month = mm;
		if(mm<10)
			yymmId = Integer.toString(yy)+":0"+Integer.toString(mm);
		else
			yymmId = Integer.toString(yy)+":"+Integer.toString(mm);
	}
	
	public void addExpectDay(int dt)
	{
		int d1 = expDays[dt-1];
		expDays[dt-1] = dt;
		if(d1 ==0) expSize++; 
	}
	public void addTakeDay(int dt)
	{   
		int d1 = tkDays[dt-1];
		tkDays[dt-1] = dt; 
		if(d1==0)tkSize++; 
	}
	public void rmExpectDay(int dt)
	{ 
		int d1 = expDays[dt-1];
		if(d1 !=0){ expDays[dt-1]=0; expSize--; }
	}
	public void rmTakeDay(int dt) 
	{ 
		int d1 = tkDays[dt-1];
		if(d1 !=0){ tkDays[dt-1]=0; tkSize--; }
	}
	
	public String toString()
	{
		StringBuffer bf = new StringBuffer();
		bf.append(roomId+", ");
		bf.append(yymmId+", ");
		int[] exd = getExpectDays();
		bf.append(Integer.toString(exd[0]));
		for(int i=1; i<expSize; i++)
			bf.append("-"+Integer.toString(exd[i]));
		bf.append(", ");
		int tkd[] = getTakeDays();
		bf.append(Integer.toString(tkd[0]));
		for(int i=1; i<tkSize; i++)
			bf.append("-"+Integer.toString(tkd[i]));
		bf.append(", ");
		bf.append(customerId);
		return bf.toString();
	}
	
	public void loadString(String row) throws Exception
	{
		StringTokenizer tk = new StringTokenizer(row, ", ");
	    roomId = tk.nextToken();
	    setYYMMId(tk.nextToken());
	    StringTokenizer tk1 = new StringTokenizer(tk.nextToken(), "-");
	    while(tk1.hasMoreTokens())
	        addExpectDay(Integer.parseInt(tk1.nextToken()));
	    
	    StringTokenizer tk2 = new StringTokenizer(tk.nextToken(), "-");
	    while(tk2.hasMoreTokens())
	        addTakeDay(Integer.parseInt(tk2.nextToken()));
	    setCustomerId(tk.nextToken());	    
	}
}
