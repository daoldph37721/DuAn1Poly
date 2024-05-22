package service;

import java.util.List;
import model.SizeGiay;
import repository.SizeGiayRepository;

public class SizeGiayService {

    SizeGiayRepository sizeRepo = new SizeGiayRepository();

    public List<SizeGiay> getAllSizeGiay() {
        return sizeRepo.getAllSizeGiay();
    }

    public String InsertSizeGiay(SizeGiay size) {
        int x = sizeRepo.InsertSizeGiay(size);
        if (x >= 0) {
            return "Thêm size giày thành công";
        }
        return "Thêm size giày thất bại";
    }

    public String UpdateSizeGiay(SizeGiay size) {
        int x = sizeRepo.UpdateSizeGiay(size);
        if (x >= 0) {
            return "Cập nhật size giày thành công";
        }
        return "Cập nhật size giày thất bại";
    }
    
    public SizeGiay getSizeByMa(String MaSize) {
        return sizeRepo.getSizeByMa(MaSize);
    }
    
    public SizeGiay getSizeBySoSize(int SoSize) {
        return sizeRepo.getSizeBySoSize(SoSize);
    }
}
