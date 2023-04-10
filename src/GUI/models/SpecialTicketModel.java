package GUI.models;

import BE.SpecialTicketType;
import GUI.BLLFacades.SpecialTicketsFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SpecialTicketModel {
    private SpecialTicketsFacade specialTicketsFacade;
    private ObservableList<SpecialTicketType> specialTicketTypes;

    public SpecialTicketModel() throws Exception {
        specialTicketsFacade = new SpecialTicketsFacade();
        specialTicketTypes = FXCollections.observableList(specialTicketsFacade.availableSpecialTicketTypes());
    }

    public void addNewSpecialTicketType(SpecialTicketType newType) throws Exception {
        if (!typeExistsAlready(newType)) {
            SpecialTicketType createdType = specialTicketsFacade.addNewSpecialTicketType(newType);

            if (createdType != null) specialTicketTypes.add(createdType);
        }
    }

    public boolean typeExistsAlready(SpecialTicketType newType) {
        for (SpecialTicketType type : specialTicketTypes) {
            boolean isSameName = type.getTypeName().equalsIgnoreCase(newType.getTypeName());

            if (isSameName) return true;
        }

        return false;
    }

    public ObservableList<SpecialTicketType> getSpecialTicketTypes() {
        return  specialTicketTypes;
    }
}
