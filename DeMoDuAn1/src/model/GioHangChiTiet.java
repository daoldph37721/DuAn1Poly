
package model;

public class GioHangChiTiet {
    private int MaGioHang;
    private String MaSPCT;
    private int SoLuong;
    private int TrangThai;
    private String TenSanPham;
    private double DonGia;
    private double ThanhTien;

    public GioHangChiTiet() {
    }

    public GioHangChiTiet(int MaGioHang, String MaSPCT, int SoLuong, int TrangThai) {
        this.MaGioHang = MaGioHang;
        this.MaSPCT = MaSPCT;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
    }

    public GioHangChiTiet(String MaSPCT, int SoLuong, String TenSanPham, double DonGia, double ThanhTien) {
        this.MaSPCT = MaSPCT;
        this.SoLuong = SoLuong;
        this.TenSanPham = TenSanPham;
        this.DonGia = DonGia;
        this.ThanhTien = ThanhTien;
    }

    public GioHangChiTiet(int MaGioHang, String MaSPCT, int SoLuong, int TrangThai, String TenSanPham, double DonGia, double ThanhTien) {
        this.MaGioHang = MaGioHang;
        this.MaSPCT = MaSPCT;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.TenSanPham = TenSanPham;
        this.DonGia = DonGia;
        this.ThanhTien = ThanhTien;
    }

    public int getMaGioHang() {
        return MaGioHang;
    }

    public void setMaGioHang(int MaGioHang) {
        this.MaGioHang = MaGioHang;
    }

    public String getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(String MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(double ThanhTien) {
        this.ThanhTien = ThanhTien;
    }
    
    
    
}
