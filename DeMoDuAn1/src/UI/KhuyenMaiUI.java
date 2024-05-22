/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KhuyenMai;
import service.KhuyenMaiService;

/**
 *
 * @author ledin
 */
public class KhuyenMaiUI extends javax.swing.JFrame {

    KhuyenMaiService serviceKM = new KhuyenMaiService();
    List<KhuyenMai> listKM = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    DecimalFormat currencyFormat = new DecimalFormat("#,##0");

    public KhuyenMaiUI() {
        initComponents();
        setLocationRelativeTo(null);
        listKM = serviceKM.getAllKhuyenMai();
        fillToTableKM(listKM);
    }

    public void openKhuyenmai() {
        dispose();
        new KhuyenMaiUI().setVisible(true);
    }

    public void openHoaDon_Main() {
        dispose();
        new MainFrame().setVisible(true);
    }

    public void openHoaDon() {
        dispose();
        new HoaDonUI().setVisible(true);
    }

    public void openKhachHangUI() {
        dispose();
        new KhachHangUI().setVisible(true);
    }

    public void openThongKeUI() {
        dispose();
        new ThongKeUI().setVisible(true);
    }

    public void openNhanVien() {
        dispose();
        new NhanVienUI().setVisible(true);
    }

    public void openSanPham() {
        dispose();
        new SanPhamUI().setVisible(true);
    }

