package slkcalendar;
/*  Date table represent calendar in table format
 *  @author GY
 */

import javax.swing.*;
import javax.swing.table.TableColumn;

import bbooker.BKFrame;

import java.awt.Color;
import java.awt.Font;
//import java.awt.Point;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import guiwidget.*;

public class DateTable extends JPanel implements ActAdapt,PanelAdapter
{
	static public final long serialVersionUID = 123456789;	
	int year, month, selectedDay, nbrDays;
	Calendar calendar;
	Date selectedDate;
		
	String[]  weekDesc ={"Sunday", "Monday", "Tuesday",  "Wednesday",  
            					  "Thursday",  "Friday","Saturday" };
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat outFmt=new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat dtFmt1 = new SimpleDateFormat("EEE, dd MMM yyyy");
    SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
    
    //appearance
    Color selectedDayColor = new Color(0, 255, 64);
    Color dayBkColor = Color.white;
    Color dayTxtColor = Color.black;
    Color borderColor = Color.black;
    DateMgr mgr;
        
    SSTablePane tabPane, tabPane0; //tabPane0 for week and date table
  //  Vector<Vector<String>> datax; //main table 
    String header[], dayHeader[];
    MonthData mData;  //current monthData
    
    private YearChooser yearCh;
	private MonthChooser monthCh;
	private JTextField selectFd, userFd;
	private GStyledTextPane descPane;
	//private JButton exitBtn, bookBtn, editBtn, reportBtn;	
	int frmWid, frmHgt;
	int cellRow=-1, cellCol=-1; // select cell number	
	protected JFrame ctner;
	
	protected List<String> bkIdss;
	protected GPopupMenu popupMenu;

	public DateTable(JFrame ctn, int m, int y, int wd, int hgt)
	{
		ctner = ctn;
		year = verifyYear(y);
		month = verifyMonth(m);
		calendar = new GregorianCalendar();
		frmWid = wd-30; frmHgt = hgt-25;
		mgr = new DateMgr();
		mgr.setDayAction(this);
		dayHeader = new String[2];
		dayHeader[0]= "Day";
		dayHeader[1]= "Date";
		//popupMenu = new GPopupMenu(null);
	}
	public void setPopupMenu(GPopupMenu gmp){ popupMenu=gmp;}
	public List<String> getBookStr() { return bkIdss; }
		
	public MonthData getDayData(int mt, int yr)
	{
		Date monthDay1 = parseDate(1, mt, yr);
		calendar.setTime(monthDay1);
		int weekDay1 = calendar.get(Calendar.DAY_OF_WEEK);
		weekDay1--; //start from zero
		nbrDays = getMonthDays(mt, yr);
		MonthData md = new MonthData(mt, yr, 2);
		md.setInitObj(new String(" "));
		md.init();
		for(int i=0; i<nbrDays; i++)
		{
			int idx = weekDay1 % 7;
			md.updateCell(i, 0, weekDesc[idx]);
			md.updateCell(i, 1, Integer.toString(i+1));
			weekDay1++;
		}
		month =mt; year =yr;
		return md;
	}
	
	public void setTabHeader(String desc[])
	{
		header = new String[desc.length];
		System.arraycopy(desc, 0, header, 0, desc.length);	
	}
	
	public String[] getTabHeader() { return header;}
	
	public void setMonthData(String data[][]){ mData = makeTabData(data, header.length); }
	
	public MonthData makeTabData(String data[][], int colNbr)
	{
		MonthData md = new MonthData(month, year, colNbr);
		md.setInitObj(new String(" "));
		md.init();
		for(int i=0; i<data.length && i<nbrDays; i++)
			for(int j=0; j<data[i].length && j<colNbr; j++)
				md.updateCell(i, j, data[i][j]);
		return md;
	}	
	
