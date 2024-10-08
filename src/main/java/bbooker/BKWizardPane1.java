package bbooker;


import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.*;

import guiwidget.*;

public class BKWizardPane1 extends GWizardPane
{
	static public final long  serialVersionUID = GWizardPane.serialVersionUID;
	java.text.DecimalFormat df = new
	java.text.DecimalFormat("######.##");	
    BKFrame frm;
    String payMethes[] ={"Cash", "Credit Card", "Debit Card", "Cheque", "Others"};
    String payBy[] = {"Individual", "Group"};
    String bookStt[] = {"provisional", "confirmed", "taken"};
    String reasons[] = {"Equipment", "Other"};
	JTextField daysFd, groupFd, fixFd, totalDiscntFd, addFd;
	JTextField arriveFd, mealNbrFd, mealFeeFd, permealFd, mealInfFd; 
	JTextField totalFd, paidFd,  balFd, nightFd, nbrFd, childFd;
	JComboBox payMtFd, payByBx,bookSttBx, reasonBx;
	JTextArea noteArea;
	JButton calBtn;
	BKWizardDlg wdlg;
    
	public BKWizardPane1(BKWizardDlg dlg)
	{
		super(dlg, "Book Room Pricing Information", 1, false);
		super.initPane();
		frm = dlg.frm;
		wdlg = dlg;
		init2();
	}
	
