package guiwidget;
/*Cell render class, it seems should bespoke fields
 * @author Guang Yang
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.table.TableCellRenderer;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SSTabCellRender extends AbstractCellEditor  implements TableCellRenderer 
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	protected int rendMode; //1--
	
	Color bkcolor1 = new Color(228, 228, 228);
	 
	JTextField cellFd = new JTextField();
	public SSTabCellRender(int md) 
	{
		rendMode = md;
	}
	 
	// This method is called each time a cell in a column
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) 
    {
        // 'value' is value contained in the cell located at
        // (rowIndex, vColIndex)
        if (isSelected) {
            // cell (and perhaps other cells) are selected
        }
        if (hasFocus) {
            // this cell is the anchor and the table has the focus
        }
        cellFd.setText(value.toString());
        if(rendMode == 1)
        {
        	cellFd.setBackground(bkcolor1);
        	cellFd.setFont(new Font("Helvetica", Font.BOLD, 11));
        	cellFd.setEditable(false);
        }
        //setToolTipText((String)value);
        return cellFd;
    }
    // The following methods override the defaults for performance reasons
    // This method is called when editing is completed.
    // It must return the new value to be stored in the cell.
    public Object getCellEditorValue() {
        return ((JTextField)cellFd).getText();
    }

    
}
 

