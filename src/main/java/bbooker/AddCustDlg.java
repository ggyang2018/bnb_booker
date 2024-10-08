package bbooker;

import guiwidget.XYLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;


public class AddCustDlg extends JDialog implements ActionListener
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	String tt="Additional Room or Other Information";
	JTextArea noteArea;
	JButton canBtn, addBtn;
	SLKCustomer cust;
	
	public AddCustDlg(SLKCustomer cus, BKWizardDlg dlg, String title)
	{
		super(dlg);
		cust = cus;
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

		        Double w = Double.valueOf(dim.getWidth());
		        Double h = Double.valueOf(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		getContentPane().setLayout(new XYLayout());
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
	
	public void init()
	{
		int y0 = 5;
		int x0=10, w=420, h=20;
		
		JLabel noteLb = new JLabel(tt, JLabel.CENTER);
		noteLb.setBounds(x0, y0, w, h);
		y0= y0+h+10;
		JSeparator sp1 = new JSeparator();
		sp1.setBounds(3, y0, w+14, 5);
		getContentPane().add(sp1);
		
		y0=y0+10;
		int nth = 160;
		noteArea = new JTextArea();
		noteArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
		noteArea.setLineWrap(true);
		noteArea.setWrapStyleWord(true);
		noteArea.setText(cust.getAddInfo());
		JScrollPane scrTxt = new JScrollPane(noteArea);
		scrTxt.setBounds(x0, y0,  w, nth);
		getContentPane().add(sp1); 
		getContentPane().add(noteLb); 
		getContentPane().add(scrTxt);
		
		y0= y0+nth+10;
		JSeparator sp2 = new JSeparator();
		sp2.setBounds(3, y0, w+14, 5);
		getContentPane().add(sp2);
		y0= y0+10;
		addBtn = new JButton("Add");
		addBtn.setBounds(220, y0, 80, h);
		canBtn = new JButton("Cancel");
		canBtn.setBounds(310, y0, 80, h);
		getContentPane().add(addBtn);
		getContentPane().add(canBtn);
		
		addBtn.addActionListener(this);
		canBtn.addActionListener(this);
	}
		
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("Add"))
		{
			cust.setAddInfo(noteArea.getText());
			dispose();
			setVisible(false);
		}else if(cmd.equals("Cancel"))
		{
			dispose();
			setVisible(false);
		}
	}
	
	
}