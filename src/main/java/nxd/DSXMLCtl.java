package nxd;

/* help to save data to XML file, and load data from XML file 
 * this is only working for DOM module. SAX module need to develop anthing else
 * Bespoke make it easy, work as map, one process one map paradigm 
 * @author GY   
 */
//import java.io.BufferedWriter;
import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

//import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
//import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class DSXMLCtl 
{
	static final public String xmlns1="http://www.w3.org/2000/svg";
	static final public String xmlns_xlink="http://www.w3.org/1999/xlink";
	static final String docType="http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd";
	static final String docTypePub="-//W3C//DTD SVG 1.0//EN";
	String dataDir = "slkdata\\";
	
	public DSXMLCtl(){}
	
	public String expI(DSXMLBuzMap mp, boolean isFile)
	{
	  String f1 = dataDir+mp.getMapName()+".xml"; 
	  try
	  {
		  DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
		  DocumentBuilder bd = fct.newDocumentBuilder();
		  Document d1 = bd.newDocument();
		  Element rt1 = d1.createElement(mp.getMapName());
		  List<String> ids = mp.getBuzIds();
		  for(int i=0; i<ids.size(); i++)
		  {
			  DSXMLBuz b1 = mp.getMap().get(ids.get(i));
			  rt1.appendChild(expI(d1, b1));
		  }
		  Source src = new DOMSource(rt1);
		  TransformerFactory tfct = TransformerFactory.newInstance();
		  Transformer t = tfct.newTransformer();
		  //t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, s1_1);
		  //t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, s1_2);
		  t.setOutputProperty(OutputKeys.INDENT, "yes");
		  if(isFile)
		  {
			  FileOutputStream fos = new FileOutputStream(f1);
			  t.transform(src, new StreamResult(fos));
			  fos.flush();
			  fos.close();
		  }else
		  {
			  StringWriter stringWriter = new StringWriter();
			  t.transform(src, new StreamResult(stringWriter));
			  f1 =  stringWriter.getBuffer().toString();			  
		  }				  
	  }catch(Exception ex){ex.printStackTrace(); f1 = ex.getMessage();}
	  return f1;		
	}
	
	protected Element expI(Document dd1, DSXMLBuz b1) throws Exception
	{
		Element e1 = dd1.createElement(b1.getTypeId());
		//e1.appendChild(dd1.createTextNode(b1.getBuzID()));
		Element ee1 = dd1.createElement(b1.getBuzIdTag());
		ee1.appendChild(dd1.createTextNode(b1.getBuzID()));
		e1.appendChild(ee1);
		List<String> tg1 = b1.getTags();
		List<String> tx1 = b1.getVals();
		for(int i=0; i<tg1.size()&& i<tx1.size(); i++)
		{	
			Element e11 = dd1.createElement(tg1.get(i));
			e11.appendChild(dd1.createTextNode(tx1.get(i)));
			e1.appendChild(e11);
		}
		return e1;
	}
	
	public DSXMLBuzMap impI(String tp, String tgsx[]) //object name/type only
	{
	   DSXMLBuzMap mp = new DSXMLBuzMap(tp);
	   try
	   {
		  DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
		  DocumentBuilder bd = fct.newDocumentBuilder();
		  Document d1 = bd.newDocument();
		  File file = new File(dataDir+mp.getMapName()+".xml");
		  d1 = bd.parse(file);
		  Element rt1 = d1.getDocumentElement();
		  rt1.normalize();
		  List<DSXMLBuz> lbs = elmE(tp, tgsx, rt1);
		  for(int i=0; i<lbs.size(); i++)
			  mp.add(lbs.get(i));		  		
	   }catch(Exception ex)
	   { 
		   ex.printStackTrace(); 
		   return null; 
		 }
		
	   return mp;
	}
	
	public List<DSXMLBuz> load(String mpnm, String tp, String tgsx[])
	{
		if(mpnm.equals("slkroommap"))
		{
			//System.out.println("DSXMLCtl126: "+tp);
			for(int j=0; j<tgsx.length; j++)
				System.out.print(tgsx[j]+", ");
		}
		try
		{
			DocumentBuilderFactory fct = DocumentBuilderFactory.newInstance();
			DocumentBuilder bd = fct.newDocumentBuilder();
			Document d1 = bd.newDocument();
			String fnm = dataDir+mpnm+".xml";
			File file = new File(fnm);	
			d1 = bd.parse(file);
			Element rt1 = d1.getDocumentElement();
			rt1.normalize();
			List<DSXMLBuz> lbs = elmE(tp, tgsx, rt1);
			return lbs;
		}catch(Exception ex)
		{  	ex.printStackTrace(); 
			return null; 
		}		
	}
	
	protected List<DSXMLBuz> elmE(String desc, String tgsx[], Element rt1) throws Exception
	{
		List<DSXMLBuz> ls = new ArrayList<DSXMLBuz>();
		NodeList nl= rt1.getElementsByTagName(desc);
		for (int i = 0; i < nl.getLength(); i++) 
		{
		    Node fstNode = nl.item(i);
		    if (fstNode.getNodeType() == Node.ELEMENT_NODE) 
		    {
		       DSXMLBuz us1 = new DSXMLBuz(desc, "anyone");
		       Element ee1 = (Element) fstNode;
		       for(int j=0; j<tgsx.length; j++)
		       {
		    	   NodeList ids = ee1.getElementsByTagName(tgsx[j]);
		    	   if(ids == null) continue;
		    	   Element ide = (Element)ids.item(0);
		    	   if(ide==null) continue;
		    	   NodeList idvs = ide.getChildNodes();
		    	   us1.getTags().add(tgsx[j]);
		    	   if(idvs == null ||idvs.item(0)==null)
		    		   us1.getVals().add(new String(" "));
		    	   else
		    		   us1.getVals().add(((Node) idvs.item(0)).getNodeValue());
		       }
		       //house keeper
		       us1.getTags().remove(0);
		       us1.setBuzId(us1.getVals().remove(0));
		       ls.add(us1);	   
		    }
		  }
		return ls;
	}
}
