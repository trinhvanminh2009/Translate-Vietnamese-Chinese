package translate_vietNamese_chinese.download_application;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class SelectContent extends JFrame {

    private JPanel contentPane;
    private JLabel lblStatus;
    private JLabel lblStatusLanguage;
    private ArrayList<ScrapingThread> threadList = new ArrayList<>();
    public static boolean resume = false;
    public static boolean stopped = true;
    public static final String DOWNLOAD_LOG = System.getProperty("user.dir") + "/download_log/";
    public static final String PACKAGE = GetPackageNameClass.getPackageName();
    private JList<CheckboxListItem> list;
    private CheckboxListItem[] arr = null;
    private ArrayList<String[]> arraySubject;
    private static JProgressBar jpb;
    private String language = "";
    private String className = "";
    private String pageName = "";
    private JButton btnPause;
    private boolean btnPauseClickAble = true;
    private static boolean downloadDone = false;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectContent frame = new SelectContent("", "none", false, "");
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showDialogPause() {
        URL urlImageIcon = SelectContent.class.getResource("/resources/downloading.gif");
        ImageIcon icon = new ImageIcon(urlImageIcon);

        Runnable runnableStopDownload = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                pauseDownload();
            }
        };
        Thread threadStopDownload = new Thread(runnableStopDownload);
        if (stopped == true) {
        } else {

            threadStopDownload.start();
        }
        /*JOptionPane.showMessageDialog(null,
				"Threads are stopping, please wait! \n " + "Please waiting for this dialog auto close. \n"
						+ "Do not stop program anyway, Data may be lost.",
				"Stopping", JOptionPane.WARNING_MESSAGE, icon);
		JOptionPane pane = new JOptionPane("Threads are stopping, please wait! \n " + "Please waiting for this dialog auto close. \n"
				+ "Do not stop program anyway, Data may be lost.");*/

    }

    /**
     * Create the frame.
     */
    // res=resume
    @SuppressWarnings("deprecation")
    public SelectContent(String page, String lan, boolean resumeable, String cName) {
        this.language = lan;
        this.pageName = page;
        makeDirectoryDownloadLog();
        URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
        setTitle("Download Application");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 640, 500);
        contentPane = new JPanel();
        contentPane.setForeground(Color.CYAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        // Remove any existing WindowListeners
        for (WindowListener wl : this.getWindowListeners()) {
            this.removeWindowListener(wl);
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (stopped == false && downloadDone == false) {
                    JOptionPane.showMessageDialog(null, "You cannot close this window");
                } else {
                    System.exit(0);
                }
            }
        });
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblStatus = new JLabel("Status");
        lblStatus.setForeground(Color.MAGENTA);
        lblStatus.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        lblStatus.setBounds(15, 5, 509, 22);
        contentPane.add(lblStatus);

        lblStatusLanguage = new JLabel("All threads downloading are stopping. Please wait until this status auto disappear!");
        lblStatusLanguage.setVisible(false);
        lblStatusLanguage.setForeground(Color.MAGENTA);
        lblStatusLanguage.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        lblStatusLanguage.setBounds(15, 38, 464, 14);
        lblStatusLanguage.setVisible(true);
        contentPane.add(lblStatusLanguage);

        ////////////////////////////////////
        jpb = new JProgressBar(0, 100);
        jpb.setBounds(50, 330, 400, 15);
        jpb.setStringPainted(true);
        contentPane.add(jpb);
        // setProgressBarValue(70);

        BufferedImage btnSelectAllImage = null;
        BufferedImage btnPauseImage = null;
        BufferedImage btnDownloadImage = null;
        BufferedImage btnUnselectAllImage = null;
        BufferedImage btnResumeImage = null;
        try {
            btnSelectAllImage = ImageIO.read(this.getClass().getResource("/resources/ic_check_all.png"));
            btnPauseImage = ImageIO.read(this.getClass().getResource("/resources/ic_pause.png"));
            btnDownloadImage = ImageIO.read(this.getClass().getResource("/resources/ic_download_button.png"));
            btnUnselectAllImage = ImageIO.read(this.getClass().getResource("/resources/ic_uncheck_all.png"));
            btnResumeImage = ImageIO.read(this.getClass().getResource("/resources/ic_resume.png"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        btnPause = new JButton(new ImageIcon(btnPauseImage));
        btnPause.setBounds(470, 360, 150, 50);
        btnPause.setEnabled(false);
        btnPause.setBorder(BorderFactory.createEmptyBorder());
        btnPause.setContentAreaFilled(false);
        btnPause.setLabel("Pause");

        JButton btnDownload = new JButton(new ImageIcon(btnDownloadImage));
        btnDownload.setBorder(BorderFactory.createEmptyBorder());
        btnDownload.setContentAreaFilled(false);
        btnDownload.setBounds(10, 360, 150, 50);
        btnDownload.setLabel("Download");

        updatePercentDownload();
        if (resumeable == true) {

            lblStatus.setText("Resume download ....");
            lblStatusLanguage.setText("");
            resume = true;
            btnPause.setEnabled(true);
            className = cName;
            resumeDownload(cName);
            this.language = getLanguageFromClassName(className);
            arr = new CheckboxListItem[threadList.size()];
            for (int i = 0; i < threadList.size(); i++) {

                arr[i] = new CheckboxListItem(getSubjectFromPageName(page, threadList.get(i).getPageName()), threadList.get(i).getPageName());
                arr[i].setSelected(true);
            }
            resume = false;
            stopped = false;
            btnDownload.setEnabled(false);
        } else {
            className = getClassNameFromWebNameAndLanguage(pageName, language);
            arraySubject = getListSubject(page);
            arr = new CheckboxListItem[arraySubject.size()];
            String[] tmp;
            for (int i = 0; i < arraySubject.size(); i++) {
                tmp = arraySubject.get(i);
                arr[i] = new CheckboxListItem(tmp[0], tmp[1]);
            }
        }
        if (language.equals("")) {
            lblStatus.setText("Current page prepare download: " + page);
        } else {
            lblStatus.setText("Current page prepare download: " + page);
            lblStatusLanguage.setText("Current language is: " + language);
        }

        list = new JList<CheckboxListItem>(arr);
        list.setCellRenderer(new CheckboxListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList<CheckboxListItem> list = (JList<CheckboxListItem>) event.getSource();

                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                CheckboxListItem item = (CheckboxListItem) list.getModel().getElementAt(index);

                // Toggle selected state
                item.setSelected(!item.isSelected());

                // Repaint cell
                list.repaint(list.getCellBounds(index, index));
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setBounds(50, 70, 400, 250);
        btnPause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (resume == false) {
                    URL urlImageIcon = SelectContent.class.getResource("/resources/downloading.gif");
                    ImageIcon icon = new ImageIcon(urlImageIcon);

                    URL urlpauseIcon = SelectContent.class.getResource("/resources/ispause.gif");
                    ImageIcon pauseIcon = new ImageIcon(urlpauseIcon);
                    btnPause.setLabel("Pause...");
                    btnPause.setEnabled(false);
                    btnPause.setIcon(pauseIcon);

                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            pauseDownload();
                        }
                    });
                    th.start();

                } else {
                    URL urlImageIcon = SelectContent.class.getResource("/resources/downloading.gif");
                    ImageIcon icon = new ImageIcon(urlImageIcon);

                    btnPause.setLabel("Pause");
                    Image iconPause;
                    try {
                        iconPause = ImageIO.read(this.getClass().getResource("/resources/ic_pause.png"));
                        btnPause.setIcon(new ImageIcon(iconPause));
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    resumeDownload(className);
                    list.setEnabled(true);
                    for (int i = 0; i < list.getModel().getSize(); i++) {
                        list.getModel().getElementAt(i).setSelected(false);
                    }
                    for (int i = 0; i < list.getModel().getSize(); i++) {
                        for (ScrapingThread scrapingThread : threadList) {
                            if (list.getModel().getElementAt(i).getPageName().equals(scrapingThread.getPageName())) {
                                list.getModel().getElementAt(i).setSelected(true);
                                break;
                            }
                        }

                    }
                    list.repaint();
                    list.setEnabled(false);
                    resume = false;
                    stopped = false;
                }
            }
        });
        contentPane.add(btnPause);

        btnDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (stopped == true) {
                    ScrapingThread st;
                    stopped = false;
                    for (int i = 0; i < list.getModel().getSize(); i++) {
                        CheckboxListItem checkbox = (CheckboxListItem) list.getModel().getElementAt(i);
                        if (checkbox.isSelected()) {
                            st = new ScrapingThread(PACKAGE + "." + getClassNameFromWebNameAndLanguage(page, language), checkbox.getPageName());
                            st.start();
                            threadList.add(st);
                            System.out.println(PACKAGE + "." + getClassNameFromWebNameAndLanguage(pageName, language) + "  " + checkbox.getPageName());
                        }
                    }
                    btnDownload.setEnabled(false);
                    btnPause.setEnabled(true);
                    btnPause.setLabel("Pause");
                    resume = false;
                }
            }
        });
        contentPane.add(btnDownload);
        JButton btnSelectAll = new JButton(new ImageIcon(btnSelectAllImage));
        btnSelectAll.setBorder(BorderFactory.createEmptyBorder());
        btnSelectAll.setContentAreaFilled(false);
        btnSelectAll.setBounds(170, 360, 150, 50);
        btnSelectAll.setLabel("Check All");
        btnSelectAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < list.getModel().getSize(); i++) {
                    CheckboxListItem checkbox = (CheckboxListItem) list.getModel().getElementAt(i);
                    if (!checkbox.isSelected()) {
                        checkbox.setSelected(true);
                    }
                }
                list.repaint();
            }
        });
        contentPane.add(btnSelectAll);
        JButton btnDeselectAll = new JButton(new ImageIcon(btnUnselectAllImage));
        btnDeselectAll.setBorder(BorderFactory.createEmptyBorder());
        btnDeselectAll.setContentAreaFilled(false);
        btnDeselectAll.setLabel("Uncheck All");
        btnDeselectAll.setBounds(320, 360, 160, 50);
        btnDeselectAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < list.getModel().getSize(); i++) {
                    CheckboxListItem checkbox = (CheckboxListItem) list.getModel().getElementAt(i);
                    if (checkbox.isSelected()) {
                        checkbox.setSelected(false);
                    }
                }
                list.repaint();
            }
        });
        contentPane.add(btnDeselectAll);

        contentPane.add(scrollPane);
    }
