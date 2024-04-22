package gui;

import model.Password;
import org.jetbrains.annotations.NotNull;
import service.passwordDao.NoPasswordsException;
import service.passwordDao.PasswordDao;

import javax.net.ssl.SSLContext;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu extends JFrame {

    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel yourPasswords;
    private JScrollPane listContainer;
    private final String[] columns;
    private final String[][] data;

    public Menu(@NotNull PasswordDao pdao){
        setTitle("Password Manager - User menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(500,200);
        setLocationRelativeTo(null);
        setVisible(true);

        columns = new String[]{"Service", "Username", "Password"};
        data = passwordListGenerator(pdao);

        DefaultTableModel model = new DefaultTableModel(data, columns){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JTable table = new JTable(data,columns);
        table.setModel(model);
        listContainer.setViewportView(table);



    }

    private String[][] passwordListGenerator(PasswordDao pdao){
        String[][] allPasswords;
        List<Password> passwordsList = List.of();
        int size = 0;
        try {
            passwordsList = pdao.getAllPasswords();
            size =  passwordsList.size();
        } catch (NoPasswordsException ignored) {
        }
        allPasswords = new String[size][3];

        for (int i = 0; i < size; i++) {
            allPasswords[i][0] = passwordsList.get(i).getServicio();
            allPasswords[i][1] = passwordsList.get(i).getUserName();
            allPasswords[i][2] = passwordsList.get(i).getPassword();
        }

        return allPasswords;
    }

    public static void main(String[] args) {
        new Menu(new PasswordDao("Extinthor17"));
    }

}
