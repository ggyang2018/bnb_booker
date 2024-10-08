package guiwidget;
/* SlickSoft SS table model based on arrays for high performance
 * Author Guang Yang
 */
import javax.swing.table.AbstractTableModel;

public class SSTableModel extends AbstractTableModel
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
    Object  RowData[][] = null;
    String Header[];

    SSTableModel(Object data[][], String head[])
    {
    	RowData = data;
        Header = head;
    }

    public int getRowCount( ){	 return RowData.length; }

    public int getColumnCount( ){ return Header.length;  }

    public Object getValueAt(int row, int col){return RowData[row][col];}

    public void setDataVector(Object Data[][], String head[])
    {
   	 	RowData = Data;
   	 	Header = head;
    }

    public String getColumnName(int col)
    {  return Header[col]; }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) { return true;}
}


