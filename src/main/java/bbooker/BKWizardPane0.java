package bbooker;
/*Book Wizard first Pane
 * @author Guang Yang
 */
//import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import slkcalendar.ActAdapt;
import slkcalendar.DateChooseDlg;
import slkcalendar.DateMgr;
import nxd.GenData;
import guiwidget.*;

public class BKWizardPane0 extends GWizardPane implements ActAdapt
{
	static public final long  serialVersionUID = GWizardPane.serialVersionUID;
	java.text.DecimalFormat df = new java.text.DecimalFormat("######.##");
	
	String bookMth[]={"Telephone","Email", "Letter", "Others"};
	String payBy[] = {"Individual", "Group"};
	int twid[]={100, 150, 45, 50, 60, 100};
	String prc[] = {"Per Person",  "Per Room", "User Input"};
	String thead[] = {"Room", "Type", "Price", "Book"};
	//String dsTit[]={"Children", "Blok booking", "Regular"};
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	
	JComboBox bookBx, prcBx, payByBx;
	JTextField nightFd, startFd, endFd, fixFd;
	JButton startBtn, endBtn, fixBtn;
	BKFrame frm;	
	JTable roomTab;
	int rowNbr=8;
	int colNbr = 4;
	BKWizardDlg wdlg;

	public BKWizardPane0(BKWizardDlg dlg)
	{
		super(dlg, "Booking Room Information", 0, false);
		super.initPane();
		frm = dlg.frm;
		wdlg = dlg;
		init2();
	}
	
	void init2()
	{
		int y0 = getY0();
		int x0=3, w=100, w1=100, h=20, off=3;
		int x1 = x0+w+off;
		JLabel prcLb1 = new JLabel("Book In", JLabel.RIGHT);
		prcLb1.setBounds(x0, y0, w, h);
		payByBx = new JComboBox(payBy);
		payByBx.setBounds(x1, y0, w1, h);
		add(prcLb1); add(payByBx);
		int nx0= x0+w+5+w1+15;
		JLabel mtLb = new JLabel("Book Method", JLabel.RIGHT);
		mtLb.setBounds(nx0, y0, w, h);
		bookBx = new JComboBox(bookMth);
		int nx1 = nx0+w+off;
		bookBx.setBounds(nx1, y0, w1, h);
		add(mtLb);
		add(bookBx);
		y0=y0+h+off;
		startBtn = new JButton("Check In");
		startBtn.setActionCommand("Start Date");
		startBtn.setBounds(x0+5, y0, w-5, h);
		startFd = new JTextField();
		startFd.setEditable(false);
		startFd.setBounds(x1, y0, w1, h);		
		endBtn = new JButton("Check Out");
		endBtn.setActionCommand("End Date");
		endBtn.setBounds(nx0, y0, w, h);
		endFd = new JTextField();
		endFd.setEditable(false);
		endFd.setBounds(nx1, y0, w1, h);
		add(startBtn); add(startFd); add(endBtn); add(endFd);
		y0=y0+h+off;
		JLabel ntLb = new JLabel("Number of Nights", JLabel.RIGHT);
		ntLb.setBounds(x0, y0, w, h);
		nightFd = new JTextField();
		nightFd.setEditable(false);
		nightFd.setBounds(x1, y0, w1, h);
		add(ntLb);
		add(nightFd);				
		fixBtn = new JButton("Fix Price");
		fixBtn.setBounds(nx0, y0, w, h);
		fixFd = new JTextField("20.5");
		fixFd.setBounds(nx1, y0, w1, h);
		add(fixBtn); add(fixFd);
		fixBtn.addActionListener(this);
		y0=y0+h+off;		
		JLabel prcLb = new JLabel("Price Basis", JLabel.RIGHT);
		prcLb.setBounds(nx0, y0, w, h);
		prcBx = new JComboBox(prc);
		prcBx.setBounds(nx1, y0, w1, h);
		add(prcLb); add(prcBx);
		y0=y0+h+10;
		int tbh= 180;
		roomTab = new JTable(new BKTableModel());
		setTableProp();
		JScrollPane jsp1 = new JScrollPane(roomTab);
		int tbw = getWid()-20;
		jsp1.setBounds(x0+5, y0, tbw, tbh);
		add(jsp1);
		
		startBtn.addActionListener(this);
		endBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent av)
	{
		super.actionPerformed(av);
		String cmd = av.getActionCommand();
   	 	int mth1 = wdlg.monthx;
   	 	int yr1 = wdlg.yearx;		
		if(cmd.equals("Start Date"))
		{
			DateChooseDlg dlg = new DateChooseDlg(wdlg, mth1, yr1); 
			dlg.setMode(0);
			dlg.setAdaptor(this);
			dlg.setVisible(true);
		}else if(cmd.equals("End Date"))
		{
			DateChooseDlg dlg = new DateChooseDlg(wdlg, mth1, yr1); 
			dlg.setMode(1);
			dlg.setAdaptor(this);
			dlg.setVisible(true);
		}else if(cmd.equals("Fix Price"))
		   replaceFixData();			
		
	}
	
