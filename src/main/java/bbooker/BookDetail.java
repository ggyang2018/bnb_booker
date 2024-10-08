package bbooker;

import java.text.SimpleDateFormat;
import java.util.*;
import nxd.GenData;

/* Room book details as atom unit object, one book one romm one customer
 * Develop independent atom objects. It is de-link table, however, serialisation
 * will serialise the subject, using Data id as linker for the link table
 * Seperate group name and number
 * Business Logic, One room one book but may be more than on clients
 * @Author Guang Yang
 */

public class BookDetail extends GenData implements Cloneable
{
	private static final long serialVersionUID = GenData.serialVersionUID;
	java.text.DecimalFormat df = new
	java.text.DecimalFormat("######.##");
	
	
	private SimpleDateFormat idFmt = new SimpleDateFormat("yyyyMMdd");
	//for one room, one book, clients
	//String code  bookId; //sorted roomId::yyyyMMdd-yyyyMMdd
	String roomId; //Foreign key
	String custId; //foreign key, as primary client 
	List<String> custIds; //as secondary client
	String prc[] = { "User Input", "Per Room", "Per Person" };
	//room features
	String roomName;
	String roomType;
	String roomStatus; //bookStatus;
	String roomPrice="0.0";; //booking price
    String roomDiscount="0.0"; //Percentage discount
    String roomDiscountCause; //children, regular discount, or block discount
	String additionFee="0.0";;
	String additionFeeCause;
	String roomDeposite="0.0";;
	String roomTotalFee="0.0";
	String roomTotalDiscount = "0.0";
	String roomPayMethod; //individaul or group
	String priceBasis;//Per Room, Per Person, User Input
    
	//Book Features
	String bookMethod;
	String bookStatus; //Confirmed, provisional.
	String startDate;
	String nightNbr="0";;
	String endDate;
	String bookDate;
	String booker;
	String note;
	String arriveTime;
	String calMethod;
	String adultNbr;
	String childNbr;
	long groupId;
			
	public BookDetail( ) 
	{ 
		super("dumy", 0);
		roomStatus = new String("free");
		custIds = new ArrayList<String>();
	}
	//general i
	public String getBookId() { return getCode(); }
	public String getRoomId() { return check(roomId); }
	public String getCustId() { return check(custId); }
	public String getSecondaryCust(int pos) { return custIds.get(pos);}
	public List<String> getSecondaryCusts() { return custIds; }
	public long getGroupId() { return groupId; }
	
	public void setBookId(String s) { setCode(s); }
	public void setRoomId(String s) { roomId = s; }
	public void setCustId(String s) { custId = s; }
	public void addSecondaryCust(String cId) { custIds.add(cId); }
	public void setGroupId(long gid) { groupId = gid; }
	
	//room properties
	public String getRoomName() { return check(roomName); }
	public String getRoomType() { return check(roomType); }
	public String getRoomStatus() { return check(roomStatus); }
	public String getRoomPrice() { return check(roomPrice); }
	public String getRoomDiscount() { return check(roomDiscount); } 
	public String getRoomDiscountCause() { return check(roomDiscountCause); }
	public String getAdditionFee() { return check(additionFee); }
	public String getAdditionFeeCause() { return check(additionFeeCause); }
	public String getRoomDeposite() { return check(roomDeposite); }
	public String getRoomTotalFee() { return check(roomTotalFee); }
	public String getRoomTotalDiscount() { return check(roomTotalDiscount); }
	public String getRoomPayMethod() { return check(roomPayMethod); }
	
	public String getChildNbr() { return check(childNbr); }
	public String getAdultNbr() { return check(adultNbr); }
	public String getPeople()
	{
		try
		{
			int nbr1 = Integer.parseInt(adultNbr);
			nbr1 = nbr1 + Integer.parseInt(childNbr);
			return Integer.toString(nbr1);
		}catch(Exception ex) { return Integer.toString(0); } 
	}
		
	
	
	public void setRoomName(String s) { roomName = s; }
	public void setRoomType(String s) { roomType = s; }
	public void setRoomStatus(String s) { roomStatus = s; }
	public void setRoomPrice(String s) { roomPrice = s; }
	public void setRoomDiscount(String s) { roomDiscount = s; } 
	public void setRoomDiscountCause(String s) {roomDiscountCause = s; }
	public void setAdditionFee(String s) { additionFee = s; }
	public void setAdditionFeeCause(String s) { additionFeeCause = s; }
	public void setRoomDeposite(String s) { roomDeposite = s; }
	public void setRoomTotalFee(String s) { roomTotalFee = s; }
	public void setRoomPayMethod(String s) { roomPayMethod = s; }
	public void setComputMethod(String s) { calMethod = s; }
	//book featuers
	public String getBookMethod() { return check(bookMethod); }
	public String getBookStatus() { return check(bookStatus); }
	public String getStartDate() { return check(startDate); }
	public String getEndDate() { return check(endDate); }
	public String getBookDate() { return check(bookDate); }
	public String getBooker() { return check(booker); }
	public String getNote() { return  check(note); }
	public String getArriveTime() { return check(arriveTime); }
	public String getComputeMethod() { return calMethod; }
	public String getPriceBasis() { return priceBasis; }

	
	public void setBookMethod(String s) { bookMethod =s; }
	public void setBookStatus(String s) { bookStatus = s; }
	public void setStartDate(String s) { startDate = s; }
	public void setEndDate(String s) { endDate = s; }
	public void setNote(String s) { note = s; }
	public void setBookDate(String s) { bookDate = s; }
	public void setBooker(String s) { booker =s; }
	public void setArriveTime(String s) { arriveTime = s; }
	public void setAdultNbr(String s) { adultNbr = s; }
	public void setChildNbr(String s) { childNbr=s; }
	public void setPriceBasis(String s) { priceBasis = s; }
	
