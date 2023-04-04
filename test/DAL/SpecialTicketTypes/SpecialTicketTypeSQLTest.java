package DAL.SpecialTicketTypes;

import BE.SpecialTicketType;
import DAL.Connectors.TestSqlConnector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class SpecialTicketTypeSQLTest {
    @DisplayName("Create new special ticket type")
    @Test
    void createSpecialTicketType() {
        try {
            ISpecialTicketTypeDAO specialTicketTypeDAO = new SpecialTicketTypeSQL(new TestSqlConnector());
            SpecialTicketType type = new SpecialTicketType(UUID.randomUUID().toString());

            SpecialTicketType newType = specialTicketTypeDAO.createSpecialTicketType(type);

            Assertions.assertNotNull(newType);
        }
        catch (Exception e) {
            Assertions.fail("An exception was thrown", e);
        }
    }

    @DisplayName("Retrieve special ticket types")
    @Test
    void getSpecialTicketTypes() {
        try {
            ISpecialTicketTypeDAO specialTicketTypeDAO = new SpecialTicketTypeSQL(new TestSqlConnector());

            List<SpecialTicketType> types = specialTicketTypeDAO.getSpecialTicketTypes();

            Assertions.assertNotNull(types);
        }
        catch (Exception e) {
            Assertions.fail("An exception was thrown", e);
        }
    }
}
