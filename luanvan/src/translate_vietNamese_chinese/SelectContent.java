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
					SelectContent frame = new SelectContent("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void classifyTheGroups()
	{
		String politics = new String("Politics");
		String world = new String("World");
		String business = new String("Business");
		String culture = new String("Culture");
		String sports = new String("Sports");
		String technology = new String("Technology");
		String society = new String("Sosiety");
		String health = new String("Health");
		String enviroment = new String("Envitoment");
		String science = new String("Science");
		String travel = new String("Travel");
		String strangeStory = new String("Strange Story");
		String classifiedAdvertising = new String("Classified Advertising");
		String backStage = new String("Backstage");
		String thoiCuoc = new String("Thời cuộc");
		String fashion = new String("Fashion");
		String beautify = new String("Beautify");
		String benLe = new String("Bên lề");
		String family = new String("Family");
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
		
	}

	/**
	 * Create the frame.
	 */
	public SelectContent(String page) {
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
		
		txtTest1.setText(page);
	}
	
	

}
