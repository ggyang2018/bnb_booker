/*
 * Main Application as application entry
 * @Author Guang Yang
 */
import slklogin.AccessCtrlDlg;
import nxd.*;
import bbooker.*;

import java.util.*;

public class MainApp 
{
	//test driever
	public void testLogin()
    {
		DBDataBusImp db1 = new DBDataBusImp();
	 	AccessCtrlDlg dlg = new AccessCtrlDlg(null, db1);
   	 	dlg.setInitDlg();
   	 	dlg.setMidBounds(320, 240);
   	 	dlg.setVisible(true);   	 
    }
	
	/*public void addRooms()
	{
		DBDataBusImp db1 = new DBDataBusImp();
		for(int i=0; i<9; i++)
		{
			RoomProp rm = new RoomProp(i);
			rm.setRoomName("Room 20"+Integer.toString(i));
			rm.setFacility("TV, ensuite");
			rm.setCapacity("Double room");
			rm.setPrice("35");
			rm.setNote("Source face");
			db1.addNewRoomProp(rm);
		}
	}*/
	
	public void testXMLDSSave()
	{   //step 1
		String mpnm ="UserMap1";
		DSXMLBuzMap mp = new DSXMLBuzMap(mpnm);
		String tgs[] = {"test_item1","test_item2","test_item3"};
	    mp.setSXSD(tgs);
		
		
		List<String> vas= new ArrayList<String>();
		vas.add("test_value1 & add");
		vas.add("test_value2 £ add");
		vas.add("test_velue3 $ add €");
		
		DSXMLBuz bz1 = new DSXMLBuz("SLStudent", "yang1");
		bz1.setTags(mp.getSXSD());
		bz1.setVals(vas);
		
		DSXMLBuz bz2 = new DSXMLBuz("SLStudent", "yang2");
		bz2.setTags(mp.getSXSD());
		bz2.setVals(vas);
		
		DSXMLBuz bz3 = new DSXMLBuz("SLStudent", "yang3");
		bz3.setTags(mp.getSXSD());
		bz3.setVals(vas);
				
		mp.add(bz1);
		mp.add(bz2);
		mp.add(bz3);
		mp.save();
	}
	
	public void testDSLoad()
	{
		String mpnm ="UserMap1";
	    DSXMLBuzMap mp = new DSXMLBuzMap(mpnm);
	    String tgs[] = {"test_item1","test_item2","test_item3"};
	    mp.setSXSD(tgs);
	    mp.load("SLStudent");
	    mp.save();
		//DSManager mgr =new DSManager("XMLTest");
	}
	
	public void testCalendarPane()
	{
		BKFrame frm = new BKFrame("B&B Room Booking System");
		frm.initFrm(3, 2010);
		frm.setVisible(true);
	}
	
	public void execute()
	{
		BKFrame frm = new BKFrame("B&B Room Booking System");
		AccessCtrlDlg dlg = new AccessCtrlDlg(frm, frm.getDataBus());
   	 	dlg.setInitDlg();
   	 	dlg.setMidBounds(320, 240);
   	 	dlg.setVisible(true);
   	    if(frm.getDataBus().getActUser()==null) System.exit(0);
   	 	Calendar cal = Calendar.getInstance();
   	 	int m = cal.get(Calendar.MONTH)+1;
   	 	int y = cal.get(Calendar.YEAR);
   	 	//System.out.println("Month: "+m+"\t Year:"+y);
   	 	frm.initFrm(m, y);
		frm.setVisible(true);
	}
	
	
	
	static public void main(String args[])
	{
		MainApp tst = new MainApp();
		tst.execute();
		//tst.addRooms();
		
		//tst.testXMLDSSave();
		//tst.testDSLoad();
	}

}



