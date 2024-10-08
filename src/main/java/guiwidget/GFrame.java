package guiwidget;

/* **************************************************************
 Name: GFrame.java
 Aim: Generic Frame works as top level framework
 Input Non
 Output contains three panel, HeadPane, TreePane and MainPane
 Model: extend by its chidren to perform specified function
 ************************************************************* */
 //package commonui;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
import java.awt.dnd.*;


 public class GFrame extends JFrame
 {
	 static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	 JPanel ContainPane;
	 
	 JPanel HeadPane;
	 GTreePane TreePane;
	 JPanel MainPane;

	 
	 Dimension ScreenSize;
	 Dimension FrameSize;
	 int HeadHeight = 150;
	 int TreeWidth = 150;

	 final boolean isTopApp;

	
	 public GFrame( )
	 {
		 this("Generic Application Frame", true);
		 
	 }

	 public GFrame(String title, boolean is)
	 {
		 super(title);
		 ContainPane = new JPanel( );
		 ScreenSize = Toolkit.getDefaultToolkit( ).getScreenSize();
		 isTopApp = is;
       
		 attachMenu( );
		 Container cp = getContentPane();
		 attachToolBar(cp);
		 cp.add(ContainPane, BorderLayout.CENTER);
		 //non default should override the constructure with 
		 //new custome panel to be set
		 addWindowListener(new WindowAdapter()
		 {
			public void windowClosing(WindowEvent we)
			{
                  if(isTopApp)
					System.exit(0);
				  else
					  dispose( );
      		}
		 
            //In order to re-display compoenent properly rather than 
			// misaligned. This methods should be called
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

	 private void setInitContainPane( )
	 {
		 ContainPane.setLayout(new XYLayout());
		 
         HeadPane.setBounds(0, 0, FrameSize.width, HeadHeight);
		 TreePane.setBounds(0, HeadHeight, TreeWidth, FrameSize.height-HeadHeight);
         MainPane.setBounds(TreeWidth, HeadHeight,
			                FrameSize.width-TreeWidth,
			                FrameSize.height-HeadHeight);
		 ContainPane.add(HeadPane);
		 ContainPane.add(TreePane);
		 ContainPane.add(MainPane);
         
	}

	 //set bounds comform with screen size
	 public void setFrameBounds(int x, int y)
	 {
		 this.setBounds(x, y, 
			             ScreenSize.width-2*x,
						 ScreenSize.height-2*y);
		 FrameSize = new Dimension(ScreenSize.width-2*x,
						           ScreenSize.height-2*y);
	 }

	 public void setFrameBounds(int x, int y, int w, int h)
	 {
		 this.setBounds(x, y, w, h);
		 FrameSize = new Dimension(w, h);
	 }

	 public void attachMenu( )
	 {
		 //setJMenuBar(new GMenuBar());
	 }

	 public void attachToolBar(Container cp )
	 {
		 GToolBar toolbar = new GToolBar( );
		 toolbar.setBasicBar(new BarAction("Tool bar"));
		 cp.add(toolbar, BorderLayout.NORTH);
         //set drop target
		 new DropTarget(toolbar, new BtnDropListener(this));
	 }


	private void setPanels(JPanel hp, GTreePane tp, JPanel mp, int h, int w)
	{
		HeadPane = hp;
		TreePane = tp;
	    MainPane = mp;
	    
		if(h <1)
		 HeadHeight = 100;
        if(w<1)
		TreeWidth = 150;
	}
        
 
	 //overrid this methods to change the appeerance
	 public void setPanels()
	 {
		 setPanels(new JPanel(), new GTreePane(0), new JPanel(), 0, 0);
		 HeadPane.setBackground(Color.cyan);
		 TreePane.setBackground(Color.blue);
		 MainPane.setBackground(Color.green);

		 setFrameBounds(30, 50);
		 setInitContainPane();
	 }
		 

	 public void display( )
	 {
		 this.setVisible(true);
	 }

	 public static void main(String args[])
	 {
		 GFrame gf = new GFrame( );
		 gf.setPanels( ); //override method to change it
		 gf.display();
	 }

 }

