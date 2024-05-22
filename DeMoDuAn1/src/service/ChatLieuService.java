package service;

import java.util.List;
import model.ChatLieu;
import repository.ChatLieuRepository;

public class ChatLieuService {

    ChatLieuRepository chatLieuRepo = new ChatLieuRepository();

    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepo.getAllChatLieu();
    }

    public String insertChatLieu(ChatLieu cl) {
        int x = chatLieuRepo.insertChatLieu(cl);
        if (x >= 0) {
            return "Thêm mới chất liệu thành công";
        }
        return "Thêm mới chất liệu thất bại";
    }
    
    public String updateChatLieu(ChatLieu cl) {
        int x = chatLieuRepo.updateChatLieu(cl);
        if (x >= 0) {
            return "Cập nhật chất liệu thành công";
        }
        return "Cập nhật chất liệu thất bại";
    }
    
    public ChatLieu getChatLieuByMa(String MaChatLieu) {
        return chatLieuRepo.getChatLieuByMa(MaChatLieu);
    }
    
    public ChatLieu getChatLieuByTen(String TenChatLieu) {
        return chatLieuRepo.getChatLieuByTen(TenChatLieu);
    }
}
