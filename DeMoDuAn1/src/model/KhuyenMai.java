
package model;

import java.util.Date;

public class KhuyenMai {
    private String MaKhuyenMai;
    private String TenKhuyenMai;
    private int SoHoaDonDaApDung;
    private int HinhThuc;
    private double MucGiamGia;
    private Date ThoiGianBatDau;
    private Date ThoiGianKetThuc;
    private String MoTa;
    private Date NgayTao;
    private Date NgaySua;
    private int TrangThai;
    private double SoTienMin;
    private int SoLuong;
    
    public KhuyenMai() {
    }

    public KhuyenMai(String MaKhuyenMai, String TenKhuyenMai, int SoHoaDonDaApDung, int HinhThuc, double MucGiamGia, Date ThoiGianBatDau, Date ThoiGianKetThuc, String MoTa, Date NgayTao, Date NgaySua, int TrangThai, double SoTienMin, int SoLuong) {
        this.MaKhuyenMai = MaKhuyenMai;
        this.TenKhuyenMai = TenKhuyenMai;
        this.SoHoaDonDaApDung = SoHoaDonDaApDung;
        this.HinhThuc = HinhThuc;
        this.MucGiamGia = MucGiamGia;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
        this.MoTa = MoTa;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.TrangThai = TrangThai;
        this.SoTienMin = SoTienMin;
        this.SoLuong = SoLuong;
    }

    public String getMaKhuyenMai() {
        return MaKhuyenMai;
    }

    public void setMaKhuyenMai(String MaKhuyenMai) {
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return TenKhuyenMai;
    }

    public void setTenKhuyenMai(String TenKhuyenMai) {
        this.TenKhuyenMai = TenKhuyenMai;
    }

    public int getSoHoaDonDaApDung() {
        return SoHoaDonDaApDung;
    }

    public void setSoHoaDonDaApDung(int SoHoaDonDaApDung) {
        this.SoHoaDonDaApDung = SoHoaDonDaApDung;
    }

    public int getHinhThuc() {
        return HinhThuc;
    }

    public void setHinhThuc(int HinhThuc) {
        this.HinhThuc = HinhThuc;
    }

    public double getMucGiamGia() {
        return MucGiamGia;
    }

    public void setMucGiamGia(double MucGiamGia) {
        this.MucGiamGia = MucGiamGia;
    }

    public Date getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(Date ThoiGianBatDau) {
        this.ThoiGianBatDau = ThoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return ThoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date ThoiGianKetThuc) {
        this.ThoiGianKetThuc = ThoiGianKetThuc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
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

    public double getSoTienMin() {
        return SoTienMin;
    }

    public void setSoTienMin(double SoTienMin) {
        this.SoTienMin = SoTienMin;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }
    
    
}
