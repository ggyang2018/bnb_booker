package bbooker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import nxd.DBDataBus;
import nxd.GDataMap;

/*As Backbone data bus can be accessed by all components
 * @Author Guang Yang
 */
public class BookBus  extends DBDataBus<GDataMap>
{
	private SimpleDateFormat dateFmt = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	
    final String dir = "slkdata/";
	String tpMpName = "RoomTypeDef";
	String roomName = "RoomDesc";
	String custName ="CRPName";
	String bookName ="Booking";
		
	List<String> bookStrs;//sorted roomId::yyyyMMdd-yyyyMMdd::bookStatus
    BKFrame frm;
	public BookBus(BKFrame frm)
	{	
		super();
		this.frm = frm;
	    bookStrs = new ArrayList<String>();
		loadMaps();		
	}
	
	public void loadMaps()
	{	
	   try
	   {
		   GDataMap typeMap = new GDataMap();
		   typeMap.loadMap(dir+tpMpName);
		   add(tpMpName, typeMap);
		
		   GDataMap roomMap = new GDataMap();
		   roomMap.loadMap(dir+roomName);
		   add(roomName, roomMap);
		
		   GDataMap custMap = new GDataMap();
		   custMap.loadMap(dir+custName);
		   add(custName, custMap);
		
		   GDataMap bookMap = new GDataMap();
		   bookMap.loadMap(dir+bookName);
		   add(bookName, bookMap);
		   updateBookStrs();
	   }catch(Exception ex) { System.out.println(ex.toString()); }
	}
	
	public GDataMap getTypeMap(){return getT(tpMpName);}
	public void saveTypeMap() { getTypeMap().saveMap(dir+tpMpName);}
	
	public GDataMap getRoomMap(){return getT(roomName);}
	public void saveRoomMap() { getRoomMap().saveMap(dir+roomName);}
	
	public GDataMap getCustMap(){return getT(custName);}
	public void saveCustMap() { getCustMap().saveMap(dir+custName);}
	
	public GDataMap getBookMap(){return getT(bookName);}
	public void saveBookMap() { getBookMap().saveMap(dir+bookName);}
	
	//sorted roomId::yyyyMMdd-yyyyMMdd::bookStatus::room_pos
	public List<String> getBookStrs(){return bookStrs; }
	
	public void updateBookStrs()	
	{
	   bookStrs.clear();
	   int cnt = -1;
	   long gid = 0;
	   for(int i=0; i<getBookMap().getCodes().size(); i++)
	   {
		  BookDetail bk1 = (BookDetail)getBookMap().find(i);
		  String k1 = bk1.getCode();
		  String str1 = k1+"::"+bk1.getBookStatus();
		  if(bk1.getGroupId()!=gid)
		  {
			  gid = bk1.getGroupId();
			  cnt++;
		  }		 			  
		   str1 = str1 +"::"+Integer.toString(cnt);
		  //String rmId = bk1.getRoomId();
		  //int rmPos = getRoomMap().indexOfCode(rmId);
		  //str1 = str1+"::"+Integer.toString(rmPos);
		  bookStrs.add(str1);
	   }
	}
		
	public String findBookDetailId(String dt, String rmid)
	{
	   dt = dt.trim();
	   List<String> cds = getBookMap().getCodes();
	   if( cds== null || cds.size()<1) return null;
	   for(int i=0; i<cds.size(); i++)
	   {
		  try
		  {
			  String es1 = cds.get(i);
			  String ss[] = separate(es1, true);
			  if(rmid.trim().compareTo(ss[0].trim())!=0) continue;
			  
			  if(dt.compareTo(ss[1].trim())<0 )continue;
			  
			  if(dt.compareTo(ss[1].trim())>=0 && dt.compareTo(ss[2].trim())<0)
				  return es1;		
		  }catch(Exception ex) {}
	   }
	   return null;
	}
	//first one primary one
	public List<SLKCustomer> getBookCusts(BookDetail bk)
	{
		List<SLKCustomer> custs = new ArrayList<SLKCustomer>();
		String cid0 = bk.getCustId();
		custs.add((SLKCustomer)getCustMap().find(cid0));
		List<String> cids = bk.getSecondaryCusts(); 
		for(int i=0; i<cids.size(); i++)
		{
		    SLKCustomer bk1 =(SLKCustomer)getCustMap().find(cids.get(i));
		    custs.add(bk1);
		}
		return custs;
	}
	
	public String getSummary(int sel[])
	{
		String spaces = new String("          ");
		StringBuffer sbf = new StringBuffer();
		String dt = frm.composeDate(sel);
		String rid = frm.composeCode(sel, true);
		BookDetail bkd1 = getSelection(dt, rid);				
		if(bkd1 != null)
		{
			sbf.append(spaces+"Dates:   "+formatChange(bkd1.getStartDate(), true));
			sbf.append("--"+formatChange(bkd1.getEndDate(), true));
			sbf.append(", "+bkd1.getNightNbr()+" nights."+spaces);
			String cid = bkd1.getCustId();
			SLKCustomer cs = (SLKCustomer)getCustMap().find(cid);
			sbf.append("Meal:   "+cs.getMealNbr());
			sbf.append(",  "+cs.getDietRequirement()+"\n");
			sbf.append("Customer:   "+cs.getForename()+" "+cs.getSurname());
			sbf.append(spaces+"Arrive Time:   "+cs.getArriveTime());		
		}
		return sbf.toString();
	}
	
	public String formatChange(String dts, boolean id_dt)
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
	//rid start end [status]
	protected String[] separate(String bookId, boolean key)
	{
		String ss[];
		if(key)	ss = new String[3];
		else ss = new String[4];
		StringTokenizer dtk = new StringTokenizer(bookId, "::");
		ss[0]= dtk.nextToken();			
		String dk1 = dtk.nextToken();
		StringTokenizer dtk1 = new StringTokenizer(dk1, "-");
		ss[1] = dtk1.nextToken();
		ss[2] = dtk1.nextToken();		
		if(!key)
			ss[3] = dtk.nextToken();
		return ss;
	}
	
	BookDetail getSelection(String dt, String rid)
	{
		String bookId1 = findBookDetailId(dt, rid);
		BookDetail bkd1 = (BookDetail)getBookMap().find(bookId1);
		return bkd1;
	}

}