	public void initPane(List<String> bks, int colLength)
	{ 
	  try
	  {
		bkIdss = bks;
		setLayout(new XYLayout());
		monthCh = new MonthChooser(month);
		monthCh.setManager(mgr);
		int h0 = 5; 
		int h1 = 35;
		monthCh.setBounds(10, h0, 160, h1);
		add(monthCh);
		yearCh = new YearChooser(year);
		yearCh.setManager(mgr);
		yearCh.setBounds(200, h0, 160, h1);
		add(yearCh);
		
		JLabel idLb = new JLabel("User ID:", JLabel.RIGHT);
		idLb.setBounds(380, h0+5, 100, 20);
		add(idLb);
		userFd = new JTextField();
		userFd.setEditable(false);
		
		userFd.setBounds(485, h0+5, 100, 20);
		add(userFd);
		
		JLabel selLb = new JLabel("Selection:", JLabel.RIGHT);
		setLabelProperty(selLb);
		selLb.setBounds(frmWid-305, h0+5, 100, 23);
		add(selLb);
		
		selectFd = new JTextField();
		selectFd.setEditable(false);
		setFieldProperty(selectFd);
		selectFd.setBounds(frmWid-200, h0+5, 200+5, 23);
		add(selectFd);
		
		int wids0[] = new int[2];
		wids0[0] = 75;
		wids0[1]= 35;
		int th1 = 40;
		int len = wids0[0]+wids0[1];
		tabPane0 = new SSTablePane(getDayData(month, year).getMatrix(), dayHeader);
		tabPane0.setBounds(10, th1, len+3, frmHgt-h1-120);;
		tabPane0.setColumnWidths(wids0);
		tabPane0.setPane(false);
		TableColumn cl0 = tabPane0.getMainCM().getColumn(0);
		TableColumn cl = tabPane0.getMainCM().getColumn(1);
		cl0.setCellRenderer(new SSTabCellRender(1));
		cl.setCellRenderer(new SSTabCellRender(1));
		add(tabPane0);
	    //mData = makeTabData(data, header.length);
	    parseMonthList(bkIdss);
	    //check which one is data
	    //mData.getMatrix();
	 	tabPane = new SSTablePane(mData.getMatrix(), header);
		int x0 = 10+len+2;
		tabPane.setBounds(x0, th1, frmWid-x0+10, frmHgt-h1-120);;
		int wids[] = getTableWids();
		tabPane.setColumnWidths(wids);
		tabPane.setPane(false);
		tabPane.setAdaptor(this);		
		//tabPane.setMutilSelectPane();
		for(int i=0; i<header.length; i++)
		{
		   tabPane.getMainCM().getColumn(i).setCellRenderer(new SSTBButtonCell(1));
		  // tabPane.getMainCM().getColumn(i).setCellEditor(new SSCellEditor(new JCheckBox()));		
			//tabPane.getMainTable().getColumn(i).setCellRenderer(new SSTBButtonCell(1, this));
			//tabPane.getMainTable().getColumn(i).setCellEditor(new SSCellEditor(new JCheckBox()));
		}
		add(tabPane);
		descPane = new GStyledTextPane();
		JScrollPane jsc1 = new JScrollPane(descPane);
		jsc1.setBounds(10, frmHgt-115, frmWid-x0+7+len, 40);
		add(jsc1);
		//add popup menu to JTable;
		tabPane.getMainTable().addMouseListener(new PopupListener());
	  }catch(Exception ex) {ex.printStackTrace();}
	}
	
	/*public void setBtnExtListener(ActionListener ls)
	{
		exitBtn.addActionListener(ls);
		bookBtn.addActionListener(ls);
		editBtn.addActionListener(ls);
		reportBtn.addActionListener(ls);
	}*/
	
	public void setManager(DateMgr mg)
	{ 
		mgr = mg;
		mgr.setDayAction(this);
	}
	public Date getSelectedDate( ) { return selectedDate; }
	public void setUserIdFd(String id){ userFd.setText(id); }
	
	public void doAction( )
	{
		//System.out.println("DayChooser: "+selectedDay+month+year);
		mgr.actDay(selectedDay, month, year);
	}

	//-------- implementation of ActAdpt -------------------
	public void setYear(int y)
	{
		year = verifyYear(y);
		freshData(month, year);
	}
	
	public void setDateAct(int row, int fld, JButton cellBtn)
	{
		System.out.println(row+" : "+fld+"--"+cellBtn.getText());
	}
	
	public void setMonth(int m)
	{
		month = verifyMonth(m);
		freshData(m, year);
	}
	public void setCellIdcs(int row, int col) 
	{ 
		cellRow = row; cellCol = col;
		if(cellRow <0 || cellCol <0) return;
		selectedDate = parseDate(cellRow+1, month, year);	
		try
		{
			String st = dtFmt1.format(selectedDate);
			selectFd.setText(st);
			st = st+"            Romm:   "+header[cellCol];
			descPane.setText("");
			descPane.append(st, 0);
			BKFrame bfrm = (BKFrame)ctner;
			descPane.append(bfrm.getBookBus().getSummary(gettCellIdcs()), 0);
		}catch(Exception ex) 
		{
			System.out.println("DateTabke 250: "+ex.toString());
			ex.printStackTrace();
		}
	}
	public int[] gettCellIdcs()
	{
		int idcs[] = new int[4];
		idcs[0] = year;
		idcs[1] = month;
		idcs[2]= cellRow;
		idcs[3] = cellCol;
		return idcs;
	}
	
	public void processRow(Object row[])
	{
		//System.out.println("DateTable.ProcessRow268: "+cellRow+": "+cellCol);
	}
	
	public void setMonthYear(int m, int y)
	{
		year = verifyYear(y);
		month = verifyMonth(m);
		freshData(month, year);
	}
	
	public void freshData()
	{
		//System.out.println(bkIdss.toString());
		parseMonthList(bkIdss);
		tabPane.refreshTab(header, mData.getMatrix());
	}
	