	void init2()
	{
		int y0 = getY0();
		int x0=3, w=100, w1=100, h=20, off=3;
		int x1 = x0+w+off;
		int nx0= x0+w+5+w1+15;
		int nx1=nx0+w+off;
		JLabel mtLb = new JLabel("Group Name", JLabel.RIGHT);
		mtLb.setBounds(x0, y0, w, h);
		groupFd = new JTextField("Individaul");
		groupFd.setBounds(x0+w+5, y0, w1, h);
		add(mtLb);
		add(groupFd);
		JLabel ntLb = new JLabel("Adult Number", JLabel.RIGHT);
		ntLb.setBounds(nx0, y0, w, h);
		nbrFd = new JTextField("1");
		nbrFd.setBounds(nx1, y0, w1, h);
		add(ntLb);
		add(nbrFd);
		y0=y0+h+off;
		JLabel chLb = new JLabel("Child Number", JLabel.RIGHT);
		chLb.setBounds(x0, y0, w, h);
		childFd = new JTextField();
		childFd.setBounds(x1, y0, w, h);
		add(chLb); add(childFd);
		JLabel ntLb1 = new JLabel("Number of Nights", JLabel.RIGHT);
		ntLb1.setBounds(nx0, y0, w, h);
		nightFd = new JTextField();
		nightFd.setEditable(false);
		nightFd.setBounds(nx1, y0, w1, h);		
	    add(ntLb1); add(nightFd);
	    
	    y0=y0+h+off;
	    JLabel mnbrLb = new JLabel("Number of Meals", JLabel.RIGHT);
		mnbrLb.setBounds(x0, y0, w, h);
		mealNbrFd = new JTextField("0");
		mealNbrFd.setBounds(x1, y0, w1, h);
		add(mnbrLb); 	add(mealNbrFd);
		JLabel mLb0 = new JLabel("Meal Price", JLabel.RIGHT);
		mLb0.setBounds(nx0, y0, w, h);
		permealFd = new JTextField("7.5");
		permealFd.setBounds(nx1, y0, w1, h);
		add(mLb0); 	add(permealFd);
	    y0 = y0+h+off;
	    JLabel mLb1 = new JLabel("Meal Infomation", JLabel.RIGHT);
		mLb1.setBounds(x0, y0, w, h);
		mealInfFd = new JTextField();
		mealInfFd.setBounds(x1, y0, w1+nx1-x1, h);
		add(mLb1); 	add(mealInfFd);
	    y0= y0+h+5;
	    JSeparator sp0 = new JSeparator();
		sp0.setBounds(2, y0, getWid()-5, 5);
		add(sp0);
		y0=y0+10;
	    //y0=y0+h+off;
	    JLabel payst = new JLabel("Total Room Fee", JLabel.RIGHT);
		payst.setBounds(x0, y0, w, h);
		totalFd = new JTextField("0.0");
		totalFd.setBounds(x1, y0, w1, h);
		add(payst);		add(totalFd);							
		JLabel addf = new JLabel("Additional Fee", JLabel.RIGHT);
		addf.setBounds(nx0, y0, w, h);
		addFd = new JTextField("0.0");
		addFd.setBounds(nx1, y0, w1, h);
		add(addf); add(addFd);
		y0=y0+h+off;
		JLabel tdLb = new JLabel("Total Discount", JLabel.RIGHT);
		tdLb.setBounds(x0, y0, w, h);
		totalDiscntFd = new JTextField("0.0");
		totalDiscntFd.setBounds(x1, y0, w1, h);	
		add(tdLb); add(totalDiscntFd);		
		
		JLabel addf1 = new JLabel("Additional Fee", JLabel.RIGHT);
		addf1.setBounds(nx0, y0, w, h);
		reasonBx = new JComboBox(reasons);
		reasonBx.setBounds(nx1, y0, w1, h);
		reasonBx.setEditable(true);
		reasonBx.setEnabled(true);		   
		add(addf1); add(reasonBx);		
		y0=y0+h+off;
		JLabel paidLb = new JLabel("Deposit", JLabel.RIGHT);
		paidLb.setBounds(x0, y0, w, h);
		paidFd = new JTextField("0.0");
		paidFd.setBounds(x1, y0, w1, h);
		add(paidLb); 	add(paidFd);		
		JLabel mfeeLb = new JLabel("Meal Fee", JLabel.RIGHT);
		mfeeLb.setBounds(nx0, y0, w, h);
		mealFeeFd = new JTextField("0.0");
		mealFeeFd.setBounds(nx1, y0, w1, h);
		add(mfeeLb);	add(mealFeeFd);	
		y0=y0+h+off;
		JLabel balLb = new JLabel("Balance", JLabel.RIGHT);
		balLb.setBounds(x0, y0, w, h);
		balFd = new JTextField("0.0");
		balFd.setBounds(x1, y0, w1, h);
		add(balLb);	add(balFd);
		JLabel arrv = new JLabel("Arrival Time", JLabel.RIGHT);
		arrv.setBounds(nx0, y0, w, h);
		arriveFd = new JTextField();
		arriveFd.setBounds(nx1, y0, w1, h);		
		add(arrv); add(arriveFd); 								
		y0=y0+h+off;
		JLabel eml = new JLabel("Pay Method", JLabel.RIGHT);
		eml.setBounds(x0, y0, w, h);	   
		payMtFd = new JComboBox(payMethes);
		payMtFd.setBounds(x1, y0, w1, h);
		add(eml);	add(payMtFd); 
		JLabel bsst = new JLabel("Book Status", JLabel.RIGHT);
		bsst.setBounds(nx0, y0, w, h);
		bookSttBx = new JComboBox(bookStt);
		bookSttBx.setSelectedIndex(0);
		bookSttBx.setBounds(nx1, y0, w1, h);
		add(bsst); add(bookSttBx);	
		
		y0=y0+h+off;
		int tbw = getWid();
		JSeparator sp1 = new JSeparator();
		sp1.setBounds(2, y0, tbw-5, 5);				
		y0=y0+off;
		JLabel noteLb = new JLabel("Booking Note", JLabel.CENTER);
		noteLb.setBounds(x0+3, y0,  tbw-20, h);
		y0=y0+h+off;
		int nth = 40;
		noteArea = new JTextArea();
		noteArea.setFont(new Font("SansSerif", Font.BOLD, 12));
		noteArea.setLineWrap(true);
		noteArea.setWrapStyleWord(true);
		JScrollPane scrTxt = new JScrollPane(noteArea);
		scrTxt.setBounds(x0+8, y0,  tbw-20, nth);
		add(sp1); add(noteLb); add(scrTxt);
		
		int y1 = getY1();
		calBtn = new JButton("Compute");
		calBtn.setBounds(180, y1, 90, h);
		add(calBtn);
		calBtn.addActionListener(this);
	}
	
