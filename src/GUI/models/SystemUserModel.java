package GUI.models;

import BE.SystemUser;
import BLL.SystemUsers.ISystemUserManager;
import BLL.SystemUsers.SystemUserManager;
import GUI.BLLFacades.SystemUserLoginFacade;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SystemUserModel {
    private static ObservableObjectValue<SystemUser> loggedInSystemUser;
    private SystemUserLoginFacade systemUserLoginFacade;
    private ObservableList<SystemUser> allSystemUsers;

    private ISystemUserManager systemUserManager;

    public SystemUserModel() throws Exception {
        loggedInSystemUser = new SimpleObjectProperty<>(null);
        systemUserLoginFacade = new SystemUserLoginFacade();
        systemUserManager = new SystemUserManager();
        allSystemUsers =  FXCollections.observableList(retrieveAllUsers());

    }

    public boolean login(String email, String password) throws Exception {
        SystemUser loginCredentials = new SystemUser(email, password);

        loggedInSystemUser = new SimpleObjectProperty<>(systemUserLoginFacade.login(loginCredentials));

        return loggedInSystemUser.get() != null;
    }

    public ObservableValue<SystemUser> getLoggedInSystemUser() {
        return loggedInSystemUser;
    }

    public SystemUser createSystemUser(SystemUser user) throws Exception {
        return systemUserManager.createSystemUser(user);
    }

    public List<SystemUser> retrieveAllUsers() throws Exception {
        return systemUserManager.getAllSystemUsers();
    }

    public ObservableList<SystemUser> getAllUsers() throws Exception {
        retrieveAllUsers();
        return allSystemUsers;
    }

    public void deleteSystemUser(SystemUser user) throws Exception {
        SystemUser deletedSystemUser = systemUserManager.deleteSystemUser(user);

        if (deletedSystemUser != null) {
            allSystemUsers.remove(deletedSystemUser);
        }
    }
}
