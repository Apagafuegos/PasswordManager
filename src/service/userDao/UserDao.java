package service.userDao;

import service.DBDao;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends DBDao {
    /**
     * Extends DBDao class, creating a connection with the DB containing user's info.
     *
     * @param username, String of the name of the user (UNIQUE, PK).
     * @return User from DB.
     * @throws NoSuchUserException, in case the provided username is not contained in the DB.
     */
    public User getUser(String username) throws NoSuchUserException {
        User user = null;
        String sql = "select * from users where nombre_usuario = ?";
        if(!userExists(username))
            throw new NoSuchUserException("User not found");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                String uname = set.getString("nombre_usuario");
                String password = set.getString("password");
                user = new User(uname, password);
            }

        } catch (SQLException e) {
            System.out.println("Not found");
        }

        return user;
    }

    /**
     * Method that generates a list of all the users in the database.
     *
     * @return a List containing all the users.
     */
    public List<User> getAllUsers(){
        String sql = "select * from users";
        List<User> userList = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                String uname = set.getString("nombre_usuario");
                String password = set.getString("password");
                User user = new User(uname, password);
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Not found");
        }

        return userList;
    }

    /**
     * Creates a new user with the given Username and password.
     *
     * @param username String with the introduced username
     * @param password String with the introduced password.
     * @return true if the user could be created, false otherwise.
     * @throws ExistingUserException in case the user with said username already exists.
     */
    public boolean newUser(String username, String password) throws ExistingUserException {
        String sql = "insert into users(nombre_usuario, password) values(?,?)";
        if(userExists(username)){
            throw new ExistingUserException("This user already exists");
        }
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println(rs.getInt(1));
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new ExistingUserException("There was a problem creating the user");
        }
    }

    /**
     * Method to check if a user exists in the database.
     *
     * @param uname String containing the username
     * @return true if it exists, false otherwise.
     */
    public boolean userExists(String uname){
        List<User> allUsers = getAllUsers();
        return allUsers.stream().anyMatch(e -> e.getNombreUsuario().equalsIgnoreCase(uname));
    }

    /**
     * Method to log in into an existing account.
     * Checks if the introduced password is correct.
     * Made for an easier implementation in {@link gui.Login}
     *
     * @param username String that contains the username.
     * @param password String that contains the password.
     * @return a stance of the newly logged User
     * @throws NoSuchUserException in case said user does not exist.
     * @throws IncorrectPasswordException in case the password introduced is incorrect.
     */
    public User login(String username, String password) throws NoSuchUserException, IncorrectPasswordException {
        User user = getUser(username);
        if(user.getPassword().equals(password))
            return user;
        else
            throw new IncorrectPasswordException("Incorrect password");
    }

    /**
     * Method that inserts a new user into the DB.
     * Made for an easier implementation in {@link gui.Register}
     *
     * @param username String that contains the username.
     * @param password String that contains the password.
     * @return a stance of the newly introduced User.
     * @throws ExistingUserException in case the user already exists.
     */
    public User register(String username, String password) throws ExistingUserException {
        newUser(username, password);
        return (new User(username, password));
    }
}
