/*
 * Independent login module. recerive user's input and output user objects. 
 */

package slklogin;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import guiwidget.*;

public class AccessCtrlDlg extends JDialog  
{
	static public final long  serialVersionUID = 987654321L;
	//static public final Color lightBlue = new Color(120, 210, 255);
	//static public final Color navy = new Color(10, 20, 120);
	 
	boolean isNew;
	GTabbedPane logTabPane;
		//working pane
	LoginPane loginPane;
	SignInPane signPane;
	JTextArea helpPane;
	Dimension ScreenSize, FrameSize;
	
	//data 
    LoginAdpt adpt;
	public AccessCtrlDlg(JFrame owner, LoginAdpt ad)
	{
		super(owner);
		adpt = ad;
		setTitle("Access Control");
		setModal(true);
		addWindowListener
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent evt){closeDlg(); }
				
				//In order to re-display compoenent properly rather than 
				// misaligned. This methods should be called
				public void windowDeiconified(WindowEvent e)
				{
					Dimension dim = getSize();
					Double w = Double.valueOf(dim.getWidth());
					Double h = Double.valueOf(dim.getHeight());
					setSize(w.intValue()+1, h.intValue()+1);
					setSize(w.intValue(), h.intValue());
				}
			}
		);		
	}
	
	public void setInitDlg( )
	{
		loginPane = new LoginPane(this);
		signPane = new SignInPane(this);
		logTabPane = new GTabbedPane();
		helpPane = new JTextArea();
		helpPane.setFont(new Font("SansSerif", Font.BOLD, 12));
		helpPane.setLineWrap(true);
		helpPane.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(helpPane);
		logTabPane.add("Log In", loginPane);
		logTabPane.add("Sign In", signPane);
		logTabPane.add("Instruction", sp);
	    //logTabPane.setBackgroundAt(0, lightBlue);
	    //logTabPane.setBackgroundAt(1, lightBlue);
	    //logTabPane.setBackgroundAt(2, lightBlue);
	    getContentPane().add(logTabPane);
	    ScreenSize = Toolkit.getDefaultToolkit( ).getScreenSize();
	    setInstruction();
	}
	
	protected void closeDlg()
	{	
		ConfigInfo cif = new ConfigInfo();
		/*if(!cif.go())
		{
			String msg1 = "Invalid copyrith, please contact Slicksoft4u:" +
			" \nhttp//www.slicksoft4u.com";
			JOptionPane.showMessageDialog(this, msg1, 
			"alert", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}*/
		dispose();
		setVisible(false);
	}
	
	//set Screen size with offset x, y
	public void setFullScreenBounds(int x, int y)
	{
		 this.setBounds(x, y, 
			             ScreenSize.width-2*x,
						 ScreenSize.height-2*y);
		 FrameSize = new Dimension(ScreenSize.width-2*x,
						           ScreenSize.height-2*y);
	 }
	//set mid of screen with lenght w, h
	public void setMidBounds(int w, int h)
	{
		int x0  = (int)Math.floor((ScreenSize.width-w)/2);
		int y0  = (int)Math.floor((ScreenSize.height-h)/2);
		setBounds(x0, y0, w, h);
		FrameSize = new Dimension(w,h);
	}
	
	//----------------- instruction information -----------
	void setInstruction()
	{
		StringBuffer help_txt = new StringBuffer();
		help_txt.append("Before use this software, you should read the following terms first ");
		help_txt.append("(after the simple login instruction) \n\n Login Instruction\n\n");
		help_txt.append("1. First user has to sign in by select the SignIn Tab. If you want to be administrator whoo" +
				"can configure rooms, select the administrator type.\n");
		help_txt.append("2. Existing user select its sign in user ID from combo box and type in password to login in");
		help_txt.append("\n\nUSAGE DENOTES ACCEPTANCE OF TERMS OF USE FOR THIS SOFTWARE PRODUCT: ");
		help_txt.append("YOU MUST READ THESE TERMS OF USE (\"TERMS\") CAREFULLY \r\n\r\n");
		help_txt.append("1. Slicksoft4u gives you a license to use this software. The license " +
				" is based on per machine. \r\n\r\n ");		   
		help_txt.append("2. YOUR OBLIGATIONS AND CONDUCT \r\n ");
		help_txt.append("In consideration of your use of the Booking System, You agree to: ");
		help_txt.append("have sole responsibility for adequate protection of your data. \r\n \r\n");
		   		
		help_txt.append("3. LIMITATION OF LIABILITY \r\n");
		help_txt.append("To the full extent permitted by law, Slicksoft4u are not ");
		help_txt.append("liable for any direct, indirect, punitive,special, ");
		help_txt.append("consequential, or exemplary damages (including, without limitation, ");
		help_txt.append("loss of business, revenue, profits, goodwill, use, data, ");
		help_txt.append("electronically transmitted orders, or other economic advantage) ");
		   
		help_txt.append("arising out of or in connection with the website, even if Slicksoft4u has ");
		help_txt.append("previously been advised of, or reasonably could have foreseen, ");
		help_txt.append("the possibility of the damages.");	
		helpPane.setText(help_txt.toString());
	}
	
	
}
