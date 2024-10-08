package bbooker;

/* Bespoke report diaglog
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

public class ReportDlg extends JDialog implements ActionListener, ActAdapt
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	BKFrame frm;
	
	JTextField titFd;
	SSTablePane reportPane;
	JButton  invoiceBtn, csvBtn, cancelBtn;
	
	JButton startBtn, endBtn, freshBtn;
	JTextField startFd, endFd;
	
	String heads[] = {"Date", "Name", "Night No.", "Country", "Pay in", 
			           "B&B", "Add Fee", "Total"};//8
	int wids[] = { 100, 150, 100, 100, 100, 100, 100, 100};
	int monthx, yearx;
	//initial data
	String data0[][];
	int dataSz=0;
	//working data
	List<String> bidList;
	String wkData[][];
	public ReportDlg(BKFrame frm, String title)
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
	
	public void createData()
	{
		bidList = new ArrayList<String>();
		//List<String> lst = frm.bkBus.getBookMap().getCodes();
		List<String> lst = frm.bkBus.getCustMap().getCodes();
		data0 = new String[lst.size()][heads.length];
		dataSz = lst.size();
		for(int i=0; i<dataSz; i++)
		{
		   int cnt=0;
		  try
		  {
			String key = lst.get(i);
			SLKCustomer cs = (SLKCustomer)frm.bkBus.getCustMap().find(key);
			BookDetail bkd1 =(BookDetail)frm.bkBus.getBookMap().find(cs.getBookId(0));			
			bidList.add(bkd1.getCode());		
			Date de1 = idFmt.parse(bkd1.getEndDate());	
			String dts = dateFmt.format(de1);
			data0[i][cnt] = dts; cnt++;
			String cnm = cs.getForename()+" "+cs.getSurname();
			data0[i][cnt] = cnm; cnt++;			
			data0[i][cnt] = bkd1.getNightNbr(); cnt++;
			data0[i][cnt] = cs.getCountry(); cnt++;
			data0[i][cnt] = cs.getPayMethod(); cnt++;
			data0[i][cnt] = cs.getRoomFee(); cnt++;
			data0[i][cnt] = cs.getAddFee(); cnt++;
			data0[i][cnt] = cs.getEndAmount();			
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
	   createData();
	   reportPane = new SSTablePane(data0, heads);
	   reportPane.setBounds(x0, h0, 870, h2);
	   reportPane.setColumnWidths(wids);
	   reportPane.setPane(false);
	   
	   h0 = h0+h2+2*off;
	   int x1 = 100, w2=120;
	   invoiceBtn = new JButton("Invoice");
	   invoiceBtn.setBounds(x1, h0, w2, h1);
	   getContentPane().add(invoiceBtn);
	   x1 = x1+w2+2*off;
	   csvBtn = new JButton("Export Excel");
	   csvBtn.setBounds(x1, h0, w2, h1);
	   
	   //x1 = x1+w2+2*off;
	   //printBtn = new JButton("Print");
	   //printBtn.setBounds(x1, h0, w2, h1);
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
	   getContentPane().add(csvBtn);
	   getContentPane().add(cancelBtn);
	   
	   invoiceBtn.addActionListener(this);
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
			String fnm = "Statement"+idFmt.format(new Date())+".csv";
			exportCSV(fnm);
		}else if(cmd.equals("Invoice"))
		{
		   GenericDlg prtDlg = new GenericDlg(this, "Invoice Print");
		   int wd = 600, ht=600;
		   prtDlg.setFavourBounds(wd, ht);
		   PrintTextBox tbx = new PrintTextBox(prtDlg, wd, ht);
		   String tit = titFd.getText();
		   if(tit!=null && tit.trim().length()>1) tbx.setPageHeader(tit);
		   writeTxtPane(tbx);
		   prtDlg.addPanel(tbx);
		   prtDlg.setVisible(true);		
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
		{	fresh(); }
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
	{//write header first
		try
		{
			for(int c=0; c<heads.length; c++)
			{
				if(c<heads.length-1)
					wrt.append(heads[c]+", ");
				else
					wrt.append(heads[c]);
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
			Date de1 = idFmt.parse(bkd1.getEndDate());
			String dts = dateFmt.format(de1);
			wrt.append(dts+", ");
			String cnm = cs.getForename()+" "+cs.getSurname();
			wrt.append(cnm+", ");
			wrt.append(bkd1.getNightNbr()+", ");
			wrt.append(cs.getCountry()+", ");
			wrt.append(cs.getPayMethod()+", ");
			wrt.append(cs.getRoomFee()+", ");
			wrt.append(cs.getAddFee()+", "); 
			wrt.append(cs.getEndAmount());				
			wrt.append("\n");
			wrt.flush();	        
		  }catch(Exception ex) {}
		}		
	}
	
	public void writeTxtPane(PrintTextBox tbx)
	{		
	   try
	   {
		  String spaces = new String("          ");
		  String key = bidList.get(reportPane.getSelectedRow());
		  BookDetail bkd1 =(BookDetail)frm.bkBus.getBookMap().find(key);
		  SLKCustomer cs =(SLKCustomer)frm.bkBus.getCustMap().find(bkd1.getCustId());
		  String cnm = cs.getForename()+" "+cs.getSurname();
		  tbx.writeTxt("Guest Name:"+spaces+cnm+"\n");
		  tbx.writeTxt("Number of guest staying"+spaces);
		  tbx.writeTxt("Adults: "+cs.getGroupAdultNbr()+";       ");
		  tbx.writeTxt("Children: "+cs.getGroupChildNbr());
		  tbx.writeTxt("\nRoom(s):\n"+spaces);
		  List<String> lst = cs.getBookIds();
		  for(int i=0; i<lst.size(); i++)
		  {
			 BookDetail bkx =(BookDetail)frm.bkBus.getBookMap().find(lst.get(i));
			 if(bkx !=null)
			 {
			    tbx.writeTxt(spaces+spaces);	 
			    tbx.writeTxt(bkx.getRoomName()+" and price: " +bkx.getRoomPrice()+".\n");
			 }
		  }
		  Date ds1 = idFmt.parse(bkd1.getStartDate());
		  Date de1 = idFmt.parse(bkd1.getEndDate());
		  String dts = dateFmt.format(ds1)+" to "+dateFmt.format(de1);
		  tbx.writeTxt("\nDate of Stay: "+spaces+dts);
		  tbx.writeTxt("\nCost of Bed and Breadfast"+spaces);
		  tbx.writeTxt(cs.getRoomFee());
		  tbx.writeTxt("\nMeal per person:"+spaces+cs.getMealPrice());
		  tbx.writeTxt("\nTotal number of meals: "+spaces+cs.getMealNbr());
		  tbx.writeTxt(spaces+"Sub Total: "+cs.getMealFee());
		  tbx.writeTxt("\nDiscount: "+spaces+cs.getDiscount());
		  tbx.writeTxt("\nAddition Fee: "+spaces+cs.getAddFee());
		  tbx.writeTxt("\nTotal: "+spaces+cs.getEndAmount());
		  tbx.writeTxt("\nPaid by: "+spaces+cs.getPayMethod());
		  tbx.writeTxt("\n\nThank you");
		  tbx.writeTxt("\nWe hope you enjoyed your staty");		  
		}catch(Exception ex) {}
		
	}
	protected void fresh()//using checkout date inclusive
	{
	   String sd0 = formatChange(startFd.getText(), false); //in id format
	   String ed0 = formatChange(endFd.getText(), false); //yyyyMMdd
	   List<List<String>> tb = new ArrayList<List<String>>();
	   //List<String> lst = frm.bkBus.getBookMap().getCodes();
	   List<String> lst = frm.bkBus.getCustMap().getCodes();
	   List<String> tmps = new ArrayList<String>();
	   int rowSize = 0;
	   for(int i=0; i<lst.size(); i++)
	   {
		  List<String> row = new ArrayList<String>();
		  try
		  {
			String key = lst.get(i);
			SLKCustomer cs =(SLKCustomer)frm.bkBus.getCustMap().find(key);
			BookDetail bkd1 = (BookDetail)frm.bkBus.getBookMap().find(cs.getBookId(0));
			String ed1 = bkd1.getEndDate();
			if(sd0 != null && ed1.compareTo(sd0)<0) continue; //filter end date
			if(ed0 != null && ed1.compareTo(ed0)>0) continue;			
			String cnm = cs.getForename()+" "+cs.getSurname();
			if(cnm == null) cnm = new String();
	        String dts = formatChange(bkd1.getEndDate(), true);
			row.add(dts);
			row.add(cnm);			
			row.add(bkd1.getNightNbr());
			row.add(cs.getCountry());
			row.add(cs.getPayMethod());
			row.add(cs.getRoomFee());
			row.add(cs.getAddFee()); 
			row.add(cs.getEndAmount());						
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
