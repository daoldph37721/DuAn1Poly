package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ChatLieu;
import model.MauSac;
import model.SizeGiay;

public class ChatLieuRepository {

    public List<ChatLieu> getAllChatLieu() {
        List<ChatLieu> listCL = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ChatLieu";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                ChatLieu cl = new ChatLieu();
                cl.setMaChatLieu(rs.getString(1));
                cl.setTenChatLieu(rs.getNString(2));
                cl.setMoTa(rs.getNString(3));
                cl.setTrangThai(rs.getInt(4));
                listCL.add(cl);
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
        return listCL;
    }

    public int insertChatLieu(ChatLieu cl) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT ChatLieu(MaChatLieu,TenChatLieu,MoTaCL,TrangThai) VALUES(?,?,?,1);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, cl.getMaChatLieu());
            sttm.setString(2, cl.getTenChatLieu());
            sttm.setString(3, cl.getMoTa());
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

    public int updateChatLieu(ChatLieu cl) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE ChatLieu SET TenChatLieu=?,MoTaCL=?\n"
                    + "WHERE MaChatLieu=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, cl.getTenChatLieu());
            sttm.setString(2, cl.getMoTa());
            sttm.setString(3, cl.getMaChatLieu());
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

    public ChatLieu getChatLieuByMa(String MaChatLieu) {
        ChatLieu cl = new ChatLieu();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ChatLieu\n"
                    + "WHERE MaChatLieu=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaChatLieu);
            rs = sttm.executeQuery();
            while (rs.next()) {
                cl.setMaChatLieu(rs.getString(1));
                cl.setTenChatLieu(rs.getNString(2));
                cl.setMoTa(rs.getNString(3));
                cl.setTrangThai(rs.getInt(4));
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
        return cl;
    }

    public ChatLieu getChatLieuByTen(String TenChatLieu) {
        ChatLieu cl = new ChatLieu();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM ChatLieu\n"
                    + "WHERE TenChatLieu=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, TenChatLieu);
            rs = sttm.executeQuery();
            while (rs.next()) {
                cl.setMaChatLieu(rs.getString(1));
                cl.setTenChatLieu(rs.getNString(2));
                cl.setMoTa(rs.getNString(3));
                cl.setTrangThai(rs.getInt(4));
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
        return cl;
    }

}
