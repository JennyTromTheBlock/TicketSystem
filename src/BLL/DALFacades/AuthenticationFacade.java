package BLL.DALFacades;

import BE.SystemUser;
import DAL.SystemUsers.ISystemUserDAO;
import DAL.SystemUsers.SystemUserDAO;

public class AuthenticationFacade {
    private ISystemUserDAO systemUserDAO;

    public AuthenticationFacade() throws Exception {
        systemUserDAO = new SystemUserDAO();
    }

    public boolean loginSystemUser(SystemUser user) throws Exception {
        return systemUserDAO.login(user);
    }
}
