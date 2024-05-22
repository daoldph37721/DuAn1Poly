
package model;

import java.util.Date;

public class SanPhamChiTiet {
    private String MaSPCT;
    private String MaSanPham;
    private String MaMau;
    private String MaSize;
    private String MaChatLieu;
    private String MaKieuDang;
    private int SoLuong;
    private double DonGia;
    private Date NgayTao;
    private Date NgaySua;
    private int TrangThai;

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(String MaSPCT, String MaSanPham, String MaMau, String MaSize, String MaChatLieu, String MaKieuDang, int SoLuong, double DonGia, Date NgayTao, Date NgaySua, int TrangThai) {
        this.MaSPCT = MaSPCT;
        this.MaSanPham = MaSanPham;
        this.MaMau = MaMau;
        this.MaSize = MaSize;
        this.MaChatLieu = MaChatLieu;
        this.MaKieuDang = MaKieuDang;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.TrangThai = TrangThai;
    }

    public String getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(String MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setMaMau(String MaMau) {
        this.MaMau = MaMau;
    }

    public String getMaSize() {
        return MaSize;
    }

    public void setMaSize(String MaSize) {
        this.MaSize = MaSize;
    }

    public String getMaChatLieu() {
        return MaChatLieu;
    }

    public void setMaChatLieu(String MaChatLieu) {
        this.MaChatLieu = MaChatLieu;
    }

    public String getMaKieuDang() {
        return MaKieuDang;
    }

    public void setMaKieuDang(String MaKieuDang) {
        this.MaKieuDang = MaKieuDang;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
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
    
    
}
