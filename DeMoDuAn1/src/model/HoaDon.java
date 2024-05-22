
package model;

import java.util.Date;

public class HoaDon {
    private int MaHoaDon;
    private String MaNhanVien;
    private int MaKhachHang;
    private String MaKhuyenMai;
    private String MaHTTT;
    private Date NgayTao;
    private Date NgaySua;
    private Date NgayThanhToan;
    private double TongTien;
    private double GiamGia;
    private double ThanhTien;
    private int TrangThai;

    public HoaDon() {
    }

    public HoaDon(int MaHoaDon, String MaNhanVien, int MaKhachHang, String MaKhuyenMai, String MaHTTT, Date NgayTao, Date NgaySua, Date NgayThanhToan, double TongTien, double GiamGia, double ThanhTien, int TrangThai) {
        this.MaHoaDon = MaHoaDon;
        this.MaNhanVien = MaNhanVien;
        this.MaKhachHang = MaKhachHang;
        this.MaKhuyenMai = MaKhuyenMai;
        this.MaHTTT = MaHTTT;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.NgayThanhToan = NgayThanhToan;
        this.TongTien = TongTien;
        this.GiamGia = GiamGia;
        this.ThanhTien = ThanhTien;
        this.TrangThai = TrangThai;
    }


    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public int getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(int MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
    }

    public String getMaKhuyenMai() {
        return MaKhuyenMai;
    }

    public void setMaKhuyenMai(String MaKhuyenMai) {
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public String getMaHTTT() {
        return MaHTTT;
    }

    public void setMaHTTT(String MaHTTT) {
        this.MaHTTT = MaHTTT;
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

    public Date getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(Date NgayThanhToan) {
        this.NgayThanhToan = NgayThanhToan;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public double getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(double GiamGia) {
        this.GiamGia = GiamGia;
    }

    public double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(double ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    
}
