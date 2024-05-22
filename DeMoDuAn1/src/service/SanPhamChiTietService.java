/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.SanPhamChiTiet;
import repository.SanPhamChiTietRepo;

/**
 *
 * @author ledin
 */
public class SanPhamChiTietService {
    
    SanPhamChiTietRepo spctRepo=new SanPhamChiTietRepo();
    
    public List<SanPhamChiTiet> getAllSPCT() {
        return spctRepo.getAllSPCT();
    }
    
    public String insertSPCT(SanPhamChiTiet spct) {
        int x=spctRepo.insertSPCT(spct);
        if(x>=0){
            return "Thêm mới sản phẩm chi tiết thành công";
        }
        return "Thêm mới sản phẩm chi tiết thất bại";
    }
    
    public String updateSPCT(SanPhamChiTiet spct) {
        int x=spctRepo.updateSPCT(spct);
        if(x>=0){
            return "Cập nhật sản phẩm chi tiết thành công";
        }
        return "Cập nhật sản phẩm chi tiết thất bại";
    }
    
    public String updateSPCT_TTDungBan(String maSPCT) {
        int x=spctRepo.updateSPCT_TTDungBan(maSPCT);
        if(x>=0){
            return "Cập nhật sản phẩm chi tiết dừng bán thành công";
        }
        return "Cập nhật sản phẩm chi tiết dừng bán thất bại";
    }
}
