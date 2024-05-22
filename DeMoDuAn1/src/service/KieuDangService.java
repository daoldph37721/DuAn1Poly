
package service;

import java.util.List;
import model.KieuDang;
import repository.KieuDangRepository;

public class KieuDangService {
    KieuDangRepository kieuDangRepo=new KieuDangRepository();
    
    public List<KieuDang> getAllKieuDang() {
        return kieuDangRepo.getAllKieuDang();
    }
    
    public String insertKieuDang(KieuDang kd) {
        int x=kieuDangRepo.insertKieuDang(kd);
        if(x>=0){
            return "Thêm mới kiểu dáng thành công";
        }
        return "Thêm mới kiểu dáng thất bại";
    }
    
    public String updateKieuDang(KieuDang kd) {
        int x=kieuDangRepo.updateKieuDang(kd);
        if(x>=0){
            return "Cập nhật kiểu dáng thành công";
        }
        return "Cập nhật kiểu dáng thất bại";
    }
    
    public KieuDang getKieuDangByMa(String MaKieuDang) {
        return kieuDangRepo.getKieuDangByMa(MaKieuDang);
    }
    
    public KieuDang getKieuDangByTen(String TenKieuDang) {
        return kieuDangRepo.getKieuDangByTen(TenKieuDang);
    }
}
