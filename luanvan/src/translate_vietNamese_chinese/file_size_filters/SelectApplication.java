package part3;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextField;

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
		frame.setBounds(100, 100, 520, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Vietnamese Select Path");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBounds(21, 11, 260, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Chinese Select path");
		btnNewButton_1.addActionListener(new ActionListener() {
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
		btnNewButton_1.setBounds(21, 131, 260, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		pathVN = new JTextField();
		pathVN.setBounds(21, 61, 403, 20);
		frame.getContentPane().add(pathVN);
		pathVN.setColumns(10);
		
		pathCN = new JTextField();
		pathCN.setColumns(10);
		pathCN.setBounds(21, 186, 403, 20);
		frame.getContentPane().add(pathCN);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Step2Application step2;
				try {
					frame.dispose();
					step2 = new Step2Application(pathVN.getText().toString(), 
							pathCN.getText().toString());
					
					step2.setVisible(true);
					step2.runTranslate();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNext.setBounds(88, 217, 89, 23);
		frame.getContentPane().add(btnNext);
		
		
	}
}
