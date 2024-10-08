package bbooker;
/* Search Old customer if existed by surname
 * @Author Guang Yang
 */
import guiwidget.XYLayout;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

public class SearchCustDlg extends JDialog 
{
   static public final long serialVersionUID = BKFrame.serialVersionUID;
   int wid, hgt;
   BookDlg dlg;
   BKWizardDlg wdlg;
   BKWizardPane2 workPane;
   
   SearchCustPane idxPane;
   Vector<String> words;
   HashMap<String, String> descs;
   HashMap<String, SLKCustomer> custMap;
   
   public SearchCustDlg(BookDlg dlg, String title)
   {
	  super(dlg);
	  this.dlg = dlg;
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
	  getContentPane().setLayout(new XYLayout());
   }
   
   public SearchCustDlg(BKWizardDlg dlg, String title)
   {
	  super(dlg);
	  this.wdlg = dlg;
	  setModal(true);
	  setTitle(title);
	  //setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
	  setIconImage(Toolkit.getDefaultToolkit().getImage("iconimg\\ss4ulogo1.gif"));  
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
	  getContentPane().setLayout(new XYLayout());
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
   
   public void setParentPane(BKWizardPane2 pn) { workPane = pn; }
   
   public void initDlg()
   {
	   idxPane = new SearchCustPane(this);
	   int x0=10, y0=5, w=wid-30, h=hgt-40;
	   createData();
	   idxPane.initPanel();
	   idxPane.setBounds(x0, y0, w, h);
	   idxPane.setBorder(BorderFactory.createLoweredBevelBorder());
	   getContentPane().add(idxPane);
   }	
   
   public void createData()
	{
		words = new Vector<String>();
		descs = new HashMap<String, String>();
		custMap = new HashMap<String, SLKCustomer>();
		List<String> lst = wdlg.frm.bkBus.getCustMap().getCodes();
		for(int i=0; i<lst.size(); i++)
		{
		  try
		  {
			String key = lst.get(i);
			SLKCustomer cs = (SLKCustomer)wdlg.frm.bkBus.getCustMap().find(key);
			String srnm = cs.getSurname();
			String des = srnm+","+cs.getForename()+":"+cs.getCountry();
			if(cs.getContact()!=null)
				des = des+":"+cs.getContact();
			
			des = des.toLowerCase();//all lowcase for easy typein. 
		    if(!words.contains(des))
		    {
		    	words.add(des);
		    	descs.put(des, cs.getCode());
		    }
		  }catch(Exception ex) { ex.printStackTrace();  }
		}
		Collections.sort(words);
		idxPane.setData(words);
	}
}
