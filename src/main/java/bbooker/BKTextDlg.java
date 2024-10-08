package bbooker;
/*Text print and copy dialog box
 * @author Guang Yang
 */
import guiwidget.XYLayout;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


public class BKTextDlg extends JDialog implements ActionListener
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	   
	PrintTextBox txtBx;
	Desktop desktop;
	JButton prntBtn, mailBtn, copyBtn, quitBtn;
	int w= 100, h = 100;
	SLKCustomer cust;
	StringBuffer sbf;
	BookBus dbus;
	
	public BKTextDlg(JDialog dlg, String title)
	{
		super(dlg);
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

	public void setCust(SLKCustomer cst, BookBus bus) 
	{ cust = cst;  dbus = bus;}
	public void setFavourBounds(int width, int height)
	{
		w = width; h = height;
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}
	
	public void init()
	{	
		int x0=10, y0=10;
		int y1 = h-60-y0;
		txtBx = new PrintTextBox(this, 10, 10, w-30, y1-10);
		
		//writeTxtPane(txtBx);
		txtBx.setBounds(x0, y0, w-50, y1-20);
		getContentPane().add(txtBx);
		
		copyBtn = new JButton("Copy");
		copyBtn.setBounds(10, y1, 80, 20);
		getContentPane().add(copyBtn);
		mailBtn = new JButton("Email");
		mailBtn.setBounds(100, y1, 80, 20);
		getContentPane().add(mailBtn);
		prntBtn = new JButton("Print"); 
		prntBtn.setBounds(190, y1, 80, 20);
		getContentPane().add(prntBtn);
		quitBtn = new JButton("Quit");
		quitBtn.setBounds(280, y1, 80, 20);
		getContentPane().add(quitBtn);			
		if (Desktop.isDesktopSupported())
		{
		   desktop = Desktop.getDesktop();
		   mailBtn.setEnabled(desktop.isSupported(Desktop.Action.MAIL));
		}else mailBtn.setEnabled(false);			
		
		prntBtn.addActionListener(this);
		mailBtn.addActionListener(this);
		copyBtn.addActionListener(this);
		quitBtn.addActionListener(this);	
	}
		
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("Print"))		
			  txtBx.printTxt(false);
		else if(cmd.equals("Email"))
	   		  mailTo();  		
		else if(cmd.equals("Quit"))
		{
			dispose();
			setVisible(false);
		}else if(cmd.equals("Copy"))
	   		  copyTo();
	}
	
	protected void mailTo()
	{
	   String mailTo = cust.getEmail();
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
	
	public void writeTxtPane()
	{
	   sbf = new StringBuffer();
	   sbf.append("Booking Detail Confirmation");   
	   try
	   {
		   String bid0 = cust.getBookId(0);
		   BookDetail bk0 = (BookDetail)dbus.getBookMap().find(bid0);
		   String st0 = formatChange(bk0.getStartDate(), true);
		   String ed0 = formatChange(bk0.getEndDate(), true);
		   sbf.append("\n\nDates: "+st0+ " ---- "+ed0);
		   sbf.append("\n Number of nights: "+ bk0.getNightNbr());		 
		   sbf.append("\nName: "+cust.getTitle()+cust.getForename()+" "+cust.getSurname());
		   String ctc = cust.getContact();
		   if(ctc == null) ctc = new String();
		   sbf.append("\nContacts:  "+ctc);  		 
		   sbf.append("\nGroup:  "+cust.getGroupName());
		   sbf.append("\nGroup Adult Number: "+cust.getGroupAdultNbr()+
			    "    and Children Number:  "+cust.getGroupChildNbr());
		   sbf.append("\nRoom(s) booked:");
		   for(int i=0; i<cust.getBookIds().size(); i++)
		   {
			   String bid = cust.getBookId(i);
			   BookDetail bkd1 = (BookDetail)dbus.getBookMap().find(bid);
			   sbf.append(bkd1.getRoomName()+", ");
		   }
		   sbf.append("\nArrive Time: "+cust.getArriveTime());
		   sbf.append("\nHow many evening Meal:  "+cust.getMealNbr());
		   sbf.append("\nTotal Room Payment: "+cust.getRoomFee());
		   sbf.append("\nTotal addition Fee: "+cust.getAddFee());
		   sbf.append("\nTotal discount: "+cust.getDiscount());
		   sbf.append("\ndeposite: "+cust.getDeposite());
		   sbf.append("\nTotal Payment: "+cust.getBalance());	 
		   txtBx.writeTxt(sbf.toString());				 		 
	   }catch(Exception ex) 
	   {
		  txtBx.setText(ex.toString());
		  ex.printStackTrace();
	   }	
	}
	
	protected void copyTo()
	{
		CopyText ct = new CopyText();
		ct.setString(sbf.toString());
	}
	
	public void pasteFrom( )
	{
		CopyText ct = new CopyText();
		txtBx.setText(ct.getString());
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