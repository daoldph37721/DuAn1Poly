package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.KieuDang;
import model.SanPhamChiTiet;

public class SanPhamChiTietRepo {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<SanPhamChiTiet> getAllSPCT() {
        List<SanPhamChiTiet> listSPCT = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SanPhamChiTiet";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setMaSPCT(rs.getString(1));
                spct.setMaSanPham(rs.getString(2));
                spct.setMaMau(rs.getString(3));
                spct.setMaSize(rs.getString(4));
                spct.setMaChatLieu(rs.getString(5));
                spct.setMaKieuDang(rs.getString(6));
                spct.setSoLuong(rs.getInt(7));
                spct.setDonGia(rs.getDouble(8));
                spct.setNgayTao(rs.getDate(9));
                spct.setNgaySua(rs.getDate(10));
                spct.setTrangThai(rs.getInt(11));
                listSPCT.add(spct);
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
        return listSPCT;
    }

    public int insertSPCT(SanPhamChiTiet spct) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT SanPhamChiTiet(MaSPCT,MaSanPham,MaMau,MaSize,MaChatLieu,MaKieuDang,\n"
                    + "SoLuong,DonGia,NgayTao,NgaySua,TrangThai)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,GETDATE(),NULL,1)";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, spct.getMaSPCT());
            sttm.setString(2, spct.getMaSanPham());
            sttm.setString(3, spct.getMaMau());
            sttm.setString(4, spct.getMaSize());
            sttm.setString(5, spct.getMaChatLieu());
            sttm.setString(6, spct.getMaKieuDang());
            sttm.setInt(7, spct.getSoLuong());
            sttm.setDouble(8, spct.getDonGia());
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

    public int updateSPCT(SanPhamChiTiet spct) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPhamChiTiet SET MaSanPham=?,MaMau=?,\n"
                    + "MaSize=?,MaChatLieu=?,MaKieuDang=?,\n"
                    + "SoLuong=?,DonGia=?,NgaySua=GETDATE()\n"
                    + "WHERE MaSPCT=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, spct.getMaSanPham());
            sttm.setString(2, spct.getMaMau());
            sttm.setString(3, spct.getMaSize());
            sttm.setString(4, spct.getMaChatLieu());
            sttm.setString(5, spct.getMaKieuDang());
            sttm.setInt(6, spct.getSoLuong());
            sttm.setDouble(7, spct.getDonGia());
            sttm.setString(8, spct.getMaSPCT());
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

    public int updateSPCT_TTDungBan(String maSPCT) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPhamChiTiet SET NgaySua=GETDATE(),TrangThai=2\n"
                    + "WHERE MaSPCT=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, maSPCT);
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
