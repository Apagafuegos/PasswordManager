package dbConnection;

import user.ExistingUserException;
import user.NoSuchUserException;
import user.User;

import java.sql.*;

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
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                String uname = set.getString("nombre_usuario");
                String password = set.getString("password");
                user = new User(uname, password);
            }

        } catch (SQLException e) {
            System.out.println("No encontrado");
            throw new NoSuchUserException("Usuario no encontrado");
        }

        return user;
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
            throw new ExistingUserException("This user already exists");
        }
    }
}
