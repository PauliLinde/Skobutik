import java.nio.file.Path;
import java.util.List;

public class Shoe {
    private int id;
    private int size;
    private String color;
    private double price;
    private String brand;
    private String shoePath;


    public Shoe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getShoePath() {
        return shoePath;
    }

    public void setShoePath(String shoePath) {
        this.shoePath = shoePath;
    }
}
