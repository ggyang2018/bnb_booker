package bbooker;
/*For customer data render
 * @Author Guang Yang
 */
import java.awt.event.ActionEvent;
import guiwidget.*;
import javax.swing.*;

public class BKWizardPane2 extends GWizardPane
{
	static public final long  serialVersionUID = GWizardPane.serialVersionUID;
	java.text.DecimalFormat df = new
	java.text.DecimalFormat("######.##");
	
    BKFrame frm;
    BKWizardDlg wdlg;
    String lastName;
    protected JTextField surNameFd, forNameFd, telFd, mobieFd, balFd;
	protected JTextField emailFd, visitFd, spRefFd, addFd;
	protected JComboBox country, titBx;
	protected JTextField postFd, addr1Fd, addr2Fd, addr3Fd;
	JButton searchBtn;
	String tits[]={"Mr.", "Mrs.", "Miss", "Ms.", "Dr.", "Prof.", ""};
	JTextArea infoArea;

    public BKWizardPane2(BKWizardDlg dlg)
	{
		super(dlg, "Customer Information ", 2, false);
		super.initPane();
		frm = dlg.frm;
		wdlg = dlg;
		init2();
	}
	
	void init2()
	{
		int y0 = getY0();
		int x0=3, w=100, w1=110, h=20, off=3;
		int x1 = x0+w+off;
		int nx0=x1+w1+off;
		int nx1=nx0+w+off;
		
		JLabel srnm = new JLabel("Surname", JLabel.RIGHT);
		srnm.setBounds(x0, y0, w, h);
		surNameFd = new JTextField();
		surNameFd.setBounds(x1, y0, w1, h);
		add(srnm);  add(surNameFd);		
		titBx = new JComboBox(tits);
		titBx.setBounds(nx0, y0, w-20, h);
		add(titBx);		
		ImageIcon cup = new ImageIcon("iconimg/find24.gif");
		searchBtn = new JButton(cup);
		searchBtn.setText("Lookup");
		searchBtn.setActionCommand("Lookup");
		searchBtn.setBounds(nx1, y0, w1, h);
		add(searchBtn);
		searchBtn.addActionListener(this);
		y0= y0+h+off;
		JLabel frnm = new JLabel("Forename", JLabel.RIGHT);
		frnm.setBounds(x0, y0, w, h);
		forNameFd = new JTextField();
		forNameFd.setBounds(x1, y0, w1, h);
		JLabel tel = new JLabel("Telephone", JLabel.RIGHT);
		tel.setBounds(nx0, y0, w, h);
		telFd = new JTextField();
		telFd.setBounds(nx1, y0, w1, h);
		add(frnm); add(forNameFd); add(tel); add(telFd);
		y0=y0+h+off;	   
		JLabel mob = new JLabel("Mobile", JLabel.RIGHT);
		mob.setBounds(x0, y0, w, h);
		mobieFd = new JTextField();
		mobieFd.setBounds(x1, y0, w1, h);
		JLabel eml = new JLabel("Email", JLabel.RIGHT);
		eml.setBounds(nx0, y0, w, h);
		emailFd = new JTextField();
		emailFd.setBounds(nx1, y0, w1, h);
		add(mob); add(mobieFd); add(eml); add(emailFd);
		y0=y0+h+off;
		JLabel add1 = new JLabel("Address1", JLabel.RIGHT);
		add1.setBounds(x0, y0, w, h);
		addr1Fd = new JTextField();
		addr1Fd.setBounds(x1, y0, w1+nx0, h);
		add(add1); add(addr1Fd);
		y0=y0+h+off;
		JLabel add2 = new JLabel("Address2", JLabel.RIGHT);
		add2.setBounds(x0, y0, w, h);
		addr2Fd = new JTextField();
		addr2Fd.setBounds(x1, y0, w1+nx0, h);
		add(add2); add(addr2Fd);
		y0=y0+h+off;
		JLabel add3 = new JLabel("Address3", JLabel.RIGHT);
		add3.setBounds(x0, y0, w, h);
		addr3Fd = new JTextField();
		addr3Fd.setBounds(x1, y0, w1+nx0, h);
		add(add3); add(addr3Fd);
		y0=y0+h+off;				
		JLabel cntry = new JLabel("Country", JLabel.RIGHT);
		cntry.setBounds(x0, y0, w, h);
		country = new JComboBox(countryNames);
		country.setSelectedIndex(219);
		country.setBounds(x1, y0, w1+20, h);
		add(cntry); add(country);
		JLabel psd = new JLabel("Postcode", JLabel.RIGHT);
		psd.setBounds(nx0+20, y0, w-20, h);
		postFd = new JTextField();
		postFd.setBounds(nx1, y0, w1, h);
		add(psd); add(postFd);		
	    y0= y0+h+off;
	    JLabel visit = new JLabel("Visit Reason", JLabel.RIGHT);
		visit.setBounds(x0, y0, w, h);
		visitFd = new JTextField("Tourist");
		visitFd.setBounds(x1, y0, w1+nx0, h);
		add(visit); add(visitFd);
		y0= y0+h+off;
	    JLabel spreq = new JLabel("Special Req", JLabel.RIGHT);
		spreq.setBounds(x0, y0, w, h);
		spRefFd = new JTextField();
		spRefFd.setBounds(x1, y0, w1+nx0, h);
		add(spreq); add(spRefFd);		
		y0= y0+h+off;
	    JSeparator sp0 = new JSeparator();
		sp0.setBounds(2, y0, getWid()-5, 5);
		add(sp0);
		y0=y0+10;
		JLabel addLb0 = new JLabel("Additional Customer Information", JLabel.CENTER);
		addLb0.setBounds(x0, y0, w1+nx1-5, h);
		add(addLb0);
		y0= y0+h+off;
		infoArea = new JTextArea();
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		JScrollPane scrTxt = new JScrollPane(infoArea);
		scrTxt.setBounds(x0+5, y0,  w1+nx1-5, 40);
		add(scrTxt);		
	}
	
