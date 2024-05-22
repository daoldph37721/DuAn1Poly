package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.SanPham;

public class SanPhamRepository {

    public List<SanPham> getAllSanPham() {
        List<SanPham> listSP = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SanPhamChiTiet.MaSPCT,SanPham.TenSanPham,ThuongHieu.TenThuongHieu, MauSac.TenMau, ChatLieu.TenChatLieu,\n"
                    + "	KieuDang.TenKieuDang,SizeGiay.SoSize,SoLuong,DonGia\n"
                    + " FROM SanPham  JOIN ThuongHieu ON SanPham.MaThuongHieu=ThuongHieu.MaThuongHieu\n"
                    + "	JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "	JOIN MauSac ON SanPhamChiTiet.MaMau=MauSac.MaMau\n"
                    + "	JOIN KieuDang ON SanPhamChiTiet.MaKieuDang=KieuDang.MaKieuDang\n"
                    + "	JOIN ChatLieu ON SanPhamChiTiet.MaChatLieu=ChatLieu.MaChatLieu\n"
                    + "	JOIN SizeGiay ON SanPhamChiTiet.MaSize=SizeGiay.MaSize\n"
                    + "WHERE SanPhamChiTiet.TrangThai=1";
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
                    + "       WHERE TenSanPham like '%"+TenSanPham+"%' AND SanPhamChiTiet.TrangThai=1";
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

    public int UpdateSoLuong(int SoLuong, String MaSPCT) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPhamChiTiet SET SoLuong=SoLuong-? WHERE MaSPCT=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, SoLuong);
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

    public int UpdateSoLuongVe(int SoLuong, String MaSPCT) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPhamChiTiet SET SoLuong=SoLuong+? WHERE MaSPCT=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, SoLuong);
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

    public SanPham getSanPhamByMa(String MaSPCT) {
        SanPham sp = new SanPham();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SanPhamChiTiet WHERE MaSPCT=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaSPCT);
            rs = sttm.executeQuery();
            while (rs.next()) {
                sp.setSoLuong(rs.getInt(7));
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
        return sp;
    }
}
