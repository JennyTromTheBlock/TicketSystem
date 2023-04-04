package DAL.SystemUsers;

import BE.Role;
import BE.SystemUser;
import DAL.Connectors.TestSqlConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SystemUserDAOTest {

    @DisplayName("DAL System User Valid Login")
    @Test
    void systemUserValidLogin() {
        try {
            ISystemUserDAO systemUserDAO = new SystemUserDAO(new TestSqlConnector());
            SystemUser systemUser1 = new SystemUser("patand01@easv365.dk", "1234");

            SystemUser loginUser = systemUserDAO.systemUserValidLogin(systemUser1);

            boolean isLoginUserEmailCorrect = loginUser.getEmail().equals(systemUser1.getEmail());
            boolean isLoginUserFirstName = loginUser.getFirstName().equals("Patrick Darling");
            boolean isLoginUserLastName = loginUser.getLastName().equals("Andersen");
            boolean isLoginUserPasswordCorrect = loginUser.getPassword().equals("1234");
            boolean isLoginUserRoles = loginUser.getRoles().contains(Role.ADMINISTRATOR);

            Assertions.assertTrue(
                    isLoginUserEmailCorrect &&
                            isLoginUserFirstName &&
                            isLoginUserLastName &&
                            isLoginUserPasswordCorrect &&
                            isLoginUserRoles
            );
        }
        catch (Exception e) {
            Assertions.fail("An exception was thrown", e);
        }
    }
}