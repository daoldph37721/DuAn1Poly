
package model;

public class HTTH {
    private String MaHTTT;
    private String TenHTTT;
    private int TrangThai;

    public HTTH() {
    }

    public HTTH(String MaHTTT, String TenHTTT, int TrangThai) {
        this.MaHTTT = MaHTTT;
        this.TenHTTT = TenHTTT;
        this.TrangThai = TrangThai;
    }

    public String getMaHTTT() {
        return MaHTTT;
    }

    public void setMaHTTT(String MaHTTT) {
        this.MaHTTT = MaHTTT;
    }

    public String getTenHTTT() {
        return TenHTTT;
    }

    public void setTenHTTT(String TenHTTT) {
        this.TenHTTT = TenHTTT;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
}
