package service;

import java.util.Date;
import java.util.List;
import model.HoaDon;
import model.SanPham;
import repository.ThongKeRepository;

public class ThongKeService {

    ThongKeRepository thongKeRepo = new ThongKeRepository();

    public List<HoaDon> getAllHoaDon() {
        return thongKeRepo.getAllHoaDon();
    }

    public List<HoaDon> getAllHoaDonTheoKhoangNgay(Date TimeBegin, Date TimeEnd) {
        return thongKeRepo.getAllHoaDonTheoKhoangNgay(TimeBegin, TimeEnd);
    }

    public List<HoaDon> getAllHoaDonSapXepTheoThanhTien() {
        return thongKeRepo.getAllHoaDonSapXepTheoThanhTien();
    }

    public List<HoaDon> getAllHoaDonSapXepTheoNgayTao() {
        return thongKeRepo.getAllHoaDonSapXepTheoNgayTao();
    }

    public List<HoaDon> getAllHoaDonTheoTrangThai(int TrangThai) {
        return thongKeRepo.getAllHoaDonTheoTrangThai(TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_Tien_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        return thongKeRepo.getAllHoaDonTheo_Ngay_Tien_TT(TimeBegin, TimeEnd, TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_NgayTao_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        return thongKeRepo.getAllHoaDonTheo_Ngay_NgayTao_TT(TimeBegin, TimeEnd, TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        return thongKeRepo.getAllHoaDonTheo_Ngay_TT(TimeBegin, TimeEnd, TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_Tien_TT(int TrangThai) {
        return thongKeRepo.getAllHoaDonTheo_Tien_TT(TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_NgayTao_TT(int TrangThai) {
        return thongKeRepo.getAllHoaDonTheo_NgayTao_TT(TrangThai);
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_NgayTao(Date TimeBegin, Date TimeEnd) {
        return thongKeRepo.getAllHoaDonTheo_Ngay_NgayTao(TimeBegin, TimeEnd);
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_Tien(Date TimeBegin, Date TimeEnd) {
        return thongKeRepo.getAllHoaDonTheo_Ngay_Tien(TimeBegin, TimeEnd);
    }

    public List<SanPham> getAllSanPham() {
        return thongKeRepo.getAllSanPham();
    }

    public List<SanPham> getSearchSanPham(String TenSanPham) {
        return thongKeRepo.getSearchSanPham(TenSanPham);
    }

    public List<SanPham> getAllSanPhamSapXepTheoDonGia() {
        return thongKeRepo.getAllSanPhamSapXepTheoDonGia();
    }

    public List<SanPham> getAllSanPhamSapXepTheoSoLuong() {
        return thongKeRepo.getAllSanPhamSapXepTheoSoLuong();
    }

    public List<SanPham> getAllSanPhambyTrangThai(int TrangThai) {
        return thongKeRepo.getAllSanPhambyTrangThai(TrangThai);
    }

    public List<SanPham> getAllSanPhamTheo_DonGia_TT(int TrangThai) {
        return thongKeRepo.getAllSanPhamTheo_DonGia_TT(TrangThai);
    }

    public List<SanPham> getAllSanPhamTheo_SoLuong_TT(int TrangThai) {
        return thongKeRepo.getAllSanPhamTheo_SoLuong_TT(TrangThai);
    }

    public double getDoanhThuTheoNgay(Date timeBegin, Date TimeOver) {
        return thongKeRepo.getDoanhThuTheoNgay(timeBegin, TimeOver);
    }

    public int getSoDonTong(Date timeBegin, Date TimeOver) {
        return thongKeRepo.getSoDonTong(timeBegin, TimeOver);
    }

    public int getSoDonCho(Date timeBegin, Date TimeOver) {
        return thongKeRepo.getSoDonCho(timeBegin, TimeOver);
    }

    public int getSoDonDaThanhToan(Date timeBegin, Date TimeOver) {
        return thongKeRepo.getSoDonDaThanhToan(timeBegin, TimeOver);
    }

    public int getSoDonDaHuy(Date timeBegin, Date TimeOver) {
        return thongKeRepo.getSoDonDaHuy(timeBegin, TimeOver);
    }

    public double getDoanhThuTheoNgayHienTai() {
        return thongKeRepo.getDoanhThuTheoNgayHienTai();
    }

    public double getDoanhThuTheoThangHienTai() {
        return thongKeRepo.getDoanhThuTheoThangHienTai();
    }

    public double getDoanhThuTheoNamHienTai() {
        return thongKeRepo.getDoanhThuTheoNamHienTai();
    }

    public double getDoanhThuTheoNgay(Date ngatTT) {
        return thongKeRepo.getDoanhThuTheoNgay(ngatTT);
    }

    public int getSoLuongSPBanTheoNgay(Date ngayTT) {
        return thongKeRepo.getSoLuongSPBanTheoNgay(ngayTT);
    }

    public double getDoanhThuTheoThang(int thang) {
        return thongKeRepo.getDoanhThuTheoThang(thang);
    }
    
    public int getSoLuongSPBanTheoThang(int thang) {
        return thongKeRepo.getSoLuongSPBanTheoThang(thang);
    }
}
