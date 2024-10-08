package bbooker;

/* Room panel to enter a Room details
 * @Author Guang Yang
 */
import javax.swing.*;
import javax.swing.border.Border;
import guiwidget.XYLayout;
import java.awt.event.*;

public class RoomPane extends JPanel implements ActionListener 
{
	static public final long serialVersionUID = BookDlg.serialVersionUID;
	protected JTextField  facilityFd, capFd, descFd, nightFd, discntFd;
	protected JTextField roomFeeFd,addPayFd, mealNbrFd, mealFeeFd;//typeFd, 
	JTextField totalFd, paidFd,  balFd, uidFd, timeFd, permealFd, mealInfFd;
	JTextArea noteArea, infoArea;	
	protected JComboBox names;
	protected JTextField names1;
	protected BKEditorDlg bookDlg; //as container
	protected boolean editFg = false;
	//protected RoomProp actRoom;
	DBDataBusImp dataBus;
	boolean isNew = true;
	String prcx[] = { "Per Room", "Per Person", "User Input"};
	JComboBox prcBx;
	
	 JTabbedPane tabPane;

	
	public RoomPane(BKEditorDlg dlg)
	{
		bookDlg = dlg;
		setLayout(new XYLayout());
		Border aBorder = BorderFactory.createRaisedBevelBorder();
		setBorder(aBorder);
		//initPane();
	}
	public void setEditable(boolean fg) { editFg = fg; }
	public void setHasNew(boolean is){isNew = is; }
	public void initPane() //wid 545 x 195
	{
	   int x0=5, y0=5, h=20, w=70, hoff=3;
	   int x1= w+x0+hoff,  w1=100;
	   int x2= x1+w1+hoff, w2=70;
	   int x3= x2+w2+hoff, w3 = 100;
	   int x4= x3+w3+hoff, w4=70;
	   int x5= x4+w4+hoff, w5 = 100;	   
	   JLabel lb0 = new JLabel("Rooms Total", JLabel.RIGHT);
	   lb0.setBounds(x0, y0, w, h);
	   nightFd = new JTextField();
	   nightFd.setBounds(x1, y0, w1, h);
	   nightFd.setEditable(false);
	   add(lb0); add(nightFd);	  	   
	   
	   JLabel Lb1 = new JLabel("Discount", JLabel.RIGHT);
	   Lb1.setBounds(x2, y0, w2, h);
	   discntFd = new JTextField("0.0");
	   discntFd.setBounds(x3, y0, w3, h);
	   add(Lb1); 	add(discntFd);
	   
	   JLabel ntLb = new JLabel("Extra Fee", JLabel.RIGHT);
	   ntLb.setBounds(x4, y0, w4, h);
	   addPayFd = new JTextField();
	   addPayFd.setBounds(x5, y0, w5, h);
	   add(ntLb); add(addPayFd);
	   y0=y0+h+hoff;
	   JLabel mLb = new JLabel("Meal Info", JLabel.RIGHT);
	   mLb.setBounds(x0, y0, w, h);
	   mealInfFd = new JTextField();
	   mealInfFd.setBounds(x1, y0, w5+x5-x1-hoff, h);
	   add(mLb); add(mealInfFd);
	   y0=y0+h+hoff;	   
	   JLabel apLb = new JLabel("Per meal", JLabel.RIGHT);
	   apLb.setBounds(x0, y0, w, h);
	   permealFd = new JTextField("0.0");
	   permealFd.setBounds(x1, y0, w1, h);
	   add(apLb);  add(permealFd);	   
	   JLabel mnbrLb = new JLabel("Meals No.", JLabel.RIGHT);
	   mnbrLb.setBounds(x2, y0, w2, h);
	   mealNbrFd = new JTextField("0");
	   mealNbrFd.setBounds(x3, y0, w3, h);
	   add(mnbrLb); 	add(mealNbrFd);
	   JLabel mfeeLb = new JLabel("Meals cost", JLabel.RIGHT);
	   mfeeLb.setBounds(x4, y0, w4, h);
	   mealFeeFd = new JTextField("0.0");
	   mealFeeFd.setBounds(x5, y0, w5, h);
	   add(mfeeLb);	add(mealFeeFd);				   	   
	   y0=y0+h+hoff;	  
	   JLabel payst = new JLabel("Total", JLabel.RIGHT);
	   payst.setBounds(x0, y0, w, h);
	   totalFd = new JTextField("0.0");
	   totalFd.setBounds(x1, y0, w1, h);
	   
	   JLabel paidLb = new JLabel("Deposit", JLabel.RIGHT);
	   paidLb.setBounds(x2, y0, w2, h);
	   paidFd = new JTextField("0.0");
	   paidFd.setBounds(x3, y0, w3, h);
	   JLabel balLb = new JLabel("Balance", JLabel.RIGHT);
	   balLb.setBounds(x4, y0, w4, h);
	   balFd = new JTextField("0.0");
	   balFd.setBounds(x5, y0, w5, h);
	   add(payst); add(totalFd);add(paidLb); add(paidFd); add(balLb); add(balFd);
	   
	   //y0=y0+h+hoff;	   
	   JLabel mob = new JLabel("Others", JLabel.RIGHT);
	   mob.setBounds(x0, y0, w, h);
	   descFd = new JTextField();
	   descFd.setEditable(editFg);
	   descFd.setBounds(x0+w+5, y0, w1, h);	   	   
	   setFields(0);
	  
	   y0=y0+h+hoff;
	   JLabel uidLb = new JLabel("Booked by", JLabel.RIGHT);
	   uidLb.setBounds(x0, y0, w, h);
	   uidFd = new JTextField();
	   uidFd.setBounds(x1, y0, w1, h);
	   uidFd.setEditable(false);
	   add(uidLb);   add(uidFd);
	   JLabel tmLb = new JLabel("Book Time", JLabel.RIGHT);
	   tmLb.setBounds(x2, y0, w2, h);
	   timeFd = new JTextField();
	   timeFd.setBounds(x3, y0, w3, h);
	   timeFd.setEditable(false);
	   add(tmLb); add(timeFd);	   
	   JLabel prcLb = new JLabel("Based On", JLabel.RIGHT);
	   prcLb.setBounds(x4, y0, w4, h);
	   prcBx = new JComboBox(prcx);
	   prcBx.setBounds(x5, y0, w5, h);
	   add(prcLb); add(prcBx);
	   y0=y0+h+hoff;
	   JSeparator sp1 = new JSeparator();
	   sp1.setBounds(2, y0, 540, 5);
	   add(sp1);
	   y0=y0+5;	   	
	   int nth = 80;
	   noteArea = new JTextArea();
	   noteArea.setLineWrap(true);
	   noteArea.setWrapStyleWord(true);
	   noteArea.setEditable(false);
	   JScrollPane scrTxt = new JScrollPane(noteArea);
	   infoArea = new JTextArea();
	   infoArea.setLineWrap(true);
	   infoArea.setWrapStyleWord(true);
	   JScrollPane scrTxt1 = new JScrollPane(infoArea);
	   tabPane  = new JTabbedPane();
	   tabPane.add("Book Info", scrTxt);
	   tabPane.add("Customer Info", scrTxt1);
	   tabPane.setBounds(x0+3, y0,  520, nth);
	   add(tabPane);
	}
	
	protected void setFields(int idx)
	{
		/*String rmid = dataBus.getRoomNameOID(1).get(idx);
		RoomProp prop = dataBus.getRoomMap().get(rmid);
		if(!isNew)names1.setText(prop.getRoomName());
		facilityFd.setText(prop.getFacility());
		capFd.setText(prop.getCapacity());
		priceFd.setText(prop.getPrice());
		descFd.setText(prop.getNote());
		*/
	}
	
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("RoomID"))
		{
			int idx1= names.getSelectedIndex();
			setFields(idx1);
    	}		
	}
	
}
