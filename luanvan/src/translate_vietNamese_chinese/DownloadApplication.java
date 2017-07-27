package translate_vietNamese_chinese;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JList;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;

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
			System.err.println("Couldn't find file: " + path);
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
		Icon icon = createImageIcon("D:\\Dowloads\\luanvan\\images\\ic_download.png", "Icon for Monolingual");
		tabbedPane.setBounds(0, 0, 486, 385);
		frmDownloadApplication.getContentPane().add(tabbedPane);
		/////////////////////////////////////Monolingual handle///////////////////////////////////////////////////////////////////
		JPanel panel_Monolingual = new JPanel();
		tabbedPane.addTab("Monolingual", null, panel_Monolingual, null);
		panel_Monolingual.setForeground(Color.GREEN);
		panel_Monolingual.setBorder(new TitledBorder(null, "Select page", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JRadioButton rdbtnVnExpress = new JRadioButton("http://vnexpress.net/");
		rdbtnVnExpress.setBounds(6, 73, 160, 23);
		rdbtnVnExpress.setActionCommand("http://vnexpress.net/");
		
		JRadioButton rdbtnNguoiLaoDong = new JRadioButton("http://nld.com.vn/");
		rdbtnNguoiLaoDong.setBounds(6, 160, 141, 23);
		rdbtnNguoiLaoDong.setActionCommand("http://nld.com.vn/");
		
		JRadioButton rdbtnDanTri = new JRadioButton("http://dantri.com.vn/");
		rdbtnDanTri.setBounds(224, 35, 140, 23);
		rdbtnDanTri.setActionCommand("http://dantri.com.vn/");
		
		JRadioButton rdbtnNgoiSaoNet = new JRadioButton("http://ngoisao.net/");
		rdbtnNgoiSaoNet.setBounds(6, 35, 191, 23);
		rdbtnNgoiSaoNet.setActionCommand("http://ngoisao.net/");
		
		JRadioButton rdbtnNgoiSaoVN = new JRadioButton("https://ngoisao.vn/");
		rdbtnNgoiSaoVN.setBounds(224, 73, 134, 23);
		rdbtnNgoiSaoVN.setActionCommand("https://ngoisao.vn/");
		
		JRadioButton rdbtnTuoitre = new JRadioButton("http://tuoitre.vn/");
		rdbtnTuoitre.setBounds(6, 117, 141, 23);
		rdbtnTuoitre.setActionCommand("http://tuoitre.vn/");
		
		JRadioButton rdbtnBongDaPlus = new JRadioButton("http://bongdaplus.vn/");
		rdbtnBongDaPlus.setBounds(224, 117, 167, 23);
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
		rdbtnNgoiSaoNet.setActionCommand("http://www.vietnamplus.vn/");
		groupSelectPage.add(rdbtnNgoiSaoNet);
		groupSelectPage.add(rdbtnDanTri);
		groupSelectPage.add(rdbtnNguoiLaoDong);
		groupSelectPage.add(rdbtnTuoitre);
		groupSelectPage.add(rdbtnVnExpress);
		groupSelectPage.add(rdbtnNgoiSaoVN);
		groupSelectPage.add(rdbtnBongDaPlus);
		JButton btnCancel_Monolingual = new JButton("Cancel");
		btnCancel_Monolingual.setBounds(190, 209, 73, 23);
		panel_Monolingual.add(btnCancel_Monolingual);
		
		
		JLabel lbStatus_Monolingual = new JLabel("Status");
		lbStatus_Monolingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus_Monolingual.setForeground(Color.RED);
		lbStatus_Monolingual.setBounds(27, 11, 374, 23);
		panel_Monolingual.add(lbStatus_Monolingual);
		
		JButton btnClose_Monilingual = new JButton("Close");
		btnClose_Monilingual.setBounds(10, 209, 89, 23);
		panel_Monolingual.add(btnClose_Monilingual);
		
	
		
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
					SelectContent selectContent = new SelectContent(groupSelectPage.getSelection().getActionCommand());
					DownloadApplication downloadApplication = new DownloadApplication();
					downloadApplication.frmDownloadApplication.setVisible(true);
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
		
		btnClose_Monilingual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		/////////////////////////////////////////////////////Bilingual handle///////////////////////////////////////////////////////////////
		JPanel panel_Bilingual = new JPanel();
		tabbedPane.addTab("Bilingual", null, panel_Bilingual, null);
		panel_Bilingual.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Select page and language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, -23, 461, 393);
		panel_Bilingual.add(panel);
		panel.setLayout(null);
		
		JLabel lblStatus_Bilingual = new JLabel("Status");
		lblStatus_Bilingual.setBounds(20, 25, 398, 16);
		panel.add(lblStatus_Bilingual);
		lblStatus_Bilingual.setForeground(Color.RED);
		lblStatus_Bilingual.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStatus_Bilingual.setVisible(false);
		
		
		JRadioButton rdbtnVietnamPlus = new JRadioButton("http://www.vietnamplus.vn/");
		rdbtnVietnamPlus.setBounds(20, 44, 217, 23);
		rdbtnVietnamPlus.setActionCommand("http://www.vietnamplus.vn/");
		panel.add(rdbtnVietnamPlus);
		
		
		JRadioButton rdbtnDongNaiGov = new JRadioButton("https://www.dongnai.gov.vn/");
		rdbtnDongNaiGov.setBounds(20, 80, 187, 23);
		rdbtnDongNaiGov.setActionCommand("https://www.dongnai.gov.vn/");
		panel.add(rdbtnDongNaiGov);
		
		JRadioButton rdbtnSaiGonGiaiPhong = new JRadioButton("http://www.sggp.org.vn/");
		rdbtnSaiGonGiaiPhong.setBounds(20, 121, 187, 23);
		rdbtnSaiGonGiaiPhong.setActionCommand("http://www.sggp.org.vn/");
		panel.add(rdbtnSaiGonGiaiPhong);
		
		JRadioButton rdbtnBaoDinhDuong = new JRadioButton("http://baobinhduong.vn/");
		rdbtnBaoDinhDuong.setBounds(239, 44, 174, 23);
		rdbtnBaoDinhDuong.setActionCommand("http://baobinhduong.vn/");
		panel.add(rdbtnBaoDinhDuong);
		
		JRadioButton rdbtnNhanDan = new JRadioButton("http://www.nhandan.com.vn/");
		rdbtnNhanDan.setBounds(239, 80, 200, 23);
		panel.add(rdbtnNhanDan);
		
		JRadioButton rdbtnBaoChinhPhu = new JRadioButton("http://baochinhphu.vn/");
		rdbtnBaoChinhPhu.setBounds(239, 121, 174, 23);
		rdbtnBaoChinhPhu.setActionCommand("http://baochinhphu.vn/");
		panel.add(rdbtnBaoChinhPhu);
		
		JRadioButton rdbtnQuanDoiNhanDan = new JRadioButton("http://www.qdnd.vn/");
		rdbtnQuanDoiNhanDan.setBounds(20, 168, 174, 23);
		rdbtnQuanDoiNhanDan.setActionCommand("http://www.qdnd.vn/");
		panel.add(rdbtnQuanDoiNhanDan);
		
		JRadioButton rdbtnTapChiCongSan = new JRadioButton("http://www.tapchicongsan.org.vn/");
		rdbtnTapChiCongSan.setBounds(239, 168, 217, 23);
		rdbtnTapChiCongSan.setActionCommand("http://www.tapchicongsan.org.vn/");
		panel.add(rdbtnTapChiCongSan);
		
		JRadioButton rdbtnThoiDai = new JRadioButton("http://thoidai.com.vn/");
		rdbtnThoiDai.setBounds(20, 210, 154, 23);
		rdbtnThoiDai.setActionCommand("http://thoidai.com.vn/");
		panel.add(rdbtnThoiDai);
		
		JRadioButton rdbtnBaoThaiNguyen = new JRadioButton("http://baothainguyen.org.vn/");
		rdbtnBaoThaiNguyen.setBounds(239, 210, 194, 23);
		rdbtnBaoThaiNguyen.setActionCommand("http://baothainguyen.org.vn/");
		panel.add(rdbtnBaoThaiNguyen);
		
		JPanel panel_languages = new JPanel();
		panel_languages.setBorder(new TitledBorder(null, "Select language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_languages.setBounds(273, 253, 135, 78);
		panel.add(panel_languages);
		panel_languages.setLayout(null);
		
		JRadioButton rdbtnVietnamese = new JRadioButton("Vietnamese");
		rdbtnVietnamese.setBounds(6, 18, 102, 23);
		panel_languages.add(rdbtnVietnamese);
		
		JRadioButton rdbtnChinese = new JRadioButton("Chinese");
		rdbtnChinese.setBounds(6, 44, 102, 23);
		panel_languages.add(rdbtnChinese);
		
		JButton btnClose_Bilingual = new JButton("Close");
		btnClose_Bilingual.setBounds(24, 347, 89, 23);
		panel.add(btnClose_Bilingual);
		
		JButton btnCancel_Bilingual = new JButton("Cancel");
		btnCancel_Bilingual.setBounds(163, 347, 89, 23);
		panel.add(btnCancel_Bilingual);
		
		JButton btnNext_Bilingual = new JButton("Next");
		btnNext_Bilingual.setBounds(348, 347, 89, 23);
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

		tabbedPane.setIconAt(1, icon);
		
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
				
			}
		});
		
		frmDownloadApplication.setBackground(Color.GRAY);
		frmDownloadApplication.setTitle("Download Application");
		frmDownloadApplication.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Dowloads\\luanvan\\images\\ic_download.png"));
		frmDownloadApplication.setBounds(100, 100, 492, 424);
		frmDownloadApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
