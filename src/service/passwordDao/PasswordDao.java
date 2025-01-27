package service.passwordDao;

import org.jetbrains.annotations.NotNull;
import model.Password;
import service.DBDao;
import service.userDao.UserDao;
import service.userDao.NoSuchUserException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import model.User;

public class PasswordDao extends DBDao {


    private static final UserDao UDAO = new UserDao(); //Class atribute to generate the user whose username has been introduced
    private User usuario;

    /**
     * Only constructor for PasswordDao.
     * Username needed for the connection to the database with all the data from said user.
     *
     * @param usuario String containing the username.
     */
    public PasswordDao(String usuario) {
        try {
            this.usuario = UDAO.getUser(usuario);
        } catch (NoSuchUserException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Setter of the class' user.
     *
     * @param usuario User object.
     */
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    /**
     * Generates a list of all the passwords contained in the DB of the introduced user.
     *
     * @return List of the passwords contained in the system by said user.
     * @throws NoPasswordsException case in which said user does not have any passwords.
     */
    public List<Password> getAllPasswords() throws NoPasswordsException {
        List<Password> listPassword = new ArrayList<>();
        String sql = "select * from passwords where idUsuario = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
                Password ps = new Password(
                        set.getString("usuario"),
                        set.getString("password"),
                        set.getString("servicio").toUpperCase()
                );

                listPassword.add(ps);

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        if (listPassword.isEmpty())
            throw new NoPasswordsException("This user does not have any password in the system");

        return listPassword;
    }

    /**
     * Gets all the passwords in the system corresponding to the introduced service.
     *
     * @param service String containing the name of the service to get the password-username
     * @return List containing username-password for said service
     * @throws NoSuchServiceException in case it does not exist a service with said name
     * @throws NoPasswordsException   in case the specified user does not have any passwords in the system
     */
    public List<Password> getPassword(@NotNull String service) throws NoSuchServiceException, NoPasswordsException {
        List<Password> aux = getAllPasswords().stream().filter(e -> e.getServicio().equals(service.toUpperCase())).toList();
        if (!aux.isEmpty())
            return aux;
        else
            throw new NoSuchServiceException("There are no passwords saved of that service");

    }

    /**
     * Adds a new password (to be new has to have different usernames in the same service
     * and in case usernames are the same, different services) to the DB.
     * Implements the later defined {@link #checkExistingPassword(Password)} method to
     * check if the password already exists.
     *
     * @param username String contaning the username.
     * @param password String contaning the password.
     * @param service String contaning the service.
     * @return true if it could be created, false otherwise.
     * @throws RepeatedPasswordException in case the password already exists.
     */
    public boolean newPassword(String username, String password, String service) throws RepeatedPasswordException {
        String sql = "insert into passwords (idUsuario, usuario, password, servicio) values (?,?,?,upper(?))";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, service.toUpperCase());
            checkExistingPassword(new Password(username, password, service));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                System.out.println(rs.getInt(1));
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Method that updates the password for a unique username-service.
     *
     * @param newPassword String containing the new password.
     * @param servicio String containing the name of the service.
     * @return true if it could be changed, false otherwise.
     * @throws NoSuchServiceException in case the server does not exist.
     */
    public boolean updatePassword(String newPassword, @NotNull String servicio) throws NoSuchServiceException {
        String sql = "update passwords set password = ? where idUsuario = ? and servicio = upper(?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, servicio.toUpperCase());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new NoSuchServiceException("Said service does not exist");
        }
    }

    /**
     * Method that deletes a password (service-username-password) from the DB.
     *
     * @param service String containing the service.
     * @param username String containing the username
     * @param password String containing the password (needed for filtering through all the passwords, as default constructor
     *                 of password class needs it).
     * @return true if it could be deleted, false otherwise.
     * @throws NoPasswordsException in case the user does not have any password.
     * @throws NoSuchServiceException in case said service does not exist.
     */
    public boolean deletePassword(String service, String username, String password) throws NoPasswordsException, NoSuchServiceException {
        if(getAllPasswords().stream().anyMatch(e -> e.equals(new Password(username, password, service)))){
            String sql = "delete from passwords where id = (select id from passwords where idUsuario = ? and servicio = ? and usuario = ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNombreUsuario());
                stmt.setString(2, service.toUpperCase());
                stmt.setString(3, username);
                stmt.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new NoSuchServiceException("Said service does not exist");
            }
        }
        return false;
    }

    /**
     * Checks if already exists a password for the same service and with the same username.
     *
     * @param ps a Password.
     * @throws RepeatedPasswordException in case the password already exists.
     */
    private void checkExistingPassword(Password ps) throws RepeatedPasswordException {
        try {
            if (getAllPasswords().stream().anyMatch(e -> e.equals(ps)))
                throw new RepeatedPasswordException("Username-service exists already in the system");
        } catch (NoPasswordsException ignored) {

        }
    }
}