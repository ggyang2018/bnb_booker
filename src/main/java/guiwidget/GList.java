package guiwidget;
/* GList is a dynamic content list. allow to add/delete and selcect items
 * @Author Guang Yang
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;

public class GList extends JList 
implements ListSelectionListener, ActionListener,MouseListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	

	Object SelectedObj = null;
	int  ObjIndex = 0;
    
	public GList()
    { 
       super(new DefaultListModel());
       //setSelectedIndex(0);
       //SelectedObj = getSelectedValue();
       
       setSelectionModel(new ToggleSelectionModel());
       addListSelectionListener(this);
       addMouseListener(this);
       registerKeyboardAction(this, "upkey", 
	            KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,true), 
	            WHEN_FOCUSED);
       registerKeyboardAction(this, "downkey", 
		            KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,true), 
		            WHEN_FOCUSED);
    }
    
    DefaultListModel getContents(){	return (DefaultListModel)getModel();}
    
    public void addItem(String itm) { getContents().addElement(itm); }
    public void rmItem(String itm) { getContents().removeElement(itm); }
    public String rmItem(int idx) { return (String)getContents().remove(idx); }
    public int indexOfItem(String itm){return getContents().indexOf(itm);}
    public String getItem(int idx)
    {  return (String)getContents().elementAt(idx); }
    public void sortItems()
    {
    	Object obs[]  = getContents().toArray();
    	List<String> lst = new ArrayList<String>();
    	for(int i=0; i<obs.length; i++)
    	  lst.add((String)obs[i]);
    	
    	Collections.sort(lst);
    	getContents().clear();
    	for(int i=0; i<lst.size(); i++)
    		addItem(lst.get(i));
    }
    
    //----- event implementation --------------------------
    //the following method must be working together.
    public void valueChanged(ListSelectionEvent e)
    {
       if (e.getValueIsAdjusting() == false)
          return;

       JList theList = (JList)e.getSource();
       ObjIndex = theList.getSelectedIndex();
       SelectedObj =theList.getSelectedValue();
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
 		ObjIndex = index;
 		doAction(command);
 	 }
    } //end actionPerformed

   
    public Object getSelectedObj( )
    {   return SelectedObj;      }

    //hooked methods for override for special actions.
    public void doSelect(  ){}
    public void doAction(String cmd){}

    
    public void mouseReleased(MouseEvent me)
    {
       if(me.getClickCount()==2)
       {
         //HelpIndexer pane =(HelpIndexer)getParent();
 		//pane.display();
 	  }
    }

    public void mouseClicked(MouseEvent me) { }
    public void mouseEntered(MouseEvent me) { }
    public void mouseExited(MouseEvent me) { }
    public void mousePressed(MouseEvent me){ }
    


}

class ToggleSelectionModel extends DefaultListSelectionModel
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	
    boolean gestureStarted = false;
    public void setSelectionInterval(int index0, int index1)
    {
        if(isSelectedIndex(index0) && !gestureStarted)
    		super.removeSelectionInterval(index0, index1);
        else 
    		super.setSelectionInterval(index0, index1);
        gestureStarted = true;
    }

    public void setValueIsAdjusting(boolean isAdjusting)
    {
    	if (isAdjusting == false)   gestureStarted = false;
	}  
}


