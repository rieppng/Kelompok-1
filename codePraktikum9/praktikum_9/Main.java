package com.mycompany.praktikum_9;

/**
 *
 * @author BlueBird
 */
public class Main {
    public static void main(String[] args) {
        MainJFrame frame = new MainJFrame();
        frame.changeMainPanel(new TambahDataPanel(frame));
        frame.setVisible(true);
    }
}
