
package model;

public class SanPham {
    private String MaSanPham;
    private String MaSPCT;
    private String TenSanPham;
    private String ThuongHieu;
    private String MauSac;
    private String ChatLieu;
    private String KieuDang;
    private int Size;
    private int SoLuong;
    private double DonGia;
    private int TrangThai;

    public SanPham() {
    }

    public SanPham(String MaSanPham, String MaSPCT, String TenSanPham, String ThuongHieu, String MauSac, String ChatLieu, String KieuDang, int Size, int SoLuong, double DonGia, int TrangThai) {
        this.MaSanPham = MaSanPham;
        this.MaSPCT = MaSPCT;
        this.TenSanPham = TenSanPham;
        this.ThuongHieu = ThuongHieu;
        this.MauSac = MauSac;
        this.ChatLieu = ChatLieu;
        this.KieuDang = KieuDang;
        this.Size = Size;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.TrangThai = TrangThai;
    }

    public SanPham(String MaSanPham, String MaSPCT, String TenSanPham, String ThuongHieu, String MauSac, String ChatLieu, String KieuDang, int Size, int SoLuong, double DonGia) {
        this.MaSanPham = MaSanPham;
        this.MaSPCT = MaSPCT;
        this.TenSanPham = TenSanPham;
        this.ThuongHieu = ThuongHieu;
        this.MauSac = MauSac;
        this.ChatLieu = ChatLieu;
        this.KieuDang = KieuDang;
        this.Size = Size;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
    }

    public SanPham(String TenSanPham, String ThuongHieu, String MauSac, String ChatLieu, String KieuDang, int Size, int SoLuong, double DonGia) {
        this.TenSanPham = TenSanPham;
        this.ThuongHieu = ThuongHieu;
        this.MauSac = MauSac;
        this.ChatLieu = ChatLieu;
        this.KieuDang = KieuDang;
        this.Size = Size;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(String MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String ThuongHieu) {
        this.ThuongHieu = ThuongHieu;
    }

    public String getMauSac() {
        return MauSac;
    }

    public void setMauSac(String MauSac) {
        this.MauSac = MauSac;
    }

    public String getChatLieu() {
        return ChatLieu;
    }

    public void setChatLieu(String ChatLieu) {
        this.ChatLieu = ChatLieu;
    }

    public String getKieuDang() {
        return KieuDang;
    }

    public void setKieuDang(String KieuDang) {
        this.KieuDang = KieuDang;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }
    
    public Object[] fillToRow(){
        return new Object[]{TenSanPham,ThuongHieu,MauSac,ChatLieu,KieuDang,Size,SoLuong,DonGia};
    }
    
    public Object[] fillToRow2(){
        String tt="";
        if(TrangThai==0){
            tt="Hết hàng";
        }else{
            tt="Còn hàng";
        }
        return new Object[]{TenSanPham,ThuongHieu,MauSac,ChatLieu,KieuDang,Size,SoLuong,DonGia,tt};
    }
}