	public List<RoomData> getModelData()
	{
		BKTableModel bm =(BKTableModel)roomTab.getModel();
		Object[][] dat = bm.getData();
		List<GenData> nms = frm.bkBus.getRoomMap().getAll();
		List<RoomData> lst = new ArrayList<RoomData>();
		for(int i=0; i<bm.getRowCount(); i++)
		{
			RoomData rmd =(RoomData)nms.get(i);
			Boolean bb = (Boolean)dat[i][5];
			if(bb.booleanValue()==true && rmd.getName().equals(dat[i][0]))
			{
				Double dd = (Double)dat[i][2];
				rmd.setPrice(dd);
				Integer ip = (Integer)dat[i][3];
				rmd.setAdultNbr(ip);
				rmd.setChildNbr((Integer)dat[i][4]);
				lst.add(rmd);
			}
		}
		return lst;
	}
	
	protected void setTableProp()
	{
	   for (int i = 0; i < 6; i++) 
	   {
		  TableColumn column = roomTab.getColumnModel().getColumn(i);
		  column.setPreferredWidth(twid[i]); //third column is bigger
		  column.setMaxWidth(twid[i]);
		   //column.setWidth(twid[i]); //set fix width
		   //column.setHeaderValue(thead[i]);
	   }
	}
	
	protected void replaceFixData()
	{
		BKTableModel btm =(BKTableModel)roomTab.getModel();
		Object datx[][] = btm.getData();
		for(int i=0; i<datx.length; i++)
		{   Double itx = new Double(0.0);
			try
			{
				itx = new Double(fixFd.getText());
			}catch(Exception ex){ itx = new Double(0.0);}
			
			datx[i][2] = itx;			
		}
		btm.fireTableDataChanged();
	}
	
	
	protected Object[][] setTabData()
	{
		List<GenData> nms = frm.bkBus.getRoomMap().getAll();		
		List<RoomData> rms = new ArrayList<RoomData>();
		int i=0;
		for(i=0; i<nms.size(); i++)
		{
			RoomData rmd =(RoomData)nms.get(i);
			if(!rmd.getStatus().equals("free")) continue;
			else rms.add(rmd);
		}
		if(rms.size()<1)
		{
			Object[][] data0 = new Object[1][6];
			data0[0][0] = new String("");
			data0[0][1] = new String("");
			data0[0][2] = new Double(0.0);
			data0[0][3] = new Integer(0);
			data0[0][4] = new Boolean(false);
		}
				
		Object[][] data1 = new Object[rms.size()][6];
		for(i=0; i<rms.size(); i++)
		{
			RoomData rmd = rms.get(i);
			data1[i][0] = rmd.getName();
			data1[i][1] = rmd.getRoomTypeDesc();
			data1[i][2] = rmd.getPrice();
			data1[i][3] = new Integer(0);
			data1[i][4] = new Integer(0);
			data1[i][5] = new Boolean(false);
		}							
		return data1;
	}
	
