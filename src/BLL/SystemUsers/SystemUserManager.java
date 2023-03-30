package BLL.SystemUsers;

import BE.SystemUser;
import BLL.DALFacades.AuthenticationFacade;
import DAL.SystemUsers.SystemUserDAO;

public class SystemUserManager implements ISystemUserManager {



    private AuthenticationFacade authenticateFacade;
    private SystemUserDAO systemUserDAO;

    public SystemUserManager() throws Exception {
        authenticateFacade = new AuthenticationFacade();
        systemUserDAO = new SystemUserDAO();

    }

    @Override
    public SystemUser authenticateSystemUser(SystemUser user) throws Exception {
        return authenticateFacade.authenticateSystemUser(user);
    }

    @Override
    public SystemUser createSystemUser(SystemUser systemUser) throws Exception {
        return systemUserDAO.createSystemUser(systemUser);
    }
}
