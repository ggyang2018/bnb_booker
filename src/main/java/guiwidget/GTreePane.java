package guiwidget;

/* ****************************************************************
 Name: GTreePane
 Input: A tree stuecture
 Output: dispaly a tree and manipulating it
 Process. User navitigate, cut and add node as it like
 Model. Self container
 ****************************************************************** */

 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
import java.util.Vector;


 public class GTreePane extends JScrollPane
 {
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 private final int DIRECTORYTREE=0;
	 private final int DIAGRAMTREE=1;

	 public GTreePane(int type)
	 {
		 if(type == DIRECTORYTREE)
		 setDirTreePane();
	 }
	 
	 public void setDirTreePane( )
	 {
		 Vector Nodes = new Vector( );
		 Nodes.addElement("root");
		 Nodes.addElement("A");
		 Nodes.addElement("B");
		 Nodes.addElement("C");
		 Nodes.addElement("D");
		 Nodes.addElement("A1");
		 Nodes.addElement("A2");
		 Nodes.addElement("B1");
		 Nodes.addElement("B3");
		 Nodes.addElement("C1");

		 int ind[] = new int[Nodes.size()];
		 ind[0] =0;
		 
		 ind[1] =0; //frist element overlap by root
		 ind[2] =1;
		 ind[3] =1;
		 ind[4] =1;
		 ind[5] =2;
		 ind[6] =7;
		 ind[7] =9;
		 ind[8] =5;
		 ind[9] = 4;

		 XTree tree = new XTree(Nodes, ind);
		 //tree.makeNodes();
		 tree.setXTreePane( );
		 getViewport().add(tree);
		 //tree.rmNode(5);
	 }

	 public static void main(String args[])
	 {
		 JFrame f = new JFrame("test");
		 f.setBounds(100, 100, 300, 300);
		 f.getContentPane().add(new GTreePane(0));
		 f.setVisible(true);
	 }
 }

