package BE;

public class SystemUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public SystemUser(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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
}
