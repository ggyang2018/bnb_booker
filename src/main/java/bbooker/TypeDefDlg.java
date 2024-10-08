package bbooker;


/*Type defintion Dialog
 * @Author Guang Yang
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import guiwidget.*;

public class TypeDefDlg extends JDialog
{
	static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	BKFrame frm;
	TypeDefPane typePane;
	public TypeDefDlg(BKFrame frm)
	{
		super(frm);
		this.frm = frm;
		setModal(true);
		setTitle("Type Definition Dialog");
		//setIconImage(Toolkit.getDefaultToolkit().getImage("slkdata\\sslogo.gif"));
		setIconImage(Toolkit.getDefaultToolkit().getImage("iconimg\\ss4ulogo1.gif"));
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
	
	public void setPane()
	{
		typePane = new TypeDefPane(this);
		typePane.setDataBus(frm.bkBus);
		typePane.initPane();
		getContentPane().add(typePane);
	}

}
