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
		frmDownloadApplication.getContentPane().setBackground(Color.GRAY);
		frmDownloadApplication.getContentPane().setLayout(null);
		ButtonGroup groupSelectPage = new ButtonGroup();
		ButtonGroup groupSelectLanguage = new ButtonGroup();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		Icon icon = createImageIcon("D:\\Dowloads\\luanvan\\images\\ic_download.png", "Icon for Monolingual");
		tabbedPane.setBounds(0, 0, 433, 331);
		frmDownloadApplication.getContentPane().add(tabbedPane);
		/////////////////////////////////////Monolingual handle///////////////////////////////////////////////////////////////////
		JPanel panel = new JPanel();
		tabbedPane.addTab("Monolingual", null, panel, null);
		panel.setForeground(Color.GREEN);
		panel.setBorder(new TitledBorder(null, "Select page", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JRadioButton rdbtnVnExpress = new JRadioButton("http://vnexpress.net/");
		rdbtnVnExpress.setBounds(6, 73, 160, 23);
		
		JRadioButton rdbtnNguoiLaoDong = new JRadioButton("http://nld.com.vn/");
		rdbtnNguoiLaoDong.setBounds(6, 160, 141, 23);
		
		JRadioButton rdbtnDanTri = new JRadioButton("http://dantri.com.vn/");
		rdbtnDanTri.setBounds(224, 35, 140, 23);
		
		JRadioButton rdbtnNgoiSaoNet = new JRadioButton("http://ngoisao.net/");
		rdbtnNgoiSaoNet.setBounds(6, 35, 191, 23);
		
		JRadioButton rdbtnNgoiSaoVN = new JRadioButton("https://ngoisao.vn/");
		rdbtnNgoiSaoVN.setBounds(224, 73, 134, 23);
		
		JRadioButton rdbtnTuoitre = new JRadioButton("http://tuoitre.vn/");
		rdbtnTuoitre.setBounds(6, 117, 141, 23);
		
		JRadioButton rdbtnBongDaPlus = new JRadioButton("http://bongdaplus.vn/");
		rdbtnBongDaPlus.setBounds(224, 117, 167, 23);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(328, 246, 73, 23);
		
		
	
		JList list = new JList();
		list.setBounds(-10008, -10031, 0, 0);
		panel.setLayout(null);
		panel.add(rdbtnVnExpress);
		panel.add(rdbtnNguoiLaoDong);
		panel.add(rdbtnDanTri);
		panel.add(rdbtnNgoiSaoNet);
		panel.add(btnNext);
		panel.add(rdbtnNgoiSaoVN);
		panel.add(rdbtnTuoitre);
		panel.add(rdbtnBongDaPlus);
		panel.add(list);
		
		//Set command for each button
		rdbtnNgoiSaoNet.setActionCommand("http://www.vietnamplus.vn/");
		groupSelectPage.add(rdbtnNgoiSaoNet);
		groupSelectPage.add(rdbtnDanTri);
		groupSelectPage.add(rdbtnNguoiLaoDong);
		groupSelectPage.add(rdbtnTuoitre);
		groupSelectPage.add(rdbtnVnExpress);
		groupSelectPage.add(rdbtnNgoiSaoVN);
		groupSelectPage.add(rdbtnBongDaPlus);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(6, 246, 73, 23);
		panel.add(btnCancel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.GREEN);
		panel_1.setBorder(new TitledBorder(null, "Select Language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(141, 197, 134, 72);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		
		
		JRadioButton rdbtnChinese = new JRadioButton("Chinese");
		rdbtnChinese.setBounds(6, 45, 84, 23);
		panel_1.add(rdbtnChinese);
		rdbtnChinese.setActionCommand("Chinese");
		
		
		JRadioButton rdbtnVietnamese = new JRadioButton("Vietnamese");
		rdbtnVietnamese.setBounds(6, 19, 105, 23);
		panel_1.add(rdbtnVietnamese);
		rdbtnVietnamese.setActionCommand("Vietnamese");
		
		
		groupSelectLanguage.add(rdbtnVietnamese);
		groupSelectLanguage.add(rdbtnChinese);
		
		JLabel lbStatus = new JLabel("Status");
		lbStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbStatus.setForeground(Color.RED);
		lbStatus.setBounds(27, 11, 374, 23);
		panel.add(lbStatus);
		
	
		
		lbStatus.setVisible(false);
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				groupSelectLanguage.clearSelection();
				groupSelectPage.clearSelection();
				
			}
		});
		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Get action comment to parse to SelectContent
				if(groupSelectLanguage.getSelection() != null && groupSelectPage.getSelection() != null)
				{
					SelectContent selectContent = new SelectContent(groupSelectPage.getSelection().getActionCommand(),
							groupSelectLanguage.getSelection().getActionCommand());
					DownloadApplication downloadApplication = new DownloadApplication();
					downloadApplication.frmDownloadApplication.setVisible(true);
					lbStatus.setVisible(false);
					selectContent.show();
				}
				else
				{
					lbStatus.setText("Please select a page and language you want to download");
					lbStatus.setVisible(true);
				}
				
			}
		});
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Bilingual", null, panel_2, null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setForeground(Color.GREEN);
		panel_3.setBorder(new TitledBorder(null, "Select Language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.add(panel_3);
		
		JRadioButton radioButton = new JRadioButton("Chinese");
		radioButton.setActionCommand("Chinese");
		radioButton.setBounds(6, 45, 84, 23);
		panel_3.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Vietnamese");
		radioButton_1.setActionCommand("Vietnamese");
		radioButton_1.setBounds(6, 19, 105, 23);
		panel_3.add(radioButton_1);
		tabbedPane.setIconAt(1, icon);
	
		frmDownloadApplication.setBackground(Color.GRAY);
		frmDownloadApplication.setTitle("Download Application");
		frmDownloadApplication.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Dowloads\\luanvan\\images\\ic_download.png"));
		frmDownloadApplication.setBounds(100, 100, 449, 370);
		frmDownloadApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
