import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends JFrame {
    String username;
    String password;

    Repository repo = new Repository();
    Scanner scanner = new Scanner(System.in);
    Customer logedInCustomer = new Customer();
    Order order = new Order();
    Shoe shoe = new Shoe();

    private JPanel loginPanel;
    private JPanel shopPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginSuccess;


    Main(){
        setSize(500,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(loginPanel);
        loginPanel.setLayout(new FlowLayout());
        loginPanel.add(usernameField); loginPanel.add(passwordField);
        usernameField.setText("Användarnamn");
        passwordField.setText("Lösenord");
        passwordField.setEchoChar('*');
        usernameField.addActionListener(l -> username = usernameField.getText());
        passwordField.addActionListener(l -> password = passwordField.getText());

        List<Customer> customerList = new ArrayList<>();
        customerList = repo.getCustomers();
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(username) && customer.getPassword().equalsIgnoreCase(password)) {
                loginPanel.removeAll();
                loginPanel.add(loginSuccess);
                loginPanel.revalidate();
                loginPanel.repaint();
                loginSuccess.setText("Inloggning lyckades");

                logedInCustomer = customer;
                List<Order> orderList = new ArrayList<>();
                orderList = repo.getOrder();
                for (Order order1 : orderList) {
                    if(order1.getCustomer() == customer.getId()){
                        order=order1;
                    }
                }
            }
        }
        if (logedInCustomer.getName() == null) {
            loginPanel.removeAll();
            loginPanel.add(loginSuccess);
            loginPanel.revalidate();
            loginPanel.repaint();
            loginSuccess.setText("Användarnamnet eller lösenordet är fel");
        }


        while(true) {
            System.out.println("Skriv in ditt användarnamn (ditt namn)");
            String username = scanner.nextLine().trim();
            System.out.println("Ditt lösenord");
            String password = scanner.nextLine().trim();
            List<Customer> customersList = new ArrayList<>();
            customersList = repo.getCustomers();
            for (Customer customer : customersList) {
                if (customer.getName().equalsIgnoreCase(username) && customer.getPassword().equalsIgnoreCase(password)) {
                    System.out.println("Inloggning lyckades");
                    logedInCustomer = customer;
                    List<Order> orderList = new ArrayList<>();
                    orderList = repo.getOrder();
                    for (Order order1 : orderList) {
                        if(order1.getCustomer() == customer.getId()){
                            order=order1;
                        }
                    }
                }
            }
            if (logedInCustomer.getName() == null) {
                System.out.println("Användarnamnet eller lösenordet är fel");
            } else {
                System.out.println(logedInCustomer.getAddress());
            }
            System.out.println("Vilken sko vill du lägga till i din beställning, ange märke och skonummer");
            List<Shoe> shoeList = new ArrayList<>();
            shoeList = repo.getShoes();
            for (Shoe s : shoeList) {
                System.out.println(s.getBrand() + " " + s.getSize() + " " + s.getColor() + " " + s.getPrice());
            }
            String chosenShoe = scanner.nextLine().trim();
            for (Shoe s : shoeList) {
                if(chosenShoe.equalsIgnoreCase(s.getBrand() + " " + s.getSize())){
                    shoe=s;
                }
            }
            repo.addShoeToCart(logedInCustomer.getId(), order.getId(), shoe.getId());
            System.out.println(shoe.getBrand() + " " + shoe.getSize() + " tillagd i din korg");
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
    }
}