import javax.swing.*;

public class Praktikum_8 {
    public static void main(String[] args) {
        // Membuat frame
        JFrame frame = new JFrame("Contoh JFrame");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Contoh Label", SwingConstants.CENTER);
        frame.add(label);
        JTextField textField = new JTextField(20);;
        frame.add(textField);
        JButton button = new JButton("Click me");
        frame.add(button);
    }
}