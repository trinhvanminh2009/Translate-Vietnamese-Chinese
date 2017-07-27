package translate_vietNamese_chinese;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JTextField;

public class SelectContent extends JFrame {

	private JPanel contentPane;
	private JTextField txtTest1;
	private JTextField txtTest2;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Dowloads\\luanvan\\images\\ic_download.png"));
		setTitle("Download Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtTest1 = new JTextField();
		contentPane.add(txtTest1, BorderLayout.NORTH);
		txtTest1.setColumns(10);
		
		txtTest2 = new JTextField();
		contentPane.add(txtTest2, BorderLayout.WEST);
		txtTest2.setColumns(10);
		
		txtTest1.setText(language);
		txtTest2.setText(page);
	}

}
