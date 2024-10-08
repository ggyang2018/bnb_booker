package bbooker;

/* Book panel to enter a booking details
 * @Author Guang Yang
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import guiwidget.XYLayout;
import slkcalendar.*;

public class BookEditPane extends JPanel implements ActionListener,ActAdapt
{
	static public final long serialVersionUID = BookDlg.serialVersionUID;
	JTextField arriveFd, visitFd, startFd, daysFd, endFd,groupFd; 
	JTextField childFd, nbrFd;
	JComboBox payMtFd, bookSttBx, bookBx;
	BKEditorDlg  bookDlg = null;
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	
	String payMethes[] ={"Cash", "Credit Card", "Debit Card", "Cheque", "Others"};
	String bookStt1[]={"provisional", "confirmed", "taken", "cancel" };
	String bookMth[]={"Telephone","Email", "Letter", "Others"};
	
	BookDetail book;

	public BookEditPane(BKEditorDlg dlg)
	{
		bookDlg = dlg;
		setLayout(new XYLayout());
		Border aBorder = BorderFactory.createRaisedBevelBorder();
		setBorder(aBorder);
	}
	public void setBook(BookDetail bk) { book = bk; }
		
	public void initPane()
	{
	   int x0=5, y0=3, h=20, w=100, w1=110, off=3;
	   JLabel ptit = new JLabel("Booking Details", JLabel.CENTER);
	   ptit.setBounds(x0, y0, 220, h);
	   y0 = h+off;
	   JLabel grp = new JLabel("Group Name:", JLabel.RIGHT);
	   grp.setBounds(x0, y0, w, h);
	   groupFd = new JTextField();
	   groupFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+off+h;
	   JLabel grNbrLb = new JLabel("Adult Number:", JLabel.RIGHT);
	   grNbrLb.setBounds(x0, y0, w+20, h);
	   nbrFd = new JTextField("0");
	   nbrFd.setBounds(x0+w+25, y0, w1-20, h);
	   add(grNbrLb);
	   add(nbrFd);
	   y0=y0+off+h;
	   JLabel adLb = new JLabel("Child Number.", JLabel.RIGHT);
	   adLb.setBounds(x0, y0, w, h);
	   childFd = new JTextField();
	   childFd.setBounds(x0+w+5, y0, w1, h);
	   add(adLb); add(childFd);
	   y0=y0+off+h;
	   JLabel mtLb = new JLabel("Book Method:", JLabel.RIGHT);
	   mtLb.setBounds(x0, y0, w, h);
	   bookBx = new JComboBox(bookMth);
	   bookBx.setBounds(x0+w+5, y0, w1, h);
	   add(mtLb);
	   add(bookBx);
	   y0=y0+off+h;
	   JLabel bsst = new JLabel("Book Status:", JLabel.RIGHT);
	   bsst.setBounds(x0, y0, w, h);
	   bookSttBx = new JComboBox(bookStt1);
	   bookSttBx.setSelectedIndex(0);
	   bookSttBx.setBounds(x0+w+5, y0, w1, h);   
	   y0=y0+h+off;	   
	   JLabel frnm = new JLabel("Arrive Time:", JLabel.RIGHT);
	   frnm.setBounds(x0, y0, w, h);
	   arriveFd = new JTextField();
	   arriveFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel tel = new JLabel("Visit Reason:", JLabel.RIGHT);
	   tel.setBounds(x0, y0, w, h);
	   visitFd = new JTextField();
	   visitFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel stLb = new JLabel("Check In: ", JLabel.RIGHT);
	   stLb.setBounds(x0, y0, w, h);
	   startFd = new JTextField();
	   startFd.setEditable(false);
	   startFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel  edLb = new JLabel("Check Out", JLabel.RIGHT);
	   edLb.setBounds(x0, y0, w, h);
	   endFd = new JTextField();
	   endFd.setEditable(false);
	   endFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;	   
	   JLabel eml = new JLabel("Pay Method:", JLabel.RIGHT);
	   eml.setBounds(x0, y0, w, h);	   
	   payMtFd = new JComboBox(payMethes);
	   payMtFd.setBounds(x0+w+5, y0, w1, h);
	   
	   add(ptit);
	   add(frnm); 	add(arriveFd);
	   add(tel);  	add(visitFd);
	   add(stLb); add(startFd);
	   add(edLb);	add(endFd);
	   add(grp);	add(groupFd);
	   add(eml);	add(payMtFd);
	   add(bsst);  add(bookSttBx);
	  
	}
	
	public void actionPerformed(ActionEvent av)
	{
		//String cmd = av.getActionCommand();
   	 	//int mth1 = bookDlg.monthx;
   	 	//int yr1 = bookDlg.yearx;		
				
	}
	
	//action adaptor 
	public void setMonth(int m){}
	public void setYear(int y){}
	public void setDateAct(int row, int fld, JButton cellBtn){}
	public void setSelectDate(Date dt, int mode)
	{   if(dt == null) return;
		if(mode==0)
		{
			startFd.setText(dateFmt.format(dt));
			DateMgr dm = new DateMgr();
			Date edt = dm.calEndDate(dt, 1);
			endFd.setText(dateFmt.format(edt));
		}else if(mode==1)
		{
			try
			{
				String edx = endFd.getText();
				Date edt1 = dateFmt.parse(edx);
				if(dt.compareTo(edt1)<0)
				{
					String msg1 = "The end date has to be after start date, \nplease change!";
					JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}catch(Exception ex){}
			endFd.setText(dateFmt.format(dt));
		}
	}
	
	public List<String> getNames() { return bookDlg.frm.bkBus.getRoomMap().getNames();}
 
	protected String getNbrNight()
	{
		try
		{
		   String std = startFd.getText().trim();
		   String etd = endFd.getText().trim();
		   Calendar ca1 = Calendar.getInstance();
		   Calendar ca2 = Calendar.getInstance();
		   ca1.setTime(dateFmt.parse(std));
		   ca2.setTime(dateFmt.parse(etd));
		   long m1 = ca1.getTimeInMillis();
		   long m2 = ca2.getTimeInMillis();
		   long df = m2-m1;
		   long r = df/(24*60*60*1000);
		   return Long.toString(r);
		}catch(Exception ex) { return Long.toString(0); }		
	}
}
