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
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.KhachHang;
import model.KhuyenMai;
import model.NhanVien;
import model.SanPham;
import service.HoaDonChiTietService;
import service.HoaDonService;
import service.KhachHangService;
import service.KhuyenMaiService;
import service.NhanVienService;
import service.SanPhamService;

/**
 *
 * @author ledin
 */
public class MainFrame extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    SanPhamService serviceSanPham = new SanPhamService();
    HoaDonChiTietService serviceHDCT = new HoaDonChiTietService();
    HoaDonService serviceHD = new HoaDonService();
    KhuyenMaiService serviceKhuyenMai = new KhuyenMaiService();
    KhachHangService serviceKH = new KhachHangService();
    NhanVienService serviceNV = new NhanVienService();

    List<SanPham> listSP = new ArrayList<>();
    List<HoaDon> listhd = new ArrayList<>();
    List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    List<KhuyenMai> listKhuyenMai = new ArrayList<>();
    List<KhachHang> listKH = new ArrayList<>();
    List<NhanVien> listNV = new ArrayList<>();
    DefaultTableModel tblModel = new DefaultTableModel();
    int vthd = -1;

    DecimalFormat currencyFormat = new DecimalFormat("#,##0");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        listSP = serviceSanPham.getAllSanPham();
        fillToTableSanPham(listSP);
        listKhuyenMai = serviceKhuyenMai.getAllKhuyenMaiByTrangThai(1);
        initComboboxKhuyenMai(listKhuyenMai);
        listNV = serviceNV.getAllNhanVien();
        initComboboxNhanVien(listNV);
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

    public void openHoaDon() {
        stopWbcam();
        dispose();
        new HoaDonUI().setVisible(true);
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

    public void initComboboxKhuyenMai(List<KhuyenMai> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        cboModel.addElement("Không dùng");
        for (KhuyenMai km : list) {
            cboModel.addElement(km.getMaKhuyenMai());
        }
        cboMaKhuyenMai.setModel(cboModel);
    }

    public void initComboboxNhanVien(List<NhanVien> list) {
        DefaultComboBoxModel cboModel3 = new DefaultComboBoxModel();
        for (NhanVien nv : list) {
            cboModel3.addElement(nv.getMaNV());
        }
        cboNhanVien.setModel(cboModel3);
    }

    public void fillToTableSanPham(List<SanPham> list) {
        String Cols[] = {"Tên Sản Phẩm", "Thương Hiệu", "Màu Sắc", "Chất Liệu", "Kiểu Dáng", "Size", "Số Lượng", "Đơn Giá"};
        tblModel.setColumnIdentifiers(Cols);
        tblModel.setRowCount(0);
        for (SanPham sp : list) {
            String donGia = currencyFormat.format(sp.getDonGia());
            tblModel.addRow(new Object[]{sp.getTenSanPham(), sp.getThuongHieu(), sp.getMauSac(),
                sp.getChatLieu(), sp.getKieuDang(), sp.getSize(),
                sp.getSoLuong(), donGia});
        }
        tblSanPhamHD.setModel(tblModel);
        //tblSanPhamHD.setEnabled(false);
    }

    public void fillToTableHoaDon(List<HoaDon> list) {
        DefaultTableModel tblModel2 = new DefaultTableModel();
        String Cols[] = {"Mã HD", "Mã NV", "Mã KH", "Trạng Thái", "Ngày Tạo"};
        tblModel2.setColumnIdentifiers(Cols);
        tblModel2.setRowCount(0);
        for (HoaDon hd : list) {
            String tt = "";
            if (hd.getTrangThai() == 0) {
                tt = "Chưa Thanh Toán";
            }
            tblModel2.addRow(new Object[]{hd.getMaHoaDon(), hd.getMaNhanVien(),
                hd.getMaKhachHang(), tt, hd.getNgayTao()});
        }
        tblHoaDon.setModel(tblModel2);
        tblHoaDon.setRowSelectionInterval(0, 0);
    }

    public void fillHDCTOver() {
        DefaultTableModel tblModel1 = new DefaultTableModel();
        String Cols2[] = {"STT", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tblModel1.setColumnIdentifiers(Cols2);
        tblModel1.setRowCount(0);
        tblGioHang.setModel(tblModel1);
    }

    public void fillToTableHDCT(List<HoaDonChiTiet> list) {
        DefaultTableModel tblModel3 = new DefaultTableModel();
        String Cols[] = {"STT", "Mã SP", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tblModel3.setColumnIdentifiers(Cols);
        tblModel3.setRowCount(0);
        int i = 0;
        double tongTien = 0;
        for (HoaDonChiTiet hdct : list) {
            i++;
            String formatDonGia = currencyFormat.format(hdct.getDonGia());
            String formatThanhTien = currencyFormat.format(hdct.getThanhTien());
            tblModel3.addRow(new Object[]{i, hdct.getMaSPCT(), hdct.getTenSanPham(), hdct.getSoLuong(), formatDonGia, formatThanhTien});
            tongTien = tongTien + hdct.getThanhTien();
        }
        tblGioHang.setModel(tblModel3);
        lblTongTien.setText(currencyFormat.format(tongTien));
        lblTienGiam.setText("0");
        double TienGiam = 0;
        double thanhTien = tongTien - TienGiam;
        lblThanhTien.setText(currencyFormat.format(thanhTien));
        serviceHD.UpdateHoaDon(tongTien, TienGiam, tongTien - TienGiam, Integer.parseInt(lblMaHoaDon.getText()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelHoaDon = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jpl125 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoaSanPham = new javax.swing.JButton();
        btnXoaTat = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamHD = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        btnHuyTK = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblTienGiam = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboHTTT = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnThanhToan = new javax.swing.JButton();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        lblMaHoaDon = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cboMaKhuyenMai = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboNhanVien = new javax.swing.JComboBox<>();
        pnlWebcam = new javax.swing.JPanel();
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
        pnlDangXuat = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlThongKe = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pnlHoaDonGoc = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panelHoaDon.setBackground(new java.awt.Color(204, 204, 204));
        panelHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 4));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jpl125.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));
        jpl125.setName(""); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HD", "Mã NV", "Tên KH", "Trạng Thái", "Ngày Tạo"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jpl125Layout = new javax.swing.GroupLayout(jpl125);
        jpl125.setLayout(jpl125Layout);
        jpl125Layout.setHorizontalGroup(
            jpl125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpl125Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpl125Layout.setVerticalGroup(
            jpl125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpl125Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ Hàng"));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành Tiền", "Trạng Thái"
            }
        ));
        jScrollPane5.setViewportView(tblGioHang);

        btnXoaSanPham.setText("Xóa Sản Phẩm");
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnXoaTat.setText("Xóa tất cả");
        btnXoaTat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128)
                .addComponent(btnXoaTat, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaSanPham)
                    .addComponent(btnXoaTat)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        tblSanPhamHD.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPhamHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamHD);

        btnTimKiem.setText("Tìm Kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnHuyTK.setText("Hủy");
        btnHuyTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyTKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuyTK)
                .addGap(67, 67, 67))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyTK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Tạo Hóa Đơn"));

        jLabel20.setText("SDT");

        txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoDienThoaiActionPerformed(evt);
            }
        });

        jLabel23.setText("Tổng Tiền");

        jLabel24.setText("Tiền Giảm");

        jLabel25.setText("Thành Tiền");

        lblTongTien.setText("0");

        lblTienGiam.setText("0");

        lblThanhTien.setText("0");

        jLabel11.setText("HT ThanhToán");

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn phương thức--", "Chuyển khoản", "Tiền Mặt" }));

        jLabel12.setText("Tiền Khách Đưa");

        jLabel14.setText("Tiền Thừa");

        lblTienThua.setText("0");

        btnThanhToan.setBackground(new java.awt.Color(51, 153, 255));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setText("Tạo Hóa Đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy ");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        jLabel26.setText("Tên KH");

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTenKH.setEnabled(false);

        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });

        jLabel29.setText("Mã Hóa Đơn");

        lblMaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaHoaDon.setText("jLabel30");

        jLabel15.setText("Mã KM");

        cboMaKhuyenMai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Không dùng", "KM001", "KM002", "KM003" }));
        cboMaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaKhuyenMaiActionPerformed(evt);
            }
        });

        jLabel9.setText("MaNV");

        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(12, 12, 12)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(cboMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 69, Short.MAX_VALUE))))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel26)
                                .addComponent(jLabel20)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(txtSoDienThoai)
                            .addComponent(cboNhanVien, 0, 174, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(18, 18, 18)
                                .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(37, 37, 37))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(lblTienThua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(lblTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(lblMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cboMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblTienGiam))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lblThanhTien))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblTienThua))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jpl125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jpl125, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(pnlWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelHoaDonLayout = new javax.swing.GroupLayout(panelHoaDon);
        panelHoaDon.setLayout(panelHoaDonLayout);
        panelHoaDonLayout.setHorizontalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        panelHoaDonLayout.setVerticalGroup(
            panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(panelHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelHoaDonLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(10, Short.MAX_VALUE)))
        );

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
                .addContainerGap(47, Short.MAX_VALUE))
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
        jLabel17.setText("   Khuyến Mại");

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

        pnlHoaDon.setBackground(new java.awt.Color(255, 153, 153));
        pnlHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHoaDonMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Full basket.png"))); // NOI18N
        jLabel16.setText("    Bán hàng");

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
                .addGap(41, 41, 41)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDangXuatLayout.setVerticalGroup(
            pnlDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDangXuatLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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

        pnlHoaDonGoc.setBackground(new java.awt.Color(0, 102, 102));
        pnlHoaDonGoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlHoaDonGocMouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Price list.png"))); // NOI18N
        jLabel28.setText("    Hóa đơn");

        javax.swing.GroupLayout pnlHoaDonGocLayout = new javax.swing.GroupLayout(pnlHoaDonGoc);
        pnlHoaDonGoc.setLayout(pnlHoaDonGocLayout);
        pnlHoaDonGocLayout.setHorizontalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonGocLayout.setVerticalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(67, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1271, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                    .addComponent(panelHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseClicked
        openThongKeUI();
    }//GEN-LAST:event_pnlThongKeMouseClicked

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        vthd = tblHoaDon.getSelectedRow();
        HoaDon hd = listhd.get(vthd);
        int maHoaDon = hd.getMaHoaDon();
        lblMaHoaDon.setText(String.valueOf(maHoaDon));
        int MaKH = hd.getMaKhachHang();
        if (MaKH > 0) {
            listKH = serviceKH.getAllKhachHangTheoMa(MaKH);
            KhachHang kh = listKH.get(0);
            txtSoDienThoai.setText(kh.getSDT());
            txtTenKH.setText(kh.getHoTen());
        } else {
            txtSoDienThoai.setText("");
            txtTenKH.setText("");
        }

        listHDCT = serviceHDCT.getAllHDCT2(maHoaDon);
        fillToTableHDCT(listHDCT);
//        khuyenMaiTuDongMousePerformed(evt);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblSanPhamHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamHDMouseClicked
        int selectedRow = -1;
        selectedRow = tblSanPhamHD.getSelectedRow();
        String x = JOptionPane.showInputDialog("Nhập số lượng:");
        if (x != null) {
            try {
                int xx = Integer.parseInt(x);
                SanPham sp = listSP.get(selectedRow);
                String MaSPCT = sp.getMaSPCT();
                int SoLuongThuc = sp.getSoLuong();
                if (xx > SoLuongThuc) {
                    JOptionPane.showMessageDialog(this, "Số lượng mua lớn hơn số lượng còn lại của sản phẩm");
                    return;
                }
                serviceSanPham.UpdateSoLuong(xx, MaSPCT);
                vthd = tblHoaDon.getSelectedRow();
                HoaDon hd = listhd.get(vthd);
                int dem = 0;
                for (HoaDonChiTiet hdct11 : listHDCT) {
                    if (hdct11.getMaSPCT().equalsIgnoreCase(MaSPCT)) {
                        dem++;
                    }
                }
                if (dem == 0) {
                    serviceHDCT.InsertHDCT(hd.getMaHoaDon(), MaSPCT, xx);
                } else {
                    serviceHDCT.UpdateHDCT_SoLuong(hd.getMaHoaDon(), MaSPCT, xx);
                }
                listHDCT = serviceHDCT.getAllHDCT2(hd.getMaHoaDon());
                fillToTableHDCT(listHDCT);
//                khuyenMaiTuDongMousePerformed(evt);
                listSP = serviceSanPham.getAllSanPham();
                fillToTableSanPham(listSP);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là số");
                return;
            }
        }
    }//GEN-LAST:event_tblSanPhamHDMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        try {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn Thanh Toán không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                vthd = tblHoaDon.getSelectedRow();
                HoaDon hd = listhd.get(vthd);
                String MaKhuyenMai = (String) cboMaKhuyenMai.getSelectedItem();
                if (cboMaKhuyenMai.getSelectedIndex() == 0) {
                    MaKhuyenMai = null;
                }
                double tongTien = currencyFormat.parse(lblTongTien.getText()).doubleValue();
                double giamGia = currencyFormat.parse(lblTienGiam.getText()).doubleValue();
                double thanhTien = tongTien - giamGia;
                int vtHTTT = cboHTTT.getSelectedIndex();
                if (vtHTTT == 0) {
                    JOptionPane.showMessageDialog(this, "Ban phai chon HTTT");
                    return;
                }
                String maHTTT;
                if (vtHTTT == 1) {
                    maHTTT = "HTTT001";
                } else if (vtHTTT == 2) {
                    maHTTT = "HTTT002";
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn phải chọn hình thức thanh toán");
                    return;
                }
                hd.setTongTien(tongTien);
                hd.setMaKhuyenMai(MaKhuyenMai);
                hd.setGiamGia(giamGia);
                hd.setThanhTien(thanhTien);
                hd.setMaHTTT(maHTTT);
                if (cboMaKhuyenMai.getSelectedIndex() != 0) {
                    listKhuyenMai = serviceKhuyenMai.getAllKhuyenMaiByMa(MaKhuyenMai);
                    KhuyenMai km = listKhuyenMai.get(0);
                    serviceKhuyenMai.UpdateSoLuongKhuyenMai(km);
                }
                //In hóa đơn
                generateAndPrintInvoice();
                //serviceHD.UpdateTrangThai(1, hd.getMaHoaDon());
                serviceHD.UpdateHoaDonTT(hd);
                listhd = serviceHD.getDSHoaDonCho();
                fillToTableHoaDon(listhd);
                fillHDCTOver();
                txtTienKhachDua.setText("");
                lblThanhTien.setText("");
                lblTienGiam.setText("");
                lblTienThua.setText("");
                lblTongTien.setText("");
                lblMaHoaDon.setText("");
                cboMaKhuyenMai.setSelectedIndex(0);
                cboHTTT.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Bạn đã thanh toán hóa đơn thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void generateAndPrintInvoice() {
        int x = tblHoaDon.getSelectedRow();
        Date currentDate = new Date();
        String maHoaDon = lblMaHoaDon.getText();
        String ngayTao = dateFormat.format(listhd.get(x).getNgayTao());
        String ngayThanhToan = dateFormat.format(currentDate);
        int soSP = listHDCT.size();
        String dsSP[] = new String[soSP];
        for (int i = 0; i < soSP; i++) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            dsSP[i] = hdct.getTenSanPham() + "        SL: " + hdct.getSoLuong() + "      TT: " + currencyFormat.format(hdct.getThanhTien());
        }
        String tongTien = lblTongTien.getText();
        String giamGia = lblTienGiam.getText();
        String thanhTien = lblThanhTien.getText();
        String khachHang = txtTenKH.getText();
        String sdtKhachHang = txtSoDienThoai.getText();

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
//            title.setAlignment(Element.ALIGN_CENTER);
//            light.setAlignment(Element.ALIGN_CENTER);
//            Ngaytao.setAlignment(Element.ALIGN_CENTER);
//            light1.setAlignment(Element.ALIGN_CENTER);
//            NgaySua.setAlignment(Element.ALIGN_CENTER);
//            MaHoaDon.setAlignment(Element.ALIGN_CENTER);
//            Khachhang.setAlignment(Element.ALIGN_CENTER);
//            NhanVien.setAlignment(Element.ALIGN_CENTER);
//            TitleSanPham.setAlignment(Element.ALIGN_CENTER);
//            SanPham.setAlignment(Element.ALIGN_CENTER);
//            GiaTien.setAlignment(Element.ALIGN_CENTER);
//            sl.setAlignment(Element.ALIGN_CENTER);
//            light3.setAlignment(Element.ALIGN_CENTER);
//            ngaythanhtoan.setAlignment(Element.ALIGN_CENTER);
//            giamgia.setAlignment(Element.ALIGN_CENTER);
//            ThanhTien.setAlignment(Element.ALIGN_CENTER);
//            Hinhthuc.setAlignment(Element.ALIGN_CENTER);
//            TrangThai.setAlignment(Element.ALIGN_CENTER);
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


    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {

            if (txtSoDienThoai.getText().trim().equals("")) {
                System.out.println("SDT Null nhé!");
                HoaDon hd1 = new HoaDon();
                //hd1.setMaKhachHang(0);
                hd1.setMaNhanVien((String) cboNhanVien.getSelectedItem());
                serviceHD.InsertHoaDonKHnull(hd1);
                listhd = serviceHD.getDSHoaDonCho();
                fillToTableHoaDon(listhd);
                HoaDon hd = listhd.get(0);
                lblMaHoaDon.setText(String.valueOf(hd.getMaHoaDon()));
            } else {
                HoaDon hd1 = new HoaDon();
                int maKH = listKH.get(0).getMaKhachHang();
                hd1.setMaKhachHang(maKH);
                hd1.setMaNhanVien((String) cboNhanVien.getSelectedItem());
                serviceHD.InsertHoaDon(hd1);
                listhd = serviceHD.getDSHoaDonCho();
                fillToTableHoaDon(listhd);
                HoaDon hd = listhd.get(0);
                lblMaHoaDon.setText(String.valueOf(hd.getMaHoaDon()));
            }

        }
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        double s = -1;
        double thanhTien;
        try {
            s = currencyFormat.parse(txtTienKhachDua.getText()).doubleValue();
            thanhTien = currencyFormat.parse(lblThanhTien.getText()).doubleValue();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số");
            return;
        }
        if (s < thanhTien) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn Thành Tiền");
            return;
        }
        lblTienThua.setText(currencyFormat.format(s - thanhTien));
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn xác nhận Hủy Hóa Đơn chứ", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            vthd = tblHoaDon.getSelectedRow();
            HoaDon hd = listhd.get(vthd);
            serviceHD.UpdateTrangThai(2, hd.getMaHoaDon());
            listhd = serviceHD.getDSHoaDonCho();
            int MaHoaDon = hd.getMaHoaDon();
            listHDCT = serviceHDCT.getAllHDChiTietTheoMa(MaHoaDon);
            for (HoaDonChiTiet hdct : listHDCT) {
                String MaSPCT = hdct.getMaSPCT();
                int SoLuong = hdct.getSoLuong();
                serviceSanPham.UpdateSoLuongVe(SoLuong, MaSPCT);
            }
            fillToTableHoaDon(listhd);
            fillHDCTOver();
            listSP = serviceSanPham.getAllSanPham();
            fillToTableSanPham(listSP);
            txtTienKhachDua.setText("");
            lblThanhTien.setText("");
            lblTienGiam.setText("");
            lblTienThua.setText("");
            lblTongTien.setText("");
            lblMaHoaDon.setText("");
        }
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn xác nhận xóa sản phẩm này chứ", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblGioHang.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn sản phẩm muốn xóa");
                return;
            } else {
                HoaDonChiTiet hdct = listHDCT.get(vt);
                int MaHoaDon = Integer.parseInt(lblMaHoaDon.getText());
                String MaSPCT = hdct.getMaSPCT();
                int SoLuong = hdct.getSoLuong();
                System.out.println("MaHoaDon = " + MaHoaDon);
                System.out.println("MaSPCT = " + MaSPCT);
                serviceHDCT.DeleteHDCT(MaHoaDon, MaSPCT);
                serviceSanPham.UpdateSoLuongVe(SoLuong, MaSPCT);
                listHDCT = serviceHDCT.getAllHDCT2(MaHoaDon);
                fillToTableHDCT(listHDCT);
//                khuyenMaiDongTuDongActionPerformed(evt);
                listSP = serviceSanPham.getAllSanPham();
                fillToTableSanPham(listSP);
            }
        }
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnXoaTatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTatActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn xác nhận xóa tất cả sản phẩm chứ", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int MaHoaDon = Integer.parseInt(lblMaHoaDon.getText());
            listHDCT = serviceHDCT.getAllHDChiTietTheoMa(MaHoaDon);
            for (HoaDonChiTiet hdct : listHDCT) {
                String MaSPCT = hdct.getMaSPCT();
                int SoLuong = hdct.getSoLuong();
                serviceSanPham.UpdateSoLuongVe(SoLuong, MaSPCT);
            }
            serviceHDCT.DeleteAllHDCT(MaHoaDon);
            listHDCT = serviceHDCT.getAllHDCT2(MaHoaDon);
            fillToTableHDCT(listHDCT);
