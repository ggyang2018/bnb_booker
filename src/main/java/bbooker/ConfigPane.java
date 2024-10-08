package bbooker;
/* Configuration Panel to configure objec with type
 * @Author Guang Yang
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import guiwidget.GList;
import guiwidget.GUIWidgetAdaptor;
import guiwidget.XYLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ConfigPane extends JPanel implements ActionListener,ListSelectionListener 
{
   static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
   JComboBox typeBx;
   JTextArea typeInfo;
   GList typeList;
   JTextField nmFd, priceFd;  
   JButton addBtn, deleteBtn, saveBtn, quitBtn;
   ConfigDlg dlg;
   BookBus dbus;  
   boolean isSaved, sortedFg;
   
   public ConfigPane(ConfigDlg dlg)
   {
	   this.dlg = dlg;
	   isSaved = true;
	   sortedFg = true;
   }
   public void setDataBus(BookBus bus){ dbus = bus; }
   public void initPane()//465 * y
   {
	   setLayout(new XYLayout());
	   int x0=5, y0=3, w0=220, w1=80, w2=140;
	   int h=20, h1= 230, off=3;
	   JLabel tit = new JLabel("Room List Panel", JLabel.CENTER);
	   tit.setBounds(x0, y0, w0, h);
	   add(tit);
	   y0=y0+h+off;
	   typeList = new GList();
	   loadListData();
	   JScrollPane sro = new JScrollPane(typeList);
	   sro.setBounds(x0, y0, w0, h1);
	   add(sro);
	   y0=3;
	   x0 = x0+w0+5; 
	   int x1 = x0+w1+off;
	   JLabel tpLb = new JLabel("Room Type:", JLabel.RIGHT);
	   tpLb.setBounds(x0, y0, w1, h);
	   typeBx = new JComboBox(loadTypes());
	   typeBx.setActionCommand("RoomID");
	   typeBx.setSelectedIndex(0);
	   TypeDefData tdd0 = (TypeDefData)dbus.getTypeMap().find(0);	   	   
	   typeBx.setBounds(x1, y0, w2, h);
	   add(tpLb);
	   add(typeBx);
	   y0=y0+h+off;
	   int th=120;
	   typeInfo = new JTextArea();
	   typeInfo.setLineWrap(true);
	   typeInfo.setWrapStyleWord(true);
	   JScrollPane scrTxt = new JScrollPane(typeInfo);
	   scrTxt.setBounds(x0, y0, w1+w2+5, th);
	   add(scrTxt);
	   
	   if(tdd0 !=null)
		   typeInfo.setText(tdd0.toString());
	   
	   DocumentFilter filter = new UppercaseDocumentFilter();
	   y0=y0+th+off;
	   int wx1=40;
	   JLabel tvLb = new JLabel("Room:", JLabel.RIGHT);
	   tvLb.setBounds(x0, y0, wx1, h);
	   nmFd = new JTextField();
	   ((AbstractDocument)nmFd.getDocument()).setDocumentFilter(filter); 
	   nmFd.setBounds(x0+wx1+off, y0, w2+40, h);
	   add(tvLb);
	   add(nmFd);
	   y0=y0+h+off;
	   JLabel prLb = new JLabel("Price:", JLabel.RIGHT);
	   prLb.setBounds(x0, y0, wx1, h);
	  // xx1 = xx1+wx1+off;
	   priceFd = new JTextField("25.55");
	   priceFd.setBounds(x0+wx1+off, y0, wx1, h);
	   add(prLb);
	   add(priceFd);
	   
	   y0=y0+h+2*off;
	   JSeparator sp1 = new JSeparator();
	   sp1.setBounds(x0-10, y0, w1+w2+20, 5);
	   add(sp1);
	   y0=y0+5+2*off;
	   int bw = 80;
	   x1 = x1+20;
	   addBtn = new JButton("Add");
	   deleteBtn = new JButton("Delete");
	   addBtn.setBounds(x0+10, y0, bw, h);
	   deleteBtn.setBounds(x1, y0, bw, h);
	   y0=y0+h+off;
	   saveBtn = new JButton("Save");
	   quitBtn = new JButton("Quit");
	   saveBtn.setBounds(x0+10, y0, bw, h);
	   quitBtn.setBounds(x1, y0, bw, h);
	   add(addBtn); add(deleteBtn);
	   add(saveBtn); add(quitBtn);
	   
	   addBtn.addActionListener(this);
	   deleteBtn.addActionListener(this);
	   saveBtn.addActionListener(this);
	   quitBtn.addActionListener(this);
	   typeBx.addActionListener(this);
	   typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	    
	   typeList.addListSelectionListener(this);	
	   saveBtn.setEnabled(false);
   }
   
   public void actionPerformed(ActionEvent av)
   {
	  String cmd = av.getActionCommand();
	  if(cmd.equals("Add")) addRoom();
	  else if(cmd.equals("Delete"))
	  {
		 int idx = typeList.getSelectedIndex();
		 if(idx !=-1)
		 {
			 String rd = typeList.rmItem(idx);
			 RoomData rm = (RoomData)dbus.getRoomMap().findByName(rd);
		 	 if(rm !=null)
		 	 {
		 		 dbus.getRoomMap().rm(rm.getCode());
		 	     setStage();
		 	     nmFd.setText("");
		 	     priceFd.setText("");
		 	 }
		 }
		
	  }else if(cmd.equals("Save"))
	  {
		  typeList.sortItems();
		  dbus.getRoomMap().sortByNames();
		  dbus.getRoomMap().setSavedFg(false);
		  dbus.saveRoomMap();
		  isSaved = true;
		  saveBtn.setEnabled(false);
	  }else if(cmd.equals("Quit"))
	  {
		 if(!isSaved)
		 {
			 String msg2="You have't save the changes you made." +
				"\nDo you want save it";
			 if(!confirmMsg(msg2)) return;		
		 }
		 dlg.dispose();
		 dlg.setVisible(false);
	  }else if(cmd.equals("RoomID"))
	  {
		 TypeDefData tdd1 = getTypeData();
		 if(tdd1 !=null)
		 {
			 typeInfo.setText(tdd1.toString());
			 RoomData rmd = getRoomData();
			 if(rmd !=null)
				 rmd.setRoomTypeId(tdd1.getCode());
		 }
	  }		
   }
   
   public void valueChanged(ListSelectionEvent e) 
   {
	   RoomData td = getRoomData();
	   if(td !=null)
	   {
		   fillPane(td, true);
		   saveBtn.setEnabled(true);
	   }	   
   }
   
   protected void loadListData()
   {
	   List<String> nms =  dbus.getRoomMap().getNames();
	   for(int i=0; i<nms.size(); i++)
	   	   typeList.addItem(nms.get(i));
   }
   
   protected String[] loadTypes()
   {
	   int sz = dbus.getTypeMap().getNames().size();
	   String ss[]  = new String[sz];
	   boolean rmFg = false;
	   int j=0;
	   for(int i=0; i<sz; i++)
	   {
		   String s1 = dbus.getTypeMap().getNames().get(i);
		   if(s1 !=null)
		   {
			   if(s1.trim().length()>1)
			   {
				   ss[j] =    dbus.getTypeMap().getNames().get(i);
				   j++;
			   }else
			   {
				   rmFg = true;
				   dbus.getTypeMap().rm(s1);
			   }
		   }		   
	   }	   
	   if(rmFg) dbus.saveTypeMap();
	   return ss;	  
   }
   
   protected void fillPane(RoomData rd, boolean toPane)
   {
	 if(rd == null) return;
	 if(toPane)
	 {
	   nmFd.setText(rd.getName());
	   String tid = rd.getRoomTypeId();
	   TypeDefData currentType = (TypeDefData)dbus.getTypeMap().find(tid);
	   typeInfo.setText(currentType.toString());
	   priceFd.setText(rd.getPrice().toString());   
	   typeBx.setSelectedItem(currentType.getName());	  
	 }else
	 {
		 rd.setName(nmFd.getText());
		 TypeDefData currentType = getTypeData();
		 if(currentType != null)
		 {
			 rd.setRoomTypeId(currentType.getCode());
			 rd.setRoomTypeDesc(currentType.getName());
		 }
		 Double db = new Double(25.25);
		 try
		 { db = Double.parseDouble(priceFd.getText());
		 }catch(Exception ex) { db = new Double(0.0); }
		 rd.setPrice(db);		 		 
	 }
   }
   
   
   void addRoom()
   {
	   String tp0 = nmFd.getText();
	   if(tp0 == null || tp0.trim().length()<2)
	   {
		   errorMsg("Room name cannot be empty.");
		   return;
	   }
	   tp0 = tp0.trim();
	   if(typeList.indexOfItem(tp0) >=0)
	   {
		   errorMsg("Room name already exists");
		   return;
	   }
	   int idx = dbus.getRoomMap().getLastNbr();
	   RoomData dt = new RoomData(idx);
	   dt.setCode(nmFd.getText());
	   dt.setName(nmFd.getText());
	   TypeDefData tdd1 = getTypeData();
	   dt.setRoomTypeId(tdd1.getCode());
	   dt.setRoomTypeDesc(tdd1.getName());
	   Double db = new Double(25.25);
	   try
	   { db = Double.parseDouble(priceFd.getText());
	   }catch(Exception ex) { db = new Double(0.0); }
	   dt.setPrice(db);
	   typeList.addItem(dt.getName());
	   dbus.getRoomMap().add(dt);	  
       setStage();
       sortedFg = false;
   }
   
   void setStage()
   {
	   isSaved = false;
	   saveBtn.setEnabled(true);
   }
   
   
   TypeDefData getTypeData()
   {
	   String nm = typeBx.getSelectedItem().toString();
	   if(nm == null || nm.trim().length()<1) return null;
	   TypeDefData tdd = (TypeDefData)dbus.getTypeMap().findByName(nm);
	   return tdd;
   }
   
   RoomData getRoomData()
   {
	   int idx = typeList.getSelectedIndex();
	   if(idx <0) return null;
	   String itm = typeList.getItem(idx);
	   RoomData tdd = (RoomData)dbus.getRoomMap().findByName(itm);
	   return tdd;
   }
   
   void errorMsg(String err)
   {
	   String msg1 = err+"\nPlease make changes or quit!";
	   JOptionPane.showMessageDialog(this, msg1, "alert", 
			   JOptionPane.ERROR_MESSAGE);
   }
   
   boolean confirmMsg(String msg)
   {	  
	   int reply = JOptionPane.showConfirmDialog(this, msg, "Confirmation", 
			JOptionPane.YES_NO_OPTION);
	   return (reply == JOptionPane.YES_OPTION);
   }
   
   class UppercaseDocumentFilter extends DocumentFilter 
   { 
	   // Override insertString method of DocumentFilter to make the text format 
	   // to uppercase. 
	   // 
	   public void insertString(FilterBypass fb, int offset,
                                String text, AttributeSet attr) throws BadLocationException
	   {  fb.insertString(offset, text.toUpperCase(), attr); } 
	   // Override replace method of DocumentFilter to make the text format 
	   // to uppercase. 
	   public void replace(FilterBypass fb, int offset, int length,
                           String text, AttributeSet attrs) throws BadLocationException
	   { fb.replace(offset, length, text.toUpperCase(), attrs); } 
   } 
  
}

