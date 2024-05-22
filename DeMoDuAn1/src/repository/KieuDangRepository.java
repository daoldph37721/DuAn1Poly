package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ChatLieu;
import model.KieuDang;

public class KieuDangRepository {

    public List<KieuDang> getAllKieuDang() {
        List<KieuDang> listKD = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KieuDang";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KieuDang kd = new KieuDang();
                kd.setMaKieuDang(rs.getString(1));
                kd.setTenKieuDang(rs.getNString(2));
                kd.setMoTa(rs.getNString(3));
                kd.setTrangThai(rs.getInt(4));
                listKD.add(kd);
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
        return listKD;
    }

    public int insertKieuDang(KieuDang kd) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT KieuDang(MaKieuDang,TenKieuDang,MoTaKD,TrangThai) VALUES(?,?,?,1);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, kd.getMaKieuDang());
            sttm.setString(2, kd.getTenKieuDang());
            sttm.setString(3, kd.getMoTa());
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

    public int updateKieuDang(KieuDang kd) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE KieuDang SET TenKieuDang=?,MoTaKD=?\n"
                    + "WHERE MaKieuDang=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, kd.getTenKieuDang());
            sttm.setString(2, kd.getMoTa());
            sttm.setString(3, kd.getMaKieuDang());
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

    public KieuDang getKieuDangByMa(String MaKieuDang) {
        KieuDang kd = new KieuDang();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KieuDang\n"
                    + "WHERE MaKieuDang=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaKieuDang);
            rs = sttm.executeQuery();
            while (rs.next()) {
                kd.setMaKieuDang(rs.getString(1));
                kd.setTenKieuDang(rs.getNString(2));
                kd.setMoTa(rs.getNString(3));
                kd.setTrangThai(rs.getInt(4));
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
        return kd;
    }

    public KieuDang getKieuDangByTen(String TenKieuDang) {
        KieuDang kd = new KieuDang();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KieuDang\n"
                    + "WHERE TenKieuDang=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, TenKieuDang);
            rs = sttm.executeQuery();
            while (rs.next()) {
                kd.setMaKieuDang(rs.getString(1));
                kd.setTenKieuDang(rs.getNString(2));
                kd.setMoTa(rs.getNString(3));
                kd.setTrangThai(rs.getInt(4));
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
        return kd;
    }

}