	public void removeRow(int pos)
	{
		BKTableModel btm =(BKTableModel)roomTab.getModel();
		btm.rmRow(pos);
	}
	
	class BKTableModel extends AbstractTableModel
	{
		static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
		
        private String[] columnNames = {"Room","Type", "Price","Adult", "Children", "Book"};                           
        private Object[][] data ;
        
        public BKTableModel()
        {
        	super();
        	data = setTabData();
        }
        public int getColumnCount(){return columnNames.length;}
        public int getRowCount(){ return data.length; }
        public String getColumnName(int col) { return columnNames[col];}
        public Object getValueAt(int row, int col) {return data[row][col];}
       @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
        public boolean isCellEditable(int row, int col) 
        {
            if (col == 0) return false;
            else return true;     
        }
        public void setValueAt(Object value, int row, int col)
        {  
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }      
        public Object[][] getData(){ return data; }
        
       public void rmRow(int pos)
       {
    	 Object[][] datax = new Object[data.length-1][5];
    	 int j=data.length-2;
    	 for(int i=data.length-1; i>=0; i--)
    	 {
    		Object[] ob = data[i];
    		if(i != pos)
    		{
    		   Object[] ob1 = datax[j];
    		   System.arraycopy(ob, 0, ob1, 0, 5);
    		   j--;
    		}
    	 }
    	 data = datax;
    	 fireTableChanged(null);
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
				String edx = startFd.getText();
				Date edt1 = dateFmt.parse(edx);
				if(dt.compareTo(edt1)<=0)
				{
					String msg1 = "The end date has to be after start date,";
					wdlg.errorMsg(msg1);
					return;
				}
			}catch(Exception ex){}
			endFd.setText(dateFmt.format(dt));
		}
		nightFd.setText(getNbrNight());
	}
	public List<String> getNames() { return wdlg.frm.bkBus.getRoomMap().getNames();}

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
	
	public void backAction(){ }
	public boolean nextAction()
	{
	   List<RoomData> rms = getModelData();
	   if(rms==null || rms.size()<1)
	   {
		   wdlg.errorMsg("Must specify the room(s) for booking");
		   return false;
	   }
	   String stid = formatChange(startFd.getText(), false);
	   if(stid==null)
	   {
		   wdlg.errorMsg("Must specify the start data");
		   return false;
	   }
	   String etid = formatChange(endFd.getText(), false);
	   double amount =0.0;
	   double disAll = 0.0;
	   int  totalAdult = 0;
	   int totalChild = 0;
	   StringBuffer sbf = new StringBuffer();
	   long gid = Calendar.getInstance().getTimeInMillis();
	   for(int i=0; i<rms.size(); i++)
	   {
		  RoomData rm = rms.get(i);
		  String tmId = wdlg.frm.bkBus.findBookDetailId(stid, rm.getCode());
		  String tmId1 = wdlg.frm.bkBus.findBookDetailId(etid, rm.getCode());
		  if(tmId != null )
		  {
			 String idstr[] = wdlg.frm.bkBus.separate(tmId, true);
			 if(!stid.equals(idstr[2]) && rm.getCode().equals(idstr[0]))
			 {
				 String msg = rm.getName()+" is double booking at the checkin date";
				 wdlg.errorMsg(msg);
				 return false;
			 }
		  }
		  if(tmId1 != null )
		  {
			 String idstr1[] = wdlg.frm.bkBus.separate(tmId1, true);
			 if(!etid.equals(idstr1[1])&& rm.getCode().equals(idstr1[0]))
			 {
				 String msg = rm.getName()+" is double booking at the checkout date";
				 wdlg.errorMsg(msg);
				 return false;
			 }
		  }
		  
		  Integer ad0 = rm.getAdultNbr();
		  if(ad0.intValue()==0)
		  {
			  String msg = rm.getName()+" adult number cannot be zero. ";
			  wdlg.errorMsg(msg);
			  return false;
		  }		  
		  
		  BookDetail bk = new BookDetail();	  
		  bk.setRoomId(rm.getCode());
		  bk.setRoomName(rm.getName());
		  bk.setRoomType(rm.getRoomTypeDesc());
		  bk.setRoomPrice(rm.getPrice().toString());
		  bk.setRoomStatus("booked");
		  bk.setNightNbr(nightFd.getText());
		  String bid = bk.getRoomId()+"::"+stid+"-"+etid;
		  bk.setBookId(bid);
		  bk.setGroupId(gid);
		  Date td = new Date();
		  bk.setBookDate(dateFmt.format(td));
		  bk.setStartDate(stid);
		  bk.setEndDate(etid);
		  bk.setBooker(frm.bus.actUsr.getUserid());
		  bk.setBookMethod(bookBx.getSelectedItem().toString());
		  bk.setRoomPayMethod(payByBx.getSelectedItem().toString());
		  //new features
		  bk.setPriceBasis(prcBx.getSelectedItem().toString());		  		  		  
		  bk.setAdultNbr(rm.getAdultNbr().toString());
		  bk.setChildNbr(rm.getChildNbr().toString());
		  bk.calculateRoomCost(prcBx.getSelectedIndex());
		  wdlg.addBook(bk);
		  amount = amount+ bk.toDouble(bk.getRoomTotalFee());
		  totalAdult = totalAdult + bk.toInteger(bk.getAdultNbr());
		  totalChild = totalChild + bk.toInteger(bk.getChildNbr());
		  disAll = disAll + bk.toDouble(bk.getRoomTotalDiscount());
		  try
		  {
			 amount = df.parse(df.format(amount)).doubleValue();
			 disAll = df.parse(df.format(disAll)).doubleValue();
		  }catch(Exception ex){ex.printStackTrace(); }
		  
		  if(i==0)
		  {
			 sbf.append("Room Information    Dates: ");
			 sbf.append(formatChange(bk.getStartDate(), true));
			 sbf.append("----"+formatChange(bk.getEndDate(), true));
			 sbf.append("  No. of night. "+bk.getNightNbr());
		  }
		  sbf.append("\n"+bk.getRoomName());
		  sbf.append(":  Price="+bk.getRoomPrice()+"/"+bk.getPriceBasis());
	   }
	   BKWizardPane1 pan1 = (BKWizardPane1)wdlg.getWizardPane(1);
	   pan1.nightFd.setText(nightFd.getText());
	   pan1.totalFd.setText(Double.toString(amount));
	   pan1.totalDiscntFd.setText(Double.toString(disAll));
	   pan1.nbrFd.setText(Integer.toString(totalAdult));
	   pan1.childFd.setText(Integer.toString(totalChild));
	   pan1.noteArea.setText(sbf.toString());
	   if(!prcBx.getSelectedItem().toString().equals("User Input"))
	   {
		   pan1.nbrFd.setEditable(false);
		   pan1.childFd.setEditable(false);
	   }else
	   {
		   pan1.nbrFd.setEditable(true);
		   pan1.childFd.setEditable(true);
	   }
	   if(payByBx.getSelectedItem().toString().equals(payBy[0]))
	   {
		   pan1.groupFd.setText(payBy[0]);
		   pan1.groupFd.setEditable(false);
		   if(rms.size()>1)
		   {
			   wdlg.errorMsg("Individual can only select one room");
			   return false;			   
		   }
	   }else
	   {
		  pan1.groupFd.setText("");
		  pan1.groupFd.setEditable(true);
	   }
	   return true;
	}
	
	protected String formatChange(String dts, boolean id_dt)
	{
	   String rt = null;	
	   try
	   {
		  if(id_dt)
		  {
			 Date dt = idFmt.parse(dts.trim());
		     rt = dateFmt.format(dt);	 
		  }else
		  {
			  Date dt1 = dateFmt.parse(dts.trim());
			  rt = idFmt.format(dt1);
		  }
	   }catch(Exception ex) 
	   {System.out.println("ReportDlg317-err: "+ex.toString()); rt=null;}
	   return rt;
	}	   
}
