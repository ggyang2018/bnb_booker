package slklogin;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import guiwidget.*;
import nxd.DSXMLBuz;

public class SignInPane extends JPanel implements ActionListener
{
	static public final long  serialVersionUID = 987654321L;
	
	JComboBox typeBox;
	String types[]= {"General User", "Administrator" };
	JTextField NameFd;
	JPasswordField PasswordFd;
	JPasswordField confirmFd;
	JTextField reminderFd;
	JButton submitBtn, clearBtn, exitBtn;
	boolean pwdFlag;
	AccessCtrlDlg loginDlg;
			
	public SignInPane(AccessCtrlDlg dlg)
	{	
		loginDlg = dlg;
	    setPane(  );
	}

	private void setPane(  )
	{
		setLayout(new XYLayout());
		setBorder(BorderFactory.createEtchedBorder());
	    
		JLabel tpLb = new JLabel("User Type:", JLabel.RIGHT);
		tpLb.setFont(new Font("Helvetica", Font.BOLD, 14));
		typeBox = new JComboBox(types);
		
		JLabel name = new JLabel("User Name:", JLabel.RIGHT);
		name.setFont(new Font("Helvetica", Font.BOLD, 14));
        NameFd = new JTextField( );

		JLabel pwd = new JLabel("Password:", JLabel.RIGHT);
		pwd.setFont(new Font("Helvetica", Font.BOLD, 14));
        PasswordFd = new JPasswordField(20);		
        submitBtn = new JButton("Submit");
		clearBtn = new JButton("Clear");
		exitBtn = new JButton("Exit");
		  
		  //layout these components
		int h0 = 5, w0=10, h1=23;
		int vsp=5;
		tpLb.setBounds(w0, h0, 150, 20);
		typeBox.setBounds(165, h0, 120, h1);
		h0=h0+h1+vsp;
		name.setBounds(w0,h0-3,150,h1);
		NameFd.setBounds(165,h0,120, h1);
		
		h0=h0+h1+vsp;
		pwd.setBounds(10, h0-3, 150, 23);
		PasswordFd.setBounds(165,h0, 120, h1);
		
		h0=h0+h1+vsp;
		JLabel confirm = new JLabel("Confirm Password:", JLabel.RIGHT);
		confirm.setFont(new Font("Helvetica", Font.BOLD, 14));
		confirm.setBounds(10, h0-3, 150, h1);
		confirmFd = new JPasswordField( );
		confirmFd.setBounds(165, h0, 120, h1);
		
		h0=h0+h1+vsp;
		JLabel rmdLb = new JLabel("Password Reminer:", JLabel.RIGHT);
		rmdLb.setFont(new Font("Helvetica", Font.BOLD, 14));
		rmdLb.setBounds(10, h0-3, 150, h1);
		reminderFd = new JTextField( );
		reminderFd.setBounds(165, h0, 120, h1);
		
		h0=h0+h1+vsp;
		submitBtn.setBounds(30, h0, 80, 25);
		clearBtn.setBounds(120, h0, 80, 25);
		exitBtn.setBounds(210, h0, 80, 25);
		
		  //add to the panel
       // add(title);
		//add(sp1);
		add(tpLb);
		add(typeBox);
		add(name);
		add(NameFd);
		add(pwd);
		add(PasswordFd);
		add(confirm);
		add(confirmFd);
		add(rmdLb);
		add(reminderFd);
		add(submitBtn);
		add(clearBtn);
		add(exitBtn);
		
		submitBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		//reminderFd.addActionListener(new GFieldAction(0, this));
    
		NameFd.requestFocus();
		 //key event handling, this can automatically perform action
		NameFd.registerKeyboardAction(new FieldKeyAction1(1), "F1", 
			 KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			 JComponent.WHEN_FOCUSED);

		PasswordFd.registerKeyboardAction(new FieldKeyAction1(2), "F2", 
			 KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			 JComponent.WHEN_FOCUSED);
		
		confirmFd.registerKeyboardAction(new FieldKeyAction1(3), "F3", 
			 KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			 JComponent.WHEN_FOCUSED);
         
		 reminderFd.registerKeyboardAction(this, "Submit",
		          KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                  JComponent.WHEN_FOCUSED);
    }
	
    public void actionPerformed(ActionEvent ev)
	{
	   String cmd = ev.getActionCommand();
	   if(cmd.equals("Submit"))
	      submit();
		else if(cmd.equals("Clear"))
		{
		    NameFd.setText("");
			PasswordFd.setText("");
			confirmFd.setText("");
			reminderFd.setText("");
			NameFd.requestFocus();
		}else if(cmd.equals("Exit"))
			loginDlg.closeDlg();
	 }
    
    private void submit( )
    {   
    	String pwd1 = new String(PasswordFd.getPassword());
    	String pwd2 = new String(confirmFd.getPassword());
    	if(pwd1.length()<5)
    	{
    		GMsgBox box = new GMsgBox("Error ", GMsgBox.LESS_LENGTH);
    		box.display(true);
    		PasswordFd.setText("");
    		confirmFd.setText("");
    		PasswordFd.requestFocus();
    		return;
    	}
    	if(!pwd1.equals(pwd2))
    	{
    		GMsgBox box = new GMsgBox("Error ", GMsgBox.FAIL_CONFIRM_PWD);
    		box.display(true);
    		PasswordFd.setText("");
    		confirmFd.setText("");
    		PasswordFd.requestFocus();
    		return;
    	}
    	
    	DSXMLBuz bz1 = new DSXMLBuz("SLKUser", NameFd.getText());
    	LoginBuz usr = new LoginBuz(bz1);
    	usr.setUserid(NameFd.getText());
    	usr.setPasswd(pwd1);
    	usr.setPwdReminder(reminderFd.getText());
    	usr.setUserType(typeBox.getSelectedItem().toString());
    	loginDlg.adpt.setNewUser(usr);
    	loginDlg.closeDlg();
   }
   
	class FieldKeyAction1 implements ActionListener
	{
		int OpID;
		FieldKeyAction1(int id)	{OpID = id;	}
		
		public void actionPerformed(ActionEvent ev)
		{
		  if(OpID ==1)
		  {
			if(!NameFd.getText().trim().equals(""))
			PasswordFd.requestFocus();
		  }
		  
		  if(OpID == 2)
		  {
			 char[] upwd=PasswordFd.getPassword();
			 String ss = new String(upwd);
	         if(ss==null || ss.trim( ).equals(""))
	        	 PasswordFd.requestFocus();
	         else
	        	 confirmFd.requestFocus();
		  }
		  
		  if(OpID == 3)
		  {
			 char[] upwd=confirmFd.getPassword();
			 String ss = new String(upwd);
	         if(ss==null || ss.trim( ).equals(""))
	        	 confirmFd.requestFocus();
	         else
	        	 reminderFd.requestFocus();
		  }
		}
	}
}
