package nxd;
/* base class for other class extension, default construct
 * @Author Guang Yang
 */

import java.util.Arrays;

public abstract class DSDataMatrix<T> 
{
	protected String matrixId;
	protected int colNbr, rowNbr;
	protected int ptCol, ptRow;
	protected T mat[][];
	protected T iniObj = null;
	
	public DSDataMatrix() 
	{ 
		colNbr=1; rowNbr=1;
		ptCol=0; ptRow =0;
		//extension should set matrix key		
	}
	public String getMatrixId() { return matrixId; }
	public void setMatrixId(String id) { matrixId = id; }
	public void setInitObj(T obj){ iniObj = obj;} 
	public void init()
	{
	   mat = makeMatrix(rowNbr, colNbr);
	   if(iniObj != null)
	   {
		   for(int i=0; i<rowNbr; i++)
			   Arrays.fill(mat[i], iniObj);
	   }
	}
	abstract public T[][] makeMatrix(int rw, int col);
	public T[][] getMatrix(){ return mat; }
	
	//work on row;
	public T[] findRow(int idx) { return mat[idx]; }
	public void deleteRow(int idx)
	{
		if(idx <0 || idx >=rowNbr) return;
		T[][] mat1 = makeMatrix(rowNbr-1, colNbr);
		int i=0;
		for(i=0; i<=idx; i++)
		   mat1[i] = Arrays.copyOfRange(mat[i], 0, colNbr);
		if(i>=rowNbr-1) return;
		for(i = idx+1; i<rowNbr; i++)
			mat1[i-1] = Arrays.copyOfRange(mat[i], 0, colNbr);
		mat = mat1;
		rowNbr--;		
	}
	public void insertRow(int idx, T[] rw)
	{
		T[][] mat1 = makeMatrix(rowNbr+1, colNbr);
		int i=0;
		if(idx<=0) //front insert
		{
			mat1[0] = Arrays.copyOfRange(rw, 0, colNbr);
			for(i=0; i<rowNbr; i++)
			  mat1[i+1] = Arrays.copyOfRange(mat[i], 0, colNbr);
		}else if(idx >=rowNbr) //backend insert--add
		{
			for(i=0; i<rowNbr; i++)
			  mat1[i] = Arrays.copyOfRange(mat[i], 0, colNbr);
			mat1[i] = Arrays.copyOfRange(rw, 0, colNbr);
		}else //insert middle
		{
			for(i=0; i<idx; i++)
				mat1[i] = Arrays.copyOfRange(mat[i], 0, colNbr);
			mat1[i] = Arrays.copyOfRange(rw, 0, colNbr);
			for(i = idx+1; i<rowNbr+1; i++)
				mat1[i] = Arrays.copyOfRange(mat[i-1], 0, colNbr);		
		}
		mat = mat1;
		rowNbr++;
	}
	// cell opertion
	public T findCell(int ridx, int cidx){ return mat[ridx][cidx];}
	public void updateCell(int ridx, int cidx, T t)
	{ 
		try
		{ mat[ridx][cidx]=t;}catch(Exception ex) {} 
	}

}
