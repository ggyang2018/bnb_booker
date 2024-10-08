package nxd;
/* DSXMLMapMgr only manager one type map so all meta data should be define here
 * Work with DSAbsData is above the business layer and application layer
 * @Author Guang Yang
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class DSXMLMapMgr
{
	static public final int TYPEDEF =0;
	static public final int ROOM = 1;
	
	List<String> names, ids;
	Map<String, DSAbsData> slkmap;
	DSXMLBuzMap mp;
	String tgs[];
	String mpName;
	String xmlTp;
	
	public DSXMLMapMgr(String mpName)
	{
		this.mpName = mpName;		
		mp = new DSXMLBuzMap(mpName);
		tgs = makeTags();
		xmlTp = makeXMLType();
	}
	
	public void loadMap()
	{
		mp.setSXSD(tgs);
		mp.setObjType(xmlTp);
		//System.out.println("SLKTypeMap: xmltp="+xmlTp);
		mp.load(mp.getObjType());
	}
	public void setAllData()
	{
		names = new ArrayList<String>();
		ids = new ArrayList<String>();
		slkmap = new HashMap<String, DSAbsData>();
		List<String> bzid = mp.getBuzIds();
		for(int i=0; i<bzid.size(); i++)
		{
			DSXMLBuz  bz1 = mp.find(bzid.get(i));
            setDataObj(bz1, i);
		}
	}
	public List<String> getName_ID(int i)
	{ 
		if( i==0) return names;
	   else return ids;
	}
	
	public Map<String, DSAbsData> getMap() { return slkmap; }
	
	public void addData(DSAbsData rm)
	{
		DSXMLBuz buz = rm.transfer();
		rm.setTgs(buz, mp.getSXSD());
		mp.add(buz);
		mp.save();
	}
	public DSXMLBuzMap getMp() { return mp; }
	
	public void saveAll()
	{
		mp = new DSXMLBuzMap(mpName);
		mp.setSXSD(tgs);
		//mp.setObjType(xmlType);
		for(int i=0; i<ids.size(); i++)
		{
			DSAbsData ad1 = slkmap.get(ids.get(i));
			DSXMLBuz bz1 = ad1.transfer();
			ad1.setTgs(bz1, mp.getSXSD());
			mp.add(bz1);
		}
		mp.save();
	}
	
    //---------- support method --------------
	abstract public String[] makeTags();
	abstract public String makeXMLType();
	abstract public void setDataObj(DSXMLBuz bz1, int i);
	
	/*protected void setTypeDef(DSXMLBuz  bz1, int i)
	{
    	TypeDefData lbz= new TypeDefData(i);
    	lbz.transfer(bz1);
    	names.add(lbz.getName());
    	ids.add(lbz.getId());
    	slkmap.put(lbz.getId(), lbz);
    }
	
	protected void setRoom(DSXMLBuz  bz1, int i)
	{
    	RoomData lbz= new RoomData(i);
    	lbz.transfer(bz1);
    	names.add(lbz.getName());
    	ids.add(lbz.getId());
    	slkmap.put(lbz.getId(), lbz);
    }*/

}
