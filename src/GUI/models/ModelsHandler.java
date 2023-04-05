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
    private SpecialTicketModel specialTicketModel;

    private ModelsHandler() throws Exception {
        eventModel = new EventModel();
        ticketModel = new TicketModel();
        systemUserModel = new SystemUserModel();
        specialTicketModel = new SpecialTicketModel();
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

    public SystemUserModel getSystemUserModel() {
        return systemUserModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public SpecialTicketModel getSpecialTicketModel() { return specialTicketModel; }
}
