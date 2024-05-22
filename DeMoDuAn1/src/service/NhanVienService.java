package service;

import java.util.List;
import model.NhanVien;
import repository.NhanVienRepository;

public class NhanVienService {

    NhanVienRepository nvRepo = new NhanVienRepository();

    public List<NhanVien> getAllNhanVien() {
        return nvRepo.getAllNhanVien();
    }

    public String InsertNhanVien(NhanVien nv) {
        int x = nvRepo.InsertNhanVien(nv);
        if (x >= 0) {
            return "thêm mới thành công";
        } else {
            return "thêm mới thất bại";
        }
    }

    public String UpdateNhanVien(NhanVien nv) {
        int x = nvRepo.UpdateNhanVien(nv);
        if (x >= 0) {
            return "Cập nhật thành công";
        } else {
            return "Cập nhật thất bại";
        }
    }

    public String UpdateNVNghiViec(String maNV) {
        int x = nvRepo.UpdateNVNghiViec(maNV);
        if (x >= 0) {
            return "Cập nhật nghỉ việc thành công";
        } else {
            return "Cập nhật nghỉ việc thất bại";
        }
    }
    
    public String UpdateMatKhauTheoEmail(String matKhau, String email) {
        int x=nvRepo.UpdateMatKhauTheoEmail(matKhau, email);
        if (x >= 0) {
            return "Cập nhật mật khẩu thành công";
        } else {
            return "Cập nhật mật khẩu thất bại";
        }
    }
    
    public int checkNhanVienTKTheoMaVaMatKhau(String maNV,String matKhau) {
        return nvRepo.checkNhanVienTKTheoMaVaMatKhau(maNV, matKhau);
    }
    
    public List<NhanVien> getAllNhanVienTKTheoTen(String hoTen) {
        return nvRepo.getAllNhanVienTKTheoTen(hoTen);
    }
    
    public List<NhanVien> getAllNhanVienTKTheoSDT(String SDT) {
        return nvRepo.getAllNhanVienTKTheoSDT(SDT);
    }
    
    public List<NhanVien> getAllNhanVienTKTheoCCCD(String CCCD) {
        return nvRepo.getAllNhanVienTKTheoCCCD(CCCD);
    }
    
    public List<NhanVien> getAllNhanVienSXTheo_SoHoaDon() {
        return nvRepo.getAllNhanVienSXTheo_SoHoaDon();
    }
    
    public List<NhanVien> getAllNhanVienSXTheo_DoanhThu() {
        return nvRepo.getAllNhanVienSXTheo_DoanhThu();
    }
    
    public List<NhanVien> getAllNhanVienTheoTrangThai(int TrangThai) {
        return nvRepo.getAllNhanVienTheoTrangThai(TrangThai);
    }
    
    public List<NhanVien> getAllNhanVienSXTheo_SoHoDon_TT(int TrangThai) {
        return nvRepo.getAllNhanVienSXTheo_SoHoDon_TT(TrangThai);
    }
    
    public List<NhanVien> getAllNhanVienSXTheo_DoanhThu_TT(int TrangThai) {
        return nvRepo.getAllNhanVienSXTheo_DoanhThu_TT(TrangThai);
    }
}
