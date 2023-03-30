package BE;

public enum Role {
    ADMINISTRATOR("Administrator"),
    EVENT_COORDINATOR("Event Coordinator");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public static Role getRole(String role) {
        if (isAdministrator(role)) return ADMINISTRATOR;

        if (isEventCoordinator(role)) return EVENT_COORDINATOR;

        return null;
    }

    private static boolean isAdministrator(String role) {
        return role.equals(ADMINISTRATOR.getRole());
    }

    private static boolean isEventCoordinator(String role) {
        return role.equals(EVENT_COORDINATOR.getRole());
    }
}
