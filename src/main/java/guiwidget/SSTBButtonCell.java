package guiwidget;
/*Cell render class, it seems should bespoke button
 * three status free, provisional, taken;
 * @author Guang Yang
 */
import java.awt.Color;
import java.awt.Component;

//import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.UIManager;

public class SSTBButtonCell extends JButton  implements TableCellRenderer
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	protected int rendMode; //1--
	Color bkcolor1 = new Color(228, 228, 228);
	Color ligtGreen = new Color(230, 255, 230);
	Color ligtYellow = new Color(255, 255, 230);
	Color ligtRed = new Color(255, 230, 230);
	
	//JButton cellBtn;
	int rowIdx, colIdx;
	public SSTBButtonCell(int md) 
	{
		rendMode = md;
		setBorder(null);
		setEnabled(true);
		setOpaque(true);
	}
	 
	// This method is called each time a cell in a column
	//getTableCellEditorComponent(JTable table, Object value,
      //      boolean isSelected, int rowIndex, int vColIndex) {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean isFocus, int rowIndex, int vColIndex) 
    {
    	rowIdx = rowIndex; colIdx = vColIndex;
    	String val = new String();
    	try
    	{
    		val = value.toString();
    		val = val.toLowerCase();
    		/*if(val != null && val.trim().length()>1)
    		{
    			if(val.equalsIgnoreCase("expected"))
    				setBackground(ligtYellow);
    			else if(val.equalsIgnoreCase("taken") 
    				 || val.equalsIgnoreCase("confirmed"))
    				setBackground(ligtRed);
    			else 
    				setBackground(ligtGreen);
    		}else
    			setBackground(ligtGreen);
    		*/
    		
    		if(val != null && val.trim().length()>1)
    		{
    			if(val.indexOf("provisional")>=0)
    				setBackground(ligtYellow);
    			else if(val.indexOf("taken")>=0 
    				 || val.indexOf("confirmed")>=0)
    				setBackground(ligtRed);
    			else 
    				setBackground(ligtGreen);
    		}else
    			setBackground(ligtGreen); 				
    	}catch(Exception ex) {setBackground(ligtGreen); val = new String();}
        setText(val);
    	
    	if (isSelected)
    	{
    	  	setBorder(UIManager.getBorder("Table.focusCellHighlightBorder") );
    	  	setForeground(table.getSelectionForeground());
    		setBackground(table.getSelectionBackground());
    	}else setBorder(null);
    	
        if (isFocus) 
        { }
        //System.out.println("ookkkk");       
        //setToolTipText((String)value);
        return this;
    }
    
       
}
 

