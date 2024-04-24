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
    private JButton updateButton;
    private JLabel yourPasswords;
    private JScrollPane listContainer;
    private JToolBar optionsToolBar;
    private JButton loginOptionButton;
    private final String[] columns;
    private final String[][] data;

    public Menu(@NotNull PasswordDao pdao) {
        setTitle("Password Manager - User menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panel1);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);

        columns = new String[]{"Service", "Username", "Password"}; //Columns e.g.(Twitch, foobar, 123)
        data = passwordListGenerator(pdao); //Data "generation"

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(data, columns);
        table.setModel(model);
        listContainer.setViewportView(table); //Inserts the table created
        setSize(400,500);


        newPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField serviceField = new JTextField();
                JTextField usernameField = new JTextField();
                JTextField passwordField = new JTextField();
                //Prompts a window for the user to insert in the 3 fields all the data of the passwords.
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Service name:"));
                panel.add(serviceField);
                panel.add(new JLabel("Username in the service:"));
                panel.add(usernameField);
                panel.add(new JLabel("Password in the service:"));
                panel.add(passwordField);
                int result = JOptionPane.showConfirmDialog(null, panel, "Enter the new password details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); //Prompt itself
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        pdao.newPassword(
                                usernameField.getText(),
                                passwordField.getText(),
                                serviceField.getText()
                        ); //Inserts the new password
                        model.addRow(new Object[]{
                                serviceField.getText().toUpperCase(),
                                usernameField.getText(),
                                passwordField.getText()
                        }); //Adds the row to the taqble
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
                int selectedRow = table.getSelectedRow(); //Gets the place of the selected row to delete
                if(selectedRow != -1){
                    deletedRow = new String[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        deletedRow[i] = (String) table.getValueAt(selectedRow, i);
                        //Sets the array with the data of the deleted row
                    }
                    model.removeRow(selectedRow);
                    model.fireTableDataChanged();
                    try {
                        pdao.deletePassword(deletedRow[0], deletedRow[1], deletedRow[2]);
                        //Deletes the data from the row from the database.
                    } catch (NoPasswordsException | NoSuchServiceException ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage());
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField passwordField = new JTextField();
                String[] updatedRow;
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("New password:"));
                panel.add(passwordField);
                int result = JOptionPane.showConfirmDialog(null, panel, "Updating password",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); //To insert the new password
                if (result == JOptionPane.OK_OPTION){
                    int selectedRow = table.getSelectedRow(); //Takes the selected row of the password to update
                    if (selectedRow != -1){
                        updatedRow = new String[table.getColumnCount()];
                        for (int i = 0; i < table.getColumnCount(); i++) {
                            updatedRow[i] = (String) table.getValueAt(selectedRow, i);
                            if(i == 2){
                                updatedRow[i] = passwordField.getText(); //Uses the new password value to fill the array
                            }
                        }
                        model.removeRow(selectedRow);
                        model.addRow(updatedRow);
                        try {
                            pdao.updatePassword(passwordField.getText(), updatedRow[0]);
                            //Updates the password with the new password and the service
                        } catch (NoSuchServiceException ex) {
                            JOptionPane.showMessageDialog(panel1, ex.getMessage());
                        }
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
    }

    /**
     * Generates a multidimensional array of the passwords (in String) of a user.
     * Done to be inserted in a Swing table.
     *
     * @param pdao connection to the DataBase
     * @return a multidimensional array.
     */
    private String[][] passwordListGenerator(@NotNull PasswordDao pdao) {
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
}