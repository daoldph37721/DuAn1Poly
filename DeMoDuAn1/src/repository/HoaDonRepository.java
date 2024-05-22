package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.SanPham;

public class HoaDonRepository {

    public int InsertHoaDon(HoaDon hd) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT INTO HoaDon(MaNV,MaKH,TrangThai,NgayTao) "
                    + "VALUES(?,?,0,GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, hd.getMaNhanVien());
            sttm.setInt(2, hd.getMaKhachHang());
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

    public int InsertHoaDonKHnull(HoaDon hd) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT INTO HoaDon(MaNV,TrangThai,NgayTao) "
                    + "VALUES(?,0,GETDATE());";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, hd.getMaNhanVien());
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

    public int UpdateHoaDon(double TongTien, double GiamGia, double ThanhTien, int MaHoaDon) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE HoaDon SET TongTien=?,GiamGia=?,ThanhTien=? \n"
                    + "WHERE MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setDouble(1, TongTien);
            sttm.setDouble(2, GiamGia);
            sttm.setDouble(3, ThanhTien);
            sttm.setInt(4, MaHoaDon);
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

    public List<HoaDon> getAllHoaDonCho() {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon WHERE TrangThai=0\n"
                    + "ORDER BY MaHoaDon DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setNgayTao(rs.getDate(6));
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

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> listHD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDon\n"
                    + "ORDER BY MaHoaDon DESC;";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt(1));
                hd.setMaNhanVien(rs.getString(2));
                hd.setMaKhachHang(rs.getInt(3));
                hd.setNgayTao(rs.getDate(6));
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

    public int UpdateTrangThai(int TrangThai, int MaHoaDon) {

        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE HoaDon SET TrangThai=? WHERE MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            sttm.setInt(2, MaHoaDon);
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

    public int UpdateHoaDonTT(HoaDon hd) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE HoaDon SET TrangThai=1, MaKhuyenMai=?,\n"
                    + "MaHTTT=?,NgayThanhToan=GETDATE(),\n"
                    + "TongTien=?,GiamGia=?,ThanhTien=?\n"
                    + "WHERE MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, hd.getMaKhuyenMai());
            sttm.setString(2, hd.getMaHTTT());
            sttm.setDouble(3, hd.getTongTien());
            sttm.setDouble(4, hd.getGiamGia());
            sttm.setDouble(5, hd.getThanhTien());
            sttm.setInt(6, hd.getMaHoaDon());
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
}
