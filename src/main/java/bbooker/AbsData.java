package bbooker;

import java.util.List;
import nxd.DSXMLBuz;

public abstract class AbsData 
{
	String tgs[];
	String xmlType; //as type id;
	String mapName;
	
	String id, name;
	int seriousNo=-1;
	
	public int getSeriouNo() { return seriousNo; }
	public void setId(String id) { this.id = check(id); }
	public void setName(String nm) { name = check(nm); }
	public String getId() { return check(id); }
	public String getName() { return check(name); }
	
	public String[] getTags() { return tgs; }
	public String getXMLType() { return check(xmlType); }
	public String getMapName() { return check(mapName); }
	
	public void setValue(DSXMLBuz buz, String s, int idx)
	{ 
		if(buz.getVals().size()>idx)
			buz.getVals().set(idx, s);
		else
			buz.getVals().add(s);
	}
	
	public void setTgs(DSXMLBuz buz, List<String> tgs)
	{
		buz.clearTags();
	    buz.setTags(tgs);	
	}
	protected String check(String s)
	{  
	   String rs = s==null ? new String() : s.trim();
	   return rs;
	}
	
	abstract public DSXMLBuz transfer();
    abstract public void transfer(DSXMLBuz buz);
    
}
