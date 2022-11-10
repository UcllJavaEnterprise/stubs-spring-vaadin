package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.UserDTO;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.List;

// Opening a new tab wil again show the login screen
@UIScope

// Uncomment to continue working in a new tab. No login screen appears
// @VaadinSessionScope

public class UserController {

    private UserDTO loggedInUser;

    private static List<UserDTO> users;

    static {
        users = new ArrayList<UserDTO>(2);
        users.add(new UserDTO("admin", "admin", "System", "Administrator", true));
        users.add(new UserDTO("user", "user", "Some", "User", false));
    }

    public UserDTO authenticateUser(UserDTO unauthenticateduser) {
        for (UserDTO user : users) {
            if (user.getUserid().equalsIgnoreCase(unauthenticateduser.getUserid()) &&
                    user.getPassword().equals(unauthenticateduser.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserSignedIn() {
        if (loggedInUser != null) return true;
        return false;
    }

    public UserDTO getUser() {
        return loggedInUser;
    }

    public void setUser(UserDTO user){
        loggedInUser = user;
    }

    public void reset() {
        loggedInUser = null;
    }
}