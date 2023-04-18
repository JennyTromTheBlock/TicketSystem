package BLL.SystemUsers;

import BE.SystemUser;
import BLL.DALFacades.AuthenticationFacade;
import BLL.Util.BCrypt;
import DAL.SystemUsers.SystemUserDAO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SystemUserManager implements ISystemUserManager {

    private AuthenticationFacade authenticateFacade;
    private SystemUserDAO systemUserDAO;

    public SystemUserManager() throws Exception {
        authenticateFacade = new AuthenticationFacade();
        systemUserDAO = new SystemUserDAO();
    }

    @Override
    public SystemUser authenticateSystemUser(SystemUser user) throws Exception, NoSuchAlgorithmException {

        SystemUser s = authenticateFacade.authenticateSystemUser(user);
        if(BCrypt.checkpw(user.getPassword(), s.getPassword())){
            return s;
        }
        return null;
    }

    @Override
    public SystemUser createSystemUser(SystemUser systemUser) throws Exception {
        String salt = BCrypt.gensalt(10);
        String hashedPassword = BCrypt.hashpw(systemUser.getPassword(), salt);
        systemUser.setPassword(hashedPassword);
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
