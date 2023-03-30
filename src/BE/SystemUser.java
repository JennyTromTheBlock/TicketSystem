package BE;

import java.util.ArrayList;
import java.util.List;

public class SystemUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<Role> roles;

    public SystemUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public SystemUser(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public static boolean isEmailValid(String email) {
        //TODO implement this method.
        return true;
    }

    public static boolean isPasswordValid(String password) {
        //TODO implement this method.
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() { return roles; }
}
