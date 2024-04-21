package dbConnection;

import org.jetbrains.annotations.NotNull;
import password.NoSuchServiceException;
import password.Password;
import password.RepeatedPasswordException;
import user.NoSuchUserException;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import user.User;

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
     * @throws NoSuchUserException case in which said user does not exist.
     */
    public List<Password> getAllPasswords() throws NoSuchUserException {
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
            throw new NoSuchUserException("Usuario not found");
        }

        return listPassword;
    }

    /**
     *  Gets all the passwords in the system corresponding to the introduced service.
     *
     * @param service String containing the name of the service to get the password-username
     * @return List containing username-password for said service
     * @throws NoSuchServiceException in case it does not exist a service with said name
     * @throws NoSuchUserException in case the specified user does not exist
     */
    public List<Password> getPassword(@NotNull String service) throws NoSuchServiceException, NoSuchUserException{
        List<Password> aux = getAllPasswords().stream().filter(e -> e.getServicio().equals(service.toUpperCase())).toList();
        if(!aux.isEmpty())
            return aux;
        else
            throw new NoSuchServiceException("There are no passwords saved of that service");

    }

    /**
     * Adds a new password (to be new has to have different usernames in the same service
     * and in case usernames are the same, different services) to the DB.
     *
     * @param ps password to be introduced in the DB.
     * @return true if the password was sucessfully introduced, false otherwise.
     */
    public boolean newPassword(@NotNull Password ps) throws RepeatedPasswordException {
        String sql = "insert into passwords (idUsuario, usuario, password, servicio) values (?,?,?,upper(?))";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, ps.getUserName());
            stmt.setString(3, ps.getPassword());
            stmt.setString(4, ps.getServicio().toUpperCase());
            checkExistingPassword(ps);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                System.out.println(rs.getInt(1));
            return true;

        } catch (SQLException | NoSuchUserException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * @param newPassword string containing the new password for the service.
     * @param servicio    string containing the name for the service to change the password.
     * @return true if it could be changed, false otherwise.
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
     * Checks if already exists a password for the same service and with the same username.
     *
     * @param ps a Password.
     * @throws RepeatedPasswordException in case the password already exists.
     * @throws NoSuchUserException in case the said user does not exist.
     */
    private void checkExistingPassword(Password ps) throws RepeatedPasswordException, NoSuchUserException{
        if(getAllPasswords().stream().anyMatch(e -> e.equals(ps)))
            throw new RepeatedPasswordException("Username-service exists already in the system");
    }
}