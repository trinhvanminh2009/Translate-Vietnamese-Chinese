package translate_vietNamese_chinese.download_application;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;

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
                    window.frmDownloadApplication.setLocationRelativeTo(null);

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

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            return null;
        }
    }

    /**
     * Initialize the contents of the frame...
     */
    private void initialize() {
        frmDownloadApplication = new JFrame();
        frmDownloadApplication.setType(Type.POPUP);
        frmDownloadApplication.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        frmDownloadApplication.getContentPane().setBackground(Color.GRAY);
        frmDownloadApplication.getContentPane().setLayout(null);
        frmDownloadApplication.setLocationRelativeTo(null);
        frmDownloadApplication.setFont(new Font("Serif", Font.BOLD, 12));
        ButtonGroup groupSelectPage = new ButtonGroup();
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        tabbedPane.setBounds(0, 0, 632, 424);
        frmDownloadApplication.getContentPane().add(tabbedPane);
        ///////////////////////////////////// Monolingual
        ///////////////////////////////////// handle///////////////////////////////////////////////////////////////////
        JPanel panel_Monolingual = new JPanel();
        tabbedPane.addTab("Monolingual", null, panel_Monolingual, null);
        panel_Monolingual.setForeground(Color.GREEN);
        panel_Monolingual
                .setBorder(new TitledBorder(null, "Select page", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JRadioButton rdbtnVnExpress = new JRadioButton("https://vnexpress.net");
        rdbtnVnExpress.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnVnExpress.setBounds(6, 92, 191, 41);
        rdbtnVnExpress.setActionCommand("https://vnexpress.net");
        try {
            rdbtnVnExpress.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnVnExpress.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnNguoiLaoDong = new JRadioButton("http://nld.com.vn/");
        rdbtnNguoiLaoDong.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnNguoiLaoDong.setBounds(6, 218, 177, 41);
        rdbtnNguoiLaoDong.setActionCommand("http://nld.com.vn/");
        try {
            rdbtnNguoiLaoDong.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnNguoiLaoDong.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnDanTri = new JRadioButton("http://dantri.com.vn/");
        rdbtnDanTri.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnDanTri.setBounds(288, 35, 177, 41);
        rdbtnDanTri.setActionCommand("http://dantri.com.vn/");
        try {
            rdbtnDanTri.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnDanTri.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnNgoiSaoNet = new JRadioButton("http://ngoisao.net/");
        rdbtnNgoiSaoNet.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnNgoiSaoNet.setBounds(6, 35, 191, 41);
        rdbtnNgoiSaoNet.setActionCommand("http://ngoisao.net/");
        try {
            rdbtnNgoiSaoNet.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnNgoiSaoNet.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnNgoiSaoVN = new JRadioButton("https://ngoisao.vn/");
        rdbtnNgoiSaoVN.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnNgoiSaoVN.setBounds(288, 92, 177, 41);
        rdbtnNgoiSaoVN.setActionCommand("https://ngoisao.vn/");
        try {
            rdbtnNgoiSaoVN.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnNgoiSaoVN.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnTuoitre = new JRadioButton("http://tuoitre.vn/");
        rdbtnTuoitre.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnTuoitre.setBounds(6, 155, 177, 41);
        rdbtnTuoitre.setActionCommand("http://tuoitre.vn/");
        try {
            rdbtnTuoitre.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnTuoitre.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnBongDaPlus = new JRadioButton("http://bongdaplus.vn/");
        rdbtnBongDaPlus.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnBongDaPlus.setBounds(288, 155, 191, 41);
        rdbtnBongDaPlus.setActionCommand("http://bongdaplus.vn/");
        try {
            rdbtnBongDaPlus.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnBongDaPlus.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JList list = new JList();
        list.setBounds(-10008, -10031, 0, 0);
        panel_Monolingual.setLayout(null);
        panel_Monolingual.add(rdbtnVnExpress);
        panel_Monolingual.add(rdbtnNguoiLaoDong);
        panel_Monolingual.add(rdbtnDanTri);
        panel_Monolingual.add(rdbtnNgoiSaoNet);

        panel_Monolingual.add(rdbtnNgoiSaoVN);
        panel_Monolingual.add(rdbtnTuoitre);
        panel_Monolingual.add(rdbtnBongDaPlus);
        panel_Monolingual.add(list);

        // Set command for each button
        groupSelectPage.add(rdbtnNgoiSaoNet);
        groupSelectPage.add(rdbtnDanTri);
        groupSelectPage.add(rdbtnNguoiLaoDong);
        groupSelectPage.add(rdbtnTuoitre);
        groupSelectPage.add(rdbtnVnExpress);
        groupSelectPage.add(rdbtnNgoiSaoVN);
        groupSelectPage.add(rdbtnBongDaPlus);
        BufferedImage btnCancelImage;
        try {
            btnCancelImage = ImageIO.read(this.getClass().getResource("/resources/button_cancel.png"));
            JButton btnCancel_Monolingual = new JButton(new ImageIcon(btnCancelImage));
            btnCancel_Monolingual.setBounds(207, 277, 135, 50);
            panel_Monolingual.add(btnCancel_Monolingual);
            btnCancel_Monolingual.setBorder(BorderFactory.createEmptyBorder());
            btnCancel_Monolingual.setContentAreaFilled(false);
            btnCancel_Monolingual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    groupSelectPage.clearSelection();

                }
            });

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        JLabel lbStatus_Monolingual = new JLabel("Status");
        lbStatus_Monolingual.setFont(new Font("Tahoma", Font.BOLD, 13));
        lbStatus_Monolingual.setForeground(Color.RED);
        lbStatus_Monolingual.setBounds(27, 11, 374, 23);
        panel_Monolingual.add(lbStatus_Monolingual);
        lbStatus_Monolingual.setVisible(false);

        try {
            BufferedImage btnCloseImage = ImageIO.read(this.getClass().getResource("/resources/button_close.png"));
            JButton btnClose_Monolingual = new JButton(new ImageIcon(btnCloseImage));
            btnClose_Monolingual.setBorder(BorderFactory.createEmptyBorder());
            btnClose_Monolingual.setBounds(10, 277, 135, 50);
            btnClose_Monolingual.setContentAreaFilled(false);
            panel_Monolingual.add(btnClose_Monolingual);

            btnClose_Monolingual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedImage btnNextImage;
        try {
            btnNextImage = ImageIO.read(this.getClass().getResource("/resources/button_next.png"));
            JButton btnNext_Monoligual = new JButton(new ImageIcon(btnNextImage));
            panel_Monolingual.add(btnNext_Monoligual);
            btnNext_Monoligual.setBounds(408, 277, 120, 50);
            btnNext_Monoligual.setBorder(BorderFactory.createEmptyBorder());
            btnNext_Monoligual.setContentAreaFilled(false);
            btnNext_Monoligual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    // Get action comment to parse to SelectContent
                    if (groupSelectPage.getSelection() != null) {
                        SelectContent selectContent = new SelectContent(
                                groupSelectPage.getSelection().getActionCommand(), "", false);
                        lbStatus_Monolingual.setVisible(false);
                        selectContent.setLocationRelativeTo(null);
                        selectContent.show();
                    } else {
                        lbStatus_Monolingual.setText("Please select a page you want to download");
                        lbStatus_Monolingual.setVisible(true);
                    }

                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ///////////////////////////////////////////////////// Bilingual
        ///////////////////////////////////////////////////// handle///////////////////////////////////////////////////////////////
        JPanel panel_Bilingual = new JPanel();
        tabbedPane.addTab("Bilingual", null, panel_Bilingual, null);
        panel_Bilingual.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(
                new TitledBorder(null, "Select page and language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(0, 0, 627, 396);
        panel_Bilingual.add(panel);
        panel.setLayout(null);

        JLabel lblStatus_Bilingual = new JLabel("Status");
        lblStatus_Bilingual.setBounds(20, 11, 398, 16);
        panel.add(lblStatus_Bilingual);
        lblStatus_Bilingual.setForeground(Color.RED);
        lblStatus_Bilingual.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblStatus_Bilingual.setVisible(false);

        JRadioButton rdbtnVietnamPlus = new JRadioButton("http://www.vietnamplus.vn");
        rdbtnVietnamPlus.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnVietnamPlus.setBounds(30, 29, 242, 41);
        rdbtnVietnamPlus.setActionCommand("http://www.vietnamplus.vn");
        panel.add(rdbtnVietnamPlus);
        try {
            rdbtnVietnamPlus.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnVietnamPlus.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnDongNaiGov = new JRadioButton("https://www.dongnai.gov.vn/");
        rdbtnDongNaiGov.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnDongNaiGov.setBounds(30, 92, 230, 41);
        rdbtnDongNaiGov.setActionCommand("https://www.dongnai.gov.vn/");
        panel.add(rdbtnDongNaiGov);
        try {
            rdbtnDongNaiGov.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnDongNaiGov.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnSaiGonGiaiPhong = new JRadioButton("http://www.sggp.org.vn/");
        rdbtnSaiGonGiaiPhong.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnSaiGonGiaiPhong.setBounds(30, 152, 217, 41);
        rdbtnSaiGonGiaiPhong.setActionCommand("http://www.sggp.org.vn/");
        panel.add(rdbtnSaiGonGiaiPhong);
        try {
            rdbtnSaiGonGiaiPhong.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnSaiGonGiaiPhong.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnBaoDinhDuong = new JRadioButton("http://baobinhduong.vn/");
        rdbtnBaoDinhDuong.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnBaoDinhDuong.setBounds(276, 25, 187, 41);
        rdbtnBaoDinhDuong.setActionCommand("http://baobinhduong.vn/");
        panel.add(rdbtnBaoDinhDuong);
        try {
            rdbtnBaoDinhDuong.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnBaoDinhDuong.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnNhanDan = new JRadioButton("http://www.nhandan.com.vn/");
        rdbtnNhanDan.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnNhanDan.setBounds(276, 152, 215, 41);
        rdbtnNhanDan.setActionCommand("http://www.nhandan.com.vn/");
        panel.add(rdbtnNhanDan);
        try {
            rdbtnNhanDan.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnNhanDan.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnBaoChinhPhu = new JRadioButton("http://baochinhphu.vn/");
        rdbtnBaoChinhPhu.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnBaoChinhPhu.setBounds(276, 92, 187, 41);
        rdbtnBaoChinhPhu.setActionCommand("http://baochinhphu.vn/");
        panel.add(rdbtnBaoChinhPhu);
        try {
            rdbtnBaoChinhPhu.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnBaoChinhPhu.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnQuanDoiNhanDan = new JRadioButton("http://www.qdnd.vn/");
        rdbtnQuanDoiNhanDan.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnQuanDoiNhanDan.setBounds(30, 210, 174, 41);
        rdbtnQuanDoiNhanDan.setActionCommand("http://www.qdnd.vn/");
        panel.add(rdbtnQuanDoiNhanDan);
        try {
            rdbtnQuanDoiNhanDan.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnQuanDoiNhanDan.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnTapChiCongSan = new JRadioButton("http://www.tapchicongsan.org.vn/");
        rdbtnTapChiCongSan.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnTapChiCongSan.setBounds(276, 210, 267, 41);
        rdbtnTapChiCongSan.setActionCommand("http://www.tapchicongsan.org.vn/");
        panel.add(rdbtnTapChiCongSan);
        try {
            rdbtnTapChiCongSan.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnTapChiCongSan.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnThoiDai = new JRadioButton("http://thoidai.com.vn/");
        rdbtnThoiDai.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnThoiDai.setBounds(30, 274, 187, 41);
        rdbtnThoiDai.setActionCommand("http://thoidai.com.vn/");
        panel.add(rdbtnThoiDai);
        try {
            rdbtnThoiDai.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnThoiDai.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnBaoThaiNguyen = new JRadioButton("http://baothainguyen.org.vn/");
        rdbtnBaoThaiNguyen.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnBaoThaiNguyen.setBounds(276, 274, 240, 41);
        rdbtnBaoThaiNguyen.setActionCommand("http://baothainguyen.org.vn/");
        panel.add(rdbtnBaoThaiNguyen);
        try {
            rdbtnBaoThaiNguyen.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnBaoThaiNguyen.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JPanel panel_languages = new JPanel();
        panel_languages.setBorder(
                new TitledBorder(null, "Select language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_languages.setBounds(469, 39, 148, 90);
        panel.add(panel_languages);
        panel_languages.setLayout(null);

        ButtonGroup radioGroupBilingual = new ButtonGroup();
        radioGroupBilingual.add(rdbtnVietnamPlus);
        radioGroupBilingual.add(rdbtnDongNaiGov);
        radioGroupBilingual.add(rdbtnSaiGonGiaiPhong);
        radioGroupBilingual.add(rdbtnBaoDinhDuong);
        radioGroupBilingual.add(rdbtnNhanDan);
        radioGroupBilingual.add(rdbtnTapChiCongSan);
        radioGroupBilingual.add(rdbtnThoiDai);
        radioGroupBilingual.add(rdbtnBaoThaiNguyen);
        radioGroupBilingual.add(rdbtnBaoChinhPhu);
        radioGroupBilingual.add(rdbtnQuanDoiNhanDan);

        JRadioButton rdbtnVietnamese = new JRadioButton("Vietnamese");
        rdbtnVietnamese.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnVietnamese.setBounds(6, 17, 136, 30);
        panel_languages.add(rdbtnVietnamese);
        rdbtnVietnamese.setActionCommand("Vietnamese");
        try {
            rdbtnVietnamese.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnVietnamese.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        JRadioButton rdbtnChinese = new JRadioButton("Chinese");
        rdbtnChinese.setFont(new Font("Tahoma", Font.PLAIN, 13));
        rdbtnChinese.setBounds(6, 50, 97, 36);
        panel_languages.add(rdbtnChinese);
        rdbtnChinese.setActionCommand("Chinese");
        try {
            rdbtnChinese.setIcon(
                    new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_unselected.png"))));
            rdbtnChinese.setSelectedIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/radio_button_selected.png"))));
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        ButtonGroup groupLanguages = new ButtonGroup();
        groupLanguages.add(rdbtnVietnamese);
        groupLanguages.add(rdbtnChinese);

        BufferedImage btnCloseImage;
        try {
            btnCloseImage = ImageIO.read(this.getClass().getResource("/resources/button_close.png"));
            JButton btnClose_Bilingual = new JButton(new ImageIcon(btnCloseImage));
            btnClose_Bilingual.setBounds(30, 323, 119, 55);
            btnClose_Bilingual.setBorder(BorderFactory.createEmptyBorder());
            btnClose_Bilingual.setContentAreaFilled(false);
            panel.add(btnClose_Bilingual);
            btnClose_Bilingual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    System.exit(0);

                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            btnCancelImage = ImageIO.read(this.getClass().getResource("/resources/button_cancel.png"));
            JButton btnCancel_Bilingual = new JButton(new ImageIcon(btnCancelImage));
            btnCancel_Bilingual.setBounds(232, 323, 119, 50);
            btnCancel_Bilingual.setBorder(BorderFactory.createEmptyBorder());
            btnCancel_Bilingual.setContentAreaFilled(false);
            panel.add(btnCancel_Bilingual);
            btnCancel_Bilingual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    radioGroupBilingual.clearSelection();
                    groupLanguages.clearSelection();
                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            btnNextImage = ImageIO.read(this.getClass().getResource("/resources/button_next.png"));
            JButton btnNext_Bilingual = new JButton(new ImageIcon(btnNextImage));
            btnNext_Bilingual.setBounds(424, 323, 119, 50);
            btnNext_Bilingual.setBorder(BorderFactory.createEmptyBorder());
            btnNext_Bilingual.setContentAreaFilled(false);
            panel.add(btnNext_Bilingual);
            btnNext_Bilingual.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if (radioGroupBilingual.getSelection() != null && groupLanguages.getSelection() != null) {
                        SelectContent selectContent = new SelectContent(
                                radioGroupBilingual.getSelection().getActionCommand(),
                                groupLanguages.getSelection().getActionCommand(), false);
                        lblStatus_Bilingual.setVisible(false);
                        selectContent.setLocationRelativeTo(null);
                        selectContent.show();
                    } else {
                        lblStatus_Bilingual.setText("Please select a page and language you want to download ");
                        lblStatus_Bilingual.setVisible(true);

                    }
                }
            });
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ///////////////////////////////////////////////////// Bilingual
        ///////////////////////////////////////////////////// handle///////////////////////////////////////////////////////////////
        JPanel paneResume = new JPanel();
        tabbedPane.addTab("Resume", null, paneResume, null);
        paneResume.setLayout(null);

        JComboBox comboBox = new JComboBox();
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
        comboBox.setBounds(109, 52, 381, 35);
        paneResume.add(comboBox);
        if (SelectContent.checkResumeable()) {
            File f = new File(SelectContent.DOWNLOAD_LOG);
            for (File file : f.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            })) {

                comboBox.addItem(file.getName().split("\\.")[0]);
            }
        }

        BufferedImage btnResumeImage;
        try {
            btnResumeImage = ImageIO.read(this.getClass().getResource("/resources/button_resume.png"));
            JButton btnResume = new JButton(new ImageIcon(btnResumeImage));
            if (comboBox.getModel().getSize() > 0) {
                btnResume.setEnabled(true);
            } else {
                btnResume.setEnabled(false);
            }
            btnResume.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    String webName = comboBox.getSelectedItem().toString();
                    SelectContent t = new SelectContent(SelectContent.getWebNameFromClassName(webName), "", true);
                    lblStatus_Bilingual.setVisible(false);
                    t.setLocationRelativeTo(null);
                    t.show();
                }
            });
            btnResume.setBorder(BorderFactory.createEmptyBorder());
            btnResume.setBounds(187, 124, 135, 50);
            btnResume.setContentAreaFilled(false);
            paneResume.add(btnResume);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // Add all radio button into one group to make sure only select one
        // radio button in group in one time
        frmDownloadApplication.setBackground(SystemColor.textHighlight);
        frmDownloadApplication.setTitle("Download Application");
        URL urlImageIcon = SelectContent.class.getResource("/resources/ic_download.png");
        frmDownloadApplication.setIconImage(Toolkit.getDefaultToolkit().getImage(urlImageIcon));
        frmDownloadApplication.setBounds(100, 100, 644, 456);
        frmDownloadApplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
