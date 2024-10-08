package bbooker;
/* SelectPane set first and select additional objects from List
 * @Author Guang Yang
 */
import guiwidget.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

import nxd.GenData;

public class SelectPane extends JPanel
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	//JTextField firstFd, priceFd, rtypeFd;
	JComboBox prcBx;
	JCheckBox endBx;
	int twid[]={100, 110, 45, 25};
	String thead[] = {"Room", "Type", "Price", "Book"};
	String prc[] = { "Per Room", "Fix Per Room", "Per Person"};
	//List<RoomData> rooms;	
	BookDlg  bookDlg = null;	
	JTable roomTab;
	int rowNbr=8;
	int colNbr = 4;
	
	public SelectPane(BookDlg dlg)
	{
		bookDlg = dlg;
		Border aBorder = BorderFactory.createRaisedBevelBorder();
		setBorder(aBorder);
	}
	
	
	public void initPane() //230 x 145
	{
	   setLayout(new XYLayout());
	   int x0=5, y0=2, h=20, off=3;
	   JLabel ptit = new JLabel("Room(s) for Booking", JLabel.CENTER);
	   ptit.setBounds(x0, y0, 250, h);
	   add(ptit);
	   y0 = y0+h+off;	   
	   /*JLabel fstLb = new JLabel("Room:", JLabel.RIGHT);
	   int rx0=x0, rw1=40, rw2=110, rw3=50;
	   fstLb.setBounds(x0, y0, rw1, h);
	   rx0 = rw1+x0+off;
	   firstFd = new JTextField();		  
	   firstFd.setBounds(rx0, y0, rw2, h);
	   firstFd.setEditable(false);
	   rx0 = rx0+rw2+40;
	   JLabel fstLb2 = new JLabel("Price:", JLabel.RIGHT);
	   fstLb2.setBounds(rx0, y0, rw1, h);
	   rx0 = rx0+rw1+off;
	   priceFd = new JTextField();
	   priceFd.setBounds(rx0, y0, rw3, h);
	   
	   y0=y0+h+off;
	   JLabel fstLb1 = new JLabel("Type:", JLabel.RIGHT);
	   fstLb1.setBounds(x0, y0, rw1, h);
	   rx0 = x0+rw1+off;
	   rtypeFd = new JTextField();
	   rtypeFd.setBounds(rx0, y0, 2*rw2+25, h);
	   rtypeFd.setEditable(false);
	   rx0 = rx0+rw2;
	   	   
	   add(fstLb); add(firstFd);
	   add(fstLb1); add(rtypeFd);
	   add(fstLb2); add(priceFd);
	   y0 = y0+h+off;
	   */
	   int  rw1=40, rw2=110;
	   int tbh= 120;
	   roomTab = new JTable(new BKTableModel());
	   setTableProp();
	   JScrollPane jsp1 = new JScrollPane(roomTab);
	   int tbw = 3*rw1+2*rw2-50;
	   jsp1.setBounds(x0+5, y0, tbw, tbh);
	   add(jsp1);
	   y0 = y0+tbh+2*off;
	   endBx = new JCheckBox("End of Booking", true);
	   endBx.setBounds(x0, y0, 3*rw1, h);
	   add(endBx);	   
	   prcBx = new JComboBox(prc);
	   prcBx.setBounds(tbw-90, y0, 100, h);
	   add(prcBx);	   
	}		
	
	public List<RoomData> getModelData()
	{
		BKTableModel bm =(BKTableModel)roomTab.getModel();
		Object[][] dat = bm.getData();
		List<GenData> nms = bookDlg.frm.bkBus.getRoomMap().getAll();
		List<RoomData> lst = new ArrayList<RoomData>();
		for(int i=0; i<bm.getRowCount(); i++)
		{
			RoomData rmd =(RoomData)nms.get(i);
			Boolean bb = (Boolean)dat[i][3];
			if(bb.booleanValue()==true && rmd.getName().equals(dat[i][0]))
			{
				Double dd = (Double)dat[i][2];
				rmd.setPrice(dd);
				lst.add(rmd);
			}
		}
		return lst;
	}
	
	protected void setTableProp()
	{
	   for (int i = 0; i < 3; i++) 
	   {
		  TableColumn column = roomTab.getColumnModel().getColumn(i);
		  column.setPreferredWidth(twid[i]); //third column is bigger
		  //column.setHeaderValue(thead[i]);
	   }
	}
	
	protected Object[][] setTabData()
	{
		List<GenData> nms = bookDlg.frm.bkBus.getRoomMap().getAll();		
		List<RoomData> rms = new ArrayList<RoomData>();
		int i=0;
		for(i=0; i<nms.size(); i++)
		{
			RoomData rmd =(RoomData)nms.get(i);
			
			if(!rmd.getStatus().equals("free")) continue;
			else rms.add(rmd);
		}
		if(rms.size()<1)
		{
			Object[][] data0 = new Object[1][4];
			data0[0][0] = new String("");
			data0[0][1] = new String("");
			data0[0][2] = new Double(0.0);
			data0[0][3] = new Boolean(false);
		}
				
		Object[][] data1 = new Object[rms.size()][4];
		for(i=0; i<rms.size(); i++)
		{
			RoomData rmd = rms.get(i);
			data1[i][0] = rmd.getName();
			data1[i][1] = rmd.getRoomTypeDesc();
			data1[i][2] = rmd.getPrice();
			data1[i][3] = new Boolean(false);
		}							
		return data1;
	}
	
	class BKTableModel extends AbstractTableModel
	{
		static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
		
        private String[] columnNames = {"Room","Type", "Price","Book"};                           
        private Object[][] data ;
        
        public BKTableModel()
        {
        	super();
        	data = setTabData();
        }
        public int getColumnCount(){return columnNames.length;}
        public int getRowCount(){ return data.length; }
        public String getColumnName(int col) { return columnNames[col];}
        public Object getValueAt(int row, int col) {return data[row][col];}
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @SuppressWarnings("unchecked")
		public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) 
        {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 0) return false;
            else return true;     
        }
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col)
        {  
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }      
        public Object[][] getData(){ return data; }
	}	
}
