package bbooker;
import nxd.GenData;
import java.util.*;
/* This is primary customer in charge of booking.
 * @Author Guang Yang
 */
public class SLKCustomer extends GenData
{
   public static final long serialVersionUID = GenData.serialVersionUID;
   //customer common information
   String roomName; //booked room name;
   String title = new String(" ");
   String surname = new String(" "); //as name
   String forename = new String(" ");
   String telephone = new String(" ");
   String mobile = new String(" ");
   String email = new String(" ");
   String country = new String(" ");
   String postcode = new String(" ");
   String address1 = new String(" "); //house number
   String address2 = new String(" "); //street name;
   String address3 = new String(" "); //county and    
   String dietReq = new String(" "); //as meal information,   
   String specialReq;
   String addInfo;
   //temporary variable for book object
   String arriveTime, bookStatus; 
   boolean groupLeader=true, registAll = false; 
   //additional fee, mealPrice means all meal fee
   String mealPrice, mealNbr, mealFee;
   //------- payment ------
   String addFee, roomFee, invoiceNbr;
   String endAmount, endBalance;
   //-----------------------------
   List<String> bookIdsx;
   List<SLKCustomerX> partners;
            
   public SLKCustomer(int no)
   { 
	   super("cust", no);
	   bookIdsx = new ArrayList<String>();
	   partners = new ArrayList<SLKCustomerX>();
   }
   
   public SLKCustomer(String srn, int no)
   { super(srn, no); }
   
   public String capitalFirst(String s)
   {
	   if(s==null || s.length()<2) return new String();
	   String s1 = s.toLowerCase();
	   s1= s1.substring(0,1).toUpperCase()+s1.substring(1);
	   return s1;
   }
   public String getRoomName() { return check(roomName); }
   public String getTitle() { return title; }
   public String getSurname ( ) {return capitalFirst(surname); }
   public String getForename ( ) { return capitalFirst(forename); }
   public String getTelephone ( ) { return  telephone; }
   public String getMobile ( ) { return mobile; }
   public String getEmail ( ) { return email; }  
   public String getCountry ( ) { return country; }
   public String getPostcode ( ) { return postcode; }
   public String getAddress1 ( ) { return address1; }
   public String getAddress2 ( ) { return address2; }
   public String getAddress3 ( ) { return address3; }
   public String getDietRequirement ( ) { return dietReq; }
   public List<String> getBookIds(){ return bookIdsx; }
   public String getBookId(int idx) { return bookIdsx.get(idx); }
   public String getSpecialReq() { return check(specialReq); }
   public String getArriveTime() { return check(arriveTime); }
   public String getBookStatus() { return check(bookStatus); }
   public String getAddInfo() { return check(addInfo); }
   public String getInvoiceNbr() {return check(invoiceNbr); }
   public boolean isGroupLeader() { return groupLeader; }
   public boolean isRegistForAll() { return registAll; }
   public String getMealFee() { return check(mealFee); }
   
   public void setRoomName(String s) { roomName = s; }
   public void setTitle(String s) { title = s;}
   public void setSurame (String s ) { surname =s; setName(s); }
   public void setForename (String s ) {  forename =s; }
   public void setTelephone (String s ) { telephone =s; }
   public void setMobile (String s ) { mobile =s; }
   public void setEmail (String s ) { email =s; }
   public void setCountry (String s ) { country =s; }
   public void setPostcode (String s ) { postcode =s; }
   public void setAddress1 (String s ) {  address1 =s; }
   public void setAddress2 (String s ) {  address2 =s; }
   public void setAddress3 (String s ) {  address3 =s; }
   public void setDietRequirement (String s ) { dietReq =s; }  
   public void setBookIds(List<String> bids) { bookIdsx = bids; }
   public void addBookId(String bid) {bookIdsx.add(bid); }
   public void delBookId(int idx) { bookIdsx.remove(idx); }
   public void delBookId(String bid){ bookIdsx.remove(bid); }
   public void setSpecialReq(String s) { specialReq = s; }
   public void setArriveTime(String s) { arriveTime = s; }
   public void setBookStatus(String s) { bookStatus = s; }
   public void setAddInfo(String s) { addInfo = s; }
   public void setGroupLeader(boolean is) { groupLeader = is; }
   public void setRegistForAll(boolean is) { registAll = is; }
   public void setInvoiceNbr(String s){ invoiceNbr = s; }
   public void setMealFee(String s) { mealFee = s; }
      
