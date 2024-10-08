package nxd;
/*General data object for other object inherent from it
 * It can be work as persistent and as memory layter
 * @Author Guang Yang
 */
import java.util.Date;
import java.util.StringTokenizer;

public class GenData implements java.io.Serializable
{
	static public final long serialVersionUID = ODBBuz.serialVersionUID;
	
	String code, name; //code = first word of name_timevalue; 
	long seriousNo=-1;
	long sequence=0;
	
	public GenData()
	{
		code = new String();
		name = new String();
	}
	
	public GenData(String name, long seq)
	{
		name = check(name);
		StringTokenizer tk = new StringTokenizer(name, " ");
		code = new String(tk.nextToken());
		Date td = new Date();
		sequence = seq;
		seriousNo = td.getTime()+seq;
		code = code+"_"+Long.toString(seriousNo);
	}
    //plain opertion for recovery	
	public void setCode(String s) { code = check(s); }
	public void setName(String nm)  {name = check(nm); }
	public void setSeriousNo(long nbr) { seriousNo = nbr; }
	public void setSequence(long seq) { sequence = seq; }
	
    public String getCode() { return check(code); }
	public String getName() { return check(name); }
	public long getSeriouNo() { return seriousNo; }
	public long getSequence() { return sequence; }
	
	protected String check(String s)
	{  
	   String rs = s==null ? new String() : s.trim();
	   return rs;
	}
	
	public String toString()
	{
		StringBuffer sbf = new StringBuffer();
		sbf.append("Code: "+code);
		sbf.append("Name: "+name+"\n");
		return sbf.toString();
	}
	

}

