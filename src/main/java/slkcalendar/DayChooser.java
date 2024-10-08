/*
 * Diaplay day with specified year and month
 * output day yyyy/MM/dd or object. 
 */

package slkcalendar;

import javax.swing.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
//import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import guiwidget.XYLayout;

public class DayChooser extends JPanel implements ActionListener,ActAdapt
{
	static public final long serialVersionUID = 123456789;	
	int year, month, selectedDay, offset;
	Calendar calendar;
	Date selectedDate;
	
	JPanel weekPane, dayPane;
	JButton dayBtns[] = new JButton[42]; //6x7
	String[]  weekDesc ={"Sun", "Mon", "Tue",  "Wed",  
            					  "Thu",  "Fri", "Sat"};
    Insets  mg1 =  new Insets(2, 1,  2, 1); 
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat outFmt=new SimpleDateFormat("yyyy/MM/dd");
    
    //appearance
    Color selectedDayColor = new Color(0, 255, 64);
    Color dayBkColor = Color.white;
    Color dayTxtColor = Color.black;
    Color borderColor = Color.black;
    
    DateMgr mgr;
    DateChooseDlg dateDlg;
    int cellRow, cellCol;
	
	public DayChooser(int m, int y)
	{
		year = verifyYear(y);
		month = verifyMonth(m);
		calendar = new GregorianCalendar();
	}
	
	public void initPane( )
	{   //310 x 200
		setLayout(new XYLayout());
		weekPane = new JPanel();
		weekPane.setLayout(new GridLayout(0, 7));
		for(int i=0; i<weekDesc.length; i++)
			weekPane.add(new JLabel(weekDesc[i], JLabel.CENTER));
		weekPane.setBounds(5, 0, 280, 20);
		weekPane.setBorder(null);
		add(weekPane);
		
		dayPane = new JPanel();
		//dayPane.setBackground(DateBridge.lightBlue);
		dayPane.setLayout(new GridLayout(6,7));
		for(int j=0; j<42; j++)
		{
			dayBtns[j] = new JButton("");
			dayBtns[j].setMargin(mg1);
			dayBtns[j].setBorderPainted(false);
			dayBtns[j].addActionListener(this);
			dayBtns[j].setFocusPainted(false);
			dayPane.add(dayBtns[j]);
		}
		
		dayPane.setBounds(5, 22, 280, 170);
		dayPane.setBorder(BorderFactory.createLineBorder(borderColor));
		add(dayPane);
	}
	
	public void renderDay( )
	{
		Date monthDay1 = parseDate(1, month, year);
		calendar.setTime(monthDay1);
		int weekDay1 = calendar.get(Calendar.DAY_OF_WEEK);
		offset =  weekDay1 - 2;
		int nbrDays = getMonthDays(month, year);
		
		int j= 1;
		for(int i=0; i<42; i++)
		{
			dayBtns[i].setBackground(dayBkColor);
			if( (i+1)<weekDay1 || j>nbrDays)
			{
				dayBtns[i].setText("");
				dayBtns[i].setEnabled(false);
			}else if(j<=nbrDays)
			{
				dayBtns[i].setText(Integer.toString(j));
				dayBtns[i].setForeground(dayTxtColor);
				dayBtns[i].setEnabled(true);
				j++;
			}
		}
     }
	
	public void actionPerformed(ActionEvent ave)
	{
		String cmd = ave.getActionCommand();
		//System.out.println("Day action="+cmd);
		try
		{
			int oldDay = selectedDay; //keep copy last selection 
			selectedDay = Integer.parseInt(cmd);
			selectedDate = getSelectedDay(oldDay, selectedDay);
			//System.out.println("selecte date="+selectedDate.toString());
			doAction();
		}catch(Exception ex)
		{ System.out.println("Waring: parse day string: "+ex.toString()); }
	}
	
	public void setManager(DateMgr mg)
	{ 
		mgr = mg;
		mgr.setDayAction(this);
	}
	public Date getSelectedDate( ) { return selectedDate; }
	
	public void doAction( ){ dateDlg.setChooseDate(selectedDate);}
	//---------- adapt implementation ----------------
	public void setYear(int y)
	{
		year = verifyYear(y);
		renderDay();
	}
	
	public void setMonth(int m)
	{
		month = verifyMonth(m);
		renderDay();
	}
	
	public void setDateAct(int row, int fld, JButton cellBtn)
	{
		
	}
	// ------- end of adapt implementation------------
	
	public void setMonthYear(int m, int y)
	{
		year = verifyYear(y);
		month = verifyMonth(m);
		renderDay();
	}
	
	  //day 1-31, m 1-12, y-2000
	private Date parseDate (int d, int m, int y)
	{
	    Date dt;
	    try
		{
	       dt = sdf.parse ( Integer.toString(d) + "/"
		                 + Integer.toString(m) + "/"
		                 + Integer.toString(y));
		}catch (ParseException pe)
		{   
			dt = null;
			System.out.println("Calendar.parseDate: pe="+pe.toString());
		}
	    return  dt;
	 }
	
	Date getSelectedDay(int oldDay, int newDay)
	{
		if(oldDay >0)
			dayBtns[offset+oldDay].setBackground(dayBkColor);
		dayBtns[offset+newDay].setBackground(selectedDayColor);
		Date dt = parseDate(newDay, month, year);
		return dt;
	}
	
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
	
	int verifyMonth(int m)
	{
		int m1 = m;
		if(m <1) m1=1;
		else if(m >12) m1 = 12;
		return m1;
	}
	
	int verifyYear(int y)
	{
		int y1 = y;
		if(y >= 2500) y1 = 2500;
		else if (y <=1899) y1 = 1899;
		return y1;
	}
	
	public void setCellIdx(int row, int col) { cellRow = row; cellCol = col; }
	protected void setParent(DateChooseDlg dlg){ dateDlg = dlg;}
	public void setSelectDate(Date dt, int mode){}
	public List<String> getNames() { return new ArrayList<String>(); }
	
}
