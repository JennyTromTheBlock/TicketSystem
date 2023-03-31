package BLL.SystemUsers;

import BE.SystemUser;
import BLL.DALFacades.AuthenticationFacade;
import DAL.SystemUsers.SystemUserDAO;

import java.util.List;

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

    @Override
    public SystemUser deleteSystemUser(SystemUser systemUser) throws Exception {
        return systemUserDAO.deleteSystemUser(systemUser);
    }

    @Override
    public List<SystemUser> getAllSystemUsers() throws Exception {
        return systemUserDAO.getAllSystemUsers();
    }
}
