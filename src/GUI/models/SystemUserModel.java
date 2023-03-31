package GUI.models;

import BE.SystemUser;
import GUI.BLLFacades.SystemUserLoginFacade;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;

public class SystemUserModel {
    private static ObservableObjectValue<SystemUser> loggedInSystemUser;
    private SystemUserLoginFacade systemUserLoginFacade;

    public SystemUserModel() throws Exception {
        loggedInSystemUser = new SimpleObjectProperty<>(null);
        systemUserLoginFacade = new SystemUserLoginFacade();
    }

    public boolean login(String email, String password) throws Exception {
        SystemUser loginCredentials = new SystemUser(email, password);

        loggedInSystemUser = new SimpleObjectProperty<>(systemUserLoginFacade.login(loginCredentials));

        return loggedInSystemUser.get() != null;
    }

    public ObservableValue<SystemUser> getLoggedInSystemUser() {
        return loggedInSystemUser;
    }
}
