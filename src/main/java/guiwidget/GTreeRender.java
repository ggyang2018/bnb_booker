package guiwidget;


 import javax.swing.*;
 import javax.swing.event.*;
 import javax.swing.tree.*;
 import java.util.*;
import java.awt.*;
 
 
public class GTreeRender extends JLabel implements TreeCellRenderer
 {
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	 Color backColor;
	 boolean isLeaf;

	 GTreeRender()
	 {
		 setFont(new Font("Monospaced", Font.BOLD, 16));
		 setHorizontalAlignment(SwingConstants.CENTER);
	 }

	public Component getTreeCellRendererComponent(JTree tree,
                                              Object value,
                                              boolean selected,
                                              boolean expanded,
                                              boolean leaf,
                                              int row,
                                              boolean hasFocus)
	{
		if(selected)
		{
			setOpaque(true);
			setForeground(Color.yellow);
		}
        else
		{
			setOpaque(false);
			setForeground(Color.black);
		}

		setText(value.toString());
		
		return this;
	}

	public String getToolTipText()
	{
		if(isLeaf) 
		  return "leaf";
		else
		  return "Node";
	}

	public Dimension getPreferredSize()
	{
		Dimension dim = super.getPreferredSize();
		if(dim != null)
		dim = new Dimension(dim.width+4, dim.height);
		
		return (Dimension)dim;
	}
 }//end of GTreeRender class






		



