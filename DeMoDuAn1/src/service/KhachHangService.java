
package service;

import java.util.List;
import model.KhachHang;
import repository.KhachHangRepository;

public class KhachHangService {
    KhachHangRepository KhachHangRepo=new KhachHangRepository();
    
    public List<KhachHang> getAllKhachHang() {
        return KhachHangRepo.getAllKhachHang();
    }
    
    public String InsertKhachHang(KhachHang kh) {
        int x = KhachHangRepo.InsertKhachHang(kh);
        if(x<0){
            return "Thêm mới thất bại";
        }else{
            return "Thêm mới thành công";
        }
    }
    
    public String UpdateKhachHang(KhachHang kh) {
        int x = KhachHangRepo.UpdateKhachHang(kh);
        if(x<0){
            return "Cập nhật thất bại";
        }else{
            return "Cập nhật thành công";
        }
    }
    
    public List<KhachHang> getAllKhachHangTheoMa(int maKH) {
        return KhachHangRepo.getAllKhachHangTheoMa(maKH);
    }
    
    public List<KhachHang> getAllKhachHangTheoTen(String hoTen) {
        return KhachHangRepo.getAllKhachHangTheoTen(hoTen);
    }
    
    public List<KhachHang> getAllKhachHangTheoSDT(String SDT) {
        return KhachHangRepo.getAllKhachHangTheoSDT(SDT);
    }
    
    public List<KhachHang> getAllKhachHangSapXepTheoNgayTao() {
        return KhachHangRepo.getAllKhachHangSapXepTheoNgayTao();
    }
    
    public List<KhachHang> getAllKhachHangSXTheoSoHoaDon() {
        return KhachHangRepo.getAllKhachHangSXTheoSoHoaDon();
    }
    
    public List<KhachHang> getAllKhachHangSXTheoTongTien() {
        return KhachHangRepo.getAllKhachHangSXTheoTongTien();
    }
}
