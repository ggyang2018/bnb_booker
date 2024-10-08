package bbooker;
/*copy text into clipboard and get text from clipboard
 Belongs to : JSP/Java
 */

import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.*;

public final class CopyText implements ClipboardOwner 
{

	public void lostOwnership( Clipboard aClipboard, Transferable aContents) {}

	public void setString(String data)
	{
		StringSelection stringSelection = new StringSelection(data);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}
	
	public String getString() 
	{
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText =(contents != null) &&
			contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if ( hasTransferableText )
		{
			try
			{
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			}catch (UnsupportedFlavorException e){System.out.println(e);}
			catch (IOException e) {System.out.println(e);}
		}
		return result;
	}
} 