   public String getMealPrice ( ) { return check(mealPrice); }
   public String getMealNbr ( ) { return check(mealNbr); }
   public String getAddFee ( ) { return check(addFee); }
   public String getRoomFee ( ) { return check(roomFee); }
   public String getEndAmount ( ) { return check(endAmount); }
   public String getEndBalance ( ) { return check(endBalance); }
   
   public void setMealPrice (String s ) { mealPrice =s; }
   public void setMealNbr (String s ) { mealNbr =s; }
   public void setAddFee (String s ) { addFee =s; }
   public void setRoomFee (String s ) { roomFee =s; }
   public void setEndAmount (String s ) { endAmount =s; }
   public void setEndBalance (String s ) { endBalance =s; }
   
   //store companies details
   public void addPartner(SLKCustomerX cx) { partners.add(cx);}
   public void delPartner(int idx) { partners.remove(idx); }
   public List<SLKCustomerX> getPartners() { return partners; }
   public void setPartners(List<SLKCustomerX> lst) { partners = lst; } 
   
   //
   public String getContact()
   {
	   if(mobile !=null && mobile.trim().length()>1) return mobile;
	   else if(telephone != null && telephone.trim().length()>1) return telephone;
	   else if(email!=null && email.trim().length()>1) return email;
	   else return null;
   }
   
   
 //--------- spare to make it happy--------
   String visReason;
   String groupName;
   String groupAdultNbr;
   String groupChildNbr;
   String discount;
   String payMethod;// cash, credit card and debit car, cheque, and other methods
   String deposite;
  // String total;  //total should paid
   String balance;
   String people;
   String fixPrice;
   String totalDiscnt;
   String addPay;
   String note;
	   
   //------------ ADDITIONAL FEATURES-------------------
   public String getGroupName() { return check(groupName); }
   public String getGroupAdultNbr() { return check(groupAdultNbr); }
   public String getGroupChildNbr() { return check(groupChildNbr); }
   public String getDiscount() { return check(discount); }	
   public String getVisReason() { return check(visReason); }
   public String getPayMethod() { return check(payMethod); }
   public String getDeposite() { return check(deposite); }
   public String getBalance() { return check(balance); }
   //public String getTotal() { return check(total); }
   public String getAddPay(){ return addPay; }
   public String getFixPrice(){ return fixPrice; }
   public String getNote() { return note; }
   
   public void setDeposite(String s) { deposite =s; }	
   public void setGroupName(String s) { groupName = s; }
   public void setGroupAdultNbr(String s) { groupAdultNbr = s; }
   public void setGroupChildNbr(String s) { groupChildNbr = s; }
   public void setDiscount(String s) { discount=s; }		
   public void setVisReason(String s) { visReason =s; }
   public void setPayMethod(String s) { payMethod = s; }
   public void setBalance(String s) { balance = s; }
   //public void setTotal(String s) { total =s; }
   public void setAddPay(String s) { addPay =s; }
   public void setFixPrice(String s) { fixPrice =s; }
   public void setNote(String s) { note = s; }
   //--------- get integer value------------------
   public int getGroupNum() 
   { 
	   int r=0;
	   try { r = Integer.parseInt(groupAdultNbr)+
		   Integer.parseInt(groupChildNbr);}
	   catch(Exception ex) { r = 0;}
	   return r;
   }
   public String getTotalGroupNbr()
   {  return Integer.toString(getGroupNum());}
   
   public void transferData(SLKCustomer scs)
   {
	   roomName = scs.getRoomName();
	   title = scs.getTitle();
	   surname = scs.getSurname();
	   forename = scs.getForename();
	   telephone = scs.getTelephone();
	   mobile = scs.getMobile();
	   email = scs.getEmail();
	   country = scs.getCountry();
	   postcode = scs.getPostcode();
	   address1 = scs.getAddress1(); //house number
	   address2 = scs.getAddress2(); //street name;
	   address3 = scs.getAddress3(); //county and    
	   specialReq = scs.getSpecialReq();
	   addInfo = scs.getAddInfo();
	   arriveTime  = scs.getArriveTime(); 
	   bookStatus = scs.getBookStatus();
	   groupLeader=scs.isGroupLeader(); 
	   registAll = scs.isRegistForAll(); 
	   //additional fee
	  addFee = scs.getAddFee();
	   //List<String> bookIdsx;	   
   }
}