	protected void computePayment(boolean isAll)
	{
		if(isAll)
		{
			double mf = toDouble(permealFd.getText()) * toInteger(mealNbrFd.getText());
			mealFeeFd.setText(Double.toString(mf));
		}
		double tt = toDouble(totalFd.getText())-toDouble(totalDiscntFd.getText())-
					toDouble(paidFd.getText())+toDouble(addFd.getText()) 
		            +toDouble(mealFeeFd.getText());
		
		try{tt = df.parse(df.format(tt)).doubleValue();
		  }catch(Exception ex){ex.printStackTrace(); }		  
		balFd.setText(Double.toString(tt));			
	}
	
	protected double toDouble(String s)
	{
		try 
		{  
			double pr = Double.parseDouble(s.trim());
			return pr;
		}catch(Exception ex) { return 0.0d; }		
	}
	
	protected int toInteger(String s)
	{
		try 
		{  
			int pr = Integer.parseInt(s.trim());
			return pr;
		}catch(Exception ex) { return 0; }		
	}
	
	public void backAction()
	{ //set as default
	    totalFd.setText("0.0");
		wdlg.books.clear();
		wdlg.custs.clear();		
	}
	public boolean nextAction()
	{
	   String grpNm = groupFd.getText();
	   if(grpNm == null || grpNm.length()<2)
	   {
		   wdlg.errorMsg("Group Name cannot be empty" +
		   "\nas Group booking");
		   return false;
	   }
	   double tt = toDouble(totalFd.getText())+toDouble(mealFeeFd.getText());
	   if(tt<1)
	   {
		  wdlg.errorMsg("Total cannot be empty, you have to compute or import");
		  return false;
	   }
	   computePayment(false);
	    tt = tt-toDouble(totalDiscntFd.getText())+toDouble(addFd.getText());
	  // BKWizardPane2 pan2 = (BKWizardPane2)wdlg.getWizardPane(2);
	   SLKCustomer cst = new SLKCustomer(0);
	   cst.setGroupName(groupFd.getText());
	   cst.setGroupAdultNbr(nbrFd.getText());
	   cst.setGroupChildNbr(childFd.getText());
	   cst.setRoomFee(totalFd.getText());
	   cst.setDeposite(paidFd.getText());
	   cst.setBalance(balFd.getText());
	   cst.setMealNbr(mealNbrFd.getText());
	   cst.setMealPrice(permealFd.getText());
	   cst.setMealFee(mealFeeFd.getText());
	   cst.setDiscount(totalDiscntFd.getText());
	   cst.setAddFee(addFd.getText());
	   cst.setMealNbr(mealNbrFd.getText());
	   cst.setMealFee(mealFeeFd.getText());
	   cst.setPayMethod(payMtFd.getSelectedItem().toString());
	   cst.setEndAmount(Double.toString(tt));
	   cst.setArriveTime(arriveFd.getText());
	   cst.setDietRequirement(mealInfFd.getText());
	   StringBuffer sf1 = new StringBuffer();
	   sf1.append("Rooms: ");	   
	   for(int i=0; i<wdlg.books.size(); i++)
	   {		  
		  BookDetail bk = wdlg.books.get(i);
		  bk.setBookStatus(bookSttBx.getSelectedItem().toString());
		  bk.setCustId(cst.getCode());
		  bk.setAdditionFeeCause(reasonBx.getSelectedItem().toString());
		  cst.addBookId(bk.getCode());
		  sf1.append(bk.getRoomName()+", ");
	   }
	   cst.setNote(noteArea.getText());
	   wdlg.addCust(cst);
	   return true;
	}
	
	public void actionPerformed(ActionEvent av)
	{
		super.actionPerformed(av);
		String cmd = av.getActionCommand();
   		if(cmd.equals("Compute"))
   			computePayment(true);
	}
	
	
}
