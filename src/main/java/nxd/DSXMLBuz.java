package nxd;
/*
 * XML rendering object contains fields. gv0 will as object id and
 * top tag of object.
 * @Author GY
 */
import java.util.ArrayList;
import java.util.List;

public class DSXMLBuz 
{
	final public String EMPTY ="N/A";	
		
	protected List<String> tags; //field name as tags
	protected List<String> vals; //field value as text value
	protected String typeId;
	protected String buzId;
	public static String buzIdTag = "BuzID";
	
	public DSXMLBuz(String tpId, String buzId)
	{
		typeId = tpId;
		this.buzId = buzId;
		tags = new ArrayList<String>();
		vals = new ArrayList<String>();
			
	}
	
	public void clearTags(){ tags.clear();  }
	public void clearVals(){ vals.clear(); }
	
	
	
	public List<String> getTags() { return tags; }
	public List<String> getVals() { return vals; }
	
	public void setTags(List<String> ss) { tags = ss; }
	public void setTags(String ts[])
	{
		for(int i=0; i<ts.length; i++)
			tags.add(ts[i]);
	}
	public void setVals(List<String> ss) { vals = ss; }
	
	//public interface called
	public String getBuzID() { return buzId; }
	public void setBuzId(String bid){ buzId = bid; }
	public String getBuzIdTag() { return buzIdTag; }
	public String getTypeId() { return typeId; }
	
	public String toString()
	{
		StringBuffer bf = new StringBuffer();
		bf.append(typeId+": ");
		for(int i=0; i<tags.size()&&i<vals.size(); i++)
			bf.append(tags.get(i)+": "+vals.get(i)+"\t");
		return bf.toString();
	}

}
