
package model;

public class MauSac {
    private String MaMau;
    private String TenMau;
    private int DoTuongPhan;
    private int DoBaoHoa;
    private int TrangThai;

    public MauSac() {
    }

    public MauSac(String MaMau, String TenMau, int DoTuongPhan, int DoBaoHoa, int TrangThai) {
        this.MaMau = MaMau;
        this.TenMau = TenMau;
        this.DoTuongPhan = DoTuongPhan;
        this.DoBaoHoa = DoBaoHoa;
        this.TrangThai = TrangThai;
    }

    public String getMaMau() {
        return MaMau;
    }

    public void setMaMau(String MaMau) {
        this.MaMau = MaMau;
    }

    public String getTenMau() {
        return TenMau;
    }

    public void setTenMau(String TenMau) {
        this.TenMau = TenMau;
    }

    public int getDoTuongPhan() {
        return DoTuongPhan;
    }

    public void setDoTuongPhan(int DoTuongPhan) {
        this.DoTuongPhan = DoTuongPhan;
    }

    public int getDoBaoHoa() {
        return DoBaoHoa;
    }

    public void setDoBaoHoa(int DoBaoHoa) {
        this.DoBaoHoa = DoBaoHoa;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
