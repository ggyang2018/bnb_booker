package guiwidget;
/* SS SlickSoft Table for high performance 
 * @Author Guang Yang
 */

import javax.swing.*;
import javax.swing.table.*;

public class SSTablePane extends JScrollPane
{	
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
  //UI components
    protected JTable MainTable;
  //data components
    protected  int RowIndex = 0;
    protected  int colIndex =0;
    
    protected  SSTableModel TabDataModel;
    protected  Object Data[][];
    protected  String Header[];
    private int ColWidthes[];
   //sorting
   private final boolean sortFlag;
   
   PanelAdapter adapt;
   
   boolean clearSelectFg = false; //if clear selecion after lost Focus, default is not

  
   public SSTablePane(Object data[][], String header[])
   {
      sortFlag = false;
      setData(data, header);	
   }

  public void setAdaptor(PanelAdapter adt) { adapt = adt; }

  //set data called by contructor
   public void setData(Object data[][])
   {   Data = data; 
       if(sortFlag)
       TabDataModel = new SSTableModel(Data, Header);
   }

   public void setData(Object data[][], String header[])
   {
	  Data = data;
	  Header = header;
	  TabDataModel = new SSTableModel(Data, header);
     
	  //set default width of columns as 100 pixels
	  ColWidthes = new int[Header.length];
	  for(int i=0; i<Header.length; i++)
	  	  ColWidthes[i] = 100;	  
  }
   
  public int getSelectedRow() { return RowIndex; }

   //should be override as 
   public void setPane(boolean mutipleSelect)
   {
	   setMainTable(getTabDataModel(), getMainCM(), mutipleSelect);
	   updateUI();
   }
   
   public void hasClearSelection(boolean is) { clearSelectFg = is; }
      
   public JTable getMainTable() { return MainTable; }
   public int[] getMultiSelectedRows()
   {  	return MainTable.getSelectedRows();}

   public SSTableModel getTabDataModel()
   {    return TabDataModel; }

   public void setMainTable(TableModel tm, TableColumnModel tcm, boolean multSelect )
   {		
	   MainTable  = new JTable(tm, tcm);
	   MainTable.createDefaultColumnsFromModel();
	   MainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	  if(!multSelect)
		   MainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   else
	   MainTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	  MainTable.setColumnSelectionAllowed(true);
	  MainTable.setRowSelectionAllowed(true);
     //MainTable.setCellSelectionEnabled(true);
	  MainTable.getSelectionModel()
	  .addListSelectionListener(new SSTableSelectionListener(this, true));
	  MainTable.getColumnModel().getSelectionModel().
	  addListSelectionListener(new SSTableSelectionListener(this, false));
	  getViewport().add(MainTable);     
   }
    
  public void refreshTab(String hd[], Object dta[][])
  {
	  TabDataModel.setDataVector(dta,hd);
      TabDataModel.fireTableChanged(null);
  }
  
  public Object[] getTabRow(int index){  return Data[index];}

  protected void clearTab(DefaultTableModel TabDataModel)
  {
	 String[][] nodata = new String[][]{ {" "} };
	 String[] noheader = new String[]{ " " };
	 TabDataModel.setDataVector(nodata, noheader);
	 TabDataModel.fireTableChanged(null);
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
	


  public TableColumnModel getMainCM()
  {   return cm;  }

 //------- makeXXX for rewrite -------------
 //single click on selected row and pass row index
  public void makeSingleClick(int row, int col)
  {
	  try
	  {
		 if(row<0) return;
		 adapt.setCellIdcs(row, col);
		 Object rv[] = getTabRow(row);
		 adapt.processRow(rv);		 
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
	
//---------- eays interface-----------------------------
  //user after call constructor 
  public void setColumnWidths(int wids[]) { ColWidthes = wids;}

  
}//end of GTablePane class

//------------- support class may be public-----------------
class SSColumnModel extends DefaultTableColumnModel
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

