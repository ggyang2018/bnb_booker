package guiwidget;

import javax.swing.tree.*;


public class GTreeNode extends DefaultMutableTreeNode
 {
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 private int ParentPost = 0;
	 private String ParentName = null;

	 public GTreeNode(Object obj, int pos)
	 {     
		 super(obj);
		 ParentPost = pos;
	 }

	 public GTreeNode(Object obj, String pName)
	 {  
		 super(obj);
		 ParentName = pName;
	 }

	 public void setParentPost(int pos)
	 {  ParentPost = pos;  }

	 public int getParentPost( )
	 {  return ParentPost;    }

	 public String getName( )
	 {
		 Object o = getUserObject( );
		 return o.toString();
	 }

	 public String setName(Object o)
	 {  return o.toString();   }

	 public void setParentName(String name)
	 { ParentName = name; }

	 public String getParentName( )
	 {  return ParentName;  }
 }
 
/* ********************************************************* 
 Rules:
 input data vector data the first element and parent number 
 array index[] is invisible. 
 index[0] = 0;
 index[i], i represents the object position start from zero.
 index[i]= p is a pointer to parent node also start from zero
 e.g data.addElement(e1);
     data.addElement(e1);
	 int index[] ={0, 1, 0, 2, 4, 2};

 Apart from string, every node object must be override toString().

  Extend information. 
  1. set Panel,
  2. class super constructor for data initiall input.
  3. override constructor for more composite action.

************************************************************ */