//kiem tra trong folder co file hay khong

    public static boolean checkResumeable() {
        File f = new File(DOWNLOAD_LOG);
        if (!f.exists()) {
            return false;
        } else {
            if (f.listFiles().length > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void setProgressBarValue(int value) {
        jpb.setValue(value);
    }

    public void resumeDownload(String className) {
        if (btnPauseClickAble == true) {
            if (resume == true) {
                btnPauseClickAble = false;
                ScrapingThread st;
                try (BufferedReader br = new BufferedReader(new FileReader(DOWNLOAD_LOG + className + ".txt"))) {
                    String line = br.readLine();
                    while (line != null) {
                        System.out.println(line);
                        String[] arr = line.split(" ");
                        st = new ScrapingThread(GetPackageNameClass.getPackageName() + "." + className, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
                        System.out.println("Resume " + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3]);
                        st.start();
                        threadList.add(st);
                        line = br.readLine();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnPauseClickAble = true;
            }
        }

    }

    public void pauseDownload() {
        if (btnPauseClickAble == true) {
            btnPauseClickAble = false;
            System.out.println("Stopping---------------------");

            for (int i = 0; i < threadList.size(); i++) {
                threadList.get(i).requestStop();
            }

            for (ScrapingThread scrapingThread : threadList) {
                if (scrapingThread.getStateDownload() == true) {
                    System.out.println("DONE " + scrapingThread.getName());
                }
                System.out.println("-----" + scrapingThread.getClassName() + " " + scrapingThread.getPageName() + " "
                        + scrapingThread.getCurrentPage() + " " + scrapingThread.getPosition() + " " + scrapingThread.getArticleSize());
            }
            if (downloadDone == false) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DOWNLOAD_LOG + getClassNameFromWebNameAndLanguage(pageName, language) + ".txt"), "utf-8"))) {
                    for (ScrapingThread scrapingThread : threadList) {       
                            writer.write(scrapingThread.getPageName() + " "
                                    + scrapingThread.getCurrentPage() + " "
                                    + scrapingThread.getPosition() + " "
                                    + scrapingThread.getArticleSize() + "\n");                        
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            threadList.clear();
            stopped = true;
            btnPause.setEnabled(true);
            resume = true;
            btnPause.setLabel("Resume");
            Image iconPause;
            try {
                iconPause = ImageIO.read(this.getClass().getResource("/resources/ic_resume.png"));
                btnPause.setIcon(new ImageIcon(iconPause));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            list.setEnabled(true);
            btnPauseClickAble = true;
        }

    }

    public void makeDirectoryDownloadLog() {
        File file = new File(DOWNLOAD_LOG);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println(DOWNLOAD_LOG + " Directory is created!");
            } else {
                System.out.println(DOWNLOAD_LOG + " Failed to create directory!");
            }
        }
    }

    public void updatePercentDownload() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    int per = 0;
                    boolean stopDownload=false;
                    while (!stopDownload) {
                        per = 0;
                        Thread.sleep(4000);
                        for (ScrapingThread scrapingThread : threadList) {
                            per += scrapingThread.getDownloadPercent();
                        }
                        if (threadList.isEmpty()) {
                            per = 0;
                        } else {
                            per = per / threadList.size();
                        }
                        //System.out.println("per" + per);
                        setProgressBarValue(per);

                        if (per >= 95) {
                            downloadDone = true;
                            for (ScrapingThread scrapingThread : threadList) {
                                if (scrapingThread.getStateDownload() == false) {
                                    downloadDone = false;
                                    break;
                                }
                            }

                            if (downloadDone == true) {
                                boolean  allThreadDeath = true;
                                for (ScrapingThread scrapingThread : threadList) {
                                    if (scrapingThread.isAlive()) {
                                        allThreadDeath = false;
                                        break;
                                    }
                                }
                                if(allThreadDeath){
                                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DOWNLOAD_LOG + getClassNameFromWebNameAndLanguage(pageName, language) + ".txt"), "utf-8"))) {
                                        for (ScrapingThread scrapingThread : threadList) {       
                                                writer.write(scrapingThread.getPageName() + " "
                                                        + scrapingThread.getCurrentPage() + " "
                                                        + scrapingThread.getPosition() + " "
                                                        + (scrapingThread.getArticleSize()-1) + "\n"); 
                                                System.out.println("DONE "+scrapingThread.getPageName() + " "
                                                        + scrapingThread.getCurrentPage() + " "
                                                        + scrapingThread.getPosition() + " "
                                                        + (scrapingThread.getArticleSize()-1) + "\n");
                                        }
                                    } catch (UnsupportedEncodingException ex) {
                                        Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    JOptionPane.showMessageDialog(null, "Download Complete!");
                                    btnPause.setEnabled(false);
                                    stopDownload=true;
                                }                              
                            }
                        }

                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        th.start();
    }

    public String getSubjectFromPageName(String webName, String pageName) {
        String result = "";
        ArrayList<String[]> arr = getListSubject(webName);

        for (String[] strings : arr) {
            if (strings[1].equals(pageName)) {
                return strings[0];
            }
        }
        return result;
    }

