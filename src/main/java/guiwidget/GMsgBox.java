package guiwidget;

/*
 * Generic mssage dialog with predefined data and other feature aim at
 * reduce programming. invocation procedure, create object, setText, dispaly
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GMsgBox extends JDialog implements ActionListener
 {
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	//types of dialog 
	static public final int INVALID_LOGIN		= 0;
	static public final int FAIL_CONFIRM_PWD 	= 1;
	static public final int LESS_LENGTH 		= 2;
	
	JButton  closeBtn, okBtn, goBtn, cancelBtn;
	JTextPane TxtPane = new JTextPane();
	final int type;
	
	public GMsgBox(Frame frm, String title, int type)
	{
		super(frm);
		this.type = type;
		setTitle(title); 
		getContentPane().setLayout(new XYLayout());
		//getContentPane().setBackground(AccessCtrlDlg.lightBlue);
		
		this.setBounds(200, 150, 350, 250);
	    init();
	}
	 
	public GMsgBox(String title, int type)
	{
		this.type = type;
		setTitle(title); 
		getContentPane().setLayout(new XYLayout());
		//getContentPane().setBackground(AccessCtrlDlg.lightBlue);
		this.setBounds(200, 150, 350, 260);
	    init();		
	}
	
	public void setContext(String s){TxtPane.setText(s); };
	public void display(boolean md)
	{
		 this.setModal(md);
		 this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev)
	{  
		String cmd = ev.getActionCommand();
		if(cmd.equals("Close"))
		{
			setVisible(false);
			dispose();
		}
	}
	
	private void init( )
	{
		switch(type)
		{
			case INVALID_LOGIN:
			{
				setLoginBox();
				break;
			}case FAIL_CONFIRM_PWD:
			{
				 setSignUpBox(1);
				break;
			}case LESS_LENGTH:
			{
			   setSignUpBox(0);
		       break;		
			}default:
			{
				break;
			}
		}
	}
	
	public void setLoginBox( )
	{
		//setHead("Information24.gif", "Login Error !");
		setHead("Stop24.gif", "Login Error !");
		setTextPane(getLoginErrorMsg( ));
		closeBtn = new JButton("Close");
		closeBtn.setBounds(100, 200, 100, 25);
		getContentPane().add(closeBtn);
		closeBtn.addActionListener(this);
	}
	
	public void setSignUpBox(int md )
	{
		setHead("Information24.gif", "Warning: Not Long Enough");
		setTextPane(getSignUpMsg(md));
		closeBtn = new JButton("Close");
		closeBtn.setBounds(100, 200, 100, 25);
		getContentPane().add(closeBtn);
		closeBtn.addActionListener(this);
		
	}
	
	void setTextPane(String txt)
	{
		/*StyledDocument doc = TxtPane.getStyledDocument();
			doc.insertString(doc.getLength(), txt,
					 doc.getStyle(initStyles[i]));
		 */ 
		TxtPane.setText(txt);
		//TxtPane.setBackground(AccessCtrlDlg.lightBlue);
		//TxtPane.setForeground(AccessCtrlDlg.navy);
		TxtPane.setFont(new Font("Helvetica", Font.BOLD, 12));
 		JScrollPane jsrp = new JScrollPane(TxtPane);
 		jsrp.setHorizontalScrollBarPolicy
 		(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
		jsrp.setBounds(10, 50, 320, 140);
		getContentPane().add(jsrp);
	}
    void setHead(String img_name, String head)
	{
		JLabel title1 = new JLabel(head, JLabel.CENTER);
		title1.setFont(new Font("Helvetica", Font.BOLD, 16));
		//title1.setForeground(AccessCtrlDlg.navy);
		//title1.setBackground(AccessCtrlDlg.lightBlue);
		title1.setBounds(20, 10, 150, 30);
		getContentPane().add(title1);
		try
		{
			URL imageURL = this.getClass().getResource("/rsrc/images/"
					+ img_name);
			if(imageURL ==null) return;

			Image iconImg = Toolkit.getDefaultToolkit ().getImage (imageURL);
			Image Img=iconImg.getScaledInstance(40, 40, 
							Image.SCALE_AREA_AVERAGING); 
			ImageIcon 	imgIcon = new ImageIcon (Img);
			JLabel imgLb = new JLabel(imgIcon);
			imgLb.setBounds(280, 5, 40, 40);
			getContentPane().add(imgLb);
		}catch(Exception ex) { System.out.println(ex.toString()); }
	}
	
	 
	 public String getLoginErrorMsg( )
	 {  	
		StringBuffer sb = new StringBuffer( );
		sb.append("The password you typed in are not validated against ");
		sb.append("the selected user name. Please enter validated ");
		sb.append("password. For new user please go to Sign In panel to registry ");
		return sb.toString();
	 }
	 
	 public String getSignUpMsg(int md)
	 {
		StringBuffer sbf = new StringBuffer();
		if(md==0)
		{
		   sbf.append("The password requires at least 5 characters." );
		   sbf.append(" Please retype your the password.");
		}else
		{
			sbf.append("The typed in password and its confirmation are not ");
			sbf.append(" same; please retype the password and it confirmation. ");
		}
		return sbf.toString();
	 }
	 
	 public String getNotSaveMsg()
	 {
		 StringBuffer bf = new StringBuffer();
		 bf.append("Plase save it");
		 return bf.toString();
	 }
	 
	 public String getErrorMsg()
	 {  
		 StringBuffer bf = new StringBuffer();
		 bf.append("General Error needs do something later !!!");
		 return bf.toString();
	 }
	 
 }


