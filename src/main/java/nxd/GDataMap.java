package nxd;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


public class GDataMap 
{
	final String dir = "slkdata/";
	
	String tpMpName = "roomtype";
	List<String> codes;
	List<String> names;
	Map<String, GenData> omp;
	boolean savedFg = true;
	int mode = 0; 
	
	public GDataMap()
	{
		codes = new ArrayList<String>();
		names = new ArrayList<String>();
		omp = new HashMap<String, GenData>();
	}
	//purly for update user
	public void setSavedFg(boolean saved) { savedFg = saved;} 
	
	public void add(GenData dt)
	{
		String bzid = dt.getCode();
		if(codes.contains(bzid))
		{
			omp.remove(bzid);
			omp.put(bzid, dt);
		}else
		{
			codes.add(bzid);
			names.add(dt.getName());
			omp.put(bzid, dt);
		}
		savedFg = false;
	}
	
	public void add(int pos, GenData dt)
	{
		String bzid = dt.getCode();
		if(codes.contains(bzid))
		{
			omp.remove(bzid);
			omp.put(bzid, dt);
		}else
		{
			codes.add(pos, bzid);
			names.add(pos, dt.getName());
		}
		savedFg = false;
		
	}
	
	public void rm(String k)
	{
		if(codes.contains(k))
		{
			int idx = codes.indexOf(k);
			rm(idx);
		}
	}
	public void rm(int idx)
	{
		String k = codes.get(idx);
		omp.remove(k);
		codes.remove(idx);
		names.remove(idx);
		savedFg = false;
	}
	
	public GenData find(String id1)
	{
		if(omp.containsKey(id1))
			return omp.get(id1);
		else 
			return null;
	}
	
	public GenData find(int idx)
	{
		if(idx<0 && idx >= codes.size()) return null;
		return omp.get(codes.get(idx));
	}
	
	public GenData findByName(String nm)
	{
	    int idx1 = indexOfName(nm);
	    return find(idx1);
	}
	
	public int indexOfName(String nm)
	{
		if(names.contains(nm))
			return names.indexOf(nm);
		else
			return -1;
	}
	
	public int indexOfCode(String cd)
	{
		if(codes.contains(cd))
			return codes.indexOf(cd);
		else
			return -1;
	}
    
	public List<String> getCodes() { return codes; }
	public List<String> getNames(){ return names; }
	public Map<String, GenData> getMap() { return omp; }
	public int getLastNbr() { return codes.size(); }
	public List<GenData> getAll()
	{
		List<GenData> lst = new ArrayList<GenData>();
		for(int i=0; i<codes.size(); i++)
		{
			GenData gd = omp.get(codes.get(i));
			lst.add(gd);
		}		
		return lst;
	}
	
	@SuppressWarnings("unchecked")
	public void loadMap(String fnm)
	{
	    try
	    {
	       FileInputStream fis = new FileInputStream(fnm);
	       ObjectInputStream ios = new ObjectInputStream(fis);
	       codes = (List<String>)ios.readObject();
	       names = (List<String>)ios.readObject();
	       omp = (Map<String, GenData>)ios.readObject();
	       ios.close();
	    }catch(Exception ex) { ex.printStackTrace(); }	
	
	}
	
	public void saveMap(String fnm)
	{
		if(savedFg) return;
		try
		{
			FileOutputStream fos = new FileOutputStream(fnm);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(codes);
			oos.writeObject(names);
			oos.writeObject(omp);
			oos.flush();
			oos.close();
			savedFg = true;
		}catch(Exception ex) { ex.printStackTrace(); }		
	}
	
	public void sortByNames()
	{
		Map<String, String> nmCds = new HashMap<String, String>();
		for(int i=0; i<names.size(); i++)
			nmCds.put(names.get(i), codes.get(i));
		Collections.sort(names);
		codes.clear();
		for(int i=0; i<names.size(); i++)
			codes.add(nmCds.get(names.get(i)));
		
	}
}
