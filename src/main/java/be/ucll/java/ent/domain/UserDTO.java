package be.ucll.java.ent.domain;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String userid;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    public UserDTO(String userid, String password) {
        this.userid = userid;
        this.password = password;
        firstName = "Ongeïdentificeerde";
        lastName = "gebruiker";
        isAdmin = false;
    }

    public UserDTO(String userid, String password, String firstName, String lastName, boolean isAdmin) {
        this.userid = userid;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean equals(Object o) {
        if (o instanceof UserDTO) {
            return ((UserDTO) o).getUserid() == getUserid();
        }
        return false;
    }
}