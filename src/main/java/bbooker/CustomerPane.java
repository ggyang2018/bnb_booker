package bbooker;

/* Customer panel to enter a customer details
 * @Author Guang Yang
 */
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

import guiwidget.XYLayout;


public class CustomerPane extends JPanel implements ChangeListener,ActionListener
{
	static public final long serialVersionUID = BookDlg.serialVersionUID;
	protected JTextField surNameFd, forNameFd, telFd, mobieFd;
	protected JTextField emailFd;
	protected JComboBox country;
	protected JTextField country1;
	protected JTextField postFd, addr1Fd, addr2Fd, addr3Fd;
	JCheckBox evMeal;
	JTextField dietReqFd;
	//JButton searchBtn;
	//---- container
	BookDlg bookDlg; 
	
	public CustomerPane(BookDlg dlg)
	{
	   bookDlg = dlg;
	   setLayout(new XYLayout());
	   Border aBorder = BorderFactory.createRaisedBevelBorder();
	   setBorder(aBorder);
	   //initPane();
	}
	public void setHasNew(boolean has){ isNew = has; }
	public void initPane()
	{
	   int x0=5, y0=5, h=20, w=70, w1=215, off=3;
	   JLabel ptit = new JLabel("Customer Details", JLabel.RIGHT);
	   ptit.setBounds(x0, y0, 165, h);
	   /*ImageIcon cup = new ImageIcon("iconimg/find24.gif");
	   searchBtn = new JButton(cup);
	   searchBtn.setActionCommand("Search");
	   searchBtn.setBounds(175, y0-2, 40, h);
	   add(searchBtn);
	   searchBtn.addActionListener(this);
	   */
	   y0 = h+5;
	   JLabel srnm = new JLabel("Surname:", JLabel.RIGHT);
	   srnm.setBounds(x0, y0, w, h);
	   surNameFd = new JTextField();
	   surNameFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel frnm = new JLabel("Forename:", JLabel.RIGHT);
	   frnm.setBounds(x0, y0, w, h);
	   forNameFd = new JTextField();
	   forNameFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel tel = new JLabel("Telephone:", JLabel.RIGHT);
	   tel.setBounds(x0, y0, w, h);
	   telFd = new JTextField();
	   telFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;	   
	   JLabel mob = new JLabel("Mobile:", JLabel.RIGHT);
	   mob.setBounds(x0, y0, w, h);
	   mobieFd = new JTextField();
	   mobieFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel eml = new JLabel("Email:", JLabel.RIGHT);
	   eml.setBounds(x0, y0, w, h);
	   emailFd = new JTextField();
	   emailFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel cntry = new JLabel("Country:", JLabel.RIGHT);
	   cntry.setBounds(x0, y0, w, h);
	   country = new JComboBox(countryNames);
	   country.setSelectedIndex(219);
	   country.setBounds(x0+w+5, y0, w1, h);
	   country1 = new JTextField();
	   country1.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel psd = new JLabel("Postcode:", JLabel.RIGHT);
	   psd.setBounds(x0, y0, w, h);
	   postFd = new JTextField();
	   postFd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add1 = new JLabel("Address1:", JLabel.RIGHT);
	   add1.setBounds(x0, y0, w, h);
	   addr1Fd = new JTextField();
	   addr1Fd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add2 = new JLabel("Address2:", JLabel.RIGHT);
	   add2.setBounds(x0, y0, w, h);
	   addr2Fd = new JTextField();
	   addr2Fd.setBounds(x0+w+5, y0, w1, h);
	   y0=y0+h+off;
	   JLabel add3 = new JLabel("Address3:", JLabel.RIGHT);
	   add3.setBounds(x0, y0, w, h);
	   addr3Fd = new JTextField();
	   addr3Fd.setBounds(x0+w+5, y0, w1, h);
	   
	   y0= y0+h+5;
	   evMeal = new JCheckBox("Evening Meal?");
	   evMeal.setBounds(x0, y0, w+40, h);
	   //y0= y0+h+off;
	   dietReqFd = new JTextField(); 
	   dietReqFd.setBounds(w+x0+40, y0, w1-30, h);
	   dietReqFd.setEditable(false);	   	 	   
	   //y0=y0+h+3*off;
	    add(ptit);
	   add(srnm); 	add(surNameFd);
	   add(frnm); 	add(forNameFd);
	   add(tel);  	add(telFd);
	   add(mob);	add(mobieFd);
	   add(eml);	add(emailFd);
	   add(cntry);	if(isNew)add(country); else add(country1);
	   add(psd);	add(postFd);
	   add(add1);	add(addr1Fd);
	   add(add2);	add(addr2Fd);
	   add(add3);	add(addr3Fd);
	   add(evMeal);
	   add(dietReqFd);
	   evMeal.addChangeListener(this);
	}
	
	public void stateChanged(ChangeEvent e)
	{	dietReqFd.setEditable(evMeal.isSelected());}
	
	public void actionPerformed(ActionEvent av)
	{
	   String cmd = av.getActionCommand();
	   if(cmd.equals("Search"))
	   {
		  SearchCustDlg dlg = new SearchCustDlg(bookDlg, "Search Existing Customer");
		  dlg.setFavourBounds(400, 400);
		  dlg.initDlg();
		  dlg.setVisible(true);
	   }		   
	}
	
