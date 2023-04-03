package GUI.BLLFacades;

import BE.SystemUser;
import BLL.SystemUsers.ISystemUserManager;
import BLL.SystemUsers.SystemUserManager;

public class SystemUserLoginFacade {
    private ISystemUserManager systemUserManager;

    public SystemUserLoginFacade() throws Exception {
        systemUserManager = new SystemUserManager();
    }

    public SystemUser login(SystemUser systemUser) throws Exception {
        return systemUserManager.authenticateSystemUser(systemUser);
    }
}
