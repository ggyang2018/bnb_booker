package guiwidget;
/* Generic About dialog for copy right
 * @Author Guang Yang
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class GAboutDlg extends JDialog 
{
   static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	
   JButton okBtn;
   GStyledTextPane txtPane;
   
   public GAboutDlg(JFrame frm, int w, int h)
   {
	   super(frm);
	   setTitle("About B&B Booking System");
	   setModal(true);
	   addWindowListener(new WindowAdapter()
	   {
		  public void windowClosing(WindowEvent we)
		  {   dispose(); setVisible(false);}
		 
		  public void windowDeiconified(WindowEvent e)
		  {
			 Dimension dim = getSize();
			 Double w = new Double(dim.getWidth());
			 Double h = new Double(dim.getHeight());
			 setSize(w.intValue()+1, h.intValue()+1);
			 setSize(w.intValue(), h.intValue());
		  }
	   });
	   setFavourBounds(w, h);
	   try { initDlg();}catch(Exception ex) {}
	   
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
   
   public void initDlg()throws Exception
   {
	   //getContentPane().setLayout(new XYLayout());
	   txtPane = new GStyledTextPane();
	   getContentPane().add(txtPane);	   
	   txtPane.append("              B&B BooKing System ", 3);
	   txtPane.append("\n\nUser friendly Bed & Breakfast Room Booking System", 3); 
	   txtPane.append("\n\nBuild id: 20100619-01, Version 1", 3);
	   txtPane.append("\n\n(c) Copyright SlickSoft4u.",3);
	   txtPane.append("  All rights reserved.", 3);
	   txtPane.append("\n\nVisit http://www.slicksoft4u.com", 3);
   }
	
}
