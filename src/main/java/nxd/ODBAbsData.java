package nxd;

import java.util.Date;
import java.util.StringTokenizer;

public abstract class ODBAbsData implements java.io.Serializable
{
	static public final long serialVersionUID = ODBBuz.serialVersionUID;
	
	String code, name; //code = first word of name_timevalue; 
	long seriousNo=-1;
	
	public long getSeriouNo() { return seriousNo; }
	public void setName(String nm) 
	{ 
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		code = new String(tk.nextToken());
		Date td = new Date();
		seriousNo = td.getTime();
		code = code+"_"+Long.toString(seriousNo);		
	}
	
	public void setName(String nm, int nbr)
	{
		name = check(nm);
		StringTokenizer tk = new StringTokenizer(name, " ");
		code = new String(tk.nextToken());
		seriousNo = (long)nbr;
		code = code+Long.toString(seriousNo);
	}
	
	public void setCode(String s) { code = s; }
	public void setPlainName(String s) { name = s; }
	public String getCoded() { return check(code); }
	public String getName() { return check(name); }
	
	public void setValue(ODBBuz buz, String s, int idx)
	{ 
		if(buz.getVals().size()>idx)
			buz.getVals().set(idx, s);
		else
			buz.getVals().add(s);
	}
	
	protected String check(String s)
	{  
	   String rs = s==null ? new String() : s.trim();
	   return rs;
	}
	
	abstract public ODBBuz transfer();
    abstract public void transfer(ODBBuz buz);    

}
