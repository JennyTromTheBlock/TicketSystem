package DAL.SystemUsers;

import BE.SystemUser;

public interface ISystemUserDAO {
    /**
     * Validates if a given user exists.
     * @param user The user to validate.
     * @return Returns the corresponding user if
     * the Email and password are valid. Null if otherwise.
     * @throws Exception
     */
    SystemUser SystemUserValidLogin(SystemUser user) throws Exception;
}
