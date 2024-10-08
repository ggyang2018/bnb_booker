package xhtml;
/* 
 * display html from file
 */

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

import java.io.File;
import java.net.*;

public class HTMLPane extends JScrollPane implements HyperlinkListener
{
	static public final long  serialVersionUID = 987654321L;
    private JEditorPane ep;
    private HTMLEditorKit ek ;
    private URL url;

	
    public HTMLPane(String fileName)
	{
	  // super();
       setupDisplay(fileName, false);
	} 
    
	@SuppressWarnings("deprecation")
	private void setupDisplay(String fileName, boolean inJar)
	{
		ep = new JEditorPane();
		ep.setEditable(false);
		ek = new HTMLEditorKit();
		ep.setEditorKit(ek);
		ep.addHyperlinkListener(this);
		setBackground(Color.white);
		getViewport().add(ep);
       try
	   {
    	   if(inJar)
    	   {
   			  url = this.getClass().getResource("/" + fileName);
   		   }else
   		   {
   			 File f = new File(fileName);
   			 url = f.toURL();
   		   }  			  	   
 		  ep.setPage(url);		    			
	   }catch(java.io.IOException ioe){ioe.printStackTrace();  }
	}

	public void hyperlinkUpdate(HyperlinkEvent e)
	{
		if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			if (e instanceof HTMLFrameHyperlinkEvent) 
			{
				HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
				HTMLDocument doc = (HTMLDocument)ep.getDocument();
				doc.processHTMLFrameHyperlinkEvent(evt);
			}
			else 
			{
			  try 
			  {
				//System.out.println("url:"+url.toString());
				//System.out.println("link:"+e.getURL().toString());
				ep.setPage(e.getURL());
			  }catch (Throwable t) 
			  {   t.printStackTrace();   }
			}
		}
	} 
	
	public void setPage(String expr)
	{
		try
		{
			String surl = url.toString()+"#"+expr;
			//System.out.println(surl);
			URL url1= new URL(surl);
			ep.setPage(url1);
		}catch(Exception e)
		{  e.printStackTrace(); }
	}
	
	public void setPage(URL ex_url)
	{try{ ep.setPage(ex_url);} catch(Throwable t){ t.printStackTrace(); }}

}//end class HTMLDisplay definition

