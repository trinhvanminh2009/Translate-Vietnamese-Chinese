package translate_vietNamese_chinese;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class SelectContent extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JComboBox cbSelectType;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectContent frame = new SelectContent("","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the frame.
	 */
	public SelectContent(String page, String language) {
		URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
		Scraper scraper = new Scraper();
		setTitle("Download Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 294);
		contentPane = new JPanel();
		contentPane.setForeground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.MAGENTA);
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblStatus.setBounds(15, 5, 509, 22);
		contentPane.add(lblStatus);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Download", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(132, 59, 266, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		cbSelectType = new JComboBox();
		cbSelectType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		cbSelectType.setModel(new DefaultComboBoxModel(new String[] {"Select one item"}));
		cbSelectType.setBounds(20, 25, 215, 30);
		panel.add(cbSelectType);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(20, 73, 89, 23);
		panel.add(btnCancel);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.setBounds(139, 73, 103, 23);
		panel.add(btnDownload);
		
		JLabel lblStatusDownloading = new JLabel("");
		lblStatusDownloading.setBounds(79, 107, 100, 52);
		panel.add(lblStatusDownloading);
		lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/downloading.gif")));
		
		JLabel lblStatusLanguage = new JLabel("Status");
		lblStatusLanguage.setForeground(Color.MAGENTA);
		lblStatusLanguage.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblStatusLanguage.setBounds(15, 38, 464, 14);
		contentPane.add(lblStatusLanguage);
		lblStatusDownloading.setVisible(false);
		
		
		if(language.equals(""))
		{
			lblStatus.setText("Current page prepare download: "+ page);
		}
		else
		{
			lblStatus.setText("Current page prepare download: "+ page);
			lblStatusLanguage.setText("Current language is: "+language);
		}
		
		classifyTheGroups(page, language);
		
		btnDownload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				URL url =  SelectContent.class.getResource("/resources/downloading.gif");
				lblStatusDownloading.setIcon(new ImageIcon(url));
				
				String currentItem = cbSelectType.getSelectedItem().toString();
				System.out.println(currentItem);
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Sports"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable seaGame = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thethao/seagames28.vnp", "vietnamplus_TheThao_sea_game_28");
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					
					Runnable quanVot = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thethao/quanvot.vnp", "vietnamplus_TheThao_QuanVot");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					
					Runnable bongDa = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thethao/quanvot.vnp", "vietnamplus_TheThao_BongDa");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadSeaGame = new Thread(seaGame);
					Thread threadQuanVot = new Thread(quanVot);
					Thread threadBongDa = new Thread(bongDa);
					threadSeaGame.start();
					threadQuanVot.start();
					threadBongDa.start();
					
					
					if(!threadQuanVot.isAlive() && !threadSeaGame.isAlive() && !threadBongDa.isAlive())
					{
						lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));

					}
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Economy"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable kinhTe = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/kinhte.vnp", "vietnamplus_KinhTe");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadKinhTe = new Thread(kinhTe);
					threadKinhTe.start();
					
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Politics"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable chinhTri = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/chinhtri.vnp", "vietnamplus_ChinhTri");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadChinhTri = new Thread(chinhTri);
					threadChinhTri.start();
				}
				
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Society"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable xaHoi = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/xahoi.vnp", "vietnamplus_XaHoi");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadXaHoi = new Thread(xaHoi);
					threadXaHoi.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("World"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable Asean = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/asean.vnp", "vietnamplus_TheGioi_Asean");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Runnable chauATBD = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/chaua-tbd.vnp", "vietnamplus_TheGioi_ChauA_TBD");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Runnable trungDong = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/trungdong.vnp", "vietnamplus_TheGioi_TrungDong");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Runnable chauAu = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/chauau.vnp", "vietnamplus_TheGioi_ChauAu");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Runnable chauMy = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/chaumy.vnp", "vietnamplus_TheGioi_ChauMy");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Runnable chauPhi = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/thegioi/chauphi.vnp", "vietnamplus_TheGioi_ChauPhi");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadAsean = new Thread(Asean);
					Thread threadChauATBD = new Thread(chauATBD);
					Thread threadTrungDong = new Thread(trungDong);
					Thread threadChauAu = new Thread(chauAu);
					Thread threadChauMy = new Thread(chauMy);
					Thread threadChauPhi = new Thread(chauPhi);
					threadAsean.start();
					threadChauATBD.start();
					threadTrungDong.start();
					threadChauAu.start();
					threadChauMy.start();
					threadChauPhi.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Life"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable doiSong = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/doisong.vnp", "vietnamplus_DoiSong");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadDoiSong = new Thread(doiSong);
					threadDoiSong.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Culture"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable vanHoa = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/vanhoa.vnp", "vietnamplus_VanHoa");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadVanHoa = new Thread(vanHoa);
					threadVanHoa.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Science"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable khoaHoc = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/khoahoc.vnp", "vietnamplus_KhoaHoc");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadKhoaHoc = new Thread(khoaHoc);
					threadKhoaHoc.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Technology"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable congNghe = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/congnghe.vnp", "vietnamplus_CongNghe");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadCongNghe = new Thread(congNghe);
					threadCongNghe.start();
				}
				if(page.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese") && currentItem.equals("Strange Story"))
				{
					lblStatusDownloading.setVisible(true);
					Runnable chuyenLa = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								scraper.downloadVietNamPlusVN("http://www.vietnamplus.vn/chuyenla.vnp", "vietnamplus_ChuyenLa");
								lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/done.png")));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					};
					Thread threadChuyenLa = new Thread(chuyenLa);
					threadChuyenLa.start();
				}
				
				
			}
		});
	}
	
	private void classifyTheGroups(String url, String language)
	{
		//Define each item by English. If I define by 2 languages, each item have to define two times
		String politics = new String("Politics");
		String world = new String("World");
		String business = new String("Business");
		String culture = new String("Culture");
		String sports = new String("Sports");
		String technology = new String("Technology");
		String society = new String("Society");
		String health = new String("Health");
		String enviroment = new String("Enviroment");
		String science = new String("Science");
		String travel = new String("Travel");
		String strangeStory = new String("Strange Story");
		String backStage = new String("Backstage");
		String thoiCuoc = new String("Thời cuộc");
		String fashion = new String("Fashion");
		String beautify = new String("Beautify");
		String benLe = new String("Bên lề");
		String family = new String("Family");
		String eatAndPlay = new String("Eat and play");
		String blogger= new String("Blogger");
		String dissipated = new String("Dissipated");
		String multipleChoice = new String("Multiple-Choice");
		String wedding = new String("Weeding");
		String player = new String("Player");
		String news = new String("News");
		String entertaiment = new String("Entertaiment");
		String law = new String("Law");
		String viewAngle = new String("View Angle");
		String education = new String("Education");
		String vehicle = new String("Vehicle");
		String community = new String("Community");
		String talk = new String("Talk");
		String smile = new String("Smile");
		String digitalization = new String("Digitialization");
		String economy = new String("Economy");
		String liveHealthy = new String("Live healthy");
		String admissions = new String("Admissions");
		String lifestyleYoung = new String("Lifestyle young");
		String digitalLifestyle = new String("Digital lifestyle");
		String youRead = new String("You read");
		String needToKnow = new String("Need to know");
		String domestic = new String("Domestic");
		String internaltional = new String("International");
		String union = new String("Union");
		String art = new String("Art");
		String women = new String("Women");
		String local = new String("Local");
		String seaTravel = new String("Sea travel");
		String event = new String("Event");
		String kindness = new String("Kindness");
		String strengthDigital = new String("Strength digital");
		String vehiclePlusPlus = new String("Vehicle plus plus");
		String love = new String("Love");
		String stars = new String("Stars");
		String filmMusic = new String("Film music");
		String men = new String("Men");
		String house = new String("House");
		String photo = new String("Photo");
		String destination = new String("Destination");
		String teen = new String("Teen");
		String shortNews = new String("Short news");
		String passion = new String("Passion");
		String europaLeague = new String("EUROPA LEAGUE");
		String caricatured = new String("Caricatured");
		String identified = new String("Identified");
		String life = new String("Life");
		String analysis = new String("Analysis");
		String medical = new String("Medical");
		String relax = new String("Relax");
		String vietnamAndTheWorld = new String("VietNam and the world");
		String seaIlands = new String("Sea-ilands");
		String policy = new String("Policy");
		String realEstate = new String("Real estate");
		String haNoi = new String("Ha Noi");
		String hoChiMinhCity = new String("Ho Chi Minh city");
		String leader = new String("Leader");
		String friendshipBridge = new String("Friendship-Bridge");
		String documentation = new String("Documentation");
		String asian = new String("Asian");
		String vietnamWindow = new String("VietNam window");
		String theNationOfVietNam = new String("The nation of VietNam");
		String readers = new String("Readers");
		String charity = new String("Charity");
		String integration = new String("Integration");
		String partyActivities = new String ("Party activities");
		String primeMinister = new String("Prime Minister");
		String government = new String("Government");
		String command = new String("Command");
		String newsMinistry = new String("News Ministry");
		String newsLocal = new String("News local");
		String vietNam = new String("Viet Nam");
		String china = new String("China");
		String defense = new String("Defense");
		String security = new String("Security");
		String military = new String("Military");
		String fightingForPeace = new String("Fighting for peace");
		String diplomacy = new String("Diplomacy");
		String partyBuilding = new String("Party Building");
		String research = new String("Research");
		String exchange = new String("Exchange");
		String comment = new String("Comment");
		String theoretical = new String("Theoretical");
		String issue = new String("Issue");
		String foreignAffairs = new String("Foreign Affairs");
		String activityOfThought = new String("Activity of thought");
		String information = new String("Information");
		String friendsFiveContinents = new String("Friends five continents");
		String friends = new String("Friends");
		String trafficSafety = new String("Traffic safety");
		String loveMotherLands = new String("Love motherlands");
		String friendsEverywhere = new String("Friends everywhere");
		String country = new String("Country");
		String thaiNguyenPeople = new String("Thai Nguyen people");
		String homeLand = new String("Homeland");
		String departments = new String("Departments");
		String breakingNews = new String("Breaking news");
		String tanakaUnit = new String("Tanaka unit");
		
		//Define list for each website, include 2 languages for some website
		ArrayList<String>ngoisaoNet = new ArrayList<>();
		ArrayList<String>vnExpress = new ArrayList<>();
		ArrayList<String>tuoiTre = new ArrayList<>();
		ArrayList<String>nguoiLaoDong = new ArrayList<>();
		ArrayList<String>danTri = new ArrayList<>();
		ArrayList<String>ngoiSaoVN = new ArrayList<>();
		ArrayList<String>bongDaPlus = new ArrayList<>();
		ArrayList<String>vietnamPlusVN = new ArrayList<>();
		ArrayList<String>vietnamPlusCN = new ArrayList<>();
		ArrayList<String>baoBinhDuongVN = new ArrayList<>();
		ArrayList<String>baoBinhDuongCN = new ArrayList<>();
		ArrayList<String>nhanDanVN = new ArrayList<>();
		ArrayList<String>nhanDanCN = new ArrayList<>();
		ArrayList<String>saiGonGiaiPhongVN = new ArrayList<>();
		ArrayList<String>saiGonGiaiPhongCN = new ArrayList<>();
		ArrayList<String>baoChinhPhuVN = new ArrayList<>();
		ArrayList<String>baoChinhPhuCN = new ArrayList<>();
		ArrayList<String>quanDoiNhanDanVN = new ArrayList<>();
		ArrayList<String>quanDoiNhanDanCN = new ArrayList<>();
		ArrayList<String>tapChiCongSanVN = new ArrayList<>();
		ArrayList<String>tapChiCongSanCN = new ArrayList<>();
		ArrayList<String>thoiDaiVN = new ArrayList<>();
		ArrayList<String>thoiDaiCN = new ArrayList<>();
		ArrayList<String>baoThaiNguyenVN = new ArrayList<>();		
		ArrayList<String>baoThaiNguyenCN = new ArrayList<>();
		ArrayList<String>dongNaiVN = new ArrayList<>();
		ArrayList<String>dongNaiCN = new ArrayList<>();
		

		//Add items for each website with name website and language selected
		if(url.equals("http://ngoisao.net/") && language.equals(""))
		{
			ngoisaoNet.add(backStage);
			ngoisaoNet.add(thoiCuoc);
			ngoisaoNet.add(fashion);
			ngoisaoNet.add(benLe);
			ngoisaoNet.add(family);
			ngoisaoNet.add(blogger);
			ngoisaoNet.add(player);
			ngoisaoNet.add(multipleChoice);
			ngoisaoNet.add(wedding);
			ngoisaoNet.add(eatAndPlay);
			
			for(int i = 0; i < ngoisaoNet.size(); i++)
			{
				cbSelectType.insertItemAt(ngoisaoNet.get(i), i+1);
			}
			
		}
		if(url.equals("http://vnexpress.net/") && language.equals(""))
		{
			vnExpress.add(news);
			vnExpress.add(viewAngle);
			vnExpress.add(world);
			vnExpress.add(business);
			vnExpress.add(entertaiment);
			vnExpress.add(sports);
			vnExpress.add(law);
			vnExpress.add(education);
			vnExpress.add(health);
			vnExpress.add(family);
			vnExpress.add(travel);
			vnExpress.add(science);
			vnExpress.add(digitalization);
			vnExpress.add(vehicle);
			vnExpress.add(community);
			vnExpress.add(talk);
			vnExpress.add(smile);
			for(int i =0; i< vnExpress.size(); i++)
			{
				cbSelectType.insertItemAt(vnExpress.get(i), i+1);
			}
		}
		if(url.equals("http://tuoitre.vn/") && language.equals(""))
		{
			tuoiTre.add(politics +"-"+society);
			tuoiTre.add(world);
			tuoiTre.add(law);
			tuoiTre.add(economy);
			tuoiTre.add(liveHealthy);
			tuoiTre.add(education);
			tuoiTre.add(admissions);
			tuoiTre.add(sports);
			tuoiTre.add(culture + "-"+ entertaiment);
			tuoiTre.add(lifestyleYoung);
			tuoiTre.add(digitalLifestyle);
			tuoiTre.add(youRead);
			tuoiTre.add(travel);
			tuoiTre.add(needToKnow);
			for(int i = 0; i < tuoiTre.size(); i++)
			{
				cbSelectType.insertItemAt(tuoiTre.get(i), i+1);
			}
		}
		if(url.equals("http://nld.com.vn/") && language.equals(""))
		{
			nguoiLaoDong.add(domestic);
			nguoiLaoDong.add(internaltional);
			nguoiLaoDong.add(economy);
			nguoiLaoDong.add(education);
			nguoiLaoDong.add(union);
			nguoiLaoDong.add(culture + "-"+ art);
			nguoiLaoDong.add(sports);
			nguoiLaoDong.add(health);
			nguoiLaoDong.add(women);
			nguoiLaoDong.add(youRead);
			nguoiLaoDong.add(law);
			nguoiLaoDong.add(local);
			nguoiLaoDong.add(seaTravel);
			for(int i = 0; i < nguoiLaoDong.size(); i++)
			{
				cbSelectType.insertItemAt(nguoiLaoDong.get(i), i+1);
			}
		}
		if(url.equals("http://dantri.com.vn/") && language.equals(""))
		{
			danTri.add(event);
			danTri.add(world);
			danTri.add(sports);
			danTri.add(education);
			danTri.add(kindness);
			danTri.add(business);
			danTri.add(culture);
			danTri.add(entertaiment);
			danTri.add(travel);
			danTri.add(law);
			danTri.add(lifestyleYoung);
			danTri.add(health);
			danTri.add(strengthDigital);
			danTri.add(vehiclePlusPlus);
			danTri.add(love);
			danTri.add(strangeStory);
			for(int i = 0; i < danTri.size(); i++)
			{
				cbSelectType.insertItemAt(danTri.get(i), i+1);
			}
		}
		if(url.equals("https://ngoisao.vn/") && language.equals(""))
		{
			ngoiSaoVN.add(stars);
			ngoiSaoVN.add(fashion);
			ngoiSaoVN.add(filmMusic);
			ngoiSaoVN.add(men);
			ngoiSaoVN.add(women);
			ngoiSaoVN.add(health);
			ngoiSaoVN.add(house);
			ngoiSaoVN.add(photo);
			ngoiSaoVN.add(destination);
			ngoiSaoVN.add(teen);
			ngoiSaoVN.add(strangeStory);
			ngoiSaoVN.add(technology);
			ngoiSaoVN.add(news);
			for(int i = 0; i < ngoiSaoVN.size(); i++)
			{
				cbSelectType.insertItemAt(ngoiSaoVN.get(i), i+1);
			}
		}
		if(url.equals("http://bongdaplus.vn/") && language.equals(""))
		{
			bongDaPlus.add(news);
			bongDaPlus.add(shortNews);
			bongDaPlus.add(stars);
			bongDaPlus.add(passion);
			bongDaPlus.add(europaLeague);
			bongDaPlus.add(caricatured);
			bongDaPlus.add(youRead);
			bongDaPlus.add(identified);
			
			for(int i = 0; i < bongDaPlus.size(); i++)
			{
				cbSelectType.insertItemAt(bongDaPlus.get(i), i+1);
			}
		}
		if(url.equals("https://www.dongnai.gov.vn/") && language.equals("Vietnamese"))
		{
			dongNaiVN.add(news +","+departments);
			dongNaiVN.add(local +" "+news);
			dongNaiVN.add(society +"-"+economy);
			dongNaiVN.add(security +" and "+defense); 
			
			for(int i = 0; i < dongNaiVN.size(); i++)
			{
				cbSelectType.insertItemAt(dongNaiVN.get(i), i+1);
			}
		}
		if(url.equals("https://www.dongnai.gov.vn/") && language.equals("Chinese"))
		{
			dongNaiCN.add(breakingNews);
			dongNaiCN.add(news +","+departments);
			dongNaiCN.add(newsLocal);
			dongNaiCN.add(tanakaUnit);
			for(int i = 0; i < dongNaiCN.size(); i++)
			{
				cbSelectType.insertItemAt(dongNaiCN.get(i), i+1);
			}
		}

		if(url.equals("http://www.nhandan.com.vn/") && language.equals("Vietnamese"))
		{
			nhanDanVN.add(politics);
			nhanDanVN.add(economy);
			nhanDanVN.add(culture);
			nhanDanVN.add(society);
			nhanDanVN.add(world);
			nhanDanVN.add(technology);
			nhanDanVN.add(science);
			nhanDanVN.add(education);
			nhanDanVN.add(health);
			nhanDanVN.add(law);
			nhanDanVN.add(sports);
			nhanDanVN.add(youRead);
			nhanDanVN.add(haNoi);
			nhanDanVN.add(hoChiMinhCity);
			for(int i = 0; i < nhanDanVN.size(); i++)
			{
				cbSelectType.insertItemAt(nhanDanVN.get(i), i+1);
			}
		}
		if(url.equals("http://www.nhandan.com.vn/") && language.equals("Chinese"))
		{
			nhanDanCN.add(leader);
			nhanDanCN.add(politics);
			nhanDanCN.add(internaltional);
			nhanDanCN.add(economy);
			nhanDanCN.add(society);
			nhanDanCN.add(culture);
			nhanDanCN.add(sports);
			nhanDanCN.add(travel);
			nhanDanCN.add(friendshipBridge);
			nhanDanCN.add(documentation);
			nhanDanCN.add(local);
			nhanDanCN.add(asian);
			nhanDanCN.add(vietnamWindow);
			nhanDanCN.add(theNationOfVietNam);
			for(int i = 0; i < nhanDanCN.size(); i++)
			{
				cbSelectType.insertItemAt(nhanDanCN.get(i), i+1);
			}
		}
		if(url.equals("http://baobinhduong.vn/") && language.equals("Vietnamese"))
		{
			baoBinhDuongVN.add(politics);
			baoBinhDuongVN.add(economy);
			baoBinhDuongVN.add(world);
			baoBinhDuongVN.add(society);
			baoBinhDuongVN.add(sports);
			baoBinhDuongVN.add(analysis);
			baoBinhDuongVN.add(youRead);
			baoBinhDuongVN.add(law);
			baoBinhDuongVN.add(medical);
			baoBinhDuongVN.add(culture + "-"+ art);
			baoBinhDuongVN.add(relax);
			for(int i = 0; i < baoBinhDuongVN.size(); i++)
			{
				cbSelectType.insertItemAt(baoBinhDuongVN.get(i), i+1);
			}
		}
		if(url.equals("http://baobinhduong.vn/") && language.equals("Chinese"))
		{
			baoBinhDuongCN.add(vietnamAndTheWorld);
			baoBinhDuongCN.add(seaIlands);
			baoBinhDuongCN.add(politics);
			baoBinhDuongCN.add(economy);
			baoBinhDuongCN.add(internaltional);
			baoBinhDuongCN.add(society);
			baoBinhDuongCN.add(health);
			baoBinhDuongCN.add(culture);
			baoBinhDuongCN.add(policy);
			baoBinhDuongCN.add(travel);
			baoBinhDuongCN.add(technology);
			baoBinhDuongCN.add(enviroment);
			baoBinhDuongCN.add(realEstate);
			for(int i = 0; i < baoBinhDuongCN.size(); i++)
			{
				cbSelectType.insertItemAt(baoBinhDuongCN.get(i), i+1);
			}
		}
		if(url.equals("http://www.vietnamplus.vn/") && language.equals("Vietnamese"))
		{
			vietnamPlusVN.add(economy);
			vietnamPlusVN.add(politics);
			vietnamPlusVN.add(society);
			vietnamPlusVN.add(world);
			vietnamPlusVN.add(life);
			vietnamPlusVN.add(culture);
			vietnamPlusVN.add(sports);
			vietnamPlusVN.add(science);
			vietnamPlusVN.add(technology);
			vietnamPlusVN.add(strangeStory);
			for(int i = 0; i < vietnamPlusVN.size(); i++)
			{
				cbSelectType.insertItemAt(vietnamPlusVN.get(i), i+1);
			}
		}
		if(url.equals("http://www.vietnamplus.vn/") && language.equals("Chinese"))
		{
			vietnamPlusCN.add(politics);
			vietnamPlusCN.add(world);
			vietnamPlusCN.add(economy);
			vietnamPlusCN.add(society);
			vietnamPlusCN.add(culture);
			vietnamPlusCN.add(sports);
			vietnamPlusCN.add(technology);
			for(int i = 0; i < vietnamPlusCN.size(); i++)
			{
				cbSelectType.insertItemAt(vietnamPlusCN.get(i), i+1);
			}
		}
		if(url.equals("http://www.sggp.org.vn/") && language.equals("Vietnamese"))
		{
			saiGonGiaiPhongVN.add(politics);
			saiGonGiaiPhongVN.add(society);
			saiGonGiaiPhongVN.add(law);
			saiGonGiaiPhongVN.add(economy);
			saiGonGiaiPhongVN.add(life+" "+ technology);
			saiGonGiaiPhongVN.add(education);
			saiGonGiaiPhongVN.add(science + " "+ technology);
			saiGonGiaiPhongVN.add(medical +"-"+health);
			saiGonGiaiPhongVN.add(culture + "-"+entertaiment);
			saiGonGiaiPhongVN.add(youRead);
			for(int i = 0; i < saiGonGiaiPhongVN.size(); i++)
			{
				cbSelectType.insertItemAt(saiGonGiaiPhongVN.get(i), i+1);
			}
		}
		if(url.equals("http://www.sggp.org.vn/") && language.equals("Chinese"))
		{
			saiGonGiaiPhongCN.add(politics);
			saiGonGiaiPhongCN.add(law);
			saiGonGiaiPhongCN.add(economy);
			saiGonGiaiPhongCN.add(internaltional);
			saiGonGiaiPhongCN.add(education);
			saiGonGiaiPhongCN.add(sports);
			saiGonGiaiPhongCN.add(science + " and " + technology);
			saiGonGiaiPhongCN.add(health);
			saiGonGiaiPhongCN.add(entertaiment);
			saiGonGiaiPhongCN.add(readers + "-"+charity);
			saiGonGiaiPhongCN.add(travel);
			for(int i = 0; i < saiGonGiaiPhongCN.size(); i++)
			{
				cbSelectType.insertItemAt(saiGonGiaiPhongCN.get(i), i+1);
			}
		}
		if(url.equals("http://baochinhphu.vn/") && language.equals("Vietnamese"))
		{
			baoChinhPhuVN.add(politics);
			baoChinhPhuVN.add(economy);
			baoChinhPhuVN.add(culture);
			baoChinhPhuVN.add(society);
			baoChinhPhuVN.add(education);
			baoChinhPhuVN.add(world);
			baoChinhPhuVN.add(integration);
			for(int i = 0; i < baoChinhPhuVN.size(); i++)
			{
				cbSelectType.insertItemAt(baoChinhPhuVN.get(i), i+1);
			}
		}
		if(url.equals("http://baochinhphu.vn/") && language.equals("Chinese"))
		{
			baoChinhPhuCN.add(partyActivities+"-"+government);
			baoChinhPhuCN.add(primeMinister + "-"+government);
			baoChinhPhuCN.add(command+" " + primeMinister+"-"+government);
			baoChinhPhuCN.add(newsMinistry);
			baoChinhPhuCN.add(newsLocal);
			baoChinhPhuCN.add(economy + "-"+society);
			baoChinhPhuCN.add(science+"-"+technology);
			baoChinhPhuCN.add(culture + "-"+travel);
			baoChinhPhuCN.add(world+"-"+vietNam);
			for(int i = 0; i < baoChinhPhuCN.size(); i++)
			{
				cbSelectType.insertItemAt(baoChinhPhuCN.get(i), i+1);
			}
		}
		if(url.equals("http://www.qdnd.vn/") && language.equals("Vietnamese"))
		{
			quanDoiNhanDanVN.add(politics);
			quanDoiNhanDanVN.add(defense + " "+ security);
			quanDoiNhanDanVN.add(fightingForPeace);
			quanDoiNhanDanVN.add(world + " "+ military);
			quanDoiNhanDanVN.add(law);
			quanDoiNhanDanVN.add(economy);
			quanDoiNhanDanVN.add(society);
			quanDoiNhanDanVN.add(integration);
			quanDoiNhanDanVN.add(culture);
			quanDoiNhanDanVN.add(sports);
			for(int i = 0; i < quanDoiNhanDanVN.size(); i++)
			{
				cbSelectType.insertItemAt(quanDoiNhanDanVN.get(i), i+1);
			}
		}
		if(url.equals("http://www.qdnd.vn/") && language.equals("Chinese"))
		{
			quanDoiNhanDanCN.add(politics + "-"+diplomacy);
			quanDoiNhanDanCN.add(defense+ "-"+security);
			quanDoiNhanDanCN.add(internaltional);
			quanDoiNhanDanCN.add(friendshipBridge);
			quanDoiNhanDanCN.add(economy);
			for(int i = 0; i < quanDoiNhanDanCN.size(); i++)
			{
				cbSelectType.insertItemAt(quanDoiNhanDanCN.get(i), i+1);
			}
		}
		if(url.equals("http://baothainguyen.org.vn/") && language.equals("Vietnamese"))
		{
			baoThaiNguyenVN.add(news);
			baoThaiNguyenVN.add(politics);
			baoThaiNguyenVN.add(economy);
			baoThaiNguyenVN.add(society);
			baoThaiNguyenVN.add(internaltional);
			baoThaiNguyenVN.add(culture);
			baoThaiNguyenVN.add(education);
			baoThaiNguyenVN.add(sports);
			baoThaiNguyenVN.add(science +"-"+ technology);
			baoThaiNguyenVN.add(vehicle);
			baoThaiNguyenVN.add(law);
			baoThaiNguyenVN.add(policy);
			baoThaiNguyenVN.add(trafficSafety);
			baoThaiNguyenVN.add(country +" and "+thaiNguyenPeople);
			baoThaiNguyenVN.add(homeLand +"-"+country);
			for(int i = 0; i < baoThaiNguyenVN.size(); i++)
			{
				cbSelectType.insertItemAt(baoThaiNguyenVN.get(i), i+1);
			}
		}
		if(url.equals("http://baothainguyen.org.vn/") && language.equals("Chinese"))
		{
			baoThaiNguyenCN.add(news);
			for(int i = 0; i < baoThaiNguyenCN.size(); i++)
			{
				cbSelectType.insertItemAt(baoThaiNguyenCN.get(i), i+1);
			}
		}
		if(url.equals("http://thoidai.com.vn/") && language.equals("Vietnamese"))
		{
			thoiDaiVN.add(news);
			thoiDaiVN.add(economy);
			thoiDaiVN.add(education);
			thoiDaiVN.add(health);
			thoiDaiVN.add(friendsFiveContinents);
			thoiDaiVN.add(kindness +" of "+ friends);
			thoiDaiVN.add(culture + "-"+travel);
			thoiDaiVN.add(world);
			thoiDaiVN.add(trafficSafety);
			thoiDaiVN.add(youRead);
			for(int i = 0; i < thoiDaiVN.size(); i++)
			{
				cbSelectType.insertItemAt(thoiDaiVN.get(i), i+1);
			}
		}
		if(url.equals("http://thoidai.com.vn/") && language.equals("Chinese"))
		{
			thoiDaiCN.add(politics);
			thoiDaiCN.add(economy);
			thoiDaiCN.add(culture +" and "+ sports);
			thoiDaiCN.add(vietNam +"-"+china);
			thoiDaiCN.add(society);
			thoiDaiCN.add(loveMotherLands);
			thoiDaiCN.add(vietNam + "-"+ china);
			thoiDaiCN.add(friendsEverywhere);
			thoiDaiCN.add(internaltional);
			for(int i = 0; i < thoiDaiCN.size(); i++)
			{
				cbSelectType.insertItemAt(thoiDaiCN.get(i), i+1);
			}
		}
		if(url.equals("http://www.tapchicongsan.org.vn/") && language.equals("Vietnamese"))
		{
			tapChiCongSanVN.add(politics +"-"+partyBuilding);
			tapChiCongSanVN.add(research +"-"+exchange);
			tapChiCongSanVN.add(comment);
			tapChiCongSanVN.add(theoretical +" "+ information);
			tapChiCongSanVN.add(world +": "+issue +"-"+event);
			tapChiCongSanVN.add(economy);
			tapChiCongSanVN.add(culture +"-"+society);
			tapChiCongSanVN.add(security +" and "+defense);
			tapChiCongSanVN.add(foreignAffairs +" and "+internaltional);
			tapChiCongSanVN.add(activityOfThought);
			for(int i = 0; i < tapChiCongSanVN.size(); i++)
			{
				cbSelectType.insertItemAt(tapChiCongSanVN.get(i), i+1);
			}
		}
		if(url.equals("http://www.tapchicongsan.org.vn/") && language.equals("Chinese"))
		{
			tapChiCongSanCN.add(news);
			tapChiCongSanCN.add(politics + "-"+partyBuilding);
			tapChiCongSanCN.add(economy);
			tapChiCongSanCN.add(culture +"-"+society);
			tapChiCongSanCN.add(diplomacy);
			tapChiCongSanCN.add(world +": "+issue +"-"+event);
			for(int i = 0; i < tapChiCongSanCN.size(); i++)
			{
				cbSelectType.insertItemAt(tapChiCongSanCN.get(i), i+1);
			}
		}
		
	}
}
