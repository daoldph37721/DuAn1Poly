
package model;

import java.util.Date;

public class KhachHang {
    private int MaKhachHang;
    private String HoTen;
    private String SDT;
    private String DiaChi;
    private Date NgayTao;
    private int TrangThai;
    private int SoDonHang;
    private double TongTien;
    
    public KhachHang() {
    }

    public KhachHang(int MaKhachHang, String HoTen, String SDT, String DiaChi, Date NgayTao, int TrangThai) {
        this.MaKhachHang = MaKhachHang;
        this.HoTen = HoTen;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
    }

    public KhachHang(int MaKhachHang, String HoTen, String SDT, String DiaChi, Date NgayTao, int TrangThai, int SoDonHang, double TongTien) {
        this.MaKhachHang = MaKhachHang;
        this.HoTen = HoTen;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
        this.NgayTao = NgayTao;
        this.TrangThai = TrangThai;
        this.SoDonHang = SoDonHang;
        this.TongTien = TongTien;
    }

    
    
    public int getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(int MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getSoDonHang() {
        return SoDonHang;
    }

    public void setSoDonHang(int SoDonHang) {
        this.SoDonHang = SoDonHang;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    
}
