package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import model.MauSac;
import model.SanPhamMain;

public class MauSacRepository {

    public List<MauSac> getAllMauSac() {
        List<MauSac> listMau = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM MauSac";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                MauSac mau = new MauSac();
                mau.setMaMau(rs.getString(1));
                mau.setTenMau(rs.getNString(2));
                mau.setDoTuongPhan(rs.getInt(3));
                mau.setDoBaoHoa(rs.getInt(4));
                mau.setTrangThai(rs.getInt(5));
                listMau.add(mau);
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
        return listMau;
    }

    public int InsertMauSac(MauSac mau) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT MauSac(MaMau,TenMau,DoTuongPhan,DoBaoHoa,TrangThai) VALUES(?,?,?,?,1);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, mau.getMaMau());
            sttm.setString(2, mau.getTenMau());
            sttm.setInt(3, mau.getDoTuongPhan());
            sttm.setInt(4, mau.getDoBaoHoa());
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

    public int UpdateMauSac(MauSac mau) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE MauSac SET TenMau=?, DoTuongPhan=?,DoBaoHoa=?\n"
                    + "WHERE MaMau=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, mau.getTenMau());
            sttm.setInt(2, mau.getDoTuongPhan());
            sttm.setInt(3, mau.getDoBaoHoa());
            sttm.setString(4, mau.getMaMau());
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

    public MauSac getMauSacByMa(String MaMau) {
        MauSac mau = new MauSac();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM MauSac\n"
                    + "WHERE MaMau=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaMau);
            rs = sttm.executeQuery();
            while (rs.next()) {
                mau.setMaMau(rs.getString(1));
                mau.setTenMau(rs.getNString(2));
                mau.setDoTuongPhan(rs.getInt(3));
                mau.setDoBaoHoa(rs.getInt(4));
                mau.setTrangThai(rs.getInt(5));
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
        return mau;
    }

    public MauSac getMauSacByTen(String TenMau) {
        MauSac mau = new MauSac();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM MauSac\n"
                    + "WHERE TenMau=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, TenMau);
            rs = sttm.executeQuery();
            while (rs.next()) {
                mau.setMaMau(rs.getString(1));
                mau.setTenMau(rs.getNString(2));
                mau.setDoTuongPhan(rs.getInt(3));
                mau.setDoBaoHoa(rs.getInt(4));
                mau.setTrangThai(rs.getInt(5));
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
        return mau;
    }
}
