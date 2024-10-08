/* ********************************************************
 Modified  GTabbedPane.java from http://www2.gol.com/
 
*********************************************************** */
package guiwidget;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;


public class GTabbedPane extends JTabbedPane implements ChangeListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
   Hashtable mnemonics = null;
   int condition;
   int selectIdx = 0;
   Container container;
  
   public GTabbedPane() 
   {
      setUI(new MnemonicTabbedPaneUI());
      mnemonics = new Hashtable();
      addChangeListener(this);
    
   // I don't know which one is more suitable for mnemonic action.
   //setMnemonicCondition(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	  setMnemonicCondition(WHEN_IN_FOCUSED_WINDOW);
   }
   
   public void stateChanged(ChangeEvent e){ selectIdx = getSelectedIndex();}
   public int getSelectIdx( ) { return selectIdx; }
   public void setContainer(Container ctn){ container = ctn; }
   public Container getContainer() { return container; }
   
   public void setMnemonicAt(int index, char c)
   {
	  int key = (int)c;
	  if ('a' <= key && key <='z') 
	     key -= ('a' - 'A');
	  setMnemonicAt(index, key);
   }
  
    public void setMnemonicAt(int index, int keyCode) 
    {
	    ActionListener action = new MnemonicAction(index);
	     KeyStroke stroke = KeyStroke.getKeyStroke(keyCode, 
		                         ActionEvent.ALT_MASK);
	     registerKeyboardAction(action, stroke, condition);
	     mnemonics.put(new Integer(index), new Integer(keyCode));
     }
  
    public int getMnemonicAt(int index)
    {
	    int keyCode = 0;
	    Integer m = (Integer)mnemonics.get(new Integer(index));
	    if (m != null) 
	    	keyCode = m.intValue();
	    return keyCode;
    }
  
    public void setMnemonicCondition(int condition){this.condition = condition;}
    public int getMnemonicCondition() {	return condition;    }
  
    class MnemonicAction implements ActionListener
    {
    	int index;
    
    	public MnemonicAction(int index) 
    	{
          this.index = index;
    	}
    
    	public void actionPerformed(ActionEvent e)
    	{
    		GTabbedPane tabbedPane = (GTabbedPane)e.getSource();
    		tabbedPane.setSelectedIndex(index);
    		tabbedPane.requestFocus();
    	}
    }	
  
    class MnemonicTabbedPaneUI extends MetalTabbedPaneUI
    {
    	protected void paintText(Graphics g, int tabPlacement,
    			Font font, FontMetrics metrics, int tabIndex,
    			String title, Rectangle textRect,boolean isSelected) 
    	{
            g.setFont(font);
            GTabbedPane mtabPane = (GTabbedPane)tabPane;      
            if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) 
            {
            	g.setColor(tabPane.getForegroundAt(tabIndex));
            	BasicGraphicsUtils.drawString(g,title, 
            	mtabPane.getMnemonicAt(tabIndex),
	         	textRect.x,
	         	textRect.y + metrics.getAscent());
            } else 
            {
            	g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
            	BasicGraphicsUtils.drawString(g,title, 
            			mtabPane.getMnemonicAt(tabIndex),
            			textRect.x, textRect.y + metrics.getAscent());
            	g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
            	BasicGraphicsUtils.drawString(g,title, 
					mtabPane.getMnemonicAt(tabIndex),
			        	textRect.x - 1,
			        	textRect.y + metrics.getAscent() - 1);
            }	
    	} 
    } 
}

