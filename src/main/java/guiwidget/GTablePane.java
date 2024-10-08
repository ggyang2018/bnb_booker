/* *************************************************************
   Data-model-table 
  Input: data (double vector) and header, if input data in arry
         format, call empty constractor and then call 
		 setWidget(object[][], object[]) not hold data but reference
  Output: a selected Vector or RowIndex
  Processing: Display table allow add and remvoe table
  Model: data-model-table
  @Author Guang Yang
  ************************************************************* */

package guiwidget;

 import javax.swing.*;
 import javax.swing.table.*;
 import javax.swing.event.*;
 import java.util.*;
 import java.awt.*;
import java.awt.event.*;
 
 
 public class GTablePane extends JScrollPane
 implements FocusListener, MouseListener, KeyListener 
 {	
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
   //UI components
     public JTable MainTable;
     protected JTable TRowHeader;
   //data components
     public  int RowIndex = 0;
     public  GTableModel TabDataModel;
     public  Vector<Vector<String>> Data;
     public  Vector<String> Header;
     private int ColWidthes[];
    //sorting
    private final boolean sortFlag;
    private int sortIndex = 0;
    
    PanelAdapter adapt;
    
    boolean clearSelectFg = false; //if clear selecion after lost Focus, default is not

    public GTablePane(boolean is )
    { sortFlag = is;  }
    
    public GTablePane(PanelAdapter adpt, Vector<Vector<String>> data, Vector<String> header)
    {
    	adapt = adpt;
        sortFlag = false;
        setData(data, header);     
    }

    public GTablePane(Vector<Vector<String>> data, Vector<String> header)
    {
       sortFlag = false;
       setData(data, header);	
    }

    public GTablePane(Vector<Vector<String>> data, Vector<String> header, boolean isSort, int index)
    {
        sortFlag = isSort;
        sortIndex = index;
        setData(data, header);
    }

   //set data called by contructor
    public void setData(Vector<Vector<String>> data)
    {   Data = data; 
        if(sortFlag)
        sortData(sortIndex);
        TabDataModel = new GTableModel(Data, Header);
    }

    public void setData(Vector<Vector<String>> data, Vector<String> header)
    {
	  Data = data;
	  Header = header;
      if(sortFlag)
        sortData(sortIndex);
	  TabDataModel = new GTableModel(Data, header);
		
	//set default width of columns as 100 pixels
	  ColWidthes = new int[Header.size()];
	  for(int i=0; i<Header.size(); i++)
	  {
		  ColWidthes[i] = 100;
	  }
   }

    //should be override as 
    public void setPane( )
    {
	   setMainTable(getTabDataModel(), getMainCM());
	   updateUI();
    }
    
    public void hasClearSelection(boolean is) { clearSelectFg = is; }
    
    public void setMutilSelectPane()
    {
       TableModel tm = getTabDataModel(); 
       TableColumnModel tcm = getMainCM();
       MainTable  = new JTable(tm, tcm);
  	   MainTable.createDefaultColumnsFromModel();
  	   MainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
  	   MainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  	   ListSelectionModel lsm = MainTable.getSelectionModel();
  	   lsm.addListSelectionListener(new ListSelectionListener()
  	   {
  	      public void valueChanged(ListSelectionEvent le)
            {
  	        ListSelectionModel lsm_1=(ListSelectionModel)le.getSource();
  	        if(!lsm_1.isSelectionEmpty());
  	        {
  	             RowIndex = lsm_1.getMinSelectionIndex();
  				 makeSingleClick(RowIndex);
  	        }
  		  }
  	   });
  		
  	   getViewport().add(MainTable);
  	   MainTable.addFocusListener(this);
  	   MainTable.addMouseListener(this);
  	   MainTable.addKeyListener(this);
  	   updateUI();
    }
    
    public int[] getMultiSelectedRows()
    {  	return MainTable.getSelectedRows();}

    public GTableModel getTabDataModel()
    {    return TabDataModel; }

    public void setMainTable(TableModel tm, TableColumnModel tcm )
    {		
	   MainTable  = new JTable(tm, tcm);
	   MainTable.createDefaultColumnsFromModel();
	   MainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	   MainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   ListSelectionModel lsm = MainTable.getSelectionModel();
	   lsm.addListSelectionListener(new ListSelectionListener()
	   {
	      public void valueChanged(ListSelectionEvent le)
          {
	        ListSelectionModel lsm_1=(ListSelectionModel)le.getSource();
	        if(lsm_1.isSelectionEmpty());
	          //System.out.println("No selection");
	        else
			{
	             RowIndex = lsm_1.getMinSelectionIndex();
				 makeSingleClick(RowIndex);
           	}
		  }
	   });
		
	    getViewport().add(MainTable);
        MainTable.addFocusListener(this);
        MainTable.addMouseListener(this);
        MainTable.addKeyListener(this);
	}
	
    public void addTabRow(Vector<String> row)
    {
       Data.addElement(row);
       if(sortFlag)
    	   sortData(sortIndex);
       TabDataModel.setDataVector(Data, Header);
       TabDataModel.fireTableChanged(null);
    }

   public Object rmTabRow(int index)
   {
       Object obj = Data.remove(index);
       TabDataModel.setDataVector(Data,Header);
       TabDataModel.fireTableChanged(null);
       return obj;
   }
   
   public void refreshTab(Vector<String> hd, Vector<Vector<String>> dta)
   {
	   TabDataModel.setDataVector(dta,hd);
       TabDataModel.fireTableChanged(null);
   }
   
   public Vector<String> getTabRow(int index){  return Data.get(index);}

   protected void clearTab(DefaultTableModel TabDataModel)
   {
	 String[][] nodata = new String[][]{ {" "} };
	 String[] noheader = new String[]{ " " };
	 TabDataModel.setDataVector(nodata, noheader);
	 TabDataModel.fireTableChanged(null);
    }

   public void sortData(int col_index)
   {
	 String[] b1 = new String[Data.size()];
	 for(int i=0; i<Data.size(); i++)
	 {
		 Vector<String> v = (Vector<String>)Data.elementAt(i);
	     String s = v.elementAt(col_index).toString();
	     b1[i]=s;
	 }
	 Arrays.sort(b1);
	 Vector<Vector<String>> Data1 = new Vector<Vector<String>>( );
	 int pointer = 0;
	 while(pointer <b1.length)
	 {			
	    for(int j=0; j<b1.length; j++)
	    {
		  Vector<String> v1 = (Vector<String>)Data.elementAt(j);
		  if(v1.contains(b1[pointer]))
		    Data1.addElement(v1);
	    }
	    pointer++;
	 }
	 //set new data
      Data = Data1;
   }

   // component operation	
   public void setColWidth(TableColumnModel cm, int colIndex, int width)
   {//this method being called for every model changed
	 cm.getColumn(colIndex).setPreferredWidth(width);
	 MainTable.setColumnModel(cm);
	 updateUI();	
   }

   public void setColWidth(int colIndex, int width)
   {   
	 if(colIndex >=ColWidthes.length)
	 return;

	 ColWidthes[colIndex] = width;
   }
	
   public Vector<Vector<String>> convertData(String[][] obj)
   {
	   Vector<Vector<String>> tab = new Vector<Vector<String>>();
	   for(int i=0; i<obj.length; i++)
	   {
		   Vector<String> row = new Vector<String>();
		   for(int j=0; j<obj[i].length; j++)
			   row.addElement(obj[i][j]);
	   	   tab.addElement(row);
	   }
	   return tab;
   }	

   public Vector<String> convertHeader(String[] obj)
   {
	  Vector<String> h = new Vector<String>();
	  for(int j=0; j<obj.length; j++)
	     h.addElement(obj[j]);
	  return h;
   }

  //--------- listen implemetation ---------------------------
   public void focusLost(FocusEvent fe)
   {
       if(fe.isTemporary( ))
       return;
     
       if(clearSelectFg)
    	   MainTable.clearSelection( );
       //GAbsPanel pane =(GAbsPanel)getParent();
       //pane.setActToolBar(false);
    }

   public void focusGained(FocusEvent fe) {   }

   public void mouseReleased(MouseEvent me)
   {
      if(me.getClickCount()==2)
         makeDoubleClick(RowIndex);
   }

   public void mouseClicked(MouseEvent me) { }
   public void mouseEntered(MouseEvent me) { }
   public void mouseExited(MouseEvent me) { }
   public void mousePressed(MouseEvent me){ }

   public void keyReleased(KeyEvent ke)
   {
      switch(ke.getKeyCode( ))
      {
        case KeyEvent.VK_INSERT:
           break;
        case KeyEvent.VK_F12:
           break;
        case KeyEvent.VK_DELETE:
           break;
        default:
           return;
      }
   }

   public void keyTyped(KeyEvent ke) { }
   public void keyPressed(KeyEvent ke) {  }

   public TableColumnModel getMainCM()
   {   return cm;  }

  //------- makeXXX for rewrite -------------
  //single click on selected row and pass row index
   public void makeSingleClick(int row )
   {
	   try
	   {
		 Vector<String>rv = getTabRow(row);
		 adapt.processRow(rv.toArray());
	   }catch(Exception ex)
	   {  ; }
   }

   //double click on the row and pass row index
   public void makeDoubleClick(int row)
   {
	  try
	  {
		  Vector<String>rv = getTabRow(row);
		  adapt.processRow(rv.toArray());
	  }catch(Exception ex)
	  {  ; }
   }

   //--------- inner class ------------------------------
   TableColumnModel cm = new TColumnModel()
   {
	  static public final long  serialVersionUID = 123456789;
	  int col_count = 0;
	  public void addColumn(TableColumn tc)
	  {
	     tc.setPreferredWidth(ColWidthes[col_count]);
	     col_count++;
	     super.addColumn(tc);
	  }
   };
	
// ---------- eays interface-----------------------------
   //user after call constructor 
   public void setColumnWidths(int wids[]) { ColWidthes = wids;}

   public void setTRowHeader(TableModel tm, TableColumnModel tcm )
	{		
		TRowHeader  = new JTable(tm, tcm);
		TRowHeader.createDefaultColumnsFromModel();
		//make sure two table has same selected model
		TRowHeader.setSelectionModel(MainTable.getSelectionModel());
		//make the header column look pretty
		TRowHeader.setMaximumSize(new Dimension(40, 1000));
		TRowHeader.setBackground(Color.lightGray);
		//make selection invisible on the header
		TRowHeader.setSelectionBackground(Color.lightGray);
		TRowHeader.setColumnSelectionAllowed(false);
		TRowHeader.setCellSelectionEnabled(false);
		
		//put in viewport for easy control
		JViewport jv = new JViewport();
		jv.setView(TRowHeader);
		jv.setPreferredSize(TRowHeader.getMaximumSize());

		TRowHeader.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setRowHeader(jv);
	}

 
 
}//end of GTablePane class

//------------- support class may be public-----------------
class TColumnModel extends DefaultTableColumnModel
{
   static public final long  serialVersionUID = 123456789;
   //it is main fuction to deal with. overriding this one is good
   public void addColumn(TableColumn tc){super.addColumn(tc);}

   public void setColumnWidth(int index, int wid)
   {	  getColumn(index).setPreferredWidth(wid);}

   protected void sortColumn(TableColumn tc)
   {
	 int newIndex = sortedIndexOf(tc);
	 if(newIndex != tc.getModelIndex())
	  moveColumn(tc.getModelIndex(), newIndex);
   }

   protected int sortedIndexOf(TableColumn tc)
   {
	 int stop = getColumnCount();
	 String name = tc.getHeaderValue().toString();
	 for(int i=0; i<stop; i++)
	 {
	  if(name.compareTo(getColumn(i).getHeaderValue().toString())<=0)
	   return i;
	 }
	 return stop;
   }
   
}

