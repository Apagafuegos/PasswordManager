import dbConnection.PasswordDao;
import dbConnection.UserDao;
import password.Password;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        try {
            app.start();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}