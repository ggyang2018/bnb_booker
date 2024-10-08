
package slkcalendar;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import guiwidget.XYLayout;

/*
 * Output month or month action based on user selection. 
 * Author GY
 */

public class MonthChooser extends JPanel implements ActionListener
{
	static public final long serialVersionUID = 123456789;	
	
	int thisMonth; //number form 1 -12;
	JButton  plusBtn, minuBtn;
	JComboBox monthList; 
	Insets plusIns = new Insets(2, 3, 3, 2);
	Insets minuIns = new Insets(2, 3, 3, 2);
	
	private String[] months = {"January", "February", 
								"March", "April", "May", "June", 
								"July",  "August", "September", 
								"October", "November", "December"};
	DayChooser dayCh;
	DateMgr mgr;
	int mode =0;
	//1--12
	public MonthChooser(int month)//must be four digital 
	{//initial data
		thisMonth = verifyMonth(month);
		initPane();
	}
	
	public void initPane( )
	{   //prefer size 160 X 35
		setLayout(new XYLayout());
		//setBackground(DateBridge.lightBlue);
		setBorder(null);
		minuBtn = new JButton("<");
		minuBtn.setMargin(minuIns);
		minuBtn.setBounds(5, 5, 25, 20);
		minuBtn.setFocusPainted(false);
		
		monthList = new JComboBox(months);
		monthList.setBounds(33, 3, 90, 23);
		monthList.setActionCommand("Month");
		monthList.setSelectedIndex(thisMonth-1);
		
		plusBtn = new JButton(">");
	    plusBtn.setMargin(plusIns);
		plusBtn.setBounds(125, 5, 25, 20);
		plusBtn.setFocusPainted(false);
		
		add(minuBtn);
		add(monthList);
		add(plusBtn);
		
		minuBtn.addActionListener(this);
		plusBtn.addActionListener(this);
		monthList.addActionListener(this);
	}
	
	public int getThisMonth() { return thisMonth; }
	
	//boolean acting = true;
	public void actionPerformed(ActionEvent evt)
	{
		String cmd = evt.getActionCommand();
		if(cmd.equals("<"))
		{
			thisMonth--;
			thisMonth = verifyMonth(thisMonth);
			monthList.setSelectedIndex(thisMonth-1);
			doAction(thisMonth);
		}else if(cmd.equals(">"))
		{
			thisMonth++;
			thisMonth = verifyMonth(thisMonth);
			monthList.setSelectedIndex(thisMonth-1);
			doAction(thisMonth);
		}else if(cmd.equals("Month"))
		{
			int idx1= monthList.getSelectedIndex();
			thisMonth = idx1 +1;
			doAction(thisMonth);
    	}
	}
	//may call it parent to do action may abstract. 
	//just rewrite this function. 
	public void doAction(int m)
	{ 
		if(mode==0)
			dayCh.setMonth(m);
		else if(mode==1)
			mgr.actMonth(m);
	}
	public int verifyMonth(int m)
	{
		int m1 = m;
		if(m <1) m1=1;
		else if(m >12) m1 = 12;
		return m1;
	}
	protected void setDayChooser(DayChooser dc) { mode = 0; dayCh = dc; }
	public void setManager(DateMgr mg) 
	{ 
		mode =1;
		mgr = mg;
		mgr.setMonthChooser(this);
	}

}
