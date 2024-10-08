package bbooker;
/*As primary cuostmer accompany for input if needs
 * @Author Guang Yang
 */
import java.io.Serializable;

public class SLKCustomerX implements Serializable 
{
	private static final long serialVersionUID = SLKCustomer.serialVersionUID;
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
	   //bespoke properties
	String dietReq = new String(" "); //n/a--no meal, no--requirement,  
	
	public String getSurname ( ) { return surname; }
	public String getForename ( ) { return forename; }
	public String getTelephone ( ) { return  telephone; }
	public String getMobile ( ) { return mobile; }
	public String getEmail ( ) { return email; }  
	public String getCountry ( ) { return country; }
	public String getPostcode ( ) { return postcode; }
	public String getAddress1 ( ) { return address1; }
	public String getAddress2 ( ) { return address2; }
	public String getAddress3 ( ) { return address3; }
	public String getDietRequirement ( ) { return dietReq; }
	   
	public void setSurame (String s ) { surname =s; }
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
}
