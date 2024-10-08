/*
 * Adapter action from theis abstract panel to other concrte panel
 * Author GY
 */
package guiwidget;


public interface PanelAdapter
{
	//public void showDetals( );
	//public void doAction(String command);
	
	public void processRow(Object row[]);
	public void setCellIdcs(int row, int col);

}
