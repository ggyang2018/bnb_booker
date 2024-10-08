package nxd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/*DS manager operating on map save and load
 * It serves as base class for other bespoke management.
 * NXDS native xml database manager could more than one table, and indexed
 * dbName could be a directory or package name
 * @author GY
 */

public class DSManager 
{
   protected Map<String, DSXMLBuzMap> nxds; //native xml database system
   protected List<String> nks; //key
   protected String dbName;
   
   public DSManager(String dbnm) 
   { 
	   dbName = dbnm;
	   nxds = new HashMap<String, DSXMLBuzMap>();
	   nks = new ArrayList<String>();
   }
   //---------- Map Level -------------------
   public void add(DSXMLBuzMap mp) //if exist update
   {
	   String k1 = mp.getMapName();
	   if(nks.contains(k1)) 
	   {
		   nxds.remove(k1);
		   nxds.put(mp.getMapName(), mp);
	   }else
	   {
		  nks.add(k1);
		  nxds.put(k1, mp);
	   }		   
   }
   
   public void rm(String k1)
   {
	   if(nks.contains(k1))
	   {
		  nks.remove(k1);
		  nxds.remove(k1);
	   }
   }
   
   public DSXMLBuzMap find(String k1)
   {
	   if(nks.contains(k1))
		   return nxds.get(k1);
	   else
		   return null;
   }
   //---------- object level --------------------------
   public void add(String mpnm, DSXMLBuz u1)
   {
	  if(nks.contains(mpnm))
	  {
		  nxds.get(mpnm).add(u1);
	  }else
	  {
		  DSXMLBuzMap mp = new DSXMLBuzMap(mpnm);
		  mp.add(u1);
		  add(mp);
	  }
   }
	   
   public void rm(String mpnm, DSXMLBuz u1)
   { 
	   if(nks.contains(mpnm))
		 nxds.get(mpnm).rm(u1.getBuzID());
	
	}
	   
   public DSXMLBuz find(String mpnm, String oid)
   { 	return nxds.get(mpnm).find(oid);}
	   
   public DSXMLBuz find(String mpnm, int pos) 
   { 
	   DSXMLBuzMap mp1 = find(mpnm);
	   if(mp1 !=null)
	     return mp1.find(pos);
	   else
		   return null;
	}
   	  
   
}
