
package service;

import java.util.List;
import model.ThuongHieu;
import repository.ThuongHieuRepository;

public class ThuongHieuService {
    
    ThuongHieuRepository thuongHieuRepo=new ThuongHieuRepository();
    
    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepo.getAllThuongHieu();
    }
    
    public ThuongHieu getThuongHieubyMa(String MaThuongHieu) {
        return thuongHieuRepo.getThuongHieubyMa(MaThuongHieu);
    }
    
    public ThuongHieu getThuongHieubyTen(String TenThuongHieu) {
        return thuongHieuRepo.getThuongHieubyTen(TenThuongHieu);
    }
    
}
