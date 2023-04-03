package GUI.models;

import BE.SystemUser;
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

    public SystemUserModel() throws Exception {
        loggedInSystemUser = new SimpleObjectProperty<>(null);
        systemUserLoginFacade = new SystemUserLoginFacade();
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

    public ObservableList<SystemUser> getAllUsers() {
        return allSystemUsers;
    }
}
