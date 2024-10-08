package bbooker;

/* Bepoke book dialog for perform book procedure
 * All internal date string should be yyyyMMdd
 * @author Guang Yang
 */

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import guiwidget.XYLayout;
import slkcalendar.DateMgr;
import slkcalendar.DateTable;

public class BookDlg extends JDialog implements ActionListener
{
	static public final long serialVersionUID = DateTable.serialVersionUID;
	CustomerPane custPane;
	//RoomPane roomPane;
	BookPane bookPane;
	SelectPane selPane;
	BKFrame frm;

	JButton saveBtn, computeBtn, bookBtn, cancelBtn, emailBtn; 
	boolean isNew = true;
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
//	private SimpleDateFormat uniqFmt = new SimpleDateFormat("yyyyMMddHHmmss");
	String bookId0; //original book Id
	//SLKCustomer cust;
	//BookDetail bookD;
	int monthx, yearx;
	Desktop desktop;
	
	BookDetail work; //primary booking object
	
	int selected[];
	
	public BookDlg(BKFrame frm, String title, int sel[])
	{
		super(frm);
		selected = new int[sel.length];
		for(int i=0; i<sel.length; i++)
			selected[i] = sel[i];
		this.frm = frm;
		setModal(true);
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
	    
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
	public void setBookDetail(BookDetail bd){ work = bd; }
	public void initDlg() 
	{
	   bookPane = new BookPane(this);
	   bookPane.setHasNew(isNew);
	   bookPane.initPane();		  
	   bookPane.setBounds(3, 2, 230, 485);
	   getContentPane().add(bookPane);
	   
	   custPane = new CustomerPane(this);
	   custPane.setHasNew(isNew);
	   custPane.initPane();
	   custPane.setBounds(233, 2, 315, 290);
	   getContentPane().add(custPane);
	   //roomPane = new RoomPane(this);
	  // roomPane.setHasNew(isNew);
	   //roomPane.initPane();
	   //roomPane.setBounds(233, 313, 230, 145);	  
	   //getContentPane().add(roomPane);
	   
	   selPane = new SelectPane(this);
	   selPane.initPane();
	   selPane.setBounds(233, 293, 315, 195);	  
	   getContentPane().add(selPane);
	   
	   computeBtn = new JButton("Compute");
	   emailBtn = new JButton("Email");
	   bookBtn = new JButton("Book");
	   saveBtn = new JButton("Save");
	   cancelBtn = new JButton("Close");
	   cancelBtn.setActionCommand("Cancel");
	   
	   int x0=30, y0=500, w0=90, h0=20, off=10;
	   computeBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   bookBtn.setBounds(x0, y0, w0, h0);
	   saveBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   emailBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   cancelBtn.setBounds(x0, y0, w0, h0);
	   getContentPane().add(emailBtn);
	   getContentPane().add(computeBtn);
	   if(!isNew) getContentPane().add(saveBtn);
	   else getContentPane().add(bookBtn);
	   getContentPane().add(cancelBtn);
	   
	   //searchBtn.addActionListener(this);
	   saveBtn.addActionListener(this);
	   bookBtn.addActionListener(this);
	   cancelBtn.addActionListener(this);
	   computeBtn.addActionListener(this);
	   emailBtn.addActionListener(this);
	   if (Desktop.isDesktopSupported())
	   {
	      desktop = Desktop.getDesktop();
	      emailBtn.setEnabled(desktop.isSupported(Desktop.Action.MAIL));
	   }else emailBtn.setEnabled(false);		   
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
	
	public void setHasNew(boolean has) { isNew = has; }
		
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		
		if(cmd.equals("Cancel"))
		{
			dispose();
			setVisible(false);
		}else if(cmd.equals("Book"))
		{			
		   SLKCustomer cust = setCust();
		   if(cust==null)
		   {
			  String msg1 = "Guest's surname is mandatory, " +
		    			"\nplease import";
			  JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);
			  return;
		   }									
		   //set id
		   String rid = frm.bkBus.getRoomMap().getCodes().get(selected[3]);
		   BookDetail bookD = setBookD(rid);					    
		   if(bookD == null)
		   {
			  String msg1 = "This start date has been specified , " +
		    			"\nplease change it ";
			  JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);
			  return;
		   }
		   bookD.setCustId(cust.getCode());			
		   String xid = frm.bkBus.findBookDetailId(bookD.getStartDate(), bookD.getRoomId());
		   if(xid==null)
		   {
			  frm.bkBus.getBookMap().add(bookD);
			  linkedBook(bookD);
			  frm.mainPane.freshData();
		   }else
		   {
			  String msg1 = "This override the previous booking, " +
		    			"\nplease change it or delete privous one";
			  JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);			
		   }
		  
		   
			//dispose();
			//setVisible(false);			
		}else if(cmd.equals("Save"))
		{
			/*String oldStr = bookD.getBookId()+"::"+bookD.getBookStatus();
			setCust();
			setBookD();
			if(bookD.getBookStatus().equals("cancel"))
				frm.bus.removeElm(bookId0, oldStr);
			else
			{
				frm.bus.addCustomer(cust);
				frm.bus.addBookDetail(bookD);			    
			}*/
			frm.mainPane.freshData();
		}else if(cmd.equals("Email"))
			mailTo();
		else if(cmd.equals("Compute"))
			computePayment();		   			
	}
	
	public void setInitParam(BookDetail bdk)
	{
		work = bdk;
		setStartDateFd(bdk.getStartDate());	
	}
	
	void setStartDateFd(String s)
	{
		try
		{
			Date sdt = idFmt.parse(s);
			bookPane.startFd.setText(dateFmt.format(sdt));
			DateMgr dm = new DateMgr();
			Date edt = dm.calEndDate(sdt, 1);
			bookPane.endFd.setText(dateFmt.format(edt));
			bookPane.nightFd.setText(bookPane.getNbrNight());
		}catch(Exception ex)
		{
			System.out.println("BOOKDlg210"); 
			ex.printStackTrace();
		}
	}	
	
	public void setDlgEditor(BookDetail bkd, RoomData rm, SLKCustomer cst)
	{
		work = bkd;
		bookId0 = bkd.getBookId();
		custPane.surNameFd.setText(cst.getSurname());
		custPane.forNameFd.setText(cst.getForename());
		custPane.telFd.setText(cst.getTelephone());
		custPane.mobieFd.setText(cst.getMobile());
		custPane.emailFd.setText(cst.getEmail());
		custPane.country.setSelectedItem(cst.getCountry());
		custPane.postFd.setText(cst.getPostcode());
		custPane.addr1Fd.setText(cst.getAddress1());
		custPane.addr2Fd.setText(cst.getAddress2());
		custPane.addr3Fd.setText(cst.getAddress3());
		custPane.dietReqFd.setText(cst.getDietRequirement());
					
		//roomPane.names.setSelectedItem(rm.getName());
		bookPane.arriveFd.setText(bkd.getArriveTime());
		try
		{
		    Date std = idFmt.parse(bkd.getStartDate());
			bookPane.startFd.setText(dateFmt.format(std));
			Date edd = idFmt.parse(bkd.getEndDate());
			bookPane.endFd.setText(dateFmt.format(edd));
		}catch(Exception ex){ }
				
		bookPane.groupFd.setText(cst.getGroupName());
		bookPane.payMtFd.setSelectedItem(cst.getPayMethod());
		bookPane.paidFd.setText(cst.getDeposite());
		bookPane.balFd.setText(cst.getBalance());
		bookPane.bookSttBx.setSelectedItem(bkd.getBookStatus());
		bookPane.noteArea.setText(bkd.getNote());
		custPane.surNameFd.setEditable(false);
		bookPane.endBtn.setEnabled(false);		
	}
	
	protected void setInitData()
	{
		SLKCustomer cst =(SLKCustomer)frm.bkBus.getCustMap().find(work.getCustId());
		custPane.surNameFd.setText(cst.getSurname());
		custPane.forNameFd.setText(cst.getForename());
		custPane.telFd.setText(cst.getTelephone());
		custPane.mobieFd.setText(cst.getMobile());
		custPane.emailFd.setText(cst.getEmail());
		custPane.country.setSelectedItem(cst.getCountry());
		custPane.postFd.setText(cst.getPostcode());
		custPane.addr1Fd.setText(cst.getAddress1());
		custPane.addr2Fd.setText(cst.getAddress2());
		custPane.addr3Fd.setText(cst.getAddress3());
		custPane.dietReqFd.setText(cst.getDietRequirement());

		bookPane.groupFd.setText(cst.getGroupName());
		bookPane.nbrFd.setText(cst.getGroupAdultNbr());
		bookPane.nbrBox1.setSelectedItem(work.getChildNbr());
		bookPane.nbrBox2.setSelectedItem(work.getRoomDiscount());
		bookPane.payMtFd.setSelectedItem(cst.getPayMethod());
		bookPane.bookBx.setSelectedItem(work.getBookMethod());
		bookPane.bookSttBx.setSelectedItem(work.getBookStatus());
		bookPane.arriveFd.setText(work.getArriveTime());
		bookPane.visitFd.setText(cst.getVisReason());
		try
		{
		    Date std = idFmt.parse(work.getStartDate());
			bookPane.startFd.setText(dateFmt.format(std));
			Date edd = idFmt.parse(work.getEndDate());
			bookPane.endFd.setText(dateFmt.format(edd));
			bookPane.endBtn.setEnabled(false);
		}catch(Exception ex){ }
		bookPane.nightFd.setText(work.getNightNbr());
		bookPane.nightFd.setEditable(false);
		bookPane.addPayFd.setText(cst.getAddPay());
		bookPane.payMtFd.setSelectedItem(cst.getPayMethod());
		bookPane.paidFd.setText(cst.getDeposite());
		bookPane.balFd.setText(cst.getBalance());
		bookPane.totalFd.setText(cst.getEndAmount());		
		bookPane.noteArea.setText(work.getNote());
		bookPane.uidFd.setText(work.getBooker());
		bookPane.timeFd.setText(work.getBookDate());	
	}
	
	protected SLKCustomer setCust()
	{	
		String srnm = custPane.surNameFd.getText();
		if(srnm == null || srnm.length()<2) return null;
		SLKCustomer cust = new SLKCustomer(srnm, 0);
		cust.setSurame(srnm); //not change
		cust.setForename(custPane.forNameFd.getText());
		cust.setTelephone(custPane.telFd.getText());
		cust.setMobile(custPane.mobieFd.getText());
		cust.setEmail(custPane.emailFd.getText());
		String cty = custPane.country.getSelectedItem().toString();
		cust.setCountry(cty);
		cust.setPostcode(custPane.postFd.getText());
		cust.setAddress1(custPane.addr1Fd.getText());
		cust.setAddress2(custPane.addr2Fd.getText());
		cust.setAddress3(custPane.addr3Fd.getText());
		cust.setDietRequirement(custPane.dietReqFd.getText());
		frm.bkBus.getCustMap().add(cust);
	    return cust;
	}
	
	protected BookDetail setBookD(String roomId)
	{
		//int rmi = roomPane.names.getSelectedIndex();
		BookDetail bookD = new BookDetail();
		try
		{
			Date std = dateFmt.parse(bookPane.startFd.getText());
			bookD.setStartDate(idFmt.format(std));
			Date end = dateFmt.parse(bookPane.endFd.getText());
			bookD.setEndDate(idFmt.format(end));
		}catch(Exception ex) { return null; }		
		bookD.setBookId(roomId+"::"+bookD.getStartDate()+"-"+bookD.getEndDate());		
		//bookD.setGroupName(bookPane.groupFd.getText());
		//bookD.setGroupNbr(bookPane.nbrFd.getText());
		bookD.setChildNbr(bookPane.nbrBox1.getSelectedItem().toString());
        bookD.setRoomDiscount(bookPane.nbrBox2.getSelectedItem().toString());
        bookD.setBookMethod(bookPane.bookBx.getSelectedItem().toString());
		bookD.setBookStatus(bookPane.bookSttBx.getSelectedItem().toString());
		bookD.setArriveTime(bookPane.arriveFd.getText());	
	    //bookD.setVisReason(bookPane.visitFd.getText());
		//startDate and endData set before
		//bookD.setAddPay(bookPane.addPayFd.getText());
		bookD.setPriceBasis(bookPane.payMtFd.getSelectedItem().toString());
		//bookD.setBalance(bookPane.balFd.getText());
		//bookD.setDeposite(bookPane.paidFd.getText());
		bookD.setRoomTotalFee(bookPane.totalFd.getText());	
		bookD.setNote(bookPane.noteArea.getText());							
		bookD.setBookDate(bookPane.timeFd.getText());
	    bookD.setBooker(bookPane.uidFd.getText());
	       
		//roomPane	
		bookD.setRoomId(roomId);
		return bookD;
	}
	
	protected void mailTo()
	{
		String mailTo = custPane.emailFd.getText();
        URI uriMailTo = null;
        try 
        {
            if (mailTo!= null && mailTo.trim().length()>0)
            {
                uriMailTo = new URI("mailto", mailTo.trim(), null);
                desktop.mail(uriMailTo);
            } else   desktop.mail();
       } catch(Exception ioe) { ioe.printStackTrace();}      
	}
	
	protected void computePayment()
	{
		try
		{
		   StringBuffer sbf = new StringBuffer();
		   int ngts = Integer.parseInt(bookPane.nightFd.getText());
		   List<RoomData> rms = selPane.getModelData();
		   int idxx = selPane.prcBx.getSelectedIndex();
		   double pr=toDouble(work.getRoomPrice());;
		 
		   if(idxx==0) //per room
		   {
			  sbf.append("Calculation based on Per Romm with various prices"); 
			  for(int i=0; i<rms.size(); i++)
				    pr += rms.get(i).getPrice().doubleValue();
		   }else if(idxx==1)//fix per room
		   {
			   sbf.append("Calculation based on Per Romm with fixed prices");
			   pr = pr*rms.size();
		   }else if(idxx==2) //fix person
		   {
			   sbf.append("Calculation based on Per Person with fix prices");
			   sbf.append("\nincluding child discount");
			   double pr1 = pr;
			   int nbr = toInteger(bookPane.nbrFd.getText());
			   pr = pr*nbr;
			   int chis = toInteger(bookPane.nbrBox1.getSelectedItem().toString());
			   double dsc =toDouble(bookPane.nbrBox2.getSelectedItem().toString());
			   dsc = dsc*chis*pr1;
			   pr = pr - dsc;			   
		   }
		   
		   pr = pr * ngts;
		   sbf.append("\nTotal Room Fee: "+Double.toString(pr));
		   String addStr = bookPane.addPayFd.getText();
		   double add = toDouble(addStr);
		   sbf.append("\nAddition Fee: "+addStr+"\t+");
		   sbf.append("\n---------------------------------");
		   pr +=add;
		   String ttStr = Double.toString(pr);
		   bookPane.totalFd.setText(ttStr);
		   sbf.append("\nTotal Fee + Addition Fee:"+ ttStr);
		   String dpStr = bookPane.paidFd.getText();
		   sbf.append("\nPaid Deposite: "+dpStr);
		   double bal = pr - toDouble(dpStr);		  
		   bookPane.balFd.setText(Double.toString(bal));
		   bookPane.noteArea.setText(sbf.toString());
		}catch(Exception ex) 
		{
			JOptionPane.showMessageDialog(this, ex.toString(), 
				"alert", JOptionPane.ERROR_MESSAGE); 
		}
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
	protected boolean isNumber(String s)
	{   s = s.trim();
		for (int i = 0; i < s.length(); i++) 
		{
			if (!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}
	
	protected double toDouble(String s)
	{
		try 
		{  
			double pr = Double.parseDouble(s.trim());
			return pr;
		}catch(Exception ex) { return 0.0d; }		
	}
	
	protected int toInteger(String s)
	{
		try 
		{  
			int pr = Integer.parseInt(s.trim());
			return pr;
		}catch(Exception ex) { return 0; }		
	}
	
	protected void linkedBook(BookDetail bk)
	{
	   List<RoomData> rms = selPane.getModelData();
	   if(rms != null && rms.size()>0)
	   {
		   StringTokenizer tk = new StringTokenizer(bk.getBookId(), "::");
		   tk.nextToken();
		   String pcd = tk.nextToken();
		   List<BookDetail> bkds = new ArrayList<BookDetail>();			   
		   for(int j=0; j<rms.size();j++)
		   {
			   BookDetail bd = (BookDetail)bk.clone();
			   String bid=rms.get(j).getCode()+"::"+pcd;
			   bd.setBookId(bid);
			   bkds.add(bd);
			   
			   frm.bkBus.getBookMap().add(bd);
		   }		   		      		     	 
	   }
	}
		
}
