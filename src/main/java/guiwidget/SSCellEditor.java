package guiwidget;
/*Cell render and cell editor should be use together
 * @author Guang Yang
 */



import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class SSCellEditor extends DefaultCellEditor implements ActionListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	protected JButton btn;
	protected String  lb;
	protected boolean isPushed;
	int rowIdx, colIdx;
	
	public SSCellEditor(JCheckBox chk)
	{
		super(chk);
		btn = new JButton();
		btn.setOpaque(true);
		btn.addActionListener(this);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, 
			boolean isSelected, int row, int col)
	{
		if(isSelected)
		{
		   btn.setForeground(table.getSelectionForeground());
		   btn.setBackground(table.getSelectionBackground());
		}
		
		lb = (value == null) ? "" : value.toString();
		btn.setText(lb);
		rowIdx = row; colIdx = col;
		isPushed = true;
		return btn;
	}
	
	public Object getCellEditorValue()
	{
		if(isPushed)
		{
		   System.out.println("pushed get xxx");	
		}
		isPushed = false;
		return new String(lb)+Integer.toString(rowIdx);
	}
	
	public boolean stopCellEditing()
	{
		isPushed = false;
		return super.stopCellEditing();
	}
	
	protected void fireEditingStopped()
	{		super.fireEditingStopped(); }
	
	public void actionPerformed(ActionEvent av)
	{
		System.out.println("action provoked ");
	}
	

}
