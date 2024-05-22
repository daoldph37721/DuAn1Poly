package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.GioHangChiTiet;
import model.HoaDon;
import model.HoaDonChiTiet;

public class HoaDonChiTietRepo {

    public List<HoaDonChiTiet> getAllHDCT1() {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,TenSanPham,HoaDonChiTiet.SoLuong,DonGia,HoaDonChiTiet.SoLuong*DonGia\n"
                    + "FROM HoaDonChiTiet JOIN SanPhamChiTiet ON HoaDonChiTiet.MaCTSP=SanPhamChiTiet.MaSPCT\n"
                    + "				   JOIN SanPham ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "WHERE HoaDonChiTiet.MaHoaDon=(SELECT MAX(MaHoaDon) FROM HoaDon);";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSPCT(rs.getString(1));
                hdct.setTenSanPham(rs.getNString(2));
                hdct.setSoLuong(rs.getInt(3));
                hdct.setDonGia(rs.getDouble(4));
                hdct.setThanhTien(rs.getDouble(5));
                listHDCT.add(hdct);
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
        return listHDCT;
    }

    public List<HoaDonChiTiet> getAllHDCT2(int MaHoaDon) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,TenSanPham,HoaDonChiTiet.SoLuong,DonGia,HoaDonChiTiet.SoLuong*DonGia\n"
                    + "FROM HoaDonChiTiet JOIN SanPhamChiTiet ON HoaDonChiTiet.MaCTSP=SanPhamChiTiet.MaSPCT\n"
                    + "				   JOIN SanPham ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "WHERE HoaDonChiTiet.MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, MaHoaDon);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSPCT(rs.getString(1));
                hdct.setTenSanPham(rs.getNString(2));
                hdct.setSoLuong(rs.getInt(3));
                hdct.setDonGia(rs.getDouble(4));
                hdct.setThanhTien(rs.getDouble(5));
                listHDCT.add(hdct);
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
        return listHDCT;
    }

    public int InsertHoaDonChiTiet(int MaHoaDon, String MaSPCT, int SoLuong) {
        HoaDonChiTiet hdct = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT INTO HoaDonChiTiet (MaHoaDon, MaCTSP, SoLuong, GiaHienHanh)\n"
                    + "VALUES (?, ?, ?, (SELECT DonGia FROM SanPhamChiTiet WHERE MaSPCT = ?));";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, MaHoaDon);
            sttm.setString(2, MaSPCT);
            sttm.setInt(3, SoLuong);
            sttm.setString(4, MaSPCT);
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

    public int UpdateHDCT_SoLuong(int MaHoaDon, String MaSPCT, int SoLuong) {
        HoaDonChiTiet hdct = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE HoaDonChiTiet SET SoLuong=SoLuong+?\n"
                    + "				WHERE MaHoaDon=? AND MaCTSP=?;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, SoLuong);
            sttm.setInt(2, MaHoaDon);
            sttm.setString(3, MaSPCT);
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
    
    public int DeleteHDCT(int MaHoaDon, String MaSPCT) {
        HoaDonChiTiet hdct = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "DELETE HoaDonChiTiet WHERE MaHoaDon=? AND MaCTSP=?;";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, MaHoaDon);
            sttm.setString(2, MaSPCT);
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
    
    public int DeleteAllHDCT(int MaHoaDon) {
        HoaDonChiTiet hdct = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "DELETE HoaDonChiTiet WHERE MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, MaHoaDon);
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

    public List<HoaDonChiTiet> getAllHDCTTheoMa(int MaHoaDon) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM HoaDonChiTiet WHERE MaHoaDon=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, MaHoaDon);
            rs = sttm.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSPCT(rs.getString(2));
                hdct.setSoLuong(rs.getInt(3));
                listHDCT.add(hdct);
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
        return listHDCT;
    }

}
