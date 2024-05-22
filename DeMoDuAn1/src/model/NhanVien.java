
package model;

import java.util.Date;

public class NhanVien {
    private String MaNV;
    private String MatKhau;
    private String HoTen;
    private int GioiTinh;
    private String DiaChi;
    private String SDT;
    private String CCCD;
    private Date NgayTao;
    private Date NgaySua;
    private Date NgayVaoLam;
    private Date NgayNghi;
    private int TrangThai;
    private int SoHoaDon;
    private double DoanhThu;
    private String email;

    public NhanVien(String MaNV, String MatKhau, String HoTen, int GioiTinh, String DiaChi, String SDT, String CCCD, Date NgayTao, Date NgaySua, Date NgayVaoLam, Date NgayNghi, int TrangThai, int SoHoaDon, double DoanhThu, String email) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.NgayVaoLam = NgayVaoLam;
        this.NgayNghi = NgayNghi;
        this.TrangThai = TrangThai;
        this.SoHoaDon = SoHoaDon;
        this.DoanhThu = DoanhThu;
        this.email = email;
    }
    
    

    public NhanVien() {
    }

    public NhanVien(String MaNV, String MatKhau, String HoTen, int GioiTinh, String DiaChi, String SDT, String CCCD, Date NgayTao, Date NgaySua, Date NgayVaoLam, Date NgayNghi, int TrangThai) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.NgayVaoLam = NgayVaoLam;
        this.NgayNghi = NgayNghi;
        this.TrangThai = TrangThai;
    }

    public NhanVien(String MaNV, String MatKhau, String HoTen, int GioiTinh, String DiaChi, String SDT, String CCCD, Date NgayTao, Date NgaySua, Date NgayVaoLam, Date NgayNghi, int TrangThai, int SoHoaDon, double DoanhThu) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.NgayVaoLam = NgayVaoLam;
        this.NgayNghi = NgayNghi;
        this.TrangThai = TrangThai;
        this.SoHoaDon = SoHoaDon;
        this.DoanhThu = DoanhThu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
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

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public Date getNgayNghi() {
        return NgayNghi;
    }

    public void setNgayNghi(Date NgayNghi) {
        this.NgayNghi = NgayNghi;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getSoHoaDon() {
        return SoHoaDon;
    }

    public void setSoHoaDon(int SoHoaDon) {
        this.SoHoaDon = SoHoaDon;
    }

    public double getDoanhThu() {
        return DoanhThu;
    }

    public void setDoanhThu(double DoanhThu) {
        this.DoanhThu = DoanhThu;
    }

    
}
