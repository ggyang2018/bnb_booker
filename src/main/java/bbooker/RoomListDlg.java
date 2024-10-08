package bbooker;

/* Bespoke BookSheet diaglog
 * @Author Guang Yang
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import javax.swing.*;

import slkcalendar.*;
import guiwidget.*;

public class RoomListDlg extends JDialog implements ActionListener, ActAdapt
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	BKFrame frm;
	
	JTextField titFd;
	SSTablePane reportPane;
	JButton pdfBtn, csvBtn, cancelBtn;
	
	//JButton startBtn, endBtn, freshBtn;
	//JTextField startFd, endFd;
	
	String heads[] = {"Room Name", "Room Type", "Bathroom", "Description", "Default Price"};
	int wids[] = {200, 200, 200, 200, 200};
	int monthx, yearx;
	//initial data
	String data0[][];
	int dataSz=0;
	//working data
	List<String> bidList;
	String wkData[][];
	public RoomListDlg(BKFrame frm, String title)
	{
		super(frm);
		this.frm = frm;
		setModal(true);
		setTitle(title);
		setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{     dispose(); setVisible(false);}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = new Double(dim.getWidth());
		        Double h = new Double(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		getContentPane().setLayout(new XYLayout());
	}
	public void setCurrentMonthYear(int mt, int yr) { monthx=mt; yearx=yr;}
	
	public void createData(boolean debug)
	{
		bidList = new ArrayList<String>();
		frm.bkBus.updateBookStrs();
		List<String> lst = frm.bkBus.getRoomMap().getCodes();
		data0 = new String[lst.size()][heads.length];
		dataSz = lst.size();
		for(int i=0; i<dataSz; i++)
		{
		   int cnt=0;
		  try
		  {
			String key = lst.get(i);
			bidList.add(key);
		    RoomData rm1 =(RoomData)frm.bkBus.getRoomMap().find(key);
			data0[i][cnt] = rm1.getName(); cnt++;
			data0[i][cnt] = rm1.getRoomTypeDesc(); cnt++;
			String typ = rm1.getRoomTypeId();
			TypeDefData tyd = (TypeDefData)frm.bkBus.getTypeMap().find(typ);
			data0[i][cnt] = tyd.getEnsuit(); cnt++;		
			data0[i][cnt] = tyd.getDesc(); cnt++;
			data0[i][cnt] = tyd.getRefPrice(); cnt++;
		  }catch(Exception ex) 
		  {
			  cnt--;
			  for(int j= cnt; j<heads.length; j++)
				  data0[i][j] = new String(" ");
			  ex.printStackTrace();
		  }
		}
	}
	//700x500
	public void initDlg() 
	{
	   int h0 = 5, off = 5, h1=20, h2=400;
	   int x0=5, w1=80;
	   JLabel titLb = new JLabel("Title:", JLabel.RIGHT);
	   titLb.setBounds(x0, h0, w1, h1);
	   titFd = new JTextField();
	   titFd.setBounds(w1+x0+off, h0, 340, h1);
	   //bespoke
	   	   	   	   
	   h0 = h0+off+h1;
	   createData(false);
	   //reportPane = new SSTablePane(data0, heads);
	   reportPane = new SSTablePane(data0, heads); //if false
	   reportPane.setBounds(x0, h0, 870, h2);
	   reportPane.setColumnWidths(wids);
	   reportPane.setPane(false);
	   
	   h0 = h0+h2+2*off;
	   int x1 = 100, w2=120;
	   pdfBtn = new JButton("Export PDF"); 
	   pdfBtn.setBounds(x1, h0, w2, h1);
	   
	   x1 = x1+w2+2*off;
	   csvBtn = new JButton("Export Excel");
	   csvBtn.setBounds(x1, h0, w2, h1);
	   
	   x1 = x1+w2+2*off;
	   cancelBtn = new JButton("Close");
	   cancelBtn.setBounds(x1, h0, w2, h1);
	   
	   getContentPane().add(titLb);
	   getContentPane().add(titFd);
	   
	   getContentPane().add(reportPane);
	   //getContentPane().add(pdfBtn);
	   getContentPane().add(csvBtn);
	   getContentPane().add(cancelBtn);
	   
	  // pdfBtn.addActionListener(this); 
	   csvBtn.addActionListener(this);
	   cancelBtn.addActionListener(this);
	}
	
	public void setFavourBounds(int width, int height)
	{
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}

	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("Close"))
		{
			dispose();
			setVisible(false);
		}else if(cmd.equals("Export Excel"))
		{
			String fnm = "BookSheet"+idFmt.format(new Date())+".csv";
			exportCSV(fnm);
		}
	}
	
	public void exportCSV( String fnm)
    {
	   try
	   {
		  JFileChooser chooser = new JFileChooser(); 
		  chooser.setCurrentDirectory(new File("."));
		  chooser.setDialogTitle("Export Excel Dialog");
		  chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  // disable the "All files" option.
		  chooser.setAcceptAllFileFilterUsed(false);
		  if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		  { 
		        File dir= chooser.getCurrentDirectory();
		        String pth = dir.getAbsolutePath();
		        pth = pth+fnm;
		        FileWriter wrt = new FileWriter(fnm);
		        writeCSV(wrt);
	    		wrt.close();
		   }
	   }catch(Exception ex){ex.printStackTrace(); }
    }
	
	protected void writeCSV(FileWriter wrt)
	{
	   try
	   {
		  for(int c=0; c<heads.length; c++)
		  {
			 if(c<heads.length-1)
				 wrt.append(heads[c]+", ");
			 else
				 wrt.append(heads[c]);
		  }
		  wrt.append("\n");
	   }catch(Exception ex1) {}
			
	   for(int i=0; i<bidList.size(); i++)
	   {
		  try
		  {
			String key = bidList.get(i);
			RoomData rm1 =(RoomData)frm.bkBus.getRoomMap().find(key);
			wrt.append(rm1.getName()+", ");
			wrt.append(rm1.getRoomTypeDesc()+", ");
			String typ = rm1.getRoomTypeId();
			TypeDefData tyd = (TypeDefData)frm.bkBus.getTypeMap().find(typ);
			wrt.append(tyd.getEnsuit()+", ");		
			wrt.append(tyd.getDesc()+", ");
			wrt.append(tyd.getRefPrice());						
			wrt.append("\n");
			wrt.flush();	        
		  }catch(Exception ex) {}
		}		
	}
			
	protected String formatChange(String dts, boolean id_dt)
	{
	   String rt = null;	
	   try
	   {
		  if(id_dt)
		  {
			 Date dt = idFmt.parse(dts);
		     rt = dateFmt.format(dt);	 
		  }else
		  {
			  Date dt1 = dateFmt.parse(dts);
			  rt = idFmt.format(dt1);
		  }
	   }catch(Exception ex) 
	   {System.out.println("ReportDlg317-err: "+ex.toString()); rt=null;}
	   return rt;
	}
	//implementation adapt
	public void setMonth(int m){}
	public void setYear(int y){}
	public void setDateAct(int row, int fld, JButton cellBtn){}
	public void setSelectDate(Date dt, int mode){}	
	public List<String> getNames() { return null;}
}
