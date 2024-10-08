package bbooker;	
/*Define the type in respond to selection for List
 * Data should be independ and read only for booker
 * It should be two layers of operation
 * @Author Guang Yang
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import guiwidget.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.*;

public class TypeDefPane extends JPanel implements ActionListener,ListSelectionListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	JTextField typeFd, bedFd, tvFd, showFd, teaFd, equipFd, otherFd, price0Fd;
	GList typeList;
	JButton addBtn, deleteBtn, saveBtn, quitBtn;
	TypeDefDlg dlg;
	BookBus dbus;
	boolean savedFg;
	int lastIdx;
	
	
	public TypeDefPane(TypeDefDlg dlg)
	{ 
		this.dlg = dlg;
		lastIdx = -1;
	}
	
	public void setDataBus(BookBus bus){ dbus = bus; }
	
	public void initPane()//465 * y
	{
	   savedFg = true;
	   setLayout(new XYLayout());
	   int x0=5, y0=3, w0=220, w1=100, w2=130;
	   int h=20, h1= 230, off=3;
	   JLabel tit = new JLabel("Type List Panel", JLabel.CENTER);
	   tit.setBounds(x0, y0, w0, h);
	   add(tit);
	   y0=y0+h+off;
	  // typeMap = new HashMap<String, TypeDefData>();
	   typeList = new GList();
	   loadListData();
	   JScrollPane sro = new JScrollPane(typeList);
	   sro.setBounds(x0, y0, w0, h1);
	   add(sro);
	   y0=3;
	   x0 = x0+w0; 
	   int x1 = x0+w1+off;
	   JLabel tpLb = new JLabel("Type Name:", JLabel.RIGHT);
	   tpLb.setBounds(x0, y0, w1, h);
	   typeFd = new JTextField();
	   typeFd.setBounds(x1, y0, w2, h);
	   add(tpLb);
	   add(typeFd);
	   y0=y0+h+off;
	   JLabel bdLb = new JLabel("Bed(s):", JLabel.RIGHT);
	   bdLb.setBounds(x0, y0, w1, h);
	   bedFd = new JTextField();
	   bedFd.setBounds(x1, y0, w2, h);
	   add(bdLb);
	   add(bedFd);
	   y0=y0+h+off;
	   JLabel tvLb = new JLabel("TV/Movie:", JLabel.RIGHT);
	   tvLb.setBounds(x0, y0, w1, h);
	   tvFd = new JTextField();
	   tvFd.setBounds(x1, y0, w2, h);
	   add(tvLb);
	   add(tvFd);
	   y0=y0+h+off;
	   JLabel showLb = new JLabel("Bathroom:", JLabel.RIGHT);
	   showLb.setBounds(x0, y0, w1, h);
	   showFd = new JTextField();
	   showFd.setBounds(x1, y0, w2, h);
	   add(showLb);
	   add(showFd);
	   y0=y0+h+off;
	   JLabel teaLb = new JLabel("Tee/Coffe Maker", JLabel.RIGHT);
	   teaLb.setBounds(x0, y0, w1, h);
	   teaFd = new JTextField();
	   teaFd.setBounds(x1, y0, w2, h);
	   add(teaLb);
	   add(teaFd);
	   y0=y0+h+off;
	   JLabel eqLb = new JLabel("Equipments:", JLabel.RIGHT);
	   eqLb.setBounds(x0, y0, w1, h);
	   equipFd = new JTextField();
	   equipFd.setBounds(x1, y0, w2, h);
	   add(eqLb);
	   add(equipFd);
	   y0=y0+h+off;
	   JLabel otLb = new JLabel("Description:", JLabel.RIGHT);
	   otLb.setBounds(x0, y0, w1, h);
	   otherFd = new JTextField();
	   otherFd.setBounds(x1, y0, w2, h);
	   add(otLb);
	   add(otherFd);
	   y0=y0+h+off;
	   JLabel prLb = new JLabel("Reference Price:", JLabel.RIGHT);
	   prLb.setBounds(x0, y0, w1, h);
	   price0Fd = new JTextField();
	   price0Fd.setBounds(x1, y0, w2, h);
	   add(prLb);
	   add(price0Fd);
	   y0=y0+h+2*off;
	   JSeparator sp1 = new JSeparator();
	   sp1.setBounds(x0, y0, w1+w2+10, 5);
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
	   typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	    
	   typeList.addListSelectionListener(this);	
	   saveBtn.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("Add"))addType();
		else if(cmd.equals("Delete"))
		{
			int idx = typeList.getSelectedIndex();
			typeList.rmItem(idx);
			dbus.getTypeMap().rm(idx);
			setStage();			
		}else if(cmd.equals("Save"))
		{
			if(savedFg) return;
			//still need pack up change
			int idx = typeList.getSelectedIndex();
			if(idx !=-1)
			{
			   String itm = typeList.getItem(idx);
			   TypeDefData tdd = (TypeDefData)dbus.getTypeMap().findByName(itm);
			   fillPane(tdd, false);
			}	
			dbus.getTypeMap().setSavedFg(savedFg);
			dbus.saveTypeMap();
			setStage();
			savedFg = true;
			saveBtn.setEnabled(false);
		}else if(cmd.equals("Quit"))
		{
			if(!savedFg)
			{
				String msg = "You have not saved your changes. " +
						"\n Do you want to quit? ";
				if(!confirmMsg(msg)) return;
			}
			dlg.dispose();
			dlg.setVisible(false);
		}
	}

   protected void loadListData()
   {
	   List<String> nms = dbus.getTypeMap().getNames();
	   for(int i=0; i<nms.size(); i++)
	   {
		   String nm = nms.get(i);
	   	   typeList.addItem(nm);	   	  
	   }
   }
   
   public void valueChanged(ListSelectionEvent e) 
   {
	   typeFd.setEditable(false);
	   if(lastIdx !=-1)
	   {
		   TypeDefData td = (TypeDefData)dbus.getTypeMap().find(lastIdx);
		   fillPane(td, false);
		   savedFg = false;
	   }	   	   
	   int idx = typeList.getSelectedIndex();
	   if (idx != -1)
	   {
		   TypeDefData td = (TypeDefData)dbus.getTypeMap().find(idx);
		   fillPane(td, true);
		   savedFg = false;
		   saveBtn.setEnabled(true);
	   }
	   lastIdx = idx;
   }
   
   protected void fillPane(TypeDefData dt, boolean toPane)
   {
	  if(toPane)
	  {
		  typeFd.setText(dt.getName());
		  tvFd.setText(dt.getTV()); 
		  bedFd.setText(dt.getBeds());
		  showFd.setText(dt.getEnsuit());
		  teaFd.setText(dt.getTeaMk());
		  equipFd.setText(dt.getEquip());
		  otherFd.setText(dt.getDesc());
		  price0Fd.setText(dt.getRefPrice());
	  }else
	  {
		  dt.setName(typeFd.getText());
		  dt.setTV(tvFd.getText()); 
		  dt.setBeds(bedFd.getText());
		  dt.setEnsuit(showFd.getText());
		  dt.setTeaMk(teaFd.getText());
		  dt.setEquip(equipFd.getText());
		  dt.setDesc(otherFd.getText());
		  dt.setRefPrice(price0Fd.getText());		  
	  }
   }
   
   protected void emptyPane()
   {
	   typeFd.setText("");
	   tvFd.setText(""); 
	   bedFd.setText("");
	   showFd.setText("");
	   teaFd.setText("");
	   equipFd.setText("");
	   otherFd.setText("");
	   price0Fd.setText("");
   }
   
   protected void addType()
   {
	  String tp0 = typeFd.getText();
	  if(tp0 == null || tp0.trim().length()<2)
	  {
		  errorMsg("Type name cannot be empty.");
		  return;
	  }
	  tp0 = tp0.trim();
	  if(typeList.indexOfItem(tp0) >=0)
	  {
		  errorMsg("Type name already exists");
		  return;
	  }		  
	  int idx = dbus.getTypeMap().getLastNbr();
	  TypeDefData dt = new TypeDefData(idx);
	  dt.setName(typeFd.getText());
	  dt.setTV(tvFd.getText()); 
	  dt.setBeds(bedFd.getText());
	  dt.setEnsuit(showFd.getText());
	  dt.setTeaMk(teaFd.getText());
	  dt.setEquip(equipFd.getText());
	  dt.setDesc(otherFd.getText());
	  dt.setRefPrice(price0Fd.getText());
	  typeList.addItem(dt.getName());
	  dbus.getTypeMap().add(dt);
	  setStage();
   }
   
   void setStage()
   {
	   typeList.clearSelection();
	   lastIdx = -1;
	   savedFg = false;
	   saveBtn.setEnabled(true);
	   emptyPane();
	   typeFd.setEditable(true);
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
 
   
}