package BE;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SystemUser {
    private String email;
    private String firstName;
    private String lastName;

    public void setPassword(String password) {
        this.password = password;
    }

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
        roles.add(Role.EVENT_COORDINATOR);
    }


    public SystemUser(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
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

    @Override
    public String toString() {
        return "SystemUser{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}