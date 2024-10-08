package bbooker;

import guiwidget.GWizardPane;

public class BKChangeDlg extends BKWizardDlg 
{
	static public final long  serialVersionUID = GWizardPane.serialVersionUID;
	SLKCustomer oldCust;
	BookDetail  oldBk0;  //first booker and last booker
	
    public BKChangeDlg(BKFrame frm, String tit)
    {  	super(frm, tit, 0);}
    
    public void setOldBook(SLKCustomer cstx, BookDetail bkx)
    { oldCust = cstx; oldBk0 = bkx; }
    
    public void initChangeDlg()
    {
 	   BKChangePane0 wp0 = new BKChangePane0(this);
 	   addWizarPane(wp0);
 	   BKChangePane1 wp1 = new BKChangePane1(this);
 	   addWizarPane(wp1);
 	   BKChangePane2 wp2 = new BKChangePane2(this);
 	   addWizarPane(wp2);
 	   BKChangePaneX wp3 = new BKChangePaneX(this);
 	   addWizarPane(wp3);
    }
    
    public void setOldBook()
    {
       BKChangePane0 p0 = (BKChangePane0)getWizardPane(0);
       p0.startFd.setText(p0.formatChange(oldBk0.getStartDate(), true));
       p0.endFd.setText(p0.formatChange(oldBk0.getEndDate(), true));
       p0.nightFd.setText(oldBk0.getNightNbr());
       p0.bookBx.setSelectedItem(oldBk0.getBookMethod());
       p0.payByBx.setSelectedItem(oldBk0.getRoomPayMethod());
       p0.prcBx.setSelectedItem(oldBk0.getPriceBasis());
       BKChangePane1 p1 = (BKChangePane1)getWizardPane(1);
       p1.groupFd.setText(oldCust.getGroupName());
       p1.payMtFd.setSelectedItem(oldCust.getPayMethod());
       p1.bookSttBx.setSelectedItem(oldBk0.getBookStatus());
       p1.arriveFd.setText(oldCust.getArriveTime());
       
       BKChangePane2 p2 = (BKChangePane2)getWizardPane(2);
       p2.surNameFd.setText(oldCust.getSurname());
       p2.forNameFd.setText(oldCust.getForename());
       p2.telFd.setText(oldCust.getTelephone());
       p2.mobieFd.setText(oldCust.getMobile());
       p2.emailFd.setText(oldCust.getEmail());
       p2.visitFd.setText(oldCust.getVisReason());
       p2.spRefFd.setText(oldCust.getSpecialReq());
       p2.postFd.setText(oldCust.getPostcode());
       p2.addr1Fd.setText(oldCust.getAddress1());
       p2.addr2Fd.setText(oldCust.getAddress2());
       p2.addr3Fd.setText(oldCust.getAddress3());
    }
    
    public void removeOld()
    {
    	for(int i=0; i<oldCust.getBookIds().size(); i++)
		{
		   String bidx = oldCust.getBookId(i);
		   frm.bkBus.getBookMap().rm(bidx);
		}
		frm.bkBus.getCustMap().rm(oldCust.getCode());			
	    frm.bkBus.saveBookMap();
		frm.bkBus.saveCustMap();
		frm.bkBus.updateBookStrs();			
		//frm.mainPane.freshData();
    }
}
