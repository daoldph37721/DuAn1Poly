/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.SanPham;
import service.HoaDonChiTietService;
import service.KhachHangService;
import service.ThongKeService;

/**
 *
 * @author ledin
 */
public class HoaDonUI extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    List<SanPham> listSP = new ArrayList<>();
    List<HoaDon> listHD = new ArrayList<>();
    List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    List<KhachHang> listKH = new ArrayList<>();

    KhachHangService serviceKH = new KhachHangService();
    ThongKeService serviceThongKe = new ThongKeService();
    HoaDonChiTietService serviceHDCT = new HoaDonChiTietService();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    DecimalFormat currencyFormat = new DecimalFormat("#,##0");

    public HoaDonUI() {
        initComponents();
        setLocationRelativeTo(null);
        listKH = serviceKH.getAllKhachHang();
        listHD = serviceThongKe.getAllHoaDon();
        fillToTableHD(listHD);
        initWebcam();
    }

    public void openKhuyenmai() {
        stopWbcam();
        dispose();
        new KhuyenMaiUI().setVisible(true);
    }

    public void openHoaDon_Main() {
        stopWbcam();
        dispose();
        new MainFrame().setVisible(true);
    }

    public void openKhachHangUI() {
        stopWbcam();
        dispose();
        new KhachHangUI().setVisible(true);
    }

    public void openThongKeUI() {
        stopWbcam();
        dispose();
        new ThongKeUI().setVisible(true);
    }

    public void openNhanVien() {
        stopWbcam();
        dispose();
        new NhanVienUI().setVisible(true);
    }

    public void openSanPham() {
        stopWbcam();
        dispose();
        new SanPhamUI().setVisible(true);
    }

    public void openDangXuat() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn Đăng xuất không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            stopWbcam();
            dispose();
            System.exit(0);
        }
    }

    public void openHoaDon() {
        stopWbcam();
        dispose();
        new HoaDonUI().setVisible(true);
    }

    public void fillToTableHD(List<HoaDon> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String Cols[] = {"Mã HD", "Mã NV", "Mã KH", "Mã KM",
            "Mã HTTT", "Ngày Tạo", "Ngày Thanh Toán", "Tổng tiền",
            "Giảm giá", "Thành tiền", "Trạng thái"};
        tblModel.setColumnIdentifiers(Cols);
        tblModel.setRowCount(0);
        for (HoaDon hd : list) {
            String trangThai = "";
            if (hd.getTrangThai() == 0) {
                trangThai = "Chưa thanh toán";
            } else if (hd.getTrangThai() == 1) {
                trangThai = "Đã thanh toán";
            } else {
                trangThai = "Đã hủy";
            }
            String tongTien = currencyFormat.format(hd.getTongTien());
            String giamGia = currencyFormat.format(hd.getGiamGia());
            String thanhTien = currencyFormat.format(hd.getThanhTien());
            tblModel.addRow(new Object[]{hd.getMaHoaDon(), hd.getMaNhanVien(), hd.getMaKhachHang(),
                hd.getMaKhuyenMai(), hd.getMaHTTT(), hd.getNgayTao(), hd.getNgayThanhToan(),
                tongTien, giamGia, thanhTien, trangThai});
        }
        tblHoaDonThongKe.setModel(tblModel);
    }

    public void fillToTableHDCT(List<HoaDonChiTiet> list) {
        DefaultTableModel tblModel3 = new DefaultTableModel();
        String Cols[] = {"STT", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tblModel3.setColumnIdentifiers(Cols);
        tblModel3.setRowCount(0);
        int i = 0;
        for (HoaDonChiTiet hdct : list) {
            i++;
            String donGia = currencyFormat.format(hdct.getDonGia());
            String thanhTien = currencyFormat.format(hdct.getThanhTien());
            tblModel3.addRow(new Object[]{i, hdct.getMaSPCT(), hdct.getTenSanPham(),
                hdct.getSoLuong(), donGia, thanhTien});
        }
        tblHDCTThongKe.setModel(tblModel3);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        pnlSanPham = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnlKhachHang = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        pnlNhanVien = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        pnlKhuyenMai = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pnlHoaDon = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        pnlThongKe = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pnlDangXuat = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlHoaDonGoc = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNhanVienTheoDN = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDonThongKe = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHDCTThongKe = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cboSapXepHD = new javax.swing.JComboBox<>();
        cboTrangThaiHD = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        dcsTimeOverHD = new com.toedter.calendar.JDateChooser();
        btnHuyLoc = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        dcsTimeBeginHD = new com.toedter.calendar.JDateChooser();
        btnLocHoaDon = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btnInHoaDon = new javax.swing.JButton();
        btnChinhSuaHoaDon = new javax.swing.JButton();
        pnlWebcam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setPreferredSize(new java.awt.Dimension(347, 1023));

        pnlSanPham.setBackground(new java.awt.Color(0, 102, 102));
        pnlSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSanPhamMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/shoes.png"))); // NOI18N
        jLabel13.setText("    Sản phẩm ");

        javax.swing.GroupLayout pnlSanPhamLayout = new javax.swing.GroupLayout(pnlSanPham);
        pnlSanPham.setLayout(pnlSanPhamLayout);
        pnlSanPhamLayout.setHorizontalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSanPhamLayout.setVerticalGroup(
            pnlSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSanPhamLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlKhachHang.setBackground(new java.awt.Color(0, 102, 102));
        pnlKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlKhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlKhachHangMouseEntered(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Clien list.png"))); // NOI18N
        jLabel21.setText("   Khách hàng ");

        javax.swing.GroupLayout pnlKhachHangLayout = new javax.swing.GroupLayout(pnlKhachHang);
        pnlKhachHang.setLayout(pnlKhachHangLayout);
        pnlKhachHangLayout.setHorizontalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKhachHangLayout.setVerticalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachHangLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pnlNhanVien.setBackground(new java.awt.Color(0, 102, 102));
        pnlNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNhanVienMouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Person-Male-Light-icon-24.png"))); // NOI18N
        jLabel19.setText("    Nhân viên ");

        javax.swing.GroupLayout pnlNhanVienLayout = new javax.swing.GroupLayout(pnlNhanVien);
        pnlNhanVien.setLayout(pnlNhanVienLayout);
        pnlNhanVienLayout.setHorizontalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        pnlNhanVienLayout.setVerticalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNhanVienLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pnlKhuyenMai.setBackground(new java.awt.Color(0, 102, 102));
        pnlKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlKhuyenMaiMouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Gift.png"))); // NOI18N
        jLabel17.setText("Khuyến Mại");

        javax.swing.GroupLayout pnlKhuyenMaiLayout = new javax.swing.GroupLayout(pnlKhuyenMai);
        pnlKhuyenMai.setLayout(pnlKhuyenMaiLayout);
        pnlKhuyenMaiLayout.setHorizontalGroup(
            pnlKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhuyenMaiLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKhuyenMaiLayout.setVerticalGroup(
            pnlKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhuyenMaiLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlHoaDon.setBackground(new java.awt.Color(0, 102, 102));
        pnlHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHoaDonMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Full basket.png"))); // NOI18N
        jLabel16.setText("Bán hàng");

        javax.swing.GroupLayout pnlHoaDonLayout = new javax.swing.GroupLayout(pnlHoaDon);
        pnlHoaDon.setLayout(pnlHoaDonLayout);
        pnlHoaDonLayout.setHorizontalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonLayout.setVerticalGroup(
            pnlHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHoaDonLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pnlThongKe.setBackground(new java.awt.Color(0, 102, 102));
        pnlThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlThongKeMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Bar chart.png"))); // NOI18N
        jLabel18.setText("    Thống kê");

        javax.swing.GroupLayout pnlThongKeLayout = new javax.swing.GroupLayout(pnlThongKe);
        pnlThongKe.setLayout(pnlThongKeLayout);
        pnlThongKeLayout.setHorizontalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThongKeLayout.setVerticalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlDangXuat.setBackground(new java.awt.Color(0, 102, 102));
        pnlDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDangXuatMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Exit.png"))); // NOI18N
        jLabel10.setText("    Đăng xuất ");

        javax.swing.GroupLayout pnlDangXuatLayout = new javax.swing.GroupLayout(pnlDangXuat);
        pnlDangXuat.setLayout(pnlDangXuatLayout);
        pnlDangXuatLayout.setHorizontalGroup(
            pnlDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDangXuatLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDangXuatLayout.setVerticalGroup(
            pnlDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDangXuatLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pnlHoaDonGoc.setBackground(new java.awt.Color(255, 153, 153));
        pnlHoaDonGoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHoaDonGocMouseClicked(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Price list.png"))); // NOI18N
        jLabel25.setText("     Hóa đơn");

        javax.swing.GroupLayout pnlHoaDonGocLayout = new javax.swing.GroupLayout(pnlHoaDonGoc);
        pnlHoaDonGoc.setLayout(pnlHoaDonGocLayout);
        pnlHoaDonGocLayout.setHorizontalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonGocLayout.setVerticalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlKhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHoaDon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(pnlHoaDonGoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlHoaDonGoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setMinimumSize(new java.awt.Dimension(1530, 80));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel2.setText("I");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 51));
        jLabel3.setText("Giày thể thao Big Foot");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel5.setText("I");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel7.setText("I");

        lblNhanVienTheoDN.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNhanVienTheoDN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/profile.png"))); // NOI18N
        lblNhanVienTheoDN.setText("Hello Admin ");

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/list.png"))); // NOI18N

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Bell.png"))); // NOI18N

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/email.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(585, 585, 585)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNhanVienTheoDN, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(117, 117, 117)
                .addComponent(jLabel6)
                .addGap(222, 222, 222)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel39)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel35))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel38)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel36))
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(58, 58, 58))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel33))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(16, 16, 16))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNhanVienTheoDN)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(23, 23, 23))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 3));

        tblHoaDonThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblHoaDonThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonThongKeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDonThongKe);

        tblHDCTThongKe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SPCT", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền", "Trạng Thái"
            }
        ));
        jScrollPane3.setViewportView(tblHDCTThongKe);

        jLabel12.setText("Danh sách hóa đơn");

        jLabel55.setText("Hóa đơn chi tiết");

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2));

        jLabel23.setText("Sắp xếp theo");

        cboSapXepHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---lựa chọn---", "Thành tiền giảm dần", "Ngày tạo tăng dần" }));
        cboSapXepHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSapXepHDActionPerformed(evt);
            }
        });

        cboTrangThaiHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Chưa thanh toán", "Đã thanh toán", "Đã hủy" }));
        cboTrangThaiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiHDActionPerformed(evt);
            }
        });

        jLabel14.setText("Trạng Thái");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSapXepHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboSapXepHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 2));

        dcsTimeOverHD.setDateFormatString("yyyy-MM-dd");

        btnHuyLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Actions-edit-delete-icon-16.png"))); // NOI18N
        btnHuyLoc.setText("Hủy");
        btnHuyLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyLocActionPerformed(evt);
            }
        });

        jLabel15.setText("đến ngày");

        dcsTimeBeginHD.setDateFormatString("yyyy-MM-dd");

        btnLocHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Zoom.png"))); // NOI18N
        btnLocHoaDon.setText("Lọc hóa đơn");
        btnLocHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocHoaDonActionPerformed(evt);
            }
        });

        jLabel20.setText("Từ ngày");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(btnLocHoaDon)
                .addGap(37, 37, 37)
                .addComponent(btnHuyLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcsTimeBeginHD, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(41, 41, 41)
                .addComponent(dcsTimeOverHD, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLocHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyLoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcsTimeBeginHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcsTimeOverHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(25, 25, 25))
        );

        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnChinhSuaHoaDon.setText("Chỉnh sửa hóa đơn");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnInHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChinhSuaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(42, 42, 42))))
            .addComponent(jScrollPane3)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnInHoaDon)
                        .addGap(18, 18, 18)
                        .addComponent(btnChinhSuaHoaDon)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(930, Short.MAX_VALUE)
                .addComponent(pnlWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1313, Short.MAX_VALUE))
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 262, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(pnlWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(345, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(24, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(102, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSanPhamMouseClicked
        openSanPham();
    }//GEN-LAST:event_pnlSanPhamMouseClicked

    private void pnlKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseClicked
        openKhachHangUI();
    }//GEN-LAST:event_pnlKhachHangMouseClicked

    private void pnlKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlKhachHangMouseEntered

    private void pnlNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNhanVienMouseClicked
        openNhanVien();
    }//GEN-LAST:event_pnlNhanVienMouseClicked

    private void pnlKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhuyenMaiMouseClicked
        openKhuyenmai();
    }//GEN-LAST:event_pnlKhuyenMaiMouseClicked

    private void pnlHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonMouseClicked
        openHoaDon_Main();
    }//GEN-LAST:event_pnlHoaDonMouseClicked

    private void pnlDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDangXuatMouseClicked
        openDangXuat();
    }//GEN-LAST:event_pnlDangXuatMouseClicked

    private void pnlThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseClicked
        openThongKeUI();
    }//GEN-LAST:event_pnlThongKeMouseClicked

    private void tblHoaDonThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonThongKeMouseClicked
        int vt = -1;
        vt = tblHoaDonThongKe.getSelectedRow();
        HoaDon hd = listHD.get(vt);
        listHDCT = serviceHDCT.getAllHDCT2(hd.getMaHoaDon());
        fillToTableHDCT(listHDCT);
    }//GEN-LAST:event_tblHoaDonThongKeMouseClicked

    private void cboSapXepHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSapXepHDActionPerformed
        dcsTimeBeginHD.setDate(null);
        dcsTimeOverHD.setDate(null);
        int x = cboSapXepHD.getSelectedIndex();
        int y = cboTrangThaiHD.getSelectedIndex();
        if (y == 0) {
            if (x == 1) {
                listHD = serviceThongKe.getAllHoaDonSapXepTheoThanhTien();
                fillToTableHD(listHD);
            } else if (x == 2) {
                listHD = serviceThongKe.getAllHoaDonSapXepTheoNgayTao();
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDon();
                fillToTableHD(listHD);
            }
        } else {
            if (x == 1) {
                listHD = serviceThongKe.getAllHoaDonTheo_Tien_TT(y - 1);
                fillToTableHD(listHD);
            } else if (x == 2) {
                listHD = serviceThongKe.getAllHoaDonTheo_NgayTao_TT(y - 1);
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDonTheoTrangThai(y - 1);
                fillToTableHD(listHD);
            }
        }
    }//GEN-LAST:event_cboSapXepHDActionPerformed

    private void cboTrangThaiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiHDActionPerformed
        dcsTimeBeginHD.setDate(null);
        dcsTimeOverHD.setDate(null);
        int x = cboSapXepHD.getSelectedIndex();
        int y = cboTrangThaiHD.getSelectedIndex();
        if (y == 0) {
            if (x == 1) {
                listHD = serviceThongKe.getAllHoaDonSapXepTheoThanhTien();
                fillToTableHD(listHD);
            } else if (x == 2) {
                listHD = serviceThongKe.getAllHoaDonSapXepTheoNgayTao();
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDon();
                fillToTableHD(listHD);
            }
        } else {
            if (x == 1) {
                listHD = serviceThongKe.getAllHoaDonTheo_Tien_TT(y - 1);
                fillToTableHD(listHD);
            } else if (x == 2) {
                listHD = serviceThongKe.getAllHoaDonTheo_NgayTao_TT(y - 1);
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDonTheoTrangThai(y - 1);
                fillToTableHD(listHD);
            }
        }
    }//GEN-LAST:event_cboTrangThaiHDActionPerformed

    private void btnHuyLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyLocActionPerformed
        listHD = serviceThongKe.getAllHoaDon();
        fillToTableHD(listHD);
        dcsTimeBeginHD.setDate(null);
        dcsTimeOverHD.setDate(null);
        cboSapXepHD.setSelectedIndex(0);
        cboTrangThaiHD.setSelectedIndex(0);
    }//GEN-LAST:event_btnHuyLocActionPerformed

    private void btnLocHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocHoaDonActionPerformed
        Date TimeBeginn = dcsTimeBeginHD.getDate();
        Date TimeEndd = dcsTimeOverHD.getDate();
        Date currentDate = new Date();
        if (TimeBeginn == null) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được để trống");
            return;
        }
        if (TimeEndd == null) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được để trống");
            return;
        }
        if (TimeBeginn.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không lớn hơn hiện tại");
            return;
        }
        if (TimeEndd.after(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không lớn hơn hiện tại");
            return;
        }
        if (TimeBeginn.after(TimeEndd)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không lớn hơn ngày kết thúc");
            return;
        }
        int a = cboSapXepHD.getSelectedIndex();
        int b = cboTrangThaiHD.getSelectedIndex();
        if (a == 0 && b == 0) {
            listHD = serviceThongKe.getAllHoaDonTheoKhoangNgay(TimeBeginn, TimeEndd);
            fillToTableHD(listHD);
        } else if (a == 0 && b != 0) {
            listHD = serviceThongKe.getAllHoaDonTheo_Ngay_TT(TimeBeginn, TimeEndd, b - 1);
            fillToTableHD(listHD);
        } else if (a != 0 && b == 0) {
            if (a == 1) {
                listHD = serviceThongKe.getAllHoaDonTheo_Ngay_Tien(TimeBeginn, TimeEndd);
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDonTheo_Ngay_NgayTao(TimeBeginn, TimeEndd);
                fillToTableHD(listHD);
            }
        } else {
            if (a == 1) {
                listHD = serviceThongKe.getAllHoaDonTheo_Ngay_Tien_TT(TimeBeginn, TimeEndd, b - 1);
                fillToTableHD(listHD);
            } else {
                listHD = serviceThongKe.getAllHoaDonTheo_Ngay_NgayTao_TT(TimeBeginn, TimeEndd, b - 1);
                fillToTableHD(listHD);
            }
        }
    }//GEN-LAST:event_btnLocHoaDonActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn In hóa đơn?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int x = -1;
            x = tblHoaDonThongKe.getSelectedRow();
            if (x < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn hóa đơn muốn in");
                return;
            } else {
                generateAndPrintInvoice();
            }
        }
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void pnlHoaDonGocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonGocMouseClicked
        openHoaDon();
    }//GEN-LAST:event_pnlHoaDonGocMouseClicked

    private void generateAndPrintInvoice() {
        int x = tblHoaDonThongKe.getSelectedRow();
        HoaDon hd = listHD.get(x);
        Date currentDate = new Date();
        String maHoaDon = String.valueOf(hd.getMaHoaDon());
        String ngayTao = dateFormat.format(hd.getNgayTao());
        String ngayThanhToan = dateFormat.format(currentDate);
        int soSP = listHDCT.size();
        String dsSP[] = new String[soSP];
        for (int i = 0; i < soSP; i++) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            dsSP[i] = hdct.getTenSanPham() + "        SL: " + hdct.getSoLuong() + "      TT: " + currencyFormat.format(hdct.getThanhTien());
        }
        String tongTien = String.valueOf(hd.getTongTien());
        String giamGia = String.valueOf(hd.getGiamGia());
        String thanhTien = String.valueOf(hd.getThanhTien());
        String khachHang;
        String sdtKhachHang;
        if (hd.getMaKhachHang() == 0) {
            khachHang = "";
            sdtKhachHang = "";
        } else {
            System.out.println("maKH " + hd.getMaKhachHang());
            System.out.println("size " + listKH.size());
            int maKH = hd.getMaKhachHang();
            KhachHang kh = serviceKH.getAllKhachHangTheoMa(maKH).get(0);
            khachHang = kh.getHoTen();
            sdtKhachHang = kh.getSDT();
        }

        // Tạo hóa đơn PDF
        String pdfFileName = "HoaDon_" + maHoaDon + ".pdf";
        createInvoicePDF(pdfFileName, maHoaDon, ngayTao, ngayThanhToan,
                dsSP, tongTien, giamGia, thanhTien, khachHang, sdtKhachHang);

        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createInvoicePDF(String fileName, String maHoaDon, String ngayTao, String ngayThanhToan,
            String dsSP[], String tongTien, String giamGia, String thanhTien, String khachHang,
            String sdtKhachHang) {
        Document document = new Document();
        document.addLanguage(document.toString());
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Paragraph title = new Paragraph("HOA DON THANH TOAN");
            Paragraph light = new Paragraph("----------------------------------------------------------------------------------------------------------------");
            Paragraph Ngaytao = new Paragraph("NGAY TAO: " + ngayTao);
            Paragraph NgayThanhToan = new Paragraph("NGAY THANH TOAN: " + ngayThanhToan);
            Paragraph light1 = new Paragraph("---------------------------------------------------------------------------------------------------------------");
            Paragraph MaHoaDon = new Paragraph("MA HOA DON: " + maHoaDon);
            Paragraph Khachhang = new Paragraph(" KHACH HANG: " + khachHang);
            Paragraph SDTKhachHang = new Paragraph("SDT KHACH HANG: " + sdtKhachHang);
            Paragraph TitleSanPham = new Paragraph("SAN PHAM DUOC MUA");
            String TenSanPham = "";
            for (int i = 0; i < dsSP.length; i++) {
                TenSanPham = TenSanPham + dsSP[i] + "\n";
            }
            Paragraph SanPham = new Paragraph("SAN PHAM BAO GOM: \n" + TenSanPham);
            Paragraph light3 = new Paragraph("==============================");
            Paragraph tongTien3 = new Paragraph("TONG TIEN:  " + tongTien);
            Paragraph giamgia = new Paragraph("GIAM GIA:  " + giamGia);
            Paragraph ThanhTien = new Paragraph("THANH TOAN:  " + thanhTien);
            document.add(title);
            document.add(light);
            document.add(Ngaytao);
            document.add(NgayThanhToan);
            document.add(light1);
            document.add(MaHoaDon);
            document.add(Khachhang);
            document.add(SDTKhachHang);
            document.add(TitleSanPham);
            document.add(SanPham);
            document.add(light3);
            document.add(tongTien3);
            document.add(giamgia);
            document.add(ThanhTien);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HoaDonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoaDonUI().setVisible(true);
            }
        });
    }

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        pnlWebcam.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 140));

        executor.execute(this);
    }

    private void stopWbcam() {
        //executor.shutdown();
        webcam.close();
        dispose();
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result != null) {
                String s = result.getText();
                JOptionPane.showMessageDialog(this, s);
                try {
                    int maHD = Integer.parseInt(s);
                    int maHDDau = listHD.get(0).getMaHoaDon();
//                    int vt = maHDDau - maHD;
//                    tblHoaDonThongKe.setRowSelectionInterval(vt, vt);
                    listHDCT = serviceHDCT.getAllHDCT2(maHD);
                    fillToTableHDCT(listHDCT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChinhSuaHoaDon;
    private javax.swing.JButton btnHuyLoc;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLocHoaDon;
    private javax.swing.JComboBox<String> cboSapXepHD;
    private javax.swing.JComboBox<String> cboTrangThaiHD;
    private com.toedter.calendar.JDateChooser dcsTimeBeginHD;
    private com.toedter.calendar.JDateChooser dcsTimeOverHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNhanVienTheoDN;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlHoaDonGoc;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhuyenMai;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JPanel pnlWebcam;
    private javax.swing.JTable tblHDCTThongKe;
    private javax.swing.JTable tblHoaDonThongKe;
    // End of variables declaration//GEN-END:variables
}