//////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String[]> getListSubject(String webName) {
        ArrayList<String[]> arr = new ArrayList<>();
        switch (webName) {
            case "https://vnexpress.net":
                arr.add(new String[]{"Thời sự", "https://vnexpress.net/tin-tuc/thoi-su"});
                arr.add(new String[]{"Góc nhìn", "https://vnexpress.net/tin-tuc/goc-nhin"});
                arr.add(new String[]{"Thế giới", "https://vnexpress.net/tin-tuc/the-gioi"});
                arr.add(new String[]{"Kinh doanh", "https://kinhdoanh.vnexpress.net"});
                arr.add(new String[]{"Giải trí", "https://giaitri.vnexpress.net"});
                arr.add(new String[]{"Thể thao", "https://thethao.vnexpress.net"});
                arr.add(new String[]{"Pháp luật", "https://vnexpress.net/tin-tuc/phap-luat"});
                arr.add(new String[]{"Giáo dục", "https://vnexpress.net/tin-tuc/giao-duc"});
                arr.add(new String[]{"Sức khỏe", "https://suckhoe.vnexpress.net/tin-tuc/suc-khoe"});
                arr.add(new String[]{"Gia đình", "https://giadinh.vnexpress.net"});
                arr.add(new String[]{"Du lịch", "https://dulich.vnexpress.net"});
                arr.add(new String[]{"Khoa học", "https://vnexpress.net/tin-tuc/khoa-hoc"});
                arr.add(new String[]{"Số hóa", "https://sohoa.vnexpress.net"});
                arr.add(new String[]{"Xe", "https://vnexpress.net/tin-tuc/oto-xe-may"});
                arr.add(new String[]{"Cộng đồng", "https://vnexpress.net/tin-tuc/cong-dong"});
                arr.add(new String[]{"Tâm sự", "https://vnexpress.net/tin-tuc/tam-su"});
                arr.add(new String[]{"Cười", "https://vnexpress.net/tin-tuc/cuoi"});
                break;
            case "https://www.vietnamplus.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "https://www.vietnamplus.vn/kinhte.vnp"});
                    arr.add(new String[]{"Chính trị", "https://www.vietnamplus.vn/chinhtri.vnp"});
                    arr.add(new String[]{"Xã hội", "https://www.vietnamplus.vn/xahoi.vnp"});
                    arr.add(new String[]{"Thế giới - ASEAN", "https://www.vietnamplus.vn/thegioi/asean.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Á-TBD", "https://www.vietnamplus.vn/thegioi/chaua-tbd.vnp"});
                    arr.add(new String[]{"Thế giới - Trung Đông", "https://www.vietnamplus.vn/thegioi/trungdong.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Âu", "https://www.vietnamplus.vn/thegioi/chauau.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Mỹ", "https://www.vietnamplus.vn/thegioi/chaumy.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Phi", "https://www.vietnamplus.vn/thegioi/chauphi.vnp"});
                    arr.add(new String[]{"Đời sống", "https://www.vietnamplus.vn/doisong.vnp"});
                    arr.add(new String[]{"Văn hóa", "https://www.vietnamplus.vn/vanhoa.vnp"});
                    arr.add(new String[]{"Thể thao - Bóng đá", "https://www.vietnamplus.vn/thethao/bongda.vnp"});
                    arr.add(new String[]{"Thể thao - Quần vợt", "https://www.vietnamplus.vn/thethao/quanvot.vnp"});
                    arr.add(new String[]{"Thể thao - Sea Games 29", "https://www.vietnamplus.vn/thethao/seagames29.vnp"});
                    arr.add(new String[]{"Khoa học", "https://www.vietnamplus.vn/khoahoc.vnp"});
                    arr.add(new String[]{"Công nghệ", "https://www.vietnamplus.vn/congnghe.vnp"});
                    arr.add(new String[]{"Chuyện lạ", "https://www.vietnamplus.vn/chuyenla.vnp"});
                } else {
                    arr.add(new String[]{"Politics", "https://zh.vietnamplus.vn/politics.vnp"});
                    arr.add(new String[]{"World", "https://zh.vietnamplus.vn/world.vnp"});
                    arr.add(new String[]{"Business", "https://zh.vietnamplus.vn/business.vnp"});
                    arr.add(new String[]{"Social", "https://zh.vietnamplus.vn/social.vnp"});
                    arr.add(new String[]{"Culture", "https://zh.vietnamplus.vn/culture.vnp"});
                    arr.add(new String[]{"Sports", "https://zh.vietnamplus.vn/sports.vnp"});
                    arr.add(new String[]{"Technology", "https://zh.vietnamplus.vn/technology.vnp"});
                    arr.add(new String[]{"Environment", "https://zh.vietnamplus.vn/environment.vnp"});
                    arr.add(new String[]{"Travel", "https://zh.vietnamplus.vn/Travel.vnp"});
                }

                break;
            case "http://baobinhduong.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "http://baobinhduong.vn/kinh-te"});
                    arr.add(new String[]{"Chính trị", "http://baobinhduong.vn/chinh-tri"});
                    arr.add(new String[]{"Xã hội", "http://baobinhduong.vn/xa-hoi"});
                    arr.add(new String[]{"Quốc tế", "http://baobinhduong.vn/quoc-te"});
                    arr.add(new String[]{"Thể thao", "http://baobinhduong.vn/the-thao"});
                    arr.add(new String[]{"Phân tích", "http://baobinhduong.vn/phan-tich"});
                    arr.add(new String[]{"Bạn đọc", "http://baobinhduong.vn/ban-doc"});
                    arr.add(new String[]{"Pháp luật", "http://baobinhduong.vn/phap-luat"});
                    arr.add(new String[]{"Y tế", "http://baobinhduong.vn/yte"});
                    arr.add(new String[]{"Văn hóa - Văn nghệ", "http://baobinhduong.vn/van-hoa-van-nghe"});
                    arr.add(new String[]{"Doanh nhân", "http://baobinhduong.vn/doanh-nghiep-doanh-nhan"});
                    arr.add(new String[]{"Quốc phòng - An ninh", "http://baobinhduong.vn/quoc-phong-an-ninh"});
                    arr.add(new String[]{"Gia đình", "http://baobinhduong.vn/gia-dinh"});
                    arr.add(new String[]{"Khoa học - Công nghệ", "http://baobinhduong.vn/khoahoc-congnghe"});
                    arr.add(new String[]{"Ô tô - Xe máy ", "http://baobinhduong.vn/oto-xemay"});
                    arr.add(new String[]{"Lao động", "http://baobinhduong.vn/lao-dong"});
                    arr.add(new String[]{"Hồ sơ - Tư liệu", "http://baobinhduong.vn/ho-so-tu-lieu"});
                    arr.add(new String[]{"Môi trường", "http://baobinhduong.vn/moi-truong"});
                } else {
                    arr.add(new String[]{"Politics", "http://baobinhduong.vn/cn/politic-cn"});
                    arr.add(new String[]{"Viet Nam - World", "http://baobinhduong.vn/cn/vietnam-and-world-cn"});
                    arr.add(new String[]{"Sea-islands", "http://baobinhduong.vn/cn/sea-islands-cn"});
                    arr.add(new String[]{"Economic", "http://baobinhduong.vn/cn/economic-cn"});
                    arr.add(new String[]{"International", "http://baobinhduong.vn/cn/international-cn"});
                    arr.add(new String[]{"Society", "http://baobinhduong.vn/cn/society-cn"});
                    arr.add(new String[]{"Health", "http://baobinhduong.vn/cn/health-cn"});
                    arr.add(new String[]{"Culture", "http://baobinhduong.vn/cn/culture-cn"});
                    arr.add(new String[]{"Policy", "http://baobinhduong.vn/cn/policy-cn"});
                    arr.add(new String[]{"Travel", "http://baobinhduong.vn/cn/travel-cn"});
                    arr.add(new String[]{"Technology", "http://baobinhduong.vn/cn/technology-cn"});
                    arr.add(new String[]{"Environment", "http://baobinhduong.vn/cn/environment-cn"});
                    arr.add(new String[]{"Realstate", "http://baobinhduong.vn/cn/realstate-cn"});
                }

                break;

            case "http://www.sggp.org.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "http://www.sggp.org.vn/kinhte"});
                    arr.add(new String[]{"Chính trị", "http://www.sggp.org.vn/chinhtri"});
                    arr.add(new String[]{"Xã hội", "http://www.sggp.org.vn/xahoi"});
                    arr.add(new String[]{"Thế giới", "http://www.sggp.org.vn/thegioi"});
                    arr.add(new String[]{"Đời sống công nghệ", "http://www.sggp.org.vn/doisongcongnghe"});
                    arr.add(new String[]{"Giáo dục", "http://www.sggp.org.vn/giaoduc"});
                    arr.add(new String[]{"Khoa học công nghệ", "http://www.sggp.org.vn/khoahoc_congnghe"});
                    arr.add(new String[]{"Pháp luật", "http://www.sggp.org.vn/phapluat"});
                    arr.add(new String[]{"Y tế - Sức khỏe", "http://www.sggp.org.vn/ytesuckhoe"});
                    arr.add(new String[]{"Văn hóa - Giải trí", "http://www.sggp.org.vn/vanhoavannghe"});
                    arr.add(new String[]{"Nhịp cầu bạn đọc", "http://www.sggp.org.vn/nhipcaubandoc"});

                } else {
                    arr.add(new String[]{"Politics", "http://cn.sggp.org.vn/時政/"});
                    arr.add(new String[]{"Law", "http://cn.sggp.org.vn/法律/"});
                    arr.add(new String[]{"Economic", "http://cn.sggp.org.vn/經濟/"});
                    arr.add(new String[]{"International", "http://cn.sggp.org.vn/國際/"});
                    arr.add(new String[]{"Chinese dynamic", "http://cn.sggp.org.vn/華人動態/"});
                    arr.add(new String[]{"Education", "http://cn.sggp.org.vn/教育/"});
                    arr.add(new String[]{"Sports", "http://cn.sggp.org.vn/體育/"});
                    arr.add(new String[]{"Technology", "http://cn.sggp.org.vn/科技/"});
                    arr.add(new String[]{"Health", "http://cn.sggp.org.vn/健康-飲食/"});
                    arr.add(new String[]{"Entertainment", "http://cn.sggp.org.vn/文娛/"});
                    arr.add(new String[]{"Readers - Charity", "http://cn.sggp.org.vn/讀者-慈善/"});
                    arr.add(new String[]{"Travel", "http://cn.sggp.org.vn/旅遊/"});
                }

                break;
            case "http://thoidai.com.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "http://thoidai.com.vn/kinh-te_t113c14"});
                    arr.add(new String[]{"Thời sự", "http://thoidai.com.vn/thoi-su_t113c3"});
                    arr.add(new String[]{"Sức khỏe", "http://thoidai.com.vn/suc-khoe_t113c23"});
                    arr.add(new String[]{"Thế giới", "http://thoidai.com.vn/the-gioi_t113c6"});
                    arr.add(new String[]{"Bạn năm châu", "http://thoidai.com.vn/ban-nam-chau_t113c34"});
                    arr.add(new String[]{"Giáo dục", "http://thoidai.com.vn/giao-duc_t113c20"});
                    arr.add(new String[]{"Tắm lòng bè bạn", "http://thoidai.com.vn/tam-long-be-ban_t113c35"});
                    arr.add(new String[]{"Văn hóa - Du lịch", "http://thoidai.com.vn/van-hoa-du-lich_t113c9"});
                    arr.add(new String[]{"Thế giới", "http://thoidai.com.vn/the-gioi_t113c6"});
                    arr.add(new String[]{"An toàn - Giao thông", "http://thoidai.com.vn/an-toan-giao-thong_t113c95"});
                    arr.add(new String[]{"Bạn đọc", "http://thoidai.com.vn/ban-doc_t113c30"});

                    arr.add(new String[]{"Pháp luật", "http://thoidai.com.vn/old/phap-luat_t113c54"});
                    arr.add(new String[]{"Gia đình việt", "http://thoidai.com.vn/old/gia-dinh-viet_t113c49"});
                    arr.add(new String[]{"Nông thôn", "http://thoidai.com.vn/old/nong-thon_t113c46"});
                    arr.add(new String[]{"Thể thao", "http://thoidai.com.vn/old/the-thao_t113c59"});
                    arr.add(new String[]{"Môi trường", "http://thoidai.com.vn/old/moi-truong_t113c62"});
                    arr.add(new String[]{"Nhân ái", "http://thoidai.com.vn/nhan-ai_t113c27"});

                } else {
                    arr.add(new String[]{"Politics", "http://shidai.vn/政治_t113c3"});
                    arr.add(new String[]{"Cultural - Sport", "http://shidai.vn/文化-体育_t113c6"});
                    arr.add(new String[]{"Economic", "http://shidai.vn/经济_t113c89"});
                    arr.add(new String[]{"International", "http://shidai.vn/国际_t113c30"});
                    arr.add(new String[]{"Vietnamese - Family", "http://shidai.vn/越南家庭_t113c9"});
                    arr.add(new String[]{"Society", "http://shidai.vn/社会_t113c14"});
                    arr.add(new String[]{"Love the motherland", "http://shidai.vn/热爱祖国_t113c20"});
                    arr.add(new String[]{"Vietnamese relations", "http://shidai.vn/越中关系_t113c23"});
                    arr.add(new String[]{"Four Seas Brothers", "http://shidai.vn/四海兄弟_t113c27"});

                }

                break;

            case "http://www.tapchicongsan.org.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Chính trị", "http://www.tapchicongsan.org.vn/Home/xay-dung-dang.aspx"});
                    arr.add(new String[]{"Nghiên cứu", "http://www.tapchicongsan.org.vn/Home/Nghiencuu-Traodoi.aspx"});
                    arr.add(new String[]{"Bình luận", "http://www.tapchicongsan.org.vn/Home/Binh-luan.aspx"});
                    arr.add(new String[]{"Thông tin lý luận", "http://www.tapchicongsan.org.vn/Home/Thong-tin-ly-luan.aspx"});
                    arr.add(new String[]{"Thế giới", "http://www.tapchicongsan.org.vn/Home/The-gioi-van-de-su-kien.aspx"});
                    arr.add(new String[]{"Kinh tế", "http://www.tapchicongsan.org.vn/Home/kinh-te.aspx"});
                    arr.add(new String[]{"Văn hóa", "http://www.tapchicongsan.org.vn/Home/Van-hoa-xa-hoi.aspx"});
                    arr.add(new String[]{"An ninh", "http://www.tapchicongsan.org.vn/Home/An-ninh-quoc-phong.aspx"});
                    arr.add(new String[]{"Đối ngoại", "http://www.tapchicongsan.org.vn/Home/Doi-ngoai-va-hoi-nhap.aspx"});
                    arr.add(new String[]{"Sinh hoạt tư tưởng", "http://www.tapchicongsan.org.vn/Home/Sinh-hoat-tu-tuong.aspx"});

                } else {
                    arr.add(new String[]{"Politics", "http://cn.tapchicongsan.org.vn/Home/party"});
                    arr.add(new String[]{"News", "http://cn.tapchicongsan.org.vn/Home/Political"});
                    arr.add(new String[]{"Economic", "http://cn.tapchicongsan.org.vn/Home/economic"});
                    arr.add(new String[]{"Social", "http://cn.tapchicongsan.org.vn/Home/social"});
                    arr.add(new String[]{"The World", "http://cn.tapchicongsan.org.vn/Home/TheWorld"});

                }

                break;

            case "http://baothainguyen.org.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Thời sự trong tỉnh", "http://baothainguyen.org.vn/chuyen-muc/thoi-su-trong-tinh_205.html"});
                    arr.add(new String[]{"Chính trị", "http://baothainguyen.org.vn/chuyen-muc/chinh-tri_97.html"});
                    arr.add(new String[]{"Kinh tế", "http://baothainguyen.org.vn/chuyen-muc/kinh-te_108.html"});
                    arr.add(new String[]{"Xã hội", "http://baothainguyen.org.vn/chuyen-muc/xa-hoi_85.html"});
                    arr.add(new String[]{"Văn hóa", "http://baothainguyen.org.vn/chuyen-muc/van-hoa_98.html"});
                    arr.add(new String[]{"Giáo dục", "http://baothainguyen.org.vn/chuyen-muc/giao-duc_100.html"});
                    arr.add(new String[]{"Pháp luật", "http://baothainguyen.org.vn/chuyen-muc/phap-luat_101.html"});
                    arr.add(new String[]{"Thể thao", "http://baothainguyen.org.vn/chuyen-muc/the-thao_83.html"});
                    arr.add(new String[]{"Khoa học công nghệ", "http://baothainguyen.org.vn/chuyen-muc/khoa-hoc-cn_99.html"});
                    arr.add(new String[]{"Giao thông", "http://baothainguyen.org.vn/chuyen-muc/giao-thong_103.html"});

                    arr.add(new String[]{"Oto xe máy", "http://baothainguyen.org.vn/chuyen-muc/o-to-xe-may_110.html"});
                    arr.add(new String[]{"Nét đẹp đời thường", "http://baothainguyen.org.vn/chuyen-muc/net-dep-doi-thuong_116.html"});
                    arr.add(new String[]{"Văn bản chính sách mới", "http://baothainguyen.org.vn/chuyen-muc/van-ban-chinh-sach-moi_184.html"});
                    arr.add(new String[]{"Thông tin quảng cáo", "http://baothainguyen.org.vn/chuyen-muc/thong-tin-quang-cao_38.html"});
                    arr.add(new String[]{"Đất và người Thái Nguyên", "http://baothainguyen.org.vn/chuyen-muc/dat-va-nguoi-thai-nguyen_40.html"});
                    arr.add(new String[]{"Quê hương đất nước", "http://baothainguyen.org.vn/chuyen-muc/que-huong-dat-nuoc_104.html"});
                } else {
                    arr.add(new String[]{"Chinese", "http://baothainguyen.org.vn/chuyen-muc/tieng-trung-quoc_31.html"});
                }

                break;

            case "http://www.nhandan.com.vn":
                if (language == "Vietnamese") {

                    arr.add(new String[]{"Chính trị", "http://www.nhandan.com.vn/chinhtri"});
                    arr.add(new String[]{"Kinh tế", "http://www.nhandan.com.vn/kinhte"});
                    arr.add(new String[]{"Xã hội", "http://www.nhandan.com.vn/xahoi"});
                    arr.add(new String[]{"Văn hóa", "http://www.nhandan.com.vn/vanhoa"});
                    arr.add(new String[]{"Thế giới", "http://www.nhandan.com.vn/thegioi"});
                    arr.add(new String[]{"Công nghệ", "http://www.nhandan.com.vn/congnghe"});
                    arr.add(new String[]{"Khoa học", "http://www.nhandan.com.vn/khoahoc"});
                    arr.add(new String[]{"Giáo dục", "http://www.nhandan.com.vn/giaoduc"});
                    arr.add(new String[]{"Sức khỏe", "http://www.nhandan.com.vn/suckhoe"});
                    arr.add(new String[]{"Pháp luật", "http://www.nhandan.com.vn/phapluat"});
                    arr.add(new String[]{"Thể thao", "http://www.nhandan.com.vn/thethao"});
                    arr.add(new String[]{"Bạn đọc", "http://www.nhandan.com.vn/bandoc"});

                } else {
                    arr.add(new String[]{"Leader", "http://cn.nhandan.com.vn/leader"});
                    arr.add(new String[]{"Political", "http://cn.nhandan.com.vn/political"});
                    arr.add(new String[]{"International", "http://cn.nhandan.com.vn/international"});
                    arr.add(new String[]{"Economic", "http://cn.nhandan.com.vn/economic"});
                    arr.add(new String[]{"Society", "http://cn.nhandan.com.vn/society"});
                    arr.add(new String[]{"Culture", "http://cn.nhandan.com.vn/culture"});
                    arr.add(new String[]{"Sports", "http://cn.nhandan.com.vn/sports"});
                    arr.add(new String[]{"Tourism", "http://cn.nhandan.com.vn/tourism"});
                    arr.add(new String[]{"Friendshipbridge", "http://cn.nhandan.com.vn/friendshipbridge"});
                    arr.add(new String[]{"Documentation", "http://cn.nhandan.com.vn/documentation"});
                    arr.add(new String[]{"Local", "http://cn.nhandan.com.vn/local"});
                    arr.add(new String[]{"Asean", "http://cn.nhandan.com.vn/asean"});
                    arr.add(new String[]{"Vietnam Window", "http://cn.nhandan.com.vn/vietnamwindow"});
                    arr.add(new String[]{"The nation of Vietnam", "http://cn.nhandan.com.vn/the-nation-of-vietnam"});
                }

                break;

            case "http://baochinhphu.vn":
                if (language == "Vietnamese") {

                    arr.add(new String[]{"Chính trị", "http://baochinhphu.vn/Chinh-tri/442.vgp"});
                    arr.add(new String[]{"Kinh tế", "http://baochinhphu.vn/Kinh-te/7.vgp"});
                    arr.add(new String[]{"Xã hội", "http://baochinhphu.vn/Xa-hoi/449.vgp"});
                    arr.add(new String[]{"Văn hóa", "http://baochinhphu.vn/Van-hoa/249.vgp"});
                    arr.add(new String[]{"Khoa giáo", "http://baochinhphu.vn/Khoa-giao/451.vgp"});
                    arr.add(new String[]{"Quốc tế", "http://baochinhphu.vn/Quoc-te/6.vgp"});
                    arr.add(new String[]{"Hội Nhập", "http://baochinhphu.vn/Hoi-nhap/453.vgp"});
                } else {
                    arr.add(new String[]{"Government activity", "http://cn.news.chinhphu.vn/Home/hoat-dong-dang-cp.vgp"});
                    arr.add(new String[]{"Prime minister", "http://cn.news.chinhphu.vn/Home/thu-tuong-chinh-phu.vgp"});
                    arr.add(new String[]{"Government prime minister guidance and decision", "http://cn.news.chinhphu.vn/Home/chi-dao-ttcp.vgp"});
                    arr.add(new String[]{"Government department activities", "http://cn.news.chinhphu.vn/Home/tin-bo-nganh.vgp"});
                    arr.add(new String[]{"Provinces and cities activities", "http://cn.news.chinhphu.vn/Home/tin-dia-phuong.vgp"});
                    arr.add(new String[]{"Economic Society", "http://cn.news.chinhphu.vn/Home/kinhte-xahoi.vgp"});
                    arr.add(new String[]{"Science & Technology", "http://cn.news.chinhphu.vn/Home/kh-cn.vgp"});
                    arr.add(new String[]{"Cultural Tourism", "http://cn.news.chinhphu.vn/Home/vanhoa-dulich.vgp"});
                    arr.add(new String[]{"Vietnam and the world", "http://cn.news.chinhphu.vn/Home/thegioi-vietnam.vgp"});
                    arr.add(new String[]{"Statistical Bulletin of National Economic and Social Development", "http://cn.news.chinhphu.vn/Home/thongtin-kinhte-xahoi.vgp"});
                }

                break;
        }
        return arr;
    }

    ///////////////////////////////////////////////////////////////
    public static ArrayList<String[]> getClassInfor() {
        ArrayList<String[]> arr = new ArrayList<>();

        arr.add(new String[]{"https://vnexpress.net", "VNExpress", "none"});
        arr.add(new String[]{"https://www.vietnamplus.vn", "VietNamPlusVN", "Vietnamese"});
        arr.add(new String[]{"https://www.vietnamplus.vn", "VietNamPlusCN", "Chinese"});
        arr.add(new String[]{"http://baobinhduong.vn", "BaoBinhDuongVN", "Vietnamese"});
        arr.add(new String[]{"http://baobinhduong.vn", "BaoBinhDuongCN", "Chinese"});
        arr.add(new String[]{"http://www.sggp.org.vn", "SGGPVN", "Vietnamese"});
        arr.add(new String[]{"http://www.sggp.org.vn", "SGGPCN", "Chinese"});
        arr.add(new String[]{"http://thoidai.com.vn", "ThoiDaiVN", "Vietnamese"});
        arr.add(new String[]{"http://thoidai.com.vn", "ThoiDaiCN", "Chinese"});
        arr.add(new String[]{"http://www.tapchicongsan.org.vn", "TapChiCongSanVN", "Vietnamese"});
        arr.add(new String[]{"http://www.tapchicongsan.org.vn", "TapChiCongSanCN", "Chinese"});
        arr.add(new String[]{"http://baothainguyen.org.vn", "BaoThaiNguyenVN", "Vietnamese"});
        arr.add(new String[]{"http://baothainguyen.org.vn", "BaoThaiNguyenCN", "Chinese"});
        arr.add(new String[]{"http://www.nhandan.com.vn", "NhanDanVN", "Vietnamese"});
        arr.add(new String[]{"http://www.nhandan.com.vn", "NhanDanCN", "Chinese"});
        arr.add(new String[]{"http://baochinhphu.vn", "BaoChinhPhuVN", "Vietnamese"});
        arr.add(new String[]{"http://baochinhphu.vn", "BaoChinhPhuCN", "Chinese"});

        return arr;
    }

    ////////////////////
    public static String getClassNameFromWebNameAndLanguage(String webName, String language) {
        String result = null;
        ArrayList<String[]> arr = getClassInfor();

        for (String[] strings : arr) {
            if (strings[0].equals(webName) && strings[2].equals(language)) {
                result = strings[1];
            }
        }
        return result;
    }

    public static String getWebFromClassName(String className) {
        String result = null;
        ArrayList<String[]> arr = getClassInfor();
        for (String[] strings : arr) {
            if (strings[1].equals(className)) {
                result = strings[0];
            }
        }
        return result;
    }

    public static String getLanguageFromClassName(String className) {
        String result = "";
        ArrayList<String[]> arr = getClassInfor();
        for (String[] strings : arr) {
            if (strings[1].equals(className)) {
                result = strings[2];
            }
        }
        return result;
    }

}

class CheckboxListItem {

    private String label;
    private String pageName;
    private boolean isSelected = false;

    public CheckboxListItem(String label, String pageName) {
        this.label = label;
        this.pageName = pageName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getLabel() {
        return label;
    }

    public String getPageName() {
        return pageName;
    }
}

// Handles rendering cells in the list using a check box
class CheckboxListRenderer extends JCheckBox implements ListCellRenderer<CheckboxListItem> {

    @Override
    public Component getListCellRendererComponent(JList<? extends CheckboxListItem> list, CheckboxListItem value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.getLabel());
        try {
            setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/checkbox_unchecked.png"))));
            setSelectedIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/checkbox_checked.png"))));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }
}

class GetPackageNameClass {

    public static String getPackageName() {
        return new GetPackageNameClass().getClass().getPackage().getName();
    }
}
