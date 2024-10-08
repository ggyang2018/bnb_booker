package bbooker;

/* Bespoke BookSheet diaglog
 * @Author Guang Yang
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import javax.swing.*;

import slkcalendar.*;
import guiwidget.*;

public class BookSheetDlg extends JDialog implements ActionListener, ActAdapt
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	BKFrame frm;
	
	JTextField titFd;
	SSTablePane reportPane;
	JButton pdfBtn, csvBtn, cancelBtn;
	
	JButton startBtn, endBtn, freshBtn;
	JTextField startFd, endFd;
	
	String heads[] = {"Book Code", "Customer Code", "Book String",  "Room ID", "Room"};
	String heads1[] = {"Customer Name", "Group Name", "Book Period", "Room", "Status"};
	int wids[] = {260, 120, 320, 100, 80};
	int monthx, yearx;
	//initial data
	String data0[][];
	int dataSz=0;
	//working data
	List<String> bidList;
	String wkData[][];
	public BookSheetDlg(BKFrame frm, String title)
	{
		super(frm);
		this.frm = frm;
		setModal(true);
		setTitle(title);
		//setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
		setIconImage(Toolkit.getDefaultToolkit().getImage("iconimg\\ss4ulogo1.gif"));
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{     dispose(); setVisible(false);}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = new Double(dim.getWidth());
		        Double h = new Double(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		getContentPane().setLayout(new XYLayout());
	}
	public void setCurrentMonthYear(int mt, int yr) { monthx=mt; yearx=yr;}
	
	public void createData(boolean debug)
	{
		bidList = new ArrayList<String>();
		frm.bkBus.updateBookStrs();
		List<String> lst = frm.bkBus.getBookMap().getCodes();
		data0 = new String[lst.size()][heads.length];
		dataSz = lst.size();
		for(int i=0; i<dataSz; i++)
		{
		   int cnt=0;
		  try
		  {
			String key = lst.get(i);
			bidList.add(key);
			BookDetail bkd1 =(BookDetail)frm.bkBus.getBookMap().find(key);
			SLKCustomer cs = (SLKCustomer)frm.bkBus.getCustMap().find(bkd1.getCustId());
			if(debug)data0[i][cnt] = key;
			else data0[i][cnt] = cs.getForename()+" "+cs.getSurname(); cnt++;		
			if(debug)data0[i][cnt] = cs.getCode();
			else data0[i][cnt] = cs.getGroupName(); cnt++;
			if(debug)data0[i][cnt] = frm.bkBus.getBookStrs().get(i);
			else data0[i][cnt] = bkd1.getStartDate()+"--"+bkd1.getEndDate(); cnt++;		
			if(debug)data0[i][cnt] = bkd1.getRoomId();
			else data0[i][cnt] = bkd1.getRoomName(); cnt++;
			if(debug)data0[i][cnt] = bkd1.getRoomName();
			else data0[i][cnt] = bkd1.getBookStatus(); 
		  }catch(Exception ex) 
		  {
			  cnt--;
			  for(int j= cnt; j<heads.length; j++)
				  data0[i][j] = new String(" ");
			  ex.printStackTrace();
		  }
		}
	}
	//700x500
	public void initDlg() 
	{
	   int h0 = 5, off = 5, h1=20, h2=400;
	   int x0=5, w1=80;
	   JLabel titLb = new JLabel("Title:", JLabel.RIGHT);
	   titLb.setBounds(x0, h0, w1, h1);
	   titFd = new JTextField();
	   titFd.setBounds(w1+x0+off, h0, 340, h1);
	   //bespoke
	   int wx1 = w1+x0+off+350, bw1 = 70, fw1=80;
	   startBtn = new JButton("Start");
	   startBtn.setBounds(wx1, h0, bw1, h1);
	   wx1 = wx1+bw1+off-2;
	   startFd = new JTextField();
	   startFd.setBounds(wx1, h0, fw1, h1);
	   wx1 = wx1+fw1;
	   JLabel toLb = new JLabel("==>", JLabel.CENTER);
	   toLb.setBounds(wx1, h0, 6*off, h1);
	   wx1 = wx1+6*off;
	   endFd = new JTextField();
	   endFd.setBounds(wx1, h0, fw1, h1);
	   wx1 = wx1+fw1+off-2;
	   endBtn = new JButton("End");
	   endBtn.setBounds(wx1, h0, bw1, h1);
	   	   	   	   
	   wx1 = wx1+bw1+5*off;
	   freshBtn = new JButton("Fresh");
	   freshBtn.setBounds(wx1, h0, bw1, h1);
	   h0 = h0+off+h1;
	   createData(false);
	   //reportPane = new SSTablePane(data0, heads);
	   reportPane = new SSTablePane(data0, heads1); //if false
	   reportPane.setBounds(x0, h0, 870, h2);
	   reportPane.setColumnWidths(wids);
	   reportPane.setPane(false);
	   
	   h0 = h0+h2+2*off;
	   int x1 = 100, w2=120;
	   pdfBtn = new JButton("Trace"); 
	   pdfBtn.setBounds(x1, h0, w2, h1);
	   
	   x1 = x1+w2+2*off;
	   csvBtn = new JButton("Export Excel");
	   csvBtn.setBounds(x1, h0, w2, h1);
	   
	   x1 = x1+w2+2*off;
	   cancelBtn = new JButton("Close");
	   cancelBtn.setBounds(x1, h0, w2, h1);
	   
	   getContentPane().add(titLb);
	   getContentPane().add(titFd);
	   getContentPane().add(startBtn);
	   getContentPane().add(startFd);
	   getContentPane().add(toLb);
	   getContentPane().add(endBtn);
	   getContentPane().add(endFd);
	   getContentPane().add(freshBtn);
	   
	   getContentPane().add(reportPane);
	   getContentPane().add(pdfBtn);
	   getContentPane().add(csvBtn);
	   getContentPane().add(cancelBtn);
	   
	   pdfBtn.addActionListener(this); 
	   csvBtn.addActionListener(this);
	   cancelBtn.addActionListener(this);
	   startBtn.addActionListener(this); 
	   endBtn.addActionListener(this);
	   freshBtn.addActionListener(this);
	}
	
	public void setFavourBounds(int width, int height)
	{
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}
	

	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		int mth1 = monthx;
   	 	int yr1 = yearx;	
		if(cmd.equals("Close"))
		{
			dispose();
			setVisible(false);
		}else if(cmd.equals("Export Excel"))
		{
			String fnm = "BookSheet"+idFmt.format(new Date())+".csv";
			exportCSV(fnm);
		}else if(cmd.equals("Start"))
		{
			DateChooseDlg dlg = new DateChooseDlg(this, mth1, yr1); 
			dlg.setMode(0);
			dlg.setAdaptor(this);
			dlg.setVisible(true);
		}else if(cmd.equals("End"))
		{
			DateChooseDlg dlg = new DateChooseDlg(this, mth1, yr1); 
			dlg.setMode(1);
			dlg.setAdaptor(this);
			dlg.setVisible(true);
		}else if(cmd.equals("Fresh"))
			fresh();
		else if(cmd.equals("Trace"))
			showDetail();
		
	}
	
	public void exportCSV( String fnm)
    {
	   try
	   {
		  JFileChooser chooser = new JFileChooser(); 
		  chooser.setCurrentDirectory(new File("."));
		  chooser.setDialogTitle("Export Excel Dialog");
		  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  // disable the "All files" option.
		  chooser.setAcceptAllFileFilterUsed(false);
		  if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		  { 
		        File dir= chooser.getCurrentDirectory();
		        String pth = dir.getAbsolutePath();
		        pth = pth+fnm;
		        FileWriter wrt = new FileWriter(fnm);
		        writeCSV(wrt);
	    		wrt.close();
		   }
	   }catch(Exception ex){ex.printStackTrace(); }
    }
	
	protected void writeCSV(FileWriter wrt)
	{
	   try
	   {
		  for(int c=0; c<heads1.length; c++)
		  {
			 if(c<heads1.length-1)
				 wrt.append(heads1[c]+", ");
			 else
				 wrt.append(heads1[c]);
		  }
		  wrt.append("\n");
	   }catch(Exception ex1) {}
			
	   for(int i=0; i<bidList.size(); i++)
	   {
		  try
		  {
			String key = bidList.get(i);
			BookDetail bkd1 =(BookDetail)frm.bkBus.getBookMap().find(key);
			SLKCustomer cs =(SLKCustomer)frm.bkBus.getCustMap().find(bkd1.getCustId());
			String cnm = cs.getForename()+" "+cs.getSurname();
			wrt.append(cnm+", ");
			wrt.append(cs.getGroupName()+", ");		
			Date ds1 = idFmt.parse(bkd1.getStartDate());
			Date de1 = idFmt.parse(bkd1.getEndDate());
			String dts = dateFmt.format(ds1)+"--"+dateFmt.format(de1);
			wrt.append(dts+", ");
			wrt.append(bkd1.getRoomName()+", ");
			wrt.append(bkd1.getBookStatus());
			wrt.append("\n");
			wrt.flush();	        
		  }catch(Exception ex) {}
		}		
	}
	
	
	protected void fresh()//using checkout date inclusive
	{
	   String sd0 = formatChange(startFd.getText(), false); //in id format
	   String ed0 = formatChange(endFd.getText(), false); //yyyyMMdd
	   List<List<String>> tb = new ArrayList<List<String>>();
	   List<String> lst = frm.bkBus.getBookMap().getCodes();
	   List<String> tmps = new ArrayList<String>();
	   int rowSize = 0;
	   for(int i=0; i<lst.size(); i++)
	   {
		  List<String> row = new ArrayList<String>();
		  try
		  {
			String key = lst.get(i);
			BookDetail bkd1 = (BookDetail)frm.bkBus.getBookMap().find(key);
			String ed1 = bkd1.getEndDate();
			if(sd0 != null && ed1.compareTo(sd0)<0) continue; //filter end date
			if(ed0 != null && ed1.compareTo(ed0)>0) continue;
			SLKCustomer cs =(SLKCustomer)frm.bkBus.getCustMap().find(bkd1.getCustId());
			String cnm = cs.getForename()+" "+cs.getSurname();
			if(cnm == null) cnm = new String();
	        row.add(cnm);
	        row.add(cs.getGroupName());
			String dts = formatChange(bkd1.getStartDate(), true)
			+"--"+formatChange(bkd1.getEndDate(), true);
			row.add(dts);
			row.add(bkd1.getRoomName());
			row.add(bkd1.getBookStatus());
			rowSize = row.size();				
			tb.add(row);
			tmps.add(key);
		  }catch(Exception ex) 
		  { ex.printStackTrace();}   
	   }
	   wkData = new String[tb.size()][rowSize];
	   for(int i=0; i<tb.size(); i++)
	   {
		   List<String> rls = tb.get(i);
		   for(int j=0; j<rowSize; j++)
			   wkData[i][j] = rls.get(j);
	   }
	   if(tb.size()>0)
	   {
		   bidList.clear();
		   bidList = tmps;
		   reportPane.refreshTab(heads, wkData);
	   }else
	   {
		 String msg1 = "No record for this period, \nNothing has been changed!";
		 JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);	
	   }
	}
	
	void showDetail()
	{
		dispose();
		setVisible(false);
		String key = bidList.get(reportPane.getSelectedRow());
		BookDetail bkd1 =(BookDetail)frm.bkBus.getBookMap().find(key);
		try{frm.trace(bkd1);}catch(Exception ex){ex.printStackTrace();}
		
	}
	
	protected String formatChange(String dts, boolean id_dt)
	{
	   String rt = null;	
	   try
	   {
		  if(id_dt)
		  {
			 Date dt = idFmt.parse(dts);
		     rt = dateFmt.format(dt);	 
		  }else
		  {
			  Date dt1 = dateFmt.parse(dts);
			  rt = idFmt.format(dt1);
		  }
	   }catch(Exception ex) 
	   {System.out.println("ReportDlg317-err: "+ex.toString()); rt=null;}
	   return rt;
	}
	//implementation adapt
	public void setMonth(int m){}
	public void setYear(int y){}
	public void setDateAct(int row, int fld, JButton cellBtn){}
	public void setSelectDate(Date dt, int mode)
	{
		if(mode==0)
		{
			if(dt !=null)
				startFd.setText(dateFmt.format(dt));
			else startFd.setText("");
		}else if(mode==1)
		{
			try
			{
				String edx = startFd.getText();
				Date edt1 = dateFmt.parse(edx);
				if(dt!=null && dt.compareTo(edt1)<0)
				{
					String msg1 = "The end date has to be after start date, \nplease change!";
					JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}catch(Exception ex){}
			if(dt != null)
				endFd.setText(dateFmt.format(dt));
			else
			   endFd.setText("");
		}
	}
	
	public List<String> getNames() { return null;}
}
