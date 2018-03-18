package part3;

import java.awt.EventQueue;
import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import translate_vietNamese_chinese.CompareTitle;
import translate_vietNamese_chinese.file_size_filters.TranslateAllFile;
import javax.swing.JLabel;

public class Step2Application extends JFrame {

	private JPanel contentPane;
	private String pathVN;
	private String pathCN;
	CountDownLatch latch = new CountDownLatch(1);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Step2Application frame = new Step2Application("", "");
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
	 * @throws Exception
	 */
	public Step2Application(String pathVN, String pathCN) throws Exception {
		this.pathVN = pathVN;
		this.pathCN = pathCN;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("New button");

		btnNewButton.setBounds(10, 103, 89, 23);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Tranlsating...");
		lblNewLabel.setBounds(10, 31, 101, 23);
		contentPane.add(lblNewLabel);

	}

	public void runTranslate() {
		if (pathVN != null && pathCN != null && !pathVN.isEmpty() && !pathCN.isEmpty()) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {

					TranslateAllFile translateAllFile = new TranslateAllFile();
					try {
						translateAllFile.listFilesForFolder(new File(pathCN), pathCN + "Translated");
						latch.countDown();
						//Compare title
						CompareTitle compareTitle = new CompareTitle();
						//compareTitle.listFilesForFolder(folderVN, folderCN);
				
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();

			try {
				latch.await();
				System.out.println("Done");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // W

		}
	}
}
