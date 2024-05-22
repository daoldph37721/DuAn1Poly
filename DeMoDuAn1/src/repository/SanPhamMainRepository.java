package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KieuDang;
import model.SanPhamMain;

public class SanPhamMainRepository {

    public List<SanPhamMain> getAllSanPhamMain() {
        List<SanPhamMain> listSPM = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SanPham";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SanPhamMain spm = new SanPhamMain();
                spm.setMaSanPham(rs.getString(1));
                spm.setTenSanPham(rs.getNString(2));
                spm.setXuatXu(rs.getNString(3));
                spm.setNgayTao(rs.getDate(4));
                spm.setNgaySua(rs.getDate(5));
                spm.setTrangThai(rs.getInt(6));
                spm.setMaThuongHieu(rs.getString(7));
                listSPM.add(spm);
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
        return listSPM;
    }

    public int insertSanPhamMain(SanPhamMain spm) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT SanPham(MaSanPham,TenSanPham,XuatXu,NgayTao,"
                    + "NgaySua,TrangThai,MaThuongHieu)\n"
                    + "VALUES(?,?,?,GETDATE(),NULL,1,?)";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, spm.getMaSanPham());
            sttm.setString(2, spm.getTenSanPham());
            sttm.setString(3, spm.getXuatXu());
            sttm.setString(4, spm.getMaThuongHieu());
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

    public int updateSanPhamMain(SanPhamMain spm) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPham SET TenSanPham=?,XuatXu=?,"
                    + "NgaySua=GETDATE(),MaThuongHieu=?\n"
                    + "WHERE MaSanPham=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, spm.getTenSanPham());
            sttm.setString(2, spm.getXuatXu());
            sttm.setString(3, spm.getMaThuongHieu());
            sttm.setString(4, spm.getMaSanPham());
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

    public int updatetSanPhamTrangThaiNgungBan(String maSanPham) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SanPham SET TrangThai=0,NgaySua=GETDATE()\n"
                    + "WHERE MaSanPham=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, maSanPham);
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

    public SanPhamMain getSanPhamMainbyMa(String MaSanPham) {
        SanPhamMain spm = new SanPhamMain();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SanPham\n"
                    + "WHERE MaSanPham=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaSanPham);
            rs = sttm.executeQuery();
            while (rs.next()) {
                spm.setMaSanPham(rs.getString(1));
                spm.setTenSanPham(rs.getNString(2));
                spm.setXuatXu(rs.getNString(3));
                spm.setNgayTao(rs.getDate(4));
                spm.setNgaySua(rs.getDate(5));
                spm.setTrangThai(rs.getInt(6));
                spm.setMaThuongHieu(rs.getString(7));
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
        return spm;
    }

    public SanPhamMain getSanPhamMainbyTen(String TenSanPham) {
        SanPhamMain spm = new SanPhamMain();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SanPham\n"
                    + "WHERE TenSanPham=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, TenSanPham);
            rs = sttm.executeQuery();
            while (rs.next()) {
                spm.setMaSanPham(rs.getString(1));
                spm.setTenSanPham(rs.getNString(2));
                spm.setXuatXu(rs.getNString(3));
                spm.setNgayTao(rs.getDate(4));
                spm.setNgaySua(rs.getDate(5));
                spm.setTrangThai(rs.getInt(6));
                spm.setMaThuongHieu(rs.getString(7));
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
        return spm;
    }

    public int getSoLuongSanPhamMain(String MaSanPham) {
        int SoLuong =0;
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT SUM(SanPhamChiTiet.SoLuong) FROM SanPham JOIN SanPhamChiTiet ON SanPham.MaSanPham=SanPhamChiTiet.MaSanPham\n"
                    + "WHERE SanPham.MaSanPham=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaSanPham);
            rs = sttm.executeQuery();
            while (rs.next()) {
                SoLuong=rs.getInt(1); 
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
        return SoLuong;
    }
}
