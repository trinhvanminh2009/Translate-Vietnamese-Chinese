package part3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import translate_vietNamese_chinese.CompareTitle;
import translate_vietNamese_chinese.SentenceMatching_SimilyratyByTitle;
import translate_vietNamese_chinese.file_size_filters.TranslateAllFile;

public class Step2Application extends JFrame {

	private JPanel contentPane;
	private String pathVN;
	private String pathCN;
	public static final String SIMILARITYDEMO = "SimilarityDemo";
	private JLabel lblNewLabel;
	private static Step2Application frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Step2Application("", "");
					frame.setVisible(true);	
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * 
	 * @throws Exception
	 */
	public Step2Application(String pathVN, String pathCN) throws Exception {
		this.pathVN = pathVN;
		this.pathCN = pathCN;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("New button");

		btnNewButton.setBounds(159, 227, 89, 23);
		contentPane.add(btnNewButton);
		CountDownLatch latch = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL url = Step2Application.class.getResource("/resources/processing.gif");
				ImageIcon imageIcon = new ImageIcon(url);
				 lblNewLabel = new JLabel(imageIcon);
				lblNewLabel.setBounds(78, 11, 250, 200);
				contentPane.add(lblNewLabel);
				latch.countDown();
				
			}
		});
		thread.start();
		latch.await();
		//runTranslate();

	}

	public void runTranslate() {
		
		if (pathVN != null && pathCN != null && !pathVN.isEmpty() && !pathCN.isEmpty()) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {

					TranslateAllFile translateAllFile = new TranslateAllFile();
					try {
						translateAllFile.listFilesForFolder(new File(pathCN), pathCN + "Translated");
						

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();
			try {
				thread.join();
				System.out.println("Translate All File Done");
				// Compare title
				runCompareTitle();
		
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	private void runCompareTitle() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CompareTitle compareTitle = new CompareTitle();
				String pathVNFolder = pathVN + "\\";
				String pathCNFolder = pathCN + "Translated\\";
				System.out.println(pathVNFolder + " \n" + pathCNFolder);
				File folderVN = new File(pathVNFolder);
				File folderCN = new File(pathCNFolder);
				compareTitle.listFilesForFolder(folderVN.getAbsoluteFile(), folderCN.getAbsoluteFile());
				
			}
		});
		thread.start();
		try {
			thread.join();
			Thread thread2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("Compare title Done");
					// Get file similarity over 30% going to compare file
					SentenceMatching_SimilyratyByTitle sentenceMaching = new SentenceMatching_SimilyratyByTitle();
					sentenceMaching.setFolderVN(pathVN);
					String pathForCN = pathCN +"\\";
					sentenceMaching.setFolderCN(pathForCN);
					String similarityDemoPath = System.getProperty("user.dir") + "\\" + SIMILARITYDEMO + ".txt";
					sentenceMaching.readFileSimilyratyByTitle(similarityDemoPath);
				}
			});
		thread2.start();
		thread2.join();
		System.out.println("Done");
		lblNewLabel.setVisible(false);
		
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
