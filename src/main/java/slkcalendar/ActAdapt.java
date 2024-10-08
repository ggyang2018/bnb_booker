package slkcalendar;
/* Package adapt for classes in other package to invoked, This is a standard procedure
 * Other packages set the adaptor and call its methods. This package implements the adapt and add 
 * the implementation as runtime plug-in. Adapt as inbounds call. 
 * @Author Guang Yang
 */
import javax.swing.JButton;
import java.util.Date;
import java.util.List;

public interface ActAdapt 
{
	public void setMonth(int m);
	public void setYear(int y);
	public void setDateAct(int row, int fld, JButton cellBtn);
	public void setSelectDate(Date dt, int mode);
	public List<String> getNames();
	//public void setCellIdx(int row, int col);
	
}
