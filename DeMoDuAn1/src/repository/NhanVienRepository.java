package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import model.NhanVien;

public class NhanVienRepository {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        Statement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien";
            conn = DBConnection.getDBConect();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                listNV.add(nv);
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
        return listNV;
    }

    public int InsertNhanVien(NhanVien nv) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "INSERT NhanVien(MaNV,MatKhau,HoTen,GioiTinh,"
                    + "DiaChi,SoDienThoai,CCCD,NgayVaoLam,NgayTao,TrangThai,Email)\n"
                    + "VALUES(?,?,?,?,?,?,?,GETDATE(),GETDATE(),1,?);";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, nv.getMaNV());
            sttm.setString(2, nv.getMatKhau());
            sttm.setString(3, nv.getHoTen());
            sttm.setInt(4, nv.getGioiTinh());
            sttm.setString(5, nv.getDiaChi());
            sttm.setString(6, nv.getSDT());
            sttm.setString(7, nv.getCCCD());
            sttm.setString(8, nv.getEmail());
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

    public int UpdateNhanVien(NhanVien nv) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE NhanVien SET HoTen=?,GioiTinh=?,DiaChi=?,"
                    + "SoDienThoai=?,NgaySua=GETDATE(),Email=?\n"
                    + "WHERE MaNV=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, nv.getHoTen());
            sttm.setInt(2, nv.getGioiTinh());
            sttm.setString(3, nv.getDiaChi());
            sttm.setString(4, nv.getSDT());
            sttm.setString(5, nv.getEmail());
            sttm.setString(6, nv.getMaNV());
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

    public int UpdateNVNghiViec(String maNV) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE NhanVien SET NgayNghi=GETDATE(),TrangThai=0\n"
                    + "WHERE MaNV=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, maNV);
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

    public int UpdateMatKhauTheoEmail(String matKhau, String email) {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "UPDATE NhanVien SET MatKhau=?\n"
                    + "WHERE Email=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, matKhau);
            sttm.setString(2, email);
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

    public List<NhanVien> getAllNhanVienTKTheoTen(String hoTen) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien\n"
                    + "WHERE HoTen like '%" + hoTen + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                listNV.add(nv);
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
        return listNV;
    }

    public int checkNhanVienTKTheoMaVaMatKhau(String maNV, String matKhau) {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien\n"
                    + "WHERE MaNV=? AND MatKhau=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, maNV);
            sttm.setString(2, matKhau);
            rs = sttm.executeQuery();
            while (rs.next()) {
                return 1;
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
        return -1;
    }

    public List<NhanVien> getAllNhanVienTKTheoSDT(String SDT) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien\n"
                    + "WHERE SoDienThoai like '%" + SDT + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienTKTheoCCCD(String CCCD) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien\n"
                    + "WHERE CCCD like '%" + CCCD + "%'";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienSXTheo_SoHoaDon() {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT NhanVien.*,COUNT(HoaDon.MaHoaDon),SUM(HoaDon.ThanhTien)\n"
                    + "FROM NhanVien JOIN HoaDon ON NhanVien.MaNV=HoaDon.MaNV\n"
                    + "WHERE HoaDon.TrangThai=1\n"
                    + "GROUP BY NhanVien.MaNV, NhanVien.MatKhau, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi,\n"
                    + "NhanVien.SoDienThoai, NhanVien.CCCD, NhanVien.NgayVaoLam, NhanVien.NgayNghi,\n"
                    + "NhanVien.NgayTao, NhanVien.NgaySua, NhanVien.TrangThai, NhanVien.MaCV, NhanVien.Email\n"
                    + "ORDER BY COUNT(HoaDon.MaHoaDon) DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                nv.setSoHoaDon(rs.getInt(15));
                nv.setDoanhThu(rs.getDouble(16));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienSXTheo_DoanhThu() {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT NhanVien.*,COUNT(HoaDon.MaHoaDon),SUM(HoaDon.ThanhTien)\n"
                    + "FROM NhanVien JOIN HoaDon ON NhanVien.MaNV=HoaDon.MaNV\n"
                    + "WHERE HoaDon.TrangThai=1\n"
                    + "GROUP BY NhanVien.MaNV, NhanVien.MatKhau, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi,\n"
                    + "NhanVien.SoDienThoai, NhanVien.CCCD, NhanVien.NgayVaoLam, NhanVien.NgayNghi,\n"
                    + "NhanVien.NgayTao, NhanVien.NgaySua, NhanVien.TrangThai, NhanVien.MaCV, NhanVien.Email\n"
                    + "ORDER BY SUM(HoaDon.ThanhTien) DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                nv.setSoHoaDon(rs.getInt(15));
                nv.setDoanhThu(rs.getDouble(16));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienTheoTrangThai(int TrangThai) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT * FROM NhanVien\n"
                    + "WHERE TrangThai=?";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienSXTheo_SoHoDon_TT(int TrangThai) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT NhanVien.*,COUNT(HoaDon.MaHoaDon),SUM(HoaDon.ThanhTien)\n"
                    + "FROM NhanVien JOIN HoaDon ON NhanVien.MaNV=HoaDon.MaNV\n"
                    + "WHERE HoaDon.TrangThai=1 AND NhanVien.TrangThai=?\n"
                    + "GROUP BY NhanVien.MaNV, NhanVien.MatKhau, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi,\n"
                    + "NhanVien.SoDienThoai, NhanVien.CCCD, NhanVien.NgayVaoLam, NhanVien.NgayNghi,\n"
                    + "NhanVien.NgayTao, NhanVien.NgaySua, NhanVien.TrangThai, NhanVien.MaCV, NhanVien.Email\n"
                    + "ORDER BY COUNT(HoaDon.MaHoaDon) DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(14));
                nv.setSoHoaDon(rs.getInt(15));
                nv.setDoanhThu(rs.getDouble(16));
                listNV.add(nv);
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
        return listNV;
    }

    public List<NhanVien> getAllNhanVienSXTheo_DoanhThu_TT(int TrangThai) {
        List<NhanVien> listNV = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        try {
            String sSQL = "SELECT NhanVien.*,COUNT(HoaDon.MaHoaDon),SUM(HoaDon.ThanhTien)\n"
                    + "FROM NhanVien JOIN HoaDon ON NhanVien.MaNV=HoaDon.MaNV\n"
                    + "WHERE HoaDon.TrangThai=1 AND NhanVien.TrangThai=?\n"
                    + "GROUP BY NhanVien.MaNV, NhanVien.MatKhau, NhanVien.HoTen, NhanVien.GioiTinh, NhanVien.DiaChi,\n"
                    + "NhanVien.SoDienThoai, NhanVien.CCCD, NhanVien.NgayVaoLam, NhanVien.NgayNghi,\n"
                    + "NhanVien.NgayTao, NhanVien.NgaySua, NhanVien.TrangThai, NhanVien.MaCV,NhanVien.Email\n"
                    + "ORDER BY SUM(HoaDon.ThanhTien) DESC";
            conn = DBConnection.getDBConect();
            sttm = conn.prepareStatement(sSQL);
            sttm.setInt(1, TrangThai);
            rs = sttm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getNString(3));
                nv.setGioiTinh(rs.getInt(4));
                nv.setDiaChi(rs.getNString(5));
                nv.setSDT(rs.getString(6));
                nv.setCCCD(rs.getString(7));
                nv.setNgayVaoLam(rs.getDate(8));
                nv.setNgayNghi(rs.getDate(9));
                nv.setNgayTao(rs.getDate(10));
                nv.setNgaySua(rs.getDate(11));
                nv.setTrangThai(rs.getInt(12));
                nv.setEmail(rs.getString(15));
                nv.setSoHoaDon(rs.getInt(16));
                nv.setDoanhThu(rs.getDouble(17));
                listNV.add(nv);
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
        return listNV;
    }
}
