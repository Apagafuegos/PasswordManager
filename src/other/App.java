package other;

import org.jetbrains.annotations.NotNull;
import password.*;
import user.*;
import dbConnection.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    /**
     * Scanner to read lines and options.
     */
    private static final Scanner SC = new Scanner(System.in);
    /**
     * A connection to the user's database.
     */
    private static final UserDao UDAO = new UserDao();
    /**
     * All the used messages.
     */
    private static final GUI GUI = new GUI();

    /**
     * Method for the user to log in in the system (with an existing account).
     *
     * @return A connection to the database with the inserted user.
     * @throws IncorrectPasswordException in case the password for the user is introduced incorrectly 3 times.
     * @throws NoSuchUserException in case the user does not exist.
     */
    public PasswordDao logIn(String username) throws IncorrectPasswordException, NoSuchUserException {
        /*System.out.println(">Introduce your username and password");
        System.out.print("\t>Username: ");*/
        String password = "";
        User user = null;
        user = UDAO.getUser(username);

        int cont = 0;
        while (!password.equals(user.getPassword()) && cont < 3) {
            cont++;
            if (cont == 3)
                throw new IncorrectPasswordException("The password was incorrectly introduced 3 times. Logging off.");
            System.out.print("\n\t>Password: ");
            password = SC.nextLine();
            if (!password.equals(user.getPassword()))
                GUI.incorrectPassword(cont);
        }

        return new PasswordDao(user.getNombreUsuario());

    }

    /**
     * Method to create a new user in the system (and connect to the system with the created user).
     *
     * @return A connection to the database with the created user.
     * @throws IncorrectPasswordException in case the passwords do not match.
     * @throws ExistingUserException in case the user already exists.
     */
    public PasswordDao register() throws IncorrectPasswordException, ExistingUserException {
        System.out.println(">Introduce your new username and password");
        System.out.print("\t>Username: ");
        String username = SC.nextLine();
        System.out.print("\t>Password: ");
        String password = SC.nextLine();
        System.out.print("\t>Introduce your password again: ");
        String otherPassword = "";
        do {
            int cont = 0;
            otherPassword = SC.nextLine();
            if (!otherPassword.equals(password)) {
                cont++;
                System.err.println("Incorrect password, try again.");
                if (cont == 3)
                    throw new IncorrectPasswordException("Passwords do not match");
            }
        } while (!password.equals(otherPassword));


        UserDao udao = new UserDao();
        udao.newUser(username, password);


        return new PasswordDao(new User(username, password).getNombreUsuario());

    }

    /**
     * Menu with all the options of the program.
     *
     * @param pdao a connection to the database with a user.
     */
    public void menu(@NotNull PasswordDao pdao) {
        try {
            List<Password> listAllPasswords = pdao.getAllPasswords();
            if (!listAllPasswords.isEmpty())
                GUI.allPasswords(pdao.getAllPasswords());
            else
                GUI.noPasswords();
        } catch (NoSuchUserException e) {
            GUI.getMessage(e);
        }
        Integer opt = null;
        do {
            GUI.menuStart();
            try {
                opt = SC.nextInt();
                SC.nextLine();
                if (opt != 0 && opt != 1 && opt != 2 && opt != 3) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                GUI.incorrectOption();
            }

            if (opt == 1) {
                passwordFromService(pdao);
            } else if (opt == 2) {
                insertNewPassword(pdao);
            } else if (opt == 3) {
                updatePassword(pdao);
            }
        } while (opt != 0);

    }

    /**
     * Starts the application.
     */
    public void start() {
        GUI.welcome();
        Integer opt = null;
        do {
            try {
                opt = SC.nextInt();
                SC.nextLine();
                if (opt != 1 && opt != 2)
                    throw new InputMismatchException();
            } catch (InputMismatchException e) {
                GUI.incorrectOption();
            }
        }
        while (opt != 1 && opt != 2);


        if (opt == 1) {
            try {
                menu(logIn("aa"));
            } catch (IncorrectPasswordException | NoSuchUserException e) {
                GUI.getMessage(e);
            }
        } else {
            try {
                menu(register());
            } catch (IncorrectPasswordException | ExistingUserException e) {
                GUI.getMessage(e);
            }

        }
    }

    /**
     * Method to get the password from a service.
     *
     * @param pdao a connection to the database with a user.
     */
    private void passwordFromService(@NotNull PasswordDao pdao){
        try {
            System.out.println(pdao.getPassword(SC.nextLine().toUpperCase()));
        } catch (NoSuchServiceException | NoSuchUserException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to create a new password for a service.
     *
     * @param pdao a connection to the database with a user.
     */
    private void insertNewPassword(@NotNull PasswordDao pdao){
        try {
            GUI.enterService();
            String service = SC.nextLine();
            GUI.enterUsername();
            String uname = SC.nextLine();
            GUI.enterPassword();
            String pword = SC.nextLine();
            if (pdao.newPassword(new Password(uname, pword, service)))
                GUI.successfulPassword();
            else
                GUI.problemPassword();
        } catch (RepeatedPasswordException e) {
            GUI.getMessage(e);
        }
    }

    /**
     * Method to update the password of a specified service.
     *
     * @param pdao a connection to the database with a user.
     */
    private void updatePassword(@NotNull PasswordDao pdao){
        try {
            GUI.enterService();
            String service = SC.nextLine();
            GUI.enterPassword();
            String pword = SC.nextLine();
            if (pdao.updatePassword(pword, service))
                GUI.passwordUpdated();
            else
                GUI.problemPassword();
        } catch (NoSuchServiceException e) {
            GUI.getMessage(e);
        }
    }


}
