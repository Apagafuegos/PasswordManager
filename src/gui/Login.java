package gui;

import service.userDao.IncorrectPasswordException;
import service.userDao.NoSuchUserException;
import service.userDao.UserDao;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Login extends JFrame {


    private JPanel userPass;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JToolBar optionsToolBar;
    private JButton loginOptionButton;
    private JButton registerOptionButton;
    private final UserDao udao = new UserDao();

    public Login() {
        setTitle("Password Manager - Log in");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(userPass);
        setSize(500,200);
        setLocationRelativeTo(null);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = Arrays.toString(passwordPasswordField.getPassword());

                try {
                    udao.login(username, password);
                    JOptionPane.showMessageDialog(userPass, "Welcome back!");
                } catch (IncorrectPasswordException ex) {
                    JOptionPane.showMessageDialog(userPass, "Incorrect password");
                } catch (NoSuchUserException ex) {
                    JOptionPane.showMessageDialog(userPass, "Introduced user does not exist");
                }
            }
        });

        registerOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Register();
            }
        });

        loginOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
