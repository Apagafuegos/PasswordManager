package other;

import org.jetbrains.annotations.NotNull;
import password.Password;

import java.util.List;

public class GUI {

    /**
     * Prints the welcome text for the program.
     */
    public void welcome(){
        System.out.println("Hi!");
        System.out.println("Log in (1) or do you wish to create a new account(2)?");
    }
    /**
     * Prints that an incorrect option has been chosen.
     */
    public void incorrectOption(){
        System.err.println("Incorrect option chosen.");
    }
    /**
     * Prints the message of the introduced exception, of any kind.
     * @param e an Exception of any nature.
     */
    public void getMessage(@NotNull Exception e){
        System.err.println(e.getMessage());
    }
    /**
     * Prints that the password has been updated successfully.
     */
    public void passwordUpdated(){
        System.out.println("Password updated successfully!");
    }
    /**
     * Prints a text to enter the password.
     */
    public void enterPassword(){
        System.out.println("Enter the password");
    }
    /**
     * Prints a text to enter the username.
     */
    public void enterUsername(){
        System.out.println("Enter the username");
    }
    /**
     * Prints a text to enter the service's name.
     */
    public void enterService(){
        System.out.println("Choose the service");
    }

    /**
     * Prints that a new password has been successfully introduced.
     */
    public void successfulPassword(){
        System.out.println("Password introduced successfully!");
    }

    /**
     * Prints that there has been a problem with a password.
     */
    public void problemPassword(){
        System.err.println("There was a problem with the password");
    }

    /**
     * Print of the text of the menu.
     */
    public void menuStart(){
        System.out.println("""
                    Welcome!
                    Options:\s
                                   \s
                    \t>1. A password from a service
                    \t>2. Insert a new password
                    \t>3. Update a password
                    \t>0. To finish the program
                   \s""");
    }

    /**
     * Prints all the passwords contained by a user.
     *
     * @param listPasswords a list containing the passwords.
     */
    public void allPasswords(List<Password> listPasswords){
        System.out.println(listPasswords);
    }

    /**
     * Prints a message saying the user does not have any password.
     */
    public void noPasswords(){
        System.err.println("You got no passwords at this moment");
    }

    /**
     * Prints that the password introduced was incorrect.
     *
     * @param cont amount of tries left.
     */
    public void incorrectPassword(int cont){
        System.err.println("Incorrect password, got " + (3 - cont) + " tries left.");
    }
}
