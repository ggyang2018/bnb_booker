/*
 * Drawing rangle angle with title for group the functionanlity
 * Author GY
 */

package guiwidget;

import javax.swing.*;
import java.awt.*;

public class LineGroup 
{
	public final int LEFT=0, MIDDLE=1, RIGHT=2;
	int x0, y0, width, height;
	String title;
	int alignTitle = 0, lineWidth=5;
	Color lineColor;
	Font font; 
	JPanel pane;
	public LineGroup(JPanel jp)
	{
	    pane =jp;
	   title = new String();
	   x0=0; y0=0; width=10; height=10;
	   lineColor = Color.black;
	   
	}
	
	public void setOriginal(int x, int y) { x0 = x; y0=y;}
	public void setWidth(int wid) { width = wid; }
	public void setHeight(int hei){ height = hei; }
	public void setAlignTitle(int align){ alignTitle = align; }
	public void setBounds(int x, int y, int wid, int hght)
	{ x0=x; y0=y; width=wid; height = hght; }
	public void setTitle(String tit) { title = tit; }
	
	//draw with title, defined later
	public void draw(Graphics g )
	{
		JSeparator sp1 = new JSeparator();
		sp1.setBounds(2, 115, 275, 5);
		char chs[]= title.toCharArray();
		JLabel titLb = new JLabel(title, JLabel.CENTER);
		titLb.setFont(font);
		FontMetrics fm = g.getFontMetrics();
	}
	
	public void draw()
	{
		JSeparator sp1 = new JSeparator();
		sp1.setBackground(lineColor);
		sp1.setBounds(x0, y0, width, lineWidth);
		
		JSeparator sp2 = new JSeparator(JSeparator.VERTICAL);
		sp2.setBackground(lineColor);
		sp2.setBounds(x0, y0, lineWidth, height);
		
		JSeparator sp3 = new JSeparator(JSeparator.VERTICAL);
		sp3.setBackground(lineColor);
		sp3.setBounds(x0+width, y0, lineWidth, height);
		
		JSeparator sp4 = new JSeparator();
		sp4.setBackground(lineColor);
		sp4.setBounds(x0, y0+height, width, lineWidth);
		
		pane.add(sp1);
		pane.add(sp2);
		pane.add(sp3);
		pane.add(sp4);
	}

}
