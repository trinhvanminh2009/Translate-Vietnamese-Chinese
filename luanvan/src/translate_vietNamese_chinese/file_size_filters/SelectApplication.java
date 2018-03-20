package part3;

import java.awt.EventQueue;import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

public class SelectApplication {

	private JFrame frame;
	private JTextField pathVN;
	private JTextField pathCN;
	private JButton btnNext;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectApplication window = new SelectApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 666, 508);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/ic_icon_app.png")));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		BufferedImage imageSelectPathVN;
		try {
			imageSelectPathVN = ImageIO
					.read((this.getClass().getResource("/resources/btn_select_path_for_vietnamese.png")));
			JButton btnSelectPathVn = new JButton(new ImageIcon(imageSelectPathVN));

			btnSelectPathVn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
					int returnValue = jfc.showOpenDialog(null);
					// int returnValue = jfc.showSaveDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						pathVN.setText(selectedFile.getAbsolutePath());
						System.out.println(selectedFile.getAbsolutePath());
					}
				}
			});
			btnSelectPathVn.setBounds(31, 27, 331, 41);
			frame.getContentPane().add(btnSelectPathVn);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		BufferedImage imageSelectPathCN;
		try {
			imageSelectPathCN = ImageIO
					.read((this.getClass().getResource("/resources/btn_select_path_for_chinese.png")));
			JButton btnSelectPathForCN = new JButton(new ImageIcon(imageSelectPathCN));
			btnSelectPathForCN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
					int returnValue = jfc.showOpenDialog(null);
					// int returnValue = jfc.showSaveDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						pathCN.setText(selectedFile.getAbsolutePath());
						System.out.println(selectedFile.getAbsolutePath());
					}
				}
			});
			btnSelectPathForCN.setBounds(31, 110, 304, 41);
			frame.getContentPane().add(btnSelectPathForCN);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		pathVN = new JTextField();
		pathVN.setBounds(87, 79, 366, 20);
		frame.getContentPane().add(pathVN);
		pathVN.setColumns(10);

		pathCN = new JTextField();
		pathCN.setColumns(10);
		pathCN.setBounds(87, 162, 366, 20);
		frame.getContentPane().add(pathCN);

		JLabel lblNewLabel;
		
		URL url = SelectApplication.class.getResource("/resources/processing.gif");
		ImageIcon imageIcon = new ImageIcon(url);
		lblNewLabel = new JLabel(imageIcon);
		lblNewLabel.setBounds(228, 222, 184, 174);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		
	
		BufferedImage imageButtonNext;
		try {
			imageButtonNext = ImageIO.read((this.getClass().getResource("/resources/btn_processing.png")));
			btnNext = new JButton(new ImageIcon(imageButtonNext));
			btnNext.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblNewLabel.setVisible(true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {

								BufferedImage imageDone =  ImageIO.read((this.getClass().getResource("/resources/done.png")));
							
								Step2Application step2 = new Step2Application(pathVN.getText().toString(),
										pathCN.getText().toString());
								step2.runTranslate();
								lblNewLabel.setVisible(false);
								 lblNewLabel.setIcon(new ImageIcon(imageDone));
								 lblNewLabel.setVisible(true);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} finally {

							}
						}
					}).start();
					

				}
			});

			btnNext.setBounds(31, 425, 125, 33);
			frame.getContentPane().add(btnNext);
			BufferedImage imageButtonClose;
			imageButtonClose = ImageIO.read((this.getClass().getResource("/resources/button_close.png")));
			JButton btnClose = new JButton(new ImageIcon(imageButtonClose));
			btnClose.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			btnClose.setBounds(551, 425, 89, 33);
			frame.getContentPane().add(btnClose);

			JLabel lblPathVn = new JLabel("Path VN:");
			lblPathVn.setBounds(31, 79, 60, 20);
			frame.getContentPane().add(lblPathVn);

			JLabel lblPathCn = new JLabel("Path CN:");
			lblPathCn.setBounds(31, 165, 60, 17);
			frame.getContentPane().add(lblPathCn);
			
		

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}
}
