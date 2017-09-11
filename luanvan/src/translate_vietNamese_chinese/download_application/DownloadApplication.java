package translate_vietNamese_chinese.download_application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class DownloadApplication {

	private JFrame frmDownloadApplication;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadApplication window = new DownloadApplication();
					window.frmDownloadApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DownloadApplication() {
		initialize();
	}
	
	protected ImageIcon createImageIcon(String path,String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			return null;
		}
}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDownloadApplication = new JFrame();
		frmDownloadApplication.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		frmDownloadApplication.getContentPane().setBackground(Color.GRAY);
		frmDownloadApplication.getContentPane().setLayout(null);
		frmDownloadApplication.setLocationRelativeTo(null);
		ButtonGroup groupSelectPage = new ButtonGroup();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
		tabbedPane.setBounds(0, 0, 632, 424);
		frmDownloadApplication.getContentPane().add(tabbedPane);
		/////////////////////////////////////Monolingual handle///////////////////////////////////////////////////////////////////
		JPanel panel_Monolingual = new JPanel();
		tabbedPane.addTab("Monolingual", null, panel_Monolingual, null);
		panel_Monolingual.setForeground(Color.GREEN);
		panel_Monolingual.setBorder(new TitledBorder(null, "Select page", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JRadioButton rdbtnVnExpress = new JRadioButton("https://vnexpress.net");
		rdbtnVnExpress.setBounds(6, 73, 191, 23);
		rdbtnVnExpress.setActionCommand("https://vnexpress.net");
		
		JRadioButton rdbtnNguoiLaoDong = new JRadioButton("http://nld.com.vn/");
		rdbtnNguoiLaoDong.setBounds(6, 160, 177, 23);
		rdbtnNguoiLaoDong.setActionCommand("http://nld.com.vn/");
		
		JRadioButton rdbtnDanTri = new JRadioButton("http://dantri.com.vn/");
		rdbtnDanTri.setBounds(224, 35, 177, 23);
		rdbtnDanTri.setActionCommand("http://dantri.com.vn/");
		
		JRadioButton rdbtnNgoiSaoNet = new JRadioButton("http://ngoisao.net/");
		rdbtnNgoiSaoNet.setBounds(6, 35, 191, 23);
		rdbtnNgoiSaoNet.setActionCommand("http://ngoisao.net/");
		
		JRadioButton rdbtnNgoiSaoVN = new JRadioButton("https://ngoisao.vn/");
		rdbtnNgoiSaoVN.setBounds(224, 73, 177, 23);
		rdbtnNgoiSaoVN.setActionCommand("https://ngoisao.vn/");
		
		JRadioButton rdbtnTuoitre = new JRadioButton("http://tuoitre.vn/");
		rdbtnTuoitre.setBounds(6, 117, 177, 23);
		rdbtnTuoitre.setActionCommand("http://tuoitre.vn/");
		
		JRadioButton rdbtnBongDaPlus = new JRadioButton("http://bongdaplus.vn/");
		rdbtnBongDaPlus.setBounds(224, 117, 191, 23);
		rdbtnBongDaPlus.setActionCommand("http://bongdaplus.vn/");
		
		JButton btnNext_Monoligual = new JButton("Next");
		btnNext_Monoligual.setBounds(369, 209, 73, 23);
		
		
	
		JList list = new JList();
		list.setBounds(-10008, -10031, 0, 0);
		panel_Monolingual.setLayout(null);
		panel_Monolingual.add(rdbtnVnExpress);
		panel_Monolingual.add(rdbtnNguoiLaoDong);
		panel_Monolingual.add(rdbtnDanTri);
		panel_Monolingual.add(rdbtnNgoiSaoNet);
		panel_Monolingual.add(btnNext_Monoligual);
		panel_Monolingual.add(rdbtnNgoiSaoVN);
		panel_Monolingual.add(rdbtnTuoitre);
		panel_Monolingual.add(rdbtnBongDaPlus);
		panel_Monolingual.add(list);
		
		//Set command for each button
		groupSelectPage.add(rdbtnNgoiSaoNet);
		groupSelectPage.add(rdbtnDanTri);
		groupSelectPage.add(rdbtnNguoiLaoDong);
		groupSelectPage.add(rdbtnTuoitre);
		groupSelectPage.add(rdbtnVnExpress);
		groupSelectPage.add(rdbtnNgoiSaoVN);
		groupSelectPage.add(rdbtnBongDaPlus);
		JButton btnCancel_Monolingual = new JButton("Cancel");
		btnCancel_Monolingual.setBounds(190, 209, 89, 23);
		panel_Monolingual.add(btnCancel_Monolingual);
		
		
		JLabel lbStatus_Monolingual = new JLabel("Status");
		lbStatus_Monolingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus_Monolingual.setForeground(Color.RED);
		lbStatus_Monolingual.setBounds(27, 11, 374, 23);
		panel_Monolingual.add(lbStatus_Monolingual);
		
		JButton btnResume = new JButton("Resume");
		btnResume.setBounds(10, 209, 110, 23);
		panel_Monolingual.add(btnResume);
		
	
		
		lbStatus_Monolingual.setVisible(false);
		btnCancel_Monolingual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				groupSelectPage.clearSelection();
				
			}
		});
		
		btnNext_Monoligual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Get action comment to parse to SelectContent
				if(groupSelectPage.getSelection() != null)
				{
					SelectContent selectContent = new SelectContent(groupSelectPage.getSelection().getActionCommand(),"",false);
					lbStatus_Monolingual.setVisible(false);
					selectContent.show();
				}
				else
				{
					lbStatus_Monolingual.setText("Please select a page you want to download");
					lbStatus_Monolingual.setVisible(true);
				}
				
			}
		});

		if(SelectContent.checkResumeable()){
                    btnResume.setEnabled(true);
                }
                else{
                    btnResume.setEnabled(false);
                }
		btnResume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectContent selectContent = new SelectContent("","",true);
                                selectContent.show();
			}
		});
		
		/////////////////////////////////////////////////////Bilingual handle///////////////////////////////////////////////////////////////
		JPanel panel_Bilingual = new JPanel();
		tabbedPane.addTab("Bilingual", null, panel_Bilingual, null);
		panel_Bilingual.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Select page and language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, -23, 627, 420);
		panel_Bilingual.add(panel);
		panel.setLayout(null);
		
		JLabel lblStatus_Bilingual = new JLabel("Status");
		lblStatus_Bilingual.setBounds(20, 25, 398, 16);
		panel.add(lblStatus_Bilingual);
		lblStatus_Bilingual.setForeground(Color.RED);
		lblStatus_Bilingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStatus_Bilingual.setVisible(false);
		
		
		JRadioButton rdbtnVietnamPlus = new JRadioButton("http://www.vietnamplus.vn");
		rdbtnVietnamPlus.setBounds(20, 44, 242, 23);
		rdbtnVietnamPlus.setActionCommand("http://www.vietnamplus.vn");
		panel.add(rdbtnVietnamPlus);
		
		
		JRadioButton rdbtnDongNaiGov = new JRadioButton("https://www.dongnai.gov.vn/");
		rdbtnDongNaiGov.setBounds(20, 80, 230, 23);
		rdbtnDongNaiGov.setActionCommand("https://www.dongnai.gov.vn/");
		panel.add(rdbtnDongNaiGov);
		
		JRadioButton rdbtnSaiGonGiaiPhong = new JRadioButton("http://www.sggp.org.vn/");
		rdbtnSaiGonGiaiPhong.setBounds(20, 121, 217, 23);
		rdbtnSaiGonGiaiPhong.setActionCommand("http://www.sggp.org.vn/");
		panel.add(rdbtnSaiGonGiaiPhong);
		
		JRadioButton rdbtnBaoDinhDuong = new JRadioButton("http://baobinhduong.vn/");
		rdbtnBaoDinhDuong.setBounds(266, 44, 200, 23);
		rdbtnBaoDinhDuong.setActionCommand("http://baobinhduong.vn/");
		panel.add(rdbtnBaoDinhDuong);
		
		JRadioButton rdbtnNhanDan = new JRadioButton("http://www.nhandan.com.vn/");
		rdbtnNhanDan.setBounds(266, 80, 240, 23);
		rdbtnNhanDan.setActionCommand("http://www.nhandan.com.vn/");
		panel.add(rdbtnNhanDan);
		
		JRadioButton rdbtnBaoChinhPhu = new JRadioButton("http://baochinhphu.vn/");
		rdbtnBaoChinhPhu.setBounds(266, 121, 200, 23);
		rdbtnBaoChinhPhu.setActionCommand("http://baochinhphu.vn/");
		panel.add(rdbtnBaoChinhPhu);
		
		JRadioButton rdbtnQuanDoiNhanDan = new JRadioButton("http://www.qdnd.vn/");
		rdbtnQuanDoiNhanDan.setBounds(20, 168, 174, 23);
		rdbtnQuanDoiNhanDan.setActionCommand("http://www.qdnd.vn/");
		panel.add(rdbtnQuanDoiNhanDan);
		
		JRadioButton rdbtnTapChiCongSan = new JRadioButton("http://www.tapchicongsan.org.vn/");
		rdbtnTapChiCongSan.setBounds(266, 168, 267, 23);
		rdbtnTapChiCongSan.setActionCommand("http://www.tapchicongsan.org.vn/");
		panel.add(rdbtnTapChiCongSan);
		
		JRadioButton rdbtnThoiDai = new JRadioButton("http://thoidai.com.vn/");
		rdbtnThoiDai.setBounds(20, 210, 187, 23);
		rdbtnThoiDai.setActionCommand("http://thoidai.com.vn/");
		panel.add(rdbtnThoiDai);
		
		JRadioButton rdbtnBaoThaiNguyen = new JRadioButton("http://baothainguyen.org.vn/");
		rdbtnBaoThaiNguyen.setBounds(266, 210, 240, 23);
		rdbtnBaoThaiNguyen.setActionCommand("http://baothainguyen.org.vn/");
		panel.add(rdbtnBaoThaiNguyen);
		
		JPanel panel_languages = new JPanel();
		panel_languages.setBorder(new TitledBorder(null, "Select language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_languages.setBounds(300, 253, 135, 78);
		panel.add(panel_languages);
		panel_languages.setLayout(null);
		
		JRadioButton rdbtnVietnamese = new JRadioButton("Vietnamese");
		rdbtnVietnamese.setBounds(6, 18, 121, 23);
		panel_languages.add(rdbtnVietnamese);
		rdbtnVietnamese.setActionCommand("Vietnamese");
		
		JRadioButton rdbtnChinese = new JRadioButton("Chinese");
		rdbtnChinese.setBounds(6, 44, 121, 23);
		panel_languages.add(rdbtnChinese);
		rdbtnChinese.setActionCommand("Chinese");
		
		JButton btnClose_Bilingual = new JButton("Close");
		btnClose_Bilingual.setBounds(24, 347, 89, 23);
		panel.add(btnClose_Bilingual);
		
		JButton btnCancel_Bilingual = new JButton("Cancel");
		btnCancel_Bilingual.setBounds(201, 347, 89, 23);
		panel.add(btnCancel_Bilingual);
		
		JButton btnNext_Bilingual = new JButton("Next");
		btnNext_Bilingual.setBounds(346, 347, 89, 23);
		panel.add(btnNext_Bilingual);
		//Add all radio button into one group to make sure only select one radio button in group in one time
		ButtonGroup radioGroupBilingual = new ButtonGroup();
		radioGroupBilingual.add(rdbtnVietnamPlus);
		radioGroupBilingual.add(rdbtnDongNaiGov);
		radioGroupBilingual.add(rdbtnSaiGonGiaiPhong);
		radioGroupBilingual.add(rdbtnBaoDinhDuong);
		radioGroupBilingual.add(rdbtnNhanDan);
		radioGroupBilingual.add(rdbtnTapChiCongSan);
		radioGroupBilingual.add(rdbtnThoiDai);
		radioGroupBilingual.add(rdbtnBaoThaiNguyen);
		radioGroupBilingual.add(rdbtnBaoChinhPhu);
		radioGroupBilingual.add(rdbtnQuanDoiNhanDan);
		
		ButtonGroup groupLanguages = new ButtonGroup();
		groupLanguages.add(rdbtnVietnamese);
		groupLanguages.add(rdbtnChinese);
		btnClose_Bilingual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
		});
		
		btnCancel_Bilingual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				radioGroupBilingual.clearSelection();
				groupLanguages.clearSelection();				
			}
		});
	
		btnNext_Bilingual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(radioGroupBilingual.getSelection() != null && groupLanguages.getSelection()!= null)
				{
					SelectContent selectContent = new SelectContent(radioGroupBilingual.getSelection().getActionCommand(),
							groupLanguages.getSelection().getActionCommand(),false);		
					lblStatus_Bilingual.setVisible(false);
					selectContent.show();
				}
				else
				{
					lblStatus_Bilingual.setText("Please select a page and language you want to download ");
					lblStatus_Bilingual.setVisible(true);
				}
			}
		});
		
		frmDownloadApplication.setBackground(Color.GRAY);
		frmDownloadApplication.setTitle("Download Application");
		URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
		frmDownloadApplication.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
		frmDownloadApplication.setBounds(100, 100, 644, 424);
		frmDownloadApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
