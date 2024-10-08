package guiwidget;

/*Generic Dialog to display JPanel
 * @Author Guang Yang
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class GenericDlg extends JDialog 
{
   static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	

   public GenericDlg(JFrame frm, String title)
   {
	   super(frm);
	   setModal(true);
	   setTitle(title);
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
   	}
   
   public GenericDlg(JDialog frm, String title)
   {
	   super(frm);
	   setModal(true);
	   setTitle(title);
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
   
   public void addPanel(JPanel jp) { getContentPane().add(jp); }
   public void addPanel(JScrollPane sp){ getContentPane().add(sp); }
}