	//meta
	public void setNightNbr(String s) { nightNbr = s; }
	public String getNightNbr() { return nightNbr; }
	public String getNbrNight()
	{
		long r = 0;
		try
		{
		   Calendar ca1 = Calendar.getInstance();
		   Calendar ca2 = Calendar.getInstance();
		   ca1.setTime(idFmt.parse(startDate));
		   ca2.setTime(idFmt.parse(endDate));
		   long m1 = ca1.getTimeInMillis();
		   long m2 = ca2.getTimeInMillis();
		   long df = m2-m1;
		   r = (df/(24*60*60*1000));		   
		}catch(Exception ex) { r=0; }
		return Long.toString(r);		
	}
	
	protected void calculateRoomCost(int mode)
	{
	  StringBuffer sbf = new StringBuffer();
	  try
	  {
		  double totalFee= 0.0;
		  if(mode==1)
		  {
			  totalFee = toDouble(roomPrice) * toInteger(nightNbr);
		  }else if(mode==0)
		  {
			  totalFee = toDouble(roomPrice) * toInteger(nightNbr);
			  totalFee = totalFee * toInteger(adultNbr);//adult fee
			  double childFee = toDouble(roomPrice)*toInteger(nightNbr);
			  childFee = childFee * toInteger(childNbr);
			  totalFee = totalFee +childFee;
		  }
		  try
		  {
			 totalFee = df.parse(df.format(totalFee)).doubleValue();
		  }catch(Exception ex){ex.printStackTrace(); }
		  
		  roomTotalFee = Double.toString(totalFee);
		  sbf.append("Room: "+roomName);
		  sbf.append("     Type: "+roomType);
		  if(mode==1)
		  {
			  sbf.append("     Price/room: "+roomPrice);
			  sbf.append("\nnigths: "+nightNbr+"      ");
			  sbf.append("Room Fee: "+roomTotalFee+"\n\n");
		  }else if(mode==0)
		  {
			  sbf.append("     Price/person: "+roomPrice);
			  sbf.append("\nnigths: "+nightNbr+"      ");
			  sbf.append("room Fee: "+roomTotalFee+"\n\n");
		  }else
		  {
			roomTotalFee = "0.0";
			sbf.append("\nTotal Fee: input by a user\n");
		  }		  
		  note = sbf.toString();
	  }catch(Exception ex) {note = ex.toString();}
	}
	//room discount should 
	public String compute(int discntNbr) //per room =-1, other discount nbr and per person
	{
	   String rst = "0.0";
	   int ngt = toInteger(getNbrNight());
	   if(ngt<=0) return rst;
	   double pr = toDouble(roomPrice); //roomPrice could be per person 
	   if(pr <1) return rst;
	   pr = pr *ngt;
	   if(discntNbr>=0) 
	   {
		  int pp = 1+custIds.size()-discntNbr;
		  pr = pr * pp;
		  double df = pr *discntNbr*(1-toDouble(roomDiscount)); //discount value 
		  pr = pr+df;	
	   }   
	   pr = pr+toDouble(additionFee)-toDouble(roomDeposite);		
	   rst = Double.toString(pr);
	   roomTotalFee = rst;
	   return rst;
	}

	public Object clone()
    {         
	   try 
	   {  return super.clone(); } 
	   catch( CloneNotSupportedException e ) 
	   {  e.printStackTrace(); return null; }
    }
	
	public double toDouble(String s)
	{
		try 
		{  
			double pr = Double.parseDouble(s.trim());
			return pr;
		}catch(Exception ex) { return 0.0d; }		
	}
	
	public int toInteger(String s)
	{
		try 
		{  
			int pr = Integer.parseInt(s.trim());
			return pr;
		}catch(Exception ex) { return 0; }		
	}
	public int getBasisMode()
	{
		if(priceBasis.equals(prc[0])) return 0;
		else if(priceBasis.equals(prc[1])) return 1;
		else return 2;
	}	

}
