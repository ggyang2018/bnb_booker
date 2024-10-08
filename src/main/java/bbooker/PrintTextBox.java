package bbooker;

/* Print the contents of a text box
 * @Author Guang Yang
 */
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.MessageFormat;
import java.util.Locale;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import guiwidget.GStyledTextPane;
import guiwidget.XYLayout;

public class PrintTextBox extends JPanel implements Printable, ActionListener 
{
	static public final long serialVersionUID = BKFrame.serialVersionUID;
	GStyledTextPane txtPane;
	JButton pageBtn, printBtn, quitBtn, copyBtn;
	int wid, hgt;
	
	String pageHeader = new String("Report");
	String txt;
	double scale;
	PageFormat pageFormat;
	PrintRequestAttributeSet attrs;
	JDialog dlg;
	JFrame frm;
	public PrintTextBox(JDialog dlg, int wid, int hgt)
	{
		this.dlg = dlg;
		this.wid = wid; this.hgt = hgt;
		setLayout(new XYLayout());		
		txtPane = new GStyledTextPane();
		JScrollPane js1 = new JScrollPane(txtPane);
		js1.setBounds(5, 5, wid-20, hgt-90);
		pageBtn = new JButton("Page Setup");
		pageBtn.setBounds(10, hgt-70, 100, 20);
		printBtn = new JButton("Print");
		printBtn.setBounds(120, hgt-70, 100, 20);
		copyBtn = new JButton("Copy");
		copyBtn.setBounds(230, hgt-70, 100, 20);
		
		quitBtn = new JButton("Quit");
		quitBtn.setBounds(440, hgt-70, 100, 20);
		
		add(js1);
		add(pageBtn);
		add(printBtn);
		add(copyBtn);
		add(quitBtn);
		pageBtn.addActionListener(this);
		printBtn.addActionListener(this);
		copyBtn.addActionListener(this);
		quitBtn.addActionListener(this);
		attrs = new HashPrintRequestAttributeSet();
	}
	
	public PrintTextBox(JDialog dlg, int x, int y, int wid, int hgt)
	{
		this.dlg = dlg;
		this.wid = wid; this.hgt = hgt;
		setLayout(new XYLayout());		
		txtPane = new GStyledTextPane();
		JScrollPane js1 = new JScrollPane(txtPane);
		js1.setBounds(x, y, wid-4*x, hgt-4*y);
		add(js1);
		attrs = new HashPrintRequestAttributeSet();		
	}
	public void setPageHeader(String phd) { pageHeader = phd; }
	
	public void writeTxt(String txt) 
	{ try{ txtPane.append(txt, 3);}
	  catch(Exception ex){System.out.println(ex.toString());}	  
	}
	
	public void writeTxt(String txt, int idx) 
	{ 
      idx = idx %4;
	  try{ txtPane.append(txt, idx);}
	  catch(Exception ex){System.out.println(ex.toString());}	  
	}
	
	public void setText(String txt)
	{
	   try{ txtPane.setText(txt);}
	   catch(Exception ex){System.out.println(ex.toString());}	   	  
	}

	@Override
	public void actionPerformed(ActionEvent av) 
	{
	   String cmd = av.getActionCommand();
	   if(cmd.equals("Page Setup"))
	   {
		 setupPage();		   
	   }else if(cmd.equals("Print"))
	   {
		  try
		  {
		    //printImgStyle(); //work page count
			 //txtPane.print();
			  printTxt(true);
		  }catch(Exception pex)
		  {JOptionPane.showMessageDialog(this, pex.toString());}
	   }else if(cmd.equals("Quit"))
	   { 
		   dlg.dispose();
		   dlg.setVisible(false);
	   }else if(cmd.equals("Copy"))
	     copyTo();
	}
	
	protected void copyTo()
	{
		CopyText ct = new CopyText();
		System.out.println("AAA"+txtPane.getText());
		ct.setString(txtPane.getText());
	}	
	
	//*************** Image stype print *******************
	protected void printImgStyle() throws PrinterException
	{
	    PrinterJob jb = PrinterJob.getPrinterJob();
	    jb.setPageable(makeBook());
	    if(jb.printDialog(attrs))
	    	jb.print(attrs);
	}
	
