package bbooker;

/*
 * Similar to C++ it is data bus for extension
 * @Author GY
 */
import java.util.*;

public class DBDataBus<T> 
{
	
	protected Map<String, T> omp;
	protected List<String> keys, lst;
	
	public DBDataBus()
	{
		omp = new HashMap<String, T>();
		keys = new ArrayList<String>();
		lst = new ArrayList<String>();
	}
	
	public void add(String k, T v)
	{
		lst.add(k);
		String k1 = k.toLowerCase();
		keys.add(k1);
		omp.put(k1, v);		
	}
	
	public T getT(String k)
	{
		String k1 = k.toLowerCase();
		return omp.get(k1);		
	}
	//following non-type methods
	public String getKey(int pos) { return keys.get(pos); }
	public List<String> getKey() { return keys; }
	public String getList(int pos) { return lst.get(pos); }
	public List<String> getList() { return lst; }	

}
