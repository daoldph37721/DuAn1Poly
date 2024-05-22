
package service;

import java.util.List;
import model.MauSac;
import repository.MauSacRepository;

public class MauSacService {
    
    MauSacRepository mauRepo=new MauSacRepository();
    
    public List<MauSac> getAllMauSac() {
        return mauRepo.getAllMauSac();
    }
    
    public String InsertMauSac(MauSac mau) {
        int x=mauRepo.InsertMauSac(mau);
        if(x>=0){
            return "Thêm màu thành công";
        }else{
            return "Thêm màu thất bại";
        }
    }
    
    public String UpdateMauSac(MauSac mau) {
        int x=mauRepo.UpdateMauSac(mau);
        if(x>=0){
            return "Cập nhật màu thành công";
        }else{
            return "Cập nhật màu thất bại";
        }
    }
    
    public MauSac getMauSacByMa(String MaMau) {
        return mauRepo.getMauSacByMa(MaMau);
    }
    
    public MauSac getMauSacByTen(String TenMau) {
        return mauRepo.getMauSacByTen(TenMau);
    }
}