    public void openDangXuat() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn Đăng xuất không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }

    }

    public void fillToTableKM(List<KhuyenMai> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã", "Tên", "Hình Thức", "Mức Giảm", "Hóa đơn tối thiểu", "Số Lượng", "Tổng số HD đã dùng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (KhuyenMai km : list) {
            String hinhThuc = "";
            String MucGiam;
            if (km.getHinhThuc() == 0) {
                hinhThuc = "Giảm theo số tiền";
                MucGiam = currencyFormat.format(km.getMucGiamGia()) + " (VNĐ)";
            } else {
                hinhThuc = "Giảm theo phầm trăm";
                MucGiam = km.getMucGiamGia() + " (%)";
            }
            String dieuKienAD = "";
            String TrangThai = "";
            if (km.getTrangThai() == 2) {
                TrangThai = "Chưa hoạt động";
            } else if (km.getTrangThai() == 1) {
                TrangThai = "Đang hoạt động";
            } else {
                TrangThai = "Đã hết hạn";
            }
            String soTienMin = currencyFormat.format(km.getSoTienMin());
            tblModel.addRow(new Object[]{km.getMaKhuyenMai(), km.getTenKhuyenMai(),
                hinhThuc, MucGiam, soTienMin, km.getSoLuong(),
                km.getSoHoaDonDaApDung(), km.getThoiGianBatDau(),
                km.getThoiGianKetThuc(), TrangThai});
        }
        tblKhuyenMai.setModel(tblModel);
    }

    public void fillToFormKM(int index) {
        KhuyenMai km = listKM.get(index);
        txtMaKhuyenMai.setText(km.getMaKhuyenMai());
        txtMaKhuyenMai.setEnabled(false);
        txtTenKhuyenMai.setText(km.getTenKhuyenMai());
        cboHinhThuc.setSelectedIndex(km.getHinhThuc());
        txtMucGiam.setText(String.valueOf(km.getMucGiamGia()));
        txtTienMin.setText(String.valueOf(km.getSoTienMin()));
        txtSoLuong.setText(String.valueOf(km.getSoLuong()));
        if (km.getHinhThuc() == 0) {
            lblDonVi.setText("VNĐ");
        } else {
            lblDonVi.setText("%");
        }
        dcsTimeBigin.setDate(km.getThoiGianBatDau());
        dcsTimeOver.setDate(km.getThoiGianKetThuc());
        Date NgayBatDau = dcsTimeBigin.getDate();
        Date currentDate = new Date();
        System.out.println(" " + currentDate + "    " + NgayBatDau);
        if (NgayBatDau.before(currentDate)) {
            dcsTimeBigin.setEnabled(false);
        } else {
            dcsTimeBigin.setEnabled(true);
        }
    }

    public KhuyenMai getKhuyenMaiTF() {
        if (txtMaKhuyenMai.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống mã KM");
            return null;
        }
        //Kiểm tra xem mã khuyến mại đã tồn tại chưa
        int check = 0;
        if (txtMaKhuyenMai.isEnabled()) {
            for (KhuyenMai km : listKM) {
                if (km.getMaKhuyenMai().equalsIgnoreCase(txtMaKhuyenMai.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã KM đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtTenKhuyenMai.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống tên KM");
            return null;
        }
        if (txtMucGiam.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống mức giảm giá");
            return null;
        }
        Double MucGiam;
        try {
            MucGiam = Double.parseDouble(txtMucGiam.getText());
            if (cboHinhThuc.getSelectedIndex() == 0) {
                if (MucGiam < 0 || MucGiam > 2000000) {
                    JOptionPane.showMessageDialog(this, "Mức giảm phải lớn hơn 0 và bé hơn 2 triệu");
                    return null;
                }
            } else {
                if (MucGiam < 0 || MucGiam > 40) {
                    JOptionPane.showMessageDialog(this, "Mức giảm phải lớn hơn 0% và bé hơn 40%");
                    return null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Mức giảm phải là số thực");
            return null;
        }
        if (txtTienMin.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống Hóa đơn tối thiểu");
            return null;
        }
        double TienMin;
        try {
            TienMin = Double.parseDouble(txtTienMin.getText());
            if (TienMin < MucGiam) {
                JOptionPane.showMessageDialog(this, "Hóa đơn tối thiểu phải lớn hơn mức giảm");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tiền Min phải là số thực");
            return null;
        }
        if (txtSoLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được để trống số lượng");
            return null;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong < 0 || soLuong > 200) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 và bé hơn 200");
                return null;
            }
            int xx = tblKhuyenMai.getSelectedRow();
            if (xx >= 0 && soLuong <= listKM.get(xx).getSoHoaDonDaApDung()) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn bằng số hóa đơn đã áp dụng");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số thực");
            return null;
        }
        Date NgayBatDau = dcsTimeBigin.getDate();
        Date NgayKetThuc = dcsTimeOver.getDate();
        Date currentDate = new Date();
        if (dcsTimeBigin.isEnabled()) {
            if (NgayBatDau.before(currentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải lớn hơn ngày hôm nay");
                return null;
            }
        }
        if (NgayKetThuc.before(NgayBatDau)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu");
            return null;
        }
        KhuyenMai km = new KhuyenMai();
        km.setMaKhuyenMai(txtMaKhuyenMai.getText());
        km.setTenKhuyenMai(txtTenKhuyenMai.getText());
        km.setHinhThuc(cboHinhThuc.getSelectedIndex());
        km.setMucGiamGia(MucGiam);
        km.setThoiGianBatDau(dcsTimeBigin.getDate());
        km.setThoiGianKetThuc(dcsTimeOver.getDate());
        km.setSoTienMin(TienMin);
        km.setSoLuong(soLuong);
        return km;
    }

    public void ClearForm() {
        txtMaKhuyenMai.setEnabled(true);
        txtMaKhuyenMai.setText("");
        txtTenKhuyenMai.setText("");
        cboHinhThuc.setSelectedIndex(0);
        txtMucGiam.setText("");
        txtTienMin.setText("");
        txtSoLuong.setText("");
        dcsTimeBigin.setDate(null);
        dcsTimeOver.setDate(null);
        dcsTimeBigin.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panelKhuyenMai = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaKhuyenMai = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTenKhuyenMai = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cboHinhThuc = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtMucGiam = new javax.swing.JTextField();
        lblDonVi = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnThemKhuyenMai = new javax.swing.JButton();
        btnSuaKhuyenMai = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        txtTienMin = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        dcsTimeOver = new com.toedter.calendar.JDateChooser();
        dcsTimeBigin = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnKetThucKM = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        txtTimKiemTheoMa = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        cboSapXep = new javax.swing.JComboBox<>();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelKhuyenMai.setBackground(new java.awt.Color(204, 204, 204));
        panelKhuyenMai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 4));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tạo Mã Khuyến Mại"));

        jLabel11.setText("Mã Khuyến Mại");

        txtMaKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel12.setText("Chương trình khuyến mại");

        jLabel14.setText("Hinh Thức Giảm Giá");

        cboHinhThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giảm theo số tiền", "Giảm theo phần trăm" }));
        cboHinhThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucActionPerformed(evt);
            }
        });

        jLabel15.setText("Mức giảm giá");

        lblDonVi.setText("VNĐ");

        jLabel23.setText("Hóa đơn tối thiểu");

        jLabel24.setText("Thời gian bắt đầu");

        jLabel25.setText("Thời gian kết thúc");

        btnThemKhuyenMai.setText("Thêm Khuyến Mại");
        btnThemKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhuyenMaiActionPerformed(evt);
            }
        });

        btnSuaKhuyenMai.setText("Sửa Khuyến Mại");
        btnSuaKhuyenMai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhuyenMaiActionPerformed(evt);
            }
        });

        btnClear.setText("CLEAR FORM");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel29.setText("VNĐ");

        dcsTimeOver.setDateFormatString("yyyy-MM-dd");

        dcsTimeBigin.setDateFormatString("yyyy-MM-dd");
        dcsTimeBigin.setFocusTraversalPolicyProvider(true);

        jLabel20.setText("Số Lượng");

        btnKetThucKM.setText("Kết thúc khuyến mại");
        btnKetThucKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucKMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDonVi))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThemKhuyenMai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                                .addComponent(btnSuaKhuyenMai))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(dcsTimeOver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTienMin)
                                    .addComponent(dcsTimeBigin, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)))
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel29)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoLuong))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(btnKetThucKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTenKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblDonVi)
                                .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel20)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24)
                    .addComponent(dcsTimeBigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(dcsTimeOver, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemKhuyenMai)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSuaKhuyenMai)
                                .addComponent(btnClear)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnKetThucKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách khuyến mại"));

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Hình Thức", "Mức giảm", "Số tiền tối thiểu", "Số Lượng", "Tổng số hóa đơn đã AD", "Ngày bắt đầu", "Ngày kết thúc", "Trạng Thái"
            }
        ));
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhuyenMai);

        jLabel26.setText("Tìm kiếm theo mã giảm giá");

        txtTimKiemTheoMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemTheoMaActionPerformed(evt);
            }
        });

        jLabel28.setText("Trạng Thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã hết hạn", "Đang hoạt động", "Chưa hoạt động" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp theo", "Số hóa đơn đã áp dụng", "Ngày kết thúc" }));
        cboSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSapXepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtTimKiemTheoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimKiemTheoMa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelKhuyenMaiLayout = new javax.swing.GroupLayout(panelKhuyenMai);
        panelKhuyenMai.setLayout(panelKhuyenMaiLayout);
        panelKhuyenMaiLayout.setHorizontalGroup(
            panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKhuyenMaiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelKhuyenMaiLayout.setVerticalGroup(
            panelKhuyenMaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKhuyenMaiLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
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

        pnlKhuyenMai.setBackground(new java.awt.Color(255, 153, 153));
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
        jLabel30.setText("    Hóa đơn");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .addComponent(panelKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseClicked
        openThongKeUI();
    }//GEN-LAST:event_pnlThongKeMouseClicked

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        int vtri = -1;
        vtri = tblKhuyenMai.getSelectedRow();
        fillToFormKM(vtri);
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void btnThemKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhuyenMaiActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khuyến mại không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            KhuyenMai km = getKhuyenMaiTF();
            if (km == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceKM.InsertKhuyenMai(km));
                serviceKM.UpdateTrangThaiKM(km);
                listKM = serviceKM.getAllKhuyenMai();
                fillToTableKM(listKM);
            }
        }
    }//GEN-LAST:event_btnThemKhuyenMaiActionPerformed

    private void cboHinhThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucActionPerformed
        if (cboHinhThuc.getSelectedIndex() == 0)
            lblDonVi.setText("VNĐ");
        else
            lblDonVi.setText("%");
    }//GEN-LAST:event_cboHinhThucActionPerformed

    private void btnSuaKhuyenMaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhuyenMaiActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn chỉnh sửa khuyến mại không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vtri = -1;
            vtri = tblKhuyenMai.getSelectedRow();
            if (vtri < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng muốn chỉnh sửa");
                return;
            }
            KhuyenMai km = getKhuyenMaiTF();
            if (km == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceKM.UpdateKhuyenMai(km));
                serviceKM.UpdateTrangThaiKM(km);
                listKM = serviceKM.getAllKhuyenMai();
                fillToTableKM(listKM);
            }
        }
    }//GEN-LAST:event_btnSuaKhuyenMaiActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        ClearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtTimKiemTheoMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemTheoMaActionPerformed
        String MaKM = txtTimKiemTheoMa.getText();
        listKM = serviceKM.getAllKhuyenMaiByMa(MaKM);
        fillToTableKM(listKM);
    }//GEN-LAST:event_txtTimKiemTheoMaActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        txtTimKiemTheoMa.setText("");
        int a = cboSapXep.getSelectedIndex();
        int b = cboTrangThai.getSelectedIndex();
        if (b == 0) {
            if (a == 0) {
                listKM = serviceKM.getAllKhuyenMai();
                fillToTableKM(listKM);
            } else if (a == 1) {
                listKM = serviceKM.getAllKhuyenMaiTheoSoHDAP();
                fillToTableKM(listKM);
            } else {
                listKM = serviceKM.getAllKhuyenMaiTheoNgayKetThuc();
                fillToTableKM(listKM);
            }
        } else {
            if (a == 0) {
                listKM = serviceKM.getAllKhuyenMaiByTrangThai(b - 1);
                fillToTableKM(listKM);
            } else if (a == 1) {
                listKM = serviceKM.getAllKhuyenMaiTheo_SoHDAP_TT(b - 1);
                fillToTableKM(listKM);
            } else {
                listKM = serviceKM.getAllKhuyenMaiTheo_NgayKetThuc_TT(b - 1);
                fillToTableKM(listKM);
            }
        }
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void pnlHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonMouseClicked
        openHoaDon_Main();
    }//GEN-LAST:event_pnlHoaDonMouseClicked

    private void pnlKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhuyenMaiMouseClicked
        openKhuyenmai();
    }//GEN-LAST:event_pnlKhuyenMaiMouseClicked

    private void btnKetThucKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucKMActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn chỉnh sửa khuyến mại không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vtri = -1;
            vtri = tblKhuyenMai.getSelectedRow();
            if (vtri < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng muốn kết thúc");
                return;
            } else {
                String maKhuyenMai = listKM.get(vtri).getMaKhuyenMai();
                JOptionPane.showMessageDialog(this, serviceKM.UpdateTrangThaiKMTrucTiep(maKhuyenMai));
                listKM = serviceKM.getAllKhuyenMai();
                fillToTableKM(listKM);
            }

        }
    }//GEN-LAST:event_btnKetThucKMActionPerformed

    private void pnlKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachHangMouseClicked
        openKhachHangUI();
    }//GEN-LAST:event_pnlKhachHangMouseClicked

    private void cboSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSapXepActionPerformed
        txtTimKiemTheoMa.setText("");
        int a = cboSapXep.getSelectedIndex();
        int b = cboTrangThai.getSelectedIndex();
        if (b == 0) {
            if (a == 0) {
                listKM = serviceKM.getAllKhuyenMai();
                fillToTableKM(listKM);
            } else if (a == 1) {
                listKM = serviceKM.getAllKhuyenMaiTheoSoHDAP();
                fillToTableKM(listKM);
            } else {
                listKM = serviceKM.getAllKhuyenMaiTheoNgayKetThuc();
                fillToTableKM(listKM);
            }
        } else {
            if (a == 0) {
                listKM = serviceKM.getAllKhuyenMaiByTrangThai(b - 1);
                fillToTableKM(listKM);
            } else if (a == 1) {
                listKM = serviceKM.getAllKhuyenMaiTheo_SoHDAP_TT(b - 1);
                fillToTableKM(listKM);
            } else {
                listKM = serviceKM.getAllKhuyenMaiTheo_NgayKetThuc_TT(b - 1);
                fillToTableKM(listKM);
            }
        }
    }//GEN-LAST:event_cboSapXepActionPerformed

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
            java.util.logging.Logger.getLogger(KhuyenMaiUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhuyenMaiUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhuyenMaiUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKetThucKM;
    private javax.swing.JButton btnSuaKhuyenMai;
    private javax.swing.JButton btnThemKhuyenMai;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHinhThuc;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JComboBox<String> cboTrangThai;
    private com.toedter.calendar.JDateChooser dcsTimeBigin;
    private com.toedter.calendar.JDateChooser dcsTimeOver;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDonVi;
    private javax.swing.JPanel panelKhuyenMai;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlHoaDonGoc;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhuyenMai;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JTable tblKhuyenMai;
    private javax.swing.JTextField txtMaKhuyenMai;
    private javax.swing.JTextField txtMucGiam;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKhuyenMai;
    private javax.swing.JTextField txtTienMin;
    private javax.swing.JTextField txtTimKiemTheoMa;
    // End of variables declaration//GEN-END:variables
}