	@Override
	public int print(Graphics g2d, PageFormat pft, int page)throws PrinterException 
	{
		Graphics2D g2 = (Graphics2D)g2d;
		if(page > getPageCount(g2, pft)) return Printable.NO_SUCH_PAGE;
		g2.translate(pft.getImageableX(), pft.getImageableY());
		drawPage(g2, pft, page);
		return 0;
	}
	
	public Book makeBook()
	{
	   if(pageFormat == null)
	   {
		  PrinterJob jb = PrinterJob.getPrinterJob();
		  pageFormat = jb.defaultPage();
	   }
	   Book bk = new Book();
	   txt = txtPane.getText();
	   int pgcnt = getPageCount((Graphics2D)getGraphics(), pageFormat);
	   bk.append(this, pageFormat, pgcnt);
	   return bk;
	}
	
	public int getPageCount(Graphics2D g2d, PageFormat pf)
	{
		if(txt==null || txt.trim().length()<1) return 0;
		FontRenderContext ctx = g2d.getFontRenderContext();
		Font f = txtPane.getFont(0); //bold
		Rectangle2D bounds = f.getStringBounds(txt, ctx);
		scale = pf.getImageableHeight()/bounds.getHeight();
		double wd = scale * bounds.getWidth();
		int pgs = (int)Math.ceil(wd/pf.getImageableWidth());
		return pgs;
	}
	
	protected void drawPage(Graphics2D g2, PageFormat pf, int page)
	{
		if(txt==null || txt.trim().length()<1) return;
		g2.clip(new Rectangle2D.Double(0, 0, pf.getImageableWidth(), 
				pf.getImageableHeight()));
		g2.translate(-page*pf.getImageableWidth(), 0);
		g2.scale(scale, scale);
		FontRenderContext ctx = g2.getFontRenderContext();
		Font f = txtPane.getFont(0);
		TextLayout ly = new TextLayout(txt, f, ctx);
		AffineTransform trs = AffineTransform.getTranslateInstance(0, ly.getAscent());
		Shape outline = ly.getOutline(trs);
		g2.draw(outline);
	}
	
	// ************* text Print ******************************
	 public void printTxt(boolean isBkground) 
	 {  //pattrns: object obs[] ={x, y, z} new MessageFormat("user{0}+{1});
		 //String s= message.format(obs); userx+y
	        MessageFormat header = new MessageFormat(pageHeader);
	        header.setLocale(Locale.UK);
	        Object ob[] = { pageHeader};
	        header.format(ob);
	        MessageFormat footer = new MessageFormat("page -{0}");
	        PrintingTask task = new PrintingTask(header, footer, true);
	        if (isBkground) 
	            task.execute();
	        else 
	        	task.run();
	 }
	 
	 public void setupPage()
	 {
		 PrinterJob job = PrinterJob.getPrinterJob();
		 pageFormat = job.pageDialog(attrs);
	 }
	 
	 
	private void message(boolean error, String msg) 
	{
        int type = (error ? JOptionPane.ERROR_MESSAGE :
                            JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, msg, "Printing", type);
    }
    
 	private class PrintingTask extends SwingWorker<Object, Object> 
	{
        private final MessageFormat headerFormat;
        private final MessageFormat footerFormat;
        private final boolean interactive;
        private volatile boolean complete = false;
        private volatile String message;
        
       public PrintingTask(MessageFormat head, MessageFormat foot,boolean interact)
       {
    	   this.headerFormat = head;
    	   this.footerFormat = foot;
    	   this.interactive = interact;
       }
        
       @Override
       protected Object doInBackground()
       {
          try 
          {
        	 complete = txtPane.print(headerFormat, footerFormat,
                        true, null, null, interactive);
        	 message = "Printing " + (complete ? "complete" : "canceled");
          }catch (PrinterException ex) 
          {  message = "Sorry, a printer error occurred";
          } catch (SecurityException ex) 
          { message = "Sorry, cannot access the printer due to security reasons";}
          return null;
       }    
        @Override
        protected void done() {   message(!complete, message);}
    }

}
