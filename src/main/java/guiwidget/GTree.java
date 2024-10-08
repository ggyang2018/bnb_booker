package guiwidget;

/* *************************************************************
  Generic tree structure to express a hierarchy object references
  Actural object can be get by its corresponding reference
  Input: A vector of object reference
         A vector of object reference position
  Ouput: a selected object reference
  Process: dislay a hierarchy object. user can add/remove node
  Model: data - treenode-tree model - tree
         principly work on indices.
         To get node object by name, call map get node index
		 the via index to get object
		 All node store in TreeMap in plain format. the index of
		 the root node is 0;

  Note the root and first node only display one.

  Author G.Yang
  Date: 4. Jan. 01
  ******************************************************* */

 import javax.swing.*;
 import javax.swing.event.*;
 import javax.swing.tree.*;
 import java.awt.*;
import java.util.*;
 

 public class GTree extends JTree
 {
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 //data and node postion indeces collection
	 Vector Nodes; //key is itself index instread of parent
	 HashMap NameToIndexMap; //parent name to parent index not properly

	 Object SelectedObject;
	 GTreeNode GTreeRoot = null;
	 DefaultTreeModel STreeModel;
	 DefaultTreeSelectionModel STreeSelectModel;
	 
	 public GTree()
	 {
		 Nodes = new Vector();
		 NameToIndexMap = new HashMap( );
	 }

	 public GTree(Vector v, int ind[])
	 {
		 Nodes = new Vector();
		 NameToIndexMap = new HashMap( );
		 try
		 {
		   setNodeMaps(v, ind);
		 }catch(Exception ex)
		 { ; }
	 }

	 //------convert data into node and store in tree map --------
	 public void setNodeMaps(Vector v, int pInd[]) throws Exception
	 {
		 if(v.size() != pInd.length)
			 throw new Exception( );

		 for(int i=0; i<v.size(); i++)
		 {
		   GTreeNode node1= new GTreeNode(v.elementAt(i), pInd[i]);
		   Object parent = v.elementAt(pInd[i]);
		   node1.setParentName(parent.toString());
		   Nodes.addElement(node1);
		 }
	 }

	 
	 //------- build a tree node model from tree map ----------
	 private GTreeNode makeNodes( )
	 {
		 GTreeNode root = null;

		 //find the root
		 for(int a=0; a<Nodes.size(); a++)
		 {
			 GTreeNode gn =(GTreeNode)Nodes.elementAt(a);
			 if(gn.getParentPost()==0)
			 { 	 
				 root= gn;
				 break; //only firt node be root
			 }
		 }

		 //construct the root children look for parent bottom-up
		 int j=0;
		 while(j<Nodes.size())
		 {
			 		 
			 GTreeNode pNode = (GTreeNode)Nodes.elementAt(j);
			 int pIndex = pNode.getParentPost( );
			 
			 if(pIndex >1)
			 {
				 GTreeNode node1 = (GTreeNode)Nodes.elementAt(pIndex);
				 node1.add(pNode);
			 }
			 else if(pIndex ==1)
				 root.add(pNode);
			 j++;

		 }	 
		 	 return root;
	 }

	 public void setTree( )
	 {
		 GTreeRoot = makeNodes( );
		 STreeModel = new DefaultTreeModel(GTreeRoot);
		 
		 STreeModel.addTreeModelListener(new DTreeModelListener());
         STreeSelectModel=new DefaultTreeSelectionModel();
		 STreeSelectModel.addTreeSelectionListener
			                 (new DTreeSelection());
		STreeSelectModel.setSelectionMode
			(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		 setModel(STreeModel);
		 setSelectionModel(STreeSelectModel);
		 setVisibleRowCount(8);
		 setRowHeight(-1);
		 
		 putClientProperty("JTree.lineStyle", "Angled");
		 setCellRenderer(new GTreeRender() );
   	 }

	 //--------step by step to build tree root ---------------
	 public void setTreeRoot(Object obj)
	 {  
		 String rootName = obj.toString();
		 GTreeRoot = new GTreeNode(obj, 0);
		 Nodes.insertElementAt(GTreeRoot, 0);
	 }

	 public void addToTreeRoot(Object[] strut)
	 {
		 GTreeNode[] DTreeNodes = new GTreeNode[strut.length]; 
		 for(int i=0; i<strut.length; i++)
		 {
			 DTreeNodes[i]=new GTreeNode(strut[i], 1);
			 GTreeRoot.add(DTreeNodes[i]);
			 Nodes.addElement(DTreeNodes[i]);
		 }
	 }
		 
     public void setParentChildren(int pIndex, Vector children)
	 {   
		 GTreeNode Parent = (GTreeNode)Nodes.elementAt(pIndex);
		 
				 
	     GTreeNode[] TreeNodes = new GTreeNode[children.size()]; 
		 
	     for(int i=0; i<children.size(); i++)
		 {
			 TreeNodes[i]=new GTreeNode(children.elementAt(i), 
				                              pIndex);
			 //Parent.add(TreeNodes[i]); //let make to it
			 Nodes.addElement(TreeNodes[i]);
		 }
	 
	 }

//------add/rm Node for a built tree ------------------------

	 //add node to parent identified by name
    public void	addNode(int pIndex, Object child)
	{
		  GTreeNode Parent = (GTreeNode)Nodes.elementAt(pIndex);
		  		   
		  int c = Parent.getChildCount();
          GTreeNode cNode = new GTreeNode(child, pIndex);
    	  STreeModel.insertNodeInto(cNode, Parent, c);
          Nodes.addElement(cNode);
		   updateUI();
	 }

	 //remove parent also remove the children mean time.
	 public void rmNode(int pos)
	 {//there two steps for deletion
		 //1.work out how many deletions for loop
		 //2 loop the deletions
		 GTreeNode parent = (GTreeNode)Nodes.remove(pos);
		 Nodes.trimToSize();
		 //work out how many deletions and what are they
		 Vector delNodes = new Vector();
		 for(int i=0; i<Nodes.size(); i++)
		 {
			GTreeNode node = (GTreeNode)Nodes.elementAt(i);
			if(pos == node.getParentPost())
			   delNodes.addElement(new Integer(i));
		 }

		 //delete nodes
		 int comp = Nodes.size(); //indicate unchaged size
		 for(int j=0; j<delNodes.size(); j++)
		 {
			 Integer in = (Integer)delNodes.elementAt(j);
			 if(comp > in.intValue())
			 {
			    Nodes.remove(in.intValue());
				comp = in.intValue();
			 }
			 else 
			 {
			   Nodes.remove(in.intValue()-1);
			   comp = in.intValue()-1;
			 }
		 }
		 
		 System.out.println("delete: "+Nodes.toString());
		 STreeModel.removeNodeFromParent(parent);
		 updateUI();
	 }
	

//-------inner classes for tree node selection ----------------- 
	 class DTreeModelListener implements TreeModelListener
	 {
		
		 public void treeNodesChanged(TreeModelEvent tme)
		 {
			 //String msg="call treenode change function";
			 //doPrint(msg, tme);
			 Object source = tme.getSource();
			 if( source instanceof JTree )
			 {
				 Object node = 
				 ((JTree)source).getModel().getRoot();
			 }else if(tme.getSource() instanceof TreeModel)
			 {	 Object node = 
				 ((DefaultTreeModel)source).getRoot();
			 }

		 }

		 public void treeNodesInserted(TreeModelEvent tme)
		 {
			 treeNodesChanged(tme);
			 //String msg="call insert function";
			 //doPrint(msg, tme);
		 }

		 public void treeNodesRemoved(TreeModelEvent tme)
		 {
			 //String msg="call remove function";
			 //doPrint(msg, tme);
			 //STreeSelectModel.setSelectionPath(tme.getTreePath());
			  treeNodesChanged(tme);
		 }

		 public void treeStructureChanged(TreeModelEvent tme)
		 {
			 //String msg="call StructureChanged function";
			 //doPrint(msg, tme);
			 //STreeSelectModel.setSelectionPath(tme.getTreePath());
			 treeNodesChanged(tme);
		 }
	 }//end of StrutModelListener class

	 
	 class DTreeSelection implements TreeSelectionListener
	 {
		 public void valueChanged(TreeSelectionEvent tse)
		 {
			 TreePath selectedPath=STreeSelectModel.getSelectionPath();
			 GTreeNode aNode = (GTreeNode)selectedPath.getLastPathComponent();
		    // Container.doStrutAction(aNode.toString());
			 SelectedObject = aNode.getUserObject();
			 System.out.println(SelectedObject.toString());
			 //setting current node
			 GTreeRoot = aNode;
		 }
	 }//end of strutselection class.



	
 }//end of GTree

 