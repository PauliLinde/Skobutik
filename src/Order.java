import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private boolean payedOrder;
    private List<Contains> shoesInOrder;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customerId;
    }

    public void setCustomer(int customerId) {
        this.customerId = customerId;
    }

    public boolean isPayedOrder() {
        return payedOrder;
    }

    public void setPayedOrder(boolean payedOrder) {
        this.payedOrder = payedOrder;
    }

    public List<Contains> getShoesInOrder() {
        return shoesInOrder;
    }

    public void setShoesInOrder(List<Contains> shoesInOrder) {
        this.shoesInOrder = shoesInOrder;
    }
}
