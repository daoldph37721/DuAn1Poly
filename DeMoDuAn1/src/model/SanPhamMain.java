
package model;

import java.util.Date;

public class SanPhamMain {
    private String MaSanPham;
    private String TenSanPham;
    private String XuatXu;
    private Date NgayTao;
    private Date NgaySua;
    private int TrangThai;
    private String MaThuongHieu;

    public SanPhamMain() {
    }

    public SanPhamMain(String MaSanPham, String TenSanPham, String XuatXu, Date NgayTao, Date NgaySua, int TrangThai, String MaThuongHieu) {
        this.MaSanPham = MaSanPham;
        this.TenSanPham = TenSanPham;
        this.XuatXu = XuatXu;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.TrangThai = TrangThai;
        this.MaThuongHieu = MaThuongHieu;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String XuatXu) {
        this.XuatXu = XuatXu;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public Date getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(Date NgaySua) {
        this.NgaySua = NgaySua;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaThuongHieu() {
        return MaThuongHieu;
    }

    public void setMaThuongHieu(String MaThuongHieu) {
        this.MaThuongHieu = MaThuongHieu;
    }
    
}
