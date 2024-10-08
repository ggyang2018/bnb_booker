package guiwidget;

import javax.swing.*;
 import javax.swing.event.*;
 import javax.swing.tree.*;
 import java.awt.*;
 import java.awt.event.*;
import java.util.*;
 
 public class XTreeNode extends JButton implements ActionListener
 {
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 private boolean isChildShown = false;
	 private boolean isFirstDisplay = true;
	 private GTreeNode XNode = null;
	 private XTreeNode XChild[];
	 private int ChildNmb = 0;
	 private int x0=0; int y0=0;
	 private final int wid = 30;
	 private final int hei = 20;
	 private XTree TreePane = null;

	 public XTreeNode(GTreeNode node, int x, int y, XTree tree)
	 {
		 XNode = node; 
		 setUpLeft(x, y);
		 TreePane = tree;
		 setText(XNode.toString());
		 if(!XNode.isLeaf())
		 {
			 ChildNmb = XNode.getChildCount();
		     XChild = new XTreeNode[ChildNmb];
		 }else
			 XChild = new XTreeNode[0];
		 setBounds(x0, y0, wid, hei);
		 addActionListener(this);
	 }

	 
	 public void setUpLeft(int x, int y)
	 {		 x0 = x;  y0 = y;     }

	 public GTreeNode getTreeNode( )
	 {   return XNode;  }

	 //action is to display children
	 public void actionPerformed(ActionEvent ae)
	 {
		
		 if(!isChildShown && !XNode.isLeaf())
		 {
			 if(isFirstDisplay)
			 {
				 System.out.println("OK");
			 	 
				for(int i=0; i<ChildNmb; i++)
				{
				    GTreeNode gnode = (GTreeNode)XNode.getChildAt(i);
					XChild[i] = new XTreeNode(gnode,
						                     calPointX(i),
							                  calPointY( ),
											  TreePane);
					TreePane.add(XChild[i]);
				}
				isFirstDisplay=false;
			 }
		    else 
			{
				  for(int i=0; i<ChildNmb; i++)
				  {	 
					  XChild[i].setVisible(true); 
				  }
			}
				  
				TreePane.updateUI();
				isChildShown=true;
		 }else if(isChildShown && !XNode.isLeaf())
		 {
		    for(int i=0; i<ChildNmb; i++)
			{
				if(!XChild[i].XNode.isLeaf())
				{
				   int j = 0;
				   while (j<XChild[i].XChild.length)
				   {
					   XChild[i].XChild[j].setVisible(false);
					   j++;
				   }
				}

				XChild[i].setVisible(false);
			}
			TreePane.updateUI();
			isChildShown=false;
		 }
	 }

	 private int calPointX(int i)
	 {
		 int c_size = (wid*ChildNmb+10*(ChildNmb-1))/2;
		 int midpointx = x0+wid/2;
		 int x = (midpointx-c_size)+(wid+10)*i;
		 return x;
	 }

	 private int calPointY( )
	 {	 
		 int y = y0+hei+10;
		 return y;
		 
	 }


 }