//            khuyenMaiDongTuDongActionPerformed(evt);
            listSP = serviceSanPham.getAllSanPham();
            fillToTableSanPham(listSP);
        }
    }//GEN-LAST:event_btnXoaTatActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String TenSanPham = txtTimKiem.getText();
        listSP = serviceSanPham.getSearchSanPham(TenSanPham);
        fillToTableSanPham(listSP);
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void pnlKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhuyenMaiMouseClicked
        openKhuyenmai();
    }//GEN-LAST:event_pnlKhuyenMaiMouseClicked

    private void pnlHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonMouseClicked
        openHoaDon_Main();
    }//GEN-LAST:event_pnlHoaDonMouseClicked

    /*
    public void khuyenMaiTuDong() {
        try {
            double tongTien = Double.parseDouble(lblTongTien.getText());
            double maxx1 = 0;
            int vtmaxx1 = -1;
            for (int i = 0; i < listKhuyenMai.size(); i++) {
                KhuyenMai km = listKhuyenMai.get(i);
                if (tongTien >= km.getSoTienMin()) {
                    double giamGia = 0;
                    if (km.getHinhThuc() == 1) {
                        giamGia = (tongTien * km.getMucGiamGia()) / 100;
                    } else {
                        giamGia = km.getMucGiamGia();
                    }
                    if (giamGia > maxx1) {
                        maxx1 = giamGia;
                        vtmaxx1 = i + 1;
                    }
                }
            }
            KhuyenMai km = listKhuyenMai.get(vtmaxx1);
            cboMaKhuyenMai.setSelectedItem(km.getMaKhuyenMai());
            lblTienGiam.setText(currencyFormat.format(maxx1));
            lblThanhTien.setText(currencyFormat.format(tongTien - maxx1));
        } catch (Exception e) {
        }
    }

    public void khuyenMaiTuDongMousePerformed(MouseEvent arg0) {
        try {
            double tongTien = Double.parseDouble(lblTongTien.getText());
            double maxx1 = 0;
            int vtmaxx1 = -1;
            for (int i = 0; i < listKhuyenMai.size(); i++) {
                KhuyenMai km = listKhuyenMai.get(i);
                if (tongTien >= km.getSoTienMin()) {
                    double giamGia = 0;
                    if (km.getHinhThuc() == 1) {
                        giamGia = (tongTien * km.getMucGiamGia()) / 100;
                    } else {
                        giamGia = km.getMucGiamGia();
                    }
                    if (giamGia > maxx1) {
                        maxx1 = giamGia;
                        vtmaxx1 = i + 1;
                    }
                }
            }
            KhuyenMai km = listKhuyenMai.get(vtmaxx1);
            cboMaKhuyenMai.setSelectedItem(km.getMaKhuyenMai());
            lblTienGiam.setText(currencyFormat.format(maxx1));
            lblThanhTien.setText(currencyFormat.format(tongTien - maxx1));
        } catch (Exception e) {
        }
    }

    public void khuyenMaiDongTuDongActionPerformed(ActionEvent arg0) {
        try {
            double tongTien = Double.parseDouble(lblTongTien.getText());
            double maxx1 = 0;
            int vtmaxx1 = -1;
            for (int i = 0; i < listKhuyenMai.size(); i++) {
                KhuyenMai km = listKhuyenMai.get(i);
                if (tongTien >= km.getSoTienMin()) {
                    double giamGia = 0;
                    if (km.getHinhThuc() == 1) {
                        giamGia = (tongTien * km.getMucGiamGia()) / 100;
                    } else {
                        giamGia = km.getMucGiamGia();
                    }
                    if (giamGia > maxx1) {
                        maxx1 = giamGia;
                        vtmaxx1 = i + 1;
                    }
                }
            }
            KhuyenMai km = listKhuyenMai.get(vtmaxx1);
            cboMaKhuyenMai.setSelectedItem(km.getMaKhuyenMai());
            lblTienGiam.setText(currencyFormat.format(maxx1));
            lblThanhTien.setText(currencyFormat.format(tongTien - maxx1));
        } catch (Exception e) {
        }
    }
    */
    private void cboMaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaKhuyenMaiActionPerformed
        try {
            if (cboMaKhuyenMai.getSelectedIndex() == 0) {
                lblTienGiam.setText("0");
                lblThanhTien.setText(lblTongTien.getText());
                return;
            }
            String MaKhuyenMai = (String) cboMaKhuyenMai.getSelectedItem();
            System.out.println("MaKM: " + MaKhuyenMai);
            double tongTien = currencyFormat.parse(lblTongTien.getText()).doubleValue();
            double giamGia = 0;
            List<KhuyenMai> listKM1 = new ArrayList<>();
            listKM1 = serviceKhuyenMai.getAllKhuyenMaiByMa(MaKhuyenMai);
            KhuyenMai km = listKM1.get(0);
            System.out.println("HinhThucKM: " + km.getHinhThuc());
            if (tongTien >= km.getSoTienMin()) {
                if (km.getHinhThuc() == 0) {
                    giamGia = km.getMucGiamGia();
                } else {
                    giamGia = (tongTien * km.getMucGiamGia()) / 100.0;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không thể áp dụng được mã này "
                        + "vì tổng tiền nhỏ hơn số tiền tối thiểu của khuyến mại");
                cboMaKhuyenMai.setSelectedIndex(0);
            }
            lblTienGiam.setText(currencyFormat.format(giamGia));
            lblThanhTien.setText(currencyFormat.format(tongTien - giamGia));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cboMaKhuyenMaiActionPerformed

    private void pnlKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseClicked
        openKhachHangUI();
    }//GEN-LAST:event_pnlKhachHangMouseClicked

    private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoDienThoaiActionPerformed
        String SoDT = txtSoDienThoai.getText();
        if (SoDT.trim().equals("")) {
            txtTenKH.setText("");
            return;
        }
        listKH = serviceKH.getAllKhachHangTheoSDT(SoDT);
        KhachHang kh = listKH.get(0);
        txtTenKH.setText(kh.getHoTen());
    }//GEN-LAST:event_txtSoDienThoaiActionPerformed

    private void pnlSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSanPhamMouseClicked
        openSanPham();
    }//GEN-LAST:event_pnlSanPhamMouseClicked

    private void pnlKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlKhachHangMouseEntered

    private void pnlNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNhanVienMouseClicked
        openNhanVien();
    }//GEN-LAST:event_pnlNhanVienMouseClicked

    private void pnlDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDangXuatMouseClicked
        openDangXuat();
    }//GEN-LAST:event_pnlDangXuatMouseClicked

    private void btnHuyTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyTKActionPerformed
        listSP = serviceSanPham.getAllSanPham();
        fillToTableSanPham(listSP);
    }//GEN-LAST:event_btnHuyTKActionPerformed

    private void cboNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVienActionPerformed

    }//GEN-LAST:event_cboNhanVienActionPerformed

    private void pnlHoaDonGocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonGocMouseClicked
        openHoaDon();
    }//GEN-LAST:event_pnlHoaDonGocMouseClicked

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
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
                String xx = JOptionPane.showInputDialog("Nhập số lượng:");
                if (xx != null) {
                    try {
                        int soLuong = Integer.parseInt(xx);
                        String MaSPCT = result.getText();
                        SanPham sp01 = serviceSanPham.getSanPhamByMa(MaSPCT);
                        int SoLuongThuc = sp01.getSoLuong();
                        if (soLuong > SoLuongThuc) {
                            JOptionPane.showMessageDialog(this, "Số lượng mua lớn hơn số lượng còn lại của sản phẩm");
                            return;
                        }
                        serviceSanPham.UpdateSoLuong(soLuong, MaSPCT);
                        vthd = tblHoaDon.getSelectedRow();
                        HoaDon hd = listhd.get(vthd);
                        int dem = 0;
                        for (HoaDonChiTiet hdct11 : listHDCT) {
                            if (hdct11.getMaSPCT().equalsIgnoreCase(MaSPCT)) {
                                dem++;
                            }
                        }
                        if (dem == 0) {
                            serviceHDCT.InsertHDCT(hd.getMaHoaDon(), MaSPCT, soLuong);
                        } else {
                            serviceHDCT.UpdateHDCT_SoLuong(hd.getMaHoaDon(), MaSPCT, soLuong);
                        }
                        listHDCT = serviceHDCT.getAllHDCT2(hd.getMaHoaDon());
                        fillToTableHDCT(listHDCT);
//                        khuyenMaiTuDong();
                        listSP = serviceSanPham.getAllSanPham();
                        fillToTableSanPham(listSP);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Số lượng phải là số");
                        return;
                    }
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
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnHuyTK;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXoaTat;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboMaKhuyenMai;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel jpl125;
    private javax.swing.JLabel lblMaHoaDon;
    private javax.swing.JLabel lblNhanVienTheoDN;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTienGiam;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel panelHoaDon;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlHoaDonGoc;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhuyenMai;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JPanel pnlWebcam;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPhamHD;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
