package service;

import java.util.List;
import model.HoaDonChiTiet;
import repository.HoaDonChiTietRepo;

public class HoaDonChiTietService {

    HoaDonChiTietRepo HDCTRepo = new HoaDonChiTietRepo();

    public void InsertHDCT(int MaHoaDon, String MaSPCT, int SoLuong) {
        int x = HDCTRepo.InsertHoaDonChiTiet(MaHoaDon, MaSPCT, SoLuong);
    }

    public List<HoaDonChiTiet> getAllHDCT1() {
        return HDCTRepo.getAllHDCT1();
    }

    public List<HoaDonChiTiet> getAllHDCT2(int MaHoaDon) {
        return HDCTRepo.getAllHDCT2(MaHoaDon);
    }

    public List<HoaDonChiTiet> getAllHDChiTietTheoMa(int MaHoaDon) {
        return HDCTRepo.getAllHDCTTheoMa(MaHoaDon);
    }

    public void UpdateHDCT_SoLuong(int MaHoaDon, String MaSPCT, int SoLuong) {
        int x = HDCTRepo.UpdateHDCT_SoLuong(MaHoaDon, MaSPCT, SoLuong);
    }

    public void DeleteHDCT(int MaHoaDon, String MaSPCT) {
        int x = HDCTRepo.DeleteHDCT(MaHoaDon, MaSPCT);
    }

    public void DeleteAllHDCT(int MaHoaDon) {
        int x=HDCTRepo.DeleteAllHDCT(MaHoaDon);
    }
}
