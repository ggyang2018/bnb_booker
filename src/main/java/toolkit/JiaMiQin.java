/*
 * @Author GY
 */

package toolkit;



public class JiaMiQin 
{
	byte state[]; //as state table permutation table
	int ci, cj;
	
	public JiaMiQin() { state = new byte[256];}
	
	public void zhuShiHao(byte jan[])
	{
		int i=0;
		for(i=0; i<256; i++)state[i]= (byte)i; //initial state

		byte s;
		int j=0;
		for(i=0; i<256; i++)
		{
			j = (j+ state[i] + jan[i%(jan.length)])%256; // & 0xFF; //keep one byte vaue
			if(j<0) j= 256+j;
			s = state[i];
			state[i] = state[j];
			state[j] = s;
		}
		//set permutation indecis
		ci = 0; cj = 0;
	}
	
	public void jiaMi(byte wejian[])
	{
		int i;
		int x, y;
		byte a, b;
		//unsigned char register s;
		x = ci;
		y = cj;
		for(i=0; i<wejian.length; i++)
		{
			x = (x+1) % 256; // rather than & 0xFF;  //x
			a = state[x];
			y=(x+a) %256;
			if(y<0) y = 256+y;
			state[x] = state[y];
			state[y] =  a;
			int xy = (state[x]+state[y])%256;
			if(xy <0) xy = 256+xy;
			b = state[xy];
		    wejian[i] =(byte)(wejian[i] ^ b);
		}
		ci = x;
		cj = y;
	}
	
	public boolean equalBytes(byte a[], byte b[])
	{
		if(a.length != b.length) return false;
		for(int i=0; i<a.length; i++)
			if(a[i] != b[i]) return false;
		
		return true;
	}
	
	/*static public void main(String args[])
	{
		JiaMiQin jm1 = new JiaMiQin();
		if(jm1.equalBytes(g1, cg2)) System.out.println("right");
		else System.out.println("wrong");
		
		String txt = "This is a testing for Guang Yang";
		jm1.zhuShiHao(g1);
		jm1.jiaMi(txt.getBytes());
		System.out.println("encrypted: "+txt);
		
		jm1.zhuShiHao(cg2);
		jm1.jiaMi(txt.getBytes());
		System.out.println("decrypted: "+txt);		
	}*/
}
