
package service;

import java.util.List;
import model.HoaDon;
import repository.HoaDonRepository;

public class HoaDonService {
    HoaDonRepository hdRepo=new HoaDonRepository();
    
    public void InsertHoaDon(HoaDon hd) {
         int x=hdRepo.InsertHoaDon(hd);
    }
    
    public void InsertHoaDonKHnull(HoaDon hd) {
        int x=hdRepo.InsertHoaDonKHnull(hd);
    }
    
    public void UpdateHoaDon(double TongTien,double GiamGia,double ThanhTien,int MaHoaDon) {
        int x=hdRepo.UpdateHoaDon(TongTien, GiamGia, ThanhTien, MaHoaDon);
    }
    
    public List<HoaDon> getDSHoaDonCho(){
        return hdRepo.getAllHoaDonCho();
    }
    
    public List<HoaDon> getAllHoaDon() {
        return hdRepo.getAllHoaDon();
    }
    
    public void UpdateTrangThai(int TrangThai,int MaHoaDon){
        int x=hdRepo.UpdateTrangThai(TrangThai, MaHoaDon);
    }
    public void UpdateHoaDonTT(HoaDon hd) {
        int x=hdRepo.UpdateHoaDonTT(hd);
    }
}
