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

public class BookPane extends JPanel implements ActionListener,ActAdapt
{
	static public final long serialVersionUID = BookDlg.serialVersionUID;
	JTextField arriveFd, visitFd, startFd, daysFd, endFd,groupFd, addPayFd; 
	JTextArea noteArea;
	//JButton startBtn,
	JButton endBtn;	 
	JComboBox payMtFd, discFd, bookSttBx, nbrBox1, nbrBox2, bookBx;
	JTextField totalFd, paidFd,  balFd, nightFd, uidFd, timeFd, nbrFd;
	BookDlg  bookDlg = null;
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	
	String payMethes[] ={"Cash", "Credit Card", "Debit Card", "Cheque", "Others"};
	String discnt[]={"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "1"}; 
	String bookStt[] = {"provisional", "confirmed", "taken"};
	String bookStt1[]={"provisional", "confirmed", "taken", "cancel" };
	String nbrs[]={"0", "1","2","3","4","5","6","7","8","9","10"}; 
	String bookMth[]={"Telephone","Email", "Letter", "Others"};
	boolean isNew = true;

	public BookPane(BookDlg dlg)
	{
		bookDlg = dlg;
		setLayout(new XYLayout());
		Border aBorder = BorderFactory.createRaisedBevelBorder();
		setBorder(aBorder);
	}
	public void setHasNew(boolean is){isNew = is; }
		
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
	   JLabel grNbrLb = new JLabel("Total Group Number:", JLabel.RIGHT);
	   grNbrLb.setBounds(x0, y0, w+20, h);
	   nbrFd = new JTextField("0");
	   nbrFd.setBounds(x0+w+25, y0, w1-20, h);
	   add(grNbrLb);
	   add(nbrFd);
	   y0=y0+off+h;
	   int xx0 =5, wxx=50, wxx1=50;
	   JLabel adLb = new JLabel("Child No.", JLabel.RIGHT);
	   adLb.setBounds(xx0, y0, wxx, h);
	   xx0 = xx0+wxx+off;
	   nbrBox1 = new JComboBox(nbrs);
	   nbrBox1.setBounds(xx0, y0, wxx1, h);
	   nbrBox1.setEditable(true);
	   nbrBox1.setEnabled(true);
	   add(adLb);
	   add(nbrBox1);
	   xx0 = xx0+wxx1+8;
	   JLabel chLb = new JLabel("Discount", JLabel.RIGHT);
	   chLb.setBounds(xx0, y0, wxx, h);
	   xx0 = xx0+wxx+off;
	   nbrBox2 = new JComboBox(discnt);
	   nbrBox2.setBounds(xx0, y0, wxx1, h);
	   nbrBox2.setEditable(true);
	   nbrBox2.setEnabled(true);
	   add(chLb);
	   add(nbrBox2);
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
	   if(isNew)
		   bookSttBx = new JComboBox(bookStt);
	   else
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
	   //startBtn = new JButton("Check In");
	   //startBtn.setActionCommand("Start Date");
	   //startBtn.setBounds(x0, y0, w, h);
	   JLabel stLb = new JLabel("Check In: ", JLabel.RIGHT);
	   stLb.setBounds(x0, y0, w, h);
	   startFd = new JTextField();
	   startFd.setEditable(false);
	   startFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   endBtn = new JButton("Check Out");
	   endBtn.setActionCommand("End Date");
	   endBtn.setBounds(x0, y0, w, h);
	   endFd = new JTextField();
	   endFd.setEditable(false);
	   endFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   int nx0=x0, nw0=50, nw1=50;
	   JLabel ntLb = new JLabel("Night No.", JLabel.RIGHT);
	   ntLb.setBounds(nx0, y0, nw0, h);
	   nx0 = nx0+nw0+off;
	   nightFd = new JTextField();
	   nightFd.setEditable(false);
	   nightFd.setBounds(nx0, y0, nw1, h);
	   add(ntLb);
	   add(nightFd);
	   nx0 = nx0+nw1+2*off;
	   JLabel apLb = new JLabel("Add Pay:", JLabel.RIGHT);
	   apLb.setBounds(nx0, y0, nw0, h);
	   nx0 = nx0+nw0+off;
	   addPayFd = new JTextField("0.0");
	   addPayFd.setBounds(nx0, y0, nw1, h);
	   add(apLb);
	   add(addPayFd);
	   y0=y0+h+off;	   
	   JSeparator sp = new JSeparator();
	   sp.setBounds(2, y0, x0+w+w1+8, 5);
	   y0=y0+off;
	   JLabel eml = new JLabel("Pay Method:", JLabel.RIGHT);
	   eml.setBounds(x0, y0, w, h);	   
	   payMtFd = new JComboBox(payMethes);
	   payMtFd.setBounds(x0+w+5, y0, w1, h);
	   //y0=y0+h+off+3;
	   int wx=55;
	   int x1 =x0+wx+off;
	   //JLabel dslb = new JLabel("Discount:", JLabel.RIGHT);
	   //dslb.setBounds(x0, y0, wx, h);
	   discFd = new JComboBox(discnt);
	   discFd.setBounds(x1, y0, wx, h);	   
	   y0=y0+h+off;
	   JLabel paidLb = new JLabel("Deposite:", JLabel.RIGHT);
	   paidLb.setBounds(x0, y0, w, h);
	   paidFd = new JTextField("0.0");
	   paidFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel balLb = new JLabel("Balance:", JLabel.RIGHT);
	   balLb.setBounds(x0, y0, w, h);
	   balFd = new JTextField("0.0");
	   balFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;	   
	   JLabel payst = new JLabel("Total:", JLabel.RIGHT);
	   payst.setBounds(x0, y0, w, h);
	   totalFd = new JTextField("0.0");
	   totalFd.setBounds(x0+w+off, y0, w1, h);
	   add(payst);
	   add(totalFd);
	   
	   y0=y0+h+off;
	   JSeparator sp1 = new JSeparator();
	   sp1.setBounds(2, y0, x0+w+w1+8, 5);
	   	y0=y0+off;
	   JLabel noteLb = new JLabel("Booking Note", JLabel.CENTER);
	   noteLb.setBounds(x0+3, y0,  w+w1, h);
	   y0=y0+h+off;
	   int nth = 50;
	   noteArea = new JTextArea();
	   noteArea.setLineWrap(true);
	   noteArea.setWrapStyleWord(true);
	   JScrollPane scrTxt = new JScrollPane(noteArea);
	   scrTxt.setBounds(x0+3, y0,  w+w1, nth);
	   y0 = y0+nth+5;
	   JLabel uidLb = new JLabel("Booked by:", JLabel.RIGHT);
	   uidLb.setBounds(x0, y0, w, h);
	   uidFd = new JTextField();
	   uidFd.setBounds(x0+w+off, y0, w1, h);
	   uidFd.setEditable(false);
	   add(uidLb);
	   add(uidFd);
	   y0=y0+h+off;
	   JLabel tmLb = new JLabel("Book Time:", JLabel.RIGHT);
	   tmLb.setBounds(x0, y0, w, h);
	   timeFd = new JTextField();
	   timeFd.setBounds(x0+w+off, y0, w1, h);
	   timeFd.setEditable(false);
	   add(tmLb);
	   add(timeFd);

	   add(ptit);
	   add(frnm); 	add(arriveFd);
	   add(tel);  	add(visitFd);
	   add(stLb); add(startFd);
	   add(endBtn);	add(endFd);
	   add(sp);
	   add(grp);	add(groupFd);
	   add(eml);	add(payMtFd);
	  //add(dslb); add(discFd); add(payst);add(totalFd);
	   add(bsst);  add(bookSttBx);
	   add(paidLb); add(paidFd); 
	   add(balLb); add(balFd);
	   add(sp1);
	   add(noteLb);
	   add(scrTxt);
	  // startBtn.addActionListener(this);
	   endBtn.addActionListener(this);	   
	   //init properties
	   if(isNew)
	   {
		   uidFd.setText(bookDlg.frm.getDataBus().getActUser().getUserid());
		   timeFd.setText(dateFmt.format(new Date()));
	   }   
	}
	
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
   	 	int mth1 = bookDlg.monthx;
   	 	int yr1 = bookDlg.yearx;		
		if(cmd.equals("End Date"))
		{
			DateChooseDlg dlg = new DateChooseDlg(bookDlg, mth1, yr1); 
			dlg.setMode(1);
			dlg.setAdaptor(this);
			dlg.setVisible(true);
		}		
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
		nightFd.setText(getNbrNight());
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
