/* *******************************************
 IndexContent analogue the window index help to find information.
 It is panel object to contain other components
 Author GY
 Date 15-1-2003
 ********************************************************** */
package minihelp;

import guiwidget.XYLayout;

import javax.swing.*;
 import java.awt.event.*;
 import java.util.Vector;
 import java.util.HashMap;
 import javax.swing.event.*;
 import javax.swing.text.*;
import java.awt.*;
 
 public class IndexContent extends JPanel implements ActionListener
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
	 private HashMap<String, String> Contxt;
	 private Vector<String> data;
	 private int Wid, Hei;
	 private HelpDlg dlg;
	 private JDialog dlgx; 
	 
	
	 public IndexContent(JDialog dg)
	 {
		 Contxt = new HashMap<String, String>();
		 data = new Vector<String>();
		 dlgx = dg;
	 }
	 
	 public void setData(Vector<String> dat, HashMap<String, String> ctx)
	 { data = dat; Contxt = ctx; }
	 
	 
	 public IndexContent(HelpDlg dlg)//HelperHTML hts[])
	 {
		 //MLFiles = hts;
		 Contxt = new HashMap<String, String>();
		 data = new Vector<String>();
		 this.dlg = dlg;
		 Wid = dlg.getSize().width;
         Hei = dlg.getSize().height;
		 setData( );
		 initPanel( );
	 }
	 
	 public void initPanel(int wid1, int hgt1)
	 {
		 Wid = wid1; Hei = hgt1;
		 initPanel();
	 }


	 public void initPanel( )
	 {
		 setLayout(new XYLayout());		 
		 String lbstr2="Type in the keyword to find:";
		 JLabel lb2= new JLabel(lbstr2, JLabel.LEFT);
		 lb2.setFont(new Font("Dialog", Font.BOLD, 11));
		 lb2.setForeground(Color.black);
		 lb2.setBounds(10, 5, Wid-30, 15);
		 add(lb2);
          
		 setTypeFdProperty( );
		 TypeFd.setBounds(7, 25, Wid-20, 20);
		 add(TypeFd);

		 if(data.size()<=1)
			 data.addElement("Empty");
		 
		 ChoseLst = new SearchList(data);
		 ChoseLst.setSearchCtn(this);
		 ListPane = new JScrollPane(ChoseLst);
		 ListPane.setBounds(7, 50, Wid-20, Hei-120);
		 add(ListPane);
         DisplayBtn = new JButton("Display");
	     CancelBtn  = new JButton("Close");
         DisplayBtn.setBounds(Wid-190, Hei-60, 80, 23);
	     CancelBtn.setBounds(Wid-100, Hei-60, 80, 23);
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
	    if(com.equals("Display"))
			display( );
		else if(com.equals("Close"))
		{
			if(dlg !=null)
			{
				dlg.dispose();
				dlg.setVisible(false);
			}else
			{
				dlgx.dispose();
				dlgx.setVisible(false);
			}
		}					
	}

	public void display( )
	{
		String chs = ChoseLst.getSelectedObj().toString();
		String str = (String)Contxt.get(chs);
		if(dlg !=null)
			dlg.Display.setPage(str);
	}

	public void setData( )
	{
	   HTMLProcessor prc = null;
	   try
	   {
		  prc = new HTMLProcessor(dlg.HTMLName);
		  prc.scanFile();
	   }catch(Exception ex)
	   { System.out.println("Error:"+ex.toString()); } //do nothing
        
	   if(prc == null)
		   return;

	   HashMap<String, String> mp = prc.getContext( );
	   if(mp != null && mp.size() >0)
	   {
	       Contxt.putAll(mp);
	 	   data.addAll(prc.getKeyVector());
	   }	 
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
