package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;
import DAL.Connectors.TestSqlConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecialTicketTypeSQLTest {
    @DisplayName("Create new special ticket type")
    @Test
    void createSpecialTicketType() {
        try {
            ISpecialTicketTypeDAO specialTicketTypeDAO = new SpecialTicketTypeSQL(new TestSqlConnector());
            SpecialTicketType type = new SpecialTicketType("Free drink");

            SpecialTicketType newType = specialTicketTypeDAO.createSpecialTicketType(type);

            Assertions.assertTrue(newType != null && newType.getType().equals(type.getType()));
        }
        catch (Exception e) {
            Assertions.fail("An exception was thrown", e);
        }
    }
}
