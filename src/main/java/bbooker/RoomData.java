/*Room data contains all room and its associate with type defintion
 * @Author GY
 */
package bbooker;
import nxd.GenData;

public class RoomData extends GenData
{
	private static final long serialVersionUID = GenData.serialVersionUID;
	
	private String roomTypeId;
	private String roomTypeDesc;
	private Double price;
	private String status; //free, expect, confirmed, taken all lowcase 
	private Integer occupants; //a integer string as placehold
	private Integer adultNbr;
	private Integer childNbr;
	
	public RoomData(int nbr)
	{
	  super("room", nbr);
	  price = new Double(35.5);
	  status = new String("free");
	}	
	public void setRoomTypeId(String s) { roomTypeId = check(s); }	
	public void setPrice(Double s) {price = s; }
	public void setPrice(double dg){price = new Double(dg);}
	public void setStatus(String s) {  status =s; }	
	public void setRoomTypeDesc(String s) { roomTypeDesc=check(s);}
	public void setAdultNbr(Integer s) { adultNbr =s;}
	public void setChildNbr(Integer s) { childNbr =s;}
	
	public String getRoomTypeId() { return check(roomTypeId); }
	public String getRoomTypeDesc() { return check(roomTypeDesc);}
	public Double getPrice() 
	{
		if(price==null) price = new Double(25.5);
		return price; 
	}
	public String getStatus() { return status; }
	public Integer getAdultNbr() { return adultNbr; }
	public Integer getChildNbr() { return childNbr; }
	
	
	public void setOccupants(Integer s) { occupants = s; }
	public Integer getOccupants() { return occupants; }
	
	public String toString()
	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(super.toString());
		sbf.append("Room Type Id:"+roomTypeId);
		sbf.append("\nPrice: "+price.toString());
		sbf.append("\nStatus:"+status);
		return sbf.toString();
	}
	
	
}