package guiwidget;

/* **************************************************************
 Name: GMenuBar
 Aim: A generic menu bar to handle menu items in tree structure. 
      Instead of call method, it send out the message object in
	  responding to menu item action
 Input: mouse click or key pressed
 Output A message object
 Model: Menu Bar ->Menu -> Menu Item onece one menu construction
        write or modify setXXMenu( ) and add to constructor 
 Note: it seems to need do it manually according to its application 
       rather than input a collection
	   It only start a basic tool bar as a start point
 *********************************************************** */
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
import java.util.*;

 public class GMenuBar extends JMenuBar 
 {
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 JMenu TopMenus[]; //for another convenient way
	 Vector<String> ItemID;
	 GUIWidgetAdaptor adpt;
	 boolean isAdmin = true;

	 //indicate the module (collection or individual) order
	 public GMenuBar(String names[])
	 {  makeTopMenus(names);  }

	 public GMenuBar(GUIWidgetAdaptor dpt, boolean isAdm)
	 {
		 isAdmin = isAdm;
		 adpt = dpt;
		 ItemID = new Vector<String>( );
		 setFileMenu( );
		 setConfigMenu( );
		 setBookMenu( );
		 setReportMenu();
		 setHelpMenu( );
	 }

	 //------- collection module for menu constraction-------
	 public void makeTopMenus(String names[])
	 {
		 TopMenus = new JMenu[names.length];
		 for(int i=0; i<names.length; i++)
		 {
			 TopMenus[i] = new JMenu(names[i]);
			 add(TopMenus[i]);
		 }
	 }

	 //create sub menu for item addition
	 public JMenu[] makeSubMenus(String names[], JMenu m)
	 {
		 JMenu sm[] = new JMenu[names.length];
		 for(int i=0; i<names.length; i++)
		 {
			  sm[i] = new JMenu(names[i]);
			  m.add(sm[i]);
		 }
		 return sm;
	 }
		 
	 public void makeMenuItems(String names[], JMenu mu)
	 {
		 Vector<String> v = new Vector<String>();		 
		 for(int i=0; i<names.length; i++)
		 {
			 JMenuItem item = mu.add(new GEventListener(names[i]));
			 String s = names[i].substring(0, 1);
			 s.toUpperCase();
			 int x = 0;
			 while(v.contains(s) || x < names.length)
			 {
				 s = names[i].substring(x, x+1);
				 x++;
			 }

             v.addElement(s);
			 char cs = s.charAt(0);
			 item.setAccelerator(KeyStroke.getKeyStroke(cs,
				                 Event.CTRL_MASK, false));
		 }
	 }

     //------- individual construction of menu bar -----------
	 //all method could be overrided in suit to application
	 protected void setFileMenu( )
	 {
		 JMenu file = new JMenu("File");
         file.setMnemonic(KeyEvent.VK_F);
          //set its items
         BarAction ba1 = new BarAction("Restore Data");
         ba1.setAdaptor(adpt);
		 JMenuItem newItem = file.add(ba1);
		 newItem.setMnemonic(KeyEvent.VK_R);
		 //newItem.setAccelerator(KeyStroke.getKeyStroke('R',
			//	                 Event.CTRL_MASK, false));
		 ItemID.addElement("Restore Data");
		 
		 BarAction ba2 = new BarAction("Backup Data");
         ba2.setAdaptor(adpt);
		 JMenuItem sendItem = file.add(ba2);
		 sendItem.setMnemonic(KeyEvent.VK_D);
		 ItemID.addElement("Backup Data");
		 file.addSeparator();

		 BarAction ba3 = new BarAction("Close");
         ba3.setAdaptor(adpt);
		 JMenuItem closeItem = file.add(ba3);
		 closeItem.setMnemonic(KeyEvent.VK_C);
		 file.addSeparator();
		 ItemID.addElement("Close");

		 /*BarAction ba4 = new BarAction("Save");
         ba4.setAdaptor(adpt);
		 JMenuItem saveItem = file.add(ba4);
		 saveItem.setMnemonic(KeyEvent.VK_S);
		 saveItem.setAccelerator(KeyStroke.getKeyStroke('S',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Save");
		 
		 BarAction ba41 = new BarAction("SaveAll");
         ba41.setAdaptor(adpt);
		 JMenuItem saveItem1 = file.add(ba41);
		 saveItem1.setMnemonic(KeyEvent.VK_A);
		 saveItem1.setAccelerator(KeyStroke.getKeyStroke('A',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Save All");
		 
		 file.addSeparator();
		 
		 BarAction ba5 = new BarAction("Page Setup");
         ba5.setAdaptor(adpt);
         JMenuItem pageItem = file.add(ba5);
		 pageItem.setMnemonic(KeyEvent.VK_U);
		 ItemID.addElement("Page Setup");
		 
		 BarAction ba6 = new BarAction("Print");
         ba6.setAdaptor(adpt);
		 JMenuItem printItem = file.add(ba6);
		 printItem.setMnemonic(KeyEvent.VK_P);
		 printItem.setAccelerator(KeyStroke.getKeyStroke('P',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Print");
		 */
		 file.addSeparator();
		 
		 BarAction ba7 = new BarAction("Exit");
         ba7.setAdaptor(adpt);
		 JMenuItem exitItem = file.add(ba7);
		 exitItem.setMnemonic(KeyEvent.VK_X);
		// exitItem.setAccelerator(KeyStroke.getKeyStroke('x',
          //       Event.CTRL_MASK, false));
		 ItemID.addElement("Exit");
         add(file);
	 }

	 protected void setConfigMenu( )
	 {
		 JMenu edit = new JMenu("Configure");
         edit.setMnemonic(KeyEvent.VK_C);
          //set its items
         BarAction ba1 = new BarAction("Room Type");
         ba1.setAdaptor(adpt);
		 JMenuItem editItem = edit.add(ba1);
		 ItemID.addElement("Room Type");
		 editItem.setEnabled(isAdmin);

		 BarAction ba2 = new BarAction("Add Rooms");
         ba2.setAdaptor(adpt);
		 JMenuItem undoItem = edit.add(ba2);
		 ItemID.addElement("Add Rooms");
		 undoItem.setEnabled(isAdmin);
		 add(edit);
	 }

	 protected void setBookMenu( )
	 {
		 JMenu view = new JMenu("Booking");
         view.setMnemonic(KeyEvent.VK_B);
		
         BarAction ba1 = new BarAction("Book");
         ba1.setAdaptor(adpt);
		 JMenuItem customItem = view.add(ba1);
		 customItem.setMnemonic(KeyEvent.VK_B);
		 //customItem.setAccelerator(KeyStroke.getKeyStroke(
			//                     KeyEvent.VK_F8,
			 //	                 Event.ALT_MASK, false));
		 customItem.setAccelerator(KeyStroke.getKeyStroke('B',
                 Event.CTRL_MASK, false));
		 ItemID.addElement("Book");
		 
		 BarAction ba11 = new BarAction("Edit");
         ba11.setAdaptor(adpt);
		 JMenuItem customItem1 = view.add(ba11);
		 customItem1.setMnemonic(KeyEvent.VK_E);
		 customItem1.setAccelerator(KeyStroke.getKeyStroke('E',
                 Event.CTRL_MASK, false));
		 ItemID.addElement("Edit");
		 		 
		 BarAction ba2 = new BarAction("Delete");
         ba2.setAdaptor(adpt);
		 JMenuItem ed = view.add(ba2);
		 ed.setMnemonic(KeyEvent.VK_D);
		 ed.setAccelerator(KeyStroke.getKeyStroke('D',
                 Event.CTRL_MASK, false));
		 ItemID.addElement("Delete");
		 
		 BarAction ba3 = new BarAction("Change Book");
         ba3.setAdaptor(adpt);
		 JMenuItem mb = view.add(ba3);
		 mb.setMnemonic(KeyEvent.VK_C);
		 //mb.setAccelerator(KeyStroke.getKeyStroke('C',
                // Event.CTRL_MASK, false));
		 mb.setAccelerator(KeyStroke.getKeyStroke(
			                     KeyEvent.VK_F8,
			                 Event.ALT_MASK, false));
		 ItemID.addElement("Change Book");
		 
		 BarAction ba4 = new BarAction("Meeting");
         ba4.setAdaptor(adpt);
		 JMenuItem met = view.add(ba4);
		 met.setMnemonic(KeyEvent.VK_M);
		 met.setAccelerator(KeyStroke.getKeyStroke('M',
                 Event.CTRL_MASK, false));
		 ItemID.addElement("Meeting");
		 add(view);
	 }

	 protected void setHelpMenu( )
	 {
		 JMenu help = new JMenu("Help");
         help.setMnemonic(KeyEvent.VK_H);
		 
         BarAction ba1 = new BarAction("About");
         ba1.setAdaptor(adpt);
		 JMenuItem aboutItem = help.add(ba1);
		 aboutItem.setMnemonic(KeyEvent.VK_A);
		 ItemID.addElement("About");
		 
		 BarAction ba2 = new BarAction("Help");
         ba2.setAdaptor(adpt);
		 JMenuItem helpItem = help.add(ba2);
		 helpItem.setMnemonic(KeyEvent.VK_T);
		 helpItem.setAccelerator(KeyStroke.getKeyStroke(
			                     KeyEvent.VK_F1,
			 	                 Event.ALT_MASK, false));
		 ItemID.addElement("Help");
		 add(help);
	 }
	 
	 protected void setReportMenu( )
	 {
		 JMenu help = new JMenu("Report");
         help.setMnemonic(KeyEvent.VK_R);
		 
         BarAction ba1 = new BarAction("Book Sheet");
         ba1.setAdaptor(adpt);
		 JMenuItem listItm = help.add(ba1);
		 listItm.setMnemonic(KeyEvent.VK_B);
		 ItemID.addElement("Book Sheet");
		  
		 BarAction ba2 = new BarAction("Customer List");
         ba2.setAdaptor(adpt);
		 JMenuItem helpItem = help.add(ba2);
		 helpItem.setMnemonic(KeyEvent.VK_C);
		 ItemID.addElement("Customer List");
		 
		 BarAction ba3 = new BarAction("Statement");
         ba3.setAdaptor(adpt);
		 JMenuItem item3 = help.add(ba3);
		 item3.setMnemonic(KeyEvent.VK_S);
		 item3.setAccelerator(KeyStroke.getKeyStroke('S',
                 Event.ALT_MASK, false));
		 ItemID.addElement("Statement");
		 
		 BarAction ba4 = new BarAction("Room List");
         ba4.setAdaptor(adpt);
		 JMenuItem item4 = help.add(ba4);
		 item4.setMnemonic(KeyEvent.VK_R);
		 item4.setAccelerator(KeyStroke.getKeyStroke('R',
                 Event.ALT_MASK, false));
		 ItemID.addElement("Room List");		 
		 add(help);
	 }
	 
	 //spare one
	 protected void setEditMenu( )
	 {
		 JMenu edit = new JMenu("Edit");
         edit.setMnemonic(KeyEvent.VK_E);
          //set its items

		 JMenuItem editItem = edit.add(new BarAction("Edit"));
		 editItem.setMnemonic(KeyEvent.VK_E);
		 editItem.setAccelerator(KeyStroke.getKeyStroke('D',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Edit");


		 JMenuItem undoItem = edit.add(new BarAction("Undo"));
		 undoItem.setMnemonic(KeyEvent.VK_U);
		 undoItem.setAccelerator(KeyStroke.getKeyStroke('Z',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Undo");

         JMenuItem redoItem = edit.add(new BarAction("Redo"));
		 redoItem.setMnemonic(KeyEvent.VK_R);
		 redoItem.setAccelerator(KeyStroke.getKeyStroke('Y',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Redo");

		 edit.addSeparator();
		 JMenuItem cutItem = edit.add(new BarAction("Cut"));
		 cutItem.setMnemonic(KeyEvent.VK_T);
		 cutItem.setAccelerator(KeyStroke.getKeyStroke('X',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Cut");
		 
		 JMenuItem copyItem = edit.add(new BarAction("Copy"));
		 copyItem.setMnemonic(KeyEvent.VK_C);
		 copyItem.setAccelerator(KeyStroke.getKeyStroke('C',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Copy");

         JMenuItem pasteItem = edit.add(new BarAction("Paste"));
		 pasteItem.setMnemonic(KeyEvent.VK_P);
		 pasteItem.setAccelerator(KeyStroke.getKeyStroke('V',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Paste");

		 JMenuItem deleteItem = edit.add(new BarAction("Delete"));
		 deleteItem.setMnemonic(KeyEvent.VK_D);
		 deleteItem.setAccelerator(KeyStroke.getKeyStroke(
				                 KeyEvent.VK_DELETE, 0, false));
		 ItemID.addElement("Delete");
		 edit.addSeparator();
         
		 JMenuItem selectAllItem = edit.add(new BarAction("Select All"));
		 selectAllItem.setMnemonic(KeyEvent.VK_A);
		 selectAllItem.setAccelerator(KeyStroke.getKeyStroke('A',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Select All");
		 
		 JMenuItem invertItem = edit.add(new BarAction("Invert Selection"));
		 invertItem.setMnemonic(KeyEvent.VK_I);
		 
		 JMenuItem findItem = edit.add(new BarAction("Find"));
		 findItem.setMnemonic(KeyEvent.VK_F);
		 findItem.setAccelerator(KeyStroke.getKeyStroke('F',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Find");
		 
		 JMenuItem goItem = edit.add(new BarAction("Go To"));
		 goItem.setMnemonic(KeyEvent.VK_G);
		 goItem.setAccelerator(KeyStroke.getKeyStroke('G',
				                 Event.CTRL_MASK, false));
		 		 
		 JMenuItem replaceItem = edit.add(new BarAction("Replace"));
		 replaceItem.setMnemonic(KeyEvent.VK_L);
		 replaceItem.setAccelerator(KeyStroke.getKeyStroke('H',
				                 Event.CTRL_MASK, false));
		 ItemID.addElement("Replace");
		 
		 JMenuItem bookItem = edit.add(new BarAction("Bookmarks"));
		 bookItem.setMnemonic(KeyEvent.VK_B);
		 bookItem.setAccelerator(KeyStroke.getKeyStroke(
			                     KeyEvent.VK_F2,
			 	                 Event.ALT_MASK, false));
		 ItemID.addElement("Bookmark");
		 add(edit);
	 }

	 protected void setViewMenu( )
	 {
		 JMenu view = new JMenu("Veiw");
         view.setMnemonic(KeyEvent.VK_V);
		 
		 JCheckBox tBar = new JCheckBox("Tool Bar", true);
		 tBar.setMnemonic(KeyEvent.VK_T);
		 tBar.setBorder(null);
		 tBar. setRequestFocusEnabled(false);
		 view.add(tBar);

		 JCheckBox sBar = new JCheckBox("Status Bar", true);
		 sBar.setMnemonic(KeyEvent.VK_B);
		 sBar.setBorder(null);
		 sBar. setRequestFocusEnabled(false);
		 view.add(sBar);

		 JMenuItem customItem = view.add(new BarAction("Toobbar Customize"));
		 customItem.setMnemonic(KeyEvent.VK_C);
		 customItem.setAccelerator(KeyStroke.getKeyStroke(
			                     KeyEvent.VK_F8,
			 	                 Event.ALT_MASK, false));

		 add(view);
	 }



	 //-------- support methods -------------------
	 public Vector<String> getItemID( )
	 {   return ItemID;   }
	 
 }

			 

			

