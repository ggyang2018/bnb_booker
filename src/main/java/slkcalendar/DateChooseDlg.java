package slkcalendar;
/* Choose a day based selecte month and year
 * @Author Guang yang
 */

import guiwidget.XYLayout;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class DateChooseDlg extends JDialog
{
	static public final long serialVersionUID = DayChooser.serialVersionUID;
	DayChooser dayCh;
	MonthChooser monthCh;
	YearChooser yearCh;
	int month, year, day;
	JButton selectBtn, cancelBtn;
	Date selectDate = null;
	int mode;
	ActAdapt adpt;
	
	Date initDate;
	
	public DateChooseDlg(JFrame own, int m, int y)
	{
		super(own);
		setModal(true);
		month = m; year = y;
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{    
				dispose(); 
				setVisible(false);
			}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = new Double(dim.getWidth());
		        Double h = new Double(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		initDlg();
	}
	
	public DateChooseDlg(JDialog own, int m, int y)
	{
		super(own);
		setModal(true);
		month = m; year = y;
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{   
				setChooseDate(selectDate);
				dispose(); 
				setVisible(false);
			}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = new Double(dim.getWidth());
		        Double h = new Double(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		
		initDlg();
	}
	
	void initDlg()
	{
		getContentPane().setLayout(new XYLayout());		
		yearCh = new YearChooser(year);
		//yearCh.setManager(dateMgr);
		yearCh.setBounds(30, 0, 200, 30);		
		monthCh = new MonthChooser(month);
		//monthCh.setManager(dateMgr);
		monthCh.setBounds(30, 31, 200, 30);				
		dayCh = new DayChooser(month, year);
		//dayCh.setManager(dateMgr);
		dayCh.setBounds(0, 62, 280, 190);
		
		yearCh.setDayChooser(dayCh);
		monthCh.setDayChooser(dayCh);
		dayCh.setParent(this);
		dayCh.initPane();
		dayCh.renderDay();		
		getContentPane().add(monthCh);
		getContentPane().add(yearCh);
		getContentPane().add(dayCh);
		/*selectBtn = new JButton("Select");
		selectBtn.setActionCommand("SelectDay");
		selectBtn.setBounds(10, 230, 80, 20);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("CancelDay");
		cancelBtn.setBounds(100, 230, 80, 20);
		getContentPane().add(selectBtn);
		getContentPane().add(cancelBtn);
		*/
		setBounds(100, 100, 300, 300);		
	}
	
    void setChooseDate(Date dt)
    { 
    	selectDate = dt;
    	adpt.setSelectDate(dt, mode);
    }
    public Date getSelectDate() { return selectDate; }
    public void setAdaptor(ActAdapt ad){ adpt = ad; }
    public void setMode(int md) {  mode = md; }
    public void setInitDate(Date dt) { initDate =dt; }
      
}
