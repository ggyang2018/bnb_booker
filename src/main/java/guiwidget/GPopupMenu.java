package guiwidget;
/*GPopupMenu for right clieck on to the menu with call bar action as well
 @Author Guang Yang
 */
import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class GPopupMenu extends JPopupMenu 
{
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 GUIWidgetAdaptor adpt;
	 
	 public GPopupMenu(GUIWidgetAdaptor adpt)
	 {
		 this.adpt =adpt;
		 setPopupMenu();
	 }
	 
	 private void setPopupMenu()
	 {
		 BarAction ba1 = new BarAction("Book");
         ba1.setAdaptor(adpt);
         JMenuItem item1 = add(ba1);
		 item1.setMnemonic(KeyEvent.VK_B);
		 item1.setAccelerator(KeyStroke.getKeyStroke('B',
				                 Event.CTRL_MASK, false));
		 
		 JMenuItem item11 = new JMenuItem("Edit Book");
		 item11.setActionCommand("Edit");
		 BarAction ba11 = new BarAction("Edit");
         ba11.setAdaptor(adpt);
         item11.addActionListener(ba11);
         item11.setMnemonic(KeyEvent.VK_E);
		 item11.setAccelerator(KeyStroke.getKeyStroke('E',
				                 Event.CTRL_MASK, false));
		 add(item11);
		 
		 JMenuItem item2 = new JMenuItem("Delete Booking");
		 item2.setActionCommand("Delete");
		 BarAction ba2 = new BarAction("Delete");
         ba2.setAdaptor(adpt);
         item2.addActionListener(ba2);
		 item2.setMnemonic(KeyEvent.VK_D);
		 item2.setAccelerator(KeyStroke.getKeyStroke('D',
				                 Event.CTRL_MASK, false));
		 add(item2);
		 
		 JMenuItem item3 = new JMenuItem("Room Details");
		 item3.setActionCommand("roomDetail");
		 BarAction ba3 = new BarAction("rommDetail");
         ba3.setAdaptor(adpt);
         item3.addActionListener(ba3);
		 item3.setMnemonic(KeyEvent.VK_R);
		 item3.setAccelerator(KeyStroke.getKeyStroke('R',
				                 Event.CTRL_MASK, false));
		 add(item3);
	 }
}
