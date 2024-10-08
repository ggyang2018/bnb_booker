package guiwidget;

import java.awt.*;
//import java.util.*;

public class XYLayout implements LayoutManager
{
	  private Dimension MinSize;
	  private Dimension PreferredSize;

	  //private final int BaseScreenW = 800;
	  //private final int BaseScreenH = 600;
	  //need to change to new resolution
 	  private final int BaseScreenW = 1024;
	  private final int BaseScreenH = 768;
	  
	  private double RateX = 1.0;
	  private double RateY = 1.0;

	   //-------- for application interface change------------
	  private Dimension OriginalSize = null;
	  private Rectangle ComponentBounds[];
	  private boolean OriginalFlag = false;
	  private boolean ComponentBoundsFlag = false;

	  public XYLayout( )
	  {
		  super();
		  MinSize = new Dimension(100, 100);
		  PreferredSize = new Dimension(BaseScreenW, BaseScreenH);
		  setScreenScale( );

	  }

	  
	  //------- implement Functions of Layout ----------------
	  public void addLayoutComponent(String name, Component comp)
	  {
	  }

	  public void removeLayoutComponent(Component comp)
	  {
	  }

	  public Dimension minimumLayoutSize(Container parent)
	  {  return MinSize;   }

	  public Dimension preferredLayoutSize(Container parent)
	  {  return PreferredSize;     }

	  public void layoutContainer(Container parent)
	  {
		  setOriginalSize(parent);
		  setComponentBounds(parent);
		  
		  //Insets insets = parent.getInsets();
		  //int pw=parent.getSize().width-insets.left-insets.right;
		  //int ph=parent.getSize().height-insets.top-insets.bottom;
		  
		  //not consider the insets
		  double pw=parent.getSize().getWidth();
		  double ph=parent.getSize().getHeight();
		  double x_r = pw/OriginalSize.getWidth();
		  double y_r = ph/OriginalSize.getHeight();
		  
		  for(int i=0; i<parent.getComponentCount( ); i++)
		  {
			  Component c = parent.getComponent(i);
			  //Rectangle r = c.getBounds( );
			  //take from first original one
			  Rectangle r = ComponentBounds[i];
			  //if(isConstant)
			  //c.setBounds(reBounds(r));
			  //else
			  c.setBounds(reBounds(r, x_r, y_r));
		  }
		  
	  }

	  private void setScreenScale( )
	  {
		 Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		 RateX = dim.getWidth( )/BaseScreenW;
		 RateY = dim.getHeight( )/BaseScreenH;
	  }

	  //only vary with screen size change
	  private Rectangle reBounds(Rectangle rec)
	  {
		  int x = (int)Math.round(rec.getX()*RateX);
		  int y = (int)Math.round(rec.getY()*RateY);
		  int w = (int)Math.round(rec.getWidth()*RateX);
		  int h = (int)Math.round(rec.getHeight()*RateY);

		  return new Rectangle(x, y, w, h);
	  }

	  //vary with container enlarge or shrink
	  private Rectangle reBounds(Rectangle rec, double x_r, double y_r)
	  {		  
		  int x = (int)Math.round(rec.getX()*x_r);
		  int y = (int)Math.round(rec.getY()*y_r);
		  int w = (int)Math.round(rec.getWidth()*x_r);
		  int h = (int)Math.round(rec.getHeight()*y_r);
		  return new Rectangle(x, y, w, h);
	  }

	  private void setOriginalSize(Container parent)
	  {
		  if(!OriginalFlag)
		  {
			 Insets insets = parent.getInsets();
		     int pw=parent.getSize().width-insets.left-insets.right;
		     int ph=parent.getSize().height-insets.top-insets.bottom;
			 OriginalSize = new Dimension(pw, ph);
			 OriginalFlag = true;
		  }
	  }

	  private void setComponentBounds(Container parent)
	  {
		  if(!ComponentBoundsFlag)
		  {
			 ComponentBounds = new Rectangle[parent.getComponentCount()];
			 for(int i=0; i<parent.getComponentCount( ); i++)
			 {
			  Component c = parent.getComponent(i);
			  ComponentBounds[i]  = c.getBounds( );
			 }
		
			 ComponentBoundsFlag = true;
		  }
	  }
}




