package xhtml;

/* ******************************************************
HTMLProcessor is not only for display HTML document but also 
for processing HTML document. It allow user to add his own 
processing tag allow the program to processing. The procssing
tags should different from those of HTML default tags.

It is simple plain algorithm. The <searchkey=xxxx> and </searchkey>
must stay a new line repectively. It doesn't support nested 
information and nest earch

Author GY
Date  21-01-03
************************************************************* */
import java.util.*;
import java.io.*;

public class HTMLProcessor
{ 
  	BufferedReader TplReader;
	HashMap<String, String> Mapping;

   public HTMLProcessor(String fnm)
   {
	  //for file pack with jar
	  //InputStream in=this.getClass().getResourceAsStream(FileName);
		  
		  //plan file
	   try
	   {
	     FileInputStream in = new FileInputStream(fnm);
         TplReader = new BufferedReader(new InputStreamReader(in));
	     //HTMLBuffer = new StringBuffer( );
	     //TagStack = new Stack();
		 //CntStack = new Stack();
	   }catch(Exception ex)
	   { System.out.println("Error on find file :"+ex.toString());}

   }

   public HashMap<String, String> getContext( )
   {  return Mapping;    }

 
   public void scanFile( ) throws IOException
   {
	   //Templates = new Vector();
	   Mapping = new HashMap<String, String>();
	 		  
	   String con = new String();
	   int cnt = 0;
	   do
	   {
         try
		 {
		    String s = TplReader.readLine();
		    
			if(s == null || s.length()==0)
				continue;
		
			//for simple simplicity, <a name=""> for one line
			if(s.toLowerCase().indexOf("name=") >=0 ) 
			{
				//String s1 = getSearchKey(s);
				//String key = getQuotaContext(s1);
				//String cmd = "<A HREF=\"#"+key+"\">";
                //Mapping.put(key, cmd);
			}
			con = s;
			cnt++;

		 }catch(Exception ex)
		 {
		  //System.out.println(cnt+" line error on scan:"+ex.getMessage());
		 }
		 
	   }while(!con.trim().equalsIgnoreCase("</html>"));
   }

   String getTagContext(String s) throws IOException
   {
		int bin = s.indexOf('<');
		int end = s.indexOf('>');
		 
		while(end <0)
		{
			 String sd = TplReader.readLine();
			 end = sd.indexOf('>');
			 s = s+" "+sd;
		}
		end = s.indexOf('>');
		String str = s.substring(bin+1, end);//exclude <
		return str;
	}

   String getQuotaContext(String s) throws IOException
   {
	   //must be in one line
	    int bin = s.indexOf('\"');
		int endx = s.indexOf('\"', bin+1);
		String str = s.substring(bin+1, endx);//exclude <
        
		String str1 = new String();
		int endy = str.indexOf("%"); 
		if(endy >0)
			str1 = str.substring(0, endy);
		else
			str1 = str;

		Mapping.put(str1, str);
		return str1;
	}


    //stateTag: reutn 0-start tag, 1-end tag, -1 not tag, -2 comment
	int stateTag(String s)
	{
	  if(s.indexOf("</")>=0)
	 	 return 1;
		 
	  if (s.indexOf("<--")>=0)
		return -2;
		
	  if( s.indexOf("<")>=0)
			 return 0;
		 
		 return -1;
	}

	boolean hasVariable(String str, char reg)
	{
         int in = str.indexOf(reg);
         if(in >0)
            return true;
         else
           return false;
	 }

	 protected String replace(String str, String str1, String str2)
     throws IOException
     {
        int in1 =  str.indexOf(str1);
        String tailer = str.substring(in1+str1.length());
        String begin = str.substring(0, in1);
        String s = begin+str2+tailer;
        return s;
     }

	 String getSearchKey(String s)
	 {
		 StringTokenizer tokens = new StringTokenizer(s, "=");
		 
		 String s1 = tokens.nextToken();
		 while(s1.toLowerCase().indexOf("name")<0)
			 s1 = tokens.nextToken();

		 return tokens.nextToken();
	 }

	/*ublic Vector<String> getKeyVector( )
	 {
		  Vector v = new Vector();
		  String strs[] = new String[Mapping.size()];
		  int cnt = 0;
		  Set kset = Mapping.entrySet();
		  Iterator<String> iter = kset.iterator<String>();
		  while(iter.hasNext())
		  {
			  Map.Entry entry = (Map.Entry)iter.next();
			  strs[cnt] = (String)entry.getKey();
			  cnt++;
		  }
		  if(cnt >1)
		  {
		    Arrays.sort(strs);
			for(int i=0; i<strs.length; i++)
			   v.addElement(strs[i]);
		  }

		  return v;
	  }
	  */

}