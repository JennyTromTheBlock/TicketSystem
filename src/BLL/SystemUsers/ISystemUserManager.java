package BLL.SystemUsers;

import BE.SystemUser;

public interface ISystemUserManager {
    boolean authenticateSystemUser(SystemUser user) throws Exception;
}