	public void actionPerformed(ActionEvent av)
	{
		super.actionPerformed(av);
		String cmd = av.getActionCommand();
   		if(cmd.equals("Lookup"))
   			lookup();
   		else if(cmd.equals("Add"))
   		{
   			SLKCustomer cs1 = wdlg.getCust(0); //only one anyway
   			AddCustDlg acdlg = new AddCustDlg(cs1, wdlg, "Additional Information");
   			acdlg.setFavourBounds(450, 300);
   			acdlg.init();
   			acdlg.setVisible(true);
   		}
	}
	
	public void backAction(){ wdlg.custs.clear();}
		
	public boolean nextAction()
	{ 
	   String srnm = surNameFd.getText();
	   if(srnm==null || srnm.length()<2)
	   {
		   wdlg.errorMsg("The surname is mandatory");
		   return false;	  
	   }
	   
	   String frnm = forNameFd.getText();
	   if(frnm==null || frnm.length()<2)
	   {
		   wdlg.errorMsg("The forename is mandatory");
		   return false;	  
	   }
	   
	   SLKCustomer scs  = wdlg.getCust(0);
	   setPaneData(scs, false);
	   if(scs.getContact()==null)
	   {
		   wdlg.errorMsg("At least one of mobile, telephone and email " +
		   		"\nshould be imported");
		   return false;
	   }
	   	   	  
	   BKWizardPaneX pan1 = (BKWizardPaneX)wdlg.getWizardPane(3);
	   pan1.writeTxtPane();
	   return true;
	}
	//to Pane or from pane
	
	protected void setPaneData(SLKCustomer cs, boolean toPane)
	{
	  try
	  {   
	   if(toPane)
		 surNameFd.setText(cs.getSurname());
	   else
		 cs.setSurame(surNameFd.getText());
	   
	   if(!toPane)
	   {
		  cs.setTitle(titBx.getSelectedItem().toString());
		  cs.setForename(forNameFd.getText());
		  cs.setTelephone(telFd.getText());
		  cs.setMobile(mobieFd.getText());
		  cs.setEmail(emailFd.getText());
		  String cty = country.getSelectedItem().toString();
		  cs.setCountry(cty);
		  cs.setPostcode(postFd.getText());
		  cs.setAddress1(addr1Fd.getText());
		  cs.setAddress2(addr2Fd.getText());
		  cs.setAddress3(addr3Fd.getText());
		  cs.setVisReason(visitFd.getText());
		  cs.setAddFee(addFd.getText());
		  cs.setSpecialReq(spRefFd.getText());		
		  cs.setAddInfo(infoArea.getText());
	   }else
	   {
		  //regisAll.setSelected(cs.isRegistForAll());
		  titBx.setSelectedItem(cs.getTitle());
		  forNameFd.setText(cs.getForename());
		  telFd.setText(cs.getTelephone());
		  mobieFd.setText(cs.getMobile());
		  emailFd.setText(cs.getEmail());
		  country.setSelectedItem(cs.getCountry());
		  postFd.setText(cs.getPostcode());
		  addr1Fd.setText(cs.getAddress1());
		  addr2Fd.setText(cs.getAddress2());
		  addr3Fd.setText(cs.getAddress3()); 
		  visitFd.setText(cs.getVisReason());
		  addFd.setText(cs.getAddFee());
		  spRefFd.setText(cs.getSpecialReq());
		  //arriveFd.setText(cs.getArriveTime());
		  infoArea.setText(cs.getAddInfo());
	   }
	  }catch(Exception ex) { System.out.print(ex.toString());}
	}
		
