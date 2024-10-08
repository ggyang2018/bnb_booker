package nxd;
/* Data source abstract data fro all data should be implemented
 * @Author Guang Yang
 */
import java.util.*;


public abstract class DSAbsData 
{
	String id, name; //id = first word of name_timevalue; 
	long seriousNo=-1;
	
	public long getSeriouNo() { return seriousNo; }
	public void setName(String nm) 
	{ 
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		id = new String(tk.nextToken());
		Date td = new Date();
		seriousNo = td.getTime();
		id = id+Long.toString(seriousNo);		
	}
	
	public void setName(String nm, int nbr)
	{
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		id = new String(tk.nextToken());
		seriousNo = (long)nbr;
		id = id+Long.toString(seriousNo);
	}
	
	public String getId() { return check(id); }
	public String getName() { return check(name); }
	
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
