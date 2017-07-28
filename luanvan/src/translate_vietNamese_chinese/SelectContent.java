package translate_vietNamese_chinese;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class SelectContent extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JComboBox cbSelectType;
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
		URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
		
		setTitle("Download Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 294);
		contentPane = new JPanel();
		contentPane.setForeground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("Status");
		lblStatus.setForeground(Color.MAGENTA);
		lblStatus.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblStatus.setBounds(15, 5, 397, 22);
		contentPane.add(lblStatus);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Download", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(135, 38, 266, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		cbSelectType = new JComboBox();
		cbSelectType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		cbSelectType.setModel(new DefaultComboBoxModel(new String[] {"Select one item"}));
		cbSelectType.setBounds(20, 25, 215, 30);
		panel.add(cbSelectType);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(20, 73, 89, 23);
		panel.add(btnCancel);
		
		JButton btnDownload = new JButton("Download");
		btnDownload.setBounds(139, 73, 103, 23);
		panel.add(btnDownload);
		
		JLabel lblStatusDownloading = new JLabel("");
		lblStatusDownloading.setBounds(79, 107, 100, 52);
		panel.add(lblStatusDownloading);
		lblStatusDownloading.setIcon(new ImageIcon(SelectContent.class.getResource("/resources/downloading.gif")));
		lblStatusDownloading.setVisible(false);
		
		if(language.equals(""))
		{
			lblStatus.setText("Current page prepare download: "+ page);
		}
		else
		{
			lblStatus.setText("Current page prepare download: "+ page +" with language "+ language);
		}
		
		classifyTheGroups(page, language);
		
		btnDownload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				URL url =  SelectContent.class.getResource("/resources/downloading.gif");
				lblStatusDownloading.setIcon(new ImageIcon(url));
				lblStatusDownloading.setVisible(true);
				
			}
		});
	}
	
	private void classifyTheGroups(String url, String language)
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
		String eatAndPlay = new String("Eat and play");
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
		
		
		ArrayList<String>ngoisaoNet = new ArrayList<>();
		ngoisaoNet.add(backStage);
		ngoisaoNet.add(thoiCuoc);
		ngoisaoNet.add(fashion);
		ngoisaoNet.add(benLe);
		ngoisaoNet.add(family);
		ngoisaoNet.add(blogger);
		ngoisaoNet.add(player);
		ngoisaoNet.add(multipleChoice);
		ngoisaoNet.add(wedding);
		ngoisaoNet.add(eatAndPlay);
		
		ArrayList<String>vnExpress = new ArrayList<>();
		vnExpress.add(news);
		vnExpress.add(viewAngle);
		vnExpress.add(world);
		vnExpress.add(business);
		vnExpress.add(entertaiment);
		vnExpress.add(sports);
		vnExpress.add(law);
		vnExpress.add(education);
		vnExpress.add(health);
		vnExpress.add(family);
		vnExpress.add(travel);
		vnExpress.add(science);
		
		
		if(url.equals("http://ngoisao.net/") && language.equals(""))
		{
			
			for(int i = 0; i < ngoisaoNet.size(); i++)
			{
				cbSelectType.insertItemAt(ngoisaoNet.get(i), i+1);
			}
			
		}
		
		
		
		}
}
