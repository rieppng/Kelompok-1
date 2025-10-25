/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum_8;

import javax.swing.*;

/**
 *
 * @author User
 */
public class Praktikum_8 {

    public static void main(String[] args) {
        JFrame frame = new JFrame ("contoh JFrame");
        
        //setsize
        frame.setSize(400, 300);
        
        //mengatur operasi saat ditutup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //frame terlihat
        frame.setVisible(true);
        
        //agar muncul ditengah
        frame.setLocationRelativeTo(null);
        
        JLabel label = new JLabel ("Contoh Label"), SwingConstanstCenter;
        
        frame.add(label);
        
        JTextField textField = new JTextField(20);
        frame.add(textField);
        
        JButton button = new JButton ("Click me");
        frame.add(button);
    }
}
