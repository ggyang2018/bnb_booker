/* Test Bus retrieve and save information
 * Author Guang Yang
 */
import nxd.*;
import bbooker.*;
import java.util.*;
import guiwidget.*;

public class BusUnitTest 
{
   
	static public void testBus()
	{
		BookBus bkBus = new BookBus(null);
		GDataMap rmp = bkBus.getRoomMap();
		List<GenData> lst = rmp.getAll();
		for(int i=0; i<lst.size(); i++)
		{
			RoomData rmd = (RoomData)lst.get(i);
			System.out.println(rmd.toString());
		}		
	}
	
	static public void testWizard()
	{
		GWizardDlg wdg = new GWizardDlg(null, "Wizard test");
		wdg.setFavourBounds(400, 400);
		//wdg.initDlg();
		wdg.setVisible(true);
		
	}
	
	
	static public void main(String args[])
	{
		//testBus();
		testWizard();
	}
	

}
