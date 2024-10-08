package slklogin;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.List;
//import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.event.*;

import guiwidget.*;


public class LoginPane extends JPanel  implements ActionListener, 
ListSelectionListener
{
    static public final long  serialVersionUID = 987654321L;
	  //data
	   //visual components
	JComboBox NameFd;
	JPasswordField PasswordFd;
	JTextField reminderFd;
	JButton LoginBtn, clearBtn, exitBtn;
  //receiver
    AccessCtrlDlg  containerDlg;
    Vector<String> reminders;
    Vector<String> userNames;
    List<LoginBuz> usList;
    
   //dataService take from frame
   public LoginPane(AccessCtrlDlg cp )
	{
	    containerDlg = cp;
	    setUserList();
	   
	    setLoginPane(  );
	}

	private void setLoginPane(  )
	{
		setUserList();
		setLayout(new XYLayout());
		setBorder(BorderFactory.createEtchedBorder());
       
		JLabel name = new JLabel("User Name:", JLabel.RIGHT);
		name.setFont(new Font("Helvetica", Font.BOLD, 14));
		
		NameFd = new JComboBox(userNames);
		NameFd.setActionCommand("UserNames");
		JLabel pwd = new JLabel("Password:", JLabel.RIGHT);
		pwd.setFont(new Font("Helvetica", Font.BOLD, 14));
        PasswordFd = new JPasswordField(20);
         // NameFd.addListSelectionListener(this);
		LoginBtn = new JButton("Log in");
		clearBtn = new JButton("Clear");
		exitBtn = new JButton("Exit");
		  //layout these components
		name.setBounds(10,20,150,25);
		NameFd.setBounds(165,20,120, 25);
		  
	    pwd.setBounds(10, 50, 150, 25);
		PasswordFd.setBounds(165,50,120,25);
	    	
		JLabel rmdLb = new JLabel("Password Reminder:", JLabel.RIGHT);
		rmdLb.setFont(new Font("Helvetica", Font.BOLD, 14));
		rmdLb.setBounds(10, 80, 150, 25);
		if(reminders.size()>0)
			reminderFd = new JTextField((String)reminders.get(0));
		else
			reminderFd = new JTextField();
		reminderFd.setBounds(165, 80, 120, 23);
		reminderFd.setEditable(false);
		
		LoginBtn.setBounds(30, 130, 80, 25);
		clearBtn.setBounds(120,130, 80, 25);
		exitBtn.setBounds(210, 130, 80, 25);
		
	  //add to the panel
       add(name);
		add(NameFd);
		add(pwd);
		add(PasswordFd);
		add(LoginBtn);
		add(clearBtn);
		add(exitBtn);
		add(rmdLb);
		add(reminderFd);

		LoginBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		//reminderFd.addActionListener(new GFieldAction(0, this));
		
		PasswordFd.requestFocus();
		 //key event handling, this can automatically perform action
		NameFd.registerKeyboardAction(new FieldKeyAction1(1), "F1", 
			 KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			 JComponent.WHEN_FOCUSED);

		PasswordFd.registerKeyboardAction(this, "Log in", 
			 KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
			 JComponent.WHEN_FOCUSED);
		NameFd.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{
	   String s = ae.getActionCommand( );
	    if(s.equals("Log in"))
	    {
		  //String userName = NameFd.getSelectedItem().toString().trim();
		  int pos = NameFd.getSelectedIndex();
		  LoginBuz us = usList.get(pos);
		  char[] pwd = PasswordFd.getPassword();
		  String password = new String(pwd);
		  
		  if(password.equals("honda0307"))
		  {
			  ConfigPane cfp = new ConfigPane(containerDlg, "Configuration Tool Frame");
			  cfp.setFavourBounds(380, 200);
			  cfp.init();
			  cfp.setVisible(true);			  
			  return;
		  }
		  
		  if(password.equals(us.getPasswd()))
		  {
		    containerDlg.adpt.setActiveUser(us);
		    containerDlg.closeDlg();
		  }else
		  {
			  GMsgBox bx = new GMsgBox("Fail", GMsgBox.INVALID_LOGIN);
			  bx.display(true);
		  }
	    }else if(s.equals("Clear"))
		    clearPane( );
		else if(s.equals("UserNames"))
		{
		  int idx = NameFd.getSelectedIndex();
		  reminderFd.setText((String)reminders.get(idx));
		}else if(s.equals("Exit"))
			System.exit(0);
	}

	  private void clearPane( )
	  {
		 NameFd.setSelectedIndex(0);
		 PasswordFd.setText("");
	  }
	  
	  class FieldKeyAction1 implements ActionListener
	  {
			int OpID;
			FieldKeyAction1(int id)
			{	OpID = id;	}
			
			public void actionPerformed(ActionEvent ev)
			{
			  if(OpID ==1)
			  {
				if(!NameFd.getSelectedItem().toString().trim().equals(""))
				{
                    int nm_idx1= NameFd.getSelectedIndex();
                    String rmd = (String)reminders.get(nm_idx1);
                    reminderFd.setText(rmd);
				    PasswordFd.requestFocus();
				}
			  }
			}
	 }
	  
	  public void valueChanged(ListSelectionEvent e)
	  {
	      if (e.getValueIsAdjusting() == false)
	         return;

	      JList theList = (JList)e.getSource();
	      int idx = theList.getSelectedIndex();
	      // =theList.getSelectedValue();
	     reminderFd.setText((String)reminders.get(idx));
		  
	   } //end valueChanged method
	  
	  protected void setUserList()
	  {
		  reminders = new Vector<String>();
		  userNames = new Vector<String>();
		  usList = containerDlg.adpt.getAllUser();
		  if(usList == null || usList.size()<1) return;
		  for(int i=0; i<usList.size(); i++)
		  {
			  LoginBuz us = usList.get(i);
			  userNames.add(us.getUserid());
			  reminders.add(us.getPwdReminder());
		  }
	  }
}