package service;

import java.util.List;
import model.KhuyenMai;
import repository.KhuyenMaiRepository;

public class KhuyenMaiService {

    KhuyenMaiRepository khuyenMaiRepo = new KhuyenMaiRepository();

    public List<KhuyenMai> getAllKhuyenMai() {
        return khuyenMaiRepo.getAllKhuyenMai();
    }

    public List<KhuyenMai> getAllKhuyenMaiTheoSoHDAP() {
        return khuyenMaiRepo.getAllKhuyenMaiTheoSoHDAP();
    }
    
    public List<KhuyenMai> getAllKhuyenMaiTheoNgayKetThuc() {
        return khuyenMaiRepo.getAllKhuyenMaiTheoNgayKetThuc();
    }
    
    public List<KhuyenMai> getAllKhuyenMaiByMa(String MaKhuyenMai) {
        return khuyenMaiRepo.getAllKhuyenMaiByMa(MaKhuyenMai);
    }

    public List<KhuyenMai> getAllKhuyenMaiByTrangThai(int TrangThai) {
        return khuyenMaiRepo.getAllKhuyenMaiByTrangThai(TrangThai);
    }
    
    public List<KhuyenMai> getAllKhuyenMaiTheo_SoHDAP_TT(int TrangThai) {
        return khuyenMaiRepo.getAllKhuyenMaiTheo_SoHDAP_TT(TrangThai);
    }
    
    public List<KhuyenMai> getAllKhuyenMaiTheo_NgayKetThuc_TT(int TrangThai) {
        return khuyenMaiRepo.getAllKhuyenMaiTheo_NgayKetThuc_TT(TrangThai);
    }
    
    public String InsertKhuyenMai(KhuyenMai km) {
        int x = khuyenMaiRepo.InsertKhuyenMai(km);
        if (x >= 0) {
            return "Thêm thành công";
        } else {
            return "Thêm thất bại";
        }
    }

    public String UpdateKhuyenMai(KhuyenMai km) {
        int x = khuyenMaiRepo.UpdateKhuyenMai(km);
        if (x >= 0) {
            return "Cập nhật thành công";
        } else {
            return "Cập nhật thất bại";
        }
    }

    public void UpdateTrangThaiKM(KhuyenMai km) {
        int x = khuyenMaiRepo.UpdateTrangThaiKM(km);
    }
    
    public void UpdateSoLuongKhuyenMai(KhuyenMai km) {
        int x=khuyenMaiRepo.UpdateSoLuongKhuyenMai(km);
    }
    
    public String UpdateTrangThaiKMTrucTiep(String MaKhuyenMai) {
        int x=khuyenMaiRepo.UpdateTrangThaiKMTrucTiep(MaKhuyenMai);
        if (x >= 0) {
            return "Kết thúc khuyến mãi thành công";
        } else {
            return "Kết thúc khuyến mãi thất bại";
        }
    }
    
}
