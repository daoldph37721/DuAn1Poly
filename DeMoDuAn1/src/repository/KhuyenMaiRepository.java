package repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.KhuyenMai;

public class KhuyenMaiRepository {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiTheoSoHDAP() {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai\n"
                    + "ORDER BY SoHoaDonDaApDung DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiTheoNgayKetThuc() {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai\n"
                    + "ORDER BY ThoiGianKetThuc DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiByMa(String MaKhuyenMai) {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai WHERE MaKhuyenMai like '%" + MaKhuyenMai + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiByTrangThai(int TrangThai) {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai WHERE TrangThai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiTheo_SoHDAP_TT(int TrangThai) {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai\n"
                    + "WHERE TrangThai=?\n"
                    + "ORDER BY SoHoaDonDaApDung DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public List<KhuyenMai> getAllKhuyenMaiTheo_NgayKetThuc_TT(int TrangThai) {
        List<KhuyenMai> listKM = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM KhuyenMai\n"
                    + "WHERE TrangThai=?\n"
                    + "ORDER BY ThoiGianKetThuc DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getString(1));
                km.setTenKhuyenMai(rs.getNString(2));
                km.setSoHoaDonDaApDung(rs.getInt(3));
                km.setHinhThuc(rs.getInt(4));
                km.setMucGiamGia(rs.getDouble(5));
                km.setThoiGianBatDau(rs.getDate(6));
                km.setThoiGianKetThuc(rs.getDate(7));
                km.setMoTa(rs.getNString(8));
                km.setNgayTao(rs.getDate(9));
                km.setNgaySua(rs.getDate(10));
                km.setTrangThai(rs.getInt(11));
                km.setSoTienMin(rs.getDouble(12));
                km.setSoLuong(rs.getInt(13));
                listKM.add(km);
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
        return listKM;
    }

    public int InsertKhuyenMai(KhuyenMai km) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT KhuyenMai VALUES(?,?,0,?,?,?,?,NULL,GETDATE(),NULL,1,?,?);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, km.getMaKhuyenMai());
            sttm.setString(2, km.getTenKhuyenMai());
            sttm.setInt(3, km.getHinhThuc());
            sttm.setDouble(4, km.getMucGiamGia());
            sttm.setString(5, dateFormat.format(km.getThoiGianBatDau()));
            sttm.setString(6, dateFormat.format(km.getThoiGianKetThuc()));
            sttm.setDouble(7, km.getSoTienMin());
            sttm.setInt(8, km.getSoLuong());
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

    public int UpdateKhuyenMai(KhuyenMai km) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE KhuyenMai SET TenKhuyenMai=?,HinhThuc=?,\n"
                    + "MucGiamGia=?,ThoiGianBatDau=?,ThoiGianKetThuc=?,\n"
                    + "NgaySua=GETDATE(),SoTienMin=?,SoLuong=?\n"
                    + "WHERE MaKhuyenMai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);

            sttm.setString(1, km.getTenKhuyenMai());
            sttm.setInt(2, km.getHinhThuc());
            sttm.setDouble(3, km.getMucGiamGia());
            sttm.setString(4, dateFormat.format(km.getThoiGianBatDau()));
            sttm.setString(5, dateFormat.format(km.getThoiGianKetThuc()));
            sttm.setDouble(6, km.getSoTienMin());
            sttm.setString(8, km.getMaKhuyenMai());
            sttm.setInt(7, km.getSoLuong());
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

    public int UpdateTrangThaiKM(KhuyenMai km) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "EXEC Update_TrangThaiKM ?,?,?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareCall(sSQL);
            sttm.setString(1, km.getMaKhuyenMai());
            sttm.setString(2, dateFormat.format(km.getThoiGianBatDau()));
            sttm.setString(3, dateFormat.format(km.getThoiGianKetThuc()));
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

    public int UpdateSoLuongKhuyenMai(KhuyenMai km) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "EXEC CapNhatSoLuongKM ?,?,?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareCall(sSQL);
            sttm.setString(1, km.getMaKhuyenMai());
            sttm.setInt(2, km.getSoHoaDonDaApDung());
            sttm.setInt(3, km.getSoLuong());
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

    public int UpdateTrangThaiKMTrucTiep(String MaKhuyenMai) {
        HoaDon hd = null;
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE KhuyenMai SET TrangThai=0\n"
                    + "WHERE MaKhuyenMai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaKhuyenMai);
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