	protected String countryNames[] ={"Afghanistan",
			"Albania",
			"Algeria",
			"American Samoa",
			"Andorra",
			"Angola",
			"Anguilla",
			"Antarctica",
			"Antigua and Barbuda",
			"Argentina",
			"Armenia",
			"Aruba --",
			"Australia",
			"Austria",
			"Azerbaijan",
			"Bahrain",
			"Bangladesh",
			"Barbados",
			"Belarus",
			"Belgium",
			"Belize",
			"Benin",
			"Bermuda --",
			"Bhutan",
			"Bolivia",
			"Bosnia and Herzegovina",
			"Botswana",
			"Bouvet Island --",
			"Brazil",
			"British Virgin Islands",
			"Brunei",
			"Bulgaria",
			"Burkina Faso",
			"Burundi",
			"Cambodia",
			"Cameroon",
			"Canada",
			"Cape Verde",
			"Cayman Islands --",
			"Central African Republic",
			"Chad",
			"Chile",
			"China",
			"Christmas Island --",
			"Cocos (Keeling) Islands --",
			"Colombia",
			"Comoros",
			"Congo",
			"Cook Islands --",
			"Costa Rica",
			"Croatia",
			"Cuba",
			"Cyprus",
			"Czech Republic",
			"Democratic Republic of the Congo",
			"Denmark",
			"Djibouti",
			"Dominica",
			"Dominican Republic",
			"East Timor --",
			"Ecuador",
			"Egypt",
			"Salvador",
			"Equatorial Guinea",
			"Eritrea",
			"Estonia",
			"Ethiopia",
			"Faeroe Islands --",
			"Falkland Islands --",
			"Fiji",
			"Finland",
			"Former Yugoslav Republic of Macedonia",
			"France",
			"French Guiana --",
			"French Polynesia --",
			"French Southern Territories --",
			"Gabon",
			"Georgia",
			"Germany",
			"Ghana",
			"Gibraltar --",
			"Greece",
			"Greenland --",
			"Grenada",
			"Guadeloupe --",
			"Guam --",
			"Guatemala",
			"Guinea",
			"Guinea-Bissau",
			"Guyana",
			"Haiti",
			"Heard Island and McDonald Islands --",
			"Honduras",
			"Hong Kong --",
			"Hungary",
			"Iceland",
			"India",
			"Indonesia",
			"Iran",
			"Iraq",
			"Ireland",
			"Israel",
			"Italy",
			"Jamaica",
			"Japan",
			"Jordan",
			"Kazakhstan",
			"Kenya",
			"Kiribati",
			"Kuwait",
			"Kyrgyzstan",
			"Laos",
			"Latvia",
			"Lebanon",
			"Lesotho",
			"Liberia",
			"Libya",
			"Liechtenstein",
			"Lithuania",
			"Luxembourg",
			"Macau --",
			"Madagascar",
			"Malawi",
			"Malaysia",
			"Maldives",
			"Mali",
			"Malta",
			"Marshall Islands",
			"Martinique --",
			"Mauritania",
			"Mauritius",
			"Mayotte --",
			"Mexico",
			"Micronesia",
			"Moldova",
			"Monaco",
			"Mongolia",
			"Montserrat --",
			"Morocco",
			"Mozambique",
			"Myanmar",
			"Namibia",
			"Nauru",
			"Nepal",
			"Netherlands",
			"Netherlands Antilles --",
			"New Caledonia --",
			"New Zealand",
			"Nicaragua",
			"Niger",
			"Nigeria",
			"Niue --",
			"Norfolk Island --",
			"North Korea",
			"Northern Marianas --",
			"Norway",
			"Oman",
			"Pakistan",
			"Palau",
			"Panama",
			"Papua New Guinea",
			"Paraguay",
			"Peru",
			"Philippines",
			"Pitcairn Islands --",
			"Poland",
			"Portugal",
			"Puerto Rico --",
			"Qatar",
			"Réunion --",
			"Romania",
			"Russia",
			"Rwanda",
			"Saint Helena --",
			"Saint Kitts and Nevis",
			"Saint Lucia",
			"Saint Pierre and Miquelon --",
			"Saint Vincent and the Grenadines",
			"Samoa",
			"San Marino",
			"Saudi Arabia",
			"Senegal",
			"Seychelles",
			"Sierra Leone",
			"Singapore",
			"Slovakia",
			"Slovenia",
			"Solomon Islands",
			"Somalia",
			"South Africa",
			"South Korea",
			"Spain",
			"Sri Lanka",
			"Sudan",
			"Suriname",
			"Svalbard and Jan Mayen --",
			"Swaziland",
			"Sweden",
			"Switzerland",
			"Syria",
			"Taiwan",
			"Tajikistan",
			"Tanzania",
			"Thailand",
			"The Bahamas",
			"The Gambia",
			"Togo",
			"Tokelau --",
			"Tonga",
			"Trinidad and Tobago",
			"Tunisia",
			"Turkey",
			"Turkmenistan",
			"Turks and Caicos Islands",
			"Tuvalu",
			"Virgin Islands",
			"Uganda",
			"Ukraine",
			"United Arab Emirates",
			"United Kingdom",
			"United States",
			"Uruguay",
			"Uzbekistan",
			"Vanuatu",
			"Vatican City",
			"Venezuela",
			"Vietnam",
			"Wallis and Futuna --",
			"Western Sahara",
			"Yemen",
			"Yugoslavia",
			"Zambia",
			"Zimbabwe"};
	boolean isNew = true;
}
