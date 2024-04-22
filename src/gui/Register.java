package gui;

import service.userDao.ExistingUserException;
import service.userDao.UserDao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Register extends JFrame {


    private JPanel userPass;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JButton loginButton;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JToolBar optionsToolBar;
    private JButton loginOptionButton;
    private JButton registerOptionButton;
    private JLabel againPasswordLabel;
    private JPasswordField passwordField2;
    private final UserDao udao = new UserDao();

    public Register() {
        setTitle("Password Manager - Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(userPass);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(500, 200);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password1 = Arrays.toString(passwordField.getPassword());
                String password2 = Arrays.toString(passwordField2.getPassword());
                if (!password1.equals(password2)) {
                    JOptionPane.showMessageDialog(userPass, "Passwords do not match");
                } else {
                    try {
                        udao.register(username, password1);
                        JOptionPane.showMessageDialog(userPass, "Welcome to our service!");
                    } catch (ExistingUserException ex) {
                        JOptionPane.showMessageDialog(userPass, "This user already exists");
                    }
                }
            }
        });
        loginOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });

        registerOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register();
            }
        });
    }

    public static void main(String[] args) {
        new Register();
    }


}
