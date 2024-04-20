package user;

public class User {

    private int id;
    private String nombreUsuario;
    private String password;

    public User(String usname, String pword){
        this.nombreUsuario = usname;
        this.password = pword;
    }

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

    @Override
    public String toString() {
        return "user.User{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
