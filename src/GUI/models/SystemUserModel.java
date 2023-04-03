package GUI.models;

import BE.SystemUser;
import BLL.SystemUsers.ISystemUserManager;
import BLL.SystemUsers.SystemUserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class SystemUserModel {
    private ISystemUserManager systemUserManager;
    private ObservableList<SystemUser> allSystemUsers;

    public SystemUserModel() throws Exception {
        systemUserManager = new SystemUserManager();
        allSystemUsers =  FXCollections.observableList(retrieveAllUsers());
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
