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
import slkcalendar.DateTable;

public class BKEditorDlg extends JDialog implements ActionListener
{
	static public final long serialVersionUID = DateTable.serialVersionUID;
	java.text.DecimalFormat df = new
	java.text.DecimalFormat("######.##");
	
	CustEditorPane custPane;
	RoomPane roomPane;
	BookEditPane bookPane;
	BKFrame frm;

	JButton saveBtn, computeBtn, cancelBtn, emailBtn, textBtn; 
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	SLKCustomer cst;
	int monthx, yearx;
	Desktop desktop;	
	BookDetail work; //primary booking object		
	int selected[];
	
	public BKEditorDlg(BKFrame frm, String title, int sel[])
	{
		super(frm);
		selected = new int[sel.length];
		for(int i=0; i<sel.length; i++)
			selected[i] = sel[i];
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
	public void setBookDetail(BookDetail bd)
	{ 
		work = bd;
		cst = (SLKCustomer)frm.bkBus.getCustMap().find(work.getCustId());
	}
	public void initDlg() 
	{
	   bookPane = new BookEditPane(this);
	   bookPane.setBook(work);
	   bookPane.initPane();		  
	   bookPane.setBounds(3, 2, 230, 263);
	   getContentPane().add(bookPane);
	   
	   custPane = new CustEditorPane(this);
	   custPane.initPane();
	   custPane.setBounds(233, 2, 315, 263);
	   getContentPane().add(custPane);
	   
	   roomPane = new RoomPane(this);
	   roomPane.initPane();
	   roomPane.setBounds(3, 266, 545, 220);	  
	   getContentPane().add(roomPane);
	   
	   computeBtn = new JButton("Compute");
	   emailBtn = new JButton("Email");
	   saveBtn = new JButton("Save");
	   cancelBtn = new JButton("Close");
	   cancelBtn.setActionCommand("Cancel");
	   textBtn = new JButton("Text");
	   
	   int x0=30, y0=500, w0=90, h0=20, off=10;
	   computeBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   saveBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   emailBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   cancelBtn.setBounds(x0, y0, w0, h0);
	   x0 = x0+w0+off;
	   textBtn.setBounds(x0, y0, w0, h0);
	   
	   getContentPane().add(emailBtn);
	   getContentPane().add(computeBtn);
	   getContentPane().add(saveBtn);
	   getContentPane().add(cancelBtn);
	   getContentPane().add(textBtn);
	   
	   //searchBtn.addActionListener(this);
	   saveBtn.addActionListener(this);
	   cancelBtn.addActionListener(this);
	   computeBtn.addActionListener(this);
	   emailBtn.addActionListener(this);
	   if (Desktop.isDesktopSupported())
	   {
	      desktop = Desktop.getDesktop();
	      emailBtn.setEnabled(desktop.isSupported(Desktop.Action.MAIL));
	   }else emailBtn.setEnabled(false);
	   textBtn.addActionListener(this);
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
		
		if(cmd.equals("Cancel"))
		{
			dispose();
			setVisible(false);
		}else if(cmd.equals("Save"))
		{
			resetData();
			if(cst.getContact()==null)
			{
				String msgx="At least one of mobile, telephone and email " +
				   		"\nshould be imported, please change it";
				JOptionPane.showMessageDialog(this, msgx, "alert", 
						   JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean deleteFg = false;
			for(int i=0; i<cst.getBookIds().size(); i++)
			{
			   String bidx = cst.getBookId(i);
			   BookDetail bdx =(BookDetail)frm.bkBus.getBookMap().find(bidx);
			   if(bdx.getBookStatus().equals("cancel"))
			   {
				   if(i==0)
				   {
					   String msg2="Are you sure to remove the booking from database " +
					   "\npermanently? It cannot undo";
					   int reply = JOptionPane.showConfirmDialog(this, msg2,
						   "Confirmation",  JOptionPane.YES_NO_OPTION);
					   if (reply == JOptionPane.NO_OPTION) return;
				   }
				   frm.bkBus.getBookMap().rm(bdx.getCode());
				   deleteFg = true;
			   }				   
			}
			if(deleteFg)
			   frm.bkBus.getCustMap().rm(cst.getCode());
			
			frm.bkBus.getBookMap().setSavedFg(false);
			frm.bkBus.saveBookMap();
			frm.bkBus.getCustMap().setSavedFg(false);
			frm.bkBus.saveCustMap();
			frm.bkBus.updateBookStrs();			
			frm.mainPane.freshData();
			dispose();
			setVisible(false);
		}else if(cmd.equals("Email"))
			mailTo();
		else if(cmd.equals("Compute"))
			computePayment();
		else if(cmd.equals("Text"))
			showText();
	}
	
	public void setDlgEditor()
	{		
		custPane.surNameFd.setText(cst.getSurname());
		custPane.forNameFd.setText(cst.getForename());
		custPane.telFd.setText(cst.getTelephone());
		custPane.mobieFd.setText(cst.getMobile());
		custPane.emailFd.setText(cst.getEmail());
		custPane.country1.setText(cst.getCountry());
		custPane.postFd.setText(cst.getPostcode());
		custPane.addr1Fd.setText(cst.getAddress1());
		custPane.addr2Fd.setText(cst.getAddress2());
		custPane.addr3Fd.setText(cst.getAddress3());
		custPane.surNameFd.setEditable(false);
					
		//roomPane.names.setSelectedItem(rm.getName());
		bookPane.arriveFd.setText(cst.getArriveTime());
		try
		{
		    Date std = idFmt.parse(work.getStartDate());
			bookPane.startFd.setText(dateFmt.format(std));
			Date edd = idFmt.parse(work.getEndDate());
			bookPane.endFd.setText(dateFmt.format(edd));
		}catch(Exception ex){ }	
		bookPane.groupFd.setText(cst.getGroupName());
		bookPane.nbrFd.setText(cst.getGroupAdultNbr());
		bookPane.childFd.setText(cst.getGroupChildNbr());
		bookPane.bookBx.setSelectedItem(work.getBookMethod());
		bookPane.bookSttBx.setSelectedItem(work.getBookStatus());
		bookPane.visitFd.setText(cst.getVisReason());
		bookPane.payMtFd.setSelectedItem(cst.getPayMethod());
		bookPane.arriveFd.setText(cst.getArriveTime());
		
		roomPane.nightFd.setText(cst.getRoomFee());
		roomPane.discntFd.setText(cst.getDiscount());	
		//roomPane.totalFd.setText(cst.getEndAmount());
		roomPane.paidFd.setText(cst.getDeposite());
		roomPane.balFd.setText(cst.getBalance());
		roomPane.uidFd.setText(work.getBooker());
		roomPane.timeFd.setText(work.getBookDate());
		roomPane.prcBx.setSelectedItem(work.getPriceBasis());
		roomPane.noteArea.setText(cst.getNote());
		roomPane.infoArea.setText(cst.getAddInfo());
        roomPane.mealInfFd.setText(cst.getDietRequirement());
        roomPane.permealFd.setText(cst.getMealPrice());
		roomPane.addPayFd.setText(cst.getAddFee());
		roomPane.mealNbrFd.setText(cst.getMealNbr());
		roomPane.mealFeeFd.setText(cst.getMealFee());
		roomPane.totalFd.setText(cst.getEndAmount());
	}
	
	protected void resetData()
	{
		cst.setForename(custPane.forNameFd.getText());
		cst.setTelephone(custPane.telFd.getText());
		cst.setMobile(custPane.mobieFd.getText());
		cst.setEmail(custPane.emailFd.getText());
		cst.setCountry(custPane.country1.getText());
		cst.setPostcode(custPane.postFd.getText());
		cst.setAddress1(custPane.addr1Fd.getText());
		cst.setAddress2(custPane.addr2Fd.getText());
		cst.setAddress3(custPane.addr3Fd.getText());
		cst.setAddFee(roomPane.addPayFd.getText());
					
		cst.setArriveTime(bookPane.arriveFd.getText());			
		cst.setGroupName(bookPane.groupFd.getText());
		cst.setGroupAdultNbr(bookPane.nbrFd.getText());
		cst.setGroupChildNbr(bookPane.childFd.getText());
		cst.setPayMethod(bookPane.payMtFd.getSelectedItem().toString());
		cst.setVisReason(bookPane.visitFd.getText());
		cst.setEndAmount(roomPane.totalFd.getText());
		cst.setDeposite(roomPane.paidFd.getText());
		cst.setBalance(roomPane.balFd.getText());
		cst.setAddInfo(roomPane.infoArea.getText());
		cst.setNote(roomPane.noteArea.getText());
		cst.setDietRequirement(roomPane.mealInfFd.getText());
		cst.setMealNbr(roomPane.mealNbrFd.getText());
		cst.setMealPrice(roomPane.permealFd.getText());
		cst.setMealFee(roomPane.mealFeeFd.getText());
		
		for(int i=0; i<cst.getBookIds().size(); i++)
		{
		   String bidx = cst.getBookId(i);
		   BookDetail bdx =(BookDetail)frm.bkBus.getBookMap().find(bidx);
		   bdx.setBookMethod(bookPane.bookBx.getSelectedItem().toString());
		   bdx.setBookStatus(bookPane.bookSttBx.getSelectedItem().toString());		
		   bdx.setPriceBasis(roomPane.prcBx.getSelectedItem().toString());
		}	
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
		double mf = toDouble(roomPane.permealFd.getText())
	              *toInteger(roomPane.mealNbrFd.getText());
		
		if(mf>1)
			roomPane.mealFeeFd.setText(Double.toString(mf));				
		try
		{
		   StringBuffer sbf = new StringBuffer();
		   sbf.append("\n---------------------------------");
		   sbf.append("\nPayment has been updated on:"+dateFmt.format(new Date()));
		   double prc0 = toDouble(roomPane.nightFd.getText());
		   double addF0 = toDouble(roomPane.addPayFd.getText());
		   double meal = toDouble(roomPane.mealFeeFd.getText());
		   double dpst = toDouble(roomPane.paidFd.getText());
		   double disct = toDouble(roomPane.discntFd.getText());
		   double ttal = prc0 + addF0+meal-disct;
		   double bal0 = ttal - dpst;
		   ttal = df.parse(df.format(ttal)).doubleValue();
		   bal0 = df.parse(df.format(bal0)).doubleValue();
		   
		   String ttStr = Double.toString(ttal);
		   String balStr = Double.toString(bal0);
		   String depStr = Double.toString(dpst);
		   
		   sbf.append("\nTotal Fee: "+ttStr);
		   sbf.append("\nDeposite Fee: "+depStr);
		   sbf.append("Balance: "+balStr);
		   
		   roomPane.totalFd.setText(ttStr);
		   roomPane.balFd.setText(balStr);
		   String note = work.getNote();
		   note = note + sbf.toString();
		   work.setNote(note);		   
		}catch(Exception ex) 
		{
			JOptionPane.showMessageDialog(this, ex.toString(), 
				"alert", JOptionPane.ERROR_MESSAGE); 
		}	
	}
	
	protected void showText()
	{
		int wx=700, hx = 500;
		BKTextDlg dlgx = new BKTextDlg(this, "Booking Information in Detail");
		dlgx.setFavourBounds(wx, hx);		
		dlgx.setCust(cst, frm.bkBus);
		dlgx.init();
		dlgx.writeTxtPane();
		dlgx.setVisible(true);				
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
	
		
}
