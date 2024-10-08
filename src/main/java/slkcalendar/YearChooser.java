
/*
 * output a selected year number. by call getThisYear() 
 * It's subclass will be in container and call ohters
 */

package slkcalendar;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import guiwidget.XYLayout;

public class YearChooser extends JPanel implements ActionListener
{
	static public final long serialVersionUID = 123456789;	
	
	int maxYear, minYear, thisYear;
	JButton yearBtn, plusBtn, minuBtn;
	Insets plusIns = new Insets(2, 3, 3, 2);
	Insets minuIns = new Insets(0, 3, 12, 2);
	DayChooser dayCh;
	DateMgr mgr;
	int mode =0;
	public YearChooser(int year)//must be four digital 
	{//initial data
		maxYear = 2501;
		minYear = 1899;
		thisYear = verifyYear(year);
		initPane();
	}
	
	public void initPane( )
	{   //prefer size 160 X 35
		setLayout(new XYLayout());
		setBorder(null);
		//setBackground(DateBridge.lightBlue);
		minuBtn = new JButton("__");
		minuBtn.setMargin(minuIns);
		minuBtn.setBounds(5, 5, 25, 20);
		minuBtn.setFocusPainted(false);
		
		yearBtn = new JButton(Integer.toString(thisYear));
		yearBtn.setFocusPainted(false);
		yearBtn.setBounds(33, 3, 80, 23);
		yearBtn.setActionCommand("Year");
		
		plusBtn = new JButton("+");
	    plusBtn.setMargin(plusIns);
		plusBtn.setBounds(115, 5, 25, 20);
		plusBtn.setFocusPainted(false);
		
		add(minuBtn);
		add(yearBtn);
		add(plusBtn);
		
		minuBtn.addActionListener(this);
		plusBtn.addActionListener(this);
		yearBtn.addActionListener(this);
	}
	
	public int getThisYear() { return thisYear; }
	
	public void actionPerformed(ActionEvent evt)
	{
		String cmd = evt.getActionCommand();
		if(cmd.equals("__"))
		{
			thisYear--;
			thisYear = verifyYear(thisYear);
			yearBtn.setText(Integer.toString(thisYear));
			doAction(thisYear);
		}else if(cmd.equals("+"))
		{
			thisYear++;
			thisYear = verifyYear(thisYear);
			yearBtn.setText(Integer.toString(thisYear));
			doAction(thisYear);
		}else if(cmd.equals("Year"))
			doAction(thisYear); 
		
	}
	
	//may call it parent to do action may abstract. 
	//just rewrite this function. 
	public void doAction(int y)
	{ 
		if(mode==0)
			dayCh.setYear(y);
		else if(mode==1)
			mgr.actYear(y);
	}		
	private int verifyYear(int y)
	{
		int y1 = y;
		if(y >= maxYear) y1 = maxYear;
		else if (y <=minYear) y1 = minYear;
		return y1;
	}
	//override function	
	protected void setDayChooser(DayChooser dc) { mode = 0; dayCh = dc; }
	public void setManager(DateMgr mg) 
	{ 
		mode = 1;
		mgr = mg;
		mgr.setYearChooser(this);
	}

}
