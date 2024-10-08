package bbooker;

import javax.swing.JOptionPane;

import guiwidget.GWizardPane;

public class BKChangePane0 extends BKWizardPane0 
{
   static public final long  serialVersionUID = GWizardPane.serialVersionUID;
   BKChangeDlg chDlg;
   public BKChangePane0(BKChangeDlg cdlg)
   {
	   super(cdlg);
	   chDlg = cdlg;
   }
   
	public boolean nextAction()
	{
		String msg2="Warning: change boooking will remove previous booking " +
		"\nfrom database permanently. It cannot undo. Are you sure?";
		int reply = JOptionPane.showConfirmDialog(this, msg2, "Confirmation", 
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.NO_OPTION) return false;
		chDlg.removeOld(); 
		return super.nextAction();
   }
}
