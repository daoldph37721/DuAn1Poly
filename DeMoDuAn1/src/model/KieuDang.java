
package model;

public class KieuDang {
    private String MaKieuDang;
    private String TenKieuDang;
    private String MoTa;
    private int TrangThai;

    public KieuDang() {
    }

    public KieuDang(String MaKieuDang, String TenKieuDang, String MoTa, int TrangThai) {
        this.MaKieuDang = MaKieuDang;
        this.TenKieuDang = TenKieuDang;
        this.MoTa = MoTa;
        this.TrangThai = TrangThai;
    }

    public String getMaKieuDang() {
        return MaKieuDang;
    }

    public void setMaKieuDang(String MaKieuDang) {
        this.MaKieuDang = MaKieuDang;
    }

    public String getTenKieuDang() {
        return TenKieuDang;
    }

    public void setTenKieuDang(String TenKieuDang) {
        this.TenKieuDang = TenKieuDang;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
