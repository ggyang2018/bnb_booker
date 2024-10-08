package minihelp;

import guiwidget.XYLayout;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class HelpDlg extends JDialog 
{
	static public final long  serialVersionUID = SearchList.serialVersionUID;
	int wid, hgt;
		 
	IndexContent idxPane;
	String HTMLName;
	HTMLDisplay Display;

	public HelpDlg(JFrame owner, String title)
	{
		super(owner);
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
		setFavourBounds(660, 450);
	}
	
	public void setFavourBounds(int width, int height)
	{
		wid = width; hgt = height;
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}
	
	public void setHTMLName(String s) { HTMLName = s; }
	
	public void initPanel( )
	{
		setLayout(new XYLayout());
		int x = Math.round(0.3f*wid);
		idxPane = new IndexContent(this);
		idxPane.setBounds(0, 0, x, hgt-5);
		idxPane.setBorder(BorderFactory.createLoweredBevelBorder());
		add(idxPane);
		Display = new HTMLDisplay(HTMLName);
		Display.setBounds(x+5, 0, wid-(x+7), hgt-5);
		add(Display);	   	        	  
	}

}
