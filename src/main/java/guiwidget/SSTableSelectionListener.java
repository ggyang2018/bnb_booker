package guiwidget;


import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SSTableSelectionListener implements ListSelectionListener 
{
    SSTablePane table;
    boolean xyFg;
    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    SSTableSelectionListener(SSTablePane table, boolean isRow) 
    {
    	this.table = table;
    	xyFg = isRow;
    }
    
    public void valueChanged(ListSelectionEvent e) 
    {
    	ListSelectionModel lsm_1=(ListSelectionModel)e.getSource();
    	if(!lsm_1.isSelectionEmpty());
    	{
    		if(xyFg)
    			table.RowIndex = lsm_1.getMinSelectionIndex();
    		else
    			table.colIndex = lsm_1.getMinSelectionIndex();
    	}
        //System.out.println("SSTAbListener: row="+table.RowIndex+"\t col="+table.colIndex);
        if (e.getValueIsAdjusting())
         table.makeSingleClick(table.RowIndex, table.colIndex);        
    }
       
    /* As reference
     * public void valueChanged(ListSelectionEvent e) 
    {
        // If cell selection is enabled, both row and column change events are fired
        if (e.getSource() == table.MainTable.getSelectionModel()
              && table.MainTable.getRowSelectionAllowed())
        {
            // Column selection changed
        	int first = e.getFirstIndex();
        	System.out.println("Firstx="+first);
        	table.RowIndex = first;
            
        	//int last = e.getLastIndex();
        } else if (e.getSource() == table.MainTable.getColumnModel().getSelectionModel()
               && table.MainTable.getColumnSelectionAllowed() )
         {
            // Row selection changed
        	int first = e.getFirstIndex();
            table.colIndex = first;
           // int last = e.getLastIndex();
        }
        if (e.getValueIsAdjusting()) {System.out.println("just has been changed");}
        table.makeSingleClick(table.RowIndex, table.colIndex);
    }*/
}



