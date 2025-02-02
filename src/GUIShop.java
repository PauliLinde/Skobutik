import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GUIShop extends JFrame {
    String username;
    String password;

    Repository repo = new Repository();
    Scanner scanner = new Scanner(System.in);
    Customer logedInCustomer = new Customer();
    Order order = new Order();
    Shoe shoe = new Shoe();
    List<Shoe> shoeList;

    private JPanel loginPanel = new JPanel();
    private JPanel shopPanel = new JPanel();

    private JLabel usernameLabel = new JLabel("Användarnamn: ");
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Lösenord: ");
    private JPasswordField passwordField = new JPasswordField();
    private JLabel loginSuccess = new JLabel();
    private JButton loginButton = new JButton("Enter");


    GUIShop(){
        setLayout(new BorderLayout());
        add(loginPanel , BorderLayout.CENTER);

        loginPanel.setLayout(new GridLayout(5,1));
        loginPanel.add(usernameLabel); loginPanel.add(usernameField);
        loginPanel.add(passwordLabel); loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        usernameLabel.setSize(300, 50);
        passwordLabel.setSize(300, 50);

        usernameField.addActionListener(u -> username = usernameField.getText());
        passwordField.addActionListener(p -> password = passwordField.getText());
        loginButton.addActionListener(l -> login());

        pack();
        setSize(500,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void login(){
        List<Customer> customerList = repo.getCustomers();
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(username) && customer.getPassword().equalsIgnoreCase(password)) {
                logedInCustomer = customer;
                java.util.List<Order> orderList = repo.getOrder();
                for (Order order1 : orderList) {
                    if(order1.getCustomer() == customer.getId()){
                        order=order1;
                    }
                }
            }
        }
        loginSuccess();
    }

    public void loginSuccess(){
        loginPanel.removeAll();
        loginPanel.add(loginSuccess);
        if(logedInCustomer.getName() != null){
            loginSuccess.setText("Inloggningen lyckades");
        }
        else
            loginSuccess.setText("Inloggningen misslyckades");
        theShoeShop();
        loginPanel.revalidate();
        loginPanel.repaint();
    }

    public void theShoeShop(){
        getContentPane().removeAll();
        add(shopPanel, BorderLayout.CENTER);
        shoeList = repo.getShoes();
        List<JButton> shoeButtonList = new ArrayList<>();
        for (Shoe s : shoeList) {
            JButton button = new JButton(s.getBrand() + " " + s.getSize() + " " + s.getColor() + " " + s.getPrice());
            shopPanel.add(button);
            shoeButtonList.add(button);
        }
        for (JButton button : shoeButtonList) {
            button.setSize(300, 50);
            button.addActionListener(l -> findShoe(button.getText()));
        }
        revalidate();
        repaint();
    }

    public void findShoe(String buttonText){
        for (Shoe s : shoeList) {
            if(buttonText.contains(s.getBrand() + " " + s.getSize())){
                shoe=s;
            }
        }
        addShoeToCart();
    }

    public void addShoeToCart(){
        repo.addShoeToCart(logedInCustomer.getId(), order.getId(), shoe.getId());
    }

    public static void main(String[] args) {
        GUIShop shop = new GUIShop();
    }
}
