package guiwidget;

/* ****************************************************************
 
   Name: GTooBox.java as general tool bar 
   Input: none, shoudl self contained, it integrated all button 
          images from Sun's Look and Feel repository
   Output Action of the pressed button
   Model: It contains all images icons and work with menu items
          some would be grayed out if item doesn't exists.
   Process: user clicked and customize it by drag and drop
             there are two font 24 and 16. 
   Author GY, date 8 Jan 2001

  //make it drag and drop enable
******************************************************************* */
 
 import javax.swing.*;
 import javax.swing.border.*;
 import javax.swing.table.TableModel;
 import java.awt.*;
 import java.awt.event.*;
 import java.net.*;				
 import java.io.*;
 import java.lang.reflect.*;
 import java.util.*;

 import java.awt.dnd.*;
import java.awt.datatransfer.*;


public class GToolBox extends JFrame implements MouseListener
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	static TreeMap IconIDMap;
	
	private final Dimension ScrSize = Toolkit.getDefaultToolkit( ).getScreenSize();
	Container container;
    SoftBevelBorder ActBorder;
    EmptyBorder InactBorder;

	//drag modlue
    private DragSource DragSrc;
	private DGListener DragGestLstn;
	private DragSourceListener DragSrcLstn;
	private int DragAct = DnDConstants.ACTION_COPY;
	private Vector DragBtns;

    
   public GToolBox ()
   {
		super ("General Tool Box");
		IconIDMap = new TreeMap( );
		DragBtns = new Vector( );
		container = getContentPane( );
		container.setLayout (new GridLayout(9, 9, 1, 1));

		ActBorder = new SoftBevelBorder (BevelBorder.RAISED);
		InactBorder = new EmptyBorder (0, 0, 0, 0);
		 
		//setToolBox(new BarAction("Tool Box"));
		setToolBoxAll( );
		setBounds(Math.round((float)ScrSize.getWidth())-350, 
			      Math.round((float)ScrSize.getHeight())-350, 
				   300, 300);
		setVisible(true);
	}

    public GToolBox (Vector ids)
    {
		super ("Tool Box");
		IconIDMap = new TreeMap( );
		DragBtns = new Vector( );
		container = getContentPane( );
		ActBorder = new SoftBevelBorder (BevelBorder.RAISED);
    	InactBorder = new EmptyBorder (0, 0, 0, 0);
		setToolBox(ids);
	}

  public void add (String imgURL, String tip, Action act) 
  {
	  File f = new File(imgURL);
	  if(!f.exists())
		  return;
     JButton toolBtn = new JButton( );
	 toolBtn.setAction(act);
     URL imageURL = imageURL = getClass ().getResource (imgURL);
	 Image iconImg = Toolkit.getDefaultToolkit ().getImage (imageURL);
	
	 Image Img=iconImg.getScaledInstance(24, 24, 
			                         Image.SCALE_AREA_AVERAGING); 
	 
	 ImageIcon 	BtnIcon = new ImageIcon (Img);
	 toolBtn.setIcon (BtnIcon);
	
    
      toolBtn.setBorder (InactBorder);
	  toolBtn.setText("");
	  toolBtn.setActionCommand(tip);
	  toolBtn.setToolTipText(tip);
	  toolBtn.setRequestFocusEnabled (false);
	
      container.add(toolBtn);
  }

  public void setToolBox(Action a)
  {
	    //setFloatable(false);

		//general icons
		add("iconimg/About24.gif", "About", a);
		add("iconimg/Add24.gif", "Add", a);
		add("iconimg/AlignBottom24.gif", "Align Button", a);
		add("iconimg/AlignCenter24.gif", "Align Center", a);
		add("iconimg/AlignJustifyHorizontal24.gif", "Align Horizontal", a);
		add("iconimg/AlignJustifyVertical24.gif", "Align Vertical", a);
		add("iconimg/AlignLeft24.gif", "Align Left", a);
		add("iconimg/AlignRight24.gif", "Align Right", a);
		add("iconimg/AlignTop24.gif", "Align Top", a);
		add("iconimg/Bookmarks24.gif", "Bookmark", a);
		add("iconimg/ComposeMail24.gif", "Compose Mail", a);
		add("iconimg/ContextualHelp24.gif", "Textual Help", a);
		add("iconimg/Copy24.gif", "Copy", a);
		add("iconimg/Cut24.gif","Cut", a);
		add("iconimg/Delete24.gif", "Delete", a);
		add("iconimg/Edit24.gif", "Edit", a);
        add("iconimg/Export24.gif", "Export", a);
		add("iconimg/Find24.gif", "Find", a);
		add("iconimg/FindAgain24.gif", "Find Again", a);
		add("iconimg/Help24.gif", "Help", a);
		add("iconimg/History24.gif", "History", a);
		add("iconimg/Import24.gif", "Import", a);
		add("iconimg/Information24.gif", "New", a);
		add("iconimg/New24.gif", "Information", a);
		add("iconimg/Open24.gif", "Open", a);
		add("iconimg/PageSetup24.gif", "Page Setup", a);
		add("iconimg/Paste24.gif", "Paste", a);
		add("iconimg/Preferences24.gif", "Preference", a);
		add("iconimg/Print24.gif", "Print", a);
		add("iconimg/PrintPreview24.gif", "Print Preview", a);
		add("iconimg/Properties24.gif", "Properties", a);
		add("iconimg/Redo24.gif", "Redo", a);
		add("iconimg/Refresh24.gif", "Refresh", a);
		add("iconimg/Remove24.gif", "Remove", a);
		add("iconimg/Replace24.gif", "Replace", a);
		add("iconimg/Save24.gif", "Save", a);
		add("iconimg/SaveAll24.gif", "Save All", a);
		add("iconimg/SaveAs24.gif", "Save As", a);
		add("iconimg/Search24.gif", "Search", a);
		add("iconimg/SendMail24.gif", "Send Mail", a);
		add("iconimg/Stop24.gif", "Exit", a);
		add("iconimg/TipOfTheDay24.gif", "Day Tip", a);
		add("iconimg/Undo24.gif", "Undo", a);
		add("iconimg/Zoom24.gif", "Zoom", a);
		add("iconimg/ZoomIn24.gif", "Zoom In", a);
		add("iconimg/ZoomOut24.gif", "Zoom Out", a);

		//media icons
		add("iconimg/FastForward24.gif", "Fast Forward", a);
		add("iconimg/Movie24.gif", "Movie", a);
		add("iconimg/Pause24.gif", "Pause", a);
		add("iconimg/Play24.gif", "Play", a);
		add("iconimg/Rewind24.gif", "Rewind", a);
		add("iconimg/StepBack24.gif", "Step Back", a);
		add("iconimg/StepForward24.gif", "Step Forward", a);
		add("iconimg/MStop24.gif", "Media Stop", a);
		add("iconimg/Volume24.gif", "Volume", a);
		
		//Navigation
		add("iconimg/Back24.gif", "Back", a);
		add("iconimg/Down24.gif", "Down", a);
		add("iconimg/Forward24.gif", "Forward", a);
		add("iconimg/Home24.gif", "Home", a);
		add("iconimg/Up24.gif", "Up", a);

		//table
		add("iconimg/ColumnDelete24.gif", "Column Delete", a);
		add("iconimg/ColumnInsertAfter24.gif", "Column Inserted", a);
		add("iconimg/ColumnInsertBefore24.gif", "Column Inserting", a);
		add("iconimg/RowDelete24.gif", "Row Delete", a);
		add("iconimg/RowInsertAfter24.gif", "Row Inserted", a);
		add("iconimg/RowInsertBefore24.gif", "Row Inserting", a);
		
		//text
		add("iconimg/TAlignCenter24.gif", "At Center", a);
		add("iconimg/TAlignJustify24.gif", "At Justify", a);
		add("iconimg/TAlignLeft24.gif", "At Left", a);
		add("iconimg/TAlignRight24.gif", "At Right", a);
		add("iconimg/Bold24.gif", "Bold", a);
		add("iconimg/Italic24.gif", "Italic", a);
		add("iconimg/Normal24.gif", "Normal", a);
		add("iconimg/Underline24.gif", "Underline", a);
		
		//development
		add("iconimg/ApplicationDeploy24.gif", "Deploy", a);
		add("iconimg/Host24.gif", "Host", a);
		add("iconimg/Jar24.gif", "Jar", a);
		add("iconimg/J2EEServer24.gif", "J2EE Server", a);
		add("iconimg/Server24.gif", "Server", a);
		add("iconimg/War24.gif", "War", a);
		add("iconimg/WebComponent24.gif", "Web Component", a);
  }


  //set dynamic tool bax
  private void setIconMap( )
  {
	IconIDMap.put("About","iconimg/About24.gif");
	IconIDMap.put("Add","iconimg/Add24.gif");
	IconIDMap.put("Align Button","iconimg/AlignBottom24.gif");
	IconIDMap.put("Align Center", "iconimg/AlignCenter24.gif");
	IconIDMap.put("Align Horizontal", "iconimg/AlignJustifyHorizontal24.gif");
	IconIDMap.put("Align Vertical", "iconimg/AlignJustifyVertical24.gif");
	IconIDMap.put("Align Left", "iconimg/AlignLeft24.gif");
	IconIDMap.put("Align Right", "iconimg/AlignRight24.gif");
	IconIDMap.put("Align Top", "iconimg/AlignTop24.gif");
	IconIDMap.put("Bookmark", "iconimg/Bookmarks24.gif");
	IconIDMap.put("Compose Mail", "iconimg/ComposeMail24.gif");
	IconIDMap.put("Textual Help", "iconimg/ContextualHelp24.gif");
	IconIDMap.put("Copy", "iconimg/Copy24.gif");
	IconIDMap.put("Cut", "iconimg/Cut24.gif");
	IconIDMap.put("Delete", "iconimg/Delete24.gif");
	IconIDMap.put("Edit", "iconimg/Edit24.gif");
    IconIDMap.put("Export", "iconimg/Export24.gif");
	IconIDMap.put("Find", "iconimg/Find24.gif");
	IconIDMap.put("Find Again", "iconimg/FindAgain24.gif");
	IconIDMap.put("Help", "iconimg/Help24.gif");
	IconIDMap.put("History", "iconimg/History24.gif");
	IconIDMap.put("Import", "iconimg/Import24.gif");
	IconIDMap.put("Information", "iconimg/Information24.gif");
	IconIDMap.put( "New","iconimg/New24.gif");
	IconIDMap.put("Open", "iconimg/Open24.gif");
	IconIDMap.put("Page Setup", "iconimg/PageSetup24.gif");
	IconIDMap.put("Paste", "iconimg/Paste24.gif");
	IconIDMap.put("Preference", "iconimg/Preferences24.gif");
	IconIDMap.put("Print", "iconimg/Print24.gif");
	IconIDMap.put("Print Preview", "iconimg/PrintPreview24.gif");
	IconIDMap.put("Properties", "iconimg/Properties24.gif");
	IconIDMap.put("Redo", "iconimg/Redo24.gif");
	IconIDMap.put("Refresh", "iconimg/Refresh24.gif");
	IconIDMap.put("Remove", "iconimg/Remove24.gif");
	IconIDMap.put("Replace", "iconimg/Replace24.gif");
	IconIDMap.put("Save", "iconimg/Save24.gif");
	IconIDMap.put("Save All", "iconimg/SaveAll24.gif");
	IconIDMap.put("Save As", "iconimg/SaveAs24.gif");
	IconIDMap.put("Search", "iconimg/Search24.gif");
	IconIDMap.put("Send Mail", "iconimg/SendMail24.gif");
	IconIDMap.put("Exit", "iconimg/Stop24.gif");
	IconIDMap.put("Day Tip", "iconimg/TipOfTheDay24.gif");
	IconIDMap.put("Undo", "iconimg/Undo24.gif");
	IconIDMap.put("Zoom", "iconimg/Zoom24.gif");
	IconIDMap.put("Zoom In", "iconimg/ZoomIn24.gif");
	IconIDMap.put("Zoom Out", "iconimg/ZoomOut24.gif");

		//media icons
	IconIDMap.put("Fast Forward", "iconimg/FastForward24.gif");
	IconIDMap.put("Movie", "iconimg/Movie24.gif");
	IconIDMap.put("Pause", "iconimg/Pause24.gif");
	IconIDMap.put("Play", "iconimg/Play24.gif");
	IconIDMap.put("Rewind", "iconimg/Rewind24.gif");
	IconIDMap.put("Step Back", "iconimg/StepBack24.gif");
	IconIDMap.put("Step Forward", "iconimg/StepForward24.gif");
	IconIDMap.put("Media Stop", "iconimg/MStop24.gif");
	IconIDMap.put("Volume", "iconimg/Volume24.gif");
		
		//Navigation
	IconIDMap.put("Back", "iconimg/Back24.gif");
	IconIDMap.put("Down", "iconimg/Down24.gif");
	IconIDMap.put("Forward", "iconimg/Forward24.gif");
	IconIDMap.put("Home", "iconimg/Home24.gif");
	IconIDMap.put("Up", "iconimg/Up24.gif");

		//table
	IconIDMap.put("Column Delete", "iconimg/ColumnDelete24.gif");
	IconIDMap.put("Column Inserted", "iconimg/ColumnInsertAfter24.gif");
	IconIDMap.put("Column Inserting", "iconimg/ColumnInsertBefore24.gif");
	IconIDMap.put("Row Delete", "iconimg/RowDelete24.gif");
	IconIDMap.put("Row Inserted", "iconimg/RowInsertAfter24.gif");
	IconIDMap.put("Row Inserting", "iconimg/RowInsertBefore24.gif");
		
		//text
	IconIDMap.put("At Center", "iconimg/TAlignCenter24.gif");
	IconIDMap.put("At Justify", "iconimg/TAlignJustify24.gif");
	IconIDMap.put("At Left", "iconimg/TAlignLeft24.gif");
	IconIDMap.put("At Right", "iconimg/TAlignRight24.gif");
	IconIDMap.put("Bold", "iconimg/Bold24.gif");
	IconIDMap.put("Italic", "iconimg/Italic24.gif");
	IconIDMap.put("Normal", "iconimg/Normal24.gif");
	IconIDMap.put("Underline", "iconimg/Underline24.gif");
		
		//development
	IconIDMap.put("Deploy", "iconimg/ApplicationDeploy24.gif");
	IconIDMap.put("Host", "iconimg/Host24.gif");
	IconIDMap.put("Jar", "iconimg/Jar24.gif");
	IconIDMap.put("J2EE Server", "iconimg/J2EEServer24.gif");
	IconIDMap.put("Server", "iconimg/Server24.gif");
	IconIDMap.put("War", "iconimg/War24.gif");
	IconIDMap.put("Web Component", "iconimg/WebComponent24.gif");
  }

  private void setToolBoxAll( )
  {
	  setIconMap( );
	  Action a = new BarAction("Tool Box");
	  
	  Iterator it = IconIDMap.entrySet().iterator();
		 while(it.hasNext())
		  {
			  Map.Entry e = (Map.Entry)it.next();
			  String obj = (String)e.getValue();
			  String key = (String)e.getKey();
			  add(obj, key, a);
		  }
  }

  private void setToolBox(Vector ids)
  {
	  setIconMap( );
	  //from math calculation
	  int z = ids.size();
	  Action a = new BarAction("Tool Box");
	  if(z==0)
		  return;
	  double x1 = Math.sqrt(1+z);
	  int x = 2+Math.round((float)x1); //for gurenttee
	  container.setLayout (new GridLayout(x-2, x, 1, 1));
	  setBounds(Math.round((float)ScrSize.getWidth())-(x*25+50),
		        Math.round((float)ScrSize.getHeight())-((x-2)*25+100),
				x*25, (x-2)*25+50);
	  for(int i=0; i<z; i++)
	  {
		  String s = (String)ids.elementAt(i);
		  if(IconIDMap.containsKey(s))
		  {
			  String url = (String)IconIDMap.get(s);
			  add(url, s, a);
		  }
	  }
	  setVisible(true);

  }


  public boolean isFocusTraversable ()
  {
    return false;
  }

