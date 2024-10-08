package guiwidget;

/* ***********************************************************
 Name: GEventListener.java
 Aim: Generic event listener for putton, tool bar and menu items
 Input: user selected
 Output: a message object
 model: observer, pass object to component's container who observe
        the action to do it accordinary via an action interface.
 Author GY
 **************************************************************** */

import java.awt.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;
import javax.swing.*;


public class GEventListener extends AbstractAction 
implements Observer
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
 
	public GEventListener(String name)
	{
		super(name);
	}

	public GEventListener(String name, Icon icon)
	{
		super(name, icon);
	}

	public void actionPerformed(ActionEvent ae)
	{
		JComponent cp = (JComponent) ae.getSource();
		Container cpp = null;
		if(cp instanceof JButton)
		{
			cpp = getComponentParent((JButton)cp );
		}else if(cp instanceof JMenuItem)
		{
			cpp = getComponentParent((JMenuItem)cp);
		}else 
			cpp = null;
	}//end actionPerformed definition

	private Container getComponentParent(JMenuItem menuItem)
	{
		Container c = menuItem.getParent();
		JMenu invokingMenu = (JMenu) ((JPopupMenu)c).getInvoker();
		Container menuContainer = (Container) invokingMenu.getTopLevelAncestor();
		return menuContainer;
	}

	private Container getComponentParent(JButton button)
	{
		Container c = (Container) button.getTopLevelAncestor();
		return c;
	} 

		//Implementation of Observer interface
	public void update(Observable o, Object obj)
	{
		if(!isEnabled())
		setEnabled(true);
	 	//your code below
	}
}//end class GEventListener definition 
