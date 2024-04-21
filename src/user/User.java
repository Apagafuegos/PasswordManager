package user;

public class User {

    private int id;
    private String nombreUsuario;
    private String password;

    /**
     * Only constructor for the User class.
     * Creates an object with an username and its password.
     *
     * @param usname String containing the user's username
     * @param pword String containing the user's password.
     */
    public User(String usname, String pword){
        this.nombreUsuario = usname;
        this.password = pword;
    }
    /*GETTERS AND SETTERS*/
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /*END OF GETTERS AND SETTERS*/
    @Override
    public String toString() {
        return "user.User{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
