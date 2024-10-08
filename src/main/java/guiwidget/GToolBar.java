package guiwidget;

/* ****************************************************************
 
   Name: GToolBar.java as general tool bar 
   Input: none, shoudl self contained, it integrated all button 
          images from Sun's Look and Feel repository
   Output Action of the pressed button
   Model: self setting and determined
   Process: user clicked and customize it by drag and drop
   Author GY, date 8 Jan 2001
******************************************************************* */
 
 import javax.swing.*;
 import javax.swing.border.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.io.*;


public class GToolBar extends JToolBar implements MouseListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	SoftBevelBorder ActBorder;
    EmptyBorder InactBorder;
    Dimension IconSize;


   public GToolBar ()
   {
		super ();
		setLayout (new FlowLayout (FlowLayout.LEFT));

		ActBorder = new SoftBevelBorder (BevelBorder.RAISED);
		InactBorder = new EmptyBorder (0, 0, 0, 0);
		IconSize = new Dimension (32, 26);
	}


  public void add (String imgURL, String tip, Action act) 
  {
	 File f = new File(imgURL);
	 if(!f.exists())
		 return;
	 try
	 {
		 JButton toolBtn = super.add (act);
		 //URL imageURL = getClass ().getResource (imgURL);
		 //Image iconImg = Toolkit.getDefaultToolkit ().getImage (imageURL);
		 Image iconImg = Toolkit.getDefaultToolkit ().getImage (imgURL);
		 Image Img=iconImg.getScaledInstance(24, 24, 
			                         Image.SCALE_AREA_AVERAGING); 
		 ImageIcon 	BtnIcon = new ImageIcon (Img);
		 toolBtn.setIcon (BtnIcon);
		 toolBtn.setBorder (InactBorder);
		 toolBtn.setText("");
		 toolBtn.setActionCommand(tip);
		 toolBtn.setToolTipText(tip);
		 toolBtn.setRequestFocusEnabled (false);
	 }catch(Exception ex) { ex.printStackTrace(); }
	 
  }

  public void setBasicBar(Action a)
  {
	 setFloatable(false);
	 add("iconimg/New24.gif", "Book", a);
	 add("iconimg/Edit24.gif", "Edit", a);
	 add("iconimg/Refresh24.gif", "Change Book", a);
	 add("iconimg/Delete24.gif","Delete", a);
	 add("iconimg/SaveAll24.gif", "Book Sheet", a);
	 add("iconimg/Save24.gif", "Customer List", a);
	 //add("iconimg/PageSetup24.gif", "Page Setup", a);
	 add("iconimg/print24.gif","Statement", a);
	 add("iconimg/Information24.gif", "Room List", a);	
	 add("iconimg/About24.gif", "About", a);
	 add("iconimg/Help24.gif", "Help", a);
	 add("iconimg/Stop24.gif", "Exit", a);	
  }

  public boolean isFocusTraversable(){ return false;}

//----------- action event ------------------------
  public void mouseEntered (MouseEvent e)
  {
    Object eventSource = e.getSource ();
    if (!(eventSource instanceof JButton)) return;

    JButton jb = (JButton) eventSource;
    if (jb.isEnabled ()) jb.setBorder (ActBorder);
  }


  public void mouseExited (MouseEvent e)
  {
    Object eventSource = e.getSource ();
    if (!(eventSource instanceof JButton)) return;

    JButton jb = (JButton) eventSource;
    if (jb.getBorder () == ActBorder)
      jb.setBorder (InactBorder);
  }

  //make mouse listener happy
  public void mousePressed (MouseEvent e) {}
  public void mouseReleased (MouseEvent e) {}
  public void mouseClicked (MouseEvent e) {}

  
}

 