
package model;

public class ThuongHieu {
    private String MaThuongHieu;
    private String TenThuongHieu;
    private int TrangThai;

    public ThuongHieu() {
    }

    public ThuongHieu(String MaThuongHieu, String TenThuongHieu, int TrangThai) {
        this.MaThuongHieu = MaThuongHieu;
        this.TenThuongHieu = TenThuongHieu;
        this.TrangThai = TrangThai;
    }

    public String getMaThuongHieu() {
        return MaThuongHieu;
    }

    public void setMaThuongHieu(String MaThuongHieu) {
        this.MaThuongHieu = MaThuongHieu;
    }

    public String getTenThuongHieu() {
        return TenThuongHieu;
    }

    public void setTenThuongHieu(String TenThuongHieu) {
        this.TenThuongHieu = TenThuongHieu;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
}
