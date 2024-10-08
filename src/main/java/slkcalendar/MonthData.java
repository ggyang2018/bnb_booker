package slkcalendar;
/* Month data contains a month data row number of object key/description
 * key yyyy:mm + :dd
 * @Guang Yang 
 */
import nxd.DSDataMatrix;

public class MonthData extends DSDataMatrix<String>
{
	int month, year;
	int selectDay;
	int fieldNbr, monthDays;
	
    public MonthData(int mt, int yr, int nbrFd)
    {
    	super();
    	month = verifyMonth(mt);
    	year = verifyYear(yr);
    	monthDays = getMonthDays(month, year);
    	fieldNbr = nbrFd;
    	setMatrixId(Integer.toString(year)+":"+Integer.toString(month));   	 	
    }
    public String[][] makeMatrix(int rw, int col)
    {
    	String data[][] = new String[monthDays][fieldNbr];
    	return data;
    }      
    
    public int getMonthDays(){ return monthDays; }
    public int getYear() { return year; }
    public int getMonth(){ return month; }
    
    //------- support-----------------------
    private int getMonthDays (int month, int year)	//monve to ZZCDate on ulysses
	{
        if ((month <1) || (month>12))
          return -1;
        switch (month)
		{
           case 4:		//April
           case 6:		//June
           case 9:		//Seprmber
           case 11:		//November
           return  30;
           case 1:		//January
           case 3:		//March
           case 5:		//May
           case 7:		//July
           case 8:		//August
           case 10:		//October
           case 12:		//December
           return 31;
           default:		//February
		   {
              if ((year % 4) == 0)
			  {
                   if ((year % 100) ==0)
				   {
                      if ((year%400) ==0)
	                      return 29;
                      else
  	                      return 28;
				    } else
	                    return 29;
			   }
               else return 28;
		   }
		}
	}
	
	private int verifyMonth(int m)
	{
		int m1 = m;
		if(m <1) m1=1;
		else if(m >12) m1 = 12;
		return m1;
	}
	
	private int verifyYear(int y)
	{
		int y1 = y;
		if(y >= 2500) y1 = 2500;
		else if (y <=1899) y1 = 1899;
		return y1;
	}
}
