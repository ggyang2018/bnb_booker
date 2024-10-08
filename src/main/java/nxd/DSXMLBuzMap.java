package nxd;

/* Structure Map --> object --> field
 * One map one file. MapId will top tag and file name without extension
 * One type of object one map.
 * @Author GY
 */
import java.util.*;


public class DSXMLBuzMap 
{
   protected String mpName; //object type and will be use for object id as map tag.
   protected List<String> ids; //object is as key to map.
   protected Map<String, DSXMLBuz> omp;
   
   protected DSXMLCtl p1; 
   //as simple xml scheme definition sxsd
   protected String sxsd[]; 
   protected String objType;
   
   public DSXMLBuzMap(String mpnm)
   {
	   mpName = mpnm;
	   ids = new ArrayList<String>(); 
	   omp = new HashMap<String, DSXMLBuz>();
	   p1 = new DSXMLCtl();
	}
	
   public void setObjType(String tp) { objType = tp; }
   public String getObjType() { return objType; }
   public void setSXSD(String ts[]) 
   { 
	   sxsd = new String[ts.length];
	   System.arraycopy(ts, 0, sxsd, 0, ts.length);
   }
   public List<String> getSXSD()
   {
	   List<String> lst = new ArrayList<String>();
	   for(int i=0; i<sxsd.length; i++)
		   lst.add(sxsd[i]);
	   return lst;
   }
	public String getMapName() { return mpName; }
	public List<String> getBuzIds() { return ids; }
	public Map<String, DSXMLBuz> getMap() { return omp; }
	public void add(DSXMLBuz b1)
	{
	   String bzid = b1.getBuzID();
	   if(ids.contains(bzid))
	   {
		  omp.remove(bzid);
		  omp.put(bzid, b1);
	   }else
	   {
		   ids.add(bzid);
		   omp.put(bzid, b1);
	   }
	}
	
	public void rm(String k)
	{
	   if(ids.contains(k))
	   {
		   omp.remove(k);
		   ids.remove(k);
	   }
	}
		
	public DSXMLBuz find(String id1)
	{
		if(omp.containsKey(id1))
			return omp.get(id1);
		else 
			return null;
	}

	public DSXMLBuz find(int idx)
	{
		String k = ids.get(idx);
		return find(k);
	}
			
	public String save(){return p1.expI(this, true);}
	
	public void load(String tp)
	{
	   String tgs[] = new String[sxsd.length+1];
	   tgs[0] = DSXMLBuz.buzIdTag;
	   for(int i=1; i<=sxsd.length; i++)
	   {
		   tgs[i] = sxsd[i-1];
		   //System.out.println(", "+tgs[i]);
	   }
	   List<DSXMLBuz> lbs = p1.load(mpName, tp, tgs);
	   if(lbs == null || lbs.size() <1)
	   {
		   System.out.println("empty lbs");
		   return;
	   }	   
	   for(int i=0; i<lbs.size(); i++)
		  add(lbs.get(i));	
	}
	
	public List<DSXMLBuz> findAll()
	{
		if(ids.size()<1) return null;
		List<DSXMLBuz> uss = new ArrayList<DSXMLBuz>();
		for(int i=0; i<ids.size(); i++)
		{
			DSXMLBuz b1 = omp.get(ids.get(i));
			uss.add(b1);		   
		}
		return uss;
	}
	
	public void debug()
	{
		for(int i=0; i<ids.size(); i++)
		{
			DSXMLBuz dbz = omp.get(ids.get(i));
			System.out.println(dbz.getBuzID());
			System.out.println(dbz.getTypeId());
			System.out.println(dbz.getTags().toString());
			System.out.println(dbz.getVals().toString());
		}
	}
}
