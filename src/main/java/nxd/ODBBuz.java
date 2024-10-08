package nxd;
/*Object database ODB business level objct work together with ODBBuzMap to 
 * produce easy persistent layer
 * @Author Guang Yang
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class ODBBuz implements Serializable 
{
	static public final long serialVersionUID = 123456789;	
	
	final public String EMPTY ="N/A";	
	protected List<String> vals; //field value as text value
	protected String name;
	protected String code;
	
	long seriousNo=-1;
	
	public ODBBuz() {vals = new ArrayList<String>();}
	
	public ODBBuz(String nm)
	{
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		code = new String(tk.nextToken());
		Date td = new Date();
		seriousNo = td.getTime();
		code = code+"_"+Long.toString(seriousNo);
		vals = new ArrayList<String>();			
	}
	
	public ODBBuz(String nm, int nbr)
	{
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		code = new String(tk.nextToken());
		seriousNo = (long)nbr;
		code = code+Long.toString(seriousNo);
	}
	
	public void setCode(String s) { code = check(s); }
	public void setName(String s) { name = check(s); }
	public void setVals(List<String> ss) { vals = ss; }
	
	public long getSeriouNo() { return seriousNo; }
	public String getCode() { return code; }
	public String getName() { return name; }
	public void clearVals(){ vals.clear(); }
	public List<String> getVals() { return vals; }
	
	protected String check(String s)
	{  
	   String rs = s==null ? new String() : s.trim();
	   return rs;
	}
	
	public String toString()
	{
		StringBuffer bf = new StringBuffer();
		bf.append("Code: "+code);
		bf.append("\nName: "+name+"\n");
		for(int i=0; i<vals.size(); i++)
			bf.append(vals.get(i)+"\t");
		bf.append("\n");
		return bf.toString();
	}

}
