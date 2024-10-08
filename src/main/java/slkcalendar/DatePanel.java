/*
 * Date Calculator Panel 
 * Author GY
 */

package slkcalendar;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import guiwidget.XYLayout;

public class DatePanel extends JPanel implements ActionListener
{
	static public final long serialVersionUID = 123456789;	

	//component
	private YearChooser yearCh, yearCh1;
	private MonthChooser monthCh, monthCh1;
	private DayChooser dayCh, dayCh1;
	private DateMgr dateMgr, dateMgr1;
	//private JRadioButton startRBtn, endRBtn;
	private JButton endBtn, daysBtn, addBtn, deleteBtn;
	private JTextField totalDays, dateStart, dateEnd,  weekday, weekend, holiday;
	
	private SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy/MM/dd");
	private SimpleDateFormat displayFmt  
	= new SimpleDateFormat("EEEEEEEE, MMMMMMMMM dd, ''yyyy"); 
	//private SimpleDateFormat displayFmt 
	//= new SimpleDateFormat("EEE, MMMM dd, 'yyyy");
	
	private int year, month, selectedDay;//year1, month1, selectDay1;
	private Date today, startDate, endDate;
	//KitFrame parent;
	JFrame parent;
	//public DatePanel(KitFrame frm )
	public DatePanel(JFrame frm )
	{
		parent = frm;
		today = new Date();
		startDate = new Date();
		String ds = dateFmt.format(today);
		StringTokenizer tk = new StringTokenizer(ds, "/");
		year = Integer.parseInt(tk.nextToken());
		month  = Integer.parseInt(tk.nextToken());
		selectedDay  = Integer.parseInt(tk.nextToken());
		dateMgr = new DateModel(this);
		dateMgr1 = new DateModel(this);
		dateMgr1.setIndex(1);
		initPane();
		setDatePane();
		setEndDatePane( );
	}
	
	void initPane( )
	{
		setLayout(new XYLayout());
		setBorder(BorderFactory.createEtchedBorder());
		
		JLabel startLb = new JLabel("Start Date:", JLabel.RIGHT);
		startLb.setBounds(10, 5, 70, 25);
		add(startLb);
		dateStart = new JTextField();
		setFieldProperty(dateStart);
		dateStart.setEditable(false);
		dateStart.setText(displayFmt.format(today));
		dateStart.setBounds(85, 5, 210, 25);
		add(dateStart);
		
		 //-------------- set day chose------	
		dayCh = new DayChooser(month, year);
		dayCh.setManager(dateMgr);
		dayCh.setBounds(5, 30, 290, 200);
		dayCh.initPane();
		dayCh.renderDay();
		add(dayCh);
		//-------- set month chooser----
		monthCh = new MonthChooser(month);
		monthCh.setManager(dateMgr);
		monthCh.setBounds(30, 240, 200, 40);
		add(monthCh);
		yearCh = new YearChooser(year);
		yearCh.setManager(dateMgr);
		yearCh.setBounds(30, 285, 200, 40);
		add(yearCh);
		JSeparator sp = new JSeparator(SwingConstants.VERTICAL);
		sp.setBounds(305, 0, 5, 340);
		add(sp);
	}
	
	public void setDatePane( )
	{
		JLabel dateTitle = new JLabel("Days between Start/End Date", JLabel.CENTER);
		setLabelProperty(dateTitle);
		dateTitle.setBounds(310, 5, 360, 25);
		add(dateTitle);
		setDayPane();
		setButtons();
		JSeparator sp = new JSeparator(SwingConstants.VERTICAL);
		sp.setBounds(670, 0, 5, 340);
		add(sp);
		setDayPane();
		
	}
	
	void setDayPane()
	{
		JLabel dayLb = new JLabel("Total days:", JLabel.RIGHT);
		setLabelProperty(dayLb);
		dayLb.setBounds(320, 35, 150, 25);
		totalDays = new JTextField();
		setFieldProperty(totalDays);
		totalDays.setBounds(475, 35, 180, 25);
		JLabel incLb = new JLabel("Including following:");
		setLabelProperty(incLb);
		incLb.setBounds(320, 65, 200, 25);
		
		JLabel wdLb = new JLabel("Days of Week:", JLabel.RIGHT);
		setLabelProperty(wdLb);
		wdLb.setBounds(320, 95, 180, 25);
		weekday = new JTextField();
		setFieldProperty(weekday);
		weekday.setEditable(false);
		weekday.setBounds(505, 95, 150, 25);
		
		JLabel wkLb = new JLabel("Days of Weekend:", JLabel.RIGHT);
		setLabelProperty(wkLb);
		wkLb.setBounds(320, 125, 180, 25);
		weekend = new JTextField();
		setFieldProperty(weekday);
		weekend.setEditable(false);
		weekend.setBounds(505, 125, 150, 25);
		
		JLabel hLb = new JLabel("Days of Public Holiday:", JLabel.RIGHT);
		setLabelProperty(hLb);
		hLb.setBounds(320, 155, 180, 25);
		holiday = new JTextField();
		setFieldProperty(holiday);
		holiday.setEditable(false);
		holiday.setBounds(505, 155, 150, 25);
		
		add(dayLb);
		add(totalDays);
		add(incLb);
		add(wdLb);
		add(weekday);
		add(wkLb);
		add(weekend);
		add(hLb);
		add(holiday);
		
		endBtn =new JButton("get End Date");
		endBtn.setActionCommand("GetEndDate");
		endBtn.setBounds(330, 190, 150, 25);
		daysBtn = new JButton("get Days Between");
		daysBtn.setActionCommand("GetDays");
		daysBtn.setBounds(490, 190, 150, 25);
		add(endBtn);
		add(daysBtn);
		
		JSeparator sp = new JSeparator();
		sp.setBounds(310, 220, 360, 5);
		add(sp);
		
	}
	
