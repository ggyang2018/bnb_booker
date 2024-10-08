package nxd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class ODBBuzMap 
{
	String path;
	String mpName;
	protected List<String> codes; //object is as key to map.
	protected List<String> names;
	protected Map<String, ODBBuz> omp;

	
	public ODBBuzMap(String mpName, String path)
	{
		this.mpName = mpName;
		this.path = path;
		codes = new ArrayList<String>();
		names = new ArrayList<String>();
		omp = new HashMap<String, ODBBuz>();
	}
	public void setDir(String s) { path = s;}
	public String getDir() { return path; }
	public String getMapName() { return mpName; }
	public List<String> getCodes() { return codes; }
	public List<String> getNames() { return names; }
	public Map<String, ODBBuz> getMap() { return omp; }
	public void add(ODBBuz b1)
	{
		String bzid = b1.getCode();
		if(codes.contains(bzid))
		{
			omp.remove(bzid);
			omp.put(bzid, b1);
		}else
		{
			codes.add(bzid);
			names.add(b1.getName());
			omp.put(bzid, b1);
		}
	}
		
	public void rm(String k)
	{
		if(codes.contains(k))
		{
			int idx = codes.indexOf(k);
			omp.remove(k);
			codes.remove(k);
			names.remove(idx);
		}
	}
			
	public ODBBuz find(String id1)
	{
		if(omp.containsKey(id1))
			return omp.get(id1);
		else 
			return null;
	}

	public ODBBuz find(int idx)
	{
		String k = codes.get(idx);
		return find(k);
	}
				
	public void save()
	{
	   try
	   {
		   path = path+mpName+".dat";
		   FileOutputStream fos = new FileOutputStream(path);
		   ObjectOutputStream oos = new ObjectOutputStream(fos);
		   oos.writeObject(codes);
		   oos.writeObject(names);
		   oos.writeObject(omp);
		   oos.flush();
		   oos.close();
	   }catch(Exception ex) { ex.printStackTrace(); }		
	}
		
	@SuppressWarnings("unchecked")
	public void load()
	{
	   try
	   {
		  path = path+mpName+".dat";
		  FileInputStream fis = new FileInputStream(path);
		  ObjectInputStream ios = new ObjectInputStream(fis);
		  codes = (List<String>)ios.readObject();
		  names = (List<String>)ios.readObject();
		  omp = (Map<String, ODBBuz>)ios.readObject();
		  ios.close();
	   }catch(Exception ex) { ex.printStackTrace(); }	
	}
		
	public List<ODBBuz> findAll()
	{
		if(codes.size()<1) return null;
		List<ODBBuz> uss = new ArrayList<ODBBuz>();
		for(int i=0; i<codes.size(); i++)
		{
			ODBBuz b1 = omp.get(codes.get(i));
			uss.add(b1);		   
		}
		return uss;
	}
		
	public void debug()
	{
		for(int i=0; i<codes.size(); i++)
		{
			ODBBuz dbz = omp.get(codes.get(i));
			System.out.println(dbz.getCode());
			System.out.println(dbz.getName());
			System.out.println(dbz.getVals().toString());
		}
	}
}
