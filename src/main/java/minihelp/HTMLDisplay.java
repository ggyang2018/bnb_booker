package minihelp;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import java.net.*;

public class HTMLDisplay extends JScrollPane implements HyperlinkListener
{
	static public final long  serialVersionUID = SearchList.serialVersionUID;
    private JEditorPane ep;
    private HTMLEditorKit ek ;
    private URL url;

	
    public HTMLDisplay(String fileName)
	{
	  // super();
       setupDisplay(fileName);
	}

	private void setupDisplay(String fileName)
	{
		ep = new JEditorPane();
		ep.setEditable(false);
		ek = new HTMLEditorKit();
		ep.setEditorKit(ek);
		ep.addHyperlinkListener(this);
		setBackground(Color.white);
		getViewport().add(ep);
		  
       url = this.getClass().getResource("/" + fileName);
       
       try
	   {
			ep.setPage(url);
	   }catch(java.io.IOException ioe)
	   {	ioe.printStackTrace();  }
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

}//end class HTMLDisplay definition

