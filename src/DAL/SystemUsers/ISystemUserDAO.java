package DAL.SystemUsers;

import BE.SystemUser;

import java.util.List;

public interface ISystemUserDAO {
    /**
     * Validates if a given user exists.
     * @param user The user to validate.
     * @return Returns the corresponding user if
     * the Email and password are valid. Null if otherwise.
     * @throws Exception
     */
    SystemUser SystemUserValidLogin(SystemUser user) throws Exception;

    SystemUser createSystemUser(SystemUser systemUser) throws Exception;

    List<SystemUser> getAllSystemUsers() throws Exception;

    SystemUser deleteSystemUser(SystemUser systemUser) throws Exception;

    SystemUser systemUserValidLogin(SystemUser user) throws Exception;
}
