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
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import model.KhuyenMai;
import model.NhanVien;
import model.SanPham;
import service.NhanVienService;

/**
 *
 * @author ledin
 */
public class NhanVienUI extends javax.swing.JFrame implements Runnable, ThreadFactory {

    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    List<NhanVien> listNV = new ArrayList<>();
    NhanVienService serviceNV = new NhanVienService();
    DecimalFormat currencyFormat = new DecimalFormat("#,##0");

    public NhanVienUI() {
        initComponents();
        setLocationRelativeTo(null);
        listNV = serviceNV.getAllNhanVien();
        fillToTable(listNV);
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

    public void fillToTable(List<NhanVien> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã NV", "Mật khẩu", "Họ tên", "Giới tính", "Địa chỉ", "SDT",
            "CCCD", "Email", "Ngày vào làm", "Ngày nghỉ", "Ngày tạo", "Ngày sửa", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (NhanVien nv : list) {
            String gt = "";
            if (nv.getGioiTinh() == 0) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            String tt = "";
            if (nv.getTrangThai() == 0) {
                tt = "Đã nghỉ";
            } else {
                tt = "Đang làm việc";
            }
            tblModel.addRow(new Object[]{nv.getMaNV(), nv.getMatKhau(), nv.getHoTen(),
                gt, nv.getDiaChi(), nv.getSDT(), nv.getCCCD(), nv.getEmail(), nv.getNgayVaoLam(), nv.getNgayNghi(),
                nv.getNgayTao(), nv.getNgaySua(), tt});
        }
        tblNhanVien.setModel(tblModel);
    }

    public void fillToTable22(List<NhanVien> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã NV", "Mật khẩu", "Họ tên", "Giới tính", "Địa chỉ", "SDT",
            "CCCD", "Email", "Ngày vào làm", "Ngày nghỉ", "Ngày tạo", "Ngày sửa",
            "Trạng thái", "Số hóa đơn", "DoanhThu"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (NhanVien nv : list) {
            String gt = "";
            if (nv.getGioiTinh() == 0) {
                gt = "Nữ";
            } else {
                gt = "Nam";
            }
            String tt = "";
            if (nv.getTrangThai() == 0) {
                tt = "Đã nghỉ";
            } else {
                tt = "Đang làm việc";
            }
            String doanhThu = currencyFormat.format(nv.getDoanhThu());
            tblModel.addRow(new Object[]{nv.getMaNV(), nv.getMatKhau(), nv.getHoTen(),
                gt, nv.getDiaChi(), nv.getSDT(), nv.getCCCD(), nv.getEmail(), nv.getNgayVaoLam(), nv.getNgayNghi(),
                nv.getNgayTao(), nv.getNgaySua(), tt, nv.getSoHoaDon(), doanhThu});
        }
        tblNhanVien.setModel(tblModel);
    }

    public void fillToForm(int index) {
        NhanVien nv = listNV.get(index);
        txtMaNV.setText(nv.getMaNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtHoTen.setText(nv.getHoTen());
        txtSoDienThoai.setText(nv.getSDT());
        if (nv.getGioiTinh() == 0) {
            rdoNu.setSelected(true);
        } else {
            rdoNam.setSelected(true);
        }
        txtEmail.setText(nv.getEmail());
        txtCCCD.setText(nv.getCCCD());
        txtDiaChi.setText(nv.getDiaChi());
        txtMaNV.setEnabled(false);
        txtCCCD.setEnabled(false);
        txtMatKhau.setEnabled(false);
    }

    public void clear() {
        txtMaNV.setText("");
        txtMatKhau.setText("");
        txtHoTen.setText("");
        txtSoDienThoai.setText("");
        buttonGroup1.clearSelection();
        txtCCCD.setText("");
        txtDiaChi.setText("");
        txtMaNV.setEnabled(true);
        txtCCCD.setEnabled(true);
        txtMatKhau.setEnabled(true);
        txtEmail.setText("");
    }

    private boolean check_Email() {

        String checkEmail = txtEmail.getText(); // Lấy email từ txtEmailTo

        if (checkEmail.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Email đang trống!");
            return false;
        }

        // Biểu thức chính quy để kiểm tra định dạng email
        String checkFormEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Kiểm tra định dạng email
        if (!checkEmail.matches(checkFormEmail)) {
            JOptionPane.showMessageDialog(this, "Định dạng email không hợp lệ!");
            return false;
        }

        return true; // Trả về false nếu không tìm thấy
    }

    public NhanVien getNhanVien() {
        if (txtMaNV.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống mã NV");
            return null;
        }
        //Kiểm tra xem mã khuyến mại đã tồn tại chưa
        int check = 0;
        if (txtMaNV.isEnabled()) {
            for (NhanVien nv : listNV) {
                if (nv.getMaNV().equalsIgnoreCase(txtMaNV.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã NV đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtMatKhau.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được bỏ trống");
            return null;
        }
        if (txtHoTen.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Họ tên không được bỏ trống");
            return null;
        }
        if (txtSoDienThoai.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được bỏ trống");
            return null;
        }
        if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn giới tính");
            return null;
        }
        if (txtCCCD.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "CCCD không được bỏ trống");
            return null;
        }
        if (txtDiaChi.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được bỏ trống");
            return null;
        }
        if (!check_Email()) {
            return null;
        }
        NhanVien nv = new NhanVien();
        nv.setEmail(txtEmail.getText());
        nv.setMaNV(txtMaNV.getText());
        nv.setMatKhau(txtMatKhau.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setSDT(txtSoDienThoai.getText());
        if (rdoNu.isSelected()) {
            nv.setGioiTinh(0);
        } else {
            nv.setGioiTinh(1);
        }
        nv.setCCCD(txtCCCD.getText());
        nv.setDiaChi(txtDiaChi.getText());
        return nv;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
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
        jLabel30 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        cboTimKiemTheo = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        cboSapXep = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel23 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnThemNhanVien = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnNghiLam = new javax.swing.JButton();
        pnlWebcam = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/profile.png"))); // NOI18N
        jLabel9.setText("Hello Admin ");

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
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jLabel9)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(23, 23, 23))
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

        pnlNhanVien.setBackground(new java.awt.Color(255, 153, 153));
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

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Price list.png"))); // NOI18N
        jLabel30.setText("     Hóa đơn");

        javax.swing.GroupLayout pnlHoaDonGocLayout = new javax.swing.GroupLayout(pnlHoaDonGoc);
        pnlHoaDonGoc.setLayout(pnlHoaDonGocLayout);
        pnlHoaDonGocLayout.setHorizontalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonGocLayout.setVerticalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 3));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách nhân viên"));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        cboTimKiemTheo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Họ tên", "SDT", "CCCD" }));
        cboTimKiemTheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimKiemTheoActionPerformed(evt);
            }
        });

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel25.setText("Tìm kiếm theo");

        jLabel26.setText("Trạng Thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã nghỉ", "Đang làm việc" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-------------", "Số Hóa Đơn", "Doanh Thu" }));
        cboSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSapXepActionPerformed(evt);
            }
        });

        jLabel28.setText("Sắp xếp theo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addContainerGap(926, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cboTimKiemTheo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(219, 219, 219)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTimKiemTheo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Form nhân viên"));

        jLabel11.setText("Mã nhân viên");

        jLabel12.setText("Mật khẩu");

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel14.setText("Họ tên");

        jLabel15.setText("Số điện thoại");

        jLabel20.setText("Giới tính");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel23.setText("CCCD");

        txtCCCD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel24.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        btnThemNhanVien.setText("Thêm nhân viên");
        btnThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNhanVienActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật nhân viên");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnNghiLam.setText("Nghỉ Làm");
        btnNghiLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNghiLamActionPerformed(evt);
            }
        });

        pnlWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnClear.setText("CLEAR FORM");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel29.setText("Email");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14)
                    .addComponent(jLabel20)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(27, 27, 27)
                                .addComponent(rdoNu))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(txtHoTen))
                        .addGap(117, 117, 117)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel23))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCCCD, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                            .addComponent(txtEmail))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addComponent(pnlWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnThemNhanVien)
                        .addGap(75, 75, 75)
                        .addComponent(btnCapNhat)
                        .addGap(100, 100, 100)
                        .addComponent(btnNghiLam)
                        .addGap(65, 65, 65)
                        .addComponent(btnClear)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))))
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSoDienThoai)
                                .addComponent(jLabel15)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23)
                            .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlWebcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNhanVien)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCapNhat)
                        .addComponent(btnNghiLam)
                        .addComponent(btnClear)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(265, 265, 265)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1295, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1037, 1037, 1037)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseClicked
        openKhachHangUI();
    }//GEN-LAST:event_pnlKhachHangMouseClicked

    private void pnlKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhuyenMaiMouseClicked
        openKhuyenmai();
    }//GEN-LAST:event_pnlKhuyenMaiMouseClicked

    private void pnlHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonMouseClicked
        openHoaDon_Main();
    }//GEN-LAST:event_pnlHoaDonMouseClicked

    private void pnlThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseClicked
        openThongKeUI();
    }//GEN-LAST:event_pnlThongKeMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int vt = tblNhanVien.getSelectedRow();
        fillToForm(vt);
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNhanVienActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            NhanVien nv = getNhanVien();
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceNV.InsertNhanVien(nv));
                listNV = serviceNV.getAllNhanVien();
                fillToTable(listNV);
            }
        }
    }//GEN-LAST:event_btnThemNhanVienActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật nhân viên không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblNhanVien.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng muốn sửa");
                return;
            } else {
                NhanVien nv = getNhanVien();
                if (nv == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceNV.UpdateNhanVien(nv));
                    listNV = serviceNV.getAllNhanVien();
                    fillToTable(listNV);
                }
            }
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnNghiLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNghiLamActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật nghỉ làm nhân viên không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblNhanVien.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn nhân viên muốn nghỉ");
                return;
            } else {
                String maNV = txtMaNV.getText();
                JOptionPane.showMessageDialog(this, serviceNV.UpdateNVNghiViec(maNV));
                listNV = serviceNV.getAllNhanVien();
                fillToTable(listNV);
            }
        }
    }//GEN-LAST:event_btnNghiLamActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        int a = cboTimKiemTheo.getSelectedIndex();
        String timKiem = txtTimKiem.getText();
        if (a == 0) {
            listNV = serviceNV.getAllNhanVienTKTheoTen(timKiem);
            fillToTable(listNV);
        } else if (a == 1) {
            listNV = serviceNV.getAllNhanVienTKTheoSDT(timKiem);
            fillToTable(listNV);
        } else {
            listNV = serviceNV.getAllNhanVienTKTheoCCCD(timKiem);
            fillToTable(listNV);
        }
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void cboTimKiemTheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimKiemTheoActionPerformed
        int a = cboTimKiemTheo.getSelectedIndex();
        String timKiem = txtTimKiem.getText();
        if (a == 0) {
            listNV = serviceNV.getAllNhanVienTKTheoTen(timKiem);
            fillToTable(listNV);
        } else if (a == 1) {
            listNV = serviceNV.getAllNhanVienTKTheoSDT(timKiem);
            fillToTable(listNV);
        } else {
            listNV = serviceNV.getAllNhanVienTKTheoCCCD(timKiem);
            fillToTable(listNV);
        }
    }//GEN-LAST:event_cboTimKiemTheoActionPerformed

    private void cboSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSapXepActionPerformed
        int a = cboSapXep.getSelectedIndex();
        int b = cboTrangThai.getSelectedIndex();
        if (b == 0) {
            if (a == 0) {
                listNV = serviceNV.getAllNhanVien();
                fillToTable(listNV);
            } else if (a == 1) {
                listNV = serviceNV.getAllNhanVienSXTheo_SoHoaDon();
                fillToTable22(listNV);
            } else {
                listNV = serviceNV.getAllNhanVienSXTheo_DoanhThu();
                fillToTable22(listNV);
            }
        } else {
            if (a == 0) {
                listNV = serviceNV.getAllNhanVienTheoTrangThai(b - 1);
                fillToTable(listNV);
            } else if (a == 1) {
                listNV = serviceNV.getAllNhanVienSXTheo_SoHoDon_TT(b - 1);
                fillToTable22(listNV);
            } else {
                listNV = serviceNV.getAllNhanVienSXTheo_DoanhThu_TT(b - 1);
                fillToTable22(listNV);
            }
        }
    }//GEN-LAST:event_cboSapXepActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        int a = cboSapXep.getSelectedIndex();
        int b = cboTrangThai.getSelectedIndex();
        if (b == 0) {
            if (a == 0) {
                listNV = serviceNV.getAllNhanVien();
                fillToTable(listNV);
            } else if (a == 1) {
                listNV = serviceNV.getAllNhanVienSXTheo_SoHoaDon();
                fillToTable22(listNV);
            } else {
                listNV = serviceNV.getAllNhanVienSXTheo_DoanhThu();
                fillToTable22(listNV);
            }
        } else {
            if (a == 0) {
                listNV = serviceNV.getAllNhanVienTheoTrangThai(b - 1);
                fillToTable(listNV);
            } else if (a == 1) {
                listNV = serviceNV.getAllNhanVienSXTheo_SoHoDon_TT(b - 1);
                fillToTable22(listNV);
            } else {
                listNV = serviceNV.getAllNhanVienSXTheo_DoanhThu_TT(b - 1);
                fillToTable22(listNV);
            }
        }
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void pnlSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSanPhamMouseClicked
        openSanPham();
    }//GEN-LAST:event_pnlSanPhamMouseClicked

    private void pnlNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNhanVienMouseClicked
        openNhanVien();
    }//GEN-LAST:event_pnlNhanVienMouseClicked

    private void pnlDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDangXuatMouseClicked
        openDangXuat();
    }//GEN-LAST:event_pnlDangXuatMouseClicked

    private void pnlHoaDonGocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonGocMouseClicked
        openHoaDon();
    }//GEN-LAST:event_pnlHoaDonGocMouseClicked

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
                String[] splitStrings = s.split("\\|\\|?");
                int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn lấy thông tin từ CCCD không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    txtCCCD.setText(splitStrings[0]);
                    txtHoTen.setText(splitStrings[1]);
                    txtDiaChi.setText(splitStrings[4]);
                    if (splitStrings[3].equalsIgnoreCase("Nam")) {
                        rdoNam.setSelected(true);
                    } else {
                        rdoNu.setSelected(true);
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

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(NhanVienUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienUI().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnNghiLam;
    private javax.swing.JButton btnThemNhanVien;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JComboBox<String> cboTimKiemTheo;
    private javax.swing.JComboBox<String> cboTrangThai;
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
    private javax.swing.JLabel jLabel30;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlHoaDonGoc;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhuyenMai;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JPanel pnlWebcam;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
