package slklogin;

/* Concrete business object is adopt the decorating design pattern 
 * It is a decorator of DSXMLBuz object. One decorate one business object
 * It also works as binding or convert
 * @author GY 
 */

import nxd.*;
import toolkit.JiaMiQin;
import java.util.List;

public class LoginBuz 
{
	protected byte[] pwdBytes;
	//String tgs[] ={"UserId", "Password", "Reminder", "UserType" };
	
	protected DSXMLBuz buz;
		
	public LoginBuz(DSXMLBuz bz){ buz = bz;	}
	
	public DSXMLBuz getBuz() { return buz; } 
	
	public String getType() { return buz.getTypeId(); }
	
	public String getUserid(){ return buz.getVals().get(0);}
	public String getPasswd(){ return buz.getVals().get(1);}
	public String getPwdReminder(){ return buz.getVals().get(2);}
	public String getUserTye(){ return buz.getVals().get(3); }
	public byte[] getPwdBytes(){ return pwdBytes;}
	
	public void setUserid(String s) //include updating
	{ 
		if(buz.getVals().size()>0)
			buz.getVals().set(0, s);
		else
			buz.getVals().add(s);
	}
	public void setPasswd(String s)
	{ 
		if(buz.getVals().size()>1)
			buz.getVals().set(1, s);
		else
			buz.getVals().add(s);
	}
	
	
	public void setPwdReminder(String s)
	{ 
		if(buz.getVals().size()>2)
			buz.getVals().set(2, s);
		else
			buz.getVals().add(s);
	}
	
	public void setUserType(String s)
	{ 
		if(buz.getVals().size()>3)
			buz.getVals().set(3, s);
		else
			buz.getVals().add(s);
	}
	
	
	public void setPwdBytes(byte[] bs)
	{
		int len = bs.length;
		pwdBytes = new byte[len];
		System.arraycopy(bs, 0, pwdBytes, 0, len);
	}
	
	public void setPwdBytes(String pwd, String key)
	{
		setPwdBytes(pwd.getBytes());
		JiaMiQin jm1 = new JiaMiQin();
		jm1.zhuShiHao(key.getBytes());
		jm1.jiaMi(pwdBytes);
	}
	
	public String getPasswd(String key)
	{
		byte ppwd[] = new byte[pwdBytes.length];
		System.arraycopy(pwdBytes, 0, ppwd, 0, pwdBytes.length);
		JiaMiQin jm1 = new JiaMiQin();
		jm1.zhuShiHao(key.getBytes());
		jm1.jiaMi(ppwd);
		return new String(ppwd);
	}	
	
	public void setTgs(List<String> tgs)
	{
		buz.clearTags();
	    buz.setTags(tgs);	
	}				

}
