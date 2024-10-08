package bbooker;

/*
 * DBDataBusImp will implement all module adaptor for plugin 
 * @Author GY
 */
import java.util.*;

import slklogin.*;
import nxd.*;

public class DBDataBusImp extends DBDataBus<DSXMLBuzMap> implements LoginAdpt
{
	protected String keys1;
	protected LoginBuz actUsr = null;
	String mnm = "slkusermap";
	
	public DBDataBusImp()
	{
		super();
		setUserMap();
	}
	//---------- user section -------------
	protected void setUserMap()
	{		
		DSXMLBuzMap mp = new DSXMLBuzMap(mnm);
		String xsd[] = {"UserName", "Password", "Reminder", "userType"};
		mp.setSXSD(xsd);
		mp.setObjType("SLKUser");
		mp.load(mp.getObjType());
		add(mnm, mp);
		
	}
	//implement Loginadpt
	 public List<LoginBuz> getAllUser()
	 { 
		 DSXMLBuzMap mp = getT(mnm);
		 List<LoginBuz> buzs = new ArrayList<LoginBuz>();
		 List<String> bzid = mp.getBuzIds();
		 for(int i=0; i<bzid.size(); i++)
		 {
			 DSXMLBuz  bz1 = mp.find(bzid.get(i));
			 LoginBuz lbz = new LoginBuz(bz1);
			 buzs.add(lbz);		 
		 }
		 return buzs;
     }
	 public LoginBuz getUser(int pos){ return actUsr;}
	 public void setNewUser(LoginBuz us)
	 {
		 DSXMLBuzMap mp = getT(mnm);
		 us.setTgs(mp.getSXSD());
		 actUsr = us;
		 mp.add(us.getBuz());
		 mp.save();
	 }
	 public void setActiveUser(LoginBuz us){ actUsr = us;}
	 public void intentNext()//each have method to form the command chain. 
	 { }	 
	 public LoginBuz getActUser(){ return actUsr; }
}
