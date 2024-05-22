
package model;

public class SizeGiay {
    private String MaSize;
    private int SoSize;
    private int TrangThai;

    public SizeGiay() {
    }

    public SizeGiay(String MaSize, int SoSize, int TrangThai) {
        this.MaSize = MaSize;
        this.SoSize = SoSize;
        this.TrangThai = TrangThai;
    }

    public String getMaSize() {
        return MaSize;
    }

    public void setMaSize(String MaSize) {
        this.MaSize = MaSize;
    }

    public int getSoSize() {
        return SoSize;
    }

    public void setSoSize(int SoSize) {
        this.SoSize = SoSize;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
