package slklogin;
/* Configure information encrypt information for protection
 * machine information, 
 * @Autho Guang Yang 
 */
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.regex.*;

import toolkit.JiaMiQin;
final public class ConfigInfo 
{
	private byte bys[];
	private String machInfo;//original address
	private String mode; //all
	private String fnm =  "slkdata\\bookcfg.inf";
	private String macAddr1; //local MAC address
	private String hostNm0; //original name
	private String hostNm1; //local retrieved name
	private boolean useHost = false;
	private final String FIRST="FIRST";
	private final String NONAPP="N/A";
	
	private boolean fileExist = true;
	public ConfigInfo()
	{
		String s= new String("honda0307");
		bys = s.getBytes();
		machInfo =new String(FIRST); //never being null
		hostNm0 = new String(FIRST);
		load();
	}
	
	public void setMode(boolean isAll) { mode = isAll? "all" : "Non"; }
	public String getMode() { return mode; }
	
	public String getMachInfo() { return machInfo; }
	public void setMachInfo(String s) 
	{
		if(s==null || s.trim().length()<1)
			machInfo = FIRST;
		else
			machInfo = s.trim(); 
	}
	public String getMacAddr1() { return macAddr1; }
	
	public void setHostNm0(String s) 
	{ 
		if(s==null || s.trim().length()<1)
			hostNm0 = FIRST;
		else
			hostNm0 = s.trim();
	}
	
	public String getHostNm0() { return hostNm0; }
	public String getHostNm1() { return hostNm1; }
	
	public void reset()
	{
		machInfo = FIRST;
		hostNm0 = FIRST;
	}
	
	public boolean go()
	{
		if(!fileExist) return fileExist;
		boolean fg = false;
		try
		{
			if(!useHost)
			{		
			   if(machInfo.equals(FIRST))
			   {
				 machInfo = macAddr1;
				 hostNm0 = NONAPP;
				 save(); 
				 fg=true;
			   }else
				   fg =  machInfo.equals(macAddr1);
			}else
			{
				if(hostNm0.equals(FIRST))
				{
					machInfo = NONAPP;
					hostNm0 = hostNm1;
					save();
					fg=true;
				}else
					fg =  hostNm0.trim().equals(hostNm1.trim());
			}
		}catch(Exception ex) { fg = false; }
		return fg;
	}
	public void save() throws IOException
	{
	    //machInfo = macAddr1;	
		StringBuffer sbf = new StringBuffer();
		sbf.append(machInfo+"\n");
		sbf.append(hostNm0+"\n");
		sbf.append(mode);
		JiaMiQin jm1 = new JiaMiQin();
		jm1.zhuShiHao(bys);
		
		byte ctx[] = sbf.toString().getBytes();
		jm1.jiaMi(ctx);
		
		FileOutputStream out = new FileOutputStream(fnm);
		out.write(ctx);
		out.flush();
		out.close();				
	}
	
	public void load()
	{
		try
		{
			fileExist = true; 
			File f1 = new File(fnm);
			if(!f1.exists())
			{
				fileExist= false;
				System.out.println("OOOOO");
				return;
			}
			int sz = (int)f1.length();
			byte ctx[] = new byte[sz];
			FileInputStream fin = new FileInputStream(f1);
			fin.read(ctx);
			JiaMiQin jm1 = new JiaMiQin();
			jm1.zhuShiHao(bys);
			jm1.jiaMi(ctx);
			fin.close();
			String ss1 = new String(ctx);
			StringTokenizer tk = new StringTokenizer(ss1, "\n");
			machInfo = tk.nextToken();
			hostNm0 = tk.nextToken();
			mode = tk.nextToken();
			macAddr1 =getMacAddress();
			if(macAddr1==null || macAddr1.length()<1) useHost=true;
			if(machInfo.equals(NONAPP)) useHost = true;
			hostNm1 = getHostName();
			System.out.println(machInfo);
			System.out.println(mode);
			System.out.println(macAddr1);
		}catch(Exception ex) 
		{
			machInfo = FIRST;
			hostNm0 = FIRST;
			ex.printStackTrace();
			//System.out.println(ex.toString());
		}
	}
	
	private String getMacAddress() throws IOException
	{
		String macAddress = null;
		String command = "ipconfig /all";
		Process pid = Runtime.getRuntime().exec(command);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(pid.getInputStream()));
		while (true)
		{
			String line = in.readLine();
			if (line == null)
				break;
			Pattern p = Pattern.compile(".*Physical Address.*: (.*)");
			Matcher m = p.matcher(line);
			if (m.matches()) 
			{
				macAddress = m.group(1);
				break;
			}
		}
		in.close();
		return macAddress;
	}
	
	private String getHostName()
	{  String hst = new String();
	   try 
	   {
		   InetAddress addr = InetAddress.getLocalHost();
		   hst = addr.getHostName();
	   } catch (UnknownHostException e) {hst=new String();}
	   return hst;
	}

	//testing dirver
	static public void main(String args[])
	{
		try
		{
			ConfigInfo cif = new ConfigInfo();
			System.out.println(cif.getMachInfo());
			System.out.println(cif.getMode());
			System.out.println(cif.go());
		}catch(Exception ex) { ex.printStackTrace(); }
	}

}
