import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    Properties p = new Properties();
    public Repository() {
        try {
            p.load(new FileInputStream("src/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Shoe> getShoes(){
        List<Shoe> shoes = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from skobutik.sko");
        ){
            while(rs.next()) {
                Shoe shoe = new Shoe();
                shoe.setId(rs.getInt("id"));
                shoe.setSize(rs.getInt("storlek"));
                shoe.setColor(rs.getString("färg"));
                shoe.setPrice(rs.getDouble("pris"));
                shoe.setBrand(rs.getString("märke"));
                shoe.setShoePath("C:" + File.separator + "Users" + File.separator + "juspa" + File.separator + "Downloads" + File.separator + "Skor" + File.separator + shoe.getId() + ".jpg");
                System.out.println(shoe.getShoePath());
                shoes.add(shoe);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return shoes;
    }

    public List<Customer> getCustomers(){
        List<Customer> customers = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from skobutik.kund inner join skobutik.beställning where kund.id=beställning.kundId");
        ){
            while(rs.next()) {
                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("namn"));
                customer.setAddress(rs.getString("adress"));
                customer.setPostcode(rs.getInt("postnummer"));
                customer.setCity(rs.getString("ort"));
                customer.setPassword(rs.getString("lösenord"));
                customers.add(customer);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return customers;
    }

    public List<Order> getOrder(){
        List<Order> orders = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from skobutik.beställning inner join skobutik.kund on beställning.kundId=kund.id");
        ){
            while(rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomer(rs.getInt("kundId"));
                order.setPayedOrder(rs.getBoolean("betald"));

                orders.add(order);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return orders;
    }

    public String addShoeToCart(int customerId, int orderId, int shoeId) {
        String message = "";
        try(Connection c = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"))){

            CallableStatement cstmt = c.prepareCall("call skobutik.addToCart(?, ?, ?)");

            cstmt.setInt(1, customerId);
            System.out.println(customerId);
            cstmt.setInt(2, orderId);
            System.out.println(orderId);
            cstmt.setInt(3, shoeId);
            System.out.println(shoeId);
            cstmt.executeQuery();

            message = "Produkten tillagd";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            message = e.getMessage();
        }
        return message;
    }

}
