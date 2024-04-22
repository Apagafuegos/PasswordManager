package gui;

import javax.swing.*;
import other.App;

public class window extends JFrame {
    private JPanel panel1;
    private JLabel Username;
    private JPasswordField passwordUser;
    private JTextField username;
    private JButton Enter;
    private App app;

    public window(){
        setContentPane(panel1);
        setTitle("Password Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        Enter.addActionListener(e -> JOptionPane.showMessageDialog(window.this, "Logged in!"));
    }


    public static void main(String[] args) {
        new window();
    }
}
