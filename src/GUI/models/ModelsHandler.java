package GUI.models;

/**
 * Singleton ModelsHandler
 * Ensures that all instances of our models are the same.
 */
public class ModelsHandler {
    private static ModelsHandler handler;
    private EventModel eventModel;

    public ModelsHandler() throws Exception {
        eventModel = new EventModel();
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
}
