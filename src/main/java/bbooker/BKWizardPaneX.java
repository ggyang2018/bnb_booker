package bbooker;
/* Last BKWizard 
 * Author Guang Yang
 */

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import guiwidget.GWizardPane;
import javax.swing.*;

public class BKWizardPaneX  extends GWizardPane
{
	static public final long  serialVersionUID = GWizardPane.serialVersionUID;
	java.text.DecimalFormat df = new
	java.text.DecimalFormat("######.##");
	
    BKFrame frm;
    PrintTextBox txtBx;
    JButton prntBtn, mailBtn;
    BKWizardDlg wdlg;
    Desktop desktop;

    public BKWizardPaneX(BKWizardDlg dlg)
	{
		super(dlg, "Booking Confirmation", 3, true);
		super.initPane();
		wdlg = dlg;
		frm = dlg.frm;
		init2();
	}
	
	void init2()
	{
		int y0 = getY0();
		int x0=3, w=getWid()-10, h = getY1()- getY0()-10;
		txtBx = new PrintTextBox(getWDlg(), 10, 10, w, h);
		//writeTxtPane(txtBx);
		txtBx.setBounds(x0+10, y0, w, h);
		add(txtBx);
		int y1 = getY1();
		prntBtn = new JButton("Print"); 
		prntBtn.setBounds(190, y1, 80, 20);
		add(prntBtn);
		mailBtn = new JButton("Email");
		mailBtn.setBounds(100, y1, 80, 20);
		add(mailBtn);
		
		if (Desktop.isDesktopSupported())
		{
		   desktop = Desktop.getDesktop();
		   mailBtn.setEnabled(desktop.isSupported(Desktop.Action.MAIL));
		}else mailBtn.setEnabled(false);					
		prntBtn.addActionListener(this);
		mailBtn.addActionListener(this);
	}
	
	
	public void writeTxtPane()
	{
	   StringBuffer sbf = new StringBuffer();
	   sbf.append("Booking Detail Confirmation");
	   String spaces = new String("          "); //10
	   try
	   {
		 for(int j=0; j<wdlg.getCusts().size(); j++)
		 {
			 SLKCustomer cs = wdlg.getCust(j);
			 String bid0 = cs.getBookId(0);
			 BookDetail bk0 = wdlg.getBook(bid0);
			 String st0 = wdlg.formatChange(bk0.getStartDate(), true);
			 String ed0 = wdlg.formatChange(bk0.getEndDate(), true);
			 sbf.append("\n\nDates: "+st0+ " ---- "+ed0);
			 sbf.append("\nNumber of nights:  "+ bk0.getNightNbr());
			 sbf.append("\nName:  "+cs.getTitle()+cs.getForename()+" "+cs.getSurname());
			 String ctc = cs.getContact();
			 if(ctc == null) ctc = new String();
			 sbf.append("\nContacts:  "+ctc);  		 
			 sbf.append("\nGroup:  "+cs.getGroupName());
			 sbf.append("\nAdults: "+cs.getGroupAdultNbr()+
			    "    and Children:  "+cs.getGroupChildNbr());						 
			 sbf.append("\nRoom(s) booked:  ");
			 for(int i=0; i<cs.getBookIds().size(); i++)
			 {
				 String bid = cs.getBookId(i);
				 BookDetail bkd1 = wdlg.getBook(bid);
				 sbf.append("\n"+spaces+bkd1.getRoomName()+",   ");
				 sbf.append("night/"+bkd1.getPriceBasis()+"="+bkd1.getRoomPrice());
			 }
			 sbf.append("\nArrival Time:  "+cs.getArriveTime());
			 sbf.append("\nEvening Meal Reqired:  "+cs.getMealNbr());
			 sbf.append("\n\nTotal Room Payment:  "+cs.getRoomFee());
			 sbf.append("\nTotal addition Fee:  "+cs.getAddFee());
			 sbf.append("\nTotal discount:  "+cs.getDiscount());
			 sbf.append("\ndeposit:  "+cs.getDeposite());
			 sbf.append("\nTotal Payment:  "+cs.getBalance());	 
			 txtBx.writeTxt(sbf.toString());		
		 }		 		 
	   }catch(Exception ex) 
	   {
		  txtBx.setText(ex.toString());
		  ex.printStackTrace();
	   }	
	}	
	public void backAction(){ }
	public boolean nextAction()
	{ 
		for(int j=0; j<wdlg.getCusts().size(); j++)
		  wdlg.frm.bkBus.getCustMap().add(wdlg.getCust(j));
		wdlg.frm.bkBus.saveCustMap();
		for(int i=0; i<wdlg.books.size(); i++)
			wdlg.frm.bkBus.getBookMap().add(wdlg.books.get(i));
		wdlg.frm.bkBus.saveBookMap();
		wdlg.frm.bkBus.updateBookStrs();
		wdlg.frm.mainPane.freshData();
		wdlg.dispose();
		wdlg.setVisible(false);
		return true;
	}
	
	public void actionPerformed(ActionEvent av)
	{
		super.actionPerformed(av);
		String cmd= av.getActionCommand();
   		if(cmd.equals("Print"))		
		  txtBx.printTxt(false);
   		else if(cmd.equals("Email"))
   		  mailTo();  				
	}
	
	protected String formatChange(String dts, boolean id_dt)
	{
	   SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	   SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
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
	
	protected void mailTo()
	{
	   String mailTo = wdlg.getCust(0).getEmail();
	   URI uriMailTo = null;
	   try 
	   {
		   if (mailTo!= null && mailTo.trim().length()>0)
		   {
			   uriMailTo = new URI("mailto", mailTo.trim(), null);
			   desktop.mail(uriMailTo);
		   } else   desktop.mail();
       }catch(Exception ioe) { ioe.printStackTrace();}      
	}	
}
