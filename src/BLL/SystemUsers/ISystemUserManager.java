package BLL.SystemUsers;

import BE.SystemUser;

public interface ISystemUserManager {
    /**
     * Tries to authenticate a system user.
     * @param user The user to authenticate.
     * @return The corresponding authenticated user, or null if it failed.
     * @throws Exception
     */
    SystemUser authenticateSystemUser(SystemUser user) throws Exception;


    SystemUser createSystemUser(SystemUser systemUser) throws Exception;
}
