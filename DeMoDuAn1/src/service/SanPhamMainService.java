
package service;

import java.util.List;
import model.SanPhamMain;
import repository.SanPhamMainRepository;


public class SanPhamMainService {
    
    SanPhamMainRepository spmRepository=new SanPhamMainRepository();
    
    public List<SanPhamMain> getAllSanPhamMain() {
        return spmRepository.getAllSanPhamMain();
    }
    
    public String insertSanPhamMain(SanPhamMain spm) {
        int x=spmRepository.insertSanPhamMain(spm);
        if(x>=0){
            return "Thêm sản phẩm thành công";
        }
        return "Thêm sản phẩm thất bại";
    }
    
    public String updateSanPhamMain(SanPhamMain spm) {
        int x=spmRepository.updateSanPhamMain(spm);
        if(x>=0){
            return "Cập nhật sản phẩm thành công";
        }
        return "Cập nhật sản phẩm thất bại";
    }
    
    public String updatetSanPhamTrangThaiNgungBan(String maSanPham) {
        int x=spmRepository.updatetSanPhamTrangThaiNgungBan(maSanPham);
        if(x>=0){
            return "Cập nhật dừng bán thành công";
        }
        return "Cập nhật dừng bán thất bại";
    }
    
    public SanPhamMain getSanPhamMainbyMa(String MaSanPham) {
        return spmRepository.getSanPhamMainbyMa(MaSanPham);
    }
    
    public SanPhamMain getSanPhamMainbyTen(String TenSanPham) {
        return spmRepository.getSanPhamMainbyTen(TenSanPham);
    }
    
    public int getSoLuongSanPhamMain(String MaSanPham) {
        return spmRepository.getSoLuongSanPhamMain(MaSanPham);
    }
}
