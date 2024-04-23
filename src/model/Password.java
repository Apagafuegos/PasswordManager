package model;

import java.util.Objects;

public class Password {

    private String userName; //Del servicio
    private String password;
    private String servicio;

    /**
     * Only constructor of Password class.
     *
     * @param userName String containing the username of the password.
     * @param password String containing the password for the service.
     * @param servicio String containing the name of the service.
     */
    public Password(String userName, String password, String servicio) {
        this.userName = userName;
        this.password = password;
        this.servicio = servicio.toUpperCase();
    }
    /*Getters and setters*/
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /*END OF GETTERS AND SETTERS*/

    /**
     * Override of the {@link Object} equals.
     * Two passwords are considered equal if their usernames
     * and their services(ignoring capitalisation) are the same.
     * @param o an Object
     * @return true if both passwords are equal, false if they are not or they are another class type.
     */
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

    @Override
    public String toString() {
        return "Password{" +
                "userName= " + userName +
                ", password= " + password +
                ", servicio=" + servicio +
                '}';
    }
}
