package GUI.models;

import BE.SystemUser;
import BLL.SystemUsers.ISystemUserManager;
import BLL.SystemUsers.SystemUserManager;

public class SystemUserModel {
    private ISystemUserManager systemUserManager;

    public SystemUserModel() throws Exception {
        systemUserManager = new SystemUserManager();
    }

    public SystemUser createSystemUser(SystemUser user) throws Exception {
        return systemUserManager.createSystemUser(user);
    }
}