//----------- action event ------------------------
  public void mouseEntered (MouseEvent e)
  {
    Object eventSource = e.getSource ();
    if (!(eventSource instanceof JButton)) return;

    JButton jb = (JButton) eventSource;
    if (jb.isEnabled ()) jb.setBorder (ActBorder);
  }


  public void mouseExited (MouseEvent e)
  {
    Object eventSource = e.getSource ();
    if (!(eventSource instanceof JButton)) return;

    JButton jb = (JButton) eventSource;
    if (jb.getBorder () == ActBorder)
      jb.setBorder (InactBorder);
  }

  //make mouse listener happy
  public void mousePressed (MouseEvent e) {}
  public void mouseReleased (MouseEvent e) {}
  public void mouseClicked (MouseEvent e) {}

  //-------- add drag module ------------------------
  private void addDragComponent(JComponent com)
  {
	  DragSrc = new DragSource( );
	  DragSrcLstn = (DragSourceListener) new DGListener( );
	  DragGestLstn = new DGListener( );
	  DragSrc.createDefaultDragGestureRecognizer
		  (com, DragAct, DragGestLstn);
	  DragBtns.addElement(com);
  }

  private void addDragComponent(JComponent com, int a)
  {
	  if(a !=DnDConstants.ACTION_NONE && 
		 a !=DnDConstants.ACTION_COPY &&
		 a !=DnDConstants.ACTION_MOVE && 
		 a !=DnDConstants.ACTION_COPY_OR_MOVE && 
		 a !=DnDConstants.ACTION_LINK)
	 throw new IllegalArgumentException("action"+a);

	  DragAct = a;
	  addDragComponent(com);
  }

   //inner class fro DnD
  class DGListener implements DragGestureListener
  {
	  public void dragGestureRecognized(DragGestureEvent e)
	  {
		  System.out.println(e.getDragAction( ));
		  if((e.getDragAction() & GToolBox.this.DragAct) ==0)
			  return;
		  System.out.println("kicking off drag");

		  //seems only working in string
		  Transferable trans = null;// = new Transferable("");
			  //(GToolBox.getTitle());

          try
		  {
			  e.startDrag(DragSource.DefaultCopyDrop, trans);
		  }catch(Exception ex){}
	  }
  }
  
  public static void main(String args[])
  {
	  GToolBox tf = new GToolBox( );
  }

}

 