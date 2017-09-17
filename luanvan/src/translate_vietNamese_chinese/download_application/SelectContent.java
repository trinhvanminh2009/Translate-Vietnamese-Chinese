package translate_vietNamese_chinese.download_application;

import java.awt.Button;
import java.awt.Checkbox;
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
import java.util.concurrent.CountDownLatch;
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
            lblStatusLanguage.setVisible(false);
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
                    lblStatusLanguage.setIcon(icon);
                    lblStatusLanguage.setVisible(true);

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
                    lblStatusLanguage.setIcon(icon);
                    lblStatusLanguage.setVisible(true);
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
                        st = new ScrapingThread(GetPackageNameClass.getPackageName() + "." + className, arr[0], Integer.parseInt(arr[1]) + 1, Integer.parseInt(arr[2]));
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

            CountDownLatch latch = new CountDownLatch(threadList.size());
            for (int i = 0; i < threadList.size(); i++) {
                new RequestStopThread(threadList.get(i), latch).start();
            }

            try {
                latch.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(SelectContent.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (ScrapingThread scrapingThread : threadList) {
                if (scrapingThread.getStateDownload() == true) {
                    System.out.println("DONE " + scrapingThread.getName());
                }
                System.out.println("-----" + scrapingThread.getClassName() + " " + scrapingThread.getPageName() + " "
                        + scrapingThread.getCurrentPage() + " " + scrapingThread.getArticleSize());
            }
            if (downloadDone == false) {
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DOWNLOAD_LOG + getClassNameFromWebNameAndLanguage(pageName, language) + ".txt"), "utf-8"))) {
                    for (ScrapingThread scrapingThread : threadList) {
                        if (scrapingThread.getStateDownload() == false && scrapingThread.getArticleSize() > 0) {
                            writer.write(scrapingThread.getPageName() + " "
                                    + scrapingThread.getCurrentPage() + " " + scrapingThread.getArticleSize() + "\n");
                        }
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
            //lblStatusLanguage.setVisible(false);
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
                    while (per != 100 || downloadDone == false) {
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
                                JOptionPane.showMessageDialog(null, "Download Complete!");
                                btnPause.setEnabled(false);
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

                for (String[] strings : arr) {
                    if (strings[1].equals(pageName)) {
                        return strings[0];
                    }
                }

                break;
            case "http://www.vietnamplus.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "http://www.vietnamplus.vn/kinhte.vnp"});
                    arr.add(new String[]{"Chính trị", "http://www.vietnamplus.vn/chinhtri.vnp"});
                    arr.add(new String[]{"Xã hội", "http://www.vietnamplus.vn/xahoi.vnp"});
                    arr.add(new String[]{"Thế giới - ASEAN", "http://www.vietnamplus.vn/thegioi/asean.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Á-TBD", "http://www.vietnamplus.vn/thegioi/chaua-tbd.vnp"});
                    arr.add(new String[]{"Thế giới - Trung Đông", "http://www.vietnamplus.vn/thegioi/trungdong.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Âu", "http://www.vietnamplus.vn/thegioi/chauau.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Mỹ", "http://www.vietnamplus.vn/thegioi/chaumy.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Phi", "http://www.vietnamplus.vn/thegioi/chauphi.vnp"});
                    arr.add(new String[]{"Đời sống", "http://www.vietnamplus.vn/doisong.vnp"});
                    arr.add(new String[]{"Văn hóa", "http://www.vietnamplus.vn/vanhoa.vnp"});
                    arr.add(new String[]{"Thể thao - Bóng đá", "http://www.vietnamplus.vn/thethao/bongda.vnp"});
                    arr.add(new String[]{"Thể thao - Quần vợt", "http://www.vietnamplus.vn/thethao/quanvot.vnp"});
                    arr.add(new String[]{"Thể thao - Sea Games 29", "http://www.vietnamplus.vn/thethao/seagames29.vnp"});
                    arr.add(new String[]{"Khoa học", "http://www.vietnamplus.vn/khoahoc.vnp"});
                    arr.add(new String[]{"Công nghệ", "http://www.vietnamplus.vn/congnghe.vnp"});
                    arr.add(new String[]{"Chuyện lạ", "http://www.vietnamplus.vn/chuyenla.vnp"});
                } else {
                    arr.add(new String[]{"Politics", "http://zh.vietnamplus.vn/politics.vnp"});
                    arr.add(new String[]{"World", "http://zh.vietnamplus.vn/world.vnp"});
                    arr.add(new String[]{"Business", "http://zh.vietnamplus.vn/business.vnp"});
                    arr.add(new String[]{"Social", "http://zh.vietnamplus.vn/social.vnp"});
                    arr.add(new String[]{"Culture", "http://zh.vietnamplus.vn/culture.vnp"});
                    arr.add(new String[]{"Sports", "http://zh.vietnamplus.vn/sports.vnp"});
                    arr.add(new String[]{"Technology", "http://zh.vietnamplus.vn/technology.vnp"});
                    arr.add(new String[]{"Environment", "http://zh.vietnamplus.vn/environment.vnp"});
                    arr.add(new String[]{"Travel", "http://zh.vietnamplus.vn/Travel.vnp"});
                }

                for (String[] strings : arr) {
                    if (strings[1].equals(pageName)) {
                        return strings[0];
                    }
                }

                break;
        }
        return result;
    }

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
            case "http://www.vietnamplus.vn":
                if (language == "Vietnamese") {
                    arr.add(new String[]{"Kinh tế", "http://www.vietnamplus.vn/kinhte.vnp"});
                    arr.add(new String[]{"Chính trị", "http://www.vietnamplus.vn/chinhtri.vnp"});
                    arr.add(new String[]{"Xã hội", "http://www.vietnamplus.vn/xahoi.vnp"});
                    arr.add(new String[]{"Thế giới - ASEAN", "http://www.vietnamplus.vn/thegioi/asean.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Á-TBD", "http://www.vietnamplus.vn/thegioi/chaua-tbd.vnp"});
                    arr.add(new String[]{"Thế giới - Trung Đông", "http://www.vietnamplus.vn/thegioi/trungdong.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Âu", "http://www.vietnamplus.vn/thegioi/chauau.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Mỹ", "http://www.vietnamplus.vn/thegioi/chaumy.vnp"});
                    arr.add(new String[]{"Thế giới - Châu Phi", "http://www.vietnamplus.vn/thegioi/chauphi.vnp"});
                    arr.add(new String[]{"Đời sống", "http://www.vietnamplus.vn/doisong.vnp"});
                    arr.add(new String[]{"Văn hóa", "http://www.vietnamplus.vn/vanhoa.vnp"});
                    arr.add(new String[]{"Thể thao - Bóng đá", "http://www.vietnamplus.vn/thethao/bongda.vnp"});
                    arr.add(new String[]{"Thể thao - Quần vợt", "http://www.vietnamplus.vn/thethao/quanvot.vnp"});
                    arr.add(new String[]{"Thể thao - Sea Games 29", "http://www.vietnamplus.vn/thethao/seagames29.vnp"});
                    arr.add(new String[]{"Khoa học", "http://www.vietnamplus.vn/khoahoc.vnp"});
                    arr.add(new String[]{"Công nghệ", "http://www.vietnamplus.vn/congnghe.vnp"});
                    arr.add(new String[]{"Chuyện lạ", "http://www.vietnamplus.vn/chuyenla.vnp"});
                } else {
                    arr.add(new String[]{"Politics", "http://zh.vietnamplus.vn/politics.vnp"});
                    arr.add(new String[]{"World", "http://zh.vietnamplus.vn/world.vnp"});
                    arr.add(new String[]{"Business", "http://zh.vietnamplus.vn/business.vnp"});
                    arr.add(new String[]{"Social", "http://zh.vietnamplus.vn/social.vnp"});
                    arr.add(new String[]{"Culture", "http://zh.vietnamplus.vn/culture.vnp"});
                    arr.add(new String[]{"Sports", "http://zh.vietnamplus.vn/sports.vnp"});
                    arr.add(new String[]{"Technology", "http://zh.vietnamplus.vn/technology.vnp"});
                    arr.add(new String[]{"Environment", "http://zh.vietnamplus.vn/environment.vnp"});
                    arr.add(new String[]{"Travel", "http://zh.vietnamplus.vn/Travel.vnp"});
                }

                break;
        }
        return arr;
    }
    ////////////////////
    public static String getClassNameFromWebNameAndLanguage(String webName, String language) {
        String result = null;
        ArrayList<String[]> arr = new ArrayList<>();
        arr.add(new String[]{"https://vnexpress.net", "VNExpress", "none"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusVN", "Vietnamese"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusCN", "Chinese"});
        for (String[] strings : arr) {
            if (strings[0].equals(webName) && strings[2].equals(language)) {
                result = strings[1];
            }
        }
        return result;
    }

    public static String getWebNameFromClassName(String className) {
        String result = null;
        ArrayList<String[]> arr = new ArrayList<>();
        arr.add(new String[]{"https://vnexpress.net", "VNExpress", "none"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusVN", "Vietnamese"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusCN", "Chinese"});
        for (String[] strings : arr) {
            if (strings[1].equals(className)) {
                result = strings[0];
            }
        }
        return result;
    }

    public static String getLanguageFromClassName(String className) {
        String result = "";
        ArrayList<String[]> arr = new ArrayList<>();
        arr.add(new String[]{"https://vnexpress.net", "VNExpress", "none"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusVN", "Vietnamese"});
        arr.add(new String[]{"http://www.vietnamplus.vn", "VietNamPlusCN", "Chinese"});
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
