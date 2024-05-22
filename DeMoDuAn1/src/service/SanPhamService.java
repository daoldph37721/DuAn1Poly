package service;

import java.util.List;
import model.SanPham;
import repository.SanPhamRepository;

public class SanPhamService {

    SanPhamRepository SanPhamRepo = new SanPhamRepository();

    public List<SanPham> getAllSanPham() {
        return SanPhamRepo.getAllSanPham();
    }
    
    public List<SanPham> getSearchSanPham(String TenSanPham) {
        return SanPhamRepo.getSearchSanPham(TenSanPham);
    }

    public void UpdateSoLuong(int SoLuong, String MaSPCT) {
        int x = SanPhamRepo.UpdateSoLuong(SoLuong, MaSPCT);
    }

    public void UpdateSoLuongVe(int SoLuong, String MaSPCT) {
        int x = SanPhamRepo.UpdateSoLuongVe(SoLuong, MaSPCT);
    }
    
    public SanPham getSanPhamByMa(String MaSPCT){
        return SanPhamRepo.getSanPhamByMa(MaSPCT);
    }
}
