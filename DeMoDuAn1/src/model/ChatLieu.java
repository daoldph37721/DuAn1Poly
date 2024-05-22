
package model;

public class ChatLieu {
    private String MaChatLieu;
    private String tenChatLieu;
    private String MoTa;
    private int TrangThai;

    public ChatLieu() {
    }

    public ChatLieu(String MaChatLieu, String tenChatLieu, String MoTa, int TrangThai) {
        this.MaChatLieu = MaChatLieu;
        this.tenChatLieu = tenChatLieu;
        this.MoTa = MoTa;
        this.TrangThai = TrangThai;
    }

    public String getMaChatLieu() {
        return MaChatLieu;
    }

    public void setMaChatLieu(String MaChatLieu) {
        this.MaChatLieu = MaChatLieu;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
}
