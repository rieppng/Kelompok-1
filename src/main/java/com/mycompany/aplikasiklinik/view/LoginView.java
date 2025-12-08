package com.mycompany.aplikasiklinik.view;

import java.awt.*;
import javax.swing.*;

/**
 * Class LoginView
 * * Merepresentasikan tampilan antarmuka untuk proses Login pengguna.
 * Menggunakan GridBagLayout untuk menempatkan form di tengah layar secara responsif.
 */
public class LoginView extends JFrame {

    // Komponen UI
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    /**
     * Konstruktor LoginView.
     * Menginisialisasi frame, komponen form, layout, dan properti dasar window.
     */
    public LoginView() {
        setTitle("Login Sistem Klinik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); 

        initializeComponents();
        setupLayout();

        pack(); 
        setLocationRelativeTo(null); // Tampil di tengah layar
    }

    /**
     * Menginisialisasi objek komponen UI seperti TextField dan Button.
     */
    private void initializeComponents() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin = new JButton("Login");
        btnCancel = new JButton("Keluar");

        // Styling Tombol
        btnLogin.setBackground(new Color(0, 102, 204)); 
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));

        // Event Listener Sederhana untuk tombol Cancel
        btnCancel.addActionListener(e -> System.exit(0));
    }

    /**
     * Menyusun komponen ke dalam Panel menggunakan GridBagLayout.
     */
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        // --- Header Judul ---
        JLabel lblTitle = new JLabel("KLINIK SEHAT");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.DARK_GRAY);
        
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblTitle, gbc);

        // --- Label & Input Username ---
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0; 
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(txtUsername, gbc);

        // --- Label & Input Password ---
        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(txtPassword, gbc);

        // --- Panel Tombol ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnLogin);

        gbc.gridy = 3; gbc.gridx = 0; 
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    // --- GETTERS ---

    /**
     * Mengambil teks input dari field Username.
     * @return String username yang diinputkan.
     */
    public String getUsernameInput() {
        return txtUsername.getText();
    }

    /**
     * Mengambil password dari field Password.
     * @return String password (dikonversi dari char[]).
     */
    public String getPasswordInput() {
        return new String(txtPassword.getPassword());
    }

    /**
     * Mengakses tombol Login untuk keperluan penambahan ActionListener di Controller.
     * @return Objek JButton login.
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    /**
     * Menampilkan dialog pesan informasi atau error kepada pengguna.
     * @param message Pesan yang ingin ditampilkan.
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}