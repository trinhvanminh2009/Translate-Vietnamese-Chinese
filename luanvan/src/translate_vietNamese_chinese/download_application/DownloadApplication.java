package translate_vietNamese_chinese.download_application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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

import translate_vietNamese_chinese.download_application.designer.Designer;

import java.awt.Window.Type;
import java.awt.SystemColor;

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
					window.frmDownloadApplication.setLocationRelativeTo(null);

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

	protected ImageIcon createImageIcon(String path, String description) {
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
		frmDownloadApplication.setType(Type.POPUP);
		frmDownloadApplication.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		frmDownloadApplication.getContentPane().setBackground(Color.GRAY);
		frmDownloadApplication.getContentPane().setLayout(null);
		frmDownloadApplication.setLocationRelativeTo(null);
		frmDownloadApplication.setFont(new Font("Serif", Font.BOLD, 12));
		ButtonGroup groupSelectPage = new ButtonGroup();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		tabbedPane.setBounds(0, 0, 632, 424);
		frmDownloadApplication.getContentPane().add(tabbedPane);
		///////////////////////////////////// Monolingual
		///////////////////////////////////// handle///////////////////////////////////////////////////////////////////
		JPanel panel_Monolingual = new JPanel();
		tabbedPane.addTab("Monolingual", null, panel_Monolingual, null);
		panel_Monolingual.setForeground(Color.GREEN);
		panel_Monolingual
				.setBorder(new TitledBorder(null, "Select page", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JRadioButton rdbtnVnExpress = new JRadioButton("https://vnexpress.net");
		rdbtnVnExpress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnVnExpress.setBounds(6, 73, 191, 23);
		rdbtnVnExpress.setActionCommand("https://vnexpress.net");

		JRadioButton rdbtnNguoiLaoDong = new JRadioButton("http://nld.com.vn/");
		rdbtnNguoiLaoDong.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNguoiLaoDong.setBounds(6, 160, 177, 23);
		rdbtnNguoiLaoDong.setActionCommand("http://nld.com.vn/");

		JRadioButton rdbtnDanTri = new JRadioButton("http://dantri.com.vn/");
		rdbtnDanTri.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnDanTri.setBounds(224, 35, 177, 23);
		rdbtnDanTri.setActionCommand("http://dantri.com.vn/");

		JRadioButton rdbtnNgoiSaoNet = new JRadioButton("http://ngoisao.net/");
		rdbtnNgoiSaoNet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNgoiSaoNet.setBounds(6, 35, 191, 23);
		rdbtnNgoiSaoNet.setActionCommand("http://ngoisao.net/");

		JRadioButton rdbtnNgoiSaoVN = new JRadioButton("https://ngoisao.vn/");
		rdbtnNgoiSaoVN.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNgoiSaoVN.setBounds(224, 73, 177, 23);
		rdbtnNgoiSaoVN.setActionCommand("https://ngoisao.vn/");

		JRadioButton rdbtnTuoitre = new JRadioButton("http://tuoitre.vn/");
		rdbtnTuoitre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnTuoitre.setBounds(6, 117, 177, 23);
		rdbtnTuoitre.setActionCommand("http://tuoitre.vn/");

		JRadioButton rdbtnBongDaPlus = new JRadioButton("http://bongdaplus.vn/");
		rdbtnBongDaPlus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnBongDaPlus.setBounds(224, 117, 191, 23);
		rdbtnBongDaPlus.setActionCommand("http://bongdaplus.vn/");

		JList list = new JList();
		list.setBounds(-10008, -10031, 0, 0);
		panel_Monolingual.setLayout(null);
		panel_Monolingual.add(rdbtnVnExpress);
		panel_Monolingual.add(rdbtnNguoiLaoDong);
		panel_Monolingual.add(rdbtnDanTri);
		panel_Monolingual.add(rdbtnNgoiSaoNet);

		panel_Monolingual.add(rdbtnNgoiSaoVN);
		panel_Monolingual.add(rdbtnTuoitre);
		panel_Monolingual.add(rdbtnBongDaPlus);
		panel_Monolingual.add(list);

		// Set command for each button
		groupSelectPage.add(rdbtnNgoiSaoNet);
		groupSelectPage.add(rdbtnDanTri);
		groupSelectPage.add(rdbtnNguoiLaoDong);
		groupSelectPage.add(rdbtnTuoitre);
		groupSelectPage.add(rdbtnVnExpress);
		groupSelectPage.add(rdbtnNgoiSaoVN);
		groupSelectPage.add(rdbtnBongDaPlus);
		BufferedImage btnCancelImage;
		try {
			btnCancelImage = ImageIO.read(this.getClass().getResource("/resources/button_cancel.png"));
			JButton btnCancel_Monolingual = new JButton(new ImageIcon(btnCancelImage));
			btnCancel_Monolingual.setBounds(190, 209, 135, 50);
			panel_Monolingual.add(btnCancel_Monolingual);
			btnCancel_Monolingual.setBorder(BorderFactory.createEmptyBorder());
			btnCancel_Monolingual.setContentAreaFilled(false);
			btnCancel_Monolingual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					groupSelectPage.clearSelection();

				}
			});

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel lbStatus_Monolingual = new JLabel("Status");
		lbStatus_Monolingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus_Monolingual.setForeground(Color.RED);
		lbStatus_Monolingual.setBounds(27, 11, 374, 23);
		panel_Monolingual.add(lbStatus_Monolingual);

		try {
			BufferedImage btnResumeImage = ImageIO.read(this.getClass().getResource("/resources/button_resume.png"));
			JButton btnResume_Monolingual = new JButton(new ImageIcon(btnResumeImage));
			btnResume_Monolingual.setBorder(BorderFactory.createEmptyBorder());
			btnResume_Monolingual.setBounds(10, 209, 135, 50);
			btnResume_Monolingual.setContentAreaFilled(false);
			panel_Monolingual.add(btnResume_Monolingual);
			lbStatus_Monolingual.setVisible(false);
			if (SelectContent.checkResumeable()) {
				btnResume_Monolingual.setEnabled(true);
			} else {
				btnResume_Monolingual.setEnabled(false);
			}
			btnResume_Monolingual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SelectContent selectContent = new SelectContent("", "", true);
					selectContent.show();
					selectContent.setLocationRelativeTo(null);
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedImage btnNextImage;
		try {
			btnNextImage = ImageIO.read(this.getClass().getResource("/resources/button_resume.png"));
			JButton btnNext_Monoligual = new JButton(new ImageIcon(btnNextImage));
			panel_Monolingual.add(btnNext_Monoligual);
			btnNext_Monoligual.setBounds(369, 209, 120, 50);
			btnNext_Monoligual.setBorder(BorderFactory.createEmptyBorder());
			btnNext_Monoligual.setContentAreaFilled(false);
			btnNext_Monoligual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// Get action comment to parse to SelectContent
					if (groupSelectPage.getSelection() != null) {
						SelectContent selectContent = new SelectContent(
								groupSelectPage.getSelection().getActionCommand(), "", false);
						lbStatus_Monolingual.setVisible(false);
						selectContent.setLocationRelativeTo(null);
						selectContent.show();
					} else {
						lbStatus_Monolingual.setText("Please select a page you want to download");
						lbStatus_Monolingual.setVisible(true);
					}

				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		///////////////////////////////////////////////////// Bilingual
		///////////////////////////////////////////////////// handle///////////////////////////////////////////////////////////////
		JPanel panel_Bilingual = new JPanel();
		tabbedPane.addTab("Bilingual", null, panel_Bilingual, null);
		panel_Bilingual.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Select page and language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, -23, 627, 420);
		panel_Bilingual.add(panel);
		panel.setLayout(null);

		JLabel lblStatus_Bilingual = new JLabel("Status");
		lblStatus_Bilingual.setBounds(20, 25, 398, 16);
		panel.add(lblStatus_Bilingual);
		lblStatus_Bilingual.setForeground(Color.RED);
		lblStatus_Bilingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStatus_Bilingual.setVisible(false);

		JRadioButton rdbtnVietnamPlus = new JRadioButton("http://www.vietnamplus.vn/");
		rdbtnVietnamPlus.setBounds(20, 44, 242, 23);
		rdbtnVietnamPlus.setActionCommand("http://www.vietnamplus.vn/");
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
		rdbtnNhanDan.setBounds(266, 80, 187, 23);
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
		panel_languages.setBorder(
				new TitledBorder(null, "Select language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_languages.setBounds(472, 48, 135, 78);
		panel.add(panel_languages);
		panel_languages.setLayout(null);

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

		JRadioButton rdbtnVietnamese = new JRadioButton("Vietnamese");
		rdbtnVietnamese.setBounds(6, 18, 121, 23);
		panel_languages.add(rdbtnVietnamese);
		rdbtnVietnamese.setActionCommand("Vietnamese");

		JRadioButton rdbtnChinese = new JRadioButton("Chinese");
		rdbtnChinese.setBounds(6, 44, 121, 23);
		panel_languages.add(rdbtnChinese);
		rdbtnChinese.setActionCommand("Chinese");
		ButtonGroup groupLanguages = new ButtonGroup();
		groupLanguages.add(rdbtnVietnamese);
		groupLanguages.add(rdbtnChinese);

		BufferedImage btnCloseImage;
		try {
			btnCloseImage = ImageIO.read(this.getClass().getResource("/resources/button_close.png"));
			JButton btnClose_Bilingual = new JButton(new ImageIcon(btnCloseImage));
			btnClose_Bilingual.setBounds(20, 288, 119, 55);
			btnClose_Bilingual.setBorder(BorderFactory.createEmptyBorder());
			btnClose_Bilingual.setContentAreaFilled(false);
			panel.add(btnClose_Bilingual);
			btnClose_Bilingual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);

				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			btnCancelImage = ImageIO.read(this.getClass().getResource("/resources/button_cancel.png"));
			JButton btnCancel_Bilingual = new JButton(new ImageIcon(btnCancelImage));
			btnCancel_Bilingual.setBounds(194, 288, 119, 50);
			btnCancel_Bilingual.setBorder(BorderFactory.createEmptyBorder());
			btnCancel_Bilingual.setContentAreaFilled(false);
			panel.add(btnCancel_Bilingual);
			btnCancel_Bilingual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					radioGroupBilingual.clearSelection();
					groupLanguages.clearSelection();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			btnNextImage = ImageIO.read(this.getClass().getResource("/resources/button_next.png"));
			JButton btnNext_Bilingual = new JButton(new ImageIcon(btnNextImage));
			btnNext_Bilingual.setBounds(417, 288, 100, 50);
			btnNext_Bilingual.setBorder(BorderFactory.createEmptyBorder());
			btnNext_Bilingual.setContentAreaFilled(false);
			panel.add(btnNext_Bilingual);
			btnNext_Bilingual.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (radioGroupBilingual.getSelection() != null && groupLanguages.getSelection() != null) {
						SelectContent selectContent = new SelectContent(
								radioGroupBilingual.getSelection().getActionCommand(),
								groupLanguages.getSelection().getActionCommand(), false);
						lblStatus_Bilingual.setVisible(false);
						selectContent.setLocationRelativeTo(null);
						selectContent.show();
					} else {
						lblStatus_Bilingual.setText("Please select a page and language you want to download ");
						lblStatus_Bilingual.setVisible(true);
						lblStatus_Bilingual.setVisible(false);
					}
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Add all radio button into one group to make sure only select one
		// radio button in group in one time

		

		frmDownloadApplication.setBackground(SystemColor.textHighlight);
		frmDownloadApplication.setTitle("Download Application");
		URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
		frmDownloadApplication.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
		frmDownloadApplication.setBounds(100, 100, 644, 424);
		frmDownloadApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
