package bbooker;

/* Customer panel to enter a customer details
 * @Author Guang Yang
 */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

import guiwidget.XYLayout;


public class CustEditorPane extends JPanel implements ActionListener
{
	static public final long serialVersionUID = BookDlg.serialVersionUID;
	protected JTextField surNameFd, forNameFd, telFd, mobieFd;
	protected JTextField emailFd; // addPayFd, nightFd;
	protected JTextField country1;
	protected JTextField postFd, addr1Fd, addr2Fd, addr3Fd;
	//JButton searchBtn;
	//---- container
	BKEditorDlg bookDlg; 
	
	public CustEditorPane(BKEditorDlg dlg)
	{
	   bookDlg = dlg;
	   setLayout(new XYLayout());
	   Border aBorder = BorderFactory.createRaisedBevelBorder();
	   setBorder(aBorder);
	   //initPane();
	}
	public void initPane()
	{
	   int x0=5, y0=5, h=20, w=70, w1=215, off=3;
	   JLabel ptit = new JLabel("Customer Details", JLabel.RIGHT);
	   ptit.setBounds(x0, y0, 165, h);
	   /*ImageIcon cup = new ImageIcon("iconimg/find24.gif");
	   searchBtn = new JButton(cup);
	   searchBtn.setActionCommand("Search");
	   searchBtn.setBounds(175, y0-2, 40, h);
	   add(searchBtn);
	   searchBtn.addActionListener(this);
	   */
	   y0 = h+5;
	   JLabel srnm = new JLabel("Surname:", JLabel.RIGHT);
	   srnm.setBounds(x0, y0, w, h);
	   surNameFd = new JTextField();
	   surNameFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel frnm = new JLabel("Forename:", JLabel.RIGHT);
	   frnm.setBounds(x0, y0, w, h);
	   forNameFd = new JTextField();
	   forNameFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel tel = new JLabel("Telephone:", JLabel.RIGHT);
	   tel.setBounds(x0, y0, w, h);
	   telFd = new JTextField();
	   telFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;	   
	   JLabel mob = new JLabel("Mobile:", JLabel.RIGHT);
	   mob.setBounds(x0, y0, w, h);
	   mobieFd = new JTextField();
	   mobieFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel eml = new JLabel("Email:", JLabel.RIGHT);
	   eml.setBounds(x0, y0, w, h);
	   emailFd = new JTextField();
	   emailFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel cntry = new JLabel("Country:", JLabel.RIGHT);
	   cntry.setBounds(x0, y0, w, h);
	   country1 = new JTextField();
	   country1.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel psd = new JLabel("Postcode:", JLabel.RIGHT);
	   psd.setBounds(x0, y0, w, h);
	   postFd = new JTextField();
	   postFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add1 = new JLabel("Address1:", JLabel.RIGHT);
	   add1.setBounds(x0, y0, w, h);
	   addr1Fd = new JTextField();
	   addr1Fd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add2 = new JLabel("Address2:", JLabel.RIGHT);
	   add2.setBounds(x0, y0, w, h);
	   addr2Fd = new JTextField();
	   addr2Fd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add3 = new JLabel("Address3:", JLabel.RIGHT);
	   add3.setBounds(x0, y0, w, h);
	   addr3Fd = new JTextField();
	   addr3Fd.setBounds(x0+w+5, y0, w1, h);	   	   
	   //y0=y0+h+3*off;
	    add(ptit);
	   add(srnm); 	add(surNameFd);
	   add(frnm); 	add(forNameFd);
	   add(tel);  	add(telFd);
	   add(mob);	add(mobieFd);
	   add(eml);	add(emailFd);
	   add(cntry);	add(country1);
	   add(psd);	add(postFd);
	   add(add1);	add(addr1Fd);
	   add(add2);	add(addr2Fd);
	   add(add3);	add(addr3Fd);
	}	
	
	public void actionPerformed(ActionEvent av)
	{
	   String cmd = av.getActionCommand();
	   if(cmd.equals("Search"))
	   {
		 
	   }		   
	}
}
