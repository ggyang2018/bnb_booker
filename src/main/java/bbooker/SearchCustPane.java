package bbooker;

import guiwidget.XYLayout;

import javax.swing.*;
import java.awt.event.*;	
import java.util.Vector;
import javax.swing.event.*;
import javax.swing.text.*;

import minihelp.*;
 
 public class SearchCustPane  extends IndexContent
 {
	 static public final long  serialVersionUID = SearchList.serialVersionUID;
	 //working component
     private JTextField TypeFd = null;
	 private SearchList ChoseLst = null;
	 //private JScrollPane ListPane = null;
	 private JButton DisplayBtn = null;
	 private JButton CancelBtn  = null;
	 JScrollPane ListPane;
	 //inner data holder
	 private Vector<String> data;
	 private SearchCustDlg dlgx; 
	 	
	 public SearchCustPane(SearchCustDlg dg)
	 {
		super(dg);
		dlgx = dg;
	 }

	 
	 public void setData(Vector<String> dat)
	 { data = dat;}
	
	 
	 public void initPanel( )
	 {
		 setLayout(new XYLayout());
		 int x0=10, y0=5, w0=120, h=20, off=5;
		 int x1 = w0+x0+off, w1=200;
		 JLabel lb2= new JLabel("Type Surname:", JLabel.RIGHT);
		 lb2.setBounds(x0, y0, w0, h);    
		 setTypeFdProperty( );
		 TypeFd.setBounds(x1, y0, w1, h);
		 add(lb2); add(TypeFd);

		 if(data.size()<=1)
			 data.addElement("Empty");
		 y0 = y0+h+off;
		 int h1 = 250;
		 ChoseLst = new SearchList(data);
		 ChoseLst.setSearchCtn(this);
		 ListPane = new JScrollPane(ChoseLst);
		 ListPane.setBounds(x0, y0, w1+x1-x0, h1);
		 add(ListPane);
		 
		 y0 = y0+h1+10;
         DisplayBtn = new JButton("Go");
	     CancelBtn  = new JButton("Close");
         DisplayBtn.setBounds(x0+20, y0, 80, h);
	     CancelBtn.setBounds(x1, y0, 80, h);
		 add(DisplayBtn);
		 add(CancelBtn);

		 DisplayBtn.addActionListener(this);
		 CancelBtn.addActionListener(this);

		 TypeFd.grabFocus( );
		 //TypeFd.requestFocusInWindow();
	 }

	 protected void makeVector(String s)
	 {
		 Vector<String> v = new Vector<String>( );
		 for(int i=0; i<data.size(); i++)
		 {
			 String s1 =data.elementAt(i);
			 if(s1.compareToIgnoreCase(s)>=0)
			   v.addElement(s1);
		 }
		 refreshList(v);
	 }


	public void  refreshList(Vector<String> data)
    {
       ChoseLst = new SearchList(data);
       ListPane.getViewport().add(ChoseLst); 
       
    }

	protected void setTypeFdProperty( )
	{
		TypeFd = new JTextField( );
		 TypeFd.registerKeyboardAction(this, "typekey", 
			 KeyStroke.getKeyStroke(KeyEvent.KEY_TYPED, 0,true), 
	         JTextField.WHEN_FOCUSED);
		 //TypeFd.setRequestFocusEnabled(true);
		 TypeFd.requestFocus();
		 
		 //TypeFd.addActionListener(this);
		 TypeFd.addActionListener( new ActionListener( )
		 {
			 public void actionPerformed(ActionEvent ae)
			 {
				 String com = ae.getActionCommand();
				 System.out.println(com);
				 String s = TypeFd.getText().trim();
				 makeVector(s);
			 }
		 });
		 
		 Document document = TypeFd.getDocument( );
		 document.addDocumentListener(new TxtFieldChange( ));
	}
	
	public void actionPerformed(ActionEvent ae)
	{
	    String com = ae.getActionCommand();
	    if(com.equals("Go"))
			display( );
		else if(com.equals("Close"))
		{
			dlgx.dispose();
			dlgx.setVisible(false);
		}else
		{
			dlgx.dispose();
			dlgx.setVisible(false);
		}
	}					

	public void display( )
	{
		String chs = ChoseLst.getSelectedObj().toString();
		String cd =  dlgx.descs.get(chs);
		SLKCustomer cs = (SLKCustomer)dlgx.wdlg.frm.bkBus.getCustMap().find(cd);
		if(cs !=null)
			dlgx.workPane.setPaneData(cs, true);
		dlgx.dispose();
		dlgx.setVisible(false);
	}

		 //inner class
	class TxtFieldChange implements DocumentListener
	{
	   public void insertUpdate(DocumentEvent event)
	   {
		  String s1 = TypeFd.getText( ).trim();
		  makeVector(s1);
	   }

	   public  void removeUpdate(DocumentEvent event)
	   {
	   }

	   public void changedUpdate(DocumentEvent event)
	   {
	   }
	}
 }