	protected void freshData(int m, int y)
	{
		tabPane0.refreshTab(dayHeader, getDayData(m, y).getMatrix());	
		parseMonthList(bkIdss);
		tabPane.refreshTab(header, mData.getMatrix());
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
	
	//------ display properties----------
	public void setLabelProperty(JLabel lb)
	{
		lb.setFont(new Font("Helvetica", Font.BOLD, 14));
		//lb.setBackground(Color.white)
	}
	
	public void setFieldProperty(JTextField lb)
	{
		lb.setFont(new Font("Helvetica", Font.BOLD, 14));
		//lb.setBackground(UKDiaryPanel.lightBlue);
		lb.setBackground(Color.white);
		//lb.setBorder(BorderFactory.createEtchedBorder());
		lb.setBorder(BorderFactory.createLineBorder(Color.black));
	}	
	public void setSelectDate(Date dt, int mode){}
	public List<String> getNames(){ return new ArrayList<String>(); }
	
	//sorted roomId::yyyyMMdd-yyyyMMdd::bookStatus::groupSerious
	public void parseMonthList(List<String> bks)
	{ 
	  // if(bks==null || bks.size()<1) return;
	  //create a month local table only consider this month
	  List<Date> stds = new ArrayList<Date>();
	  List<Date> ends = new ArrayList<Date>();
	  List<String> grpIds = new ArrayList<String>();
	  //List<String> seriNo = new ArrayList<String>();
	  List<Integer> rms = new ArrayList<Integer>();
	  List<String> stts = new ArrayList<String>();
	  Date last = parseDate(nbrDays, month, year);
	  Date first = parseDate(1, month, year);
	 // Date tdy = new Date();
	  if(bks !=null)
	  {
		  for(int k=0; k<bks.size(); k++)
		  {
			  try
			  {
				  String ss[] = separate(bks.get(k));
				  Date  std = idFmt.parse(ss[1]);
				  Date  ed = idFmt.parse(ss[2]);
				  if(std.compareTo(last)>0) continue;
				  if(ed.compareTo(first)<0) continue;
				  stds.add(std);
				  ends.add(ed);
				  //Integer ri = Integer.parseInt(ss[4]);
				  stts.add(ss[3]);
				  Integer ri = indexOfHead(ss[0]);
				  if(ri>=0) rms.add(ri);
				  grpIds.add(ss[4]);
				  					  			  			 
			  }catch(Exception ex){ ex.printStackTrace(); }
		  }
	  }
	  mData = new MonthData(month, year, header.length);
	  mData.setInitObj(new String(" "));
	  mData.init();
	  for(int i=0; i<nbrDays; i++)
	  {
		 Date wkd = parseDate(i+1, month, year);
		 for(int k=0; k<stds.size(); k++)
		 {
			if(wkd.compareTo(stds.get(k))>=0 && 
				wkd.compareTo(ends.get(k)) <0 )
			{
			  try
			  {
				  int j = rms.get(k).intValue();
				  String stt1 = stts.get(k);
				  stt1 = stt1+grpIds.get(k);
				  mData.updateCell(i, j, stt1);
			  }catch(Exception ex) { ex.printStackTrace(); }
			}
		 }				   
	  }
	}
	
	protected int  indexOfHead(String head)
	{	
		if(head==null) return -1;
		head = head.trim();
		int i=0;
		for(i=0; i<header.length; i++)
			if(head.equals(header[i])) return i;
		
		return -1;
	}
	
	protected String[] separate(String bookStr1)
	{
		String ss[] = new String[5];
		StringTokenizer dtk = new StringTokenizer(bookStr1, "::");
		ss[0]= dtk.nextToken();			
		String dk1 = dtk.nextToken();
		StringTokenizer dtk1 = new StringTokenizer(dk1, "-");
		ss[1] = dtk1.nextToken();
		ss[2] = dtk1.nextToken();		
		ss[3] = dtk.nextToken();
		ss[4] = dtk.nextToken();
		return ss;
	}
	
	int[] getTableWids()
	{
		int sz = header.length;
		int wids[] = new int[sz];
		int tw = frmWid - 115;
		int min = 100; //minimum
		try
		{
		   min = (int) Math.floor(tw/sz);
		   if(min<100) min=100;
		}catch(Exception ex) {min = 100; }
		for(int i=0; i<header.length; i++)
			wids[i] = min;
		return wids;	
	}
	
	class PopupListener extends MouseAdapter 
	{
	   public void mousePressed(MouseEvent e){showPopup(e);}
	   public void mouseReleased(MouseEvent e){showPopup(e);}
		   
	   private void showPopup(MouseEvent e) 
	   {
		  if (e.isPopupTrigger())
		  {
			 popupMenu.show(e.getComponent(), e.getX(), e.getY());
	//		 System.out.println("row="+cellRow+", col="+cellCol);
		  }
	   }
	   //seems no action 
	   public void mouseClicked( MouseEvent e )
	   {
		  if ( SwingUtilities.isRightMouseButton( e ))
		  {
			 // get the coordinates of the mouse click
			 //Point p = e.getPoint();
			 // get the row index that contains that coordinate
			 //int rowNumber = tabPane.getMainTable().rowAtPoint(p);
			 //int iCol = tabPane.getMainTable().columnAtPoint(e.getPoint());
			 //System.out.println("row1="+rowNumber+", col1="+iCol );
			 // Get the ListSelectionModel of the JTable
			 //ListSelectionModel model = tabPane.getMainTable().getSelectionModel();
			 //model.setSelectionInterval( rowNumber, rowNumber );
		  }
	   }
	}
}
