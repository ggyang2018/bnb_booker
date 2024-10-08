package slklogin;
/* Configure Pane display/edit configuration 
 * @Author Guang Yang
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import guiwidget.*;

public class ConfigPane extends JDialog implements ActionListener
{
	static public final long  serialVersionUID =AccessCtrlDlg.serialVersionUID;
	ConfigInfo cif;
	String md[] = {"All", "Non"};
	
	JTextField macAddr0, macAddr1, hostNm0, hostNm1;
	JComboBox modeBx;
	JButton saveBtn, closeBtn, resetBtn;
	
	public ConfigPane(JDialog owner, String tit)
	{
		super(owner);
		setTitle(tit);
		setModal(true);
		cif = new ConfigInfo();
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{     System.exit(0);}
		 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
		        Double w = Double.valueOf(dim.getWidth());
		        Double h = Double.valueOf(dim.getHeight());
		        setSize(w.intValue()+1, h.intValue()+1);
		        setSize(w.intValue(), h.intValue());
			}
		});
		getContentPane().setLayout(new XYLayout());
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
	
	public void init()
	{
	   int x0=5, y0=3, w0=150, w1=200, h0=20, off=5;
	   int x1 = x0+w0+off;
	   JLabel addLb1 = new JLabel("Stored MAC Address:", JLabel.RIGHT);
	   addLb1.setBounds(x0, y0, w0, h0);
	   macAddr0 = new JTextField();
	   macAddr0.setBounds(x1, y0, w1, h0);
	   y0 = y0+h0+off;
	   JLabel addLb2 = new JLabel("Local MAC Address:", JLabel.RIGHT);
	   addLb2.setBounds(x0, y0, w0, h0);
	   macAddr1 = new JTextField();
	   macAddr1.setBounds(x1, y0, w1, h0);
	   y0 = y0+h0+off;
	   JLabel hostLb1 = new JLabel("Stored Host Name:", JLabel.RIGHT);
	   hostLb1.setBounds(x0, y0, w0, h0);
	   hostNm0 = new JTextField();
	   hostNm0.setBounds(x1, y0, w1, h0);
	   y0 = y0+h0+off;
	   JLabel hostLb2 = new JLabel("Local Host Name:", JLabel.RIGHT);
	   hostLb2.setBounds(x0, y0, w0, h0);
	   hostNm1 = new JTextField();
	   hostNm1.setBounds(x1, y0, w1, h0);
	   
	   y0 = y0+h0+off;
	   JLabel addLb3 = new JLabel("Mode:", JLabel.RIGHT);
	   addLb3.setBounds(x0, y0, w0, h0);
	   modeBx = new JComboBox(md);
	   modeBx.setBounds(x1, y0, w0, h0);
	   y0 = y0+h0+2*off;
	   JSeparator sp = new JSeparator();
	   sp.setBounds(3, y0, 370, 5);
	   y0 = y0+h0-2*off;
	   saveBtn = new JButton("Save");
	   saveBtn.setBounds(x0+20, y0, 100, h0);
	   resetBtn = new JButton("Reset");
	   resetBtn.setBounds(x0+130, y0, 100, h0);
	   closeBtn= new JButton("Close");
	   closeBtn.setBounds(x0+240, y0, 100, h0);
	   
	   getContentPane().add(addLb1);getContentPane().add(macAddr0);
	   getContentPane().add(addLb2);getContentPane().add(macAddr1);
	   getContentPane().add(hostLb1);getContentPane().add(hostNm0);
	   getContentPane().add(hostLb2);getContentPane().add(hostNm1);
	   getContentPane().add(addLb3);getContentPane().add(modeBx);
	   getContentPane().add(sp);
	   getContentPane().add(saveBtn); getContentPane().add(resetBtn);
	   getContentPane().add(closeBtn);
	   
	   macAddr0.setText(cif.getMachInfo());
	   macAddr1.setText(cif.getMacAddr1());
	   macAddr1.setEditable(false);
	   hostNm0.setText(cif.getHostNm0());
	   hostNm1.setText(cif.getHostNm1());
	   hostNm1.setEditable(false);
	   modeBx.setSelectedIndex(0);
	   
	   saveBtn.addActionListener(this);
	   closeBtn.addActionListener(this);
	   resetBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent av)
	{
		String cmd = av.getActionCommand();
		if(cmd.equals("Close"))System.exit(0);
		else if(cmd.equals("Save"))
		{
			try
			{
				cif.setMachInfo(macAddr0.getText());
			    cif.setHostNm0(hostNm0.getText());
				cif.setMode(modeBx.getSelectedIndex()==0);
				cif.save();
			}catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this, ex.toString(), 
						"alert", JOptionPane.ERROR_MESSAGE);
			}
			System.exit(0);
		}else if(cmd.equals("Reset"))
		{
			hostNm0.setText("");
			macAddr0.setText("");		
		}
	}
}
