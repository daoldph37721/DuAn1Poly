package repository;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KieuDang;
import model.ThuongHieu;

public class ThuongHieuRepository {

    public List<ThuongHieu> getAllThuongHieu() {
        List<ThuongHieu> listTH = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ThuongHieu";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                ThuongHieu th = new ThuongHieu();
                th.setMaThuongHieu(rs.getString(1));
                th.setTenThuongHieu(rs.getNString(2));
                th.setTrangThai(rs.getInt(3));
                listTH.add(th);
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
        return listTH;
    }

    public ThuongHieu getThuongHieubyMa(String MaThuongHieu) {
        ThuongHieu th = new ThuongHieu();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ThuongHieu\n"
                    + "WHERE MaThuongHieu=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaThuongHieu);
            rs = sttm.executeQuery();
            while (rs.next()) {
                th.setMaThuongHieu(rs.getString(1));
                th.setTenThuongHieu(rs.getNString(2));
                th.setTrangThai(rs.getInt(3));
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
        return th;
    }

    public ThuongHieu getThuongHieubyTen(String TenThuongHieu) {
        ThuongHieu th = new ThuongHieu();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ThuongHieu\n"
                    + "WHERE TenThuongHieu=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, TenThuongHieu);
            rs = sttm.executeQuery();
            while (rs.next()) {
                th.setMaThuongHieu(rs.getString(1));
                th.setTenThuongHieu(rs.getNString(2));
                th.setTrangThai(rs.getInt(3));  
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
        return th;
    }

}
