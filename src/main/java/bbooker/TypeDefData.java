package bbooker;

import nxd.*;

/*Type definition property object, tag always low case for android
 * @Author Guang ID: = type_serious
 */
public class TypeDefData extends GenData
{
	private static final long serialVersionUID = GenData.serialVersionUID;
	
	String  beds, tv, ensuit, teaMk, equip, desc, refPrice;
	public TypeDefData(int no)
	{ 
	   super("roomType", no);
	   setName("roomType");
	}
	
	public void setTV(String s) { tv = check(s);}
	public void setBeds(String s) { beds = check(s);}
	public void setEnsuit(String s) { ensuit = check(s);}
	public void setTeaMk(String s) { teaMk = check(s);}
	public void setEquip(String s) { equip = check(s);}
	public void setDesc(String s) { desc = check(s);}
	public void setRefPrice(String s) { refPrice = check(s);}
	
	public String getTV() { return check(tv); }
	public String getBeds() { return check(beds);}
	public String getEnsuit() { return check(ensuit);}
	public String getTeaMk() { return check(teaMk);}
	public String getEquip() { return check(equip);}
	public String getDesc() { return check(desc);}
	public String getRefPrice() { return check(refPrice);}	
	
	public String toString()
	{
		StringBuffer sbf = new StringBuffer();
		sbf.append("Beds: "+beds);
		sbf.append("\nTV: "+tv); 
		sbf.append("\nEnsuite: "+ensuit);
		sbf.append("\nTea/Coffee Maker: "+teaMk);
		sbf.append("\nEquipments: "+equip);
		sbf.append("\nOther Description: "+desc);
		//sbf.append("\nRef Price: "+refPrice);		
		return sbf.toString();
	}
}
