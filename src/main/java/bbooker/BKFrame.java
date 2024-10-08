package bbooker;
/*Booker Frame extends GFrame with many staff
 * @Author Guang Yang
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import guiwidget.*;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.filechooser.FileFilter;

import slkcalendar.DateTable;
import xhtml.HTMLPane;

public class BKFrame extends JFrame implements GUIWidgetAdaptor
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	protected DBDataBusImp bus;
	BookBus bkBus;
	int frmWd = 980;
	int frmHgt = 700;
	DateTable mainPane;
	String hd[] = {"Room1","Room2", "Room3", "Room4", "Room5", "Room6" };
    String dt[][] = { {"provisional", "provisional"}, {"taken", "taken"}};
    Toolkit kit;
    PrintRequestAttributeSet attrs;
    PageFormat pageFormat;
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	
	public BKFrame(String title)
	{
		super(title);
		bus = new DBDataBusImp();	
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		kit = Toolkit.getDefaultToolkit();
		//setIconImage(kit.getImage("slkdata\\sslogo.gif"));
		setIconImage(kit.getImage("iconimg\\ss4ulogo1.gif"));
		setBounds(10, 20, frmWd, frmHgt);  		   
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{     System.exit(0);}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = new Double(dim.getWidth());
		        Double h = new Double(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
	}
	
	public void initFrm(int m, int y)
	{
		attachMenu( );
		bkBus = new BookBus(this);
		mainPane = new DateTable(this, m, y, frmWd, frmHgt);
		GPopupMenu popM = new GPopupMenu(this);
		mainPane.setPopupMenu(popM);
		setHD();  
		mainPane.setTabHeader(hd);
		//mainPane.initPane(bus.getBookStrs(), 100);
		mainPane.initPane(bkBus.getBookStrs(), 100);
		mainPane.setUserIdFd(bus.getActUser().getUserid());
		getContentPane().add(mainPane, BorderLayout.CENTER);
	}
	// ---- public funtional methods ----------
	public DBDataBusImp getDataBus() { return bus; }
	public BookBus getBookBus() { return bkBus; }
	
	public void setFavourBounds(int width, int height)
	{
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}
	
	void setUILook( )
	{   
		//String laf = UIManager.getSystemLookAndFeelClassName();
		try{
			Border aBorder = BorderFactory.createRaisedBevelBorder();
			Border cBorder 
			 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			//Border bBorder = new CompoundBorder(new CompoundBorder(
			//	             aBorder, cBorder), aBorder);

			Border bBorder = new CompoundBorder(aBorder, cBorder);
			UIManager.put("Button.border", bBorder);
			/*Border dBorder 
			 = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			
			Border eBorder
		    = BorderFactory.createLoweredBevelBorder( );
			
			UIManager.put("Panel.border", eBorder);
            */
			MetalLookAndFeel mlf = new MetalLookAndFeel();
			MetalLookAndFeel.setCurrentTheme(new BlueTheme());
			UIManager.setLookAndFeel(mlf);
		}catch(UnsupportedLookAndFeelException exc)
		{
			System.err.println("UnsupportedLookAndFeel:"+exc.toString());
		}
		catch(Exception exc)
		{
			System.err.println("Error loading:"+exc.toString()); 
		}
	}
		
	protected void setHD()
	{
	  try
	  {
		List<String> st= bkBus.getRoomMap().getNames();
		hd = new String[st.size()];
		for(int i=0; i<st.size(); i++)
			hd[i]= st.get(i);
	  }catch(Exception ex){ex.printStackTrace(); }
	}
	
	public void attachMenu( )
	{
		boolean fg =bus.getActUser().getUserTye().equals("Administrator");
		setJMenuBar(new GMenuBar(this, fg));
		Container cp = getContentPane();
	   	attachToolBar(cp);		
     }
	public void attachToolBar(Container cp )
	{
		GToolBar toolbar = new GToolBar( );
		BarAction ba1 = new BarAction("Tool bar");
        ba1.setAdaptor(this);
		toolbar.setBasicBar(ba1);
		cp.add(toolbar, BorderLayout.NORTH);
	}
	public void acting(String cmd)
	{  
		if(cmd.equals("Exit")) System.exit(0);
		else if(cmd.equals("Close")) System.exit(0);
		else if(cmd.equals("Book")) performBook();
		else if(cmd.equals("Edit")) performEdit();
		else if(cmd.equals("Delete")) performDelete();
		else if(cmd.equals("Book Sheet")) showBookSheet();
		else if(cmd.equals("Customer List")) showCustomerList();
		else if(cmd.equals("Statement")) showStatement();
		else if(cmd.equals("Change Book")) changeBook();
		else if(cmd.equals("Room List")) showRooms();
		else if(cmd.equals("Report"))
		{
			ReportDlg rdg = new ReportDlg(this, "Room Booking Report");
			rdg.setFavourBounds(900, 500);
			int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
			rdg.setCurrentMonthYear(sel[1], sel[0]);
			rdg.initDlg();
			rdg.setVisible(true);
		}else if(cmd.equals("Room Type"))
		{
			TypeDefDlg tdlg = new TypeDefDlg(this);
			tdlg.setPane();
			tdlg.setFavourBounds(475, 300);
			tdlg.setVisible(true);
		}else if(cmd.equals("Add Rooms"))
		{
			ConfigDlg cdlg = new ConfigDlg(this);
			cdlg.setPane();
			cdlg.setFavourBounds(475, 300);
			cdlg.setVisible(true);
		}else if(cmd.equals("Restore Data"))
			impRepository();			
		else if(cmd.equals("Backup Data"))
			expRepository();
		else if(cmd.equals("Page Setup"))
			pageSetup();
		else if(cmd.equals("Help"))
		{
			String fnm= "iconimg/bkhelp.html";
			HTMLPane hp= new HTMLPane(fnm);
			GenericDlg hdg = new GenericDlg(this, "Booking Help");
			hdg.setFavourBounds(700, 500);
			hdg.addPanel(hp);
			hdg.setVisible(true);
		}else if(cmd.equals("About"))
		{
			GAboutDlg d = new GAboutDlg(this, 300, 200);
			d.setVisible(true);
		}
		
	}
	//*********** support method******************
	void impRepository()
	{
	   try
	   {
		  File dstDir = new File("./slkdata");
		  JFileChooser chooser = new JFileChooser();
		  chooser.setMultiSelectionEnabled(true);
		  chooser.setCurrentDirectory(new File("./slkbackup"));
		  chooser.setDialogTitle("Import Backup Data to Repository");
		  //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		  chooser.setAcceptAllFileFilterUsed(true);
		  //chooser.addChoosableFileFilter(new DirFilter());
		  //chooser.setFileFilter(new DirFilter()); 
		  if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		  { 
			  File[] files = chooser.getSelectedFiles();
			  for(int i=0; i<files.length; i++)
			  {
				 try
				 {
				    File srcF = files[i];
				    if(srcF.isDirectory()) continue;
				    String dspt = dstDir.getAbsolutePath()+"/"+srcF.getName();
				    InputStream in = new FileInputStream(srcF);
					OutputStream out = new FileOutputStream(dspt); 
					// Transfer bytes from in to out
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) 
						out.write(buf, 0, len);
					in.close();
					out.close();		    			    
				 }catch(Exception ex) { ex.printStackTrace(); }
			  }
		  }
	   }catch(Exception ex){ex.printStackTrace(); }
	}
	
	void expRepository()
	{
		try
		{
			File srcDir = new File("./slkdata");
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new File("./slkbackup"));
			chooser.setDialogTitle("Export Backup Data From Repository");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			  // disable the "All files" option.
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(new DirFilter()); 
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{ 
				//File dstDir= chooser.getCurrentDirectory(); parent directory
				File dstDir1 = chooser.getSelectedFile();
				String abP = dstDir1.getAbsolutePath();
				Date td = new Date();
				abP=abP+"/slkdata_"+idFmt.format(td);
				File dstDir = new File(abP);
		System.out.println("BKFrm259: desDir "+dstDir1.getName());
				copyDir(srcDir, dstDir);
			}
		   }catch(Exception ex){ex.printStackTrace(); }
	}
	
	public void copyDir(File srcPath, File dstPath)throws IOException
    {
		if (srcPath.isDirectory())
		{
			if (!dstPath.exists())
				dstPath.mkdir();

			String files[] = srcPath.list();
			for(int i = 0; i < files.length; i++)
				copyDir(new File(srcPath, files[i]), 
								new File(dstPath, files[i]));
		}else
		{
			InputStream in = new FileInputStream(srcPath);
			OutputStream out = new FileOutputStream(dstPath); 
			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) 
				out.write(buf, 0, len);
			in.close();
			out.close();
		}
    }

	void pageSetup()
	{
		attrs = new HashPrintRequestAttributeSet();
		PrinterJob job = PrinterJob.getPrinterJob();
		pageFormat = job.pageDialog(attrs);
	
	}
	
	//return: yyyyMMdd for complete date
	String composeDate(int sel[])
	{
		if(sel[2]<0) return new String();
		try
		{
			String yr = Integer.toString(sel[0]);
			String mth = Integer.toString(sel[1]);
			if(sel[1]<10) mth = "0"+mth;
			int dd = sel[2]+1;
			String dy = Integer.toString(dd);
			if(dd<10) dy = "0"+dy;
			String dt = yr+mth+dy;
			return dt;
		}catch(Exception ex) 
		{ 
			ex.printStackTrace();
			return new String(); 
		}
	}
	
	String composeCode(int sel[], boolean isCode)
	{
		try
		{
			String rid = null;
			if(isCode)
				rid = bkBus.getRoomMap().getCodes().get(sel[3]);
			else
				rid = bkBus.getRoomMap().getNames().get(sel[3]);
			return rid;
		}catch(Exception ex) { return new String(); }
	}
	
	void performBook()
	{		
		BKWizardDlg wdg = new BKWizardDlg(this, "New Book Wizard: step by step", 1);
		wdg.setFavourBounds(450, 400);
		int idx1[] = mainPane.gettCellIdcs();
		wdg.setCurrentMonthYear(idx1[1], idx1[0]);
		wdg.initWizardDlg();
	    wdg.setVisible(true);
	}
	
	void performEdit()
	{
	   int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room_idx
	   String dt = composeDate(sel);
	   String rid = composeCode(sel, true);
	   BookDetail bkd1 = getSelection(dt, rid);
	   if(bkd1 == null) return;
	   BKEditorDlg dlg = new BKEditorDlg(this, "Edit B&B Room Booking", sel);
	   dlg.setBookDetail(bkd1);
	   dlg.setCurrentMonthYear(sel[1], sel[0]);
	   dlg.initDlg();
	   dlg.setDlgEditor();		
	   dlg.setFavourBounds(560, 565);
	   dlg.setVisible(true);
	}
	
	void performDelete()
	{
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		String dt = composeDate(sel);
		String rid = composeCode(sel, true);
		BookDetail bkd1 = getSelection(dt, rid);
		if(bkd1 == null) return;
		   
		String msg2="Are you sure to remove the booking:\n"+
			"Room:  "+bkd1.getRoomName()+", " +bkd1.getStartDate()+"--"
			+bkd1.getEndDate()+"\n"+"from database permanently? It cannot undo";
		int reply = JOptionPane.showConfirmDialog(this, msg2, "Confirmation", 
				JOptionPane.YES_NO_OPTION);
	    if (reply == JOptionPane.NO_OPTION) return;
	    SLKCustomer cst =(SLKCustomer)bkBus.getCustMap().find(bkd1.getCustId());
	    for(int i=0; i<cst.getBookIds().size(); i++)
		{
		   String bidx = cst.getBookId(i);
		   bkBus.getBookMap().rm(bidx);
		}
		bkBus.getCustMap().rm(cst.getCode());			
	    bkBus.saveBookMap();
		bkBus.saveCustMap();
		bkBus.updateBookStrs();			
		mainPane.freshData();			
	}
	
	void showBookSheet()
	{
		BookSheetDlg rdg = new BookSheetDlg(this, "Booking Sheet ");
		rdg.setFavourBounds(900, 500);
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		rdg.setCurrentMonthYear(sel[1], sel[0]);
		rdg.initDlg();
		rdg.setVisible(true);
	}
	
	void showCustomerList()
	{
		CustomerListDlg rdg = new CustomerListDlg(this, "Customer List");
		rdg.setFavourBounds(900, 500);
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		rdg.setCurrentMonthYear(sel[1], sel[0]);
		rdg.initDlg();
		rdg.setVisible(true);
	}
	void showStatement()
	{
		ReportDlg rdg = new ReportDlg(this, "Room Booking Statement");
		rdg.setFavourBounds(900, 500);
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		rdg.setCurrentMonthYear(sel[1], sel[0]);
		rdg.initDlg();
		rdg.setVisible(true);
	}
	
	void showRooms()
	{
		RoomListDlg rdg = new RoomListDlg(this, "Room List");
		rdg.setFavourBounds(900, 500);
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		rdg.setCurrentMonthYear(sel[1], sel[0]);
		rdg.initDlg();
		rdg.setVisible(true);
	}
	
	void changeBook()
	{
		int sel[] = mainPane.gettCellIdcs(); //y, m, day-1, room
		String dt = composeDate(sel);
		String rid = composeCode(sel, true);
		BookDetail bkd1 = getSelection(dt, rid);
		if(bkd1 == null) return;
		BKChangeDlg wdg = new BKChangeDlg(this, "New Book Wizard: step by step");
		wdg.setFavourBounds(450, 400);
		int idx1[] = mainPane.gettCellIdcs();
		SLKCustomer cst =(SLKCustomer)bkBus.getCustMap().find(bkd1.getCustId());
		wdg.setOldBook(cst, bkd1);
		wdg.setCurrentMonthYear(idx1[1], idx1[0]);
		wdg.initChangeDlg();
		wdg.setOldBook();
	    wdg.setVisible(true);		   		
	}

	BookDetail getSelection(String dt, String rid)
	{
		if(dt.length()<1)
		{
			String msg1 = "You have to select the book date and room for editing," +
			" \nplease select first: just click the cell";
			JOptionPane.showMessageDialog(this, msg1, "alert", JOptionPane.ERROR_MESSAGE);
			return null;
		}	
		String bookId1 = bkBus.findBookDetailId(dt, rid);
		BookDetail bkd1 = (BookDetail)bkBus.getBookMap().find(bookId1);
		if(bkd1==null) //new booker
		{
			String msg2 = "Cannot find booking: do you select the booked room," +
			" \nplease select first: just click the cell";
			JOptionPane.showMessageDialog(this, msg2, "alert", JOptionPane.ERROR_MESSAGE);
			   return null;
		}
		return bkd1;
	}
	
	void trace(BookDetail bdk) throws Exception
	{
		int sel[] = new int[4]; //y, m, day-1, room_idx
		Date std = idFmt.parse(bdk.getStartDate());
	    //Date etd = idFmt.parse(bdk.getEndDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(std);
        sel[0] = calendar.get(Calendar.YEAR);
        sel[1] = calendar.get(Calendar.MONTH);
		sel[2] = calendar.get(Calendar.DAY_OF_MONTH)-1;
		sel[3] = bkBus.getRoomMap().indexOfCode(bdk.getRoomId());		
		BKEditorDlg dlg = new BKEditorDlg(this, "Edit B&B Room Booking", sel);
		dlg.setBookDetail(bdk);
		dlg.setCurrentMonthYear(sel[1], sel[0]);
		dlg.initDlg();
		dlg.setDlgEditor();		
		dlg.setFavourBounds(560, 565);
		dlg.setVisible(true);		
	}
	
	class BlueTheme extends DefaultMetalTheme
	{
		 public String getName() { return "Mars"; }
		 //overide something.
		 //for separator line and edge color
		 private final ColorUIResource primary1 = new ColorUIResource(0, 250, 250);	 
		 private final ColorUIResource primary2 = new ColorUIResource(250, 250, 0);
		 private final ColorUIResource primary3 = new ColorUIResource(255, 0, 0);
		 
		 // actually pain on panel 
		 private final ColorUIResource secondary3 = 
			           new ColorUIResource(120, 210, 255);
		 protected ColorUIResource getPrimary1() { return primary1; }
		 protected ColorUIResource getPrimary2() { return primary2; }
		 protected ColorUIResource getPrimary3() { return primary3; }
		 protected ColorUIResource getSecondary3() { return secondary3; }
	}
	
	class DirFilter extends FileFilter
	{
		public DirFilter() { super();}
		public boolean accept(File f)
		{
			 if (f.isDirectory()) return true;
			 else return false;			      
		}
		public String getDescription()
		{ return new String("Directory only"); }
		
	}
	
}



