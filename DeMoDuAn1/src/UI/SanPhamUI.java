/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ChatLieu;
import model.KhachHang;
import model.KieuDang;
import model.MauSac;
import model.NhanVien;
import model.SanPhamChiTiet;
import model.SanPhamMain;
import model.SizeGiay;
import model.ThuongHieu;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.ChatLieuService;
import service.KieuDangService;
import service.MauSacService;
import service.SanPhamChiTietService;
import service.SanPhamMainService;
import service.SizeGiayService;
import service.ThuongHieuService;

/**
 *
 * @author ledin
 */
public class SanPhamUI extends javax.swing.JFrame {

    List<MauSac> listMau = new ArrayList<>();
    List<SizeGiay> listSize = new ArrayList<>();
    List<ChatLieu> listCL = new ArrayList<>();
    List<KieuDang> listKD = new ArrayList<>();
    List<ThuongHieu> listTH = new ArrayList<>();
    List<SanPhamMain> listSPM = new ArrayList<>();
    List<SanPhamChiTiet> listSPCT = new ArrayList<>();

    MauSacService serviceMau = new MauSacService();
    SizeGiayService serviceSize = new SizeGiayService();
    ChatLieuService serviceChatLieu = new ChatLieuService();
    KieuDangService serviceKieuDang = new KieuDangService();
    ThuongHieuService serviceThuongHieu = new ThuongHieuService();
    SanPhamMainService serviceSPM = new SanPhamMainService();
    SanPhamChiTietService serviceSPCT = new SanPhamChiTietService();

    DecimalFormat currencyFormat = new DecimalFormat("#,##0");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SanPhamUI() {
        initComponents();
        setLocationRelativeTo(null);
        listMau = serviceMau.getAllMauSac();
        fillToTableMauSac(listMau);
        listSize = serviceSize.getAllSizeGiay();
        fillToTableSizeGiay(listSize);
        listCL = serviceChatLieu.getAllChatLieu();
        fillToTableChatLieu(listCL);
        listKD = serviceKieuDang.getAllKieuDang();
        fillToTableKieuDang(listKD);
        listTH = serviceThuongHieu.getAllThuongHieu();
        initComboboxThuongHieu(listTH);
        listSPM = serviceSPM.getAllSanPhamMain();
        fillToTableSanPhamMain(listSPM);
        initComBoBoxSanPham(listSPM);
        initComBoBoxMauSac(listMau);
        initComBoBoxSizeGiay(listSize);
        initComBoBoxChatLieu(listCL);
        initComBoBoxKieuDang(listKD);
        listSPCT = serviceSPCT.getAllSPCT();
        fillToTableSanPhamChiTiet(listSPCT);
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

    //CRUD Màu SẮC
    public void fillToTableMauSac(List<MauSac> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã màu", "Tên màu", "Độ tương phản", "Độ bảo hòa", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        for (MauSac mau : list) {
            String tt = "";
            if (mau.getTrangThai() == 0) {
                tt = "Non-active";
            } else {
                tt = "Active";
            }
            tblModel.addRow(new Object[]{mau.getMaMau(), mau.getTenMau(),
                mau.getDoTuongPhan(), mau.getDoBaoHoa(), tt});
        }
        tblMauSac.setModel(tblModel);
    }

    public void fillToFormMauSac(int index) {
        MauSac mau = listMau.get(index);
        txtMaMau.setText(mau.getMaMau());
        txtTenMau.setText(mau.getTenMau());
        txtDoTuongPhan.setText(String.valueOf(mau.getDoTuongPhan()));
        txtDoBaoHoa.setText(String.valueOf(mau.getDoBaoHoa()));
        txtMaMau.setEnabled(false);
    }

