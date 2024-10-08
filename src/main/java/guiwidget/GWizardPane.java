package guiwidget;
/*Generic Wizard Panel working with the GWizardDlg 
 * @Author Guang Yang
 */

import java.awt.event.*;

import javax.swing.*;

abstract public class GWizardPane extends JPanel implements ActionListener
{
   static public final long  serialVersionUID = GWizardDlg.serialVersionUID;
   String paneTitle;
   GWizardDlg wdlg;
   
   GWizardPane next;
   GWizardPane previous;
   
   int wid, hgt;
   int y0 = 5; 
   int y1, seq=0;
   boolean ended = false;
   
   
   JButton backBtn, nextBtn, quitBtn, finishBtn;
   //input 100 as last/finish sign
   public GWizardPane(GWizardDlg dlg, String tit, int nbr, boolean isEnd)
   {
	   wdlg = dlg; paneTitle = tit;
	   next = null;
	   previous = null;
	   wid = dlg.getDialogWidth()-10;
	   hgt = dlg.getDialogHeight()-10;
	   y1 = hgt - 60;
	   seq = nbr;
	   ended = isEnd;
   }
   
   public void initPane()
   {
	   setLayout(new XYLayout());
	   JLabel titLb = new JLabel(paneTitle, JLabel.CENTER);
	   int x0=5, h0=20, off=3;
	   titLb.setBounds(x0, y0, wid, h0);
	   add(titLb);
	   y0 = y0+h0+2*off;
	   JSeparator sp1 = new JSeparator();
	   sp1.setBounds(x0-3, y0, wid, 5);
	   add(sp1);
	   y0 = y0+5+2*off;
	   	   
	   JSeparator sp2 = new JSeparator();
	   sp2.setBounds(x0-3, y1, wid, 5);
	   add(sp2);
	   y1 = y1+5+2*off;
	   int bw0=70;
	   backBtn = new JButton("Back");
	   backBtn.setBounds(10, y1, bw0, h0);
	   add(backBtn);
	   backBtn.setEnabled(seq>0);
	   
	   int bx0 = wid - (bw0+15);
	   quitBtn = new JButton("Quit");
	   quitBtn.setBounds(bx0, y1, bw0, h0);
	   add(quitBtn);
	   
	   bx0 = bx0 - (bw0+10);
	   nextBtn = new JButton("Next");
	   nextBtn.setBounds(bx0, y1, bw0, h0);
	   
	   finishBtn = new JButton("Finish");
	   finishBtn.setBounds(bx0, y1, bw0, h0);
	   
	   if(ended)add(finishBtn); 
	   else add(nextBtn);
	   
	   backBtn.addActionListener(this);
	   nextBtn.addActionListener(this);
	   quitBtn.addActionListener(this);
	   finishBtn.addActionListener(this);
   }
   
   public GWizardDlg getWDlg() { return wdlg; }
   public int getY0() { return y0; }
   public int getY1() { return y1; }
   public int getWid() { return wid; }
   public int getHgt() { return hgt; }
   public int getSeq() { return seq; }
   public GWizardPane getNext() { return next; }
   public void setNext(GWizardPane wp) { next = wp; }
   public GWizardPane getPrevious() { return previous; }
   public void setPrevious(GWizardPane wp) { previous = wp; }
   
  
   public void actionPerformed(ActionEvent av)
   {
	   String cmd = av.getActionCommand();
	   if(cmd.equals("Quit"))
	   {
		   wdlg.dispose();
		   wdlg.setVisible(false);
	   }else if(cmd.equals("Back"))
	   {
		   wdlg.showPane(seq-1);
		   backAction();
			   
	   }else if(cmd.equals("Next"))
	   {
		   if(nextAction())
			   wdlg.showPane(seq+1);
	   }else if(cmd.equals("Finish"))
	   {
		   if(nextAction())
		   {
			   wdlg.dispose();
			   wdlg.setVisible(false);
		   }
	   }	   		
   }  
   abstract public void backAction();
   abstract public boolean nextAction();   
}
