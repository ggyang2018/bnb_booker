package guiwidget;
/*Generic Wizard dialog for perform complicate operation
 * @Author Guang Yang
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;

public class GWizardDlg extends JDialog 
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	int wid, hgt;
	
	List<GWizardPane> paneList;
	JFrame frame;
	
	public GWizardDlg(JFrame dlg, String title)
	{
	   super(dlg);
	   frame = dlg;
	   setModal(true);
	   setTitle(title);
	   setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
	   paneList = new ArrayList<GWizardPane>();	    
	   addWindowListener(new WindowAdapter()
	   {
		   public void windowClosing(WindowEvent we)
		   {  dispose(); setVisible(false);}
			 
		   public void windowDeiconified(WindowEvent e)
		   {
			  Dimension dim = getSize();
			  Double w = new Double(dim.getWidth());
		      Double h = new Double(dim.getHeight());
		      setSize(w.intValue()+1, h.intValue()+1);
		      setSize(w.intValue(), h.intValue());
		   }
	   });
	   //getContentPane().setLayout(new XYLayout());
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
	
	public void addWizarPane(GWizardPane wp)
	{ 
		if(paneList.size()<1) getContentPane().add(wp);//display first
		paneList.add(wp);		
	}
	
	public GWizardPane getWizardPane(int i)
	{
		if(i<0 || i>=paneList.size()) return null;
		return paneList.get(i);
	}
	//override
	/*public void initDlg()
	{
	   GWizardPane pane0 = new GWizardPane(this, "Pane1", 0, false);
	   pane0.initPane();
	   getContentPane().add(pane0, 0);
	   paneList.add(pane0);
	   GWizardPane pane1 = new GWizardPane(this, "Pane2", 1, false);
	   pane1.initPane();
	   paneList.add(pane1);
	   GWizardPane pane2 = new GWizardPane(this, "Pane3", 2, false);
	   pane2.initPane();
	   paneList.add(pane2);
	   GWizardPane pane3 = new GWizardPane(this, "Pane4", 3, true);
	   pane3.initPane();
	   paneList.add(pane3);
	}*/
	  
	public JFrame getFrame() { return frame;}
	public void showPane(int idx)
	{
	    if(idx<0 || idx>=paneList.size()) return;
	   if(getContentPane().getComponentCount()>0)
		   getContentPane().removeAll();
	   GWizardPane wp = paneList.get(idx);
	   getContentPane().add(wp);
	   getContentPane().validate();
	   getContentPane().repaint();
	}
	
	public int getDialogWidth() { return wid;}
	public int getDialogHeight() { return hgt; }
}
