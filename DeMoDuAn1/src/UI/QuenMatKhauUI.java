/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.NhanVien;
import service.NhanVienService;

/**
 *
 * @author ledin
 */
public class QuenMatKhauUI extends javax.swing.JFrame {

    List<NhanVien> listNV=new ArrayList<>();
    NhanVienService serviceNV=new NhanVienService();
    
    public QuenMatKhauUI() {
        initComponents();
        setLocationRelativeTo(null);
        listNV=serviceNV.getAllNhanVien();
        fillToComboboxNV(listNV);
    }

    public void fillToComboboxNV(List<NhanVien> list){
        DefaultComboBoxModel cboModel=new DefaultComboBoxModel();
        cboModel.addElement("Chon ma nhan vien");
        for(int i=0;i<list.size();i++){
            cboModel.addElement(listNV.get(i).getMaNV());
        }
        cboNhanVien.setModel(cboModel);
    }
    
    private String codeMKM = "";

    private void Gui_Email() {
        final String username = "daoldph37721@fpt.edu.vn";
        final String password = "mapenvboruxjobux";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        for (int i = 0; i < 6; i++) {
            double randomDouble = Math.random();
            randomDouble = randomDouble * 9 + 1;
            int randomInt = (int) randomDouble;
            this.codeMKM += randomInt;
        }
        final String textMKM = "Đây là mã OTP của bạn: " + this.codeMKM + "\nVui lòng không chia sẻ mã này cho bất kì ai!";

        //Đăng nhập email
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(txtEmailTo.getText())
            );
            message.setSubject("Big-Foot: Mã OTP!");
            message.setText(textMKM);

            Transport.send(message);
            JOptionPane.showMessageDialog(this, "Email đã được gửi thành công!");
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {
                // Xử lý sau khi thời gian hiệu lực hết hạn
                System.out.println("Mã OTP đã hết hạn sau 120s.");
                JOptionPane.showMessageDialog(this, "Mã OTP đã hết hạn");
                // Có thể thực hiện các hành động khác ở đây
                this.codeMKM="";
                // Đóng scheduler sau khi hoàn thành
                scheduler.shutdown();
            }, 120, TimeUnit.SECONDS);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    private boolean checkCodeMKM() {
        String code = this.txtCode.getText();
        if (!code.equalsIgnoreCase(this.codeMKM)) {
            JOptionPane.showMessageDialog(this, "Bạn đã nhập sai code! \n" + "Mời bạn nhập lại");
            return false;
        } else {
            return true;
        }
    }
    
    private boolean check_Email() {

        String checkEmail = txtEmailTo.getText(); // Lấy email từ txtEmailTo

        if (checkEmail.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Email đang trống!");
            return false;
        }

        // Biểu thức chính quy để kiểm tra định dạng email
        String checkFormEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Kiểm tra định dạng email
        if (!checkEmail.matches(checkFormEmail)) {
            JOptionPane.showMessageDialog(this, "Định dạng email không hợp lệ!");
            return false;
        }

        return true; // Trả về false nếu không tìm thấy
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEmailTo = new javax.swing.JTextField();
        btnGuiMail = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        btnXacNhan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtMKMoi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtXNMK = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnLuu = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cboNhanVien = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("QUÊN MẬT KHẨU");

        jLabel2.setText("Email");

        txtEmailTo.setEnabled(false);

        btnGuiMail.setBackground(new java.awt.Color(255, 255, 102));
        btnGuiMail.setText("Gửi");
        btnGuiMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiMailActionPerformed(evt);
            }
        });

        jLabel3.setText("CODE");

        btnXacNhan.setBackground(new java.awt.Color(0, 204, 204));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel4.setText("Mật khẩu mới");

        txtMKMoi.setEnabled(false);

        jLabel5.setText("Xác nhận MK");

        txtXNMK.setEnabled(false);

        btnLuu.setBackground(new java.awt.Color(0, 255, 0));
        btnLuu.setText("Lưu thay đổi");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(255, 51, 0));
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        jLabel6.setText("Mã NV");

        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtXNMK)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnLuu)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnThoat)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMKMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnXacNhan)
                                        .addGap(8, 8, 8))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtEmailTo, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGuiMail, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel1)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtEmailTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuiMail))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnXacNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMKMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtXNMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu)
                    .addComponent(btnThoat))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuiMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiMailActionPerformed
        if (check_Email() == true) {
            Gui_Email();
        } else {
            JOptionPane.showMessageDialog(this,"Mời bạn thử lại");
        }
    }//GEN-LAST:event_btnGuiMailActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        
        if (check_Email() == true && checkCodeMKM() == true) {
            JOptionPane.showMessageDialog(this,"Xác nhận thành công");
            txtMKMoi.setEnabled(true);
            txtXNMK.setEnabled(true);
        }else{
            txtCode.setText("");
            JOptionPane.showMessageDialog(this,"Xác nhận thất bại");
            return;
        }
       
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
        new DangNhapUUI().setVisible(true);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if(txtMKMoi.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"Bạn không được bỏ trống MK Mới");
            return;
        }
        if(txtXNMK.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this,"Bạn không được bỏ trống XN Mật khẩu");
            return;
        }
        if(!txtMKMoi.getText().equals(txtXNMK.getText())){
            JOptionPane.showMessageDialog(this,"Mật khẩu và Xác nhận MK phải trùng khớp");
            return;
        }
        JOptionPane.showMessageDialog(this,serviceNV.UpdateMatKhauTheoEmail(txtMKMoi.getText(), txtEmailTo.getText()) );
        dispose();
        new DangNhapUUI().setVisible(true);
    }//GEN-LAST:event_btnLuuActionPerformed

    private void cboNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNhanVienActionPerformed
        int x=cboNhanVien.getSelectedIndex();
        if(x>0){
            NhanVien nv=listNV.get(x-1);
            txtEmailTo.setText(nv.getEmail());
        }else{
            txtEmailTo.setText("");
        }
    }//GEN-LAST:event_cboNhanVienActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuenMatKhauUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuenMatKhauUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuiMail;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtEmailTo;
    private javax.swing.JTextField txtMKMoi;
    private javax.swing.JTextField txtXNMK;
    // End of variables declaration//GEN-END:variables
}
