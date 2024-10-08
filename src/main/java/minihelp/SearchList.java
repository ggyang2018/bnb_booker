package minihelp;

import guiwidget.GUIWidgetAdaptor;

import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SearchList extends JList 
implements ListSelectionListener, ActionListener,MouseListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	
	Object selectedObj = null;
	int  objIndex = 0;
	Vector<String> data = null;
	IndexContent content;

	public SearchList(Vector<String> data )
	{
		super(data);
		this.data = data;
        setSelectedIndex(0);
		selectedObj = getSelectedValue();
		addListSelectionListener(this);
		addMouseListener(this);
        registerKeyboardAction(this, "upkey", 
	            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,true), 
	            WHEN_FOCUSED);
        registerKeyboardAction(this, "downkey", 
		            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,true), 
		            WHEN_FOCUSED);
	}

	public void setData(Vector<String> d){data = d; }
	public void setSearchCtn(IndexContent ctn) { content = ctn;}

	public void doAction(String cmd)
	{}
	public void doSelect( )
	{
		//Help.setHTMLPane(ObjIndex);
	}
	
	 //----- event implementation --------------------------
	   //the following method must be working together.
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting() == false)
			return;

		JList theList = (JList)e.getSource();
		objIndex = theList.getSelectedIndex();
		selectedObj =theList.getSelectedValue();
		//hear sent message to others
		doSelect( );		  
	} //end valueChanged method

	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if (command.equals("upkey") || command.equals("downkey"))
		{
			int index = ((JList)e.getSource()).getSelectedIndex();
			valueChanged(new ListSelectionEvent((JList)e.getSource(),
	                                             index, index, true));
			objIndex = index;
			doAction(command);
		}
	} //end actionPerformed
	  
	public Object getSelectedObj( ){ return selectedObj; }

	public void mouseReleased(MouseEvent me)
	{
      if(me.getClickCount()==2)
         content.display();
	}
	
	public void mouseClicked(MouseEvent me) { }
	public void mouseEntered(MouseEvent me) { }
	public void mouseExited(MouseEvent me) { }
	public void mousePressed(MouseEvent me){ }
	  
}