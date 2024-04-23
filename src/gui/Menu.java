package gui;

import model.Password;
import org.jetbrains.annotations.NotNull;
import service.passwordDao.NoPasswordsException;
import service.passwordDao.NoSuchServiceException;
import service.passwordDao.PasswordDao;
import service.passwordDao.RepeatedPasswordException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Menu extends JFrame {

    private JPanel panel1;
    private JButton newPasswordButton;
    private JButton deleteButton;
    private JButton button3;
    private JLabel yourPasswords;
    private JScrollPane listContainer;
    private final String[] columns;
    private final String[][] data;

    public Menu(@NotNull PasswordDao pdao) {
        setTitle("Password Manager - User menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        columns = new String[]{"Service", "Username", "Password"};
        data = passwordListGenerator(pdao);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(data, columns);
        table.setModel(model);
        listContainer.setViewportView(table);


        newPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField serviceField = new JTextField();
                JTextField usernameField = new JTextField();
                JTextField passwordField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Service name:"));
                panel.add(serviceField);
                panel.add(new JLabel("Username in the service:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password in the service:"));
                panel.add(passwordField);
                int result = JOptionPane.showConfirmDialog(null, panel, "Enter the new password details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        pdao.newPassword(
                                usernameField.getText(),
                                passwordField.getText(),
                                serviceField.getText()
                        );
                        model.addRow(new Object[]{
                                serviceField.getText().toUpperCase(),
                                usernameField.getText(),
                                passwordField.getText()
                        });
                    } catch (RepeatedPasswordException ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "New password operation cancelled");
                }


            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] deletedRow;
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1){
                    deletedRow = new String[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        deletedRow[i] = (String) table.getValueAt(selectedRow, i);
                    }
                    model.removeRow(selectedRow);
                    model.fireTableDataChanged();
                    try {
                        pdao.deletePassword(deletedRow[0], deletedRow[1], deletedRow[2]);
                    } catch (NoPasswordsException | NoSuchServiceException ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage());
                    }
                }
            }
        });
    }

    private String[][] passwordListGenerator(PasswordDao pdao) {
        String[][] allPasswords;
        List<Password> passwordsList = List.of();
        int size = 0;
        try {
            passwordsList = pdao.getAllPasswords();
            size = passwordsList.size();
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