	protected void lookup()
	{
		SearchCustDlg dlg = new SearchCustDlg(wdlg, "Search Existing Customer");
		dlg.setFavourBounds(380, 370);
		dlg.setParentPane(this);
		dlg.initDlg();
		dlg.setParentPane(this);
		dlg.setVisible(true);
	}
	
	
	protected String countryNames[] ={
	 "Afghanistan",	"Albania",	"Algeria","American Samoa",	"Andorra",
	 "Angola",	"Anguilla",	"Antarctica","Antigua and Barbuda",	"Argentina",
	 "Armenia",	"Aruba --",	"Australia","Austria","Azerbaijan",
	 "Bahrain",	"Bangladesh", "Barbados", "Belarus",	"Belgium",
	 "Belize",	"Benin", "Bermuda --",	"Bhutan", "Bolivia",
	 "Bosnia and Herzegovina","Botswana","Bouvet Island --","Brazil","British Virgin Islands",
	 "Brunei",	"Bulgaria",	"Burkina Faso",	"Burundi",	"Cambodia",
	 "Cameroon","Canada","Cape Verde","Cayman Islands --","Central African Republic",
	 "Chad", "Chile",	"China", "Christmas Island --","Cocos (Keeling) Islands --",
	 "Colombia","Comoros",	"Congo","Cook Islands --",	"Costa Rica",
	 "Croatia",	"Cuba",	"Cyprus","Czech Republic","Democratic Republic of the Congo",
	 "Denmark",	"Djibouti",	"Dominica",	"Dominican Republic","East Timor --",
	 "Ecuador",	"Egypt",	"Salvador",	"Equatorial Guinea","Eritrea",
	 "Estonia",	"Ethiopia",	"Faeroe Islands","Falkland Islands","Fiji",
	 "Finland","Former Yugoslav Republic of Macedonia","France","French Guiana","French Polynesia --",
	 "French Southern Territories",	"Gabon","Georgia",	"Germany",
	 "Ghana","Gibraltar --","Greece","Greenland --", "Grenada",
	 "Guadeloupe --",	"Guam --",	"Guatemala","Guinea","Guinea-Bissau",
	 "Guyana",	"Haiti","Heard Island and McDonald Islands --",	"Honduras","Hong Kong --",
	 "Hungary",	"Iceland",	"India","Indonesia",	"Iran",
	 "Iraq", "Ireland",	"Israel",	"Italy","Jamaica",
	 "Japan",	"Jordan",	"Kazakhstan",	"Kenya","Kiribati",	"Kuwait",
	 "Kyrgyzstan",	"Laos",	"Latvia",	"Lebanon",	"Lesotho",
	 "Liberia",	"Libya",	"Liechtenstein","Lithuania","Luxembourg",
	 "Macau --","Madagascar",	"Malawi",	"Malaysia",	"Maldives",
	 "Mali",	"Malta",	"Marshall Islands",	"Martinique --",	"Mauritania",
	 "Mauritius",	"Mayotte --",	"Mexico",	"Micronesia",	"Moldova",
	 "Monaco",	"Mongolia",	"Montserrat --",	"Morocco",	"Mozambique",
	 "Myanmar",	"Namibia",	"Nauru",	"Nepal",	"Netherlands",
	 "Netherlands Antilles --",	"New Caledonia --",	"New Zealand","Nicaragua","Niger",
	 "Nigeria",	"Niue --",	"Norfolk Island --","North Korea","Northern Marianas --",
	 "Norway",	"Oman",	"Pakistan",	"Palau",	"Panama",
	 "Papua New Guinea",	"Paraguay",	"Peru",	"Philippines",	"Pitcairn Islands --",
	 "Poland",	"Portugal",	"Puerto Rico --",	"Qatar",	"Réunion --",
	 "Romania",	"Russia",	"Rwanda",	"Saint Helena --",	"Saint Kitts and Nevis",
	 "Saint Lucia",	"Saint Pierre and Miquelon --",	"Saint Vincent and the Grenadines",	"Samoa","San Marino",
	 "Saudi Arabia",	"Senegal",	"Seychelles","Sierra Leone",	"Singapore",
	 "Slovakia","Slovenia",	"Solomon Islands","Somalia",	"South Africa",
	 "South Korea",	"Spain",	"Sri Lanka","Sudan", "Suriname",
	 "Svalbard and Jan Mayen --","Swaziland","Sweden","Switzerland","Syria",
	 "Taiwan",	"Tajikistan",	"Tanzania",	"Thailand",	"The Bahamas",
	 "The Gambia",	"Togo",	"Tokelau --",	"Tonga","Trinidad and Tobago",
	 "Tunisia",	"Turkey","Turkmenistan","Turks and Caicos Islands",	"Tuvalu",
	 "Virgin Islands",	"Uganda",	"Ukraine",	"United Arab Emirates",	"United Kingdom",
	 "United States","Uruguay",	"Uzbekistan",	"Vanuatu",	"Vatican City",
	 "Venezuela","Vietnam",	"Wallis and Futuna --",	"Western Sahara","Yemen",
	 "Yugoslavia",			"Zambia",			"Zimbabwe"
	 };
}