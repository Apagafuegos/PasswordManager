package password;

import java.util.Objects;

public class Password {

    private String userName; //Del servicio
    private String password;
    private String servicio;

    public Password(String userName, String password, String servicio) {
        this.userName = userName;
        this.password = password;
        this.servicio = servicio.toUpperCase();
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(userName, password.userName) && Objects.equals(servicio, password.servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, servicio);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Password{" +
                "userName= " + userName +
                ", password= " + password +
                ", servicio=" + servicio +
                '}';
    }
}
