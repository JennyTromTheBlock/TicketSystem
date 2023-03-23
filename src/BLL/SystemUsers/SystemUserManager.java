package BLL.SystemUsers;

import BE.SystemUser;
import BLL.DALFacades.AuthenticationFacade;

public class SystemUserManager implements ISystemUserManager {
    private AuthenticationFacade authenticateFacade;

    public SystemUserManager() throws Exception {
        authenticateFacade = new AuthenticationFacade();
    }

    @Override
    public SystemUser authenticateSystemUser(SystemUser user) throws Exception {
        return authenticateFacade.authenticateSystemUser(user);
    }
}