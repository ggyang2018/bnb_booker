package bbooker;
/*Book Wizard dialog
 * @Author Guang Yang
 */

import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;

import guiwidget.*;

public class BKWizardDlg extends GWizardDlg
{
   static public final long  serialVersionUID = GWizardDlg.serialVersionUID;
   BKFrame  frm;
   int monthx, yearx;
   Desktop desktop;	
   List<BookDetail> books;
   List<SLKCustomer> custs;
   //SLKCustomer cust;
   int mode1;
   private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
   private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
   
   public BKWizardDlg(BKFrame frm, String tit, int mod)
   {
	  super(frm, tit);
	  this.frm = frm;
	  mode1 = mod;
	  books = new ArrayList<BookDetail>();
	  custs = new ArrayList<SLKCustomer>();
   }
   
   public void initWizardDlg()
   {
	   BKWizardPane0 wp0 = new BKWizardPane0(this);
	   addWizarPane(wp0);
	   BKWizardPane1 wp1 = new BKWizardPane1(this);
	   addWizarPane(wp1);
	   BKWizardPane2 wp2 = new BKWizardPane2(this);
	   addWizarPane(wp2);
	   BKWizardPaneX wp3 = new BKWizardPaneX(this);
	   addWizarPane(wp3);
   }
   //if useing update: get first then update
   public void setCurrentMonthYear(int mt, int yr){monthx=mt; yearx=yr;}
   public void addBook(BookDetail bk) 
   {
	   if(getBook(bk.getCode())==null)		   
		   books.add(bk);
   }
   public BookDetail getBook(int idx) { return books.get(idx); }
   public BookDetail getBook(String bid)
   {
	   for(int i=0; i<books.size(); i++)
	   {
		  BookDetail bkx = books.get(i);
		  if(bid.equals(bkx.getCode()))
			  return bkx;
	   }
	   return null;
   }
   public List<BookDetail> getBooks() { return books;}
   public void addCust(SLKCustomer cst)
   { 
	   if(getCust(cst.getCode())==null)
		   custs.add(cst);
   }
   public SLKCustomer getCust(int idx) { return custs.get(idx);}
   public SLKCustomer getCust(String cid)
   {
	   for(int i=0; i<custs.size(); i++)
	   {
		  SLKCustomer csx = custs.get(i);
		  if(cid.equals(csx.getCode()))
			  return csx;
	   }
	   return null;
   }
   public List<SLKCustomer> getCusts() { return custs; }
   
   
   public void errorMsg(String err)
   {
	   String msg1 = err+"\nPlease make changes or quit!";
	   JOptionPane.showMessageDialog(this, msg1, "alert", 
			   JOptionPane.ERROR_MESSAGE);
   }
   //internal operation
   public BookDetail getBookByRoom(String roomName)
   {
	   BookDetail bkx = null;
	   for(int i=0; i<books.size(); i++)
	   {
		   bkx = books.get(i);
		   if(bkx.getRoomName().equals(roomName))
		    return bkx; 
	   }
	   return null;
   }
   
   public BookDetail getBookByCustId(String cid)
   {
	   BookDetail bkx = null;
	   for(int i=0; i<books.size(); i++)
	   {
		   bkx = books.get(i);
		   if(bkx.getCustId().equals(cid))
		    return bkx; 
	   }
	   return null;
   }
   
   protected String formatChange(String dts, boolean id_dt)
	{
	   String rt = null;	
	   try
	   {
		  if(id_dt)
		  {
			 Date dt = idFmt.parse(dts.trim());
		     rt = dateFmt.format(dt);	 
		  }else
		  {
			  Date dt1 = dateFmt.parse(dts.trim());
			  rt = idFmt.format(dt1);
		  }
	   }catch(Exception ex) 
	   {System.out.println("ReportDlg317-err: "+ex.toString()); rt=null;}
	   return rt;
	}
   
}
