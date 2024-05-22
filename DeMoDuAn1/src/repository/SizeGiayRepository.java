/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.MauSac;
import model.SizeGiay;

/**
 *
 * @author ledin
 */
public class SizeGiayRepository {

    public List<SizeGiay> getAllSizeGiay() {
        List<SizeGiay> listSize = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SizeGiay";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                SizeGiay size = new SizeGiay();
                size.setMaSize(rs.getString(1));
                size.setSoSize(rs.getInt(2));
                size.setTrangThai(rs.getInt(3));
                listSize.add(size);
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
        return listSize;
    }

    public int InsertSizeGiay(SizeGiay size) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT SizeGiay(MaSize,SoSize,TrangThai) VALUES(?,?,1);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, size.getMaSize());
            sttm.setInt(2, size.getSoSize());
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

    public int UpdateSizeGiay(SizeGiay size) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE SizeGiay SET SoSize=?\n"
                    + "WHERE MaSize=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, size.getSoSize());
            sttm.setString(2, size.getMaSize());
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

    public SizeGiay getSizeByMa(String MaSize) {
        SizeGiay size = new SizeGiay();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SizeGiay\n"
                    + "WHERE MaSize=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaSize);
            rs = sttm.executeQuery();
            while (rs.next()) {
                size.setMaSize(rs.getString(1));
                size.setSoSize(rs.getInt(2));
                size.setTrangThai(rs.getInt(3));
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
        return size;
    }

    public SizeGiay getSizeBySoSize(int SoSize) {
        SizeGiay size = new SizeGiay();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM SizeGiay\n"
                    + "WHERE SoSize=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, SoSize);
            rs = sttm.executeQuery();
            while (rs.next()) {
                size.setMaSize(rs.getString(1));
                size.setSoSize(rs.getInt(2));
                size.setTrangThai(rs.getInt(3));
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
        return size;
    }
}
