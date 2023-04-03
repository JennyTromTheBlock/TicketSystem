package GUI.models;

/**
 * Singleton ModelsHandler
 * Ensures that all instances of our models are the same.
 */
public class ModelsHandler {

    private static ModelsHandler handler;
    private EventModel eventModel;
    private SystemUserModel systemUserModel;
    private TicketModel ticketModel;

    private ModelsHandler() throws Exception {
        eventModel = new EventModel();
        systemUserModel = new SystemUserModel();
        ticketModel = new TicketModel();
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

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public SystemUserModel getSystemUserModel() {
        return systemUserModel;
    }
}
