package BLL.DALFacades;

import BE.SystemUser;
import DAL.SystemUsers.ISystemUserDAO;
import DAL.SystemUsers.SystemUserDAO;

public class AuthenticationFacade {
    private ISystemUserDAO systemUserDAO;

    public AuthenticationFacade() throws Exception {
        systemUserDAO = new SystemUserDAO();
    }

    public SystemUser authenticateSystemUser(SystemUser user) throws Exception {
        return systemUserDAO.systemUserValidLogin(user);
    }
}
