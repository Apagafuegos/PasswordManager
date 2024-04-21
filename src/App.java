import password.*;
import user.*;
import dbConnection.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner SC = new Scanner(System.in);
    private static final UserDao UDAO = new UserDao();

    public PasswordDao logIn() throws IncorrectPasswordException, NoSuchUserException {
        System.out.println(">Introduce your username and password");
        System.out.print("\t>Username: ");
        String username = SC.nextLine();
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
                System.err.println("Incorrect password, got " + (3 - cont) + " tries left.");
        }

        return new PasswordDao(user.getNombreUsuario());

    }

    public PasswordDao register() throws IncorrectPasswordException, ExistingUserException {
        System.out.println(">Introduce your new username and password");
        System.out.print("\t>Username: ");
        String username = SC.nextLine();
        System.out.print("\t>Password: ");
        String password = SC.nextLine();
        System.out.print("\t>Introduce your password again: ");
        String otherPassword = "";
        do{
            int cont = 0;
            otherPassword = SC.nextLine();
            if(!otherPassword.equals(password)){
                cont++;
                System.err.println("Incorrect password, try again.");
                if(cont==3)
                    throw new IncorrectPasswordException("Passwords do not match");
            }
        }while(!password.equals(otherPassword));


        UserDao udao = new UserDao();
        udao.newUser(username, password);


        return new PasswordDao(new User(username, password).getNombreUsuario());

    }

    public void menu(PasswordDao pdao) {
        try {
            List<Password> listAllPasswords = pdao.getAllPasswords();
            if(!listAllPasswords.isEmpty())
                System.out.println(pdao.getAllPasswords());
            else
                System.err.println("You got no passwords at this moment");
        } catch (NoSuchUserException e) {
            System.out.println(e.getMessage());
        }
        Integer opt = null;
        do {
            System.out.println("""
                    Welcome!
                    Options:\s
                                    
                    \t>1. A password from a service
                    \t>2. Insert a new password
                    \t>3. Update a password
                    \t>0. To finish the program
                    """);
            try{
                opt = SC.nextInt();
                if(opt != 0 && opt != 1 && opt != 2 && opt != 3){
                    throw new InputMismatchException();
                }
            }catch(InputMismatchException e){
                System.err.println("Wrong option chosen");
            }

            SC.nextLine();

            if (opt == 1) {
                try {
                    System.out.println(pdao.getPassword(SC.nextLine().toUpperCase()));
                } catch (NoSuchServiceException | NoSuchUserException e) {
                    System.out.println(e.getMessage());
                }
            } else if (opt == 2) {
                try {
                    System.out.println("Choose the service");
                    String service = SC.nextLine();
                    System.out.println("Enter the username");
                    String uname = SC.nextLine();
                    System.out.println("Enter the password");
                    String pword = SC.nextLine();
                    if (pdao.newPassword(new Password(uname, pword, service)))
                        System.out.println("Password introduced successfully!");
                    else
                        System.err.println("There was a problem introducing the password");
                } catch (RepeatedPasswordException e) {
                    System.out.println(e.getMessage());
                }
            } else if (opt == 3) {
                try {
                    System.out.println("Choose the service");
                    String service = SC.nextLine();
                    System.out.println("Enter the password");
                    String pword = SC.nextLine();
                    if (pdao.updatePassword(pword, service))
                        System.out.println("Password updated successfully!");
                    else
                        System.err.println("There was a problem updating the password");
                } catch (NoSuchServiceException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (opt != 0);

    }

    public void start(){
        System.out.println("Hi!");
        System.out.println("Log in (1) or do you wish to create a new account(2)?");
        Integer opt = null;
        do{
            try{
                opt = SC.nextInt();
                SC.nextLine();
                if(opt != 1 && opt != 2)
                    throw new InputMismatchException();
            }catch(InputMismatchException e){
                System.err.println("Incorrect option chosen.");
            }
        }
        while(opt != 1 && opt != 2 );


        if(opt == 1) {
            try {
                menu(logIn());
            } catch (IncorrectPasswordException | NoSuchUserException e) {
                System.out.println(e.getMessage());
            }
        }else if (opt == 2){
            try {
                menu(register());
            } catch (IncorrectPasswordException | ExistingUserException e) {
                System.err.println(e.getMessage());
            }

        }
    }


}
