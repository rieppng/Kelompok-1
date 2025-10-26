package com.mycompany.praktikum_8;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JOptionPane;
/**
 *
 * @author BlueBird
 */
public class jframe {
    public static void main(String[] args) {
       // 1. Buat frame utama
       JFrame frame = new JFrame("Aplikasi Sederhana");
       frame.setSize(300, 150);
       frame.setLocation(100, 100);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       // 2. Dapatkan content pane
       JPanel panel = new JPanel();
       panel.setLayout(new FlowLayout(FlowLayout.CENTER));
       
       // 3. Tambahkan komponen ke content pane
       JLabel label = new JLabel("Nama:");
       JTextField textField = new JTextField(15);
       JButton button = new JButton("Kirim");
       panel.add(label);
       panel.add(textField);
       panel.add(button);

       // Menambahkan ActionListener ke tombol
       button.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
            
       // Aksi yang dilakukan saat tombol diklik
       String nama = textField.getText();
       JOptionPane.showMessageDialog(frame, "Halo, " + nama + "!");
       }
       });
       
       // 4. Tampilkan frame
       frame.setContentPane(panel);
       frame.setVisible(true);
   }
 }


// ... (di dalam metode main setelah semua komponen dibuat)

// ...(Lanjutkan dengan frame.setVisible(true))

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        System.out.println("Username : " + username);
        System.out.println("Password : " + password);
        
        if(username.equals("varel") && password.equals("123")){
            alertLabel.setText("Login Berhasil , selamat datang " + username);
            alertLabel.setForeground(Color.blue);
        } else {
            alertLabel.setText("Login Gagal!");
            alertLabel.setForeground(Color.red);
        }