    public MauSac getMauSac() {
        if (txtMaMau.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã màu");
            return null;
        }
        int check = 0;
        if (txtMaMau.isEnabled()) {
            for (MauSac mau : listMau) {
                if (mau.getMaMau().equalsIgnoreCase(txtMaMau.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã màu đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtTenMau.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống tên màu");
            return null;
        }
        if (txtDoTuongPhan.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống độ tương phản");
            return null;
        }
        if (txtDoBaoHoa.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống độ bảo hòa");
            return null;
        }
        int doTuongPhan;
        int doBaoHoa;
        try {
            doTuongPhan = Integer.parseInt(txtDoTuongPhan.getText());
            doBaoHoa = Integer.parseInt(txtDoBaoHoa.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Độ tương phản và độ bảo hòa phải là số");
            return null;
        }
        MauSac mau = new MauSac();
        mau.setMaMau(txtMaMau.getText());
        mau.setTenMau(txtTenMau.getText());
        mau.setDoTuongPhan(doTuongPhan);
        mau.setDoBaoHoa(doBaoHoa);
        return mau;
    }

    public void clearMauSac() {
        txtMaMau.setText("");
        txtTenMau.setText("");
        txtDoBaoHoa.setText("");
        txtDoTuongPhan.setText("");
        txtMaMau.setEnabled(true);
    }
    //KẾT THÚC CRUD MÀU SẮC

    //-------------------------------------------------------------------------//
    //CRUD SIZE GIÀY
    public void fillToTableSizeGiay(List<SizeGiay> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã size", "Số size", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (SizeGiay size : list) {
            String tt = "";
            if (size.getTrangThai() == 0) {
                tt = "Non-active";
            } else {
                tt = "Active";
            }
            tblModel.addRow(new Object[]{size.getMaSize(), size.getSoSize(), tt});
        }
        tblSizeGiay.setModel(tblModel);
    }

    public void fillToFormSizeGiay(int index) {
        SizeGiay size = listSize.get(index);
        txtMaSize.setText(size.getMaSize());
        txtSoSize.setText(String.valueOf(size.getSoSize()));
        txtMaSize.setEnabled(false);
    }

    public SizeGiay getSizeGiay() {
        if (txtMaSize.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã size");
            return null;
        }
        int check = 0;
        if (txtMaSize.isEnabled()) {
            for (SizeGiay size : listSize) {
                if (size.getMaSize().equalsIgnoreCase(txtMaSize.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã size đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtSoSize.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống số size");
            return null;
        }
        int soSize;
        try {
            soSize = Integer.parseInt(txtSoSize.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số size phải là số");
            return null;
        }
        SizeGiay size = new SizeGiay();
        size.setMaSize(txtMaSize.getText());
        size.setSoSize(soSize);
        return size;
    }

    public void clearSizeGiay() {
        txtMaSize.setText("");
        txtSoSize.setText("");
        txtMaSize.setEnabled(true);
    }
    //KẾT THÚC CRUD SIZE GIÀY

    //------------------------------------------------------------------------//
    //CRUD CHẤT LIỆU
    public void fillToTableChatLieu(List<ChatLieu> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã chất liệu", "Tên chất liệu", "Mô tả CL", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (ChatLieu cl : list) {
            String tt = "";
            if (cl.getTrangThai() == 0) {
                tt = "Non-active";
            } else {
                tt = "Active";
            }
            tblModel.addRow(new Object[]{cl.getMaChatLieu(),
                cl.getTenChatLieu(), cl.getMoTa(), tt});
        }
        tblChatLieu.setModel(tblModel);
    }

    public void fillToFormChatLieu(int index) {
        ChatLieu cl = listCL.get(index);
        txtMaChatLieu.setText(cl.getMaChatLieu());
        txtTenChatLieu.setText(cl.getTenChatLieu());
        txtMoTaCL.setText(cl.getMoTa());
        txtMaChatLieu.setEnabled(false);
    }

    public ChatLieu getChatLieu() {
        if (txtMaChatLieu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã chất liệu");
            return null;
        }
        int check = 0;
        if (txtMaChatLieu.isEnabled()) {
            for (ChatLieu cl : listCL) {
                if (cl.getMaChatLieu().equalsIgnoreCase(txtMaChatLieu.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã chất liệu đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtTenChatLieu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống tên chất liệu");
            return null;
        }
        if (txtMoTaCL.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mô tả");
            return null;
        }
        ChatLieu cl = new ChatLieu();
        cl.setMaChatLieu(txtMaChatLieu.getText());
        cl.setTenChatLieu(txtTenChatLieu.getText());
        cl.setMoTa(txtMoTaCL.getText());
        return cl;
    }

    public void clearChatLieu() {
        txtMaChatLieu.setText("");
        txtTenChatLieu.setText("");
        txtMoTaCL.setText("");
        txtMaChatLieu.setEnabled(true);
    }
    //KẾT THÚC CRUD CHẤT LIỆU

    //------------------------------------------------------------------------//
    //CRUD KIỂU DÁNG
    public void fillToTableKieuDang(List<KieuDang> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã kiểu dáng", "Tên kiểu dáng", "Mô tả KD", "Trạng thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (KieuDang kd : list) {
            String tt = "";
            if (kd.getTrangThai() == 0) {
                tt = "Non-active";
            } else {
                tt = "Active";
            }
            tblModel.addRow(new Object[]{kd.getMaKieuDang(),
                kd.getTenKieuDang(), kd.getMoTa(), tt});
        }
        tblKieuDang.setModel(tblModel);
    }

    public void fillToFormKieuDang(int index) {
        KieuDang kd = listKD.get(index);
        txtMaKieuDang.setText(kd.getMaKieuDang());
        txtTenKieuDang.setText(kd.getTenKieuDang());
        txtMoTaKD.setText(kd.getMoTa());
        txtMaKieuDang.setEnabled(false);
    }

    public KieuDang getKieuDang() {
        if (txtMaKieuDang.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã kiểu dáng");
            return null;
        }
        int check = 0;
        if (txtMaKieuDang.isEnabled()) {
            for (KieuDang kd : listKD) {
                if (kd.getMaKieuDang().equalsIgnoreCase(txtMaKieuDang.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã kiểu dáng đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtTenKieuDang.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống tên kiểu dáng");
            return null;
        }
        if (txtMoTaKD.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mô tả kiểu dáng");
            return null;
        }
        KieuDang kd = new KieuDang();
        kd.setMaKieuDang(txtMaKieuDang.getText());
        kd.setTenKieuDang(txtTenKieuDang.getText());
        kd.setMoTa(txtMoTaKD.getText());
        return kd;
    }

    public void clearKieuDang() {
        txtMaKieuDang.setText("");
        txtTenKieuDang.setText("");
        txtMoTaKD.setText("");
        txtMaKieuDang.setEnabled(true);
    }
    //KẾT THÚC CRUD KIỂU DÁNG

    //------------------------------------------------------------------------//
    //CRUD SẢN PHẨM
    public void initComboboxThuongHieu(List<ThuongHieu> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (ThuongHieu th : list) {
            cboModel.addElement(th.getTenThuongHieu());
        }
        cboThuongHieu.setModel(cboModel);
    }

    public void fillToTableSanPhamMain(List<SanPhamMain> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã sản phẩm", "Tên sản phẩm", "Xuất xứ", "Ngày Tạo", "Ngày sửa",
            "Trạng Thái", "Tên Thương Hiệu"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (SanPhamMain spm : list) {
            String tt = "";
            if (spm.getTrangThai() == 0) {
                tt = "Đã dừng bán";
            } else {
                tt = "Đang hoạt động";
            }
            ThuongHieu th = serviceThuongHieu.getThuongHieubyMa(spm.getMaThuongHieu());
            tblModel.addRow(new Object[]{spm.getMaSanPham(), spm.getTenSanPham(),
                spm.getXuatXu(), spm.getNgayTao(), spm.getNgaySua(), tt, th.getTenThuongHieu()});
        }
        tblSanPhamMain.setModel(tblModel);
    }

    public void fillToFormSanPhamMain(int index) {
        SanPhamMain spm = listSPM.get(index);
        txtMaSanPhamMain.setText(spm.getMaSanPham());
        txtTenSanPhamMain.setText(spm.getTenSanPham());
        txtXuatXu.setText(spm.getXuatXu());
        ThuongHieu th = serviceThuongHieu.getThuongHieubyMa(spm.getMaThuongHieu());
        cboThuongHieu.setSelectedItem(th.getTenThuongHieu());
        txtMaSanPhamMain.setEnabled(false);
    }

    public SanPhamMain getSanPhamMain() {
        if (txtMaSanPhamMain.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã sản phẩm");
            return null;
        }
        int check = 0;
        if (txtMaSanPhamMain.isEnabled()) {
            for (SanPhamMain spm : listSPM) {
                if (spm.getMaSanPham().equalsIgnoreCase(txtMaSanPhamMain.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtTenSanPhamMain.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống tên sản phẩm");
            return null;
        }
        if (txtXuatXu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống xuất xứ");
            return null;
        }
        ThuongHieu th = listTH.get(cboThuongHieu.getSelectedIndex());
        String maThuongHieu = th.getMaThuongHieu();
        SanPhamMain spm = new SanPhamMain();
        spm.setMaSanPham(txtMaSanPhamMain.getText());
        spm.setTenSanPham(txtTenSanPhamMain.getText());
        spm.setXuatXu(txtXuatXu.getText());
        spm.setMaThuongHieu(maThuongHieu);
        return spm;
    }

    public void clearSanPhamMain() {
        txtMaSanPhamMain.setText("");
        txtTenSanPhamMain.setText("");
        txtXuatXu.setText("");
        txtMaSanPhamMain.setEnabled(true);
    }
    //KẾT THÚC CRUD SẢN PHẨM

    ///-----------------------------------------------------------------------//
    //CRUD SẢN PHẨM CHI TIẾT
    public void initComBoBoxSanPham(List<SanPhamMain> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (SanPhamMain sp : list) {
            cboModel.addElement(sp.getTenSanPham());
        }
        cboSanPhamMain.setModel(cboModel);
    }

    public void initComBoBoxMauSac(List<MauSac> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (MauSac mau : list) {
            cboModel.addElement(mau.getTenMau());
        }
        cboMauSac.setModel(cboModel);
    }

    public void initComBoBoxSizeGiay(List<SizeGiay> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (SizeGiay size : list) {
            cboModel.addElement(size.getSoSize());
        }
        cboSizeGiay.setModel(cboModel);
    }

    public void initComBoBoxChatLieu(List<ChatLieu> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (ChatLieu cl : list) {
            cboModel.addElement(cl.getTenChatLieu());
        }
        cboChatLieu.setModel(cboModel);
    }

    public void initComBoBoxKieuDang(List<KieuDang> list) {
        DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
        for (KieuDang kd : list) {
            cboModel.addElement(kd.getTenKieuDang());
        }
        cboKieuDang.setModel(cboModel);
    }

    public void fillToTableSanPhamChiTiet(List<SanPhamChiTiet> list) {
        DefaultTableModel tblModel = new DefaultTableModel();
        String cols[] = {"Mã SPCT", "Tên sản phẩm", "Tên màu", "Số Size",
            "Tên Chất liệu", "Tên Kiểu dáng", "Số lượng", "Đơn giá",
            "Ngày tạo", "Ngày sửa", "Trạng Thái"};
        tblModel.setColumnIdentifiers(cols);
        tblModel.setRowCount(0);
        for (SanPhamChiTiet spct : list) {
            String tt = "";
            if (spct.getTrangThai() == 0) {
                tt = "Hết hàng";
            } else if (spct.getTrangThai() == 1) {
                tt = "Còn hàng";
            } else {
                tt = "Đã dừng bán";
            }
            String tenSP = serviceSPM.getSanPhamMainbyMa(spct.getMaSanPham()).getTenSanPham();
            String tenMau = serviceMau.getMauSacByMa(spct.getMaMau()).getTenMau();
            int soSize = serviceSize.getSizeByMa(spct.getMaSize()).getSoSize();
            String tenChatLieu = serviceChatLieu.getChatLieuByMa(spct.getMaChatLieu()).getTenChatLieu();
            String tenKieuDang = serviceKieuDang.getKieuDangByMa(spct.getMaKieuDang()).getTenKieuDang();
            String donGia = currencyFormat.format(spct.getDonGia());
            tblModel.addRow(new Object[]{spct.getMaSPCT(), tenSP, tenMau,
                soSize, tenChatLieu, tenKieuDang, spct.getSoLuong(),
                donGia, spct.getNgayTao(), spct.getNgaySua(), tt});
        }
        tblSPCT.setModel(tblModel);
    }

    public void filltoFormSPCT(int index) {
        SanPhamChiTiet spct = listSPCT.get(index);
        txtMaSPCT.setText(spct.getMaSPCT());
        txtDonGia.setText(String.valueOf(spct.getDonGia()));
        txtSoLuong.setText(String.valueOf(spct.getSoLuong()));
        cboSanPhamMain.setSelectedItem(serviceSPM.getSanPhamMainbyMa(spct.getMaSanPham()).getTenSanPham());
        cboMauSac.setSelectedItem(serviceMau.getMauSacByMa(spct.getMaMau()).getTenMau());
        cboSizeGiay.setSelectedItem(serviceSize.getSizeByMa(spct.getMaSize()).getSoSize());
        cboChatLieu.setSelectedItem(serviceChatLieu.getChatLieuByMa(spct.getMaChatLieu()).getTenChatLieu());
        cboKieuDang.setSelectedItem(serviceKieuDang.getKieuDangByMa(spct.getMaKieuDang()).getTenKieuDang());
        txtMaSPCT.setEnabled(false);
    }

    public SanPhamChiTiet getSanPhamChiTiet() {
        if (txtMaSPCT.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống mã SPCT");
            return null;
        }
        int check = 0;
        if (txtMaSPCT.isEnabled()) {
            for (SanPhamChiTiet spct : listSPCT) {
                if (spct.getMaSPCT().equalsIgnoreCase(txtMaSPCT.getText())) {
                    check++;
                    break;
                }
            }
        }
        if (check > 0) {
            JOptionPane.showMessageDialog(this, "Mã SPCT đã tồn tại mời nhập mã khác");
            return null;
        }
        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống số lượng");
            return null;
        }
        if (txtDonGia.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn không được bỏ trống đơn giá");
            return null;
        }
        int soLuong;
        double donGia;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
            return null;
        }
        try {
            donGia = Double.parseDouble(txtDonGia.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số thực");
            return null;
        }
        SanPhamChiTiet spct = new SanPhamChiTiet();
        spct.setMaSPCT(txtMaSPCT.getText());
        spct.setSoLuong(soLuong);
        spct.setDonGia(donGia);
        spct.setMaSanPham(listSPM.get(cboSanPhamMain.getSelectedIndex()).getMaSanPham());
        spct.setMaMau(listMau.get(cboMauSac.getSelectedIndex()).getMaMau());
        spct.setMaSize(listSize.get(cboSizeGiay.getSelectedIndex()).getMaSize());
        spct.setMaChatLieu(listCL.get(cboChatLieu.getSelectedIndex()).getMaChatLieu());
        spct.setMaKieuDang(listKD.get(cboKieuDang.getSelectedIndex()).getMaKieuDang());
        return spct;
    }

    public void clearFormSanPhamChiTiet() {
        txtMaSPCT.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        txtMaSPCT.setEnabled(true);
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
        pnlDangXuat = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlThongKe = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pnlHoaDonGoc = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtMaSanPhamMain = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTenSanPhamMain = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtXuatXu = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboThuongHieu = new javax.swing.JComboBox<>();
        btnThemSanPhamMain = new javax.swing.JButton();
        btnSuaSanPhamMain = new javax.swing.JButton();
        btnDungBanSPMain = new javax.swing.JButton();
        btnClearSanPhamMain = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamMain = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        cboSanPhamMain = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtMaSPCT = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        cboSizeGiay = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        cboChatLieu = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        cboKieuDang = new javax.swing.JComboBox<>();
        btnThemSPCT = new javax.swing.JButton();
        btnSuaSPCT = new javax.swing.JButton();
        btnDungBan = new javax.swing.JButton();
        btnClearSPCT = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnDocFile = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSizeGiay = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        txtMaSize = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtSoSize = new javax.swing.JTextField();
        btnThemSize = new javax.swing.JButton();
        btnSuaSize = new javax.swing.JButton();
        btnClearSize = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMauSac = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtMaMau = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtTenMau = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtDoTuongPhan = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtDoBaoHoa = new javax.swing.JTextField();
        btnThemMau = new javax.swing.JButton();
        btnSuaMau = new javax.swing.JButton();
        btnClearMau = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblChatLieu = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        txtMaChatLieu = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtTenChatLieu = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTaCL = new javax.swing.JTextArea();
        btnThemChatLieu = new javax.swing.JButton();
        btnSuaChatLieu = new javax.swing.JButton();
        btnClearChatLieu = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblKieuDang = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        txtMaKieuDang = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtTenKieuDang = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtMoTaKD = new javax.swing.JTextArea();
        btnThemKieuDang = new javax.swing.JButton();
        btnSuaKieuDang = new javax.swing.JButton();
        btnClearKieuDang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setPreferredSize(new java.awt.Dimension(347, 1023));

        pnlSanPham.setBackground(new java.awt.Color(255, 153, 153));
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
                .addContainerGap(44, Short.MAX_VALUE))
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

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Price list.png"))); // NOI18N
        jLabel51.setText("      Hóa đơn");

        javax.swing.GroupLayout pnlHoaDonGocLayout = new javax.swing.GroupLayout(pnlHoaDonGoc);
        pnlHoaDonGoc.setLayout(pnlHoaDonGocLayout);
        pnlHoaDonGocLayout.setHorizontalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHoaDonGocLayout.setVerticalGroup(
            pnlHoaDonGocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHoaDonGocLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 3));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tùy chỉnh sản phẩm"));

        jLabel11.setText("Mã Sản Phẩm");

        jLabel12.setText("Tên Sản Phẩm");

        jLabel14.setText("Xuất Xứ");

        jLabel15.setText("Thương hiệu");

        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThemSanPhamMain.setText("Thêm sản phẩm");
        btnThemSanPhamMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamMainActionPerformed(evt);
            }
        });

        btnSuaSanPhamMain.setText("Chỉnh sửa sản phẩm");
        btnSuaSanPhamMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamMainActionPerformed(evt);
            }
        });

        btnDungBanSPMain.setText("Dừng bán");
        btnDungBanSPMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungBanSPMainActionPerformed(evt);
            }
        });

        btnClearSanPhamMain.setText("CLEAR FORM");
        btnClearSanPhamMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSanPhamMainActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenSanPhamMain))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaSanPhamMain, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(124, 124, 124)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboThuongHieu, 0, 224, Short.MAX_VALUE)
                            .addComponent(txtXuatXu)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnThemSanPhamMain)
                        .addGap(44, 44, 44)
                        .addComponent(btnSuaSanPhamMain)
                        .addGap(45, 45, 45)
                        .addComponent(btnDungBanSPMain, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnClearSanPhamMain)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtMaSanPhamMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTenSanPhamMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSanPhamMain)
                    .addComponent(btnSuaSanPhamMain)
                    .addComponent(btnDungBanSPMain)
                    .addComponent(btnClearSanPhamMain))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        tblSanPhamMain.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSanPhamMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMainMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamMain);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 98, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm chi tiết"));

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSPCT);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1017, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 93, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Tùy chỉnh sản phẩm chi tiết"));

        jLabel20.setText("Tên sản phẩm");

        cboSanPhamMain.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel23.setText("Mã SPCT");

        jLabel24.setText("Đơn giá");

        jLabel25.setText("Số lượng");

        jLabel26.setText("Màu sắc");

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel28.setText("Số size");

        cboSizeGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel29.setText("Chất liệu");

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel30.setText("Kiểu dáng");

        cboKieuDang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnThemSPCT.setText("Thêm SPCT");
        btnThemSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPCTActionPerformed(evt);
            }
        });

        btnSuaSPCT.setText("Sửa SPCT");
        btnSuaSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPCTActionPerformed(evt);
            }
        });

        btnDungBan.setText("Dừng bán");
        btnDungBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungBanActionPerformed(evt);
            }
        });

        btnClearSPCT.setText("CLEAR FORM");
        btnClearSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSPCTActionPerformed(evt);
            }
        });

        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnDocFile.setText("Đọc file");
        btnDocFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23)
                            .addComponent(jLabel26)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboSanPhamMain, 0, 170, Short.MAX_VALUE)
                            .addComponent(txtMaSPCT)
                            .addComponent(cboMauSac, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnThemSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnSuaSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnDungBan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel28))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboSizeGiay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSoLuong)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(txtDonGia))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboKieuDang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(btnClearSPCT)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnDocFile)
                .addGap(46, 46, 46))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cboSanPhamMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel26))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(cboSizeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)
                        .addComponent(cboKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSuaSPCT)
                        .addComponent(btnThemSPCT))
                    .addComponent(btnDungBan)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClearSPCT)
                        .addComponent(btnXuatFile)
                        .addComponent(btnDocFile)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sản Phẩm Chi Tiết", jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Số size"));

        tblSizeGiay.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSizeGiay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSizeGiayMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblSizeGiay);

        jLabel43.setText("Mã size");

        jLabel44.setText("Số size");

        btnThemSize.setText("Thêm Size");
        btnThemSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSizeActionPerformed(evt);
            }
        });

        btnSuaSize.setText("Chỉnh sửa size");
        btnSuaSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSizeActionPerformed(evt);
            }
        });

        btnClearSize.setText("Clear form");
        btnClearSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel43)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSize, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoSize, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(btnThemSize)
                        .addGap(51, 51, 51)
                        .addComponent(btnSuaSize)
                        .addGap(54, 54, 54)
                        .addComponent(btnClearSize)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtMaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(52, 52, 52)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemSize)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSuaSize)
                        .addComponent(btnClearSize)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Màu sắc"));

        tblMauSac.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMauSacMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblMauSac);

        jLabel31.setText("Mã màu");

        jLabel40.setText("Tên màu");

        jLabel41.setText("Độ tương phản");

        jLabel42.setText("Độ bảo hòa");

        btnThemMau.setText("Thêm Màu");
        btnThemMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMauActionPerformed(evt);
            }
        });

        btnSuaMau.setText("Chỉnh sửa");
        btnSuaMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMauActionPerformed(evt);
            }
        });

        btnClearMau.setText("Clear form");
        btnClearMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearMauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaMau, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(18, 18, 18)
                                .addComponent(txtDoTuongPhan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(18, 18, 18)
                                .addComponent(txtDoBaoHoa))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTenMau, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnThemMau)
                .addGap(46, 46, 46)
                .addComponent(btnSuaMau)
                .addGap(49, 49, 49)
                .addComponent(btnClearMau)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtMaMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txtTenMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtDoTuongPhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(txtDoBaoHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemMau)
                    .addComponent(btnSuaMau)
                    .addComponent(btnClearMau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Chất liệu"));

        tblChatLieu.setModel(new javax.swing.table.DefaultTableModel(
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
        tblChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChatLieuMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblChatLieu);

        jLabel45.setText("Mã chất liệu");

        jLabel46.setText("Tên chất liệu");

        jLabel47.setText("Mô tả");

        txtMoTaCL.setColumns(20);
        txtMoTaCL.setRows(5);
        jScrollPane1.setViewportView(txtMoTaCL);

        btnThemChatLieu.setText("Thêm chất liệu");
        btnThemChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChatLieuActionPerformed(evt);
            }
        });

        btnSuaChatLieu.setText("Chỉnh sửa");
        btnSuaChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaChatLieuActionPerformed(evt);
            }
        });

        btnClearChatLieu.setText("Clear form");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel47))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel46)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnThemChatLieu)
                        .addGap(41, 41, 41)
                        .addComponent(btnSuaChatLieu)
                        .addGap(44, 44, 44)
                        .addComponent(btnClearChatLieu)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txtMaChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(txtTenChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemChatLieu)
                    .addComponent(btnSuaChatLieu)
                    .addComponent(btnClearChatLieu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Kiểu dáng"));

        tblKieuDang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblKieuDang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKieuDangMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblKieuDang);

        jLabel48.setText("Mã kiểu dáng");

        jLabel49.setText("Tên kiểu dáng");

        jLabel50.setText("Mô tả");

        txtMoTaKD.setColumns(20);
        txtMoTaKD.setRows(5);
        jScrollPane8.setViewportView(txtMoTaKD);

        btnThemKieuDang.setText("Thêm kiểu dáng");
        btnThemKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKieuDangActionPerformed(evt);
            }
        });

        btnSuaKieuDang.setText("Chỉnh sửa");
        btnSuaKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKieuDangActionPerformed(evt);
            }
        });

        btnClearKieuDang.setText("Clear form");
        btnClearKieuDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearKieuDangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel50))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(txtMaKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel49)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTenKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnThemKieuDang)
                        .addGap(38, 38, 38)
                        .addComponent(btnSuaKieuDang)
                        .addGap(53, 53, 53)
                        .addComponent(btnClearKieuDang)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtMaKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(txtTenKieuDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemKieuDang)
                    .addComponent(btnSuaKieuDang)
                    .addComponent(btnClearKieuDang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 251, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thuộc tính", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1379, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
                .addContainerGap())
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

    private void tblMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMauSacMouseClicked
        int x = tblMauSac.getSelectedRow();
        fillToFormMauSac(x);
    }//GEN-LAST:event_tblMauSacMouseClicked

    private void btnThemMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMauActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm màu sắc không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            MauSac mau = getMauSac();
            if (mau == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceMau.InsertMauSac(mau));
                listMau = serviceMau.getAllMauSac();
                fillToTableMauSac(listMau);
            }
        }
    }//GEN-LAST:event_btnThemMauActionPerformed

    private void btnSuaMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMauActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật màu sắc không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int x = -1;
            x = tblMauSac.getSelectedRow();
            if (x < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn dòng muốn cập nhật");
                return;
            } else {
                MauSac mau = getMauSac();
                if (mau == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceMau.UpdateMauSac(mau));
                    listMau = serviceMau.getAllMauSac();
                    fillToTableMauSac(listMau);
                }
            }
        }
    }//GEN-LAST:event_btnSuaMauActionPerformed

    private void btnClearMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearMauActionPerformed
        clearMauSac();
    }//GEN-LAST:event_btnClearMauActionPerformed

    private void tblSizeGiayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSizeGiayMouseClicked
        int vt = tblSizeGiay.getSelectedRow();
        fillToFormSizeGiay(vt);
    }//GEN-LAST:event_tblSizeGiayMouseClicked

    private void btnThemSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSizeActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm size giày không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            SizeGiay size = getSizeGiay();
            if (size == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceSize.InsertSizeGiay(size));
                listSize = serviceSize.getAllSizeGiay();
                fillToTableSizeGiay(listSize);
            }
        }
    }//GEN-LAST:event_btnThemSizeActionPerformed

    private void btnSuaSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSizeActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa size giày không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblSizeGiay.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn size muốn sửa");
                return;
            } else {
                SizeGiay size = getSizeGiay();
                if (size == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceSize.UpdateSizeGiay(size));
                    listSize = serviceSize.getAllSizeGiay();
                    fillToTableSizeGiay(listSize);
                }
            }
        }
    }//GEN-LAST:event_btnSuaSizeActionPerformed

    private void btnClearSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSizeActionPerformed
        clearSizeGiay();
    }//GEN-LAST:event_btnClearSizeActionPerformed

    private void tblChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatLieuMouseClicked
        int vt = tblChatLieu.getSelectedRow();
        fillToFormChatLieu(vt);
    }//GEN-LAST:event_tblChatLieuMouseClicked

    private void btnThemChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChatLieuActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm chất liệu không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            ChatLieu cl = getChatLieu();
            if (cl == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceChatLieu.insertChatLieu(cl));
                listCL = serviceChatLieu.getAllChatLieu();
                fillToTableChatLieu(listCL);
            }
        }
    }//GEN-LAST:event_btnThemChatLieuActionPerformed

    private void btnSuaChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaChatLieuActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa chất liệu không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblChatLieu.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn chất liệu muốn sửa");
                return;
            } else {
                ChatLieu cl = getChatLieu();
                if (cl == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceChatLieu.updateChatLieu(cl));
                    listCL = serviceChatLieu.getAllChatLieu();
                    fillToTableChatLieu(listCL);
                }
            }
        }
    }//GEN-LAST:event_btnSuaChatLieuActionPerformed

    private void tblKieuDangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKieuDangMouseClicked
        int vt = tblKieuDang.getSelectedRow();
        fillToFormKieuDang(vt);
    }//GEN-LAST:event_tblKieuDangMouseClicked

    private void btnThemKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKieuDangActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm kiểu dáng không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            KieuDang kd = getKieuDang();
            if (kd == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceKieuDang.insertKieuDang(kd));
                listKD = serviceKieuDang.getAllKieuDang();
                fillToTableKieuDang(listKD);
            }
        }
    }//GEN-LAST:event_btnThemKieuDangActionPerformed

    private void btnSuaKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKieuDangActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa kiểu dáng không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblKieuDang.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn kiểu dáng muốn sửa");
                return;
            } else {
                KieuDang kd = getKieuDang();
                if (kd == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceKieuDang.updateKieuDang(kd));
                    listKD = serviceKieuDang.getAllKieuDang();
                    fillToTableKieuDang(listKD);
                }
            }
        }
    }//GEN-LAST:event_btnSuaKieuDangActionPerformed

    private void btnClearKieuDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearKieuDangActionPerformed
        clearKieuDang();
    }//GEN-LAST:event_btnClearKieuDangActionPerformed

    private void tblSanPhamMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMainMouseClicked
        int index = tblSanPhamMain.getSelectedRow();
        fillToFormSanPhamMain(index);
    }//GEN-LAST:event_tblSanPhamMainMouseClicked

    private void btnThemSanPhamMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamMainActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm sản phẩm không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            SanPhamMain spm = getSanPhamMain();
            if (spm == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceSPM.insertSanPhamMain(spm));
                listSPM = serviceSPM.getAllSanPhamMain();
                fillToTableSanPhamMain(listSPM);
            }
        }
    }//GEN-LAST:event_btnThemSanPhamMainActionPerformed

    private void btnSuaSanPhamMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamMainActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa sản phẩm không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblSanPhamMain.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn sản phẩm muốn sửa");
                return;
            } else {
                SanPhamMain spm = getSanPhamMain();
                if (spm == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceSPM.updateSanPhamMain(spm));
                    listSPM = serviceSPM.getAllSanPhamMain();
                    fillToTableSanPhamMain(listSPM);
                }
            }
        }
    }//GEN-LAST:event_btnSuaSanPhamMainActionPerformed

    private void btnDungBanSPMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDungBanSPMainActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn dừng bán sản phẩm không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblSanPhamMain.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn sản phẩm muốn dừng bán");
                return;
            } else {
                String maSanPham = txtMaSanPhamMain.getText();
                int soLuongSP = serviceSPM.getSoLuongSanPhamMain(maSanPham);
                System.out.println("soLuong :" + soLuongSP);
                if (soLuongSP > 0) {
                    int choice2 = JOptionPane.showConfirmDialog(this, "Sản phẩm này vẫn còn hàng. Bạn vẫn muốn xóa chứ?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (choice2 == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(this, serviceSPM.updatetSanPhamTrangThaiNgungBan(maSanPham));
                        listSPM = serviceSPM.getAllSanPhamMain();
                        fillToTableSanPhamMain(listSPM);
                    } else {
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, serviceSPM.updatetSanPhamTrangThaiNgungBan(maSanPham));
                listSPM = serviceSPM.getAllSanPhamMain();
                fillToTableSanPhamMain(listSPM);
            }
        }
    }//GEN-LAST:event_btnDungBanSPMainActionPerformed

    private void btnClearSanPhamMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSanPhamMainActionPerformed
        clearSanPhamMain();
    }//GEN-LAST:event_btnClearSanPhamMainActionPerformed

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        int index = tblSPCT.getSelectedRow();
        filltoFormSPCT(index);
    }//GEN-LAST:event_tblSPCTMouseClicked

    private void btnThemSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPCTActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm sản phẩm chi tiết không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            SanPhamChiTiet spct = getSanPhamChiTiet();
            if (spct == null) {
                JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, serviceSPCT.insertSPCT(spct));
                listSPCT = serviceSPCT.getAllSPCT();
                fillToTableSanPhamChiTiet(listSPCT);
            }
        }
    }//GEN-LAST:event_btnThemSPCTActionPerformed

    private void btnSuaSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPCTActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa sản phẩm chi tiết không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblSPCT.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn sản phẩm chi tiết muốn sửa");
                return;
            } else {
                SanPhamChiTiet spct = getSanPhamChiTiet();
                if (spct == null) {
                    JOptionPane.showMessageDialog(this, "Mời bạn thử lại!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, serviceSPCT.updateSPCT(spct));
                    listSPCT = serviceSPCT.getAllSPCT();
                    fillToTableSanPhamChiTiet(listSPCT);
                }
            }
        }
    }//GEN-LAST:event_btnSuaSPCTActionPerformed

    private void btnDungBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDungBanActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn dừng bán sản phẩm chi tiết này không?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            int vt = -1;
            vt = tblSPCT.getSelectedRow();
            if (vt < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn sản phẩm chi tiết muốn dừng bán");
                return;
            } else {
                SanPhamChiTiet spct = getSanPhamChiTiet();
                if (spct.getSoLuong() > 0) {
                    int choice2 = JOptionPane.showConfirmDialog(this, "Sản phẩm này đang còn hàng, bạn vẫn muốn xóa chứ?", "Xác nhận yêu cầu", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (choice2 == JOptionPane.YES_OPTION) {
                        String maSPCT = txtMaSPCT.getText();
                        JOptionPane.showMessageDialog(this, serviceSPCT.updateSPCT_TTDungBan(maSPCT));
                        listSPCT = serviceSPCT.getAllSPCT();
                        fillToTableSanPhamChiTiet(listSPCT);
                    } else {
                        return;
                    }
                }
                String maSPCT = txtMaSPCT.getText();
                JOptionPane.showMessageDialog(this, serviceSPCT.updateSPCT_TTDungBan(maSPCT));
                listSPCT = serviceSPCT.getAllSPCT();
                fillToTableSanPhamChiTiet(listSPCT);
            }
        }
    }//GEN-LAST:event_btnDungBanActionPerformed

    private void btnClearSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSPCTActionPerformed
        clearFormSanPhamChiTiet();
    }//GEN-LAST:event_btnClearSPCTActionPerformed

    private void pnlSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSanPhamMouseClicked
        openSanPham();
    }//GEN-LAST:event_pnlSanPhamMouseClicked

    private void pnlNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNhanVienMouseClicked
        openNhanVien();
    }//GEN-LAST:event_pnlNhanVienMouseClicked

    private void pnlDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDangXuatMouseClicked
        openDangXuat();
    }//GEN-LAST:event_pnlDangXuatMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Thành viên");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH SẢN PHẨM");

            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã SPCT");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Tên Sản Phẩm");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên màu");
            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue("Số Size");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Tên chất liệu");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Tên kiểu dáng");
            cell = row.createCell(7, CellType.NUMERIC);
            cell.setCellValue("Số lượng");
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Đơn giá");
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Ngày Tạo");
            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue("Ngày Sửa");
            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue("Trạng Thái");

            for (int i = 0; i < listSPCT.size(); i++) {
                SanPhamChiTiet spct = listSPCT.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(i + 1);
//                row.createCell(1).setCellValue(spct.getMaSPCT());
//                row.createCell(2).setCellValue(spct.getMaSanPham());
//                row.createCell(3).setCellValue(spct.getMaMau());
//                row.createCell(4).setCellValue(spct.getMaSize());
//                row.createCell(5).setCellValue(spct.getMaChatLieu());
                String tt = "";
                if (spct.getTrangThai() == 0) {
                    tt = "Hết hàng";
                } else if (spct.getTrangThai() == 1) {
                    tt = "Còn hàng";
                } else {
                    tt = "Đã dừng bán";
                }
                String tenSP = serviceSPM.getSanPhamMainbyMa(spct.getMaSanPham()).getTenSanPham();
                String tenMau = serviceMau.getMauSacByMa(spct.getMaMau()).getTenMau();
                int soSize = serviceSize.getSizeByMa(spct.getMaSize()).getSoSize();
                String tenChatLieu = serviceChatLieu.getChatLieuByMa(spct.getMaChatLieu()).getTenChatLieu();
                String tenKieuDang = serviceKieuDang.getKieuDangByMa(spct.getMaKieuDang()).getTenKieuDang();
                String donGia = currencyFormat.format(spct.getDonGia());
                String ngaySua = "";
                if (spct.getNgaySua() != null) {
                    ngaySua = spct.getNgaySua().toString();
                }
                row.createCell(1).setCellValue(spct.getMaSPCT());
                row.createCell(2).setCellValue(tenSP);
                row.createCell(3).setCellValue(tenMau);
                row.createCell(4).setCellValue(soSize);
                row.createCell(5).setCellValue(tenChatLieu);
                row.createCell(6).setCellValue(tenKieuDang);
                row.createCell(7).setCellValue(spct.getSoLuong());
                row.createCell(8).setCellValue(donGia);
                row.createCell(9).setCellValue(spct.getNgayTao().toString());
                row.createCell(10).setCellValue(ngaySua);
                row.createCell(11).setCellValue(tt);
            }

            FileOutputStream out = new FileOutputStream(new File("D:\\A_JAVA\\hv.xlsx"));
            workbook.write(out);
            out.close();
            JOptionPane.showMessageDialog(this, "Xuất file thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnDocFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocFileActionPerformed
        try {
            FileInputStream file = new FileInputStream(new File("D:\\A_JAVA\\hv.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            // Tạo danh sách để lưu trữ dữ liệu từ sheet Excel
            //List<SanPhamChiTiet> listSPCT = new ArrayList<>();
            // Lặp qua từng dòng trong sheet và thêm dữ liệu vào listSPCT
            for (Row row : sheet) {
                // Bỏ qua dòng đầu tiên vì đó có thể là tiêu đề
                if (row.getRowNum() <= 3) {
                    continue;
                }
                SanPhamChiTiet spct = new SanPhamChiTiet(); // Tạo đối tượng SanPhamChiTiet mới

                // Lặp qua từng ô trong dòng
                for (Cell cell : row) {
                    int columnIndex = cell.getColumnIndex();
                    // Xử lý giá trị của ô tại đây
                    switch (columnIndex) {
                        case 1:
                            spct.setMaSPCT(cell.getStringCellValue());
                            break;
                        case 2:
                            String tenSP = cell.getStringCellValue();
                            spct.setMaSanPham(serviceSPM.getSanPhamMainbyTen(tenSP).getMaSanPham());
                            break;
                        case 3:
                            String tenMau = cell.getStringCellValue();
                            spct.setMaMau(serviceMau.getMauSacByTen(tenMau).getMaMau());
                            break;
                        case 4:
                            int soSize = (int) cell.getNumericCellValue();
                            spct.setMaSize(serviceSize.getSizeBySoSize(soSize).getMaSize());
                            break;
                        case 5:
                            String tenCL = cell.getStringCellValue();
                            spct.setMaChatLieu(serviceChatLieu.getChatLieuByTen(tenCL).getMaChatLieu());
                            break;
                        case 6:
                            String tenKD = cell.getStringCellValue();
                            spct.setMaKieuDang(serviceKieuDang.getKieuDangByTen(tenKD).getMaKieuDang());
                            break;
                        case 7:
                            spct.setSoLuong((int) cell.getNumericCellValue());
                            break;
                        case 8:
                            String ss = cell.getStringCellValue();
                            double x = currencyFormat.parse(ss).doubleValue();
                            spct.setDonGia(x);
                            break;
                        case 9:
                            String ngayTao = cell.getStringCellValue();
                            Date dateNT = dateFormat.parse(ngayTao);
                            spct.setNgayTao(dateNT);
                            //System.out.println("ngày tạo: " + columnIndex + " " + dateNT);
                            break;
                        case 10:
                            if (cell.getStringCellValue().equals("")) {
                                break;
                            }
                            spct.setNgaySua(dateFormat.parse(cell.getStringCellValue()));
                            break;
                        case 11:
                            String trangThai = cell.getStringCellValue();
                            int tt;
                            if (trangThai.equalsIgnoreCase("Còn hàng")) {
                                tt = 1;
                            } else if (trangThai.equalsIgnoreCase("Đã dừng bán")) {
                                tt = 2;
                            } else {
                                tt = 0;
                            }
                            spct.setTrangThai(tt);
                            break;
                    }
                }

                // Thêm đối tượng SanPhamChiTiet vào danh sách
                listSPCT.add(spct);
            }

            // Đóng FileInputStream
            JOptionPane.showMessageDialog(this, "Lấy dữ liệu thành công");
            fillToTableSanPhamChiTiet(listSPCT);
            //Thêm dữ liệu vào database
//            for (SanPhamChiTiet spct : listSPCT) {
//                String s=serviceSPCT.insertSPCT(spct);
//            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDocFileActionPerformed

    private void pnlHoaDonGocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlHoaDonGocMouseClicked
        openHoaDon();
    }//GEN-LAST:event_pnlHoaDonGocMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        listMau = serviceMau.getAllMauSac();
        fillToTableMauSac(listMau);
        listSize = serviceSize.getAllSizeGiay();
        fillToTableSizeGiay(listSize);
        listCL = serviceChatLieu.getAllChatLieu();
        fillToTableChatLieu(listCL);
        listKD = serviceKieuDang.getAllKieuDang();
        fillToTableKieuDang(listKD);
        listTH = serviceThuongHieu.getAllThuongHieu();
        initComboboxThuongHieu(listTH);
        listSPM = serviceSPM.getAllSanPhamMain();
        fillToTableSanPhamMain(listSPM);
        initComBoBoxSanPham(listSPM);
        initComBoBoxMauSac(listMau);
        initComBoBoxSizeGiay(listSize);
        initComBoBoxChatLieu(listCL);
        initComBoBoxKieuDang(listKD);
        listSPCT = serviceSPCT.getAllSPCT();
        fillToTableSanPhamChiTiet(listSPCT);
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(SanPhamUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearChatLieu;
    private javax.swing.JButton btnClearKieuDang;
    private javax.swing.JButton btnClearMau;
    private javax.swing.JButton btnClearSPCT;
    private javax.swing.JButton btnClearSanPhamMain;
    private javax.swing.JButton btnClearSize;
    private javax.swing.JButton btnDocFile;
    private javax.swing.JButton btnDungBan;
    private javax.swing.JButton btnDungBanSPMain;
    private javax.swing.JButton btnSuaChatLieu;
    private javax.swing.JButton btnSuaKieuDang;
    private javax.swing.JButton btnSuaMau;
    private javax.swing.JButton btnSuaSPCT;
    private javax.swing.JButton btnSuaSanPhamMain;
    private javax.swing.JButton btnSuaSize;
    private javax.swing.JButton btnThemChatLieu;
    private javax.swing.JButton btnThemKieuDang;
    private javax.swing.JButton btnThemMau;
    private javax.swing.JButton btnThemSPCT;
    private javax.swing.JButton btnThemSanPhamMain;
    private javax.swing.JButton btnThemSize;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboKieuDang;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSanPhamMain;
    private javax.swing.JComboBox<String> cboSizeGiay;
    private javax.swing.JComboBox<String> cboThuongHieu;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlHoaDon;
    private javax.swing.JPanel pnlHoaDonGoc;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhuyenMai;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlSanPham;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JTable tblChatLieu;
    private javax.swing.JTable tblKieuDang;
    private javax.swing.JTable tblMauSac;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTable tblSanPhamMain;
    private javax.swing.JTable tblSizeGiay;
    private javax.swing.JTextField txtDoBaoHoa;
    private javax.swing.JTextField txtDoTuongPhan;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaChatLieu;
    private javax.swing.JTextField txtMaKieuDang;
    private javax.swing.JTextField txtMaMau;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtMaSanPhamMain;
    private javax.swing.JTextField txtMaSize;
    private javax.swing.JTextArea txtMoTaCL;
    private javax.swing.JTextArea txtMoTaKD;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoSize;
    private javax.swing.JTextField txtTenChatLieu;
    private javax.swing.JTextField txtTenKieuDang;
    private javax.swing.JTextField txtTenMau;
    private javax.swing.JTextField txtTenSanPhamMain;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
