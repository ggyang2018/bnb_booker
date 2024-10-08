package guiwidget;

/* **********************************************************
   Name: BarAction.java 
   Input: name of event generator
   Output: Action
   Model: A template for both menu and tool bar action
   Process:
   @Author Guang Yang
   *********************************************************** */

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;


 public class BarAction extends AbstractAction
 {
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	GUIWidgetAdaptor adpt;
	
	public BarAction(String name){super(name);}

	public BarAction(String name, Icon icon){super(name, icon); }
	
	public void setAdaptor(GUIWidgetAdaptor apt) { adpt = apt; }

	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand( );
		adpt.acting(command);
		//System.out.println(command);
		/*try
		{   JComponent cmp = (JComponent) ae.getSource();
			String actee = "";
			Window containWnd = null;
			if(cmp instanceof JButton)
				containWnd =getContainerWnd((JButton)cmp);
			else if(cmp instanceof JMenuItem)
				containWnd = getContainerWnd((JMenuItem)cmp);
			actee = containWnd.getClass().getName();
			//if(command.equals("Toobbar Customize"))
			//{
				//cast to specifiy container
				//GFrame frm = (GFrame)containWnd;
				//GMenuBar mBar = (GMenuBar)frm.getJMenuBar( );
				//GToolBox tools = new GToolBox(mBar.getItemID( ));
			//}
		}catch(Exception ex) {}
		//dialog example
		//helpDialog.setPage(actee);
		//containWnd.addWindowListener (helpDialog);
		 * */		 
	}

	Window getContainerWnd(JMenuItem menuItem)
	{
		Container c = menuItem.getParent();
		JMenu invokingMenu = (JMenu) ((JPopupMenu)c).getInvoker();
		return (Window) invokingMenu.getTopLevelAncestor();
	}

	Window getContainerWnd(JButton button)
	{
		 return (Window) button.getTopLevelAncestor();
	}

 }//end class of BarAction 
