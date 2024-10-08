package slkcalendar;

public class DateModel extends DateMgr 
{
	DatePanel workPane;
	int mode = 0;
    public DateModel(DatePanel dp)
    {
    	super();
    	workPane = dp;
    	mode = 1;
    }
        
    public void doDayAction(int d, int m, int y, int idx)
    {
       workPane.setDateField(d, m, y, idx);
	}
}
