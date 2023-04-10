package BE;

public class SpecialTicketType {
    private String typeName;
    private int price;

    public SpecialTicketType(String type, int price) {
        this.typeName = type;
        this.price = price;
    }

    public String getTypeName() { return typeName; }

    public int getPrice() { return price; }
}
