package DAL.UsersOnEvents;

import BE.Event;
import BE.SystemUser;

import java.util.List;

public interface IUsersOnEventsDAO {

    void assignUserToEvent(SystemUser user, Event event) throws Exception;

    List<SystemUser> getUsersAssignedToEvent(Event event) throws Exception;

    List<Event> getEventsAssignedToUser(SystemUser user) throws Exception;

}
