
 /* ********************************************************
  Table model for non-edit cells
 ******************************************************** */
package guiwidget;

 import java.util.*;
 import javax.swing.table.*;
 
 public class GTableModel extends AbstractTableModel
 {
	 static public final long  serialVersionUID = 123456789;
     Vector<Vector<String>> RowData = null;
     Vector<String> Header;

     GTableModel(Vector<Vector<String>> data, Vector<String> head)
     {
    	 RowData = data;
         Header = head;
     }

     public int getRowCount( ){	 return RowData.size(); }

     public int getColumnCount( ){ return Header.size();  }

     public String getValueAt(int row, int col)
     {
    	// System.out.println("row="+row+"\tcol="+col);
    	 Vector<String> v = RowData.elementAt(row);
    	 return v.elementAt(col);
     }

     public void setDataVector(Vector<Vector<String>> Data, Vector<String> head)
     {
    	 RowData = Data;
    	 Header = head;
     }

     public String getColumnName(int col)
     {  return Header.elementAt(col); }
 }



