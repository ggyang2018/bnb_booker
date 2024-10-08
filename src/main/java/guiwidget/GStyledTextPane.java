/*
 * GStyledTextPane is multiColor and multifont styled text pane.
 * Author GY
 */

package guiwidget;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GStyledTextPane extends JTextPane
{
   static public final long  serialVersionUID = GUIWidgetAdaptor.serialVersionUID;	

   StyledDocument doc;
   SimpleAttributeSet txtAttrs[]; //set predefint attributes
   Font txtFonts[];
   Color txtColors[];
   
   private int attr_cnt=4, font_cnt=2, color_cnt=2;
   
   public GStyledTextPane( )
   {
	   super();
	   doc = getStyledDocument();
	   setCaretPosition(0);
	   setMargin(new Insets(5, 5, 5, 5));
	   txtAttrs = new SimpleAttributeSet[attr_cnt];
	   txtFonts = new Font[font_cnt];
	   txtColors = new Color[color_cnt];
	   setTextAttributeSet(); 
   }
   
   //override function
   public void setTextAttributeSet( )
   {
	   txtFonts[0] = new Font("SansSerif", Font.BOLD, 12);
	   txtFonts[1]= new Font("SansSerif", Font.PLAIN, 12);
	   
	   txtAttrs[0] = new SimpleAttributeSet();
	   StyleConstants.setFontFamily(txtAttrs[0], "SansSerif");
	   StyleConstants.setFontSize(txtAttrs[0], 12);
	   StyleConstants.setBold(txtAttrs[0], true);
	   StyleConstants.setForeground(txtAttrs[0], Color.red);
	   
	   txtAttrs[1] = new SimpleAttributeSet();
	   StyleConstants.setFontFamily(txtAttrs[1], "SansSerif");
	   StyleConstants.setFontSize(txtAttrs[1], 12);
	   StyleConstants.setBold(txtAttrs[1], true);
	   StyleConstants.setForeground(txtAttrs[1], Color.black);
	   
	   txtAttrs[2] = new SimpleAttributeSet();
	   StyleConstants.setFontFamily(txtAttrs[2], "SansSerif");
	   StyleConstants.setFontSize(txtAttrs[2], 12);
	   StyleConstants.setBold(txtAttrs[2], true);
	   StyleConstants.setForeground(txtAttrs[2], Color.blue);
	   
	   txtAttrs[3] = new SimpleAttributeSet();
	   StyleConstants.setFontFamily(txtAttrs[3], "SansSerif");
	   StyleConstants.setFontSize(txtAttrs[3], 12);
	   StyleConstants.setBold(txtAttrs[3], true);
	   StyleConstants.setForeground(txtAttrs[3], Color.black);
   }
   
   public void setFonts( ) //list all using fonts
   {
	   
   }
   
   public Font getFont(int idx) { return txtFonts[idx]; } 
   
   public void append(String str, int attr_idx) throws BadLocationException
   {
	   doc.insertString(doc.getLength(), str, txtAttrs[attr_idx]);
      
   }
   
   public String getText()
   {   
	   String rts = new String();
	   try{ rts = doc.getText(0, doc.getLength());
      }catch(Exception ex) { rts = new String(); }
      return rts;
   }
    
}
