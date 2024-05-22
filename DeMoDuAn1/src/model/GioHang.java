
package model;

import java.util.Date;

public class GioHang {
    private int MaGioHang;
    private int MaKhachHang;
    private Date NgayTao;
    private Date NgaySua;
    private int TrangThai;

    public GioHang() {
    }

    public GioHang(int MaGioHang, int MaKhachHang, Date NgayTao, Date NgaySua) {
        this.MaGioHang = MaGioHang;
        this.MaKhachHang = MaKhachHang;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
    }

    public GioHang(int MaGioHang, int MaKhachHang, Date NgayTao, Date NgaySua, int TrangThai) {
        this.MaGioHang = MaGioHang;
        this.MaKhachHang = MaKhachHang;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.TrangThai = TrangThai;
    }

    public int getMaGioHang() {
        return MaGioHang;
    }

    public void setMaGioHang(int MaGioHang) {
        this.MaGioHang = MaGioHang;
    }

    public int getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(int MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
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
