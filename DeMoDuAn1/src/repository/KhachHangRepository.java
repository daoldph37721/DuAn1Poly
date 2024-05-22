package repository;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import model.KhuyenMai;

public class KhachHangRepository {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhachHang";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                listKH.add(kh);
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
        return listKH;
    }

    public int InsertKhachHang(KhachHang kh) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT INTO KhachHang(HoTen,SoDienThoai,DiaChi,TrangThai,NgayTao)"
                    + " VALUES(?,?,?,1,GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, kh.getHoTen());
            sttm.setString(2, kh.getSDT());
            sttm.setString(3, kh.getDiaChi());
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int UpdateKhachHang(KhachHang kh) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE KhachHang SET HoTen=?,SoDienThoai=?,DiaChi=?\n"
                    + "WHERE MaKH=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, kh.getHoTen());
            sttm.setString(2, kh.getSDT());
            sttm.setString(3, kh.getDiaChi());
            sttm.setInt(4, kh.getMaKhachHang());
            return sttm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sttm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<KhachHang> getAllKhachHangTheoTen(String hoTen) {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhachHang\n"
                    + "WHERE HoTen like '%" + hoTen + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                listKH.add(kh);
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
        return listKH;
    }

    public List<KhachHang> getAllKhachHangTheoSDT(String SDT) {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhachHang\n"
                    + "WHERE SoDienThoai like '%" + SDT + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                listKH.add(kh);
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
        return listKH;
    }

    public List<KhachHang> getAllKhachHangTheoMa(int maKH) {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhachHang\n"
                    + "WHERE MaKH=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, maKH);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                listKH.add(kh);
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
        return listKH;
    }

    public List<KhachHang> getAllKhachHangSapXepTheoNgayTao() {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhachHang\n"
                    + "ORDER BY NgayTao DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                listKH.add(kh);
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
        return listKH;
    }

    public List<KhachHang> getAllKhachHangSXTheoSoHoaDon() {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT KhachHang.*, COUNT(HoaDon.MaHoaDon) AS SoLuongHoaDon, SUM(ThanhTien)\n"
                    + "FROM KhachHang\n"
                    + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\n"
                    + "WHERE HoaDon.TrangThai=1\n"
                    + "GROUP BY KhachHang.MaKH, HoTen, SoDienThoai, DiaChi ,KhachHang.TrangThai, KhachHang.NgayTao\n"
                    + "ORDER BY COUNT(HoaDon.MaHoaDon) DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                kh.setSoDonHang(rs.getInt(7));
                kh.setTongTien(rs.getDouble(8));
                listKH.add(kh);
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
        return listKH;
    }

    public List<KhachHang> getAllKhachHangSXTheoTongTien() {
        List<KhachHang> listKH = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT KhachHang.*, COUNT(HoaDon.MaHoaDon) AS SoLuongHoaDon, SUM(ThanhTien)\n"
                    + "FROM KhachHang\n"
                    + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\n"
                    + "WHERE HoaDon.TrangThai=1\n"
                    + "GROUP BY KhachHang.MaKH, HoTen, SoDienThoai, DiaChi ,KhachHang.TrangThai, KhachHang.NgayTao\n"
                    + "ORDER BY SUM(ThanhTien) DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(rs.getInt(1));
                kh.setHoTen(rs.getNString(2));
                kh.setSDT(rs.getString(3));
                kh.setDiaChi(rs.getNString(4));
                kh.setTrangThai(rs.getInt(5));
                kh.setNgayTao(rs.getDate(6));
                kh.setSoDonHang(rs.getInt(7));
                kh.setTongTien(rs.getDouble(8));
                listKH.add(kh);
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
        return listKH;
    }
}
