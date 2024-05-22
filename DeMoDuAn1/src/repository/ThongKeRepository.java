package repository;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.SanPham;
import java.util.Date;

public class ThongKeRepository {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "ORDER BY NgayTao DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheoKhoangNgay(Date TimeBegin, Date TimeEnd) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao>=? AND NgayTao<=?\n"
                    + "ORDER BY NgayTao DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonSapXepTheoThanhTien() {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "ORDER BY ThanhTien DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonSapXepTheoNgayTao() {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "ORDER BY NgayTao ASC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheoTrangThai(int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE TrangThai=?\n"
                    + "ORDER BY NgayTao DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_Tien_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao >= ? AND NgayTao <= ? AND TrangThai=?\n"
                    + "ORDER BY ThanhTien DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            sttm.setInt(3, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_NgayTao_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao >= ? AND NgayTao <= ? AND TrangThai=?\n"
                    + "ORDER BY NgayTao ASC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            sttm.setInt(3, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_TT(Date TimeBegin, Date TimeEnd, int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao >= ? AND NgayTao <= ? AND TrangThai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            sttm.setInt(3, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_NgayTao(Date TimeBegin, Date TimeEnd) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao >= ? AND NgayTao <= ?\n"
                    + "ORDER BY NgayTao ASC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Ngay_Tien(Date TimeBegin, Date TimeEnd) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE NgayTao >= ? AND NgayTao <= ?\n"
                    + "ORDER BY ThanhTien DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(TimeBegin));
            sttm.setString(2, dateFormat.format(TimeEnd));
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_Tien_TT(int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE TrangThai = ?\n"
                    + "ORDER BY ThanhTien DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> getAllHoaDonTheo_NgayTao_TT(int TrangThai) {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "WHERE TrangThai = ?\n"
                    + "ORDER BY NgayTao ASC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setMaKhuyenMai(rs.getString(4));
                hd.setMaHTTT(rs.getString(5));
                hd.setNgayTao(rs.getDate(6));
                hd.setNgaySua(rs.getDate(7));
                hd.setNgayThanhToan(rs.getDate(8));
                hd.setTongTien(rs.getDouble(9));
                hd.setGiamGia(rs.getDouble(10));
                hd.setThanhTien(rs.getDouble(11));
                hd.setTrangThai(rs.getInt(12));
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "	KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "	JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "	JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "	JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "	JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "	JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getSearchSanPham(String TenSanPham) {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,"
                    + "ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + " KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "  JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + " JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "    JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "      JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "          JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + "       WHERE TenSanPham like '%" + TenSanPham + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
//            sttm.setString(1,TenSanPham);
            rs = sttm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getAllSanPhamSapXepTheoDonGia() {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "	KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "	JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "	JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "	JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "	JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "	JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + " ORDER BY SanPhamChiTiet.DonGia DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getAllSanPhamSapXepTheoSoLuong() {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "	KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "	JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "	JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "	JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "	JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "	JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + " ORDER BY SanPhamChiTiet.SoLuong DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getAllSanPhambyTrangThai(int TrangThai) {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "	KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "	JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "	JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "	JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "	JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "	JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + " WHERE SanPhamChiTiet.TrangThai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getAllSanPhamTheo_DonGia_TT(int TrangThai) {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + "FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + "WHERE SanPhamChiTiet.TrangThai=?\n"
                    + "ORDER BY SanPhamChiTiet.DonGia DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public List<SanPham> getAllSanPhamTheo_SoLuong_TT(int TrangThai) {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia,SanPhamChiTiet.TrangThai\n"
                    + "FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + "WHERE SanPhamChiTiet.TrangThai=?\n"
                    + "ORDER BY SanPhamChiTiet.SoLuong DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSPCT(rs.getString(1));
                sp.setTenSanPham(rs.getNString(2));
                sp.setThuongHieu(rs.getNString(3));
                sp.setMauSac(rs.getNString(4));
                sp.setChatLieu(rs.getNString(5));
                sp.setKieuDang(rs.getString(6));
                sp.setSize(rs.getInt(7));
                sp.setSoLuong(rs.getInt(8));
                sp.setDonGia(rs.getDouble(9));
                sp.setTrangThai(rs.getInt(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }

    public double getDoanhThuTheoNgay(Date timeBegin, Date TimeOver) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien) AS TongThanhTien\n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai=1 AND NgayThanhToan>=? AND NgayThanhToan<=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(timeBegin));
            sttm.setString(2, dateFormat.format(TimeOver));
            rs = sttm.executeQuery();
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public int getSoDonTong(Date timeBegin, Date TimeOver) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soDonTong = 0;
        try {
            String sSQL = "SELECT COUNT(MaHoaDon) AS TongDon\n"
                    + "FROM HoaDon\n"
                    + "WHERE NgayTao>=? AND NgayTao<=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(timeBegin));
            sttm.setString(2, dateFormat.format(TimeOver));
            rs = sttm.executeQuery();
            while (rs.next()) {
                soDonTong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soDonTong;
    }

    public int getSoDonCho(Date timeBegin, Date TimeOver) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soDonCho = 0;
        try {
            String sSQL = "SELECT COUNT(CASE WHEN TrangThai = 0 THEN 1 END) AS SoHoaDonCho \n"
                    + "FROM HoaDon\n"
                    + "WHERE NgayTao>=? AND NgayTao<=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(timeBegin));
            sttm.setString(2, dateFormat.format(TimeOver));
            rs = sttm.executeQuery();
            while (rs.next()) {
                soDonCho = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soDonCho;
    }

    public int getSoDonDaThanhToan(Date timeBegin, Date TimeOver) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soDonDaThanhToan = 0;
        try {
            String sSQL = "SELECT COUNT(CASE WHEN TrangThai = 1 THEN 1 END) AS SoHoaDonCho \n"
                    + "FROM HoaDon\n"
                    + "WHERE NgayTao>=? AND NgayTao<=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(timeBegin));
            sttm.setString(2, dateFormat.format(TimeOver));
            rs = sttm.executeQuery();
            while (rs.next()) {
                soDonDaThanhToan = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soDonDaThanhToan;
    }

    public int getSoDonDaHuy(Date timeBegin, Date TimeOver) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soDonDaHuy = 0;
        try {
            String sSQL = "SELECT COUNT(CASE WHEN TrangThai = 2 THEN 1 END) AS SoHoaDonCho \n"
                    + "FROM HoaDon\n"
                    + "WHERE NgayTao>=? AND NgayTao<=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(timeBegin));
            sttm.setString(2, dateFormat.format(TimeOver));
            rs = sttm.executeQuery();
            while (rs.next()) {
                soDonDaHuy = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soDonDaHuy;
    }

    public double getDoanhThuTheoNgayHienTai() {
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien) AS TongThanhTien\n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai = 1 AND "
                    + "CONVERT(DATE, NgayThanhToan) = CONVERT(DATE, GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public double getDoanhThuTheoThangHienTai() {
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien) AS TongThanhTien\n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai = 1 AND "
                    + "DATEPART(MONTH, NgayThanhToan) = DATEPART(MONTH, GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public double getDoanhThuTheoNamHienTai() {
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien) AS TongThanhTien\n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai = 1 AND "
                    + "DATEPART(YEAR, NgayThanhToan) = DATEPART(YEAR, GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public double getDoanhThuTheoNgay(Date ngatTT) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien) \n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai=1 AND NgayThanhToan=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(ngatTT));
            rs = sttm.executeQuery();
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public int getSoLuongSPBanTheoNgay(Date ngayTT) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soLuongSP = 0;
        try {
            String sSQL = "SELECT  SUM(HoaDonChiTiet.SoLuong)\n"
                    + "FROM HoaDon\n"
                    + "INNER JOIN HoaDonChiTiet ON HoaDon.MaHoaDon = HoaDonChiTiet.MaHoaDon\n"
                    + "WHERE HoaDon.TrangThai=1 AND HoaDon.NgayThanhToan = ?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, dateFormat.format(ngayTT));
            rs = sttm.executeQuery();
            while (rs.next()) {
                soLuongSP = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuongSP;
    }

    public double getDoanhThuTheoThang(int thang) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        double doanhThu = 0;
        try {
            String sSQL = "SELECT SUM(ThanhTien)\n"
                    + "FROM HoaDon\n"
                    + "WHERE TrangThai=1 AND DATEPART(MONTH, HoaDon.NgayThanhToan) = ?;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, thang);
            rs = sttm.executeQuery();
            while (rs.next()) {
                doanhThu = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public int getSoLuongSPBanTheoThang(int thang) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        int soLuongSP = 0;
        try {
            String sSQL = "SELECT  SUM(HoaDonChiTiet.SoLuong)\n"
                    + "FROM HoaDon\n"
                    + "INNER JOIN HoaDonChiTiet ON HoaDon.MaHoaDon = HoaDonChiTiet.MaHoaDon\n"
                    + "WHERE HoaDon.TrangThai=1 AND DATEPART(MONTH, HoaDon.NgayThanhToan) = ?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, thang);
            rs = sttm.executeQuery();
            while (rs.next()) {
                soLuongSP = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rs.close();
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuongSP;
    }
}
