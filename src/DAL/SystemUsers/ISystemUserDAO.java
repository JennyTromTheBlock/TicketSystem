package DAL.SystemUsers;

import BE.SystemUser;

public interface ISystemUserDAO {
    /**
     * Validates if a given user exists.
     * @param user The user to validate.
     * @return true if the user matches an entry. False otherwise.
     */
    boolean login(SystemUser user) throws Exception;
}
