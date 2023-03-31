package GUI.models;

/**
 * Singleton ModelsHandler
 * Ensures that all instances of our models are the same.
 */
public class ModelsHandler {
    private static ModelsHandler handler;
    private EventModel eventModel;
    private SystemUserModel systemUserModel;

    private ModelsHandler() throws Exception {
        eventModel = new EventModel();
        systemUserModel = new SystemUserModel();
    }

    public static ModelsHandler getInstance() throws Exception {
        if (handler == null) {
            handler = new ModelsHandler();
        }
        return handler;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public SystemUserModel getSystemUserModel() { return systemUserModel; }
}