	void setButtons()
	{
		JLabel dayTitle = new JLabel("Fixed Public Holidays(month/day-name):");
		setLabelProperty(dayTitle);
		dayTitle.setBounds(320, 230, 330, 25);
		
		//may be a table
				
		addBtn = new JButton("Add");
		addBtn.setBounds(560, 260, 80, 25);
		deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(560, 290, 80, 25);
	
		add(dayTitle);
		add(addBtn);
		add(deleteBtn);
		
		endBtn.addActionListener(this);
		daysBtn.addActionListener(this);
		addBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
	}
	
	void setEndDatePane( )
	{
		setLayout(new XYLayout());
		setBorder(BorderFactory.createEtchedBorder());
		
		JLabel endLb = new JLabel("End Date:", JLabel.RIGHT);
		endLb.setBounds(680, 5, 70, 25);
		add(endLb);
		dateEnd = new JTextField();
		setFieldProperty(dateEnd);
		dateEnd.setEditable(false);
		dateEnd.setText(displayFmt.format(today));
		dateEnd.setBounds(755, 5, 210, 25);
		add(dateEnd);
		
	   //-------------- set day chose------	
		dayCh1 = new DayChooser(month, year);
		dayCh1.setManager(dateMgr1);
		dayCh1.setBounds(680, 30, 290, 200);
		dayCh1.initPane();
		dayCh1.renderDay();
		add(dayCh1);
		//-------- set month chooser----
		monthCh1 = new MonthChooser(month);
		monthCh1.setManager(dateMgr1);
		monthCh1.setBounds(700, 240, 200, 40);
		add(monthCh1);
		yearCh1 = new YearChooser(year);
		yearCh1.setManager(dateMgr1);
		yearCh1.setBounds(700, 285, 200, 40);
		add(yearCh1);
	} 
	
	public void setLabelProperty(JLabel lb)
	{
		lb.setFont(new Font("Helvetica", Font.BOLD, 14));
	}
	
	public void setFieldProperty(JTextField lb)
	{
		lb.setFont(new Font("Helvetica", Font.BOLD, 14));
		//lb.setBackground(UKDiaryPanel.lightBlue);
		lb.setBackground(Color.white);
		//lb.setBorder(BorderFactory.createEtchedBorder());
		lb.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void actionPerformed(ActionEvent ev)
	{
		String cmd = ev.getActionCommand();
		System.out.println("Cmd="+cmd);
		if(cmd.equals("GetEndDate"))
			calEndDate();
		else if(cmd.equals("GetDays"))
			calDays( );
		
	}
	
	//--------------manipulate function---------------------
	
	//return yyyy/MM/dd
	private String toDateString(int d, int m, int y)
	{
		String s1 = Integer.toString(y);
		String s2 = Integer.toString(m);
		if(m <10) s2 = "0"+s2;
		String s3 = Integer.toString(d);
		if(d<10) s3 = "0"+s3;
		String rs = s1+"/"+s2+"/"+s3;
		return rs;
	}
	
	public void setDateField(int d, int m, int y, int idx)
	{
		selectedDay = d; month = m; year = y;
		Date dt= new Date(); 
		try
		{  
			dt = dateFmt.parse(toDateString(d, m, y));
		}catch(Exception ex)
		{ System.out.println("Warning setDateField: "+ex.toString()); }
		
		//System.out.println("DatePanel> "+dt.toString());
		
	
		if(idx == 1)
		{
			dateEnd.setText(displayFmt.format(dt));
			endBtn.setEnabled(false);
			daysBtn.setEnabled(true);
			endDate = dt;
		}else
		{
			dateStart.setText(displayFmt.format(dt));
			endBtn.setEnabled(true);
			startDate = dt;
		}
			
	}
	
	void calEndDate()
	{
		try
		{
			String s1 = totalDays.getText();
		    long days1 = Long.parseLong(s1);
			Date endt = dateMgr.calEndDate(startDate, days1);
			dateEnd.setText(displayFmt.format(endt));
			endDate = endt;
			long wkd = getWeekDay();
			weekday.setText(Long.toString(wkd));
			setFieldProperty(weekday);
			weekend.setText(Long.toString(days1-wkd));
			setFieldProperty(weekend);
		}catch(Exception ex)
		{System.out.println("Warning calEndDate: "+ex.toString()); }
	}
	
	void calDays()
	{
		try
		{
			long days1 = dateMgr.calDayInterval(startDate, endDate);
			totalDays.setText(Long.toString(days1));
			long wkd = getWeekDay();
			weekday.setText(Long.toString(wkd));
			setFieldProperty(weekday);
			weekend.setText(Long.toString(days1-wkd));
			setFieldProperty(weekend);
		}catch(Exception ex)
		{System.out.println("Warning calEndDate: "+ex.toString()); }
	}
	
	long getWeekDay( )
	{
		Calendar c1 = dateMgr.getDateCalendar(startDate);
		Calendar c2 = dateMgr.getDateCalendar(endDate);
		return  dateMgr.calWeekDay(c1,  c2);
	}
	
	public void perform(String cmd, Object parm)
	{
		
	}
	
	static public void main(String args[])
	{
		JFrame frm = new JFrame("Yeear tester");
		frm.setBounds(10, 20, 980, 450);
		//frm.getContentPane().setLayout(new GridLayout(1,1));
		frm.getContentPane().setLayout(null);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		DatePanel  udPane = new DatePanel(frm);
		udPane.setBounds(10, 20, 950, 340);
		frm.getContentPane().add(udPane);
		frm.setVisible(true);
	}

}
